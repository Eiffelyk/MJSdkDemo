## 智能推荐
### 功能说明
    通过已选配件推荐更多配件

### 调用
```java
/**
 * 智能推荐接口
 * @param params 智能推荐参数
 * @param queryCallBack
 */
void queryRecommendPartsBySelected(RecommendPartsRequesParams params, QueryCallBack queryCallBack);
```

参数说明：

```java
/**
 * 智能推荐配件  参数
 */
public class RecommendPartsRequesParams {
       /**是否包含可操作项目**/
       private boolean containOperation = false;
       /** 车辆信息,必须赋值字段vinCode,body,brand，optionCode为null时返回全部配置（高配激光大灯，低配卤素，都返回）*/
       private CarInfo carInfo;
       /** 圈选查询配件返回的上下文，推荐去重使用*/
       private String context;
       /** 是否是底盘件 */
       private boolean isBottom = false;
       /** 已选配件的名称列表*/
       private List<SelectionInfo> selectedPartNameList;
}
```

```java
public class SelectionInfo {
    /**当前配件的坐标*/
    private int[] positions;
    /**配件名称 not null*/
    private String partName;
```
```java
/**
 *  通过VIN定型获取的车体信息
 */
public class CarInfo {
    /** 车的VIN码 */
    private String vinCode = null;
    /** 车配置 id码 */
    private String optionCode = null;
    /** 厂商*/
    private String maker = null;
    /** 品牌 */
    private String brand = null;
    /** 中文标识 */
    private String vehicleChn = null;
    /** 车辆品牌所属国系 */
    private String country = null;
    /** 车辆大小类别  如 "大中型"*/
    private String carGrade	= null;
    /** 价格范围底价 */
    private String minPrice = null;
    /** 价格范围最高价 */
    private String maxPrice	= null;
    /** 车体型号  如"三厢4门"*/
    private String body = null;
}
```

### 返回
| 参数名称 | 类型|是否有值|描述|
| --- | --- |----|----|
|partList|List<[PartInfo](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/model/partInfo.md)|是|配件列表|
|oeQuery|String|是|是否是OE查询|
|brandClass|String|是|品牌|
|prefix|String|是|配件前缀|



### 返回示例
```
{
	"code": "0000",
	"codeDescription": "成功",
	"toastMessage": "成功",
	"brand": "东风日产",
	"brandId": "009",
	"brandClass": "Nissan",
	"online": false,
	"prefix": "nissan",
	"oeQuery": false,
	"partList": [{
		"standardPartName": "倒车镜固定座饰板（右）",
		"srcPartName": "COVER-FRONT DOOR CORNER,OUTER RH",
		"partPrice": "28.50",
		"partNumber": "802903RA0A",
		"substitute": "",
		"image": null,
		"partRefOnImage": "80290",
		"qty": null,
		"comment": null,
		"srcPartNumber": null,
		"reqPartNumber": null,
		"originalName": null,
		"position": "(782,211,51,17)",
		"reqPartPrice": null,
		"rank": -1,
		"parent": null,
		"child": null,
		"partId": "normal:倒车镜固定座饰板（右）802903RA0A80290",
		"partType": "normal",
		"thumbnailImage": "http://mjresource.oss-cn-hangzhou.aliyuncs.com/part-preview/倒车镜固定座饰板（右）.png"
	}],
	"ResponseCode": "8000001",
	"ResponseCodeDescription": "成功",
	"StandardQueryID": null,
	"QueryTime": null
}
```