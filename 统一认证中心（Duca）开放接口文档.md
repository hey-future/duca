# 文档说明



| 域名                                    | 描述 |
|---------------------------------------|  ---- |
| https://auth.crtvup.com.cn/           | 正式统一认证中心地址 |
| https://ducafront.multimediapress.cn/ |  开发环境统一认证中心地址 |



# 统一认证开放接口

> 本文档描述了统一认证中心（Duca）对外开放的 REST API，涵盖用户管理、部门组织和快捷登录等功能，供第三方业务系统接入使用。

## 接口总览

| 模块 | 接口 | 方法 | URL | 所需scope |
|------|------|------|-----|-----------|
| 用户 | 获取用户列表（AND） | POST | /duca/open/user/list/page/{page}/pageSize/{pageSize} | user:list 或 user |
| 用户 | 获取用户列表（OR） | POST | /duca/open/user/lists/page/{page}/pageSize/{pageSize} | user:list 或 user |
| 用户 | 根据用户名批量查询 | POST | /duca/open/user/listByUserName | user:list 或 user |
| 用户 | 用户添加 | POST | /duca/open/user/add | user:add 或 user |
| 用户 | 用户编辑 | PUT | /duca/open/user/{uuid} | user:update 或 user |
| 用户 | 用户删除 | DELETE | /duca/open/user/{uuid} | user:del 或 user |
| 用户 | 用户信息 | GET | /duca/open/user/{uuid} | user:view 或 user |
| 用户 | 批量获取用户信息 | POST | /duca/open/user/find | user:view 或 user |
| 用户 | 禁止或允许用户登录 | POST | /duca/open/user/action | user:update 或 user |
| 用户 | 调整用户所属部门 | POST | /duca/open/user/mvGroup | user:move 或 user |
| 用户 | 修改当前登录用户密码 | POST | /duca/open/user/passwd | user:passwd 或 user |
| 用户 | 发送修改密码验证码 | GET | /duca/open/user/sendCode | user:passwd 或 user |
| 用户 | 通过验证码修改密码 | POST | /duca/open/user/modifyPasswd | user:passwd 或 user |
| 用户 | 发送更换手机号验证码 | GET | /duca/open/user/phone/sendCode | user:phone 或 user |
| 用户 | 通过验证码更换手机号 | POST | /duca/open/user/phone/update | user:phone 或 user |
| 部门 | 获取分组列表 | GET | /duca/open/group/list | group:list 或 group |
| 部门 | 获取分组详细信息 | GET | /duca/open/group/{id} | group:view 或 group |
| 部门 | 添加分组 | POST | /duca/open/group/add | group:add 或 group |
| 部门 | 修改分组 | PUT | /duca/open/group/{id} | group:update 或 group |
| 部门 | 删除分组 | DELETE | /duca/open/group/{id} | group:del 或 group |

## 用户


### 获取用户列表（用户名和昵称同时包含）

**接口描述**

> 分页查询用户列表，支持按昵称、用户名、手机号、邮箱筛选。当同时传入 name 和 userName 时，返回同时满足两个条件的用户记录。

**scope需要包含user:list或user**

**接口URL**

> /duca/open/user/list/page/{page}/pageSize/{pageSize}

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| page | 1 | string | 是 | 当前页数 |
| pageSize | 20 | string | 是 | 每页数量 |

**请求Body参数**

