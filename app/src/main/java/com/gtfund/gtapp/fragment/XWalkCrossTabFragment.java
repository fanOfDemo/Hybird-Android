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

    public XWalkView getxWebView() {
        return xWebView;
    }

    public void setxWebView(XWalkView xWebView) {
        this.xWebView = xWebView;
    }

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
        xWebView = findView(R.id.x_walk_view);
        xWebView.loadUrl(url);
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
