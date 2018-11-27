package com.dataenlighten.aimjsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dataenlighten.aimjsdk.demo.Adapter.ResultAdapter;
import com.dataenlighten.aimjsdk.demo.bean.PartBean;
import com.dataenlighten.aimjsdk.demo.bean.QueryPartsResult;
import com.dataenlighten.aimjsdk.demo.utils.GsonUtils;
import com.mj.sdk.bean.QueryPartsByKeyRequesParams;
import com.mj.sdk.bean.RecommendPartsRequesParams;
import com.mj.sdk.bean.SelectionInfo;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;
import com.mj.thinkkey.MJInitialService;
import com.mj.thinkkey.QueryThinKedKeysCallback;

import java.util.ArrayList;
import java.util.List;


public class PartListActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MJSDKDemo";

    private RecyclerView mRecyclerView;
    private ResultAdapter adapter;
    private List<PartBean> mList = new ArrayList<>();
    private Button searchMore;
    private String contextKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partlist);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("partListResult")) {
            String partListResult = intent.getStringExtra("partListResult");
            QueryPartsResult result = GsonUtils.parseJsonToBean(partListResult, QueryPartsResult.class);
            if (result != null && result.getPartList() != null) {
                contextKey = result.getContext();
                mList.clear();
                mList.addAll(result.getPartList());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initView() {
        searchMore = findViewById(R.id.search_more);
        searchMore.setOnClickListener(this);
        mRecyclerView = findViewById(R.id.rv_partlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(PartListActivity.this));
        adapter = new ResultAdapter(PartListActivity.this, mList, true);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_more:
                queryRecommendPartsBySelected(contextKey, mList);
                break;
        }
    }

    private void queryRecommendPartsBySelected(String contextKeyA, final List<PartBean> mList) {
        RecommendPartsRequesParams recommendPartsRequesParams = new RecommendPartsRequesParams();
        recommendPartsRequesParams.setContainOperation(true);
        recommendPartsRequesParams.setContext(contextKeyA);
        List<SelectionInfo> selectedPartNameList = new ArrayList<>();
        for (PartBean partBean : mList) {
            SelectionInfo selectionInfo = new SelectionInfo();
            selectionInfo.setPartName(partBean.getStandardPartName());
//            selectionInfo.setPositions();//加入配件中position位置推荐更准确
            selectedPartNameList.add(selectionInfo);
        }
        recommendPartsRequesParams.setSelectedPartNameList(selectedPartNameList);
        recommendPartsRequesParams.setCarInfo(VinQueryActivity.carInfo);
        MJSdkService.getInstance().queryRecommendPartsBySelected(recommendPartsRequesParams, new QueryCallBack() {
            @Override
            public void onSuccess(final String responseBody) {
                Log.d(TAG, "queryRecommendPartsBySelected onSuccess: "+responseBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        QueryPartsResult result = GsonUtils.parseJsonToBean(responseBody, QueryPartsResult.class);
                        if (result != null && result.getPartList() != null) {
                            contextKey = result.getContext();
//                            mList.clear();
                            mList.addAll(result.getPartList());
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                e.printStackTrace();
                Toast.makeText(PartListActivity.this, "queryRecommendPartsBySelected failed ! msg:" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