```javascript
{
    "name":"姓名",
    "userName":"",
    "phone":"",
    "email":""
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| name | - | string | 否 | 昵称,非必传 |
| userName | - | string | 否 | 用户名,非必传，name和userName都传时，会查询出同时符合这2个条件的记录 |
| phone | - | string | 否 | 手机号，非必传 |
| email | - | string | 否 | 邮箱，非必传 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": {
        "isFirst": true,
        "total": 5,
        "pages": 1,
        "data": [
            {
                "userName": "whey2",
                "name": "whey2",
                "uuid": "HTPmWmSmSr0YiMyWaBb5o8mEF3Omeq73ozYmkGUz5S",
                "status": 1,
                "createDate": 1717153369,
                "modifyDate": 1717153444,
                "fromApp": null,
                "avatar": "",
                "sex": 1,
                "bornDate": null
            }
        ],
        "isLast": true,
        "prePage": 0,
        "nextPage": 0,
        "pageSize": 20,
        "page": 1
    }
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | object | 响应数据 |
| data.isFirst | true | boolean | 是否是第一页 |
| data.total | 5 | number | 总记录数 |
| data.pages | 1 | number | 总页数 |
| data.data | - | array | - |
| data.data.userName | whey2 | string | 账号 |
| data.data.name | whey2 | string | 昵称 |
| data.data.uuid | HTPmWmSmSr0YiMyWaBb5o8mEF3Omeq73ozYmkGUz5S | string | 唯一 |
| data.data.status | 1 | number | 1正常2密码输入错误账号锁定3禁止登录 |
| data.data.createDate | 1717153369 | number | 创建日期，秒的时间戳 |
| data.data.modifyDate | 1717153444 | number | 修改日期，秒的时间戳 |
| data.data.fromApp | - | string | 通过哪个客户端注册的 |
| data.data.avatar | - | string | 头像 |
| data.data.sex | 1 | number | 1男2女 |
| data.data.bornDate | - | number | 出生日期，秒的时间戳 |
| data.isLast | true | boolean | 是否是最后一页 |
| data.prePage | 0 | number | 前一页编号 |
| data.nextPage | 0 | number | 后一页编号 |
| data.pageSize | 20 | number | 每页数量 |
| data.page | 1 | number | 当前页数 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 获取用户列表 (用户名或昵称包含任意一个)

**接口描述**

> 分页查询用户列表，支持按昵称、用户名、手机号、邮箱筛选。当同时传入 name 和 userName 时，返回满足任一条件的用户记录（OR 逻辑）。

**scope需要包含user:list或user**

**接口URL**

> /duca/open/user/lists/page/{page}/pageSize/{pageSize}

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| page | 1 | string | 是 | 当前页数 |
| pageSize | 20 | string | 是 | 每页数量 |

**请求Body参数**

```javascript
{
    "name":"311",
    "userName":"388",
    "phone":"",
    "email":""
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| name | 姓名 | string | 否 | 昵称,非必传 |
| userName | - | string | 否 | 用户名,非必传，name和userName都传时，会查询出符合任意条件的记录 |
| phone | - | string | 否 | 手机号，非必传 |
| email | - | string | 否 | 邮箱，非必传 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": {
        "isFirst": true,
        "total": 5,
        "pages": 1,
        "data": [
            {
                "userName": "dingdian",
                "name": "丁典",
                "uuid": "YqrfmsTEbNq7KDHRo8wDErh4wgri49MFeEJp33GRvZ",
                "status": 1,
                "createDate": 1715312816,
                "modifyDate": 0,
                "fromApp": null,
                "avatar": "",
                "sex": 1,
                "bornDate": null
            } 
        ],
        "isLast": true,
        "prePage": 0,
        "nextPage": 0,
        "pageSize": 20,
        "page": 1
    }
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | object | 响应数据 |
| data.isFirst | true | boolean | 是否是第一页 |
| data.total | 5 | number | 总记录数 |
| data.pages | 1 | number | 总页数 |
| data.data | - | array | - |
| data.data.userName | whey2 | string | 账号 |
| data.data.name | whey2 | string | 昵称 |
| data.data.uuid | HTPmWmSmSr0YiMyWaBb5o8mEF3Omeq73ozYmkGUz5S | string | 唯一 |
| data.data.status | 1 | number | 1正常2密码输入错误账号锁定3禁止登录 |
| data.data.createDate | 1717153369 | number | 创建日期，秒的时间戳 |
| data.data.modifyDate | 1717153444 | number | 修改日期，秒的时间戳 |
| data.data.fromApp | - | string | 通过哪个客户端注册的 |
| data.data.avatar | - | string | 头像 |
| data.data.sex | 1 | number | 1男2女 |
| data.data.bornDate | - | number | 出生日期，秒的时间戳 |
| data.isLast | true | boolean | 是否是最后一页 |
| data.prePage | 0 | number | 前一页编号 |
| data.nextPage | 0 | number | 后一页编号 |
| data.pageSize | 20 | number | 每页数量 |
| data.page | 1 | number | 当前页数 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 根据用户名批量查询（一次最多50个）

**接口描述**

> 根据用户名列表批量查询用户信息，一次最多查询 50 个。请求 Body 为 JSON 数组，传入待查询的 userName 列表即可。

**scope需要包含user:list或user**

> 一次最多查询 50 个用户名。

**接口URL**

