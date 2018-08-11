package com.gtfund.gtapp.common.manager;

/**
 * Created by enzexue on 2018/8/8.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.gtfund.gtapp.common.model.CochinModel;
import com.gtfund.gtapp.common.util.JSON;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import com.gtfund.gtapp.common.util.SSLUtil;
import com.gtfund.gtapp.common.base.BaseApplication;
import com.gtfund.gtapp.common.util.Log;
import com.gtfund.gtapp.common.interfaces.OnHttpResponseListener;
import com.gtfund.gtapp.common.util.StringUtil;
import com.gtfund.gtapp.common.model.Parameter;

/**
 * HTTP请求管理类
 *
 * Created by enzexue on 2018/8/8.
 *
 * @use HttpManager.getInstance().get(...)或HttpManager.getInstance().post(...)  > 在回调方法onHttpRequestSuccess和onHttpRequestError处理HTTP请求结果
 */
public class HttpManager {
    private static final String TAG = "HttpManager";

    private static final MediaType CONTENT_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String KEY_AUTHORIZATION = "Authorization";
    public static final String KEY_COOKIE = "cookie";
    public static final String KEY_X_CLIENT_ID = "X-Client-Id";
    public static final String KEY_X_CHANNEL = "X-Channel";

    public static final String VALUE_X_CHANNEL = "android";
    public static final String VALUE_X_CLIENT_ID = UUID.randomUUID().toString().replaceAll("-", "");

    private Context context;
    private SSLSocketFactory socketFactory;// 单例
    private HttpManager(Context context) {
        this.context = context;

        try {
            //TODO 初始化自签名，demo.cer（这里demo.cer是空文件）为服务器生成的自签名证书，存放于assets目录下，如果不需要自签名可删除
            socketFactory = SSLUtil.getSSLSocketFactory(context.getAssets().open("demo.cer"));
        } catch (Exception e) {
            Log.e(TAG, "HttpManager  try {" +
                    "  socketFactory = SSLUtil.getSSLSocketFactory(context.getAssets().open(\"demo.cer\"));\n" +
                    "\t\t} catch (Exception e) {\n" + e.getMessage());
        }
    }

