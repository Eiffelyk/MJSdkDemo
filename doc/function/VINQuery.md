## VIN定型
### 功能说明
    通过用户输入的VIN锁定车辆唯一信息，查询出该车的品牌、车型、年款、排量等信息、以及该车包含的配件信息

### 调用
```java
/**
 * VIN解析，返回对应VIN码的车辆信息
 * @param vin VIN码
  * @param partList 获取常用配件个数（0不获取[默认]，1获取top300，2获取top600，3获取topAll）
 * @param queryCallBack 请求回调。
 */
void VINQuery(String vin,int partList, QueryCallBack queryCallBack);
```
### 入参
| 入参 | 类型|是否必须|描述|
| --- | --- |----|----|
|vin|String|是|需要锁定的17位车架号|
|partList|Integer|否|获取常用配件数量（0不获取[默认]，1获取top300，2获取top600，3获取topAll）|

### 入参示例
```
{
	"vinCode": "WBASZ6109ED551752",
	"partList": 3
}
```
### 返回
| 入参 | 类型|是否有值|描述|
| --- | --- |----|----|
|brand|String|是|品牌（中文）|
|brandId|String|是|品牌Id|
|brandClass|String|是|品牌（英文）|
|vehicleList|List<[VehicleCarInfo](https://github.com/Eiffelyk/MJSdkDemo/blob/master/doc/model/vehicleInfo.md)|否|车型信息（此处可能会有多个配置）|


### 返回示例
```
{
	"code": "0000",
	"codeDescription": "错误码描述",
	"toastMessage": "可以展示给用户的提醒",
	"brand": "进口宝马",
	"brandId": "023",
	"brandClass": "Bmw",
	"online": false,
	"prefix": "bmw",
	"vehicleList": [{
		"vehicle": {
			"vinCode": "WBASZ6109ED551752",
			"brandClass": "宝马",
			"maker": "进口宝马",
			"transmission": "8AT",
			"displacement": "2.0T",
			"year": "2014",
			"vehicleChn": "5系",
			"vehicleEn": "528I  (EUR)",
			"body": "三厢4门",
			"t4sSaleModelName": "5系",
			"optionInfo": "",
			"optionCode": "000",
			"gyroBrand": "宝马",
			"carCountry": "德系",
			"carGrade": "中大型",
			"priceZone": "50≤X＜60",
			"brand": "进口宝马",
			"prefix": "Bmw",
			"minPrice": "500000",
			"maxPrice": "600000"
		},
		"partNameListAsList": ["前保险杠皮","后保险杠皮"]
	}],
}
```

### 返回错误码
| 错误码code | 描述                   |
| ------ | ----------------------- |
|0000	|成功|
|1001|VIN不合法|
|1002|VIN不支持|
|1003|非乘用车|
|1004|VIN错误|
|1005|VIN无法解析|
|1006|您无权查看该品牌数据|