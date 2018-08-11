package com.gtfund.gtapp.common.interfaces;

import android.app.Activity;

/**
 * Fragment的逻辑接口
 * Created by enzexue on 2018/8/7.
 */

public interface FragmentPresenter extends Presenter {

    /**
     * 该Fragment在Activity添加的所有Fragment中的位置
     */
    static final String ARGUMENT_POSITION = "ARGUMENT_POSITION";
    static final String ARGUMENT_ID = "ARGUMENT_ID";
    static final String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";

    static final int RESULT_OK = Activity.RESULT_OK;
    static final int RESULT_CANCELED = Activity.RESULT_CANCELED;
}
