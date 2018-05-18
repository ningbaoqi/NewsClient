package ningbaoqi.com.newsclient.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.fragment.HomeFragment;
import ningbaoqi.com.newsclient.fragment.SlidingMenuFragment;

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

public class HomeActivity extends SlidingFragmentActivity {
    private static final String HOMEFRAGMENTTAG = "home_fragment_tag";
    private static final String SLIDINGMENUTAG = "sliding_menu_tag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initSlidingMenu();
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_home_root, new HomeFragment(), HOMEFRAGMENTTAG);
        fragmentTransaction.replace(R.id.fragment_sliding_menu_root, new SlidingMenuFragment(), SLIDINGMENUTAG);
        fragmentTransaction.commit();
    }

    private void initSlidingMenu() {
        this.setBehindContentView(R.layout.home_sliding_menu);//设置隐藏布局文件
        SlidingMenu slidingMenu = getSlidingMenu();//获取SlidingMenu对象
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置SlidingMenu的触摸模式
        slidingMenu.setMode(SlidingMenu.LEFT);//设置SlidingMenu的模式
        slidingMenu.setBehindOffset(700);//设置剩余的宽度
    }
}
