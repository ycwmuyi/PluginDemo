package com.ycw.intentservicedemo.plugina;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by ycw on 2017/4/19.
 */

public class LifeCircleController{

    Activity mProxy;
    PluginActivity mPlugin;
    Resources mResources;
    Resources.Theme mTheme;
    PluginApk mPluginAppk;
    String mPluginClazz;


    public LifeCircleController(Activity mProxy) {
        this.mProxy = mProxy;
    }


    public void onCreate(Bundle bundle){
        //1.获取通过Intent传递进来的插件Activity的类路径和包名
        mPluginClazz = bundle.getString(DLConstants.PLUGIN_CLASS_NAME);
        String packageName = bundle.getString(DLConstants.PACKAGE_NAME);
        //2.从PluginManager取出缓存的PluginApk对象
        mPluginAppk = PluginManager.getInstance().getPluginApk(packageName);

        try {
            //3.加载插件Activity
            mPlugin = (PluginActivity) mPluginAppk.classLoader.loadClass(mPluginClazz).getConstructor(new Class[]{}).newInstance(new Object[]{});
            //4.将ActivityProxy对象注入到插件Activity中
            mPlugin.attach(mProxy,mPluginAppk);
            //设置资源为插件APK的资源
            mResources = mPluginAppk.resources;
            //5.调用插件的onCreate函数
            mPlugin.onCreate(bundle);

        } catch (Exception e) {
            Log.e("LifeCircleController",e.toString());
            e.printStackTrace();
        }

    }




    public ClassLoader getClassLoader(){
        return mPluginAppk.classLoader;
    }

    public AssetManager getAssets(){
        return mPluginAppk.assetManager;
    }


    public Resources getResources(){
        return mResources;
    }

    public Resources.Theme getTheme(){
        return mTheme;
    }



    public PluginActivity getRemoteActivity(){
        return mPlugin;
    }











}
