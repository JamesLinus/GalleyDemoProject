# AlbumsDemoProject

# 封装图片信息获取工具类

* **首先需要获取图片信息：** 于是，使用了MediaStore获取数据库中的图片信息

  ​

* 相册信息和图片信息封装为AlbumMessage类和ImageMessage类

# 利用ImageLoader加载图片

* **原因：** 因为ImageLoader支持缓存以及拖动列表时暂停加载等；可以缓解列表有大量的图片加载的时候卡顿的现象

  ​

* ~~后来为了避免多次编写重复代码，将加载的过程封装成工具类~~ 为了复用方便，已将ImageLoader的数据加载封装在自定义ImageView中；

# 列表的页面替换成Fragment

* 最近图片列表、相册列表、相册的视图利用Fragment进行展示，跳转效率更高；

  ​

* 编写newInstance函数返回Fragment实例，需要的数据利用参数的方式传入；降低Activity与Fragment间的耦合度

  ​

* 编写CallBack接口，让Activity实现其显示隐藏逻辑，便于让Activity统一管理Fragment

  ​

* ~~为了使点击Home后，在相册中增加删除图片后，返回应用后能够同步；于是在onResume中初始化列表数据；~~ 修改为使用**registerContentObserver** 实现数据更改时的监听

# RecyclerView的定位

* 记录RecyclerView所滚动到的**位置** （一个比较常见的实现方式）
* 具体方法如下：

1.获取可见的第一个view；

2.获取他与顶部的偏移量lastOffset ；

3.得到这个view的数组位置lastPosition ；

4.滚动到这个位置： scrollToPositionWithOffset(lastPosition, lastOffset)

5.在recyclerView中addOnScrollListener监听滚动事件；

```
private void getPositionAndOffset() {
    LinearLayoutManager layoutManager = (LinearLayoutManager) mRvImages.getLayoutManager();
    /** 获取可视的第一个view */
    View topView = layoutManager.getChildAt(0);
    if(topView != null) {
        //获取与该view的顶部的偏移量
        lastOffset = topView.getTop();
        //得到该View的数组位置
        lastPosition = layoutManager.getPosition(topView);
    }
}

/**
 * 让RecyclerView滚动到指定位置
 */
private void scrollToPosition() {
    if(mRvImages.getLayoutManager() != null && lastPosition >= 0) {
        ((LinearLayoutManager) mRvImages.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
    }
}
```

```
mRvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                if(recyclerView.getLayoutManager() != null) {
//                    getPositionAndOffset();
//                }
            }
        });
```

**已弃用：** 后来修改代码，让RecyclerView的初始化在onCreate中进行，避免了返回视图时RecyclerView重载导致可视部分重载为初始状态的情况；

# 自定义ToolBar

* 继承LinearLayout，编setButtonClickListener、 setTitle、 setButton；
* 避免重复编写定制的ToolBar

# 使用ViewPager显示图片

* 使用了VeiwPager（没有Tab），使得左右滑动可以查看上下图片
* 编写了函数：setCurrentItem；遍历当前相册的所有相片的URL；获取与当前点击的图片的URL相同的选项，以确定当前ViewPager所在的位置；

# 自定义图片显示控件：CustomImageView

* **拉伸图片至与View顶格显示：** 

  1.onMeasure中获取控件的长宽和图片Bitmap的原始长宽；

  2.对比两者，将利用Matrix将图片拉伸至与控件顶格；

  3.记录图片此时显示的长宽及缩放比；

  //为了便于控制缩放比例，并避免ViewPager的视图复用问题，这里的缩放直接修改Matrix的矩阵值；

  ​

* **将图片绘制在View中心位置：** 

  绘制时，将canvas移动到绘制起点的坐标为控件中心点减去图片长宽的一半；使得图片在中间显示；



* **缩放与平移：** 

  1.**缩放操作：** 在双指放在屏幕上时，获取双指初始距离；双指移动时，获取两手指的距离，每一帧都与上一次两手指距离对比，获取缩放倍数；调用Matrix的postScale()后调用setImageMatrix()；

  2.**平移操作：** 在双指放在屏幕上时，获取双指的初始中心点；双指移动时，获取两手指的中心点，每一帧都与上一次获取的中心点对比，调用Matrix的postTranslate()后调用setImageMatrix()；

  （疑问： invalidate()与setImageMatrix()的对比）

  ​

* **控制缩放极限：** 在缩放比为原始缩放比的3倍（或者1/3倍）时，并且此时在执行放大（缩小）操作时，onTouchEvent返回false；以达到限制放大倍数；（**存在问题：** 此时可能会影响到平移操作）

  ​

