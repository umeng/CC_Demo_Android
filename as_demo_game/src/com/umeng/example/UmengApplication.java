package com.umeng.example;

import android.app.Application;
import android.util.Log;

import com.umeng.cconfig.RemoteConfigSettings;
import com.umeng.cconfig.UMRemoteConfig;
import com.umeng.cconfig.listener.OnConfigStatusChangedListener;
import com.umeng.commonsdk.UMConfigure;



public class UmengApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 打开调试log
        UMConfigure.setLogEnabled(true);

        //云配置自动更新代码逻辑
        //UMRemoteConfig.getInstance().setConfigSettings(new RemoteConfigSettings.Builder().setAutoUpdateModeEnabled(true).build());
        //UMRemoteConfig.getInstance().setDefaults(R.xml.cloud_config_parms);


        //云配置手动更新代码逻辑
        UMRemoteConfig.getInstance().setConfigSettings(new RemoteConfigSettings.Builder().setAutoUpdateModeEnabled(false).build());
        UMRemoteConfig.getInstance().setDefaults(R.xml.cloud_config_parms);
        UMRemoteConfig.getInstance().setOnNewConfigfecthed(new OnConfigStatusChangedListener() {
            @Override
            public void onFetchComplete() {
                UMRemoteConfig.getInstance().activeFetchConfig();
            }

            @Override
            public void onActiveComplete() {
                String value = UMRemoteConfig.getInstance().getConfigValue("test_args");
                Log.i("UMENG_CC", "value = " + value);
            }
        });


        UMConfigure.init(this, "5ef2cdf5978eea088379c950", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);

        // 本地默认值获取例子。
        String defaultParamValue = UMRemoteConfig.getInstance().getConfigValue("some_text");
        Log.i("UMENG_CC", "default value of key some_text is: " + defaultParamValue);
    }
}
