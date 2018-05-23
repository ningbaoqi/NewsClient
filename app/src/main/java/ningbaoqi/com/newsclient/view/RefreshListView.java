package ningbaoqi.com.newsclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 创建 时间:18-5-23
 * <p/>
 * 描    述:
 * <p/>
 * <p/>
 * 修订 历史:
 * <p/>
 * =========================================
 * 下拉刷新ListView
 */

public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final int STATE_PULL_REFRESH = 0;//下拉刷新
    private static final int STATE_EALEASE_REFRESH = 1;//松开刷新
    private static final int STATE_REFRESHING = 2;//正在刷新
    private int currentState = STATE_PULL_REFRESH;//当前的状态

    private View mHeaderView;
    private int startY = -1;//滑动起点的Y坐标
    private int headViewPadding;
    private TextView tvTitle;
    private TextView tvTime;
    private ProgressBar progressBar;
    private ImageView ivArrow;
    private RotateAnimation animationUp;//向上动画
    private RotateAnimation animationDown;//向下动画
    private View footerView;
    private int mFooterViewHeight;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
    }

    private void initHeaderView() {
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
        tvTitle = mHeaderView.findViewById(R.id.tv_titler);
        tvTime = mHeaderView.findViewById(R.id.tv_time);
        progressBar = mHeaderView.findViewById(R.id.progress);
        ivArrow = mHeaderView.findViewById(R.id.iv_arr);
        this.addHeaderView(mHeaderView);
        mHeaderView.measure(0, 0);
        headViewPadding = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -headViewPadding, 0, 0);
        initArrowAnim();
        tvTime.setText("最后刷新时间:" + getCurrentTime());
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.refresh_footer, null);
        this.addFooterView(footerView);
        footerView.measure(0, 0);
        mFooterViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -mFooterViewHeight, 0, 0);
        this.setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {//确保startY的值是有效的
                    startY = (int) ev.getRawY();
                }
                if (currentState == STATE_REFRESHING) {//正在刷新时不做处理
                    break;
                }
                int endY = (int) ev.getRawY();
                int distanceY = endY - startY;//移动的距离
                if (distanceY > 0 && getFirstVisiblePosition() == 0) {//只有在下拉并且现在是第一个Item才允许下拉
                    int paddding = distanceY - headViewPadding;
                    mHeaderView.setPadding(0, paddding, 0, 0);
                    if (paddding > 0 && currentState != STATE_EALEASE_REFRESH) {//将状态改为松开刷新
                        currentState = STATE_EALEASE_REFRESH;
                        refreshState();
                    } else if (paddding < 0 && currentState != STATE_PULL_REFRESH) {//将状态改为下拉刷新
                        currentState = STATE_PULL_REFRESH;
                        refreshState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentState == STATE_EALEASE_REFRESH) {
                    currentState = STATE_REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    refreshState();
                } else if (currentState == STATE_PULL_REFRESH) {
                    mHeaderView.setPadding(0, -headViewPadding, 0, 0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 刷新状态方法
     */
    private void refreshState() {
        switch (currentState) {
            case STATE_PULL_REFRESH:
                tvTitle.setText(getResources().getString(R.string.pull_refresh));
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.startAnimation(animationDown);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            case STATE_EALEASE_REFRESH:
                tvTitle.setText(getResources().getString(R.string.release_refersh));
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.startAnimation(animationUp);
                progressBar.setVisibility(View.INVISIBLE);
                break;
            case STATE_REFRESHING:
                tvTitle.setText(getResources().getString(R.string.refreshing));
                ivArrow.clearAnimation();//必须先清除动画才能隐藏
                ivArrow.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                if (listener != null) {
                    listener.onRefresh();
                }
                break;
        }
    }

    private void initArrowAnim() {
        animationUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationUp.setDuration(200);
        animationUp.setFillAfter(true);

        animationDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationDown.setDuration(200);
        animationDown.setFillAfter(true);
    }

    OnRefreshListener listener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    private boolean isLoadingMore = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {//滑动到了最后
                footerView.setPadding(0, 0, 0, 0);
                setSelection(getCount() - 1);
                isLoadingMore = true;
                if (listener != null) {
                    listener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * 收起下拉刷新的控件
     */
    public void onRefreshComplete(boolean success) {
        if (isLoadingMore) {
            footerView.setPadding(0, -mFooterViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            currentState = STATE_PULL_REFRESH;
            tvTitle.setText(getResources().getString(R.string.pull_refresh));
            ivArrow.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            mHeaderView.setPadding(0, -headViewPadding, 0, 0);
            if (success) {
                tvTime.setText("最后刷新时间:" + getCurrentTime());
            }
        }
    }

    /**
     * 获取当前的时间
     */
    public String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//H大写是24进制，h小写是12进制；MM表示一月份从1开始，小写表示一月份从0开始
        return simpleDateFormat.format(new Date());
    }
}
