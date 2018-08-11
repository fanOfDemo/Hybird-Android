package com.gtfund.gtapp.common.model;

import java.io.Serializable;

/**
 * 基础传输Model
 *
 * implements Serializable 是为了网络传输字节流转换
 * Created by enzexue on 2018/8/7.
 */

@SuppressWarnings("serial")
public class CochinModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String api;
    private Object params;

    public CochinModel() {}

    public CochinModel(String api, Object params) {
        this.api = api;
        this.params = params;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

}
