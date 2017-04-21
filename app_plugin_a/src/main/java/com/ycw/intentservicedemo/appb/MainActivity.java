package com.ycw.intentservicedemo.appb;


import android.os.Bundle;
import android.util.Log;

import com.ycw.intentservicedemo.plugina.PluginActivity;

public class MainActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MainActivity","我是pluginactivity");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("MainActivity","onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MainActivity","onResume()");
    }
}
