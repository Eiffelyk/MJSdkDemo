package com.dataenlighten.aimjsdk.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;

public class AnalysisActivity extends AppCompatActivity {
    public static final String TAG = "MJSDKDemo";

    private EditText actAnalysisPartVin;
    private EditText actAnalysisPartKey;
    private Button actAnalysisPartBtn;
    private TextView actAnalysisPartShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_part);
        actAnalysisPartVin = findViewById(R.id.act_analysis_part_vin);
        actAnalysisPartKey = findViewById(R.id.act_analysis_part_key);
        actAnalysisPartBtn = findViewById(R.id.act_analysis_part_btn);
        actAnalysisPartShow = findViewById(R.id.act_analysis_part_show);
        actAnalysisPartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analysis(actAnalysisPartVin.getText().toString(), actAnalysisPartKey.getText().toString());
            }
        });
    }

    private void analysis(String vinCode, String key) {
        if (TextUtils.isEmpty(key)) {
            Toast.makeText(AnalysisActivity.this, "搜索关键字不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        MJSdkService.getInstance().queryPartAnalysis(vinCode, key, new QueryCallBack() {
            @Override
            public void onSuccess(final String responseBody) {
                Log.d(TAG, "queryPartAnalysis onSuccess: " + responseBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actAnalysisPartShow.setText(responseBody);
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                Toast.makeText(AnalysisActivity.this, "queryPartAnalysis failed " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
