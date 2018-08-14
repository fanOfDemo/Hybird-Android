package com.gtfund.gtapp.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gtfund.gtapp.R;
import com.gtfund.gtapp.common.base.BaseFragment;
import com.gtfund.gtapp.common.util.Log;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;

/**
 * Created by enzexue on 2018/8/10.
 */

public class XWalkCrossTabFragment extends BaseFragment {

    private static final String TAG = "XWalkCrossFragment";

    public XWalkView getxWebView() {
        return xWebView;
    }

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
        XWalkPreferences.setValue("enable-javascript", true);
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
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
//        XWalkPreferences.setValue(XWalkPreferences.ANIMATABLE_XWALK_VIEW, true);
        this.xWebView.loadUrl(this.url, null);
        xWebView.setBackgroundDrawable(null);
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

//
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (xWebView == null) return;
//        if (hidden) {// 不在最前端显示 相当于调用了onPause();
//            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    xWebView.setAlpha((Float) animation.getAnimatedValue());
//                }
//            });
//            valueAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    xWebView.onHide();
//                    xWebView.pauseTimers();
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    xWebView.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            });
//            valueAnimator.setDuration(2000);
//            valueAnimator.start();
//
//
////            xWebView.setVisibility(View.INVISIBLE);
//        } else {  // 在最前端显示 相当于调用了onResume()
////            xWebView.setVisibility(View.VISIBLE);
//            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    xWebView.setAlpha((Float) animation.getAnimatedValue());
//                }
//            });
//            valueAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    xWebView.setVisibility(View.VISIBLE);
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    xWebView.onShow();
//                    xWebView.resumeTimers();
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//
//                }
//            });
//            valueAnimator.setDuration(2000);
//            valueAnimator.start();
//
//
//        }
//    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (xWebView != null) {
            xWebView.onDestroy();
        }
    }


    /**
     * 创建一个Fragment实例
     *
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
