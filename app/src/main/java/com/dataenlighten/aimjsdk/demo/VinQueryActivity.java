package com.dataenlighten.aimjsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mj.sdk.bean.CarInfo;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VinQueryActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = VinQueryActivity.class.getSimpleName();

    private EditText mEditText;//输入的控件
    private ImageView clearBtn;
    private Button nextBtn;
    private TextView resultTxtView = null;

    private boolean vinParseComplete = false;
    public static CarInfo carInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vin);
        initView();
    }

    private void initView(){
        mEditText = (EditText) findViewById(R.id.vin_input_vin);
        resultTxtView = (TextView) findViewById(R.id.tv_vin_result);
        clearBtn = (ImageView) findViewById(R.id.img_clear);
        clearBtn.setOnClickListener(this);
        nextBtn = (Button) findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(this);

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String character = mEditText.getText().toString().trim();
                if(event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()){
                    vinQuery();
                    return true;
                }
                if ((actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) && character.length() > 0
                        ) {
                    vinQuery();
                    return true;
                }
                return false;
            }
        });
    }


    private void vinQuery(){
        vinParseComplete = false;
        resultTxtView.setText("");
        final String VIN =  mEditText.getText().toString().trim().toUpperCase();
        MJSdkService.getInstance().VINQuery(VIN, 0, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                resultTxtView.setText(responseBody);
                parseVINInfo(responseBody,VIN);
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(VinQueryActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_clear:
                mEditText.setText("");
                mEditText.setSelection(mEditText.getText().toString().length());
                break;
            case R.id.btn_next:
                if(!vinParseComplete){
                    Toast.makeText(VinQueryActivity.this, "未完成VIN解析",Toast.LENGTH_LONG).show();
                    return;
                }
                startActivity(new Intent(VinQueryActivity.this,DrawActivity.class));
                break;

        }
    }

    //解析VINResponse
    public void parseVINInfo(String json, String VIN) {
        try {
            JSONObject job = new JSONObject(json);
            String rcode = job.optString("code");

            if (rcode.equals("0000")) {
                CarInfo mCarInfo = new CarInfo();

                JSONArray jay = job.optJSONArray("vehicleList");
                for (int i = 0; i < jay.length(); i++) {
                    JSONObject obj = jay.optJSONObject(i);
                    JSONObject sjob = obj.optJSONObject("vehicle");
                    String body = sjob.optString("body");
                    if (TextUtils.isEmpty(body) || body.equals("") || body.equals("null")) {
                        body = "三厢4门";
                    }
                    mCarInfo.setBrand(sjob.optString("gyroBrand"));
                    mCarInfo.setBody(body);
                    mCarInfo.setVinCode(VIN);
                    mCarInfo.setOptionCode(sjob.optString("optionCode"));
                    mCarInfo.setCountry(sjob.optString("carCountry"));
                    mCarInfo.setMaker(sjob.optString("maker"));
                    mCarInfo.setVehicleChn(sjob.optString("vehicleChn"));
                    mCarInfo.setCarGrade(sjob.optString("carGrade"));
                    mCarInfo.setMinPrice(sjob.optString("minPrice"));
                    mCarInfo.setMaxPrice(sjob.optString("maxPrice"));
                    mCarInfo.setPrefix(sjob.optString("prefix"));
                    carInfo = mCarInfo;
                    vinParseComplete = true;
                    return;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        vinParseComplete = false;
    }
}