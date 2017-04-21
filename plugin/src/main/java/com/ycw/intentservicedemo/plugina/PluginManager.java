package com.ycw.intentservicedemo.plugina;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by ycw on 2017/4/4.
 */

public class PluginManager {


    static class  PluginMarHolder{
        static PluginManager sManager = new PluginManager();
    }

    private static Context mContext;

    Map<String,PluginApk> map = new HashMap<String,PluginApk>();


    public static PluginManager getInstance(){
        return PluginMarHolder.sManager;
    }


    public PluginApk getPluginApk(String packageName){
        return map.get(packageName);
    }

    /**
     * 使用前初始化
     * @param context
     */
    public static void init(Context context){
        mContext = context.getApplicationContext();
    }


    public final void loadApk(String apkPath){
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES|PackageManager.GET_SERVICES);
        if(packageInfo == null|| TextUtils.isEmpty(packageInfo.packageName)){
            return;
        }
        PluginApk pluginApk = map.get(packageInfo.packageName);
        if(pluginApk == null){
            pluginApk = createApk(apkPath,packageInfo);
            if(pluginApk!=null){
                map.put(packageInfo.packageName,pluginApk);
            }else {
                throw new NullPointerException("PluginApk is null !");
            }
        }

    }

    /**
     * 启动插件
     * @param intent
     */
    public void startActivity(Intent intent){
        Intent myIntent = new Intent(mContext,ActivityProxy.class);
        Bundle extra  = intent.getExtras();
        if(extra==null||!extra.containsKey(DLConstants.PLUGIN_CLASS_NAME)||!extra.containsKey(DLConstants.PACKAGE_NAME)){
            throw new  IllegalArgumentException("没有插件的类路径和包名");
        }
        myIntent.putExtras(intent);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(myIntent);
    }



    private PluginApk createApk(String apkPath, PackageInfo packageInfo){
        PluginApk pluginApk = null;
        try{
            //1.创建AssetManager
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath",String.class);
            //2.将APK的目录添加到AssetManager的资源路径下
            addAssetPath.invoke(assetManager,apkPath);
            //3.以AssetManager和设备配置来构建Resource对象
            Resources pluginRes = new Resources(assetManager,mContext.getResources().getDisplayMetrics(),mContext.getResources().getConfiguration());
            //4.创建PluginApk对象，储存插件apk的资源对象
            pluginApk = new PluginApk(createDexClassLoader(mContext,apkPath),pluginRes,packageInfo);

        }catch (Exception e){
            Log.e("PluginManager",e.toString());
        }
        return pluginApk;
    }


    private DexClassLoader createDexClassLoader(Context mContext, String apkPath){
        File dexOutputDir = mContext.getDir("dex",Context.MODE_PRIVATE);
        DexClassLoader loader = new DexClassLoader(apkPath,dexOutputDir.getAbsolutePath(),null,mContext.getClassLoader());
        return loader;
    }




}
