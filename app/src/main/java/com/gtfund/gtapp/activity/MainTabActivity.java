package com.gtfund.gtapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.gtfund.gtapp.R;
import com.gtfund.gtapp.common.base.BaseActivity;
import com.gtfund.gtapp.common.base.BaseBottomTabActivity;
import com.gtfund.gtapp.fragment.XWalkCrossTabFragment;

import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enzexue on 2018/8/8.
 */
public class MainTabActivity extends BaseActivity {

    private static final String TAG = "MainTabActivity";

    private static final String[] TAB_NAMES = {"主页", "基金", "资产"};

    private XWalkCrossTabFragment fragmentTabMain;
    private XWalkCrossTabFragment fragmentTabFunds;
    private XWalkCrossTabFragment fragmentTabMy;

    List<XWalkCrossTabFragment> fragments = new ArrayList<>();
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_activity);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        getWindow().setBackgroundDrawable(null);
        //功能归类分区方法，必须调用<<<<<<<<<<
//        initView();
//        initData();
//        initEvent();
        //功能归类分区方法，必须调用>>>>>>>>>>
        viewPager = findViewById(R.id.frame_main_tab_container);
        fragmentTabMain = XWalkCrossTabFragment.createInstance("https://m.gtfund.com/vue/index.html#/");
        fragmentTabFunds = XWalkCrossTabFragment.createInstance("https://m.gtfund.com/vue/index.html#/jijin");
        fragmentTabMy = XWalkCrossTabFragment.createInstance("https://m.gtfund.com/vue/index.html#/user");
        fragments.add(fragmentTabMain);
        fragments.add(fragmentTabFunds);
        fragments.add(fragmentTabMy);

        viewPager.setOffscreenPageLimit(3);
        TabLayout tableLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return TAB_NAMES[position];
            }
        });
        tableLayout.setupWithViewPager(viewPager);


    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }


//
//
//    @Override
//    public Activity getActivity() {
//        return this;
//    }
//
//    @Override
//    public void initView() {// 必须调用
//        super.initView();
//        exitAnim = R.anim.bottom_push_out;
//
//
//    }
//
//    @Override
//    public void initData() {// 必须调用
//        super.initData();
//    }
//
//    @Override
//    public void initEvent() {// 必须调用
//        super.initEvent();
//    }
//
//    @Override
//    protected void selectTab(int position) {
//        tvBaseTitle.setText(TAB_NAMES[position]);
//    }
//
//    @Override
//    protected int[] getTabClickIds() {
//        return new int[]{R.id.tab_main, R.id.tab_funds, R.id.tab_my};
//    }
//
//    @Override
//    protected int[][] getTabSelectIds() {
//        return new int[][]{
//                new int[]{R.id.tab_main, R.id.tab_funds, R.id.tab_my}
//        };
//    }
//
//    @Override
//    public int getFragmentContainerResId() {
//        return R.id.frame_main_tab_container;
//    }
//
//    @Override
//    protected Fragment getFragment(int position) {
//        switch (position) {
//            case 0:
//                return fragmentTabMain;
//            case 1:
//                return fragmentTabFunds;
//            case 2:
//                return fragmentTabMy;
//            default:
//                return fragmentTabMain;
//        }
//    }

//    @Override
//    public void selectFragment(int position) {
//        super.selectFragment(position);
//        if (fragmentTabMain.getxWebView() == null||
//                fragmentTabFunds.getxWebView()==null||
//                fragmentTabMy.getxWebView()==null
//                ) return;
//        switch (position) {
//            case 0:
//                fragmentTabMain.getxWebView().setVisibility(View.VISIBLE);
//                fragmentTabFunds.getxWebView().setVisibility(View.GONE);
//                fragmentTabMy.getxWebView().setVisibility(View.GONE);
//                break;
//            case 1:
//                fragmentTabMain.getxWebView().setVisibility(View.GONE);
//                fragmentTabFunds.getxWebView().setVisibility(View.VISIBLE);
//                fragmentTabMy.getxWebView().setVisibility(View.GONE);
//                break;
//            case 2:
//                fragmentTabMain.getxWebView().setVisibility(View.GONE);
//                fragmentTabFunds.getxWebView().setVisibility(View.GONE);
//                fragmentTabMy.getxWebView().setVisibility(View.VISIBLE);
//                break;
//            default:
//                fragmentTabMain.getxWebView().setVisibility(View.VISIBLE);
//                fragmentTabFunds.getxWebView().setVisibility(View.GONE);
//                fragmentTabMy.getxWebView().setVisibility(View.GONE);
//                break;
//        }
//    }
}
