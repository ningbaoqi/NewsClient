package ningbaoqi.com.newsclient.pager;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import ningbaoqi.com.newsclient.base.BaseMenuDetailPager;
import ningbaoqi.com.newsclient.domiam.NewsData;

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
 * 页签详情页
 */

public class TabDetailPager extends BaseMenuDetailPager {
    private NewsData.NewsTabData mTabData;
    private TextView textView;

    public TabDetailPager(Activity mActivity, NewsData.NewsTabData newsTabData) {
        super(mActivity);
        this.mTabData = newsTabData;
    }

    @Override
    public View initView() {
        textView = new TextView(mActivity);
        textView.setText("页签详情页");
        textView.setTextSize(22);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText(mTabData.title);
    }
}
