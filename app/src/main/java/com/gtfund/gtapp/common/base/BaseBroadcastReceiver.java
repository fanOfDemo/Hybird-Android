package com.gtfund.gtapp.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import com.gtfund.gtapp.common.util.Log;
import com.gtfund.gtapp.common.util.StringUtil;

/**
 * 基础广播接收器
 *
 * Created by enzexue on 2018/8/7.
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "BaseBroadcastReceiver";

    /**接收信息监听回调
     */
    public interface OnReceiveListener{
        void onReceive(Context context, Intent intent);
    }
    protected OnReceiveListener onReceiveListener = null;
    /**注册接收信息监听
     * @must 在register后，unregister前调用
     * @param onReceiveListener
     */
    public void setOnReceiveListener(OnReceiveListener onReceiveListener) {
        this.onReceiveListener = onReceiveListener;
    }


    protected Context context = null;
    public BaseBroadcastReceiver(Context context) {
        this.context = context;
    }

    /**接收信息监听回调方法
     */
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive intent = " + intent);
        if (onReceiveListener != null) {
            onReceiveListener.onReceive(context, intent);
        }
    }



    /**
     * 注册广播接收器
     * @use 一般在Activity或Fragment的onCreate中调用
     */
    public abstract BaseBroadcastReceiver register();
    /**
     * 取消注册广播接收器
     * @use 一般在Activity或Fragment的onDestroy中调用
     */
    public abstract void unregister();


    /**注册广播接收器
     * @param context
     * @param receiver
     * @param action
     * @return
     */
    public static BroadcastReceiver register(Context context, @Nullable BroadcastReceiver receiver, String action) {
        return register(context, receiver, new String[] {action});
    }
    /**注册广播接收器
     * @param context
     * @param receiver
     * @param actions
     * @return
     */
    public static BroadcastReceiver register(Context context, @Nullable BroadcastReceiver receiver, String[] actions) {
        return register(context, receiver, actions == null ? null : Arrays.asList(actions));
    }
    /**注册广播接收器
     * @param context
     * @param receiver
     * @param actionList
     * @return
     */
    public static BroadcastReceiver register(Context context, @Nullable BroadcastReceiver receiver, List<String> actionList) {
        IntentFilter filter = new IntentFilter();
        for (String action : actionList) {
            if (StringUtil.isNotEmpty(action, true)) {
                filter.addAction(StringUtil.getTrimedString(action));
            }
        }
        return register(context, receiver, filter);
    }
    /**注册广播接收器
     * @param context
     * @param receiver
     * @param filter
     * @return
     */
    public static BroadcastReceiver register(Context context, @Nullable BroadcastReceiver receiver, IntentFilter filter) {
        Log.i(TAG, "register >>>");
        if (context == null || filter == null) {
            Log.e(TAG, "register  context == null || filter == null >> return;");
            return receiver;
        }

        context.registerReceiver(receiver, filter);

        return receiver;
    }



    /**取消注册广播接收器
     * @param context
     * @param receiver
     * @return
     */
    public static void unregister(Context context, BroadcastReceiver receiver) {
        Log.i(TAG, "unregister >>>");
        if (context == null || receiver == null) {
            Log.e(TAG, "unregister  context == null || receiver == null >> return;");
            return;
        }

        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e(TAG, "unregister  try { context.unregisterReceiver(receiver);" +
                    " } catch (Exception e) { \n" + e.getMessage());
        }
    }

}

