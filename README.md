# AlbumsDemoProject

**封装图片信息获取工具类**

* 使用MediaStore获取数据库中的图片信息
* 利用Map不重复特性，保存相册名-相册信息键值对……
* 相册信息和图片信息封装为AlbumMessage类和ImageMessage类

**利用ImageLoader加载图片**

* 因为ImageLoader支持缓存以及拖动列表时暂停加载等
* 可以缓解列表有大量的图片加载的时候卡顿的现象
* 后来为了避免多次编写重复代码，将加载的过程封装成工具类

**列表的页面替换成Fragment**

* 将最近图片列表、相册列表、相册改为Fragment
* 因为发现Fragment的切换更高效
* 编写newInstance函数返回Fragment实例，降低Activity与Fragment间的耦合度
* 编写CallBack接口，让Activity实现其显示隐藏逻辑，便于让Activity统一管理Fragment
* 为了使点击Home后，在相册中增加删除图片后，返回应用后能够同步；于是在onResume中初始化列表数据；
* 最近图片的列表与相册图片的列表的RecyclerView使用同一个Adapter；

**RecyclerView的定位**

* 记录RecyclerView所滚动到的位置（一个比较常见的实现方式）

1.获取可见的第一个view；

2.获取他与顶部的偏移量lastOffset ；

3.得到这个view的数组位置lastPosition ；

4.滚动到这个位置： scrollToPositionWithOffset(lastPosition, lastOffset)

5.在recyclerView中addOnScrollListener监听滚动事件；

**自定义ToolBar**

* 继承LinearLayout，编setButtonClickListener、 setTitle、 setButton；
* 省的重复编写定制的ToolBar

**使用ViewPager显示图片**

* 为了左右滑动可以查看上下图片；使用了VeiwPager
* 编写了函数：setCurrentItem；遍历当前相册的所有相片的URL；获取与当前点击的图片的URL相同的选项，以确定当前ViewPager所在的位置；

**自定义图片显示控件：CustomImageView**

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

**//TODO**

* 还需要做一个最近图片的列表中，按时间分类显示

**思路1：** 加载多个RecyclerView；

**结论：** 太粗暴，我们有节操，不可取；

**思路2：** 改写RecyclerView；

**结论：** 还没想到具体的思路；

**思路3：** 利用ExpandableListView；

**最终方案：** 使用RecyclerView的getViewType方法；