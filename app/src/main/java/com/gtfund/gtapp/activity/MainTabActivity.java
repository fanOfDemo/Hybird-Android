package com.gtfund.gtapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gtfund.gtapp.R;
import com.gtfund.gtapp.common.base.BaseBottomTabActivity;
import com.gtfund.gtapp.fragment.XWalkCrossTabFragment;

import org.xwalk.core.XWalkView;

/**
 * Created by enzexue on 2018/8/8.
 */
public class MainTabActivity extends BaseBottomTabActivity{

    private static final String TAG = "MainTabActivity";

    private static final String[] TAB_NAMES = {"主页", "基金", "资产"};

    private XWalkCrossTabFragment fragmentTabMain;
    private XWalkCrossTabFragment fragmentTabFunds;
    private XWalkCrossTabFragment fragmentTabMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);

        //功能归类分区方法，必须调用<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {// 必须调用
        super.initView();
        exitAnim = R.anim.bottom_push_out;

        fragmentTabMain = XWalkCrossTabFragment.createInstance("https://m.gtfund.com/vue/index.html#/");
        fragmentTabFunds = XWalkCrossTabFragment.createInstance("https://m.gtfund.com/vue/index.html#/jijin");
        fragmentTabMy = XWalkCrossTabFragment.createInstance("https://m.gtfund.com/vue/index.html#/user");
    }

    @Override
    public void initData() {// 必须调用
        super.initData();
    }

    @Override
    public void initEvent() {// 必须调用
        super.initEvent();
    }

    @Override
    protected void selectTab(int position) {
        tvBaseTitle.setText(TAB_NAMES[position]);
    }

    @Override
    protected int[] getTabClickIds() {
        return new int[]{R.id.tab_main, R.id.tab_funds, R.id.tab_my};
    }

    @Override
    protected int[][] getTabSelectIds() {
        return new int[][]{
            new int[]{R.id.tab_main, R.id.tab_funds, R.id.tab_my}
        };
    }

    @Override
    public int getFragmentContainerResId() {
        return R.id.frame_main_tab_container;
    }

    @Override
    protected Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return fragmentTabMain;
            case 1:
                return fragmentTabFunds;
            case 2:
                return fragmentTabMy;
            default:
                return fragmentTabMain;
        }
    }
}
