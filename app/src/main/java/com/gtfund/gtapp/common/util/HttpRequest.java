package com.gtfund.gtapp.common.util;

/**
 * HTTP请求工具类
 *
 * Created by enzexue on 2018/8/8.
 */
public class HttpRequest {

    private static final String TAG = "HttpRequest";

    /**基础URL，这里服务器设置可切换*/
    public static final String URL_BASE = SettingUtil.getCurrentServerAddress();

    public static final String HTTP_CODE_SUCCESS = "200";
    public static final String HTTP_CODE_FORBIDDEN = "403";
    public static final String HTTP_CODE_NOT_FIND = "404";
}
