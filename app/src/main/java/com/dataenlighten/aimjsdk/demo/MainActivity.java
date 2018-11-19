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
    public static final String VIN = "WBASZ6109ED551752";
    private Button vinParse;
    private Button gotoDraw;
    private Button gotoSpeech;
    public static String prefix = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vinParse = findViewById(R.id.vinParse);
        gotoDraw = findViewById(R.id.gotoDraw);
        gotoSpeech = findViewById(R.id.gotoSpeech);
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
        /*MJSdkService.getInstance().VINQuery(VIN,2, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                Log.e(TAG, "VINQuery responseBody-" + responseBody);
                parseVINInfo(responseBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gotoDraw.setEnabled(true);
                        gotoSpeech.setEnabled(true);
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG, "VINQuery Exception", e);
            }
        });*/
    }


    public void gotoDraw(View view) {
        startActivity(new Intent(MainActivity.this, DrawActivity.class));
    }

    public void gotoSpeech(View view) {
        startActivity(new Intent(MainActivity.this, SpeechActivity.class));
    }

    /*//解析VINResponse
    public void parseVINInfo(String json) {
        try {
            JSONObject job = new JSONObject(json);
            String rcode = job.optString("code");

            if (rcode.equals("0000")) {
                CarInfo carInfo = new CarInfo();

                prefix = job.optString("brandClass");
                JSONArray jay = job.optJSONArray("vehicleList");
                for (int i = 0; i < jay.length(); i++) {
                    JSONObject obj = jay.optJSONObject(i);
                    JSONObject sjob = obj.optJSONObject("vehicle");
                    String body = sjob.optString("body");
                    if (TextUtils.isEmpty(body) || body.equals("") || body.equals("null")) {
                        body = "三厢4门";
                    }
                    carInfo.setBrand(sjob.optString("gyroBrand"));
                    carInfo.setBody(body);
                    carInfo.setVinCode(VIN);
                    carInfo.setOptionCode(sjob.optString("optionCode"));
                    carInfo.setCountry(sjob.optString("carCountry"));
                    carInfo.setMaker(sjob.optString("maker"));
                    carInfo.setVehicleChn(sjob.optString("vehicleChn"));
                    carInfo.setCarGrade(sjob.optString("carGrade"));
                    carInfo.setMinPrice(sjob.optString("minPrice"));
                    carInfo.setMaxPrice(sjob.optString("maxPrice"));
                    DrawManager.getInstance().init(carInfo);

                    JSONArray jsonArray = obj.optJSONArray("partNameListAsList");
                    List<String> names = new ArrayList<>();
                    for (int index = 0; index < jsonArray.length(); index++) {
                        String str = jsonArray.optString(index);
                        names.add(str);
                    }
                    String[] nameArr = new String[names.size()];
                    names.toArray(nameArr);
                    testThinkedKey("bxg", nameArr);
                }

            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    private void testThinkedKey(String input, String[] names) {
        MJInitialService.getInstance().queryThinkedKeys(input, names, new QueryThinKedKeysCallback(){

            @Override
            public void onCallback(List<String> list) {
            }

            @Override
            public void onException(Exception e) {
            }
        });
    }

}
