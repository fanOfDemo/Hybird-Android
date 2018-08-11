package com.gtfund.gtapp.manager;

import android.content.Context;

import com.gtfund.gtapp.application.GTApplication;

/**
 * 登陆验证模块
 * Created by enzexue on 2018/8/8.
 */
public class ValidateManager{
    private final String TAG = "ValidateManager";

    private Context context;
    private ValidateManager(Context context) {
        this.context = context;
    }

    private static ValidateManager instance;
    public static ValidateManager getInstance() {
        if (instance == null) {
            synchronized (ValidateManager.class) {
                if (instance == null) {
                    instance = new ValidateManager(GTApplication.getInstance());
                }
            }
        }
        return instance;
    }
}
