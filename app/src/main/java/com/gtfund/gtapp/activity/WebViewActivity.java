package com.gtfund.gtapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gtfund.gtapp.common.base.BaseActivity;
import com.gtfund.gtapp.common.interfaces.OnBottomDragListener;
import com.gtfund.gtapp.common.util.StringUtil;
import com.gtfund.gtapp.common.util.Log;

import com.gtfund.gtapp.R;

/**
 * 通用网页Activity
 * Created by enzexue on 2018/8/8.
 */
public class WebViewActivity extends BaseActivity implements OnBottomDragListener, View.OnClickListener {
    public static final String TAG = "WebViewActivity";

    public static final String INTENT_RETURN = "INTENT_RETURN";
    public static final String INTENT_URL = "INTENT_URL";

    /**获取启动这个Activity的Intent
     * @param title
     * @param url
     */
    public static Intent createIntent(Context context, String title, String url) {
        return new Intent(context, WebViewActivity.class).
                putExtra(WebViewActivity.INTENT_TITLE, title).
                putExtra(WebViewActivity.INTENT_URL, url);
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    private String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity, this);//传this是为了全局滑动返回

        url = StringUtil.getCorrectUrl(getIntent().getStringExtra(INTENT_URL));
        if (StringUtil.isNotEmpty(url, true) == false) {
            Log.e(TAG, "initData  StringUtil.isNotEmpty(url, true) == false >> finish(); return;");
            enterAnim = exitAnim = R.anim.null_anim;
            finish();
            return;
        }

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private ProgressBar pbWebView;
    private WebView wvWebView;
    @Override
    public void initView() {
        autoSetTitle();

        pbWebView = findView(R.id.pbWebView);
        wvWebView = findView(R.id.wvWebView);
    }


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
    @Override
    public void initData() {

        WebSettings webSettings = wvWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        wvWebView.requestFocus();

        // 设置setWebChromeClient对象
        wvWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                tvBaseTitle.setText(StringUtil.getTrimedString(title));
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbWebView.setProgress(newProgress);
            }
        });

        wvWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                wvWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                tvBaseTitle.setText(StringUtil.getTrimedString(wvWebView.getUrl()));
                pbWebView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tvBaseTitle.setText(StringUtil.getTrimedString(wvWebView.getTitle()));
                pbWebView.setVisibility(View.GONE);
            }
        });

        wvWebView.loadUrl(url);
    }

    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void initEvent() {

        tvBaseTitle.setOnClickListener(this);
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            if (wvWebView.canGoForward()) {
                wvWebView.goForward();
            }
            return;
        }
        onBackPressed();
    }

    @Override
    public void onReturnClick(View v) {
        finish();
    }


    @Override
    public void onClick(View v) {
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onBackPressed() {
        if (wvWebView.canGoBack()) {
            wvWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    protected void onPause() {
        super.onPause();
        wvWebView.onPause();
    }

    @Override
    protected void onResume() {
        wvWebView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (wvWebView != null) {
            wvWebView.destroy();
            wvWebView = null;
        }
        super.onDestroy();
    }

    protected static final int REQUEST_TO_EDIT_TEXT_WINDOW = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
    }

    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
