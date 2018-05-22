package ningbaoqi.com.newsclient.menudetail;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.activity.HomeActivity;
import ningbaoqi.com.newsclient.base.BaseMenuDetailPager;
import ningbaoqi.com.newsclient.domiam.NewsData;
import ningbaoqi.com.newsclient.pager.TabDetailPager;

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
 * 菜单详情页 新闻
 */

public class NewsMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private ArrayList<TabDetailPager> pagers;
    private ArrayList<NewsData.NewsTabData> mNewsTabData;//页签数据
    private TabPageIndicator indicator;
    private ImageButton next;

    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsData.NewsTabData> children) {
        super(mActivity);
        this.mNewsTabData = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
        mViewPager = view.findViewById(R.id.menu_detail);
        indicator = view.findViewById(R.id.indicator);
        indicator.setOnPageChangeListener(this);//当ViewPager和Indicator绑定时，设置滑动监听，需要设置给indicator而不是ViewPager
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        pagers = new ArrayList<>();
        for (int i = 0; i < mNewsTabData.size(); i++) {
            TabDetailPager pager = new TabDetailPager(mActivity, mNewsTabData.get(i));
            pagers.add(pager);
        }
        mViewPager.setAdapter(new MenuDetailAdapter());
        indicator.setViewPager(mViewPager);//必须在ViewPager设置完adapter后才能调用
    }

    @Override
    public void onClick(View v) {
        int currentItem = mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(++currentItem);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SlidingMenu slidingMenu = ((HomeActivity) mActivity).getSlidingMenu();
        if (position == 0) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MenuDetailAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager pager = pagers.get(position);
            container.addView(pager.mRootView);
            pager.initData();
            return pager.mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabData.get(position).title;
        }
    }
}
