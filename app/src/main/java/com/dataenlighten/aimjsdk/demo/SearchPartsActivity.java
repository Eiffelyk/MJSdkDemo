package com.dataenlighten.aimjsdk.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mj.sdk.bean.DamageInfo;
import com.mj.sdk.bean.EstimateByPartsRequestParams;
import com.mj.sdk.bean.QueryPartsByKeyRequesParams;
import com.mj.sdk.bean.RecommendPartsRequesParams;
import com.mj.sdk.bean.RelatedPartsRequesParams;
import com.mj.sdk.callback.QueryCallBack;
import com.mj.sdk.service.MJSdkService;
import com.mj.sdk.view.DrawManager;
import com.mj.sdk.view.DrawPartView;
import com.mj.sdk.view.OnDrawQueryListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SearchPartsActivity extends AppCompatActivity {

    public static final String TAG = SearchPartsActivity.class.getSimpleName();
    private DrawPartView drawPartView;
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawPartView = (DrawPartView) findViewById(R.id.draw_drawview);
        DrawManager.getInstance().setOnDrawQueryListener(new OnDrawQueryListener() {
            @Override
            public void beforeQueryDraw() {

            }

            @Override
            public void onDrawQuerySuccess(String result) {
                Log.e(TAG,"onDrawQuerySuccess:"+result);

                try {
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
//                    recommendPartsRequesParams.setSelectedPartNameList(nameList);
                    testRecommendPart(recommendPartsRequesParams);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onDrawQueryFailure(Exception e) {
                Log.e(TAG,"onDrawQueryFailure:",e);
            }
        });
    }

    private void testRecommendPart(RecommendPartsRequesParams partsRequesParams){
        MJSdkService.getInstance().queryRecommendPartsBySelected(partsRequesParams, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                Log.e(TAG,"testRecommendPart onSuccess: " + responseBody);

                RelatedPartsRequesParams relatedPartsRequesParams = new RelatedPartsRequesParams();
                relatedPartsRequesParams.setCarInfo(DrawManager.getInstance().getCarInfo());

                JSONObject object = null;
                try {
                    object = new JSONObject(responseBody);

//                    String ImagePrePath = object.optString("");
//                    relatedPartsRequesParams.setPrefix("");

                    if ( "0000".equals(object.optString("code"))) {
                        JSONArray jay = object.optJSONArray("partList");
                        int requiredLength = jay.length();
                        if(requiredLength > 1){
                            requiredLength = 1;
                        }
                        for (int i = 0; i < requiredLength; i++) {
                            JSONObject sjob = jay.getJSONObject(i);
                            String imageName = sjob.optString("image");
//                            relatedPartsRequesParams.setPartNo(sjob.optString("partNumber"));
                            relatedPartsRequesParams.setImageName(imageName);
//                            relatedPartsRequesParams.setPrefix("bmw");
                            testThumbnail(imageName);
                            testEPCImg(MainActivity.prefix,imageName);
                        }
                    }
                    testRelatedPart(relatedPartsRequesParams);

                    QueryPartsByKeyRequesParams partsByKeyRequesParams = new QueryPartsByKeyRequesParams();
                    partsByKeyRequesParams.setCarInfo(DrawManager.getInstance().getCarInfo());
                    partsByKeyRequesParams.setInput("保险杠");
                    partsByKeyRequesParams.setQueryMode(QueryPartsByKeyRequesParams.QueryMode.Manual_Input);

                    testPartsByKey(partsByKeyRequesParams);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG,"testRecommendPart onFail: " ,e);
            }
        });
    }

    private void testRelatedPart(RelatedPartsRequesParams params){
        MJSdkService.getInstance().queryRelatedParts(params, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                Log.e(TAG,"testRelatedPart onSuccess: " + responseBody);
            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG,"testRecommendPart onFail: " ,e);
            }
        });
    }

    private void testThumbnail(String imageName){
       /* MJSdkService.getInstance().getPartThumbnail(imageName, new GetBitmapCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                ((ImageView)findViewById(R.id.img_thumbnail)).setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG,"testThumbnail onFail: " ,e);
            }
        });*/
    }

    /**
     * @param preName
     * @param imageName
     */
    private void testEPCImg(String preName,String imageName){
        MJSdkService.getInstance().queryPartEPCImg(imageName, preName, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
               Log.e(TAG,"queryPartEPCImg responseBody:"+responseBody);
            }

            @Override
            public void onFail(Exception e) {
                Log.e(TAG,"queryPartEPCImg onFail:",e);
            }
        });
    }

    public void testPartsByKey(final QueryPartsByKeyRequesParams params){
        MJSdkService.getInstance().queryPartsByKey(params, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                EstimateByPartsRequestParams eParams = new EstimateByPartsRequestParams();
                eParams.setCarInfo(DrawManager.getInstance().getCarInfo());
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    JSONArray partList = jsonObject.optJSONArray("partList");
                    List<DamageInfo> dList = new ArrayList<>();
                    for(int i = 0; i < partList.length(); i++){
                        DamageInfo damageInfo = new DamageInfo();
                        JSONObject partObj = partList.optJSONObject(i);
                        damageInfo.setPartId(partObj.optString("standardPartName"));
                        damageInfo.setStandardPartName(partObj.optString("standardPartName"));
                        damageInfo.setLaborCost("-1");
                        damageInfo.setPartPrice(partObj.optString("partPrice"));
                        JSONArray operationArr = partObj.optJSONArray("operationList");
                        JSONObject operationObj = operationArr.optJSONObject(0);
                        damageInfo.setOperation(operationObj.optString("operation"));
                        dList.add(damageInfo);
                    }
                    eParams.setDamageInfoList(dList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                testEstimate(eParams);
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    public void testEstimate(EstimateByPartsRequestParams params){
        MJSdkService.getInstance().estimateByParts(params, new QueryCallBack() {
            @Override
            public void onSuccess(String responseBody) {

            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

    public void change(View view) {
        flag = !flag;
        drawPartView.turnSurfaceChassis(flag);
    }
}
