## 装配图查询
### 功能说明
    根据配件图片名查询该配件的装配图，返回的是临时授权的url

### 调用
```
/**
 * 获取配件EPC装配图
 * @param imageName   图片名，PartInfo中的image字段
 * @param imagePrefix  图片前缀，对应VIN定型返回的prefix字段
 * @param queryCallBack
 */
void queryPartEPCImg(String imageName,String imagePrefix, QueryCallBack queryCallBack);
```
### 入参
| 入参 | 类型 |是否必须|描述|
| --- | --- |----|----|
|imageName|String |是|图片名，PartInfo中的image字段|
|imagePrePath|String |是|图片前缀，对应VIN定型返回的prefix字段）|

### 入参示例
```
{
	"imageName": "115_963A_001.png",
	"imagePrePath": "Nissan"
}
```
### 返回
| 参数名称 | 类型|是否有值|描述|
| --- | --- |----|----|
|imageInfo|String|是|装配图URL|


### 返回示例
```
{
	"code": "0000",
	"codeDescription": "成功",
	"toastMessage": "成功",
	"imageInfo": "https://mj-test-paic-image.oss-cn-shenzhen.aliyuncs.com/Nissan/xxxxxxx.png"
}
```
