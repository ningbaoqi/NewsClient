package ningbaoqi.com.newsclient.base;

import android.app.Activity;
import android.view.View;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-21
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 * 新闻、专题、组图、互动页面的基类
 */

public abstract class BaseMenuDetailPager {
    protected Activity mActivity;
    public View mRootView;

    public BaseMenuDetailPager(Activity mActivity) {
        this.mActivity = mActivity;
        mRootView = initView();
    }

    public abstract View initView();

    public void initData() {

    }
}
