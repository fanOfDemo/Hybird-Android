package com.gtfund.gtapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.gtfund.gtapp.R;
import com.gtfund.gtapp.common.base.BaseActivity;
import com.gtfund.gtapp.common.interfaces.OnHttpResponseListener;
import com.gtfund.gtapp.common.manager.HttpManager;
import com.gtfund.gtapp.common.model.CochinModel;
import com.gtfund.gtapp.common.util.HttpRequest;
import com.gtfund.gtapp.common.util.JSON;
import com.gtfund.gtapp.common.util.Log;
import com.gtfund.gtapp.model.Authorization;

import java.util.Hashtable;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initEvent();
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
        findView(R.id.button_login).setOnClickListener(this);
        findView(R.id.button_fund_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                authorize();
                break;
            case R.id.button_fund_list:
                getFundList();
                break;
        }
    }

    /**
     * 登陆测试模块
     */
    public void authorize() {
        Authorization auz = new Authorization(
                "20",
                "210105198202204910",
                "",
                "12312312",
                "etrade",
                "test"
        );

        CochinModel cm = new CochinModel("authorize", auz);

        HttpManager.getInstance().postAPI(cm, HttpRequest.URL_BASE, 0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, Hashtable<String, String> result, Exception e) {
                Log.e(TAG, result.toString());
                showShortToast("测试Http请求:结果为\n" + result.toString());

                // 如果验证成功，则重新塞入验证的TOKEN
                if (result.get("code").equals(HttpRequest.HTTP_CODE_SUCCESS)) {
                    JSONObject resultJson = JSON.parseObject(result.get("result"));
                    HttpManager.getInstance().saveAuthorizationToken(resultJson.getString("jwt"));
                }
            }
        });
    }

    /**
     * 拉取基金列表测试模块
     */
    public void getFundList() {
        Hashtable<String, String> params = new Hashtable<>();
        params.put("fundCode", "003515");

        CochinModel cm = new CochinModel("trade.redemption.prepare", params);

        HttpManager.getInstance().postAPI(cm, HttpRequest.URL_BASE, 0, new OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, Hashtable<String, String> result, Exception e) {
                Log.e(TAG, result.toString());
                showShortToast("测试Http请求:结果为\n" + result.toString());
            }
        });
    }
}
