package com.gtfund.gtapp.common.ui.CustomCrosswalk;

import com.gtfund.gtapp.common.util.Log;

import org.xwalk.core.XWalkJavascriptResult;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

/**
 * Created by enzexue on 2018/8/13.
 */
public class CustomCrosswalkUIClient extends XWalkUIClient {
    @SuppressWarnings("all")
    private static final String TAG = "CustomCrosswalkUIClient";

    public CustomCrosswalkUIClient(XWalkView view) {
        super(view);
    }

    // To prevent alerts from the websites
    @Override
    public boolean onJavascriptModalDialog(XWalkView view, XWalkUIClient.JavascriptMessageType type, String url, String message,
                                           String defaultValue, XWalkJavascriptResult result) {
        result.cancel();
        return true;
    }

    // To see console messages in the logcat
    @Override
    public boolean onConsoleMessage(XWalkView view, String message, int lineNumber, String sourceId,
                                    ConsoleMessageType messageType) {

        Log.i(TAG, message + " " + lineNumber + " " + sourceId + " " + messageType.name());
        return super.onConsoleMessage(view, message, lineNumber, sourceId, messageType);
    }

    // Calls if website starts to load
    @Override
    public void onPageLoadStarted(XWalkView view, String url) {
        super.onPageLoadStarted(view, url);

        Log.i(TAG, url + " is loading");
    }

    // Calls if website finishs to load
    @Override
    public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
        super.onPageLoadStopped(view, url, status);

        Log.i(TAG, url + " is fully loaded");
    }
}
