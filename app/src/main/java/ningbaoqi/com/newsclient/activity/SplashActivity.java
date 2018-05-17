package ningbaoqi.com.newsclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import ningbaoqi.com.newsclient.R;
import ningbaoqi.com.newsclient.utils.SharedPreferenceUtils;

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
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jumpNextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splashRoot.startAnimation(animationSet);
    }

    private void jumpNextActivity() {
        if (SharedPreferenceUtils.getBoolean(this, SharedPreferenceUtils.FIRSTCOMEIN, true)) {
            startActivity(new Intent(this, GuideActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }
}