> /duca/open/user/listByUserName

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
[
    "tianfei","wangmanna","xiaoming "
]
```


**响应示例**

* 成功(200)

```javascript
{"code":200,"msg":"成功","data":[{"userName":"tianfei","name":"田飞","uuid":"sYU7avbvVyuTbw36K4ZbbxKGJwMj5RYLPSNe7ws6mE2enIyoiaV9p1Q2NtKk3JU","status":1,"createDate":1717055300,"modifyDate":1775043161,"fromApp":null,"avatar":"/duca/static/avatar/06b79ab3-1fe9-4db9-85c6-546494c069e5.png","sex":1,"bornDate":null}]}
```

| 参数名 | 示例值 | 参数类型   | 参数描述 |
| --- | --- |--------| ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | object | 响应数据 |
| data.userName | tianfei | string | 账号 |
| data.name | 田飞 | string | 昵称 |
| data.uuid | sYU7avbvVyuTbw36K4Zbbx7FRVselNseIf7QoqRH9a | string | - |
| data.status | 1 | number | - |
| data.createDate | 1768963484 | number | 创建日期，秒的时间戳 |
| data.modifyDate | 1775008739 | number | 修改日期，秒的时间戳 |
| data.fromApp | - | String | 通过哪个客户端注册的 |
| data.avatar | /duca/static/avatar/1519e05c-b234-44c5-9bb7-19176e296f03.jpg | string | 头像 |
| data.sex | 1 | number | - |
| data.bornDate | - | number   | 出生日期，秒的时间戳 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 用户添加

**接口描述**

> 创建新用户。支持设置昵称、用户名、密码（默认123456）、头像、部门、性别、邮箱、手机号、出生日期、账号到期时间等。

**scope需要包含user:add或user**


**接口URL**

> /duca/open/user/add

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
{
    "name": "duca",
    "userName": "duca",
    "password": "123456"
         
}

```
完整参数说明

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| name | - | string | 是 | 昵称 |
| userName | - | string | 是 | 用户名 |
| password | - | string | 否 | 密码,默认123456 |
| avatar | - | string | 否 | 头像url |
| groupId | 1 | number | 否 | 部门编号 |
| sex | 1 | number | 否 | 性别1男2女 |
| email | - | string | 否 | 邮箱 |
| phone | - | string | 否 | 手机号 |
| fromApp | - | string | 否 | 客户端标识，可以传clientId |
| bornDate | 1 | number | 否 | 出生日期 秒的时间戳 |
| expireDate | - | number | 否 | 账号到期时间，秒的时间戳 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "",
    "data": {
        "id": 1,
        "userName": "duca",
        "name": "duca",
        "uuid": "ZDTgVV7BDqBzAN8VfT4MDFa0JcPWpBTceKdbpSePI2",
        "avatar": "/duca/static/avatar/06b79ab3-1fe9-4db9-85c6-546494c069e5.png",
        "groupId": 1,
        "sex": 1,
        "email": "",
        "phone": "",
        "fromApp": "",
        "bornDate": null,
        "expireDate": 0,
        "status": 1
    }
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 1 | integer | 业务状态码，1表示成功 |
| msg | - | string | 提示信息 |
| data | - | object | 返回创建的用户信息 |
| data.id | 1 | number | 用户ID |
| data.userName | duca | string | 账号 |
| data.name | duca | string | 昵称 |
| data.uuid | ZDTgVV7BDqBzAN8VfT4MDFa0JcPWpBTceKdbpSePI2 | string | 用户唯一标识 |
| data.avatar | /duca/static/avatar/06b79ab3-1fe9-4db9-85c6-546494c069e5.png | string | 头像 |
| data.groupId | 1 | number | 部门编号 |
| data.sex | 1 | number | 性别，1男2女 |
| data.email | - | string | 邮箱 |
| data.phone | - | string | 手机号 |
| data.fromApp | - | string | 通过哪个客户端注册的 |
| data.bornDate | - | number | 出生日期，秒的时间戳 |
| data.expireDate | 0 | number | 账号到期时间，秒的时间戳 |
| data.status | 1 | number | 1正常2账号到期3禁止登录 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 用户编辑

**接口描述**

> 修改指定 UUID 用户的信息，支持更新昵称、密码、头像、部门、性别、邮箱、手机号、出生日期、账号到期时间、直属领导等字段，按需传入即可。

**scope需要包含user:update或user**


**接口URL**

> /duca/open/user/{uuid}

**请求方式**

> PUT

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Content-Type | application/json | string | 是 | - |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| uuid | ZfxHcYF6O2EnholEtPeBqsPY5hiFaTkwdv56n80x0A | string | 是 | - |

