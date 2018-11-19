package com.dataenlighten.aimjsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dataenlighten.aimjsdk.demo.Adapter.ResultAdapter;
import com.dataenlighten.aimjsdk.demo.bean.PartBean;
import com.dataenlighten.aimjsdk.demo.bean.QueryPartsResult;
import com.dataenlighten.aimjsdk.demo.utils.GsonUtils;
import com.mj.sdk.bean.QueryPartsByKeyRequesParams;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;

import java.util.ArrayList;
import java.util.List;


public class PartListActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = PartListActivity.class.getSimpleName();

    private EditText mEditText;                 //输入框
    private ImageView mImgClear;                //清空输入
    private ImageView mImageVoice;              //语音图标

    private RecyclerView mRecyclerView;
    private ResultAdapter adapter;
    private List<PartBean> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partlist);
        initView();
        initEditText();
        initData();
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("partListResult")){
            String partListResult = intent.getStringExtra("partListResult");
            QueryPartsResult result = GsonUtils.parseJsonToBean(partListResult,QueryPartsResult.class);
            if(result != null && result.getPartList() != null){
                mList.clear();
                mList.addAll(result.getPartList());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initView(){
        mImageVoice = (ImageView) findViewById(R.id.pic_voice);
        mEditText = (EditText) findViewById(R.id.pic_word);
        mImgClear = (ImageView) findViewById(R.id.img_clear);
        mImageVoice.setOnClickListener(this);
        mImgClear.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rv_partlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PartListActivity.this));
        adapter = new ResultAdapter(PartListActivity.this,mList,true);
        mRecyclerView.setAdapter(adapter);
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
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
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
                break;

        }
    }

    private void queryPartsByKey(String key, QueryPartsByKeyRequesParams.QueryMode queryMode){
        QueryPartsByKeyRequesParams params = new QueryPartsByKeyRequesParams();
        params.setQueryMode(queryMode);
        params.setInput(key);
        params.setSecondQuery(false);
        params.setParentChild(true);
        params.setAutoChooseOption(false);
        params.setContainOperation(false);
        params.setCarInfo(VinQueryActivity.carInfo);
        MJSdkService.getInstance().queryPartsByKey(params, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(PartListActivity.this, "queryPartsByKey failed ! msg:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
