package com.gtfund.gtapp.common.interfaces;

/**
 * Activity和Fragment的公共逻辑接口
 * Created by enzexue on 2018/8/7.
 */

public interface Presenter {

    static final String INTENT_TITLE = "INTENT_TITLE";
    static final String INTENT_ID = "INTENT_ID";
    static final String INTENT_TYPE = "INTENT_TYPE";
    static final String INTENT_PHONE = "INTENT_PHONE";
    static final String INTENT_PASSWORD = "INTENT_PASSWORD";
    static final String INTENT_VERIFY = "INTENT_VERIFY";
    static final String INTENT_USER_ID = "INTENT_USER_ID";
    static final String RESULT_DATA = "RESULT_DATA";
    static final String ACTION_EXIT_APP = "ACTION_EXIT_APP";

    /**
     * UI显示方法(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initView();

    /**
     * Data数据方法(存在数据获取或处理代码，但不存在事件监听代码)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initData();

    /**
     * Event事件方法(只要存在事件监听代码就是)
     *
     * @must Activity-在子类onCreate方法内初始化View(setContentView)后调用；Fragment-在子类onCreateView方法内初始化View后调用
     */
    void initEvent();


    /**
     * 是否存活(已启动且未被销毁)
     */
    boolean isAlive();

    /**
     * 是否在运行
     */
    boolean isRunning();
}
