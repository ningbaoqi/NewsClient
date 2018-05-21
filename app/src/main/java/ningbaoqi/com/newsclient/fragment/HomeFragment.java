package ningbaoqi.com.newsclient.fragment;

import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.base.BasePager;
import ningbaoqi.com.newsclient.pager.GovPager;
import ningbaoqi.com.newsclient.pager.HomePager;
import ningbaoqi.com.newsclient.pager.NewsPager;
import ningbaoqi.com.newsclient.pager.SettingsPager;
import ningbaoqi.com.newsclient.pager.SmartServicePager;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-18
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

public class HomeFragment extends BaseFragment {
    //@ViewInject(R.id.RadioGroup_root)
    private RadioGroup radioGroupRoot;
    //@ViewInject(R.id.home_view_pager)
    private ViewPager homeViewPager;
    private ArrayList<BasePager> pagers;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_home, null);
        //x.Ext.init(getActivity().getApplication());//初始化
        radioGroupRoot = view.findViewById(R.id.RadioGroup_root);
        homeViewPager = view.findViewById(R.id.home_view_pager);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        radioGroupRoot.check(R.id.RadioButton_home);//选中第一个
        pagers = new ArrayList<>();
        pagers.add(new HomePager(mActivity));
        pagers.add(new NewsPager(mActivity));
        pagers.add(new SmartServicePager(mActivity));
        pagers.add(new GovPager(mActivity));
        pagers.add(new SettingsPager(mActivity));
        homeViewPager.setAdapter(new MyContentAdapter());
        radioGroupRoot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.RadioButton_home:
                        homeViewPager.setCurrentItem(0, false);//不需要显示动画
                        break;
                    case R.id.RadioButton_news:
                        homeViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.RadioButton_smarrt:
                        homeViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.RadioButton_gov:
                        homeViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.RadioButton_settings:
                        homeViewPager.setCurrentItem(4, false);
                        break;
                }
            }
        });
        homeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagers.get(position).initData();//为了节省流量，当之后显示该界面的时候再去加载数据，因为ViewPager的机制是自动下载下一页
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagers.get(0).initData();
    }

    class MyContentAdapter extends PagerAdapter {

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
            BasePager pager = pagers.get(position);
            container.addView(pager.getRootView());
            return pager.getRootView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public NewsPager getNewsPager() {
        return (NewsPager) pagers.get(1);
    }
}

