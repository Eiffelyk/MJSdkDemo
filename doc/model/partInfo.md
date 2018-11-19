#### PartInfo
|参数名称| 类型|是否有值| 描述|
| --- | --- |----|----|
|partId       |   String    |是      | 明觉设置的配件唯一ID|
|standardPartName |  String |是  |   配件明觉标准名|
|srcPartName  |   String  |是       |  EPC原厂名称|
|partPrice                  |   String  |是        |    配件价格|
|partNumber            | String   |是      |  配件oe号|
|substitute              |     String   |是       |  配件替换号|
|image                       | String   |是       | 配件名称|
|partRefOnImage       |   String |是      |  配件在图中的编号|
|qty                          |  String|是  |此配件在车上的数量|
|comment                |  String|是 |EPC中的描述|
|srcPartNumber       |   String  |是      |EPC原厂OE号，可能有-等分割字符|
|reqPartNumber      |  String   |是     |    oe反查时入参OE|
|thumbnailImage  |   String  |否       |  缩略图|
|reqPartPrice       |   String    |否       | oe翻查的时入参OE价格|
|originalName       |   String    |否       | 二次查询原名称|
|position       |   String    |否       | 装配图中的位置|
|rank       |   Integer    |否       | |
|parent       |   String    |否       | 局部总成关系中的 总|
|child       |   String    |否       | 局部总成关系中的 部|
|partType       |   String    |否      | 配件类型（normal：标准件、custom：自定义配件、little：小件）|
|operationList       |   List<OperationInfo    |否       | 可操作项目列表|
|brand|	String	|否	|没有VIN时候返回，适用品牌|
|brandClass	|String	|否	|没有VIN时候返回，品牌英文品牌|
|vehicle	|String	|否	|没有VIN时候返回，适用车型|
|year|	String|	否	|没有VIN时候返回，年款|
|matchWay	|String	|否|	没有VIN时候返回，匹配方式|


#### OperationInfo
|参数名称| 类型| 是否有值 | 描述|
| --- | ---- |----------|-----|
|showText	|String	|否|工项展示字段（例如:换、喷、拆、修、拆附件）|
|operation	|String|是|	与中台交互的实际工项（例如:更换、维修、拆装、辅料、附件拆装、外修）|
|choosable	|boolean|是|是否可选（如果不可选，在App中置灰）|
|chooseFlag	|boolean|是|	是否选中（如果为true，在App端该工项默认选中状态）|
|comment|	String|	否|备注信息，是通过一言为定选中的、还是通过推荐选中的|