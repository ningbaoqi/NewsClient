package ningbaoqi.com.newsclient.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.activity.HomeActivity;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-19
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

public abstract class BasePager {
    protected Activity mActivity;
    protected TextView titleText;
    protected ImageButton titleMenu;
    protected View rootView;
    protected FrameLayout mFrameLayoutContent;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        initView();
    }

    protected void initView() {
        rootView = View.inflate(mActivity, R.layout.base_pager_content, null);
        titleText = rootView.findViewById(R.id.title_text);
        titleMenu = rootView.findViewById(R.id.title_menu);
        mFrameLayoutContent = rootView.findViewById(R.id.framelayout_content);
    }

    public abstract void initData();

    public View getRootView() {
        return rootView;
    }

    protected void setSlidingMenuEnabled(boolean enabled) {
        if (enabled) {
            ((HomeActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            ((HomeActivity) mActivity).getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
}
