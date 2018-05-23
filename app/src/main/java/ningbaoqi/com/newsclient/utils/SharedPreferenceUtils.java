package ningbaoqi.com.newsclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * =========================================
 * <p/>
 * 版    权: ningxiansheng
 * <p/>
 * 作    者: 宁宝琪
 * <p/>
 * 版    本:1.0
 * <p/>
 * 创建 时间:18-5-17
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 */

public class SharedPreferenceUtils {
    private static final String NAME = "config";
    public static final String FIRSTCOMEIN = "first_com_in";
    public static final String READIDS = "read_ids";

    public static boolean getBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, value);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }
}
