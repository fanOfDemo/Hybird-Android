package com.gtfund.gtapp.activity;

import android.os.Bundle;

import com.gtfund.gtapp.R;

import org.xwalk.core.XWalkActivity;
import org.xwalk.core.XWalkView;

/**
 * Created by enzexue on 2018/8/10.
 */
public class XWalkViewActivity extends XWalkActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.x_walk_view_activity);
    }

    @Override
    protected void onXWalkReady() {
        XWalkView mXWalkView = findViewById(R.id.x_walk_view);
        // XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
        mXWalkView.loadUrl("https://blog.csdn.net/nmyangmo/article/details/73105712");
    }
}
