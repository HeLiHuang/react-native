package com.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import java.io.File;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {
    private static final String TAG = "RNBaseActivity";
    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    /**
     * @return 返回的是bundle文件的名称
     */
    private String getBundleFileName(){
        return "index.android.bundle";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileUtil.installPluginApk(this, getBundleFileName());

        // Packages that cannot be autolinked yet can be added manually here, for example:
        // packages.add(MyReactNativePackage())
        // Remember to include them in `settings.gradle` and `app/build.gradle` too.
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setCurrentActivity(this)
                //.setBundleAssetName("index.android.bundle") // load assets bundle file
                .setJSMainModulePath("index") // setJSMainModulePath就是调试时候第一页面地址
                .setJSBundleFile(new File(getDir("jsbundle", MODE_PRIVATE), getBundleFileName()).getAbsolutePath()) // load local bundle
                .addPackage(new MainReactPackage())
                .addPackage(new NativeLocalStoragePackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();


        mReactRootView = new ReactRootView(this);

        // The string here (e.g. "MyReactNativeApp") has to match
        // the string in AppRegistry.registerComponent() in index.js
        // 这个名称具有唯一性
        mReactRootView.startReactApplication(mReactInstanceManager, "myapp", null);

        setContentView(mReactRootView);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mReactInstanceManager.onHostPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReactInstanceManager.onHostResume(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReactInstanceManager.onHostDestroy(this);
        mReactRootView.unmountReactApplication();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mReactInstanceManager.onBackPressed();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }
}
