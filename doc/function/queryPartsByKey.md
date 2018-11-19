##  通过字符串查询配件，支持返回操作项。
### 功能说明
    该接口是一个多功能查询的接口，包含:标准名查询、江湖名二次查询、二次推荐、OE反查（模糊查询）、OE纠偏查询等查询配件的功能


### 调用
```java
/**
 * 根据关键字查询配件。
 * @param params  关键字查询参数。
 * @param queryCallBack
 */
void queryPartsByKey(QueryPartsByKeyRequesParams params, QueryCallBack queryCallBack);
```

参数说明：

```java
public class QueryPartsByKeyRequesParams {
        /**是否开启二次查询，明觉标准名查询时可以传false*/
        private boolean secondQuery = true;
        /**是否返回局部总成关系*/
        private boolean parentChild = false;
        /**是否包含可操作工项*/
        private boolean containOperation = true;
        /**是否包含已选工项（key=“换左前大灯”，返回结果中，配件已经选中左前大灯的“更换”工项）*/
        private boolean autoChooseOption = true;
        /** 车辆信息 vinCode不能为空，optionCode为null时返回所有配置（高配激光大灯，低配卤素灯 全返回）*/
        private CarInfo carInfo;
        /** 查询关键字 */
        private String input;
        /** 查询类型 选择对应的类型，查询出来的配件更加准确*/
        private QueryMode QueryMode;
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
