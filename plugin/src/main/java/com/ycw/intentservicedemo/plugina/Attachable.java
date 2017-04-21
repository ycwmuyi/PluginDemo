package com.ycw.intentservicedemo.plugina;

import android.app.Activity;

/**
 * Created by ycw on 2017/4/5.
 */

public interface Attachable {


    abstract void attach(Activity proxyActivity, PluginApk pluginApk);

}
