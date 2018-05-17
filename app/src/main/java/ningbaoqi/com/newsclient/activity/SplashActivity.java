package ningbaoqi.com.newsclient.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import ningbaoqi.com.newsclient.R;

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

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout splashRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashRoot = (RelativeLayout) findViewById(R.id.splash_root);
        startAnimation();
    }

    private void startAnimation() {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        splashRoot.startAnimation(animationSet);
    }
}
