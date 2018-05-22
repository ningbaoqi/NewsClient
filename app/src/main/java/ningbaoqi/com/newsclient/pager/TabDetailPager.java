package ningbaoqi.com.newsclient.pager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.base.BaseMenuDetailPager;
import ningbaoqi.com.newsclient.domiam.NewsData;
import ningbaoqi.com.newsclient.domiam.TabData;
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

public class TabDetailPager extends BaseMenuDetailPager implements ViewPager.OnPageChangeListener {
    private NewsData.NewsTabData mTabData;
    private String url;
    private TabData tabData;
    private ViewPager viewPager;
    private TextView tvTitle;
    private ArrayList<TabData.TopNewsData> topnews;

    public TabDetailPager(Activity mActivity, NewsData.NewsTabData newsTabData) {
        super(mActivity);
        this.mTabData = newsTabData;
        url = GlobalContants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        tvTitle = view.findViewById(R.id.tv_title);
        viewPager = view.findViewById(R.id.vp_news);
        viewPager.setOnPageChangeListener(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromServer();
    }

    public void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    private void parseData(String data) {
        Gson gson = new Gson();
        tabData = gson.fromJson(data, TabData.class);
        topnews = tabData.data.topnews;
        tvTitle.setText(topnews.get(0).title);
        Log.d(GlobalContants.TAG, tabData.toString());
        viewPager.setAdapter(new TopNewsAdapter());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvTitle.setText(topnews.get(position).title);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class TopNewsAdapter extends PagerAdapter {

        private final BitmapUtils bitmapUtils;

        public TopNewsAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);//设置默认图片
        }

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            TabData.TopNewsData topNewsData = topnews.get(position);
            bitmapUtils.display(imageView, topNewsData.topimage);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
