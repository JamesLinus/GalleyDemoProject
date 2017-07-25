# AlbumsDemoProject

# 封装图片信息获取工具类

* 使用MediaStore获取数据库中的图片信息
* ~~利用Map不重复特性，保存相册名-相册信息键值对……~~ （已被优化
* 相册信息和图片信息封装为AlbumMessage类和ImageMessage类

# 利用ImageLoader加载图片

* 因为ImageLoader支持缓存以及拖动列表时暂停加载等
* 可以缓解列表有大量的图片加载的时候卡顿的现象
* 后来为了避免多次编写重复代码，将加载的过程封装成工具类

# 列表的页面替换成Fragment

* 将最近图片列表、相册列表、相册改为Fragment
* Fragment的切换更高效
* 编写newInstance函数返回Fragment实例，降低Activity与Fragment间的耦合度
* 编写CallBack接口，让Activity实现其显示隐藏逻辑，便于让Activity统一管理Fragment
* ~~为了使点击Home后，在相册中增加删除图片后，返回应用后能够同步；于是在onResume中初始化列表数据；~~ 修改为使用registerContentObserver实现数据更改时的监听
* ~~最近图片的列表与相册图片的列表的RecyclerView使用同一个Adapter；~~ 为了编写最近图片列表按时间分类，已分为两个Adapter

# RecyclerView的定位

* 记录RecyclerView所滚动到的位置（一个比较常见的实现方式）

1.获取可见的第一个view；

2.获取他与顶部的偏移量lastOffset ；

3.得到这个view的数组位置lastPosition ；

4.滚动到这个位置： scrollToPositionWithOffset(lastPosition, lastOffset)

5.在recyclerView中addOnScrollListener监听滚动事件；

# 自定义ToolBar

* 继承LinearLayout，编setButtonClickListener、 setTitle、 setButton；
* 避免重复编写定制的ToolBar

# 使用ViewPager显示图片

* 为了左右滑动可以查看上下图片；使用了VeiwPager
* 编写了函数：setCurrentItem；遍历当前相册的所有相片的URL；获取与当前点击的图片的URL相同的选项，以确定当前ViewPager所在的位置；

# 自定义图片显示控件：CustomImageView

* onMeasure中获取控件的长宽和图片Bitmap的原始长宽；对比两者，将图片拉伸至与控件顶格；并记录图片此时显示的长宽及缩放比；为了便于控制缩放比例，并避免ViewPager的视图复用问题，这里的缩放直接修改Matrix的矩阵值；
* 绘制时，将canvas移动到绘制起点的坐标为控件中心点减去图片长宽的一半；使得图片在中间显示；
* 缩放移动操作时，修改Matrix后调用setImageMatrix重绘；

（疑问： invalidate()与setImageMatrix()的对比）

* 缩放操作：获取两手指的距离，与上一次两手指距离对比，获取缩放倍数；调用postScale()；
* 其中，在缩放比为原始缩放比的3倍（或者1/3倍）时，并且此时在执行放大（缩小）操作时，onTouchEvent返回false；以达到限制放大倍数；（存在问题：此时可能会影响到平移操作）
* 平移操作：在两只手指同时在屏幕上时，获取两手指之间的中心点与上一次中心的位置，作为移动的方向与距离；调用postTranslate()
* 将缩放动画和X轴Y轴平移动画封装为函数；便于在手指放开时图片小于原始显示大小时调用、双击放大缩小时调用；
* 使用了ValueAnimator；
* 为了便于控制缩放比例，这里的缩放直接修改Matrix的矩阵值；
* 如果在动画播放过程中，双指触碰控件，则调用ValueAnimator的cancel函数取消动画；
* 双击的手势使用了GestureDecetor；声明了内部类MyGestureListener，改写其onDoubleTap函数；
* getParent().requestDisallowInterceptTouchEvent(true);请求父容器不要拦截该操作；避免在拖动以及缩放操作的时候触发ViewPager的切换操作

# 使用RecyclerView的getViewType写了多级列表17.07.23

* 还需要做一个最近图片的列表中，按时间分类显示

**思路1：** 加载多个RecyclerView；

**结论：** 太粗暴，我们有节操，不可取；

**思路2：** 改写RecyclerView；

**结论：** 还没想到具体的思路；

**思路3：** 利用ExpandableListView；

**最终方案：** 使用RecyclerView的getViewType方法；

# 多级列表的表头浮动17.07.24

外部布局使用FrameLayout，FrameLayout中有两个布局，一个为LinearLayout（作为title），一个为recyclerView；

title初始化为i第一个item的title；



RecyclerView addOnScrollListener，添加OnScrollListener监听RecyclerView的滚动；

“onScrollStateChanged”，当滚动状态改变时，获取title的高度；

“onScroll”，当列表滚动时，做两件事情：

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

以此来控制列表标题的浮动；

# 修改部分数据库查询语句17.07.25



* 将查询最近图片的语句增加了 limit 100限制；

* ```
  cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
          projection,
          null,
          null,
          MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc" + " limit 100");
  ```

极大的提高了性能；

* 将查询各相册图片数进行了优化：

  ```
  String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
              MediaStore.Images.ImageColumns.DATA,
              "COUNT(*)"};

  String selection = "0=0)GROUP BY (" + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;

  cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
              projection,
              selection,
              null,
              MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + "  desc");
  ```

# 增加ContentResolver，绑定数据变化的观察者

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