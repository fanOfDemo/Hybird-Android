package com.gtfund.gtapp.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gtfund.gtapp.R;
import com.gtfund.gtapp.common.base.BaseFragment;
import com.gtfund.gtapp.common.util.Log;

import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;

/**
 * Created by enzexue on 2018/8/10.
 */

public class XWalkCrossTabFragment extends BaseFragment{

    private static final String TAG = "XWalkCrossFragment";

    private XWalkView xWebView;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setContentView(R.layout.x_walk_view_fragment);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>

        return view;//返回值必须为view
    }


    @Override
    public void initView() {
        this.xWebView = findView(R.id.x_walk_view);
        this.xWebView.loadUrl(this.url);

        XWalkSettings mWebSettings = this.xWebView.getSettings();
        mWebSettings.setSupportZoom(true);//支持缩放
        mWebSettings.setBuiltInZoomControls(true);//可以任意缩放
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        mWebSettings.setLoadsImagesAutomatically(true);
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        mWebSettings.setJavaScriptEnabled(true);//支持JS

        Log.i(TAG, this.url);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    /**
     * 创建一个Fragment实例
     * @return
     */
    public static XWalkCrossTabFragment createInstance(String url) {
        XWalkCrossTabFragment fragment = new XWalkCrossTabFragment();
        fragment.setUrl(url);

        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;
    }
}
