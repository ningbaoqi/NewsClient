package ningbaoqi.com.newsclient.pager;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.base.BasePager;
import ningbaoqi.com.newsclient.domiam.NewsData;
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
        getDataFromServer();
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
        NewsData newsData = gson.fromJson(result, NewsData.class);
        Log.d(GlobalContants.TAG, newsData.toString());
    }
}
