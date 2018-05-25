package ningbaoqi.com.newsclient.utils.bitmap;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import ningbaoqi.com.newsclient.R;
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
 * 创建 时间:18-5-24
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 * Bitmap工具类
 */

/**
 * 自定义图片加载工具类
 */
public class MyBitmapUtils {
    NetCacheUtils netCacheUtils;
    LocalCacheUtils localCacheUtils;
    MemoryCacheUtils memoryCacheUtils;

    public MyBitmapUtils() {
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        netCacheUtils = new NetCacheUtils(localCacheUtils, memoryCacheUtils);
    }

    public void display(ImageView ivPic, String listimageUrl) {
        ivPic.setImageResource(R.drawable.news_pic_default);//设置默认图片
        Bitmap bitmap = null;
        //从内存中读取
        bitmap = memoryCacheUtils.getBitmapFromMemory(listimageUrl);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            Log.d(GlobalContants.TAG, "read from memory");
            return;
        }
        //从本地SD卡读取
        bitmap = localCacheUtils.getBitmapFromLocal(listimageUrl);
        if (bitmap != null) {
            ivPic.setImageBitmap(bitmap);
            memoryCacheUtils.setBitmapToMemory(listimageUrl, bitmap);
            Log.d(GlobalContants.TAG, "read from local");
            return;
        }
        //从网络中读
        netCacheUtils.getBitmapFromNet(ivPic, listimageUrl);
    }
}
