package com.dataenlighten.aimjsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mj.sdk.Exception.LisenceNotFoundException;
import com.mj.sdk.bean.CarInfo;
import com.mj.sdk.callback.OnSdkInitLisener;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.callback.QueryThinKedKeysCallback;
import com.mj.sdk.service.MJSdkService;
import com.mj.sdk.view.DrawManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String VIN = "WBASZ6109ED551752";
 	private Button vinParse;
    private Button gotoDraw;
    private Button gotoSpeech; public static String prefix = "";
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

                        }
                    });
                    Log.e(TAG, "onInitSuccess");
                }

                @Override
                public void onInitFailure(Exception e) {
                    Log.e(TAG, "onInitFailure", e);
                }
            });
        } catch (LisenceNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void vinParse(View view) {
        MJSdkService.getInstance().VINQuery(VIN, new QueryCallBack() {
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
        });
    }


    public void gotoDraw(View view) {
        startActivity(new Intent(MainActivity.this, DrawActivity.class));
    }

    public void gotoSpeech(View view) {
        startActivity(new Intent(MainActivity.this, SpeechActivity.class));
    }

    /**
     * @param json private String mCarBody = null;
     *             private String mVinCode = null;
     *             private String mCarBrand = null;
     *             private String mOptionCode = null;
     *             private String mVehicleId = null;
     */
    //解析VINResponse
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
                    carInfo.setBrandName(sjob.optString("gyroBrand"));
                    carInfo.setBody(body);
                    carInfo.setVinCode(VIN);
                    carInfo.setOptionCode(sjob.optString("optionCode"));
                    carInfo.setCountry(sjob.optString("carCountry"));
                    carInfo.setMaker(sjob.optString("maker"));
                    carInfo.setVehicleChn(sjob.optString("vehicleChn"));
                    carInfo.setVehicleStyle(sjob.optString("carGrade"));
                    carInfo.setMinPrice(sjob.optString("minPrice"));
                    carInfo.setMaxPrice(sjob.optString("maxPrice"));

                    DrawManager.getInstance().init(carInfo);

//                    JSONArray jsonArray = obj.optJSONArray("partNameListAsList");
//                    List<String> names = new ArrayList<>();
//                    for (int index = 0; index < jsonArray.length(); index++) {
//                        String str = jsonArray.optString(index);
//                        names.add(str);
//                    }
//                    String[] nameArr = new String[names.size()];
//                    names.toArray(nameArr);
//                    testThinkedKey("bxg", nameArr);

//                    info.setGyroBrand(sjob.optString("gyroBrand"));
//                    info.setCarCountry(sjob.optString("carCountry"));
//                    info.setCarSerial(sjob.optString("carSerial"));
//                    info.setCarType(sjob.optString("carType"));
//                    info.setMaxPrice(sjob.optString("maxPrice"));
//                    info.setMinPrice(sjob.optString("minPrice"));
//                    info.setModelName(sjob.optString("modelName"));
//                    info.setModelNameEng(sjob.optString("modelNameEng"));
//                    info.setBrandClass(sjob.optString("brandClass"));
//                    info.setMotor(sjob.optString("motor"));
//                    info.setOptionCode(sjob.optString("optionCode"));
//                    info.setOptionInfo(sjob.optString("optionInfo"));
//                    info.setProduceYear(sjob.optString("produceYear"));
//                    info.setTransMission(sjob.optString("transMission"));
//                    info.setStandardPartNamesList(sjob.optString("standardPartNames").split("[,]"));
//                    optionList.add(info);
                }

            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void testThinkedKey(String input, String[] names) {
        MJSdkService.getInstance().queryThinkedKeys(input, names, new QueryThinKedKeysCallback() {
            @Override
            public void onCallback(List<String> keys) {
                Log.e(TAG, "queryThinkedKeys: " + keys.toString());
            }
        });
    }
}
