package com.umeng.example;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import com.umeng.example.analytics.AnalyticsHome;
import com.umeng.example.cconfig.CconfigHome;


public class MainActivity extends Activity {

    private static final String SP_FILE_NAME = "permissions_require_flag";

    private Context mContext;

    private boolean getRequireFlag () {
        SharedPreferences sp = mContext.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        String flag = sp.getString("require_flag", "false");
        if (flag.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

    private void setRequiredFlag(boolean flag) {
        SharedPreferences sp = mContext.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (flag) {
            editor.putString("require_flag", "true");
        } else {
            editor.putString("require_flag", "false");
        }
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if (!getRequireFlag()) {
            if (Build.VERSION.SDK_INT >= 23) {
                String[] mPermissionList = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE};
                this.requestPermissions(mPermissionList, 123);
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void onClick(View v) {
        int id = v.getId();
        Intent in = null;
        if (id == R.id.normal) {
            in = new Intent(this, AnalyticsHome.class);
            startActivity(in);
        }else if (id == R.id.cconfig) {
            in = new Intent(this, CconfigHome.class);
            startActivity(in);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        android.util.Log.i("mob", "--Main-onResume-----");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        android.util.Log.i("mob", "--Main-onPause-----");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        setRequiredFlag(true);
    }
}