**请求Body参数**

```javascript
{
    "uuid": "ZDTgVV7BDqBzAN8VfT4MDFa0JcPWpBTceKdbpSePI2",
    "name": "duca2",
    "leaderUuid": "ed0e4b27-0ff8-425e-84f7-424c90af71f3"
}
```
完整参数

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| uuid | ZDTgVV7BDqBzAN8VfT4MDFa0JcPWpBTceKdbpSePI2 | string | 是 | 唯一 必须 |
| name | duca | string | 否 | 昵称 |
| password | 123456 | string | 否 | 密码,默认123456 |
| avatar | - | string | 否 | 头像url |
| groupId | 1 | number | 否 | 部门编号 |
| sex | 1 | number | 否 | 1男2女 |
| email | - | string | 否 | 邮箱 |
| phone | - | string | 否 | 手机号 |
| fromApp | - | string | 否 | 客户端标识，可以传clientId |
| bornDate | 1 | number | 否 | 出生日期 秒的时间戳 |
| expireDate | 1 | number | 否 | 账号到期时间，秒的时间戳 |
| leaderUuid | ed0e4b27-0ff8-425e-84f7-424c90af71f3 | string | 否 | 直属领导用户 UUID |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": null
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| msg | 成功 | string | 提示信息 |
| code | 200 | number | 业务状态码，200表示成功 |

* 失败(404)

