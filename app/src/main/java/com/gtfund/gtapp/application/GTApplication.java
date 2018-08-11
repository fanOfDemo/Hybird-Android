package com.gtfund.gtapp.application;

import com.gtfund.gtapp.common.base.BaseApplication;

/**
 * Created by enzexue on 2018/8/8.
 */
public class GTApplication extends BaseApplication {
    private static final String TAG = "GTApplication";

    private static GTApplication context;

    public static GTApplication getInstance() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
