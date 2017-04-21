package com.ycw.intentservicedemo.plugina;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Created by ycw on 2017/4/5.
 */
public class PluginActivity extends Activity{



    protected Activity mProxyActivity;//activityProxy对象

    private Resources mResource; //插件Activity的资源

    PluginApk mPluginApk;

    public void attach(Activity proxyActivity, PluginApk pluginApk) {
        mProxyActivity = proxyActivity;
        mPluginApk = pluginApk;
        mResource = pluginApk.resources;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    }

    @Override
    protected void onStart() {
    }

    @Override
    public void setContentView(int layoutResID) {
        mProxyActivity.setContentView(layoutResID);
    }

    @Override
    public View findViewById(int id) {
        return mProxyActivity.findViewById(id);
    }


    @Override
    public Resources getResources() {
        return mResource;
    }


    @Override
    public Window getWindow() {
        return mProxyActivity.getWindow();
    }


    @Override
    public Intent getIntent() {
            return mProxyActivity.getIntent();
    }


}
