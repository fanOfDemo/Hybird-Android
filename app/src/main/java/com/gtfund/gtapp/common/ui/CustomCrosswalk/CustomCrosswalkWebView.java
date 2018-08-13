package com.gtfund.gtapp.common.ui.CustomCrosswalk;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;

import com.gtfund.gtapp.BuildConfig;
import com.gtfund.gtapp.common.util.Log;

import org.xwalk.core.XWalkNavigationHistory;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkView;
import org.xwalk.core.internal.XWalkSettingsInternal;
import org.xwalk.core.internal.XWalkViewBridge;

import java.lang.reflect.Method;

/**
 * Created by enzexue on 2018/8/13.
 */
public class CustomCrosswalkWebView extends XWalkView {

    private static final String TAG = "CustomCrosswalkWebView";

    public CustomCrosswalkWebView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init(context);
        }
    }

    public CustomCrosswalkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init(context);
        }
    }

    /**
     * Inits all needed settings for the CustomCrosswalkWebView.
     */
    private void init(Context context) {
        // Enable remote debugging if in debug build
        XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, BuildConfig.DEBUG);

        // Set custom resource client
        setResourceClient(new CustomCrosswalkResourceClient(this));
        // Set custom UI Client
        setUIClient(new CustomCrosswalkUIClient(this));
        // Use this to override the user client
        setUserAgent(this, "My Custom XWalkWebView");
        setAppCachePath(this, context);
    }

    /**
     * Sets custom user agent for crosswalk view.
     *
     * @param view      Crosswalk view
     * @param userAgent Custom user agent
     */
    private void setUserAgent(XWalkView view, String userAgent) {
        try {
            final Method getBridgeMethod = XWalkView.class.getDeclaredMethod("getBridge");
            getBridgeMethod.setAccessible(true);
            final XWalkViewBridge xWalkViewBridge = (XWalkViewBridge) getBridgeMethod.invoke(view);
            final XWalkSettingsInternal xWalkSettingsInternal = xWalkViewBridge.getSettings();
            xWalkSettingsInternal.setUserAgentString(userAgent);
            xWalkSettingsInternal.setAllowUniversalAccessFromFileURLs(true);
        } catch (Exception ignored) {

        }
    }

    private void setAppCachePath(XWalkView view, Context context) {
        try {
            final Method getBridgeMethod = XWalkView.class.getDeclaredMethod("getBridge");
            getBridgeMethod.setAccessible(true);
            final XWalkViewBridge xWalkViewBridge = (XWalkViewBridge) getBridgeMethod.invoke(view);
            final XWalkSettingsInternal xWalkSettingsInternal = xWalkViewBridge.getSettings();
            xWalkSettingsInternal.setAllowUniversalAccessFromFileURLs(true);

            xWalkSettingsInternal.setDomStorageEnabled(true);
            xWalkSettingsInternal.setAppCacheEnabled(true);
            xWalkSettingsInternal.setAllowFileAccess(true);
            xWalkSettingsInternal.setDatabaseEnabled(true);
            xWalkSettingsInternal.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            String cacheDirPath = context.getFilesDir().getAbsolutePath() + "cache/";
            xWalkSettingsInternal.setAppCachePath(cacheDirPath);//设置缓存路径

            Log.e(TAG, cacheDirPath);

        } catch (Exception ignored) {

        }
    }

    /**
     * Goes to previous page.
     */
    public void goBack() {
        if (getNavigationHistory().canGoBack()) {
            getNavigationHistory().navigate(XWalkNavigationHistory.Direction.BACKWARD, 1);
        }
    }

    /**
     * Goes to next page.
     */
    public void goForward() {
        if (getNavigationHistory().canGoForward()) {
            getNavigationHistory().navigate(XWalkNavigationHistory.Direction.FORWARD, 1);
        }
    }

    /**
     * Reloads the website in normal mode.
     */
    public void reload() {
        reload(RELOAD_NORMAL);
    }

    @Override
    public void loadUrl(String url) {
        // Add http:// at first if the url does not contains
        if (url != null && !url.isEmpty() && !url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        super.loadUrl(url);
    }
}
