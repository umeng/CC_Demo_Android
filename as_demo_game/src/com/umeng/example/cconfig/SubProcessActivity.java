package com.umeng.example.cconfig;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.cconfig.UMRemoteConfig;
import com.umeng.example.R;


public class SubProcessActivity extends Activity {
    private EditText mEditText;
    private Button mButton1;
    private Button mButton2;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_config);
        mButton1 = (Button) findViewById(R.id.normal_button);
        mButton2 = (Button) findViewById(R.id.siman_button);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String value = UMRemoteConfig.getInstance().getConfigValue("some_text");
                String key = mEditText.getText().toString();
                if (TextUtils.isEmpty(key)){
                    String value = UMRemoteConfig.getInstance().getConfigValue("account_color");
                    mTextView.setText(value);
                }else {
                    String value = UMRemoteConfig.getInstance().getConfigValue(key);
                    mTextView.setText(value);
                }
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = UMRemoteConfig.getInstance().getConfigValue("siman_test");
                mTextView.setText(value);
            }
        });
        mTextView = (TextView) findViewById(R.id.valueText);

    }
}
