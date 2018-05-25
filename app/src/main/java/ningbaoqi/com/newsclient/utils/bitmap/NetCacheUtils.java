package ningbaoqi.com.newsclient.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
 * 创建 时间:18-5-25
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

/**
 * 网络缓存
 */
public class NetCacheUtils {
    LocalCacheUtils localCacheUtils;
    MemoryCacheUtils memoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 从网络下载图片
     *
     * @param ivPic
     * @param listimageUrl
     */
    public void getBitmapFromNet(ImageView ivPic, String listimageUrl) {
        new BitmapTask().execute(ivPic, listimageUrl);
    }

    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];
            ivPic.setTag(url);//将Url与InageView绑定，非常重要
            return downLoadBitmap(url);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                String bindUrl = (String) ivPic.getTag();
                if (url.equals(bindUrl)) {//确保图片设定给正确的ImageView，因为ImageView可能被重用，避免发生错乱
                    ivPic.setImageBitmap(bitmap);
                    localCacheUtils.setBitmapToLocal(bindUrl, bitmap);
                    memoryCacheUtils.setBitmapToMemory(bindUrl, bitmap);//将图片保存到内存
                    Log.d(GlobalContants.TAG, "read from net");
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private Bitmap downLoadBitmap(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;//压缩比例，宽高都压缩为原来的二分之一
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;//设置图片每个像素的大小
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream ,null , options);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
