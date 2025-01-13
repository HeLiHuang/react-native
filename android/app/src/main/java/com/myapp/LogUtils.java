package com.myapp;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author GW00262242
 */
public class LogUtils {

    public static void i(String tag, String msg) {
        Log.i(tag, msg(msg));
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg(msg));
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg(msg));
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg(msg));
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg(msg));
    }

    private static String msg(String msg){
        if(TextUtils.isEmpty(msg)){
            msg = "log msg == null or blank character";
        }

        return msg;
    }
}
