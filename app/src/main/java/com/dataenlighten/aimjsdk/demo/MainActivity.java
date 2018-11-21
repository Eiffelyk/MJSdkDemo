package com.dataenlighten.aimjsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mj.sdk.bean.CarInfo;
import com.mj.sdk.callback.OnSdkInitLisener;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.exception.LicenseNotFoundException;
import com.mj.sdk.service.MJSdkService;
import com.mj.sdk.view.DrawManager;
import com.mj.thinkkey.MJInitialService;
import com.mj.thinkkey.QueryThinKedKeysCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private Button vinParse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vinParse = findViewById(R.id.vinParse);
    }

    public void sdkInit(View view) {
        try {
            MJSdkService.getInstance().init(getApplication(), new OnSdkInitLisener() {
                @Override
                public void onInitSuccess() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            vinParse.setEnabled(true);
                            Toast.makeText(MainActivity.this, "初始化成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.e(TAG, "onInitSuccess");
                }

                @Override
                public void onInitFailure(Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "初始化失败",Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.e(TAG, "onInitFailure", e);
                }
            });
        } catch (LicenseNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void vinParse(View view) {
        startActivity(new Intent(this,VinQueryActivity.class));
    }
}
