package ningbaoqi.com.newsclient.utils.bitmap;

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

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 */
public class MemoryCacheUtils {
    //    private HashMap<String, SoftReference<Bitmap>> memoryCache = new HashMap<>();//软引用可以解决内存溢出，促使垃圾回收机制回收,google已经不提倡使用
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory();//获取手机最大内存，默认是16M
        lruCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();//Bitmap需要的字节数
                return byteCount;
            }
        };//需要设置缓存大小
    }


    /**
     * 从内存中读取
     *
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url) {
//        SoftReference<Bitmap> bitmapSoftReference = memoryCache.get(url);
//        if (bitmapSoftReference != null) {
//            Bitmap bitmap = bitmapSoftReference.get();
//            return bitmap;
//        }
//        return null;
        return lruCache.get(url);
    }

    /**
     * 写入内存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
//        SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
//        memoryCache.put(url, softBitmap);
        lruCache.put(url, bitmap);
    }
}