    private static HttpManager instance; // 单例
    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager(BaseApplication.getInstance());
                }
            }
        }
        return instance;
    }

    /**
     * POSTAPI请求
     * @param cm
     * @param baseURL 接口baseURL
     * @param requestCode
     *            请求码，类似onActivityResult中请求码，当同一activity中以实现接口方式发起多个网络请求时，请求结束后都会回调
     *            在发起请求的类中可以用requestCode来区分各个请求
     * @param listener
     */
    public void postAPI(final CochinModel cm, final String baseURL,
                     final int requestCode, final OnHttpResponseListener listener) {

        new AsyncTask<Void, Void, Exception>() {

            Hashtable<String, String> result;
            @Override
            protected Exception doInBackground(Void... params) {
                OkHttpClient client = getHttpClient(baseURL);
                if (client == null) {
                    return new Exception(TAG + ".post  AsyncTask.doInBackground  client == null >> return;");
                }

                RequestBody requestBody = RequestBody.create(CONTENT_TYPE_JSON, JSON.toJSONString(cm));

                Log.i(TAG, "请求参数：" + JSON.toJSONString(cm));

                try {
                    result = getResponseJson(client, new Request.Builder()
                            .addHeader(KEY_X_CHANNEL, VALUE_X_CHANNEL)
                            .addHeader(KEY_X_CLIENT_ID, VALUE_X_CLIENT_ID)
                            .addHeader(KEY_AUTHORIZATION, getAuthorizationToken())
                            .url(StringUtil.getNoBlankString(baseURL))
                            .post(requestBody)
                            .build());
                } catch (Exception e) {
                    Log.e(TAG, "post  AsyncTask.doInBackground  try {  result = getResponseJson(..." +
                            "} catch (Exception e) {\n" + e.getMessage());
                    return e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Exception exception) {
                super.onPostExecute(exception);
                listener.onHttpResponse(requestCode, result, exception);
            }

        }.execute();
    }

    /**
     * GET请求
     * @param paramList 请求参数列表，（可以一个键对应多个值）
     * @param url 接口url
     * @param requestCode
     *            请求码，类似onActivityResult中请求码，当同一activity中以实现接口方式发起多个网络请求时，请求结束后都会回调
     *            在发起请求的类中可以用requestCode来区分各个请求
     * @param listener
     */
    public void get(final List<Parameter> paramList, final String url,
                    final int requestCode, final OnHttpResponseListener listener) {

        new AsyncTask<Void, Void, Exception>() {

            Hashtable<String, String> result;
            @Override
            protected Exception doInBackground(Void... params) {
                OkHttpClient client = getHttpClient(url);
                if (client == null) {
                    return new Exception(TAG + ".get  AsyncTask.doInBackground  client == null >> return;");
                }

                StringBuffer sb = new StringBuffer();
                sb.append(StringUtil.getNoBlankString(url));
                if (paramList != null) {
                    Parameter parameter;
                    for (int i = 0; i < paramList.size(); i++) {
                        parameter = paramList.get(i);
                        sb.append(i <= 0 ? "?" : "&");
                        sb.append(StringUtil.getTrimedString(parameter.key));
                        sb.append("=");
                        sb.append(StringUtil.getTrimedString(parameter.value));
                    }
                }

                try {
                    result = getResponseJson(client, new Request.Builder()
                            .url(sb.toString())
                            .build());
                } catch (Exception e) {
                    Log.e(TAG, "get  AsyncTask.doInBackground  try {  result = getResponseJson(..." +
                            "} catch (Exception e) {\n" + e.getMessage());
                    return e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Exception exception) {
                super.onPostExecute(exception);
                listener.onHttpResponse(requestCode, result, exception);
            }

        }.execute();

    }


    /**
     * POSTFORM请求
     * @param paramList 请求参数列表，（可以一个键对应多个值）
     * @param url 接口url
     * @param requestCode
     *            请求码，类似onActivityResult中请求码，当同一activity中以实现接口方式发起多个网络请求时，请求结束后都会回调
     *            在发起请求的类中可以用requestCode来区分各个请求
     * @param listener
     */
    public void postForm(final List<Parameter> paramList, final String url,
                     final int requestCode, final OnHttpResponseListener listener) {

        new AsyncTask<Void, Void, Exception>() {

            Hashtable<String, String> result;
            @Override
            protected Exception doInBackground(Void... params) {
                OkHttpClient client = getHttpClient(url);
                if (client == null) {
                    return new Exception(TAG + ".post  AsyncTask.doInBackground  client == null >> return;");
                }

                FormEncodingBuilder fBuilder = new FormEncodingBuilder();
                if (paramList != null) {
                    for (Parameter p : paramList) {
                        fBuilder.add(StringUtil.getTrimedString(p.key), StringUtil.getTrimedString(p.value));
                    }
                }

                try {
                    result = getResponseJson(client, new Request.Builder()
                            .url(StringUtil.getNoBlankString(url))
                            .post(fBuilder.build())
                            .build());
                } catch (Exception e) {
                    Log.e(TAG, "post  AsyncTask.doInBackground  try {  result = getResponseJson(..." +
                            "} catch (Exception e) {\n" + e.getMessage());
                    return e;
                }

                return null;
            }

            @Override
            protected void onPostExecute(Exception exception) {
                super.onPostExecute(exception);
                listener.onHttpResponse(requestCode, result, exception);
            }

        }.execute();
    }

    /**
     * @param url
     * @return
     */
    private OkHttpClient getHttpClient(String url) {
        Log.i(TAG, "getHttpClient  url = " + url);
        if (StringUtil.isNotEmpty(url, true) == false) {
            Log.e(TAG, "getHttpClient  StringUtil.isNotEmpty(url, true) == false >> return null;");
            return null;
        }

        OkHttpClient client = new OkHttpClient();
        client.setCookieHandler(new HttpHead());
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        //添加信任https证书,用于自签名,不需要可删除
        if (url.startsWith(StringUtil.URL_PREFIXs) && socketFactory != null) {
            client.setSslSocketFactory(socketFactory);
        }

        return client;
    }

    /**
     * 获取用于登陆的Token
     */
    public String getAuthorizationToken() {
        return context.getSharedPreferences(KEY_AUTHORIZATION, Context.MODE_PRIVATE).getString(KEY_AUTHORIZATION, "");
    }

    /**
     * 存储用于登陆的Token
     */
    public void saveAuthorizationToken(String value) {
        context.getSharedPreferences(KEY_AUTHORIZATION, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_AUTHORIZATION)
                .putString(KEY_AUTHORIZATION, "Bearer " + value)
                .commit();
    }

    /**
     * @return
     */
    public String getCookie() {
        return context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE).getString(KEY_COOKIE, "");
    }
    /**
     * @param value
     */
    public void saveCookie(String value) {
        context.getSharedPreferences(KEY_COOKIE, Context.MODE_PRIVATE)
                .edit()
                .remove(KEY_COOKIE)
                .putString(KEY_COOKIE, value)
                .commit();
    }

    /**
     * @param client
     * @param request
     * @return
     * @throws Exception
     */
    private Hashtable<String, String> getResponseJson(OkHttpClient client, Request request) throws Exception {
        if (client == null || request == null) {
            Log.e(TAG, "getResponseJson  client == null || request == null >> return null;");
            return null;
        }

        Response response = client.newCall(request).execute();

        String code = Integer.toString(response.code());
        String result = response.body().string();

        Hashtable<String, String> m = new Hashtable<>();
        m.put("code", code);
        m.put("result", result);

        Log.e(TAG, m.toString());

        return m;
    }

    /**
     * http请求头
     */
    public class HttpHead extends CookieHandler {
        public HttpHead() {
        }

        @Override
        public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders) throws IOException {
            String cookie = getCookie();
            Map<String, List<String>> map = new HashMap<String, List<String>>();
            map.putAll(requestHeaders);
            if (!TextUtils.isEmpty(cookie)) {
                List<String> cList = new ArrayList<String>();
                cList.add(cookie);
                map.put("Cookie", cList);
            }
            return map;
        }

        @Override
        public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
            List<String> list = responseHeaders.get("Set-Cookie");
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    String cookie = list.get(i);
                    if (cookie.startsWith("JSESSIONID")) {
                        saveCookie(list.get(i));
                        break;
                    }
                }
            }
        }
    }
}

