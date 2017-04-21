package com.ycw.intentservicedemo.plugina;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by ycw on 2017/4/19.
 */

public class ActivityProxy extends Activity {

    protected DLPlugin mRemoteActivity;
    LifeCircleController mPluginController = new LifeCircleController(this);


    public void attach(DLPlugin remoteActivity, PluginManager pluginManager) {
        this.mRemoteActivity = remoteActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //调用mPluginController的onCreate,实际上是启动插件APK的Activity
        mPluginController.onCreate(getIntent().getExtras());
    }


    @Override
    public Resources getResources() {
        //资源在加载apk时就已经构建
        Resources resources = mPluginController.getResources();
        return resources == null ? super.getResources():resources;
    }


    @Override
    public AssetManager getAssets() {
        return mPluginController.getAssets();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPluginController.getRemoteActivity().onStart();
    }

}
