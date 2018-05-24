package ningbaoqi.com.newsclient.utils;

import android.content.Context;

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
 * 缓存工具类:缓存的内容是json，以Url为key，以json为value
 */


public class CacheUtils {

    /**
     * 设置缓存
     *
     * @param key  url
     * @param json value
     */
    public static void setCache(String key, String json, Context context) {
        SharedPreferenceUtils.setString(context, key, json);
    }

    /**
     * 获取缓存
     *
     * @param key url
     */
    public static String getCache(String key, Context context) {
        return SharedPreferenceUtils.getString(context, key, null);
    }
}
