package ningbaoqi.com.newsclient.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import ningbaoqi.com.newsclient.global.GlobalContants;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-22
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

public class TopNewsViewPager extends ViewPager {

    private int startX;
    private int startY;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 事件分发：请求父控件及祖宗控件是否拦截事件
     * 一：右滑：而且是第一个页面，需要父控件拦截
     * 二：左滑：而且是最后一个页面，需要父控件拦截
     * 三：上下滑动：需要父控件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//刚触摸的时候不要拦截，这样是为了保证，move的调用
                //raw是基于屏幕的位置，没有raw是基于父控件的移动
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getRawX();
                int endY = (int) ev.getRawY();
                if (Math.abs(endX - startX) > Math.abs(endY - startY)) {//如果X坐标的移动位移大于Y轴的移动位移，表示是在左右滑
                    if (endX > startX) {//往右滑
                        if (getCurrentItem() == 0) {
                            Log.d(GlobalContants.TAG, "" + getCurrentItem());
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {//往左滑
                        if (getCurrentItem() == getAdapter().getCount() - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else {//上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
