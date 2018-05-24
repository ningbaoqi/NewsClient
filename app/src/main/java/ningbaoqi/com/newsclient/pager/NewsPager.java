package ningbaoqi.com.newsclient.pager;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.activity.HomeActivity;
import ningbaoqi.com.newsclient.base.BaseMenuDetailPager;
import ningbaoqi.com.newsclient.base.BasePager;
import ningbaoqi.com.newsclient.domiam.NewsData;
import ningbaoqi.com.newsclient.global.GlobalContants;
import ningbaoqi.com.newsclient.menudetail.InteractMenuDetailPager;
import ningbaoqi.com.newsclient.menudetail.NewsMenuDetailPager;
import ningbaoqi.com.newsclient.menudetail.PhotoMenuDetailPager;
import ningbaoqi.com.newsclient.menudetail.TopicMenuDetailPager;
import ningbaoqi.com.newsclient.utils.CacheUtils;

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
    private ArrayList<BaseMenuDetailPager> pagers;//新闻中能够显示的四个详情页
    private NewsData newsData;

    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        setSlidingMenuEnabled(true);
        String cache = CacheUtils.getCache(GlobalContants.CATAGORY_URL, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            parseData(cache);//如果缓存存在直接解析数据
        }
        getDataFromServer();//为了避免服务器更新数据了，还是需要获取最新数据
    }

    /**
     * 从服务器获取数据
     */
    public void getDataFromServer() {
        Log.d("nbq", "getDataFromServer");
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalContants.CATAGORY_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                Log.d(GlobalContants.TAG, result);
                parseData(result);
                CacheUtils.setCache(GlobalContants.CATAGORY_URL, result, mActivity);//设置缓存
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d(GlobalContants.TAG, s);
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.net_error), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    /**
     * Gson 是谷歌为了简化Json解析提供的解析框架
     *
     * @param result
     */
    private void parseData(String result) {
        Gson gson = new Gson();
        newsData = gson.fromJson(result, NewsData.class);
        Log.d(GlobalContants.TAG, newsData.toString());
        ((HomeActivity) mActivity).getSlidingMenuFragment().setSlidingMenuData(newsData);

        pagers = new ArrayList<>();
        pagers.add(new NewsMenuDetailPager(mActivity, newsData.data.get(0).children));
        pagers.add(new TopicMenuDetailPager(mActivity));
        pagers.add(new PhotoMenuDetailPager(mActivity , titlePhoto));
        pagers.add(new InteractMenuDetailPager(mActivity));
        setCurrentMenuDetailPager(0);
    }

    /**
     * 设置当前菜单详情页
     *
     * @param position 当前页面的位置
     */
    public void setCurrentMenuDetailPager(int position) {
        BaseMenuDetailPager baseMenuDetailPager = pagers.get(position);
        mFrameLayoutContent.removeAllViews();
        mFrameLayoutContent.addView(baseMenuDetailPager.mRootView);
        titleText.setText(newsData.data.get(position).title);
        baseMenuDetailPager.initData();
        if (baseMenuDetailPager instanceof PhotoMenuDetailPager) {
            titlePhoto.setVisibility(View.VISIBLE);
        } else {
            titlePhoto.setVisibility(View.GONE);
        }
    }
}