* *控制与ViewPager的时间冲突** getParent().requestDisallowInterceptTouchEvent(true);请求父容器不要拦截该操作；避免在拖动以及缩放操作的时候触发ViewPager的切换操作

  ​


* **缩放动画：** 将缩放动画和X轴Y轴平移动画封装为函数；便于在手指放开时图片小于原始显示大小时调用、双击放大缩小时调用；

  1.使用了ValueAnimator；

  2.为了便于控制缩放比例，这里的缩放直接修改Matrix的矩阵值；

  3.如果在动画播放过程中，双指触碰控件，则调用ValueAnimator的cancel函数取消动画；

  4.双击的手势使用了GestureDecetor；声明了内部类MyGestureListener，改写其onDoubleTap函数；

  ​

# 使用RecyclerView的getViewType写了多级列表17.07.23

* 还需要做一个最近图片的列表中，按时间分类显示

**思路1：** 加载多个RecyclerView；

**结论：** 太粗暴，我们有节操，不可取；

**思路2：** 改写RecyclerView；

**结论：** 还没想到具体的思路；

**思路3：** 利用ExpandableListView；

~~**最终方案：** 使用RecyclerView的getViewType方法；~~

后来，为了利用OnScrollListener的方式编写多级列表表头浮动效果，改用RecyclerView的item中套用一个RecyclerView；

# 多级列表的表头浮动17.07.24

1.外部布局使用FrameLayout；FrameLayout中分别有两个子布局，**一个为LinearLayout（作为title）** ，**另一个为recyclerView** ；

title中的文字初始化为RecyclerView中第一个item的title；



2.RecyclerView addOnScrollListener，添加OnScrollListener监听RecyclerView的滚动；

**“onScrollStateChanged”** ，当滚动状态改变时，获取title的高度：

```
public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    super.onScrollStateChanged(recyclerView, newState);
    height = mLlTop.getHeight();
}
```

**“onScroll”** ，当列表滚动时，做两件事情：

```
public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /**
         * 获取下一个显示的View / 即，当前topView所覆盖的item的下一个item
         * 如过下一个item的顶部坐标小于height（因为屏幕坐标系的原点是左上角；
         * 此时证明该item已经开始顶掉当前item了；
         * 那么，此时，滚动过程中设置mLlTop的Y值，即让他向上移动
         * 下一个item完全顶替当前item后，它就是当前item了；
         * 于是进行下一轮检测；
         */
        View view = linearLayoutManager.findViewByPosition(currentPosition + 1);
        if (null == view)return;
        if (view.getTop() <= height){
            mLlTop.setY(view.getTop()-height);
        }else {
            mLlTop.setY(0);
        }

        /**
         * 获取当前第一个可见item的位置；
         * 获取后设置其文字
         */
        if (currentPosition != linearLayoutManager.findFirstVisibleItemPosition()){
            currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
            mLlTop.setY(0);

            mTvTop.setText(mListTitle.get(currentPosition));
        }
    }
});
```

# 修改部分数据库查询语句17.07.25

* **将查询最近图片的语句增加了 limit 100限制；** 

* ```
  cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
          projection,
          null,
          null,
          MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc" + " limit 100");
  ```

避免了搜索所有数据后取前100条，极大的提高了性能；

* 将查询各**相册图片数**进行了优化：

  ```
  String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
              MediaStore.Images.ImageColumns.DATA,
              "COUNT(*)"};

  //将数据按文件夹名归类
  String selection = "0=0)GROUP BY (" + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;

  cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
              projection,
              selection,
              null,
              MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + "  desc");
  ```

# 利用ContentResolver的registerContentObserver绑定数据变化时的观察者

```
contentResolver = getActivity().getContentResolver();
contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, new 										RecentChangeContentObserver(new Handler()));
private class RecentChangeContentObserver extends ContentObserver {
        public RecentChangeContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            initData();
        }
    }
```


# 将图片加载封装到自定义ImageView中17.07.25

将图片加载的过程封装到ImageView中，便于使用；

```
public void setImage(String uri){
    ImageLoader mImageLoader;
    DisplayImageOptions options;
    mImageLoader = ImageLoader.getInstance();
    options = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .build();

    if (!mImageLoader.isInited()) {
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }
    mImageLoader.displayImage("file://" + uri, this, options);
}
```

# 尝试着将代码结构整改为MVP模式17.07.26

将数据查询的工具类，整改为Model层；

eg：

```
public interface IImageMessageModel {
    ImageMessage getImageMessage(ContentResolver contentResolver, String uri);
}
```



