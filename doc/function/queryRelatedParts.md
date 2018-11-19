## 相邻配件查询
### 功能说明
    根据配件图片名查询该配件的相邻配件信息

### 调用

```java
/**
 * 查询关联配件
 * @param params 关联配件查询参数
 * @param queryCallBack
 */
void queryRelatedParts(RelatedPartsRequesParams params, QueryCallBack queryCallBack);
```

参数说明：

```java
/**
 * 获取关联配件 参数
 */
public class RelatedPartsRequesParams {
    /** 车辆信息,carInfo必须赋值字段为vinCode,optionCode为null时返回所有配置（高配激光大灯，低配卤素灯 全返回） */
    private CarInfo carInfo;
    /** 配件图片名称*/
    private String imageName;
}
```
### 入参
| 入参 | 类型 |是否必须|描述|
| --- | --- |----|----|
|vinCode|String |是|VIn码(长度不超过17位、非空、全部转换成大写)|
|optionCode|String |否|车型配置码（VIN定型返回的），000默认选择最高配置 option_code传null，会返回所有的配置|
|imageName|String |是|图片名称（非空）|
|containOperation|boolean |否|是否包含可维修项目（当此字段为true时，返回的PartInfo中才包含可操作项目）|

### 入参示例
```
{
"vinCode": "LHGTF3879E8026237",
"imageName": "GR/A1/432335.png",
"optionCode": "000"
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