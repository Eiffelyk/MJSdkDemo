## SDK简述
本SDK开发旨在提供方便快捷地获取汽车配件信息，通过VIN码或汽车品牌配置信息进行车辆定型后即可使用圈选或者配件名、OE等形式获取配件信息。

本SDK使用时需要获取正式授权的license文件。商务合作请联系[明觉科技](http://www.dataenlighten.com)，SDK仅提供合作客户使用，违用必究!
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
    implementation 'com.mingjue.sdk:mjsdk:1.0.13'
```
#####     * 或Maven
```
<dependency>
  <groupId>com.mingjue.sdk</groupId>
  <artifactId>mjsdk</artifactId>
  <version>1.0.13</version>
  <type>pom</type>
</dependency>
```
#####     * 在项目AndroidManifest.xml配置脚本中添加权限
```java

    <!-- 网络状态 -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!-- 获取手机录音机使用权限，听写、识别、语义理解需要用到这些权限 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
### 2.初始化以及方法调用：

#####     * 初始化
```java
void init(Context ctx,OnSdkInitLisener onSdkInitLisener) throws LisenceNotFoundException;
```

#####     * VIN解析
```java
void VINQuery(String vin, QueryCallBack queryCallBack);
```

#####     * 智能推荐接口（圈选页面调用，不需要独立调用）

 ```java
void queryPartsByDraw(String paramsJsonStr,QueryCallBack queryCallBack);
```

#####     * 智能推荐接口
```java
void queryRecommendPartsBySelected(RecommendPartsRequesParams params, QueryCallBack queryCallBack);
```

#####     * 语音定损服务。
```java
void initSpeechEngine(Context context, View touchView, SpeechListener speechListener);
```

#####      * 查询关联配件
```java
void queryRelatedParts(RelatedPartsRequesParams params, QueryCallBack queryCallBack);
```

#####      * 根据关键字查询。
```java
void queryPartsByKey(QueryPartsByKeyRequesParams params, QueryCallBack queryCallBack);
```

#####     * 通过字符串查询配件，支持返回操作项
```java
void queryThinkedKeys(String input, String[] standardNames, QueryThinKedKeysCallback queryThinKedKeysCallback);
```

#####     * 获取配件EPC装配图
```java
void queryPartEPCImg(String imageName,String imagePrefix, QueryCallBack queryCallBack);
```

#####     * 生成圈选小汽车 View
第一种方式---直接使用返回的DrawPartView动态填充界面
```java
DrawPartView createDrawPartView(Context context,CarInfo carInfo, OnDrawQueryListener onDrawQueryListener);
```
第二种方式---布局xml文件种直接引用DrawPartView
```java
DrawManager.getInstance().setOnDrawQueryListener(OnDrawQueryListener listener);
```
注：turnSurfaceChassis方法为切换全车件和底盘件
```java
drawPartView.turnSurfaceChassis(boolean flag);
```
#####     * 通过已选配件智能定损
```java
void estimateByParts(EstimateByPartsRequestParams params, QueryCallBack queryCallBack);
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