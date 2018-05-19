package ningbaoqi.com.newsclient.pager;

import android.app.Activity;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.base.BasePager;

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

public class NewsPager extends BasePager {
    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        titleText.setText(mActivity.getResources().getString(R.string.news_title));
        setSlidingMenuEnabled(true);
    }
}
