package com.dataenlighten.aimjsdk.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mj.mjspeech.SpeechListener;
import com.mj.mjspeech.SpeechService;
import com.mj.sdk.bean.QueryPartsByKeyRequesParams;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;
import com.mj.sdk.view.DrawManager;
import com.mj.sdk.view.DrawPartView;
import com.mj.sdk.view.OnDrawQueryListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class DrawActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = DrawActivity.class.getSimpleName();
    private DrawPartView drawPartView;
    private boolean isBottom = false;

    private EditText mEditText;                 //输入框
    private ImageView mImgClear;                //清空输入
    private ImageView mImageVoice;              //语音图标
    private TextView mTvChannel1,mTvChannel2;

    private RelativeLayout mCarVoiceLayout;     //语音搜索布局
    private TextView mTextVoice;                //语音界面底部的TextView

    //语音动画
    private FrameLayout mVoiceAnimLayout;
    private ImageView mVoiceAnimImage1;
    private ImageView mVoiceAnimImage2;
    private ImageView mVoiceAnimImage3;
    private ImageView mVoiceAnimImage4;
    private ImageView mVoiceAnimImage5;
    private ImageView mVoiceAnimImage6;
    private ImageView mVoiceAnimImage7;
    private ImageView mVoiceAnimIcon;
    private TextView mVoiceAnimTextView;

    private boolean permissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        initDraw();
        initView();
        initEditText();
    }

    private void initDraw(){
        drawPartView = (DrawPartView) findViewById(R.id.draw_drawview);
        DrawManager.getInstance().init(VinQueryActivity.carInfo);
        DrawManager.getInstance().setOnDrawQueryListener(onDrawQueryListener);
    }

    private void initView(){
        mImageVoice = (ImageView) findViewById(R.id.pic_voice);
        mEditText = (EditText) findViewById(R.id.pic_word);
        mImgClear = (ImageView) findViewById(R.id.img_clear);
        mTvChannel1 = (TextView) findViewById(R.id.tv_channel1);
        mTvChannel2 = (TextView) findViewById(R.id.tv_channel2);
        mTvChannel1.setOnClickListener(this);
        mTvChannel2.setOnClickListener(this);
        mImageVoice.setOnClickListener(this);
        mImgClear.setOnClickListener(this);

        mCarVoiceLayout = (RelativeLayout) findViewById(R.id.ll_voice);
        mCarVoiceLayout.setVisibility(View.GONE);

        mTextVoice = (TextView) findViewById(R.id.tv_voice);
        mVoiceAnimLayout = (FrameLayout) findViewById(R.id.frame_voice);
        mVoiceAnimImage1 = (ImageView) findViewById(R.id.img_voice1);
        mVoiceAnimImage2 = (ImageView) findViewById(R.id.img_voice2);
        mVoiceAnimImage3 = (ImageView) findViewById(R.id.img_voice3);
        mVoiceAnimImage4 = (ImageView) findViewById(R.id.img_voice4);
        mVoiceAnimImage5 = (ImageView) findViewById(R.id.img_voice5);
        mVoiceAnimImage6 = (ImageView) findViewById(R.id.img_voice6);
        mVoiceAnimImage7 = (ImageView) findViewById(R.id.img_voice7);
        mVoiceAnimIcon = (ImageView) findViewById(R.id.img_voice_icon);
        mVoiceAnimTextView = (TextView) findViewById(R.id.tv_voice_alert);
        SpeechService speechService = SpeechService.getInstance(getApplicationContext());
        speechService.init(mTextVoice,speechListener);
    }


    private void initEditText(){
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String character = mEditText.getText().toString().trim();
                if(event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()){
                    queryPartsByKey(character, QueryPartsByKeyRequesParams.QueryMode.Manual_Input);
                    return true;
                }
                if ((actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) && character.length() > 0
                        ) {
                    queryPartsByKey(character, QueryPartsByKeyRequesParams.QueryMode.Manual_Input);
                    return true;
                }
                return false;
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mEditText.setHint("");
                } else
                    mEditText.setHint("输入配件名称");
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mImgClear.setVisibility(View.GONE);
                    return;
                }
                if (s.length() > 0 && mImgClear.getVisibility() != View.VISIBLE) {
                    mImgClear.setVisibility(View.VISIBLE);
                }
            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCarVoiceLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mCarVoiceLayout.isShown()){
                mCarVoiceLayout.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_clear:
                mEditText.setText("");
                break;
            case R.id.pic_voice:
                mCarVoiceLayout.setVisibility(View.VISIBLE);
                reqestPermission();
                break;
            case R.id.tv_channel1:
            case R.id.tv_channel2:
                mTvChannel1.setBackgroundResource(R.drawable.shape_channel_unselect);
                mTvChannel2.setBackgroundResource(R.drawable.shape_channel_unselect);
                mTvChannel1.setTextColor(getResources().getColor(R.color.black));
                mTvChannel2.setTextColor(getResources().getColor(R.color.black));
                view.setBackgroundResource(R.drawable.shape_channel_select);
                ((TextView) view).setTextColor(getResources().getColor(R.color.themecolor));
                if (view.getId() == R.id.tv_channel2) {
                    if (isBottom)
                        return;
                } else {
                    if (!isBottom)
                        return;
                }
                isBottom = !isBottom;
                drawPartView.turnSurfaceChassis(!isBottom);
                break;

        }
    }

    private void reqestPermission(){
        if(permissionGranted){
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable disposable = rxPermissions.requestEach(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    permissionGranted = true;
                } else if (permission.shouldShowRequestPermissionRationale) {
                    Log.e(TAG, "testRxPermission CallBack onPermissionsDenied() : " + permission.name + "request denied");
                } else {
                    Log.e(TAG, "testRxPermission CallBack onPermissionsDenied() : this " + permission.name + " is denied " +
                            "and never ask again");
                }
            }
        });
    }

    private SpeechListener speechListener = new SpeechListener() {
        @Override
        public void speechInitFailure(Exception e) {
            mVoiceAnimIcon.setVisibility(View.GONE);
            mVoiceAnimLayout.setVisibility(View.GONE);
        }

        @Override
        public void speechRecongnizing(String content) {
            mEditText.setText(content);
        }

        @Override
        public void speechRecongnizeEnd() {
            queryPartsByKey(mEditText.getText().toString().trim(), QueryPartsByKeyRequesParams.QueryMode.Voice);
        }

        @Override
        public void speechStart() {
            mVoiceAnimLayout.setVisibility(View.VISIBLE);
            mVoiceAnimTextView.setText("可以试试一次说多个配件哦");
            mTextVoice.setBackground(getResources().getDrawable(R.drawable.bg_voicelayout_press));
        }

        @Override
        public void speechFinish() {
            mVoiceAnimTextView.setText("可以试试一次说多个配件哦");
            mTextVoice.setBackground(getResources().getDrawable(R.drawable.bg_voicelayout_normal));
            mVoiceAnimIcon.setVisibility(View.GONE);
            mVoiceAnimLayout.setVisibility(View.GONE);
        }

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            mVoiceAnimIcon.setVisibility(View.VISIBLE);
            mVoiceAnimImage1.setVisibility(View.VISIBLE);
            mVoiceAnimImage2.setVisibility(View.GONE);
            mVoiceAnimImage3.setVisibility(View.GONE);
            mVoiceAnimImage4.setVisibility(View.GONE);
            mVoiceAnimImage5.setVisibility(View.GONE);
            mVoiceAnimImage6.setVisibility(View.GONE);
            mVoiceAnimImage7.setVisibility(View.GONE);
            if (i > 0)
                mVoiceAnimImage7.setVisibility(View.VISIBLE);
            if (i > 5)
                mVoiceAnimImage6.setVisibility(View.VISIBLE);
            if (i > 10)
                mVoiceAnimImage5.setVisibility(View.VISIBLE);
            if (i > 15)
                mVoiceAnimImage4.setVisibility(View.VISIBLE);
            if (i > 20)
                mVoiceAnimImage3.setVisibility(View.VISIBLE);
            if (i > 25)
                mVoiceAnimImage2.setVisibility(View.VISIBLE);
        }
    };

    private OnDrawQueryListener onDrawQueryListener = new OnDrawQueryListener() {
        @Override
        public void beforeQueryDraw() {

        }

        @Override
        public void onDrawQuerySuccess(String result) {
            Log.e(TAG,"onDrawQuerySuccess:"+result);

          /*  try {
                JSONObject object = new JSONObject(result);
                String context = object.optString("context");
                RecommendPartsRequesParams recommendPartsRequesParams = new RecommendPartsRequesParams();
                recommendPartsRequesParams.setCarInfo(DrawManager.getInstance().getCarInfo());
                recommendPartsRequesParams.setContext(context);

                List<String> nameList = new ArrayList<>();
                if ("0000".equals(object.optString("code"))
                        ) {
                    JSONArray jay = object.optJSONArray("partList");
                    int requiredLength = jay.length();
                    if(requiredLength > 3){
                        requiredLength = 3;
                    }
                    for (int i = 0; i < requiredLength; i++) {
                        JSONObject sjob = jay.getJSONObject(i);
                        nameList.add(sjob.optString("standardPartName"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            Intent intent = new Intent(DrawActivity.this, PartListActivity.class);
            intent.putExtra("partListResult",result);
            startActivity(intent);
        }

        @Override
        public void onDrawQueryFailure(Exception e) {
            Log.e(TAG,"onDrawQueryFailure:",e);
        }
    };

    private void queryPartsByKey(String key, QueryPartsByKeyRequesParams.QueryMode queryMode){
        QueryPartsByKeyRequesParams params = new QueryPartsByKeyRequesParams();
        params.setQueryMode(queryMode);
        params.setInput(key);
        params.setSecondQuery(true);
        params.setParentChild(true);
        params.setAutoChooseOption(true);
        params.setContainOperation(true);
        params.setCarInfo(VinQueryActivity.carInfo);
        MJSdkService.getInstance().queryPartsByKey(params, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                Intent intent = new Intent(DrawActivity.this, PartListActivity.class);
                intent.putExtra("partListResult",responseBody);
                startActivity(intent);
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(DrawActivity.this, "queryPartsByKey failed ! msg:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
