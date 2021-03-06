package ningbaoqi.com.newsclient.pager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.activity.NewsDetailActivity;
import ningbaoqi.com.newsclient.base.BaseMenuDetailPager;
import ningbaoqi.com.newsclient.domiam.NewsData;
import ningbaoqi.com.newsclient.domiam.TabData;
import ningbaoqi.com.newsclient.global.GlobalContants;
import ningbaoqi.com.newsclient.utils.CacheUtils;
import ningbaoqi.com.newsclient.utils.SharedPreferenceUtils;
import ningbaoqi.com.newsclient.view.RefreshListView;

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
    private CirclePageIndicator circlePageIndicator;
    private RefreshListView lv_list;
    private ArrayList<TabData.TabNewsData> mNewsList;
    private String mMoreUrl;//更多页面的地址
    private NewsAdapter adapter;
    private String ids = "";
    private Handler handler;

    public TabDetailPager(Activity mActivity, NewsData.NewsTabData newsTabData) {
        super(mActivity);
        this.mTabData = newsTabData;
        url = GlobalContants.SERVER_URL + mTabData.url;
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
        View headerView = View.inflate(mActivity, R.layout.list_header_view, null);
        tvTitle = headerView.findViewById(R.id.tv_title);
        viewPager = headerView.findViewById(R.id.vp_news);
        circlePageIndicator = headerView.findViewById(R.id.circleIndicator);
        lv_list = view.findViewById(R.id.lv_list);
        lv_list.addHeaderView(headerView);//为ListView添加HeadView
        lv_list.setOnRefreshListener(new RefreshListView.OnRefreshListener() {//设置下拉刷新监听
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                if (!TextUtils.isEmpty(mMoreUrl)) {
                    getMoreDataFromServer();
                } else {
                    Toast.makeText(mActivity, "最后一页了，请观看其他模块", Toast.LENGTH_LONG).show();
                    lv_list.onRefreshComplete(false);//收起脚布局
                }
            }
        });
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(GlobalContants.TAG, position + "");
                String mId = mNewsList.get(position).id;
                ids = SharedPreferenceUtils.getString(mActivity, SharedPreferenceUtils.READIDS, "");
                if (!ids.contains(mId)) {
                    ids += mId + ",";
                    SharedPreferenceUtils.setString(mActivity, SharedPreferenceUtils.READIDS, ids);
                }
                changeReadState(view);
                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                intent.putExtra("url", mNewsList.get(position).url);
                mActivity.startActivity(intent);
            }
        });
        return view;
    }

    /**
     * 专门改变已读颜色
     */
    private void changeReadState(View view) {
        TextView tvitle = view.findViewById(R.id.tv_title);
        tvitle.setTextColor(Color.GRAY);
    }

    @Override
    public void initData() {
        super.initData();
        String cache = CacheUtils.getCache(url, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache, false);
        }
        getDataFromServer();
    }

    public void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result, false);
                lv_list.onRefreshComplete(true);
                CacheUtils.setCache(url, result, mActivity);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                lv_list.onRefreshComplete(false);
            }
        });
    }

    /**
     * @param data
     * @param isLoadingMore
     */
    private void parseData(String data, boolean isLoadingMore) {
        Gson gson = new Gson();
        tabData = gson.fromJson(data, TabData.class);
        String more = tabData.data.more;
        if (!TextUtils.isEmpty(more)) {
            mMoreUrl = GlobalContants.SERVER_URL + more;
        } else {
            mMoreUrl = null;
        }
        if (!isLoadingMore) {
            topnews = tabData.data.topnews;
            mNewsList = tabData.data.news;
            if (topnews != null) {
                tvTitle.setText(topnews.get(0).title);
                Log.d(GlobalContants.TAG, tabData.toString());
                viewPager.setAdapter(new TopNewsAdapter());
                circlePageIndicator.setViewPager(viewPager);
                circlePageIndicator.setSnap(true);
                circlePageIndicator.setOnPageChangeListener(this);
                circlePageIndicator.onPageSelected(0);//让指示器重新定位到第一个点
            }
            if (mNewsList != null) {
                adapter = new NewsAdapter();
                lv_list.setAdapter(adapter);
            }
            if (handler == null) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        int currentItem = viewPager.getCurrentItem();
                        if (currentItem < topnews.size() - 1) {
                            currentItem++;
                        } else {
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem);
                        handler.sendEmptyMessageDelayed(0x123, 3000);
                    }
                };
                handler.sendEmptyMessageDelayed(0x123, 3000);
            }
        } else {//如果是加载下一页，需要将数据追加给原来的集合中
            ArrayList<TabData.TabNewsData> news = tabData.data.news;
            mNewsList.addAll(news);
            adapter.notifyDataSetChanged();
        }
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

    public void getMoreDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseData(result, true);
                lv_list.onRefreshComplete(true);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(mActivity, s, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                lv_list.onRefreshComplete(false);
            }
        });
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
            imageView.setOnTouchListener(new TopNewsTouchListener());
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class TopNewsTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendEmptyMessageDelayed(0x123, 3000);
                    break;
                case MotionEvent.ACTION_CANCEL://事件被取消时走这个
                    handler.sendEmptyMessageDelayed(0x123, 3000);
                default:
                    break;
            }
            return true;
        }
    }

    class NewsAdapter extends BaseAdapter {

        private final BitmapUtils bitmapUtils;

        public NewsAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public TabData.TabNewsData getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_news_item, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_pic = convertView.findViewById(R.id.iv_pic);
                viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
                viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            TabData.TabNewsData item = getItem(position);
            viewHolder.tv_title.setText(item.title);
            viewHolder.tv_date.setText(item.pubdate);
            bitmapUtils.display(viewHolder.iv_pic, item.listimage);//加载图片
            if (ids.contains(getItem(position).id)) {
                viewHolder.tv_title.setTextColor(Color.GRAY);
            } else {
                viewHolder.tv_title.setTextColor(Color.BLACK);
            }
            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView iv_pic;
        public TextView tv_title;
        public TextView tv_date;
    }
}
