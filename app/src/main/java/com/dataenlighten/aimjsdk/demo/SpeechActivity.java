package com.dataenlighten.aimjsdk.demo;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mj.sdk.service.MJSdkService;
import com.mj.sdk.speech.SpeechListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class SpeechActivity extends AppCompatActivity {

    public static final String TAG = SpeechActivity.class.getSimpleName();
    //讯飞语音
    private Context mContext = null;
    private long mLastDownTime;
    private float mLastPointY;
    private String recongnizeStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_speech);
        RxPermissions rxPermissions = new RxPermissions(this);
        Disposable disposable = rxPermissions.requestEach(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if (permission.granted) {
                    MJSdkService.getInstance().initSpeechEngine(SpeechActivity.this, findViewById(R.id.tv_touch_speek_view), new SpeechListener() {
                        @Override
                        public void speechInitFailure(Exception e) {
                            Log.e(TAG, "speechInitFailure Exception:", e);
                        }

                        @Override
                        public void speechRecongnize(String content) {
                            Log.e(TAG, "speechRecongnize content:" + content);
                            recongnizeStr = content;
                        }

                        @Override
                        public void speechStart() {

                        }

                        @Override
                        public void speechFinish() {
                            Log.e(TAG, "speechFinish content:" + recongnizeStr);
                        }
                    });
                } else if (permission.shouldShowRequestPermissionRationale) {
                    Log.e(TAG, "testRxPermission CallBack onPermissionsDenied() : " + permission.name + "request denied");
//                            ToastUtil.showShort(instance, "拒绝权限，等待下次询问哦");
                } else {
                    Log.e(TAG, "testRxPermission CallBack onPermissionsDenied() : this " + permission.name + " is denied " +
                            "and never ask again");
//                            ToastUtil.showShort(instance, "拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限");
                }
            }
        });



       /* mIat = SpeechRecognizer.createRecognizer(this, new InitListener() {
            @Override
            public void onInit(int code) {
                Log.e(TAG,"init code :"+ code);
            }
        });*/
    }

    /*private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLastDownTime = event.getEventTime();
                    mLastPointY = event.getRawY();
                    // 移动数据分析，收集开始听写事件
                    FlowerCollector.onEvent(SpeechActivity.this, "iat_recognize");
                    // 设置参数
                    // 清空参数
                    mIat.setParameter(SpeechConstant.PARAMS, null);
                    // 设置听写引擎
                    mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
                    // 设置返回结果格式
                    mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
                    // 设置语言
                    mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
                    // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
                    mIat.setParameter(SpeechConstant.VAD_BOS, "20000");
                    // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
                    mIat.setParameter(SpeechConstant.VAD_EOS, "20000");
                    // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
                    mIat.setParameter(SpeechConstant.ASR_PTT, "1");
                    mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
                    mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
                    mIat.startListening(mRecognizerListener);
                    Toast.makeText(SpeechActivity.this,"开始说话,,,",Toast.LENGTH_LONG).show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    long duration = event.getEventTime() - mLastDownTime;
                    if (duration >= 20 * 1000) {
                        mIat.stopListening();
                    }

                    if (mLastPointY - event.getRawY() > 60 && mIat.isListening()) {
                        mIat.cancel();
                    }
                    break;
                case MotionEvent.ACTION_UP:

                    if (mIat.isListening()) {
                        try {
                            Thread.sleep(300);
                            mIat.stopListening();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (mIat.isListening()) {
                        mIat.stopListening();
                    }
                    break;
            }
            return true;
        }
    };


    //语音识别监听器
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            Log.e(TAG,"onError");
        }

        @Override
        public void onBeginOfSpeech() {
            Log.e(TAG,"onError");
        }

        @Override
        public void onEndOfSpeech() {
            Log.e(TAG,"onEndOfSpeech");
        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.e(TAG,"onResult");
            if (!b) {
                printResult(recognizerResult);
            } else {
                onEndOfSpeech();
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e(TAG,"onError");
            Toast.makeText(mContext,speechError.getErrorDescription(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
        }
    };

    //输出语音结果
    private void printResult(RecognizerResult results) {
        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
