package com.ycw.demo.plugindemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ycw.intentservicedemo.plugina.DLConstants;
import com.ycw.intentservicedemo.plugina.PluginManager;

import java.io.File;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    PluginManager pluginMar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.init(getApplicationContext());
        pluginMar = PluginManager.getInstance();
        String pluginApkPath = Environment.getExternalStorageDirectory() + File.separator + "plugins" + File.separator + "plugin.apk";
        String pluginClass = "com.ycw.intentservicedemo.appb.MainActivity";
        String pluginPackage = "com.ycw.intentservicedemo.appb";

        PluginManager.getInstance().loadApk(pluginApkPath);
        final Intent intent = new Intent();
        intent.putExtra(DLConstants.PLUGIN_CLASS_NAME,pluginClass);
        intent.putExtra(DLConstants.PACKAGE_NAME,pluginPackage);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pluginMar.startActivity(intent);
            }
        });

    }

    private DexClassLoader createDexClassLoader(Context mContext,String apkPath){
        File dexOutputDir = mContext.getDir("dex",Context.MODE_PRIVATE);
        DexClassLoader loader = new DexClassLoader(apkPath,dexOutputDir.getAbsolutePath(),null,mContext.getClassLoader());
        return loader;
    }





}
