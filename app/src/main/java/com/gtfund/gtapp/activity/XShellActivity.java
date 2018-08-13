package com.gtfund.gtapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.gtfund.gtapp.R;
import com.gtfund.gtapp.common.base.BaseXWalkViewActivity;
import com.gtfund.gtapp.common.ui.CustomCrosswalk.CustomCrosswalkWebView;

import org.xwalk.core.XWalkSettings;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkSettingsInternal;
import org.xwalk.core.internal.XWalkViewBridge;

import java.lang.reflect.Method;

/**
 * Created by enzexue on 2018/8/10.
 */
public class XShellActivity extends BaseXWalkViewActivity implements View.OnClickListener{

    public static final String TAG = "XShellActivity";

    private CustomCrosswalkWebView xWalkViewMain;   // 主页的路由
    private CustomCrosswalkWebView xWalkViewFunds;  // 基金的路由
    private CustomCrosswalkWebView xWalkViewMy;     // 我的资产路由

    private LinearLayout tabMain;
    private LinearLayout tabFunds;
    private LinearLayout tabMy;

    private LinearLayout frameMainContainer;

    private TranslateAnimation mShowAction;
    private TranslateAnimation mHiddenAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_shell_activity);

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onXWalkReady() {
        xWalkViewMain = new CustomCrosswalkWebView(context);
        frameMainContainer.addView(xWalkViewMain);
        xWalkViewMain.loadUrl("https://m.gtfund.com/vue/index.html#/");

        xWalkViewFunds = new CustomCrosswalkWebView(context);
        frameMainContainer.addView(xWalkViewFunds);
        xWalkViewFunds.loadUrl("https://m.gtfund.com/vue/index.html#/jijin");

        xWalkViewMy = new CustomCrosswalkWebView(context);
        frameMainContainer.addView(xWalkViewMy);
        xWalkViewMy.loadUrl("https://m.gtfund.com/vue/index.html#/user");
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {
        tabMain = findView(R.id.tab_main);
        tabFunds = findView(R.id.tab_funds);
        tabMy = findView(R.id.tab_my);

        frameMainContainer = findView(R.id.frame_main_container);

        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(2000);

        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(2000);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        tabMain.setOnClickListener(this);
        tabFunds.setOnClickListener(this);
        tabMy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main:
                xWalkViewMain.setVisibility(View.VISIBLE);
                xWalkViewFunds.setVisibility(View.INVISIBLE);
                xWalkViewMy.setVisibility(View.INVISIBLE);
                break;
            case R.id.tab_funds:
                //xWalkViewMain.startAnimation(mHiddenAction);
                //xWalkViewMain.setVisibility(View.INVISIBLE);

                xWalkViewFunds.startAnimation(mShowAction);
                xWalkViewFunds.setVisibility(View.VISIBLE);

                //xWalkViewFunds.setVisibility(View.VISIBLE);
                //xWalkViewMy.setVisibility(View.INVISIBLE);
                break;
            case R.id.tab_my:
                xWalkViewMy.setVisibility(View.VISIBLE);
                xWalkViewFunds.setVisibility(View.INVISIBLE);
                xWalkViewMain.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