```
public class ImageMessageModel implements IImageMessageModel {

    public static ImageMessageModel getInstance(){
        return new ImageMessageModel();
    }

    @Override
    public ImageMessage getImageMessage(ContentResolver contentResolver, String uri) {
        ImageMessage imageMessage = new ImageMessage();
        Cursor cursor;

        String[] projection = {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DISPLAY_NAME,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        String selection = MediaStore.Images.ImageColumns.DATA +  "=?";

        String[] selectionArgs = {uri};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);

        cursor.moveToNext();

        String id = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

        String imageName = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));

        String albumName = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));

        String imageUri = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

        long date = cursor.getLong(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

        imageMessage.setId(id);
        imageMessage.setImageName(imageName);
        imageMessage.setAlbum(albumName);
        imageMessage.setPath(imageUri);
        imageMessage.setDate(date);

        cursor.close();
        cursor = null;

        return imageMessage;
    }
}
```

让Activity和Fragment分别绑定各自的Presenter层；将Activity和Fragment中的逻辑处理，分离到Presenter层中判断；

eg：

```
public interface IImageMessageView {

    void setImageId(String id);

    void setImageName(String imageName);

    void setImageFile(String file);

    void setImagePath(String path);

    void setCreateDate(String date);

}
```



```
public class ImageMessagePresenter {
    private IImageMessageView mIImageMessageView;
    private ImageMessageModel mImageMessageModel;
    private ImageMessage mImageMessage;

    public ImageMessagePresenter(IImageMessageView view){
        mIImageMessageView = view;
        mImageMessageModel = ImageMessageModel.getInstance();
    }

    public void loadData(Context context,String imageUri, ContentResolver contentResolver){
        mImageMessage = mImageMessageModel.getImageMessage(contentResolver, imageUri);

        mIImageMessageView.setImageId(mImageMessage.getId());
        mIImageMessageView.setImageName(mImageMessage.getImageName());
        mIImageMessageView.setImageFile(mImageMessage.getAlbum());
        mIImageMessageView.setImagePath(mImageMessage.getPath());
        mIImageMessageView.setCreateDate(mImageMessage.getDate().toString());
    }


}
```



```
public class ImageMessageActivity extends AppCompatActivity implements IImageMessageView{

    private static final String IMAGE_MESSAGE_KEY = "image_message";

    private String imageUri;

    private ContentResolver contentResolver;

    private TextView mTvImageMessageId;
    private TextView mTvImageMessageName;
    private TextView mTvImageMessageFile;
    private TextView mTvImageMessagePath;
    private TextView mTvImageMessageDate;
    private CustomToolBar mCustomToolBar;

    private ImageMessagePresenter mImageMessagePresenter;

    public static Intent newInstance(Context context, String imageMessageKey){
        Intent intentImageMessageActivity = new Intent(context, ImageMessageActivity.class);
        intentImageMessageActivity.putExtra(IMAGE_MESSAGE_KEY, imageMessageKey);
        return intentImageMessageActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_message_activity);

        findWidget();

        imageUri = getIntent().getStringExtra(IMAGE_MESSAGE_KEY);
        contentResolver = getContentResolver();

        mImageMessagePresenter = new ImageMessagePresenter(this);
        mImageMessagePresenter.loadData(this, imageUri, contentResolver);

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    public void setImageId(String id) {
        mTvImageMessageId.setText(id);
    }

    @Override
    public void setImageName(String imageName) {
        mTvImageMessageName.setText(imageName);
    }

    @Override
    public void setImageFile(String file) {
        mTvImageMessageFile.setText(file);
    }

    @Override
    public void setImagePath(String path) {
        mTvImageMessagePath.setText(path);

    }

    @Override
    public void setCreateDate(String date) {
        mTvImageMessageDate.setText(date);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                finishActivity();
                break;
            }
            default:{
                break;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    private void finishActivity(){
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private void findWidget(){
        mTvImageMessageId = (TextView)findViewById(R.id.tv_image_id);
        mTvImageMessageName = (TextView)findViewById(R.id.tv_image_name);
        mTvImageMessageFile = (TextView)findViewById(R.id.tv_image_file);
        mTvImageMessagePath = (TextView)findViewById(R.id.tv_image_path);
        mTvImageMessageDate = (TextView)findViewById(R.id.tv_image_date);

        mCustomToolBar = (CustomToolBar) findViewById(R.id.ctb_message);
    }
}
```



**尝试使用MVP模式的感受：** 分离后的View层感觉更整洁了；代码的层次更清晰一些了；需要改动一些逻辑的时候只需要改动Presenter就可以了；

**一些没弄懂的东西：** 对一些列表数据，不知道自己的编写有没有符合规范；