package com.umeng.example.analytics;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.example.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AnalyticsHome extends Activity {
    private Context mContext;
    private final String mPageName = "AnalyticsHome";
    private String zh128 = "start_中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国_end";
    private String str128
        =
        "start_123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890_end";
    private String zh256
        =
        "start_中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国中国_end";
    private String str256
        =
        "start_1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890_end";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.util.Log.i("mob", "----ana---onCreate----------");
        mContext = this;
        setTitle("友盟统计(普通场景)");
        setContentView(R.layout.umeng_example_analytics);
        //UMConfigure.init(mContext, "505c04a2527015104b00007b", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(mContext, EScenarioType.E_UM_NORMAL);
        //UMGameAgent.init(mContext);
        //UMGameAgent.setPlayerLevel(1);

//        MobclickAgent.registerSuperProperty(this, "umsp_1", "aaa");
//        MobclickAgent.registerSuperProperty(this, "umsp_2", "bbb");
        JSONObject pre = new JSONObject();
        try {
            pre.put("PreProperties-key1", "PreProperties-value1");
            pre.put("PreProperties-key2", "PreProperties-value2");
            pre.put("PreProperties-key3", "PreProperties-value3");
            pre.put("PreProperties-key4", "PreProperties-value4");
            pre.put("PreProperties-key5", "PreProperties-value5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MobclickAgent.registerPreProperties(mContext, pre);

        UMConfigure.setLogEnabled(true);
        MobclickAgent.setCatchUncaughtExceptions(true);
        MobclickAgent.setSecret(mContext, "111");
        MobclickAgent.setCheckDevice(true);
        MobclickAgent.setLocation(11, 11);
        MobclickAgent.setLatencyWindow(10);
        UMConfigure.setEncryptEnabled(true);


    }





    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(mContext);
        android.util.Log.i("mob", "--Home-onResume-----");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(mContext);
        android.util.Log.i("mob", "--Home-onPause-----");
    }

    /**
     * android:onClick="onButtonClick"
     *
     * @param view
     */
    public void onButtonClick(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.umeng_example_analytics_event:
                android.util.Log.i("mob", "-------umeng_example_analytics_event--------");
                MobclickAgent.onEvent(mContext, "click");
                MobclickAgent.onEvent(mContext, "click", "clickLabel");

                break;
            case R.id.umeng_example_analytics_ekv:
                android.util.Log.i("mob", "-------umeng_example_analytics_ekv--------");
                Map<String, Object> ekvs = new HashMap<String, Object>();
                ekvs.put("type", "popular");
                ekvs.put("artist", "JJLin");
                ekvs.put("type", "popular");
                ekvs.put("artist", "JJLin");
                MobclickAgent.onEventObject(mContext, "music", ekvs);
                Log.i("mob", "----map_ekv---");
                break;
            


            case R.id.umeng_example_analytics_signinDefalut:
                MobclickAgent.onProfileSignIn("example_id");
                break;
            case R.id.umeng_example_analytics_signin:
                MobclickAgent.onProfileSignIn("example_id", "uid");
                break;
            case R.id.umeng_example_analytics_signoff:
                MobclickAgent.onProfileSignOff();
                break;
            


            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            hook();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // /对于好多应用，会在程序中杀死 进程，这样会导致我们统计不到此时Activity结束的信息，
    // /对于这种情况需要调用 'MobclickAgent.onKillProcess( Context )'
    // /方法，保存一些页面调用的数据。正常的应用是不需要调用此方法的。
    private void hook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("退出应用", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                MobclickAgent.onKillProcess(mContext);

                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
        builder.setNeutralButton("后退一下", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        builder.setNegativeButton("点错了", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();
    }
}