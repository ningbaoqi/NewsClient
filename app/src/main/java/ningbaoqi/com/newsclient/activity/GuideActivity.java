package ningbaoqi.com.newsclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

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

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager guideRoot;
    private static final int[] resID = new int[]{R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    private ArrayList<ImageView> imageViews;
    private LinearLayout llPoints;
    private View redPoint;
    private int lengthWithPoint;//原点之间需要移动的距离
    private Button btnGuide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        guideRoot.setAdapter(new GuidePageAdapter());
        llPoints.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {//因为在OnCreate方法执行的时候，View还没有绘制，所以设置监听器，当onLayout调用完成之后完成回调
                lengthWithPoint = llPoints.getChildAt(1).getLeft() - llPoints.getChildAt(0).getLeft();
                llPoints.getViewTreeObserver().removeOnGlobalLayoutListener(this);//因为会多次调用onLayout，由于只需要收集一次即可，所以注销掉监听器
            }
        });
        guideRoot.setOnPageChangeListener(new MyPageChangeListener());
        btnGuide.setOnClickListener(this);
    }

    private void initView() {
        imageViews = new ArrayList<>();
        guideRoot = (ViewPager) findViewById(R.id.guideRoot);
        llPoints = (LinearLayout) findViewById(R.id.ll_points);
        redPoint = findViewById(R.id.guide_red_point);
        btnGuide = (Button) findViewById(R.id.btn_guide);
        for (int i = 0; i < resID.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(resID[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }
        for (int i = 0; i < resID.length; i++) {
            View view = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp2px(10), dp2px(10));
            view.setBackgroundResource(R.drawable.guide_point_gray);
            if (i > 0) {
                params.leftMargin = dp2px(10);
            }
            view.setLayoutParams(params);
            llPoints.addView(view);
        }
    }

    private int dp2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        SharedPreferenceUtils.setBoolean(this, SharedPreferenceUtils.FIRSTCOMEIN, false);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return resID.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        /**
         * 当移动ViewPager的时候调用
         *
         * @param position             当前的位置
         * @param positionOffset       移动的百分比
         * @param positionOffsetPixels 移动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();
            int translateDistance = (int) (lengthWithPoint * positionOffset + position * lengthWithPoint);
            params.leftMargin = translateDistance;
            redPoint.setLayoutParams(params);
        }

        @Override
        public void onPageSelected(int position) {
            if (position == resID.length - 1) {
                btnGuide.setVisibility(View.VISIBLE);
            } else {
                if (btnGuide.getVisibility() == View.VISIBLE) {
                    btnGuide.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
