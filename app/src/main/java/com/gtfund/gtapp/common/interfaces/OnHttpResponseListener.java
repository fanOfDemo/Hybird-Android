package com.gtfund.gtapp.common.interfaces;

import com.alibaba.fastjson.JSONObject;

import java.util.Hashtable;

/**
 * 网络请求回调接口
 *
 * Created by enzexue on 2018/8/8.
 */

public interface OnHttpResponseListener {
    /**
     * @param requestCode 请求码，自定义，在发起请求的类中可以用requestCode来区分各个请求
     * @param result 服务器返回的结果
     * @param e 异常
     */
    void onHttpResponse(int requestCode, Hashtable<String, String> result, Exception e);
}
