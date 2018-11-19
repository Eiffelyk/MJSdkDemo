package com.dataenlighten.aimjsdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.dataenlighten.aimjsdk.demo.Adapter.ResultAdapter;
import com.dataenlighten.aimjsdk.demo.bean.PartBean;
import com.dataenlighten.aimjsdk.demo.bean.QueryPartsResult;
import com.dataenlighten.aimjsdk.demo.custom.ZoomImageView;
import com.dataenlighten.aimjsdk.demo.utils.GsonUtils;
import com.mj.sdk.bean.RelatedPartsRequesParams;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RelatedPartActivity extends AppCompatActivity {

    public static final String TAG = RelatedPartActivity.class.getSimpleName();

    private TextView partName,partOE,partPrice,partImgNumber;
    private RecyclerView recyclerView;
    private ZoomImageView zoomImageView;
    private List<PartBean> partBeanList = new ArrayList<>();
    private  ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_parts);
        initView();
        initData();
    }

    private void initView(){
        partName = findViewById(R.id.tv_partname);
        partOE = findViewById(R.id.tv_oe);
        partImgNumber = findViewById(R.id.tv_number);
        partPrice = findViewById(R.id.tv_price);
        recyclerView = findViewById(R.id.rlv_deatil);
        zoomImageView = findViewById(R.id.img_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(RelatedPartActivity.this));
        adapter = new ResultAdapter(RelatedPartActivity.this, partBeanList,false);
        recyclerView.setAdapter(adapter);
    }

    private void initData(){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null){
            Bundle bundle = intent.getExtras();
            PartBean bean =  bundle.getParcelable("partBean");

            partName.setText(bean.getStandardPartName());
            partOE.setText("OE号: " + bean.getPartNumber());
            partPrice.setText("4s价格: "+bean.getPartPrice());
            partImgNumber.setText("图中编号: "+ bean.getPartRefOnImage());

            getEPCImg(bean.getImage(),VinQueryActivity.carInfo.getPrefix());
            queryRelatedParts(bean.getImage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getEPCImg(String imageName, String prefix){
        MJSdkService.getInstance().queryPartEPCImg(imageName, prefix, new QueryCallBack() {
            @Override
            public void onSuccess(final String responseBody) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String code = jsonObject.getString("code");
                            if ("0000".equals(code)) {
                                String imageInfo = jsonObject.optString("imageInfo");
                                Glide.with(RelatedPartActivity.this)
                                        .load(imageInfo)
                                        .priority(Priority.LOW)
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder)
                                        .into(zoomImageView);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(RelatedPartActivity.this, "getEPCImg failed ! msg:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void queryRelatedParts(String imageName){
        RelatedPartsRequesParams partsRequesParams = new RelatedPartsRequesParams();
        partsRequesParams.setImageName(imageName);
        partsRequesParams.setCarInfo(VinQueryActivity.carInfo);
        MJSdkService.getInstance().queryRelatedParts(partsRequesParams, new QueryCallBack() {
            @Override
            public void onSuccess(final String responseBody) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        QueryPartsResult queryPartsResult = GsonUtils.parseJsonToBean(responseBody,QueryPartsResult.class);
                        partBeanList.clear();
                        partBeanList.addAll(queryPartsResult.getPartList());
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(RelatedPartActivity.this, "queryRelatedParts failed ! msg:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    /*private void queryPartsByKey(String key, QueryPartsByKeyRequesParams.QueryMode queryMode){
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
                Toast.makeText(RelatedPartActivity.this, "queryPartsByKey failed ! msg:"+ e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }*/



}
