package com.myapp;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class FileUtil {
    private static final String TAG = "FileUtil";
    public static void installPluginApk(Context context, String pluginPath){
        File pluginFile = new File(context.getDir("jsbundle", Context.MODE_PRIVATE), pluginPath);
        if(pluginFile.exists()){
            LogUtils.i(TAG, "bundle file already exist.");
            return;
        }

        BufferedInputStream bis;
        OutputStream dexWriter;

        final int BUF_SIZE = 8 * 1024;
        try {
            bis = new BufferedInputStream(context.getAssets().open(pluginFile.getName()));
            dexWriter = new BufferedOutputStream(Files.newOutputStream(pluginFile.toPath()));
            byte[] buf = new byte[BUF_SIZE];
            int len;
            while((len = bis.read(buf, 0, BUF_SIZE)) > 0) {
                dexWriter.write(buf, 0, len);
            }
            dexWriter.close();
            bis.close();

        } catch (IOException e){
            LogUtils.e(TAG, e.getMessage());
        }
    }
}
