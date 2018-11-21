## SDK简述
本SDK开发旨在提供方便快捷地获取汽车配件信息，通过VIN码或汽车品牌配置信息进行车辆定型后即可使用圈选或者配件名、OE等形式获取配件信息。

本SDK使用时需要获取正式授权的license文件。商务合作请联系[明觉科技](http://www.dataenlighten.com)，SDK仅提供合作客户使用，违用必究!

### 更新记录
|时间|版本|更新内容|
|----|-----|-----|
|2018年11月19日|mjsdk:1.0.19<br>mjthinkkey:1.0.1<br>mjspeech:1.0.1|1.修复获取TopN无数据的bug<br>2.添加设备唯一标识UUID<br>3.规范上传字段|

## **使用步骤：**
### 0.拷贝申请到的license.lic（此文件请勿重命名）文件到assets目录中
### 1.添加依赖及权限：
在工程build.gradle配置脚本中buildscript和allprojects段中添加【明觉科技SDK】 新maven仓库地址
```java

allprojects {
    repositories {
        maven {
            url 'https://dl.bintray.com/dataenlighten/MJSDK'
        }
    }
}
```
#####     *Gradle
 在项目module build.gradle配置脚本中dependencies添加
```java
    //SDK依赖
    implementation 'com.mingjue.sdk:mjsdk:1.0.19'//SDK主体功能
    implementation 'com.mingjue.sdk:mjthinkkey:1.0.1'//首字母联想模块独立
    implementation 'com.mingjue.sdk:mjspeech:1.0.1'//语音模块独立
```

#####     * 在项目AndroidManifest.xml配置脚本中添加权限
```java

    <!-- 网络状态 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!-- 获取手机录音机使用权限，听写、识别、语义理解需要用到这些权限 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <!--获取网络状态，请求网络前判断是否有网络-->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
### 2.初始化以及方法调用：

#####     * 初始化
```java
/**
 * 服务初始化。
 * @param ctx
 * @param  onSdkInitLisener 初始化回调
 * @throws  LicenseNotFoundException 未发现license异常
 */
void init(Context ctx,OnSdkInitLisener onSdkInitLisener) throws LisenceNotFoundException;
```

#####     * VIN解析

```java
/**
 * VIN解析，返回对应VIN码的车辆信息
 * @param vin VIN码
  * @param partList 获取常用配件个数（0不获取[默认]，1获取top300，2获取top600，3获取topAll）
 * @param queryCallBack 请求回调。
 */
void VINQuery(String vin,int partList, QueryCallBack queryCallBack);
```
[详细信息](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/function/VINQuery.md).

#####     * 智能推荐接口
```java
/**
 * 智能推荐接口
 * @param params 智能推荐参数
 * @param queryCallBack
 */
void queryRecommendPartsBySelected(RecommendPartsRequesParams params, QueryCallBack queryCallBack);
```
[详细信息](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/function/queryRecommendPartsBySelected.md).

##### * 查询关联配件

```java
/**
 * 查询关联配件
 * @param params 关联配件查询参数
 * @param queryCallBack
 */
void queryRelatedParts(RelatedPartsRequesParams params, QueryCallBack queryCallBack);
```
[详细信息](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/function/queryRelatedParts.md).

#####      * 通过字符串查询配件，支持返回操作项。

```java
/**
 * 根据关键字查询配件。
 * @param params  关键字查询参数。
 * @param queryCallBack
 */
void queryPartsByKey(QueryPartsByKeyRequesParams params, QueryCallBack queryCallBack);
```
[详细信息](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/function/queryPartsByKey.md).

#####     * 获取配件EPC装配图

```java
/**
 * 获取配件EPC装配图
 * @param imageName  配件图片名
 * @param imagePrefix  车辆图片前缀 ，在VIN定型接口中获取。
 * @param queryCallBack
 */
void queryPartEPCImg(String imageName,String imagePrefix, QueryCallBack queryCallBack);
```
[详细信息](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/function/queryPartEPCImg.md).


#####     * 生成圈选小汽车 View

第一种方式---直接使用返回的DrawPartView动态填充界面
```java
/**
 * 生成圈选小汽车 View
 * @param context  Android上下文
 * @param carInfo   车辆信息。
 * @param onDrawQueryListener
 * @return DrawPartView  圈选的view
 */
DrawPartView createDrawPartView(Context context,CarInfo carInfo, OnDrawQueryListener onDrawQueryListener);
```
第二种方式---布局xml文件种直接引用DrawPartView，并且设置车辆信息以及回调
```java
//先设置车辆信息
DrawManager.getInstance().init(carInfo);
DrawManager.getInstance().setOnDrawQueryListener（listener）
//再初始化DrawPartView
```
注：turnSurfaceChassis方法为切换全车件和底盘件
```java
drawPartView.turnSurfaceChassis(boolean flag);
```
##### * 语音定损服务。

添加依赖：

```java
 implementation 'com.mingjue.sdk:mjspeech:1.0.1'
```

接口定义：

```java
/**
 * 语音定损服务。
 * @param context  android上下文，建议用app的context
 * @param touchView  长按说话的view
 * @param speechListener 语音回调
 */
SpeechService speechService = SpeechService.getInstance(context);
speechService.init(View touchView, SpeechListener speechListener);
```

#####     * 通过已选配件智能定损

```java
/**
 * 通过已选配件智能定损
 * @param params 定损的参数。
 * @param queryCallBack
 */
void estimateByParts(EstimateByPartsRequestParams params, QueryCallBack queryCallBack);
```

参数说明：

```java
/**
 * 智能定损接口参数
 */
public class EstimateByPartsRequestParams {
    /** 车辆信息*/
    private CarInfo carInfo = null;
    /** 修理厂id*/
    private String repairer = null;
    /** 维修类型 如"送修" */
    private String repairType = null;
    /** 保险公司id*/
    private String insurer = null;
    /** 选择的配件工项信息列表 */
    private List<DamageInfo> damageInfoList = null;
}
```

```java
public class DamageInfo {
    /** 配件选择的工项 */
    private String operation = null;
    /** 配件Id */
    private String partId = null;
    /** 配件标准名称 */
    private String standardPartName = null;
    /** 配件损伤程度 */
    private String severity = null;
    /** 配件价格 */
    private String partPrice = null;
    /** 工时价格 */
    private String laborCost = null;
}
```



##### * 首字符关联配件名。

添加依赖：

```java
 implementation 'com.mingjue.sdk:mjthinkkey:1.0.1'
```

接口定义：

```java
	/**
     * 首字母关联配件名
     * @param input   用户输入的首字母串
     * @param names   VIN定型里面返回的top1000 配件名称数组
     * @param callBack  查询回调
     */
    void queryThinkedKeys(String input, String[] names, QueryThinKedKeysCallback callBack) 
```

接口调用：

```java
MJInitialService.getInstance().queryThinkedKeys(input,names,queryThinKedKeysCallback）
```



### 3.混淆文件：

```java
    #-libraryjars libs/okhttp3
    -dontwarn okhttp3.**
    -keep class okhttp3.** { *; }

    #-libraryjars libs/okio
    -dontwarn okio.**
    -keep class okio.** { *; }
```

### 4.接口错误码表

| 错误码 | m描述                   |
| ------ | ------------------ |
| 0000   | 成功                    |
| 1001   | VIN不合法               |
| 1002   | VIN不支持               |
| 1003   | 非乘用车                |
| 1004   | VIN错误                 |
| 1005   | VIN无法解析             |
| 1006   | 无权查看该品牌数据      |
| 1011   | 请求VIN的配件数据不存在 |
| 1012   | 该车无此配件            |
| 1013   | 请求图片不存在          |
| 1014   | 推荐配件数据不存在      |
| 1015   | 该车型查不到配件        |
| 9007   | 后台API异常             |

