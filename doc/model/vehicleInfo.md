#### BrandInfo
| 字段 | 类型|是否有值|描述|
| --- | --- |----|----|
|brandName|String|是|品牌名称|
|brandId|String|是|品牌Id|
|brandImageUrl|String|是|品牌缩略图|
|subBrandList|List<BrandInfo|否|二级品牌|

#### VehicleCarInfo
| 字段 | 类型|是否有值|描述|
| --- | --- |----|----|
|vehicle|Vehicle|是|车辆信息|
|partNameListAsList|List<String|否|该车常用TopN的配件|

#### Vehicle
| 字段 | 类型|是否有值|描述|
| --- | --- |----|----|
|vinCode|String|是|车架号（17位）|
|brandClass|String|是|品牌（英文）|
|maker|String|是|厂商（进口宝马）|
|transmission|String|是|变速箱（8AT）|
|displacement|String|是|排量（2.0T）|
|year|String|是|year|年款（2014）|
|vehicleChn|String|否|车型中文名（5系）|
|vehicleEn|String|否|车型英文名（528I  (EUR)）|
|body|String|是|车体信息（三厢4门）|
|t4sSaleModelName|String|否| 4s店销售车型名称|
|optionInfo|String|否| 车型配置信息|
|optionCode| String|是| 车型配置编号|
|gyroBrand|String|是|陀螺仪品牌|
|carCountry|String|是|国别（德系）|
|carGrade|String|是|大中型|
|brand|String|是|品牌（中文）|
|brandId|String|是|品牌Id|
|prefix|String|是|品牌前缀（Bmw）|
|minPrice|String|否|最低售价|
|maxPrice|String|否|最高售价|
|priceZone|String|否|价格区间，单位万（50≤X＜60）|
|carGrade|String|否||