```javascript
{
    "msg": "Unauthorized",
    "code": 401
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| msg | Unauthorized | string | 提示信息 |
| code | 401 | number | 业务状态码，200表示成功 |

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Content-Type | application/json | string | 是 | - |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


### 用户删除

**接口描述**

> 根据 UUID 删除指定用户。

**scope需要包含user:del或user**


**接口URL**

> /duca/open/user/{uuid}

**请求方式**

> DELETE

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| uuid | HTPmWmSmSr0YiMyWaBb5o8mEF3Omeq73ozYmkGUz5S | string | 是 | - |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": null
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | null | - |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 用户信息

**接口描述**

> 根据 UUID 查询单个用户的详细信息，包括基本资料、角色列表、部门信息、登录记录等。

**scope需要包含user:view或user**


**接口URL**

> /duca/open/user/{uuid}

**请求方式**

> GET

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| uuid | ed0e4b27-0ff8-425e-84f7-424c90af71f3 | string | 是 | - |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": {
        "cookie": false,
        "id": 1,
        "password": "$2a$10$4d.4F5irXtCPcJhzRwPIJu7Mc7DVjS2NClfRtMP6bX5JgcVC2Eil6",
        "userName": "hey",
        "status": 1,
        "expireDate": 0,
        "name": "hey",
        "avatar": "/duca/static/avatar/7abd0001-b0c5-4982-9b77-e8e3454f3476.jpeg",
        "sex": 0,
        "email": "hey@dayu-cloud.com",
        "phone": "18595808972",
        "code": null,
        "token": null,
        "position": "",
        "openId": null,
        "unionId": null,
        "lastLoginDate": 1751872751,
        "lastLoginIp": "221.217.141.183",
        "loginCount": 2244,
        "createDate": 0,
        "modifyDate": 1670500854,
        "uuid": "ed0e4b27-0ff8-425e-84f7-424c90af71f3",
        "groupId": 0,
        "allowPassword": null,
        "passwdDate": 1663919491,
        "restPassword": null,
        "ptype": null,
        "station": null,
        "state": null,
        "workNo": null,
        "idType": null,
        "idNo": null,
        "bornDate": null,
        "political": null,
        "offical": null,
        "telphone": null,
        "extension": null,
        "leader": null,
        "leaderName": null,
        "allowSyncWorkWx": null,
        "roles": [
            {
                "id": 1,
                "name": "系统管理员",
                "remark": "",
                "createDate": 0,
                "modifyDate": 1661788166,
                "projectId": 0,
                "preset": 1,
                "code": "admin",
                "menu": null
            }
        ],
        "groups": [],
        "grant": null,
        "task": null,
        "type": 0
    }
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | --- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | object | 响应数据 |
| data.cookie | false | boolean | 保持登录 |
| data.id | 1 | number | 用户ID |
| data.password | $2a$10$4d.4F5irXtCPcJhzRwPIJu7Mc7DVjS2NClfRtMP6bX5JgcVC2Eil6 | string | 密码,默认123456 |
| data.userName | hey | string | 账号 |
| data.status | 1 | number | 1正常2账号到期3禁止登录 |
| data.expireDate | 0 | number | 账号到期时间，秒的时间戳 |
| data.name | hey | string | 昵称 |
| data.avatar | /duca/static/avatar/7abd0001-b0c5-4982-9b77-e8e3454f3476.jpeg | string | 头像 |
| data.sex | 0 | number | 1男2女 |
| data.email | hey@dayu-cloud.com | string | 邮箱 |
| data.phone | 18595808972 | string | 手机号 |
| data.code | - | string |  |
| data.token | - | string | sessionId |
| data.salt | WS/W+YE3vH4OBwDuTbgV7g== | string | - |
| data.position | - | string | 职务 |
| data.openId | - | string | 微信OpenId |
| data.unionId | - | string | 微信UnionId |
| data.lastLoginDate | 1751872751 | number | 最后登录时间，秒的时间戳 |
| data.lastLoginIp | 221.217.141.183 | string | 最后登录IP |
| data.loginCount | 2244 | number | 登录次数 |
| data.createDate | 0 | number | 创建日期，秒的时间戳 |
| data.modifyDate | 1670500854 | number | 修改日期，秒的时间戳 |
| data.uuid | ed0e4b27-0ff8-425e-84f7-424c90af71f3 | string | 用户唯一标识 |
| data.groupId | 0 | number | 部门编号 |
| data.allowPassword | - | boolean | 是否需要强制修改密码 |
| data.passwdDate | 1663919491 | number | 密码生成日期，秒的时间戳 |
| data.workwx_userid | hey | string | 企业微信用户编号 |
| data.restPassword | - | number | 1需要强制修改密码0非强制修改密码 |
| data.ptype | - | number | 职务类别，1集团领导2原集团领导3中层干部4职员 |
| data.station | - | string | 岗位名称 |
| data.state | - | number | 在岗状态，1在职2试用期3退休4内退5长期病休6长期工伤7离职 |
| data.workNo | - | string | 工号 |
| data.idType | - | number | 证件类型，1身份证2港澳通行证3台湾通行证4护照5军官证6其他 |
| data.idNo | - | string | 证件号码 |
| data.bornDate | - | number | 出生日期，秒的时间戳 |
| data.political | - | number | 政治面貌，1中共党员2中共预备党员3共青团员4民盟盟员5民建会员6民进会员7农工党党员8致公党党员9九三学社社员10台盟盟员11无党派民主人士12民革会员99群众 |
| data.offical | - | string | 所属办公室 |
| data.telphone | - | string | 办公电话 |
| data.extension | - | string | 分机 |
| data.leader | - | number | 直属上级用户ID |
| data.leaderName | - | string | 上级名称 |
| data.allowSyncWorkWx | - | number | 是否同步到企业微信，1-是，0-否 |
| data.roles | - | array | 角色列表 |
| data.roles.id | 1 | number | 角色ID |
| data.roles.name | 系统管理员 | string | 角色名称 |
| data.roles.remark | - | string | 角色备注 |
| data.roles.createDate | 0 | number | 创建日期，秒的时间戳 |
| data.roles.modifyDate | 1661788166 | number | 修改日期，秒的时间戳 |
| data.roles.projectId | 0 | number | 项目编号 |
| data.roles.preset | 1 | number | 是否预设角色，1-是 |
| data.roles.code | admin | string | 角色编码 |
| data.roles.menu | - | string | - |
| data.groups | - | array | 所属部门列表 |
| data.grant | - | array | 授权应用列表 |
| data.task | - | array | 数据同步应用编号列表 |
| data.type | 0 | number | 用户类型，0管理用户，1C端用户 |
| data.thirdUserId | - | string | 第三方用户标识 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 批量获取用户信息

**接口描述**

> 根据 UUID 列表批量查询多个用户的详细信息。请求 Body 为 JSON 数组，传入待查询的用户 UUID 列表。

**scope需要包含user:view或user**

> 一次最多查询 50 个用户。

**接口URL**

> /duca/open/user/find

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
[
    "cq8yoMaw2y2RDqINEQ5UmTD58vQRCf2I2U8A6jlKD4",
    "8k11WpgJ6nnTPe3nyKifC0rSuXTsaCdeo6BXfjoUUE"
]
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| 0 | cq8yoMaw2y2RDqINEQ5UmTD58vQRCf2I2U8A6jlKD4 | string | 是 | - |
| 1 | 8k11WpgJ6nnTPe3nyKifC0rSuXTsaCdeo6BXfjoUUE | string | 是 | - |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": [
        {
            "cookie": false,
            "id": 1200,
            "password": "ee272291022d6c9c4bf8e2484f933e24",
            "userName": "dingdian",
            "status": 1,
            "expireDate": 0,
            "name": "丁典",
            "avatar": "",
            "sex": 1,
            "email": "",
            "phone": "15392630451",
            "code": null,
            "token": null,
            "salt": "WS/W+YE3vH4OBwDuTbgV7g==",
            "position": "",
            "openId": null,
            "unionId": null,
            "lastLoginDate": 1719800509,
            "lastLoginIp": "123.121.199.225",
            "loginCount": 25,
            "createDate": 1715312816,
            "modifyDate": 0,
            "uuid": "YqrfmsTEbNq7KDHRo8wDErh4wgri49MFeEJp33GRvZ",
            "projectId": null,
            "groupId": 1,
            "allowPassword": null,
            "passwdDate": null,
            "workwx_userid": "duca_Ny9zP",
            "restPassword": null,
            "ptype": 4,
            "station": "",
            "state": 1,
            "workNo": "",
            "idType": 1,
            "idNo": "",
            "bornDate": null,
            "political": 99,
            "offical": "",
            "telphone": "",
            "extension": "",
            "leader": null,
            "leaderName": null,
            "allowSyncWorkWx": null,
            "roles": [
                {
                    "id": 1,
                    "name": "系统管理员",
                    "remark": "",
                    "createDate": 0,
                    "modifyDate": 1661788166,
                    "projectId": 0,
                    "preset": 1,
                    "code": "admin",
                    "menu": null
                }
            ],
            "groups": [
                {
                    "id": 1,
                    "parent": 0,
                    "name": "辽阳烟草",
                    "icon": null,
                    "spaceOn": null,
                    "size": null,
                    "spaceFolder": null,
                    "inGroup": true,
                    "workwxId": null,
                    "code": "iBGDVZWALpSkFrWU0Kc1dS6o9s1x9jQxd9Mc8Xhw4h",
                    "type": 1,
                    "userId": null,
                    "leader": null,
                    "estabDate": 1714924800,
                    "gptConfig": null,
                    "child": null
                }
            ],
            "grant": null,
            "task": null,
            "type": 0
        }
    ]
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 1 | integer | - |
| msg | - | string | 提示信息 |
| data | - | object | 响应数据 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 禁止或允许用户登录

**接口描述**

> 控制用户的登录权限，可禁止或恢复指定用户的登录能力。

**scope需要包含user:update或user**


**接口URL**

> /duca/open/user/action

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
{
    "uuid": "ZfxHcYF6O2EnholEtPeBqsPY5hiFaTkwdv56n80x0A",
    "action": 1
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| uuid | ZfxHcYF6O2EnholEtPeBqsPY5hiFaTkwdv56n80x0A | string | 是 | 用户唯一标识 |
| action | 1 | integer | 是 | 1-允许登录，3-禁止登录 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": null
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | null | - |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 调整某个用户的所属部门

**接口描述**

> 将指定用户移动到目标部门。传入用户 UUID 和目标部门 ID 即可完成部门迁移。

**scope需要包含user:move或user**


**接口URL**

> /duca/open/user/mvGroup

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
{
    "uuid": "ZfxHcYF6O2EnholEtPeBqsPY5hiFaTkwdv56n80x0A",
    "groupId": 1
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| uuid | ZfxHcYF6O2EnholEtPeBqsPY5hiFaTkwdv56n80x0A | string | 是 | 用户唯一标识 |
| groupId | 12 | number | 是 | 目标部门编号 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "成功",
    "data": null
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | null | - |

* 失败(404)

```javascript
{
    "code": 403,
    "msg": "没有访问权限",
    "data": null
}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 修改当前登录用户密码(scope=user:passwd或者scope=user)

