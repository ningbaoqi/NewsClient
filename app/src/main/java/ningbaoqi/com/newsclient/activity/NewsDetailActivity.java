package ningbaoqi.com.newsclient.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

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
 * 新闻详情页
 */

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    private ImageButton back;
    private ImageButton share;
    private ImageButton size;
    private ProgressBar progress;
    private WebSettings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        mWebView = (WebView) findViewById(R.id.wv_web);
        back = (ImageButton) findViewById(R.id.btn_back);
        share = (ImageButton) findViewById(R.id.btn_share);
        size = (ImageButton) findViewById(R.id.btn_size);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        size.setOnClickListener(this);
        progress = (ProgressBar) findViewById(R.id.pb_progress);
        String url = getIntent().getStringExtra("url");
        settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        settings.setBuiltInZoomControls(true);//显示放大缩小按钮
        settings.setUseWideViewPort(true);//支持双击缩放
        mWebView.setWebViewClient(new WebViewClient() {
            /**
             * 网页开始加载
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
                Log.d(GlobalContants.TAG, "网页开始加载");
            }

            /**
             * 网页加载结束
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
                Log.d(GlobalContants.TAG, "网页加载结束");
            }

            /**
             * 所有跳转的链接都会在此方法中回调
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);//强制用自己的WebView加载
//                return super.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            /**
             * 获取网页标题
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            /**
             * 进度发生变化
             * @param view
             * @param newProgress
             */
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
//        mWebView.goBack();
        mWebView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                break;
            case R.id.btn_size:
                showChooseDialog();
                break;
            default:
                break;
        }
    }

    private int currentChooseItem = 2;

    private void showChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.textsize_settings));
        String[] items = new String[]{"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        builder.setSingleChoiceItems(items, currentChooseItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentChooseItem = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (currentChooseItem) {
                    case 0:
                        settings.setTextZoom(200);
                        break;
                    case 1:
                        settings.setTextZoom(150);
                        break;
                    case 2:
                        settings.setTextZoom(100);
                        break;
                    case 3:
                        settings.setTextZoom(75);
                        break;
                    case 4:
                        settings.setTextZoom(50);
                        break;
                }
            }
        }).setNegativeButton("取消", null);
        builder.create().show();
    }
}