**接口描述**

> 修改当前登录用户的密码。需提供旧密码进行验证，验证通过后设置为新密码。


**接口URL**

> /duca/open/user/passwd

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
{
    "oldPassword":"hey@20222",
    "newPassword":"hey@2022"
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| oldPassword | 123@qwe | string | 是 | 旧密码 |
| newPassword | WER34@E | string | 是 | 新密码 |


**响应示例**

* 成功(200)

```javascript
暂无数据
```

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |


### 修改密码 发送验证码(scope=user:passwd或者scope=user)

**接口描述**

> 向当前登录用户绑定的手机号发送短信验证码，用于后续通过验证码修改密码。


**接口URL**

> /duca/open/user/sendCode

**请求方式**

> GET

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


**响应示例**

* 成功(200)

```javascript
暂无数据
```

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


### 修改当前登录用户密码修改密码 通过验证码(scope=user:passwd或者scope=user)

**接口描述**

> 通过手机验证码修改当前登录用户的密码。需先调用发送验证码接口获取验证码，再传入验证码和新密码完成修改。


**接口URL**

> /duca/open/user/modifyPasswd

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**请求Body参数**

```javascript
{
    "code":"676845",
    "newPassword":"ADESD@20220"
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| code | 676845 | string | 是 | 手机验证码 |
| newPassword | ADESD@20220 | string | 是 | 新密码 |


**响应示例**

* 成功(200)

```javascript
暂无数据
```

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


### 更换手机号 发送验证码(scope=user:phone或者scope=user)

**接口描述**

> 向指定手机号发送短信验证码，用于后续更换绑定手机号。可传入手机号参数，为空则自动使用注册手机号。


**接口URL**

> /duca/open/user/phone/sendCode?phone=15210693684

**请求方式**

> GET

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| phone | 15210693684 | string | 否 | 注册手机号，如果phone不为空，要保证手机号格式正确，且与注册手机号一致；如果phone为空会自动获取注册时手机号 |


**响应示例**

* 成功(200)

```javascript
{"code":200,"msg":"成功","data":null}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | - | null | - |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


### 更换当前登录用户手机号 通过验证码(scope=user:phone或者scope=user)

**接口描述**

> 通过手机验证码更换当前登录用户的绑定手机号。需先调用发送验证码接口获取验证码，再传入原手机号、验证码和新手机号完成更换。


**接口URL**

> /duca/open/user/phone/update

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**请求Body参数**

```javascript
{
    "phone":"18595808972",
    "code":"364976",
    "newPhone":"17004952032"
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| phone | - | string | 是 | 注册手机号 |
| code | 4567 | string | 是 | 手机验证码 |
| newPhone | - | string | 是 | 新手机号 |


**响应示例**

* 成功(200)

```javascript
{"code":200,"msg":"成功","data":"手机号更换成功"}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 200 | number | 业务状态码，200表示成功 |
| msg | 成功 | string | 提示信息 |
| data | 手机号更换成功 | string | - |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


## 部门

### 获取分组列表

**接口描述**

> 查询部门（分组）树形列表，支持按 projectId、groupId、userId 等条件组合筛选，提供多种 URL 模式灵活调用。

**scope需要包含group:list或group**


**接口URL**

> /duca/open/group/list
> /duca/open/group/list/groupId/{groupId}

**请求方式**

> GET

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**路径变量**

> 以上 URL 中的路径变量均为可选，按需选择对应 URL 即可。

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| groupId | 1 | integer | 否 | 部门编号，筛选指定部门及其子部门 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "SUCCESS",
    "data": [
        {
            "id": 1,
            "parent": 0,
            "name": "辽阳烟草",
            "code": "iBGDVZWALpSkFrWU0Kc1dS6o9s1x9jQxd9Mc8Xhw4h",
            "type": 1,
            "userId": null,
            "leader": null,
            "estabDate": 1714924800,
            "child": [
                {
                    "id": 2,
                    "parent": 1,
                    "name": "技术部",
                    "code": "tech",
                    "type": 2,
                    "userId": null,
                    "leader": null,
                    "estabDate": 1714924800,
                    "child": []
                }
            ]
        }
    ]
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 1 | number | 业务状态码，1表示成功 |
| msg | SUCCESS | string | 提示信息 |
| data | - | array | 部门树形列表 |
| data[].id | 1 | number | 部门ID |
| data[].parent | 0 | number | 上级部门ID |
| data[].name | 辽阳烟草 | string | 部门名称 |
| data[].code | iBGDVZWALpSkFrWU0Kc1dS6o9s1x9jQxd9Mc8Xhw4h | string | 部门编码，唯一 |
| data[].type | 1 | number | 部门类型，1公司2部门 |
| data[].userId | - | number | 部门负责人用户ID |
| data[].leader | - | string | 部门负责人名称 |
| data[].estabDate | 1714924800 | number | 部门成立时间，秒的时间戳 |
| data[].child | - | array | 子部门列表，结构同父级，递归嵌套 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### 获取分组详细信息

**接口描述**

> 根据分组 ID 查询单个部门的详细信息。

**scope需要包含group:view或group**


**接口URL**

> /duca/open/group/{id}

**请求方式**

> GET

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| id | 1 | string | 是 | - |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "SUCCESS",
    "data": {
        "id": 1,
        "name": "辽阳烟草",
        "parent": 0,
        "hasChild": 1,
        "code": "iBGDVZWALpSkFrWU0Kc1dS6o9s1x9jQxd9Mc8Xhw4h",
        "type": 1,
        "userId": null,
        "estabDate": 1714924800,
        "level": 1,
        "path": "辽阳烟草/",
    }
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 1 | number | 业务状态码，1表示成功 |
| msg | SUCCESS | string | 提示信息 |
| data | - | object | 响应数据 |
| data.id | 1 | number | 部门ID |
| data.name | 辽阳烟草 | string | 部门名称 |
| data.parent | 0 | number | 上级部门ID |
| data.hasChild | 1 | number | 是否有子级，0无子级1有子级 |
| data.code | iBGDVZWALpSkFrWU0Kc1dS6o9s1x9jQxd9Mc8Xhw4h | string | 部门编码，唯一 |
| data.type | 1 | number | 部门类型，1公司2部门 |
| data.userId | - | number | 部门负责人用户ID |
| data.estabDate | 1714924800 | number | 部门成立时间，秒的时间戳 |
| data.level | 1 | number | 部门等级，1一级2二级3三级 |
| data.path | 辽阳烟草/ | string | 部门路径 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |


### 添加分组

**接口描述**

> 创建新部门（分组），支持设置部门名称、上级部门、部门类型（公司/部门）、负责人 UUID、成立时间、是否同步企业微信等。

**scope需要包含group:add或group**


**接口URL**

> /duca/open/group/add

**请求方式**

> POST

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**请求Body参数**

```javascript
{
    "name": "",
    "parentId": 1,
    "code": "",
    "type": 1,
    "uuid": "",
    "estabDate": 1
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- |------| ---- |
| name | - | string | 是    | 部门名称 |
| parentId | 1 | integer | 否    | 上级部门编号 |
| code | - | string | 否    | - |
| type | 1 | integer | 否    | 部门类型1公司2部门 |
| uuid | - | string | 否    | 部门负责人UUID |
| estabDate | 1 | integer | 否    | 部门成立时间 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": "",
    "data": 1
}
```

| 参数名 | 示例值 | 参数类型    | 参数描述 |
| --- | --- |---------|------|
| code | 1 | integer | -    |
| msg | - | string  | 提示信息 |
| data | - | int     | 部门编号 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### 修改分组

**接口描述**

> 修改指定分组 ID 的部门信息，支持更新部门名称、上级部门、部门类型、负责人、成立时间等字段。

**scope需要包含group:update或group**


**接口URL**

> /duca/open/group/{id}

**请求方式**

> PUT

**Content-Type**

> application/json

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |
| Content-Type | application/json | string | 是 | - |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| id | - | integer | 是 | - |

**请求Body参数**

```javascript
{
    "id": 1,
    "name": "",
    "parentId": 1,
    "code": "",
    "type": 1,
    "uuid": "",
    "estabDate": 1
}
```

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| id | 1 | integer | 否 | - |
| name | - | string | 否 | 部门名称 |
| parentId | 1 | integer | 否 | 上级部门编号 |
| code | - | string | 否 | - |
| type | 1 | integer | 否 | 部门类型1公司2部门 |
| uuid | - | string | 否 | 部门负责人UUID |
| estabDate | 1 | integer | 否 | 部门成立时间 |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": ""
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| code | 1 | integer | - |
| msg | - | string | 提示信息 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### 删除分组

**接口描述**

> 根据分组 ID 删除指定部门。注意：部门下存在子部门或用户时无法删除。

**scope需要包含group:del或group**


**接口URL**

> /duca/open/group/{id}

**请求方式**

> DELETE

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 | 认证令牌/授权头 |

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| id | - | integer | 是 | - |


**响应示例**

* 成功(200)

```javascript
{
    "code": 200,
    "msg": ""
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- |-----| ---- | ---- |
| code | 200 | integer | - |
| msg | -   | string | 提示信息 |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

