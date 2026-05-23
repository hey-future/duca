# 文档说明



 | 域名                                    | 描述 |
|---------------------------------------|  ---- |
| https://auth.crtvup.com.cn/           | 正式统一认证中心地址 |
| https://ducafront.multimediapress.cn/ |  开发环境统一认证中心地址 |
  

 
# 统一认证

> 统一认证中心（Duca）是基于 OAuth 2.0 和 OpenID Connect 协议构建的企业级身份认证与授权服务平台。提供授权码模式、密码模式、客户端凭证模式、PKCE、DPoP、PAR、设备授权、Token Exchange 等标准授权流程，支持单点登录（SSO）、统一退出（RP-Initiated Logout）、令牌内省与吊销，以及手机号、微信、第三方用户ID、UUID 等自定义授权模式。

## 认证与授权

### 服务发现

**接口描述**

> OpenID Connect Discovery 服务发现端点。客户端通过此接口获取授权服务器的所有OAuth 2.0/OIDC端点、支持的算法及能力。遵循 [OpenID Connect Discovery 1.0](https://openid.net/specs/openid-connect-discovery-1_0.html) 规范。

**接口URL**

> /duca/.well-known/:client_id/openid-configurations

**请求方式**

> GET

**Content-Type**

> none

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | - | string | 是 | 客户端ID |



**响应示例**

* 成功(200)

```javascript
{
    "fw": "2",
    "issuer": "http://localhost:8188/duca/",
    "authorization_endpoint": "http://localhost:8188/duca/oauth2/authorize",
    "pushed_authorization_request_endpoint": "http://localhost:8188/duca/oauth2/par",
    "device_authorization_endpoint": "http://localhost:8188/duca/oauth2/device_authorization",
    "token_endpoint": "http://localhost:8188/duca/oauth2/token",
    "token_endpoint_auth_methods_supported": [
        "client_secret_post",
        "client_secret_basic"
    ],
    "jwks_uri": "http://localhost:8188/duca/oauth2/DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s/jwks",
    "userinfo_endpoint": "http://localhost:8188/duca/userinfo",
    "end_session_endpoint": "http://localhost:8188/duca/connect/logout",
    "registration_endpoint": "http://localhost:8188/duca/connect/register",
    "revocation_endpoint": "http://localhost:8188/duca/oauth2/revoke",
    "revocation_endpoint_auth_methods_supported": [
        "client_secret_post",
        "client_secret_basic"
    ],
    "introspection_endpoint": "http://localhost:8188/duca/oauth2/introspect",
    "introspection_endpoint_auth_methods_supported": [
        "client_secret_post",
        "client_secret_basic"
    ],
    "response_types_supported": [
        "code"
    ],
    "grant_types_supported": [
        "refresh_token",
        "client_credentials",
        "password",
        "authorization_code",
        "urn:ietf:params:oauth:grant-type:token-exchange"
    ],
    "code_challenge_methods_supported": [
        "S256"
    ],
    "tls_client_certificate_bound_access_tokens": true,
    "ddd": "11",
    "ddd1": "22",
    "dpop_signing_alg_values_supported": [
        "RS256",
        "RS384",
        "RS512",
        "PS256",
        "PS384",
        "PS512",
        "ES256",
        "ES384",
        "ES512"
    ],
    "subject_types_supported": [
        "public"
    ],
    "id_token_signing_alg_values_supported": [
        "RS256"
    ],
    "scopes_supported": [
        "user:view",
        "phone",
        "openid",
        "offline_access",
        "profile",
        "roles",
        "userName",
        "email"
    ]
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| issuer | http://localhost:8188/duca/ | string | 颁发者标识符，授权服务器根URL |
| authorization_endpoint | http://localhost:8188/duca/oauth2/authorize | string | OAuth 2.0授权端点URL |
| pushed_authorization_request_endpoint | http://localhost:8188/duca/oauth2/par | string | PAR推送授权请求端点(RFC 9126) |
| device_authorization_endpoint | http://localhost:8188/duca/oauth2/device_authorization | string | OAuth 2.0设备授权端点(RFC 8628) |
| token_endpoint | http://localhost:8188/duca/oauth2/token | string | OAuth 2.0令牌端点URL |
| token_endpoint_auth_methods_supported | - | array | 令牌端点支持的客户端认证方式 |
| jwks_uri | http://localhost:8188/duca/oauth2/DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s/jwks | string | JWKS公钥集端点URL |
| userinfo_endpoint | http://localhost:8188/duca/userinfo | string | OpenID Connect UserInfo端点URL |
| end_session_endpoint | http://localhost:8188/duca/connect/logout | string | OpenID Connect会话结束端点URL |
| registration_endpoint | http://localhost:8188/duca/connect/register | string | OIDC动态客户端注册端点URL |
| revocation_endpoint | http://localhost:8188/duca/oauth2/revoke | string | OAuth 2.0令牌吊销端点(RFC 7009) |
| revocation_endpoint_auth_methods_supported | - | array | 吊销端点支持的认证方式 |
| introspection_endpoint | http://localhost:8188/duca/oauth2/introspect | string | OAuth 2.0令牌内省端点(RFC 7662) |
| introspection_endpoint_auth_methods_supported | - | array | 内省端点支持的认证方式 |
| response_types_supported | - | array | 支持的OAuth 2.0响应类型(response_type) |
| grant_types_supported | - | array | 支持的OAuth 2.0授权类型(grant_type) |
| code_challenge_methods_supported | - | array | 支持的PKCE代码挑战方法 |
| tls_client_certificate_bound_access_tokens | true | boolean | 是否支持mTLS客户端证书绑定令牌 |
| dpop_signing_alg_values_supported | - | array | 支持的DPoP签名算法(RFC 9449) |
| subject_types_supported | - | array | 支持的OIDC主体类型(public/pairwise) |
| id_token_signing_alg_values_supported | - | array | ID Token支持的JWS签名算法 |
| scopes_supported | - | array | 支持的OAuth 2.0授权范围(scope) |

### JWKS公钥端点

**接口描述**

> JSON Web Key Set 端点，返回用于验证ID Token和Access Token签名的RSA公钥。客户端可使用此公钥验证JWT签名。遵循 [RFC 7517](https://tools.ietf.org/html/rfc7517)。


**接口URL**

> /duca/oauth2/:client_id/jwks

**请求方式**

> GET

**Content-Type**

> none

**路径变量**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 应用编号 |


**响应示例**

* 成功(200)

```javascript
{
    "keys": [
        {
            "kty": "RSA",
            "e": "AQAB",
            "kid": "394d4711-0f01-4d5c-9793-0411a78cc3e3",
            "n": "sBD_SJpoALo0EzjUiOWC5k5oVLkZBBs11EvUX7dViARJeq7ASvTD_0pQ13Pw31FQhz7nngzZHvm7tu1GCoqime8BaMUEKerhP0eRmkayRHkspGPk9fWVVMaw4gOSqWdP9BIazHDEFIgbTV-a0jQo9ZMm4XUDDiCsO6v_Ph6-H_jjE8tzht7ACJMbq8YjEqbJbwp1VqZ8BxrpzAQb84z02Rgy_GWpU_4gbZ0d4Rgwmkd-o9qJS4KXyg0iNNtGxvuZ-K6ZXM8hsoMn2DxJEmoe6XTv6zvYwkYP-kW8mFkTNJYzuxSLvf3DmR5bOlZECOS2CnnMLKf8tvyHo4CYC7JAIQ"
        }
    ]
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| keys | - | array | JWK密钥列表 |
| keys.kty | RSA | string | 密钥类型(Key Type)，如RSA、EC |
| keys.e | AQAB | string | RSA公钥指数(Exponent) |
| keys.kid | 394d4711-0f01-4d5c-9793-0411a78cc3e3 | string | 密钥ID(Key Identifier)，用于匹配JWT头部kid |
| keys.n | sBD_SJpoALo0EzjUiOWC5k5oVLkZBBs11EvUX7dViARJeq7ASvTD_0pQ13Pw31FQhz7nngzZHvm7tu1GCoqime8BaMUEKerhP0eRmkayRHkspGPk9fWVVMaw4gOSqWdP9BIazHDEFIgbTV-a0jQo9ZMm4XUDDiCsO6v_Ph6-H_jjE8tzht7ACJMbq8YjEqbJbwp1VqZ8BxrpzAQb84z02Rgy_GWpU_4gbZ0d4Rgwmkd-o9qJS4KXyg0iNNtGxvuZ-K6ZXM8hsoMn2DxJEmoe6XTv6zvYwkYP-kW8mFkTNJYzuxSLvf3DmR5bOlZECOS2CnnMLKf8tvyHo4CYC7JAIQ | string | RSA公钥模数(Modulus) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### auth-1 授权端点(浏览器访问)

**接口描述**

> OAuth 2.0授权码模式——第一步：浏览器重定向到此端点，用户登录并授权后，授权服务器将授权码通过redirect_uri回调给客户端。遵循 [RFC 6749 Section 4.1](https://tools.ietf.org/html/rfc6749#section-4.1)。


**接口URL**

> /duca/oauth2/authorize?response_type=code&client_id={{clientId}}&scope=openid&redirect_uri=http://localhost:8187/duca/callback

**请求方式**

> GET

**Content-Type**

> none

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| response_type | code | string | 是 | 响应类型，固定为code（授权码模式） |
| client_id | {{clientId}} | string | 是 | 客户端ID/应用编号 |
| scope | openid | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 回调地址 |


**响应示例**

* 成功(200)

```javascript
暂无数据
```

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### auth-2 Token端点(授权码模式) client_secret_post

**接口描述**

> OAuth 2.0授权码模式——第二步：用授权码换取access_token。客户端使用client_secret_post方式提交客户端凭证（client_id + client_secret放在Body中）。

**前提：先调用/duca/oauth/authorize接口获取授权码**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| grant_type | authorization_code | string | 是 | 授权类型，固定为authorization_code（授权码模式） |
| redirect_uri | {{redirect_uri}} | string | 是 | 回调地址 |
| code | A30jvrMrWpn7evvXMslmyttGEH-yqkUiUFQhhmO5VkPB_OYVUYUBK7I73hwb-HoGX2z2h9J5vIuWPDViZNqveqBIyen0_kHK9vhJQn11-KOe0fMYzfYNu3wtThDiiROc | string | 是 | 授权码 |


**响应示例**

* 成功(200)

```javascript
{
	"access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ",
	"refresh_token": "FuXYzlid3rbwuHqiEXNRtWeuM8U3pc4FD9CG-E2DXJA9xKv5n7T26bmjw5UBszV5UXfUn2sptSx0TBXE_hkNCg5ByoAs4DOHQDE39d7HLJe8MM3orinI4WnDeCBO23Np",
	"scope": "openid",
	"id_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDc2NjEsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5NzA4LCJpYXQiOjE3NTEyMDc5MDgsImp0aSI6ImEyMWE4Yzc0LTk0OWEtNGJkNi04YzhmLWFhYmYwYzQzNjNlMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.ShAxLD54A0Z3HYXJEt4yGRIJwt0bqyUGZykA_q2sUFljxNEs5CmVk4kUiW1ZGddxI60Gn7_E10OnFva743p23MDs0pDArwvZ4tmmcsWuYIn4OgBBA631J81Mm3buQHUyT76FSJo4eungyZeFbYCqHKvCb962ihS-EKb5LGe3w9MdxRaofjd9g19BhXqr6gMnNxPlWneREeql4-HUT6dG2tlpshGY0NzZrMyDjdhvd9uTLsJdM6XW9WAAYklFLnXmZuh0-XnA56Kmrw_KVxhTj-JPdD0ZhWwnybmq_aPGSS3pLQ1FyBsZI28ANgP4JNe0ONN76oUcCZWRNm073TmViA",
	"token_type": "Bearer",
	"expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ | string | 访问令牌(JWT格式) |
| refresh_token | FuXYzlid3rbwuHqiEXNRtWeuM8U3pc4FD9CG-E2DXJA9xKv5n7T26bmjw5UBszV5UXfUn2sptSx0TBXE_hkNCg5ByoAs4DOHQDE39d7HLJe8MM3orinI4WnDeCBO23Np | string | 授权码获取token接口返回的refresh_token |
| scope | openid | string | 实际授予的授权范围 |
| id_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDc2NjEsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5NzA4LCJpYXQiOjE3NTEyMDc5MDgsImp0aSI6ImEyMWE4Yzc0LTk0OWEtNGJkNi04YzhmLWFhYmYwYzQzNjNlMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.ShAxLD54A0Z3HYXJEt4yGRIJwt0bqyUGZykA_q2sUFljxNEs5CmVk4kUiW1ZGddxI60Gn7_E10OnFva743p23MDs0pDArwvZ4tmmcsWuYIn4OgBBA631J81Mm3buQHUyT76FSJo4eungyZeFbYCqHKvCb962ihS-EKb5LGe3w9MdxRaofjd9g19BhXqr6gMnNxPlWneREeql4-HUT6dG2tlpshGY0NzZrMyDjdhvd9uTLsJdM6XW9WAAYklFLnXmZuh0-XnA56Kmrw_KVxhTj-JPdD0ZhWwnybmq_aPGSS3pLQ1FyBsZI28ANgP4JNe0ONN76oUcCZWRNm073TmViA | string | ID令牌(JWT格式)，包含用户身份信息 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### auth-3 Token端点(授权码模式) client_secret_basic

**接口描述**

> OAuth 2.0授权码模式——第二步：用授权码换取access_token。客户端使用client_secret_basic方式提交客户端凭证（Authorization头携带Base64(client_id:client_secret)）。

**先调用/duca/oauth/authorize接口获取授权码**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | {{authorzation}} | string | 是 | base64(clientId:clientSecret) |

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | authorization_code | string | 是 | 授权类型，固定为authorization_code（授权码模式） |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 回调地址 |
| code | 9chX4zqWZfsWTPdgei7R7iAbGbCX819xzMhVsFJ1rt42Bu9wTsYCVqmdfBCKNMBFOIO9mc7hy4IuJ5HvtxqwnIm8mctLUzNTAxEL2fFgfF6nsdQBf9_WkqJZEd5Sr-cM | string | 是 | 授权码 |


**响应示例**

* 成功(200)

```javascript
{
	"access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ",
	"refresh_token": "FuXYzlid3rbwuHqiEXNRtWeuM8U3pc4FD9CG-E2DXJA9xKv5n7T26bmjw5UBszV5UXfUn2sptSx0TBXE_hkNCg5ByoAs4DOHQDE39d7HLJe8MM3orinI4WnDeCBO23Np",
	"scope": "openid",
	"id_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDc2NjEsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5NzA4LCJpYXQiOjE3NTEyMDc5MDgsImp0aSI6ImEyMWE4Yzc0LTk0OWEtNGJkNi04YzhmLWFhYmYwYzQzNjNlMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.ShAxLD54A0Z3HYXJEt4yGRIJwt0bqyUGZykA_q2sUFljxNEs5CmVk4kUiW1ZGddxI60Gn7_E10OnFva743p23MDs0pDArwvZ4tmmcsWuYIn4OgBBA631J81Mm3buQHUyT76FSJo4eungyZeFbYCqHKvCb962ihS-EKb5LGe3w9MdxRaofjd9g19BhXqr6gMnNxPlWneREeql4-HUT6dG2tlpshGY0NzZrMyDjdhvd9uTLsJdM6XW9WAAYklFLnXmZuh0-XnA56Kmrw_KVxhTj-JPdD0ZhWwnybmq_aPGSS3pLQ1FyBsZI28ANgP4JNe0ONN76oUcCZWRNm073TmViA",
	"token_type": "Bearer",
	"expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ | string | 访问令牌(JWT格式) |
| refresh_token | FuXYzlid3rbwuHqiEXNRtWeuM8U3pc4FD9CG-E2DXJA9xKv5n7T26bmjw5UBszV5UXfUn2sptSx0TBXE_hkNCg5ByoAs4DOHQDE39d7HLJe8MM3orinI4WnDeCBO23Np | string | 授权码获取token接口返回的refresh_token |
| scope | openid | string | 实际授予的授权范围 |
| id_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDc2NjEsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5NzA4LCJpYXQiOjE3NTEyMDc5MDgsImp0aSI6ImEyMWE4Yzc0LTk0OWEtNGJkNi04YzhmLWFhYmYwYzQzNjNlMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.ShAxLD54A0Z3HYXJEt4yGRIJwt0bqyUGZykA_q2sUFljxNEs5CmVk4kUiW1ZGddxI60Gn7_E10OnFva743p23MDs0pDArwvZ4tmmcsWuYIn4OgBBA631J81Mm3buQHUyT76FSJo4eungyZeFbYCqHKvCb962ihS-EKb5LGe3w9MdxRaofjd9g19BhXqr6gMnNxPlWneREeql4-HUT6dG2tlpshGY0NzZrMyDjdhvd9uTLsJdM6XW9WAAYklFLnXmZuh0-XnA56Kmrw_KVxhTj-JPdD0ZhWwnybmq_aPGSS3pLQ1FyBsZI28ANgP4JNe0ONN76oUcCZWRNm073TmViA | string | ID令牌(JWT格式)，包含用户身份信息 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | {{authorzation}} | string | 是 | base64(clientId:clientSecret) |


### Token端点(密码模式)

**接口描述**

> OAuth 2.0密码模式(Resource Owner Password Credentials Grant)。直接使用用户名和密码换取access_token。
> 注意：该模式将用户凭据直接暴露给客户端，仅推荐在可信的第一方应用中使用。遵循 [RFC 6749 Section 4.3](https://tools.ietf.org/html/rfc6749#section-4.3)。

**grant_type需要等于password<br>
userName,password必传
建议在服务端调用此接口**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | password | string | 是 | 授权类型，固定为password（密码模式） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid profile user:view user:add user:list user:passwd | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| username | {{userName}} | string | 是 | 用户名/账号 |
| password | {{password}} | string | 是 | 密码 |


**响应示例**

* 成功(200)

```javascript
{
    "access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A",
    "token_type": "Bearer",
    "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A | string | 访问令牌(JWT格式) |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token端点(手机号自动登录) 【basic】

**接口描述**

> 自定义授权模式——手机号自动登录（client_secret_basic认证方式）。客户端在Authorization头携带Base64(client_id:client_secret)，通过手机号直接换取access_token。若无对应用户则自动创建。

**grant_type需要等于phone<br>
phone参数必传
 **


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Basic N053T2ZiNjQ4MmNMcFBmMGFRYU5xZEZheWw4RVhFdk46ZDRWcDhjb0JxbmFhYXR5NWg1OVdVVDJVZnhkSG9BQlBBbmtjc2h5Rw== | string | 是 | 认证令牌/授权头 |

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述                   |
| --- | --- | ---- |------|------------------------|
| grant_type | phone | string | 是    | 授权类型，固定为phone（手机号自动登录） |
| client_id | {{clientId}} | string | 是    | 应用编号                   |
| client_secret | {{client_secret}} | string | 是    | 应用密钥                   |
| scope | openid profile | string | 是    | 请求的授权范围，多个scope用空格分隔   |
| phone | 17314952032 | string | 是    | 手机号码                   |
 | code | 123456 | string | 否    | 短信验证码，如果传了会校验短信验证码的正确性 |


**响应示例**

* 成功(200)

```javascript
{"access_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzU1NzY2NTAzLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiZXhwIjoxNzU2OTc2MTAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6IjMzOThmNjNjLTFmMmYtNGMwNC05MmEwLTc5MDk3YTc3ZTkxNSJ9.ZZVrWtw1SqIX5UuJ7iZwBwxb9yk0KmyOslPQ2G8EsNcnkF30PpBmmvcDmhhumpDOl4NbErnljyj4CihPaqvl5IJSnudPoadBSUc_EGDng5l9I_BcCNEaIiFgifRciN4c3RknAyp-pXgl7RIN9Kxyz6I1dh90ppCnbVsSjOtZRILoWWjxlF1HrTbZOJ_lFwIL5JoUrsJvYyzSgKfe2dwFTeGYgzVKkdzvx8IvWnCDR96zGi7FxuLe06XH76nFAwxiblcEMGLNxNm9sJkO8gs_vN3kpyilgghgGgwE0kC9iZIyTSXV2-c_k5_Zl1EcuHkMmVDHHLw9cUzQoBYFW6x_oA","refresh_token":"xL9_n4v4-qtfuVRLZeT4yG-jhPx8c0UGG37fKMOeHEB4Jkqb_7iw3z_9UgAyAPAbEOYjyIjHTE3JjYytJ1MDwAmDmTQnzqNL18yi7ENI4f7QlHkb0DMqousnX7CHiCla","scope":"openid profile","id_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiYXZhdGFyIjoiIiwidXNlck5hbWUiOiJ1c2VyNTIwMyIsInV1aWQiOiJvYlNCRXhuMVRZOGQ0ZmFWVHpJOE5FaXlFQTBnbjFOSFJhZDVUczh4eWoiLCJhdWQiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsInBob25lIjoiMTczMTQ5NTIwMzIiLCJhenAiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsIm5hbWUiOiLnlKjmiLcxNDkiLCJpZCI6MTIzMSwiZXhwIjoxNzU1NzY4MzAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6ImEwMWUyY2JlLTJiYzItNDA3ZS1iMDI3LTllYzcwYTgwOTQ2OSIsImVtYWlsIjoiIn0.JUDQwNCsrywlgI-6ePhUqKU6HT8bcwsil-oPKDXkKeDmSvLl20tTlb85kCNsttIW1nLklFQvuE1dmx6SN4z8yz6lZtJ8gR0pQ7kP17RfQfXsizcNPbIKA5E-IIuMC5uRYnvH_GeGfh9YTYoQZNCXGes6jE2Q_HUcjyUEyqDkjSatYM-AxWE5_0TO4mV2O60JFo9HxLMCAGB8--D9TE_Nd_yP_H_KWO9iPMJQRshRFhgdi_qOpRbauHiKDM4kMA-JP9YNUamDrzCLl5xBnQzjtmtNU4eybY-696wGuj2APczNQg0MQzZJSj57-EVhW_ynXfeyc06POfM9txi6zTzZAA","token_type":"Bearer","expires_in":1209600}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A | string | 访问令牌(JWT格式) |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Basic N053T2ZiNjQ4MmNMcFBmMGFRYU5xZEZheWw4RVhFdk46ZDRWcDhjb0JxbmFhYXR5NWg1OVdVVDJVZnhkSG9BQlBBbmtjc2h5Rw== | string | 是 | 认证令牌/授权头 |


### Token端点(手机号自动登录)【post】

**接口描述**

> 自定义授权模式——手机号自动登录（client_secret_post认证方式）。客户端在Body中提交client_id和client_secret，通过手机号直接换取access_token。若无对应用户则自动创建。

**grant_type需要等于phone<br>
phone参数必传**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | phone | string | 是 | 授权类型，固定为phone（手机号自动登录） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| phone | 17004952032 | string | 是 | 手机号码 |
| code | 123456 | string | 否 | 短信验证码，如果传了会校验短信验证码的正确性 |



**响应示例**

* 成功(200)

```javascript
{"access_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzU1NzY2NTAzLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiZXhwIjoxNzU2OTc2MTAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6IjMzOThmNjNjLTFmMmYtNGMwNC05MmEwLTc5MDk3YTc3ZTkxNSJ9.ZZVrWtw1SqIX5UuJ7iZwBwxb9yk0KmyOslPQ2G8EsNcnkF30PpBmmvcDmhhumpDOl4NbErnljyj4CihPaqvl5IJSnudPoadBSUc_EGDng5l9I_BcCNEaIiFgifRciN4c3RknAyp-pXgl7RIN9Kxyz6I1dh90ppCnbVsSjOtZRILoWWjxlF1HrTbZOJ_lFwIL5JoUrsJvYyzSgKfe2dwFTeGYgzVKkdzvx8IvWnCDR96zGi7FxuLe06XH76nFAwxiblcEMGLNxNm9sJkO8gs_vN3kpyilgghgGgwE0kC9iZIyTSXV2-c_k5_Zl1EcuHkMmVDHHLw9cUzQoBYFW6x_oA","refresh_token":"xL9_n4v4-qtfuVRLZeT4yG-jhPx8c0UGG37fKMOeHEB4Jkqb_7iw3z_9UgAyAPAbEOYjyIjHTE3JjYytJ1MDwAmDmTQnzqNL18yi7ENI4f7QlHkb0DMqousnX7CHiCla","scope":"openid profile","id_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiYXZhdGFyIjoiIiwidXNlck5hbWUiOiJ1c2VyNTIwMyIsInV1aWQiOiJvYlNCRXhuMVRZOGQ0ZmFWVHpJOE5FaXlFQTBnbjFOSFJhZDVUczh4eWoiLCJhdWQiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsInBob25lIjoiMTczMTQ5NTIwMzIiLCJhenAiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsIm5hbWUiOiLnlKjmiLcxNDkiLCJpZCI6MTIzMSwiZXhwIjoxNzU1NzY4MzAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6ImEwMWUyY2JlLTJiYzItNDA3ZS1iMDI3LTllYzcwYTgwOTQ2OSIsImVtYWlsIjoiIn0.JUDQwNCsrywlgI-6ePhUqKU6HT8bcwsil-oPKDXkKeDmSvLl20tTlb85kCNsttIW1nLklFQvuE1dmx6SN4z8yz6lZtJ8gR0pQ7kP17RfQfXsizcNPbIKA5E-IIuMC5uRYnvH_GeGfh9YTYoQZNCXGes6jE2Q_HUcjyUEyqDkjSatYM-AxWE5_0TO4mV2O60JFo9HxLMCAGB8--D9TE_Nd_yP_H_KWO9iPMJQRshRFhgdi_qOpRbauHiKDM4kMA-JP9YNUamDrzCLl5xBnQzjtmtNU4eybY-696wGuj2APczNQg0MQzZJSj57-EVhW_ynXfeyc06POfM9txi6zTzZAA","token_type":"Bearer","expires_in":1209600}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A | string | 访问令牌(JWT格式) |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token端点(微信客户端登录)

**接口描述**

> 自定义授权模式——微信客户端登录。通过微信OpenId直接换取access_token。

**grant_type需要等于wxOpenId
openId参数必传
建议在服务端调用此接口**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | wxOpenId | string | 是 | 授权类型，固定为wxOpenId（微信客户端登录） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| openId | w1701495203267 | string | 是 | openId 必传 |


**响应示例**

* 成功(200)

```javascript
{"access_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3MTcwMTQ5NTIwMzI2NyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzU1NzY2NDQxLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiZXhwIjoxNzU2OTc2MDQxLCJpYXQiOjE3NTU3NjY0NDEsImp0aSI6ImMyOGIxYTQ0LTg0ZDktNDg5YS1hODg2LTIzYzMwYzIxMWRjYyJ9.S22PK4is2aT93h2kJxRn-bP2NdQ9V6gA4MlRqC2C6VVuT9c1XD2QGV5Vo8g5uKvKyNVvVcm9pEFgl9T-SDHRIGaGyOnHG1-o5xNzmS3GmZTPlwdwIS4ULmVkSsueICiL2YJsWJaIUZUSjbT7ezuzFZV8UXXIBCohpKyLlZa_n9DJHsvvOfOYkoFhagIGTNoiBOoHsU3l15nTEx7W0QUzJcW1zGq8fR9NvC0brLDQv3zV943_HU0CdCos7z-DH-Cr-fIr_Vy9DzV3EIdNE2betLkcCP66jqj3cZYfkZSds1exPe64XqJ0W_afpUtidgTXl6F7jsDTqW4yIffdQ-8ylw","refresh_token":"738ts7eyI6Y61fPv-XkrMQBQMmam0pzrERMTlmidhX2LbX5B6z-PHkc79uflchM-wNU4y7WJ7E7StTDEsKxBpb4y6y01X0WHRyLqfdpVKCpIm1-xPHsw3XGO6cKAmYKU","scope":"openid profile","id_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3MTcwMTQ5NTIwMzI2NyIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiYXZhdGFyIjoiIiwidXNlck5hbWUiOiJ3MTcwMTQ5NTIwMzI2NyIsInV1aWQiOiJiQzlaZlJMNDRiMzdFOVpnejdFUnk0YUZDck1qZXRRUG5aMHhlUlFhNkwiLCJhdWQiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsInBob25lIjoiIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJuYW1lIjoi55So5oi3dzE3MDE0OTUyMDMyNjciLCJpZCI6MTIzMCwiZXhwIjoxNzU1NzY4MjQxLCJpYXQiOjE3NTU3NjY0NDEsImp0aSI6ImMzMmU2YThkLTNmN2ItNGExNy1hODQ5LTc0ZjliN2I0YTYxZiIsImVtYWlsIjoiIn0.imsyiPviBCFgeegh-woyOZN0nHezW-mST41cVom2tdTou3LMoZ92PndqT8pHKgBWRbR9gMUtrYEWzRzxGOP1xHbpJQtjjDcm9Imuszc-N_770pqnnskQTUbRoqR9QrIcklNp_03nL3tNkU3Dx9vOSDi7Hm8tY08EpsndPiFNUNCehORcQNhuz1LpP6yQYgeurpE79wyDK7hzio-CTeY4pSp0PBd-2-Csrg5LpTuqdPe8N4g6ZYEzXAZST7OCKTNrEEc3K1wBBbtJQqMtm2R8g203Ec_LZFkGgiZpvX9E7uCigmpRMrKtw-qTOM-bP1UHjdy5Necva2EIizWZBWSlPA","token_type":"Bearer","expires_in":1209600}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A | string | 访问令牌(JWT格式) |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token端点 client_credentials /client_secret_post

**接口描述**

> OAuth 2.0客户端凭证模式(Client Credentials Grant)。服务器端应用使用自己的client_id和client_secret直接获取access_token，不涉及用户身份。使用client_secret_post方式提交凭证。遵循 [RFC 6749 Section 4.4](https://tools.ietf.org/html/rfc6749#section-4.4)。


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | client_credentials | string | 是 | 授权类型，固定为client_credentials（客户端凭证模式） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid user:list user:add user:view user:del user:update user:move | string | 是 | 注意：参数值前后不要带空格 |


**响应示例**

* 成功(200)

```javascript
{
    "access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA0MDI0LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTM2MjQsImlhdCI6MTc1MTIwNDAyNCwianRpIjoiNjgwMzNlYzItYTk2ZC00YWVkLWJjNTEtOWNkZTY0NDRlNzFkIn0.JUo_3HQ_qPpfPe6HsC6a9gDRNJzvpXcMCfkTGQbUOiD-qxEDSsKQvcbat52AuD-FynF8RL4clmFlUMDm0CN7KEMCf4XimyGsNhXeIEuiOfXeDktesSYkPw79ioU33vIpj3BAkpqLSOOJSwWWcpI_e7SNpkzuhwkXg1CLk_AaOKZ-BTrpnniNSW3SIbfDcnNceZZ0NgVfu5DI7jUNeDWHE54sYsPgmDguy5sLsp3aWw-_y5VT9hZUQ9xu2D_R-4rklAwy4TJd8lL9hc5KSuEYr3hnruTY4NpirxO92z1KseE6s3N1ERGlrBaRzo9efdt2e7bX59c3cOvlTkZ0qSoKTA",
    "scope": "openid",
    "token_type": "Bearer",
    "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA0MDI0LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTM2MjQsImlhdCI6MTc1MTIwNDAyNCwianRpIjoiNjgwMzNlYzItYTk2ZC00YWVkLWJjNTEtOWNkZTY0NDRlNzFkIn0.JUo_3HQ_qPpfPe6HsC6a9gDRNJzvpXcMCfkTGQbUOiD-qxEDSsKQvcbat52AuD-FynF8RL4clmFlUMDm0CN7KEMCf4XimyGsNhXeIEuiOfXeDktesSYkPw79ioU33vIpj3BAkpqLSOOJSwWWcpI_e7SNpkzuhwkXg1CLk_AaOKZ-BTrpnniNSW3SIbfDcnNceZZ0NgVfu5DI7jUNeDWHE54sYsPgmDguy5sLsp3aWw-_y5VT9hZUQ9xu2D_R-4rklAwy4TJd8lL9hc5KSuEYr3hnruTY4NpirxO92z1KseE6s3N1ERGlrBaRzo9efdt2e7bX59c3cOvlTkZ0qSoKTA | string | 访问令牌(JWT格式) |
| scope | openid | string | 实际授予的授权范围 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token端点 client_credentials /client_secret_jwt

**接口描述**

> OAuth 2.0客户端凭证模式——使用client_secret_jwt方式认证。客户端使用共享密钥签发JWT作为客户端断言(client_assertion)进行身份认证。遵循 [RFC 7523](https://tools.ietf.org/html/rfc7523)。


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | client_credentials | string | 是 | 授权类型，固定为client_credentials（客户端凭证模式） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| client_assertion_type | urn:ietf:params:oauth:client-assertion-type:jwt-bearer | string | 是 | 客户端断言类型，固定为urn:ietf:params:oauth:client-assertion-type:jwt-bearer |
| client_assertions | eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmc2NCQkhrWjU5VnQ0MmRPMHhVYmdVUWhZc0N5Mlp1ciIsInN1YiI6ImZzY0JCSGtaNTlWdDQyZE8weFViZ1VRaFlzQ3kyWnVyIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2Evb2F1dGgyL3Rva2VuIiwiZXhwIjoxNzUxMjkwNjg1LCJpYXQiOjE3NTEyMDQyODUsImp0aSI6IjI1YTM0MzNiLWE0ODMtNGQ3Mi1hMDQ2LTg1OThiMGM3NjdiMSJ9.NeRtVogNHgUsr7MKxRh-4aW7cd8o4a4tod7yjG2nH1c | string | 是 | JWT格式的客户端断言，使用client_secret签名的JWT |


**响应示例**

* 成功(200)

```javascript
{
    "access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA0Mjk4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTM4OTgsImlhdCI6MTc1MTIwNDI5OCwianRpIjoiMGI0YmQ5NGItNjI1MS00N2U1LTg4ZTAtYmZlYWRkN2QzNTQ2In0.B9RaMn3kyrL1mGzPd04nDwJHDgVbxcJ24GpKFV-U2UjWDZTbBiL80q4bmGNM099AFzTifX0zJH-KYabb6MTuw98fzkdJKBzXAKUin7jOLDX8rUsHj8Hre4_Zl46zKYf4Simvyg_utF6wZCdfrurRNAeM925PdPD8lr8itiI9Q0VmOxgPkMT7d58TdD9r911uGM22QY1Jc5kaqeeNQloqHWQpO8Hrkvkjp454FxdbvJJ7VmL3IvfTiQqJAzVwm11Pg86DvreKVS39HrU1TpqwOsgGAecjlztG3FreYmviQLKZMplLDDwcc6b92fv-SbceuD3MilaLFzgVckBdZKbU4g",
    "scope": "openid",
    "token_type": "Bearer",
    "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA0Mjk4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTM4OTgsImlhdCI6MTc1MTIwNDI5OCwianRpIjoiMGI0YmQ5NGItNjI1MS00N2U1LTg4ZTAtYmZlYWRkN2QzNTQ2In0.B9RaMn3kyrL1mGzPd04nDwJHDgVbxcJ24GpKFV-U2UjWDZTbBiL80q4bmGNM099AFzTifX0zJH-KYabb6MTuw98fzkdJKBzXAKUin7jOLDX8rUsHj8Hre4_Zl46zKYf4Simvyg_utF6wZCdfrurRNAeM925PdPD8lr8itiI9Q0VmOxgPkMT7d58TdD9r911uGM22QY1Jc5kaqeeNQloqHWQpO8Hrkvkjp454FxdbvJJ7VmL3IvfTiQqJAzVwm11Pg86DvreKVS39HrU1TpqwOsgGAecjlztG3FreYmviQLKZMplLDDwcc6b92fv-SbceuD3MilaLFzgVckBdZKbU4g | string | 访问令牌(JWT格式) |
| scope | openid | string | 实际授予的授权范围 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token端点(thirdUserId自动登录)【post】

**接口描述**

> 自定义授权模式——第三方用户ID自动登录。通过第三方系统的用户ID直接换取access_token。若用户不存在则自动创建，可同步昵称和头像。

**grant_type需要等于thirdUid
phone参数必传
建议在服务端调用此接口**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | thirdUid | string | 是 | 固定thirdUid |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| thirdUserId | 1700495909 | string | 是 | 第三方用户编号，必传,thirdUserId不存在会自动新增 |
| name | hi | string | 否 | 昵称，非必传 |
| avatar | http://www.baiu.com/aa.ajpg | string | 否 | 头像，非必传 |
| userName | 1700495909 | string | 是 | 用户名， 必传 |


**响应示例**

* 成功(200)

```javascript
{"access_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzU1NzY2NTAzLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiZXhwIjoxNzU2OTc2MTAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6IjMzOThmNjNjLTFmMmYtNGMwNC05MmEwLTc5MDk3YTc3ZTkxNSJ9.ZZVrWtw1SqIX5UuJ7iZwBwxb9yk0KmyOslPQ2G8EsNcnkF30PpBmmvcDmhhumpDOl4NbErnljyj4CihPaqvl5IJSnudPoadBSUc_EGDng5l9I_BcCNEaIiFgifRciN4c3RknAyp-pXgl7RIN9Kxyz6I1dh90ppCnbVsSjOtZRILoWWjxlF1HrTbZOJ_lFwIL5JoUrsJvYyzSgKfe2dwFTeGYgzVKkdzvx8IvWnCDR96zGi7FxuLe06XH76nFAwxiblcEMGLNxNm9sJkO8gs_vN3kpyilgghgGgwE0kC9iZIyTSXV2-c_k5_Zl1EcuHkMmVDHHLw9cUzQoBYFW6x_oA","refresh_token":"xL9_n4v4-qtfuVRLZeT4yG-jhPx8c0UGG37fKMOeHEB4Jkqb_7iw3z_9UgAyAPAbEOYjyIjHTE3JjYytJ1MDwAmDmTQnzqNL18yi7ENI4f7QlHkb0DMqousnX7CHiCla","scope":"openid profile","id_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiYXZhdGFyIjoiIiwidXNlck5hbWUiOiJ1c2VyNTIwMyIsInV1aWQiOiJvYlNCRXhuMVRZOGQ0ZmFWVHpJOE5FaXlFQTBnbjFOSFJhZDVUczh4eWoiLCJhdWQiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsInBob25lIjoiMTczMTQ5NTIwMzIiLCJhenAiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsIm5hbWUiOiLnlKjmiLcxNDkiLCJpZCI6MTIzMSwiZXhwIjoxNzU1NzY4MzAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6ImEwMWUyY2JlLTJiYzItNDA3ZS1iMDI3LTllYzcwYTgwOTQ2OSIsImVtYWlsIjoiIn0.JUDQwNCsrywlgI-6ePhUqKU6HT8bcwsil-oPKDXkKeDmSvLl20tTlb85kCNsttIW1nLklFQvuE1dmx6SN4z8yz6lZtJ8gR0pQ7kP17RfQfXsizcNPbIKA5E-IIuMC5uRYnvH_GeGfh9YTYoQZNCXGes6jE2Q_HUcjyUEyqDkjSatYM-AxWE5_0TO4mV2O60JFo9HxLMCAGB8--D9TE_Nd_yP_H_KWO9iPMJQRshRFhgdi_qOpRbauHiKDM4kMA-JP9YNUamDrzCLl5xBnQzjtmtNU4eybY-696wGuj2APczNQg0MQzZJSj57-EVhW_ynXfeyc06POfM9txi6zTzZAA","token_type":"Bearer","expires_in":1209600}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A | string | 访问令牌(JWT格式) |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token端点(uuid登录)（post） 

**接口描述**

> 自定义授权模式——UUID登录。通过统一认证系统自动生成的UUID唯一标识直接换取access_token。若用户不存在则自动创建，可同步昵称和头像。

**grant_type需要等于phone
phone参数必传
建议在服务端调用此接口**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | uuid | string | 是 | 固定uuid |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| uuid | I5081ZyRbuSJFDcXiky4spEAq4JOk8Ho4CfC4mmVFw | string | 是 | 统一认证自动生成的uuid(唯一标识） |
| name | hi | string | 否 | 昵称，非必传 |
| avatar | http://www.baiu.com/aa.ajpg | string | 否 | 头像，非必传 |


**响应示例**

* 成功(200)

```javascript
{"access_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzU1NzY2NTAzLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiZXhwIjoxNzU2OTc2MTAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6IjMzOThmNjNjLTFmMmYtNGMwNC05MmEwLTc5MDk3YTc3ZTkxNSJ9.ZZVrWtw1SqIX5UuJ7iZwBwxb9yk0KmyOslPQ2G8EsNcnkF30PpBmmvcDmhhumpDOl4NbErnljyj4CihPaqvl5IJSnudPoadBSUc_EGDng5l9I_BcCNEaIiFgifRciN4c3RknAyp-pXgl7RIN9Kxyz6I1dh90ppCnbVsSjOtZRILoWWjxlF1HrTbZOJ_lFwIL5JoUrsJvYyzSgKfe2dwFTeGYgzVKkdzvx8IvWnCDR96zGi7FxuLe06XH76nFAwxiblcEMGLNxNm9sJkO8gs_vN3kpyilgghgGgwE0kC9iZIyTSXV2-c_k5_Zl1EcuHkMmVDHHLw9cUzQoBYFW6x_oA","refresh_token":"xL9_n4v4-qtfuVRLZeT4yG-jhPx8c0UGG37fKMOeHEB4Jkqb_7iw3z_9UgAyAPAbEOYjyIjHTE3JjYytJ1MDwAmDmTQnzqNL18yi7ENI4f7QlHkb0DMqousnX7CHiCla","scope":"openid profile","id_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyNTIwMyIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiYXZhdGFyIjoiIiwidXNlck5hbWUiOiJ1c2VyNTIwMyIsInV1aWQiOiJvYlNCRXhuMVRZOGQ0ZmFWVHpJOE5FaXlFQTBnbjFOSFJhZDVUczh4eWoiLCJhdWQiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsInBob25lIjoiMTczMTQ5NTIwMzIiLCJhenAiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsIm5hbWUiOiLnlKjmiLcxNDkiLCJpZCI6MTIzMSwiZXhwIjoxNzU1NzY4MzAzLCJpYXQiOjE3NTU3NjY1MDMsImp0aSI6ImEwMWUyY2JlLTJiYzItNDA3ZS1iMDI3LTllYzcwYTgwOTQ2OSIsImVtYWlsIjoiIn0.JUDQwNCsrywlgI-6ePhUqKU6HT8bcwsil-oPKDXkKeDmSvLl20tTlb85kCNsttIW1nLklFQvuE1dmx6SN4z8yz6lZtJ8gR0pQ7kP17RfQfXsizcNPbIKA5E-IIuMC5uRYnvH_GeGfh9YTYoQZNCXGes6jE2Q_HUcjyUEyqDkjSatYM-AxWE5_0TO4mV2O60JFo9HxLMCAGB8--D9TE_Nd_yP_H_KWO9iPMJQRshRFhgdi_qOpRbauHiKDM4kMA-JP9YNUamDrzCLl5xBnQzjtmtNU4eybY-696wGuj2APczNQg0MQzZJSj57-EVhW_ynXfeyc06POfM9txi6zTzZAA","token_type":"Bearer","expires_in":1209600}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJEckJJb1NTNk9ucXo4VWxiZDBPd2ExYnBYQU0wdUc2cyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjAzNjMxLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODgvZHVjYSIsImV4cCI6MTc1MjQxMzIzMSwiaWF0IjoxNzUxMjAzNjMxLCJqdGkiOiIyZWM2OTY5Mi02NjFmLTRmZWMtOThlNC0zODk1MGVhNzQ4ZmMifQ.Ot50Eljym9psElpkd2ExTNU4Mp89eOHAwgPCI1nvR0zNkoXYbc5fv7LlaL8wMWwhpXMU_O6XqhEccucwoNkRJJJPW_68CNtDq2-q3c0YKdm-1Cqo4t5nG1O3Hi8kbwo-ZpGtNGdO9Gydggy7QPdOVSPVE7ggG1u_ptulggt1VB_nCMneuPKJ6kEf4gFEUn6cmzNw_naIC63oqvqaTqGKKZtWl-stoGDOTbmeCy_djLL-aX56pNzTQ34a8IFwdmaf7Ly9RHFDfqqbYuwkvjueG93asFUv8mU1XM07rvmmgmiCPq5t1xM0l4orEKJtMVAu0lM1HCoYqiLKDJOHi4W-9A | string | 访问令牌(JWT格式) |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### PKCE-1 授权端点(浏览器访问,PKCE)

**接口描述**

> OAuth 2.0授权码模式 + PKCE增强——第一步。PKCE(Proof Key for Code Exchange)通过code_challenge和code_verifier防止授权码拦截攻击，适用于移动端和SPA等无法安全存储client_secret的场景。遵循 [RFC 7636](https://tools.ietf.org/html/rfc7636)。


**接口URL**

> /duca/oauth2/authorize?response_type=code&client_id={{clientId}}&scope=openid&redirect_uri=http://localhost:8187/duca/callback&code_challenge=iFR4H8hHjSVuRLwUBcz6Xr4q6keTJT4J4ME0EzlJtOg&code_challenge_method=S256

**请求方式**

> GET

**Content-Type**

> none

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| response_type | code | string | 是 | 响应类型，固定为code（授权码模式） |
| client_id | {{clientId}} | string | 是 | 客户端ID/应用编号 |
| scope | openid | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 回调地址 |
| code_challenge | iFR4H8hHjSVuRLwUBcz6Xr4q6keTJT4J4ME0EzlJtOg | string | 是 | 挑战码 |
| code_challenge_method | S256 | string | 是 | 加密方式 |


**响应示例**

* 成功(200)

```javascript
暂无数据
```

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### PKCE-2 Token端点(通过挑战码获取token)

**接口描述**

> OAuth 2.0 授权码模式 + PKCE 增强——第二步。客户端将 PKCE-1 步骤获取的授权码、以及 code_verifier（与 code_challenge 对应的验证器）提交到令牌端点，授权服务器验证 code_verifier 的 SHA-256 哈希值与 code_challenge 匹配后颁发 token。遵循 [RFC 7636](https://tools.ietf.org/html/rfc7636)。

**1、获取挑战码，参考代码如下：**

 ```java
    /**
     *
     * 生成 code_verifier
     */
    public static String codeVerifierGenerator(){
        return  Base64URL.encode(UUID.randomUUID().toString()).toString();
    }

    /**
     * 生成 code_challenge
     *
     */
    public static String codeChallengeGenerator(String codeVerifier) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digestCodeVerifier = messageDigest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
        return Base64URL.encode(digestCodeVerifier).toString();
    }
```

**2、浏览器访问获取授权码
http://localhost:8890/duca/oauth2/authorize?response_type=code&client_id=QHwrGmrHhH0RMg9o99mql7zb6DlhFi4y&scope=profile&redirect_uri=http://localhost:8187/ddms/login/callback&code_challenge=dC_9y-u_scuqEzci1UUyFF8qDtanAU50v7n8R_wAflA&code_challenge_method=S256**

**3、通过授权码获取token**

**[object Object]**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | authorization_code | string | 是 | 授权类型，固定为authorization_code（授权码模式） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| scope | openid | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| code | k62MJzjTpNG7mkWM6jyTcxh6wptiJHJD7Pnp8W57KuItbyRoe4QgpNKHHuZ6Fcs-X_zjpmUEOc5LfViWj5Bg3Nd1sVS9gWiWNcvUtat1Xd9v4trDySHSWht4hYoDywrd | string | 是 | 授权码 通过“认证端点”获取 |
| code_verifier | YmNhMDIyNjUtNWY0My00M2VjLThlZTQtMzRkZjdjZDM4MTk4 | string | 是 | PKCE代码验证器，与授权请求中的code_challenge对应 |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 回调地址 |


**响应示例**

* 成功(200)

```javascript
{
    "access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA2NTIxLCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTYxMjEsImlhdCI6MTc1MTIwNjUyMSwianRpIjoiZDQ5NzE5ZjEtODYwOS00YTU4LWFjMjgtOTBlMThlOTQzODM2In0.G3qF4kljnyhY3PWrT8EYcZOgBzzlSCEPUJUN3sgkzPJ1EQbxdJimzI6VgwjVvmO0XTi9zO5BXqnD6rmTHdFw9WEa61qWeaJUl0p8CQdQ_DdIrqG9DTTEZ-VWkpf4vZpdWbgaNa-egFAWLuDM_AM0Ad63UYZm4KgB5cqONnTg_F2MTz-3sCbT-EKmeD7cV905sieY8iYAqgb9-iMMHoVXFj4a2BJScjFipUl3YxqUcB5VsuAxZ0Dlrnc5xzTKXGLDQfj1l37EYMAQ1eLxdTkXNZvYV12fMmaoNv2tuVTp_QlJDqVsIgSuTkThxaUW8j06gd2UJBFuLUGrzV6cz6tsBg",
    "refresh_token": "Rg_7hx8ucYjhqtrMgPEWbZ_IzkxofdxAx4Ri1XEYDHOh2XQnFUlLGn-m18B_VYDxapNUMfmk0l22SmqzO5xmTMWpzMW2xZOhq-7KGka-OEwtCzI0o9NS6BTgdXeRKdAy",
    "scope": "openid",
    "id_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDY0MDYsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA4MzIxLCJpYXQiOjE3NTEyMDY1MjEsImp0aSI6Ijk4MDJiZjA2LTc4MGItNDc2My1hNjVjLWNmMmNjMTc3MTc1YiIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.SNyf5FMUIhQthllEqd5G2qpA5ejocpb0ZdIP4cGzlJzAGaMXph75qsitIx7DLhLfk2R9ic-tUukHbqIAXxiiqi3r2a_ZJNoIntTctZ2RwRX48ZndDQxBDxjLvcWS-WTbLAinN8JIGHaJm1eWYxo9n49805Di8K3s64uiFOGUtKGWoyl_NsJ1n_gdb2rLv8gvsFOmeZ8BrtCMryvzIWvsVVwff1kxQDgqtFWOACX9oSWPmo9Aln48ytZt3KrttUJd8OrA4gX2DdEvtyK6YjUWGJ50_4LHDqUDFMgb-CUX6jlr5Sqhpt8DNm9ihsBWYfsMRXNw78M1apSDbtDwRtuHQA",
    "token_type": "Bearer",
    "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA2NTIxLCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTYxMjEsImlhdCI6MTc1MTIwNjUyMSwianRpIjoiZDQ5NzE5ZjEtODYwOS00YTU4LWFjMjgtOTBlMThlOTQzODM2In0.G3qF4kljnyhY3PWrT8EYcZOgBzzlSCEPUJUN3sgkzPJ1EQbxdJimzI6VgwjVvmO0XTi9zO5BXqnD6rmTHdFw9WEa61qWeaJUl0p8CQdQ_DdIrqG9DTTEZ-VWkpf4vZpdWbgaNa-egFAWLuDM_AM0Ad63UYZm4KgB5cqONnTg_F2MTz-3sCbT-EKmeD7cV905sieY8iYAqgb9-iMMHoVXFj4a2BJScjFipUl3YxqUcB5VsuAxZ0Dlrnc5xzTKXGLDQfj1l37EYMAQ1eLxdTkXNZvYV12fMmaoNv2tuVTp_QlJDqVsIgSuTkThxaUW8j06gd2UJBFuLUGrzV6cz6tsBg | string | 访问令牌(JWT格式) |
| refresh_token | Rg_7hx8ucYjhqtrMgPEWbZ_IzkxofdxAx4Ri1XEYDHOh2XQnFUlLGn-m18B_VYDxapNUMfmk0l22SmqzO5xmTMWpzMW2xZOhq-7KGka-OEwtCzI0o9NS6BTgdXeRKdAy | string | 授权码获取token接口返回的refresh_token |
| scope | openid | string | 实际授予的授权范围 |
| id_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDY0MDYsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA4MzIxLCJpYXQiOjE3NTEyMDY1MjEsImp0aSI6Ijk4MDJiZjA2LTc4MGItNDc2My1hNjVjLWNmMmNjMTc3MTc1YiIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.SNyf5FMUIhQthllEqd5G2qpA5ejocpb0ZdIP4cGzlJzAGaMXph75qsitIx7DLhLfk2R9ic-tUukHbqIAXxiiqi3r2a_ZJNoIntTctZ2RwRX48ZndDQxBDxjLvcWS-WTbLAinN8JIGHaJm1eWYxo9n49805Di8K3s64uiFOGUtKGWoyl_NsJ1n_gdb2rLv8gvsFOmeZ8BrtCMryvzIWvsVVwff1kxQDgqtFWOACX9oSWPmo9Aln48ytZt3KrttUJd8OrA4gX2DdEvtyK6YjUWGJ50_4LHDqUDFMgb-CUX6jlr5Sqhpt8DNm9ihsBWYfsMRXNw78M1apSDbtDwRtuHQA | string | ID令牌(JWT格式)，包含用户身份信息 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token Exchange 

**接口描述**

> OAuth 2.0 令牌交换（Token Exchange）。客户端持有一个已有的 subject_token，提交给授权服务器以换取一个新的 token，通常用于跨安全域委派或降权场景。遵循 [RFC 8693](https://tools.ietf.org/html/rfc8693)。


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{otherClientId}} | string | 是 | 应用编号 |
| client_secret | {{otherClientSecret}} | string | 是 | 应用密钥 |
| grant_type | urn:ietf:params:oauth:grant-type:token-exchange | string | 是 | 授权类型，固定为token-exchange（令牌交换） |
| subject_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ | string | 是 | subject_token必须是通过authcode获取的，推送授权获取【/par】、或者devicecode获取的access_token |
| subject_token_type | urn:ietf:params:oauth:token-type:access_token | string | 是 | subjectTokenType类型包括urn:ietf:params:oauth:token-type:access_token、urn:ietf:params:oauth:token-type:id_token、urn:ietf:params:oauth:token-type:jwt |
| scope | profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| audience | https://api.example.com | string | 是 | 目标资源服务器标识符 |


**响应示例**

* 成功(200)

```javascript
{
    "access_token": "0cj8_5SG3dwzh9FLr3gIxEs9kcgOV3-oE0QCfhugr5Nn_q46xdVnIDMezpcu43n92F6UCb-yVae44UdoBf21JIo1u3nzHZkrDJ648ulYYJMSwYNpAFR7xa9HjTwvIBVm",
    "issued_token_type": "urn:ietf:params:oauth:token-type:access_token",
    "scope": "profile",
    "token_type": "Bearer",
    "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | 0cj8_5SG3dwzh9FLr3gIxEs9kcgOV3-oE0QCfhugr5Nn_q46xdVnIDMezpcu43n92F6UCb-yVae44UdoBf21JIo1u3nzHZkrDJ648ulYYJMSwYNpAFR7xa9HjTwvIBVm | string | 访问令牌(JWT格式) |
| issued_token_type | urn:ietf:params:oauth:token-type:access_token | string | 颁发的令牌类型 |
| scope | profile | string | 实际授予的授权范围 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### DPOP-1 授权端点(浏览器访问,PKCE) 

**接口描述**

> PKCE 增强的授权码模式第一步。浏览器重定向到此端点，携带 code_challenge（通过 S256 生成的挑战码）和 code_challenge_method 参数。用户授权后，授权码通过 redirect_uri 回调给客户端，后续用 code_verifier 换取 token 以完成 PKCE 验证。遵循 [RFC 7636](https://tools.ietf.org/html/rfc7636)。


**接口URL**

> /duca/oauth2/authorize?response_type=code&client_id={{clientId}}&scope=openid&redirect_uri=http://localhost:8187/duca/callback&code_challenge=5cbXHmejjGZPVrrV8AyNPfF-t_3ncWur1EO_uhqNzeg&code_challenge_method=S256

**请求方式**

> GET

**Content-Type**

> none

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| response_type | code | string | 是 | 响应类型，固定为code（授权码模式） |
| client_id | {{clientId}} | string | 是 | 客户端ID/应用编号 |
| scope | openid | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 回调地址 |
| code_challenge | 5cbXHmejjGZPVrrV8AyNPfF-t_3ncWur1EO_uhqNzeg | string | 是 | 挑战码 |
| code_challenge_method | S256 | string | 是 | 加密方式 |


**响应示例**

* 成功(200)

```javascript
暂无数据
```



### DPOP-2 Token端点(授权码+DPoP获取token)

**接口描述**

> DPoP + PKCE 增强的授权码模式第二步。客户端将 DPOP-1 步骤获取的授权码、code_verifier（与 code_challenge 对应的验证器）提交到令牌端点，同时在 DPoP 请求头中携带 DPoP 证明 JWT，以证明令牌持有权。遵循 [RFC 7636](https://tools.ietf.org/html/rfc7636) (PKCE) 及 [RFC 9449](https://tools.ietf.org/html/rfc9449) (DPoP)。

**1、获取挑战码，参考代码如下：**

 ```java
    /**
     *
     * 生成 code_verifier
     */
    public static String codeVerifierGenerator(){
        return  Base64URL.encode(UUID.randomUUID().toString()).toString();
    }

    /**
     * 生成 code_challenge
     *
     */
    public static String codeChallengeGenerator(String codeVerifier) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] digestCodeVerifier = messageDigest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
        return Base64URL.encode(digestCodeVerifier).toString();
    }
```

**2、浏览器访问获取授权码
http://localhost:8890/duca/oauth2/authorize?response_type=code&client_id=QHwrGmrHhH0RMg9o99mql7zb6DlhFi4y&scope=profile&redirect_uri=http://localhost:8187/ddms/login/callback&code_challenge=dC_9y-u_scuqEzci1UUyFF8qDtanAU50v7n8R_wAflA&code_challenge_method=S256**

**3、生成Dpop，参考代码**

```java
private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        //NimbusJwkSetEndpointFilter
        // OAuth2AuthorizationServerMetadataEndpointFilter
        return keyPair;
    }
    public static void main(String[] args) {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSource<SecurityContext> jwkSource = (jwkSelector, securityContext) -> jwkSelector
                .select(new JWKSet(rsaKey));
        NimbusJwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource);

        LocalDateTime now = LocalDateTime.now();
        Instant operateTime = now.atZone(ZoneId.systemDefault()).toInstant();

        JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256)
                .type("dpop+jwt")
                .jwk(rsaKey.toPublicJWK().toJSONObject())
                .build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(operateTime)
                //htm和htu必须的， 
                 .claim("htm", "POST")
                .claim("htu", "http://localhost:8890/duca/oauth2/token")
                .id(UUID.randomUUID().toString())
                .expiresAt(Instant.parse("2025-12-03T10:15:30.00Z"))
                .build();
        Jwt dPoPProof = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
        System.out.println(dPoPProof.getTokenValue());




    }
```

**4、通过授权码获取token**


```http
@code=34T27vggPUk7LksNkA58IKNshIemmkw0NgOxoRrD7I9Z2JuXKvXm_iaIgj88JthAykODzlebRYhLzDBkNMJHWGh2t1IZul9wbmS5WOXOKKHQIF43-6aeAFl-EytNAeO6
@client_secret=bJVxtEYFQjRq13jVyfkFwUVpO6ACYeHFtamyx7Gx
@userName=wanghailong
@password=hailong@2022
@authorzation=Y2hpY2s6MTIzNDU2
@redirect_uri=http://localhost:8187/ddms/login/callback
POST http://127.0.0.1:8188/duca/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&redirect_uri={{redirect_uri}}&code={{code}}&client_id={{client_id}}&client_secret={{client_secret}}&code_verifier={{code_verifier}}
```


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Dpop | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJ0eXAiOiJkcG9wK2p3dCIsImFsZyI6IlJTMjU2IiwiandrIjp7Imt0eSI6IlJTQSIsImUiOiJBUUFCIiwia2lkIjoiZjgyMWQ2OTItODI0Ny00ZmNiLThhMzAtNTc2YjkxZDc5ODc3IiwibiI6InJiMHhVekFpTmg2b3I5Rlp2cDJ6OVN3dlVmM2pDY3dHS3ZsNU5uSmpNM2dJM0VlaFNEbnU2UGlHcVNPd01LTm1JN0J1NnlmM3dnZ2dmdU55UVhVdGtxeVN4V0NpVWRyZFBEemRCRFNhOHdxN0V4VnVZUXcyU04yQ3FTNTVBTVdBdUp5Y2s5bnFtUVl5OHlYZWR0Wjgtdk1ZYXNFa190MEMwelNRQXd4MEx1dEJRV2JOUmJ3LUlEaUNaNU1kendWYXlRbDA1NHZ1OEdwcTVNekt4Z0lkMXdKSGk2MDJjSm9KZG9fYXhXOUhVS1JYMFNmeTh4b1FVZXRmaGxPcXRLVlVhVGtDd29UMkpBTTRsdHNJRDI1c2duWWpYY3BEYjM3UW8wVzNMLTRNemlTalpuU3NhOG5UeHBsRDFsdlBpTU5Yek5sZDlmeWVQV3BHYTNWMlZPU0hWdyJ9fQ.eyJodG0iOiJQT1NUIiwiaHR1IjoiaHR0cDovL2xvY2FsaG9zdDo4ODkwL2R1Y2Evb2F1dGgyL3Rva2VuIiwiZXhwIjoxNzY0NzU2OTMwLCJpYXQiOjE3NTEyMTAzNTcsImp0aSI6ImYzZTEwYTZkLTVlOTctNGU1MS04MzQ5LTdmZjlmODQ0MTNiYiJ9.SEW5Cov5gnV6t8b1Rm5oMKF0rJ1bTd_CseBWSDXCYhW9dvKSJL6Zmy_LHydI31httZZUtuUcRWDq9dfDXdnbm3XDw0JKAEjT6H9NQ4umQW7xNxhjnFpj05tvBvP3iCHgTZEpDTJQXJ952rdIXwiu3kUtJneZ9SjI4dybaDC6hmlyvFhX788mCe4FILpZ9lmvJATVWOmcUfxzVk1PjjxqON-GL9ngjAsTv2ZIPKRKomxaJGp5azr8iyTJAi4yhiC72QQR_gXQy_5XofOGrE5U-R38pd0nxzlaD8yfCbhrhOnCwwCe6Z6YihJtzdU4t1oSsY9Y4PIEoCsgctPX39QF-w | string | 是 | DPoP证明JWT，用于绑定令牌与客户端密钥 |

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| grant_type | authorization_code | string | 是 | 授权类型，固定为authorization_code（授权码模式） |
| code | C3gEl6rsW_H2Y0UysTx1zxEO58kkBra06dm4AisjY2CJtAwTyCyOi7OpoZiXL7rOV5BaUXpxQI08Pi9NMI4ZSY_ERnv4GfGtdQ6yYJy00m0vsSbM1eCgAt5E_pYQi4zn | string | 是 | 授权码 |
| scope | profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| code_verifier | NzEwZmM1OWQtOWQwNC00YjliLTkyNTgtNGIwNzNjZjFlMWNk | string | 是 | PKCE代码验证器，与授权请求中的code_challenge对应 |


**响应示例**

* 成功(200)

```javascript
{
  "access_token": "eyJraWQiOiIwYTc3ZDQxOS0wM2FkLTQyOWEtYTQwMS1kOTNhMTkwMTlkMTAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IlFId3JHbXJIaEgwUk1nOW85OW1xbDd6YjZEbGhGaTR5IiwibmJmIjoxNzUxMjEwNTM3LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MjAxMzcsImlhdCI6MTc1MTIxMDUzNywianRpIjoiNTI5MmU2M2MtZWE2NS00ZTg3LWI4ZTYtYWVmYjZmMjhmNWM3In0.XsvnxXB794TK3w4WbpOBPlc_p9QfbGKXSCxXIPkyrTEJb6tWn3brcooslkxvxsmkwQefiND-EHeydQuB6IVYp0MK0srqOOPQqhBd_19Q6Sk7AigkTwDNnmx7kk_fbBrWFQgiZdbh5Nu0oImWXCI8xTqnzIpAEJyQG6SuYqSICBjVITbbZSrkJ2CRlXlGBu9Az9VKsHLdUgKXflPhYNlAGxPsL4MPQ1LeV2GFEFQzTKJKVG3DdNm_ZNUnRas0LimXcVuCO8rRVbI-Qu1pnuIvDkZ8VE3NO_wjLy-GjRwJ47HoZat0Htb9J0-PVRLZYbnN-bC2Oj7jgmjGc8SNF7NEdg",
  "refresh_token": "AZiNuuFdK_Rm805qrW2SyhXYFonXCPu6JzCyr07JAlzTOnDGu54PXpBiuiUrwWI_tj1hht43sfh0nWXfBm3bUiU1l5laVKIbhxya_1we2Ky70v944JhRNvWne76xwglX",
  "scope": "openid",
  "id_token": "eyJraWQiOiIwYTc3ZDQxOS0wM2FkLTQyOWEtYTQwMS1kOTNhMTkwMTlkMTAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IlFId3JHbXJIaEgwUk1nOW85OW1xbDd6YjZEbGhGaTR5IiwiYXpwIjoiUUh3ckdtckhoSDBSTWc5bzk5bXFsN3piNkRsaEZpNHkiLCJhdXRoX3RpbWUiOjE3NTEyMTA0MTIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjEyMzM4LCJpYXQiOjE3NTEyMTA1MzgsImp0aSI6IjNjYjQzYTQzLWMyZmItNGI2Zi1iZjI1LTI2YWNlZDI1M2JmMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.NPlk7McLlpC_xRVxa-efLNSQ2i3_5tMVdoiCjDqDtqWQCu5Nr9yRhQR9x85RuGJBuot5gOEeZKDoq1JJmTEeO2LalOQEx6JrSTCvjoIM8riI7XnqqDEBLw3kGP7Rh5YQZ1VyRSN2_hEYX_GTNp7TYpDsEnZpnSRRp8fUm1q3j9t1gJQX3UXCBc4w6FzIU-PjKgrIvup7y6mHa5_HZvQ9fgMNZd1DfSPiGlyGfk4kLqn8ajV_MGH9IekYyxGuKul1AkJYad7scZBu5lyBIaewoN0LXZYi1-BF-wz4qju3C-VSNl3f_tAed4ILXUWFf0MYqp0IHKx9ZFGuVwum6Yp_ig",
  "token_type": "Bearer",
  "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIwYTc3ZDQxOS0wM2FkLTQyOWEtYTQwMS1kOTNhMTkwMTlkMTAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IlFId3JHbXJIaEgwUk1nOW85OW1xbDd6YjZEbGhGaTR5IiwibmJmIjoxNzUxMjEwNTM3LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MjAxMzcsImlhdCI6MTc1MTIxMDUzNywianRpIjoiNTI5MmU2M2MtZWE2NS00ZTg3LWI4ZTYtYWVmYjZmMjhmNWM3In0.XsvnxXB794TK3w4WbpOBPlc_p9QfbGKXSCxXIPkyrTEJb6tWn3brcooslkxvxsmkwQefiND-EHeydQuB6IVYp0MK0srqOOPQqhBd_19Q6Sk7AigkTwDNnmx7kk_fbBrWFQgiZdbh5Nu0oImWXCI8xTqnzIpAEJyQG6SuYqSICBjVITbbZSrkJ2CRlXlGBu9Az9VKsHLdUgKXflPhYNlAGxPsL4MPQ1LeV2GFEFQzTKJKVG3DdNm_ZNUnRas0LimXcVuCO8rRVbI-Qu1pnuIvDkZ8VE3NO_wjLy-GjRwJ47HoZat0Htb9J0-PVRLZYbnN-bC2Oj7jgmjGc8SNF7NEdg | string | 访问令牌(JWT格式) |
| refresh_token | AZiNuuFdK_Rm805qrW2SyhXYFonXCPu6JzCyr07JAlzTOnDGu54PXpBiuiUrwWI_tj1hht43sfh0nWXfBm3bUiU1l5laVKIbhxya_1we2Ky70v944JhRNvWne76xwglX | string | 授权码获取token接口返回的refresh_token |
| scope | openid | string | 实际授予的授权范围 |
| id_token | eyJraWQiOiIwYTc3ZDQxOS0wM2FkLTQyOWEtYTQwMS1kOTNhMTkwMTlkMTAiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IlFId3JHbXJIaEgwUk1nOW85OW1xbDd6YjZEbGhGaTR5IiwiYXpwIjoiUUh3ckdtckhoSDBSTWc5bzk5bXFsN3piNkRsaEZpNHkiLCJhdXRoX3RpbWUiOjE3NTEyMTA0MTIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjEyMzM4LCJpYXQiOjE3NTEyMTA1MzgsImp0aSI6IjNjYjQzYTQzLWMyZmItNGI2Zi1iZjI1LTI2YWNlZDI1M2JmMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.NPlk7McLlpC_xRVxa-efLNSQ2i3_5tMVdoiCjDqDtqWQCu5Nr9yRhQR9x85RuGJBuot5gOEeZKDoq1JJmTEeO2LalOQEx6JrSTCvjoIM8riI7XnqqDEBLw3kGP7Rh5YQZ1VyRSN2_hEYX_GTNp7TYpDsEnZpnSRRp8fUm1q3j9t1gJQX3UXCBc4w6FzIU-PjKgrIvup7y6mHa5_HZvQ9fgMNZd1DfSPiGlyGfk4kLqn8ajV_MGH9IekYyxGuKul1AkJYad7scZBu5lyBIaewoN0LXZYi1-BF-wz4qju3C-VSNl3f_tAed4ILXUWFf0MYqp0IHKx9ZFGuVwum6Yp_ig | string | ID令牌(JWT格式)，包含用户身份信息 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Dpop | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJ0eXAiOiJkcG9wK2p3dCIsImFsZyI6IlJTMjU2IiwiandrIjp7Imt0eSI6IlJTQSIsImUiOiJBUUFCIiwia2lkIjoiZjgyMWQ2OTItODI0Ny00ZmNiLThhMzAtNTc2YjkxZDc5ODc3IiwibiI6InJiMHhVekFpTmg2b3I5Rlp2cDJ6OVN3dlVmM2pDY3dHS3ZsNU5uSmpNM2dJM0VlaFNEbnU2UGlHcVNPd01LTm1JN0J1NnlmM3dnZ2dmdU55UVhVdGtxeVN4V0NpVWRyZFBEemRCRFNhOHdxN0V4VnVZUXcyU04yQ3FTNTVBTVdBdUp5Y2s5bnFtUVl5OHlYZWR0Wjgtdk1ZYXNFa190MEMwelNRQXd4MEx1dEJRV2JOUmJ3LUlEaUNaNU1kendWYXlRbDA1NHZ1OEdwcTVNekt4Z0lkMXdKSGk2MDJjSm9KZG9fYXhXOUhVS1JYMFNmeTh4b1FVZXRmaGxPcXRLVlVhVGtDd29UMkpBTTRsdHNJRDI1c2duWWpYY3BEYjM3UW8wVzNMLTRNemlTalpuU3NhOG5UeHBsRDFsdlBpTU5Yek5sZDlmeWVQV3BHYTNWMlZPU0hWdyJ9fQ.eyJodG0iOiJQT1NUIiwiaHR1IjoiaHR0cDovL2xvY2FsaG9zdDo4ODkwL2R1Y2Evb2F1dGgyL3Rva2VuIiwiZXhwIjoxNzY0NzU2OTMwLCJpYXQiOjE3NTEyMTAzNTcsImp0aSI6ImYzZTEwYTZkLTVlOTctNGU1MS04MzQ5LTdmZjlmODQ0MTNiYiJ9.SEW5Cov5gnV6t8b1Rm5oMKF0rJ1bTd_CseBWSDXCYhW9dvKSJL6Zmy_LHydI31httZZUtuUcRWDq9dfDXdnbm3XDw0JKAEjT6H9NQ4umQW7xNxhjnFpj05tvBvP3iCHgTZEpDTJQXJ952rdIXwiu3kUtJneZ9SjI4dybaDC6hmlyvFhX788mCe4FILpZ9lmvJATVWOmcUfxzVk1PjjxqON-GL9ngjAsTv2ZIPKRKomxaJGp5azr8iyTJAi4yhiC72QQR_gXQy_5XofOGrE5U-R38pd0nxzlaD8yfCbhrhOnCwwCe6Z6YihJtzdU4t1oSsY9Y4PIEoCsgctPX39QF-w | string | 是 | DPoP证明JWT，用于绑定令牌与客户端密钥 |


### 刷新TOKEN

**接口描述**

> OAuth 2.0 刷新令牌（Refresh Token）。客户端在 access_token 过期后，使用 refresh_token 换取新的 access_token 及可选的 refresh_token（轮换）。遵循 [RFC 6749 Section 6](https://tools.ietf.org/html/rfc6749#section-6)。

**先调用/duca/oauth/authorize接口**


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | refresh_token | string | 是 | 授权类型，固定为refresh_token（刷新令牌） |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| refresh_token | {{refresh_token}} | string | 是 | 授权码获取token接口返回的refresh_token |


**响应示例**

* 成功(200)

```javascript
{"access_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA4MTYxLCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc3NjEsImlhdCI6MTc1MTIwODE2MSwianRpIjoiMGEyMzUxMTAtMzNiZC00NmJhLThlOTUtYzY5ZWFkYWU5YTRmIn0.P7pSMOvK2bc7n6x2HYEFGZsp-wDlw8NI3s36ZRTmWjAiuzArNzEahUoDRssKN0JuxGB-FpEhz38c5PteZSWzm98bDryKyXH_F5cK4Vxw79ZYvQBsibYd43X-G1GYvY2DfUaxV_M6ryZUJ-CCgC7IaI-OdGOblG19rWK_8qv_W69MoCPsSdHR5meLZNe7Yu2ddG0cMsmfmMzxgXF1zcGtc1w-YdqcwD7qA4WAWMfGPhhkULLtZX44p5un0CU638tgyuC4IV1UqslNkaYoXoINHMOLShAPoljCk5_nBuUQ_8Wc4P4LkYMgOlQIgyePua7CdLJ_aohDw8WXhDF9ETDN4w","refresh_token":"viGZ8-6pEnaq7GlLcPwMWOEO4101c_EeA0TWX6dnWNdlqScJIyBdEHUvzgJFNyxrDXq41Yj8ScBpik83AptFLYmAQgxn-l1WEZA7fyglvUiSG-vH0dZkxNRZzI9fEUQ8","scope":"openid","id_token":"eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDgxMDAsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5OTYxLCJpYXQiOjE3NTEyMDgxNjEsImp0aSI6IjAyZDdjMTBlLTc4NjgtNGJiZi05NmQwLTJlYWMyNzY2ZWUwOSIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.bvC6KNKUQQ1HzCsJbC-_WLFQQsNWc9mF92SlLM-QbjXbF6yZr0vDoihM7AkCBklZGr3LRS8KpnwrrYdLbr-Z_vG0wKL-IR0_d8HTPBQOK41avgMlTvrRcV-gH0zwAZ_2_L0-vmtkzqshatZfLCV5aORfjhI0ob2GfGfruXWL2QfMl06AquMSxX7rboYf1-ZYB7XD6VW9YyM5FNYr6kEb7fpok_jFMI_o7BAY7BImZRNhnwYCrAOM-UL0H0yJoati8IfKIoVr0KrN6gtZs7sDQVQwAHmRr5cZjdjit-PeOr97GODgv3J0uTGRSpwaq8g4hRdFnsNUZ5huVEoLxe6NlA","token_type":"Bearer","expires_in":1209600}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA4MTYxLCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc3NjEsImlhdCI6MTc1MTIwODE2MSwianRpIjoiMGEyMzUxMTAtMzNiZC00NmJhLThlOTUtYzY5ZWFkYWU5YTRmIn0.P7pSMOvK2bc7n6x2HYEFGZsp-wDlw8NI3s36ZRTmWjAiuzArNzEahUoDRssKN0JuxGB-FpEhz38c5PteZSWzm98bDryKyXH_F5cK4Vxw79ZYvQBsibYd43X-G1GYvY2DfUaxV_M6ryZUJ-CCgC7IaI-OdGOblG19rWK_8qv_W69MoCPsSdHR5meLZNe7Yu2ddG0cMsmfmMzxgXF1zcGtc1w-YdqcwD7qA4WAWMfGPhhkULLtZX44p5un0CU638tgyuC4IV1UqslNkaYoXoINHMOLShAPoljCk5_nBuUQ_8Wc4P4LkYMgOlQIgyePua7CdLJ_aohDw8WXhDF9ETDN4w | string | 访问令牌(JWT格式) |
| refresh_token | viGZ8-6pEnaq7GlLcPwMWOEO4101c_EeA0TWX6dnWNdlqScJIyBdEHUvzgJFNyxrDXq41Yj8ScBpik83AptFLYmAQgxn-l1WEZA7fyglvUiSG-vH0dZkxNRZzI9fEUQ8 | string | 授权码获取token接口返回的refresh_token |
| scope | openid | string | 实际授予的授权范围 |
| id_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDgxMDAsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5OTYxLCJpYXQiOjE3NTEyMDgxNjEsImp0aSI6IjAyZDdjMTBlLTc4NjgtNGJiZi05NmQwLTJlYWMyNzY2ZWUwOSIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.bvC6KNKUQQ1HzCsJbC-_WLFQQsNWc9mF92SlLM-QbjXbF6yZr0vDoihM7AkCBklZGr3LRS8KpnwrrYdLbr-Z_vG0wKL-IR0_d8HTPBQOK41avgMlTvrRcV-gH0zwAZ_2_L0-vmtkzqshatZfLCV5aORfjhI0ob2GfGfruXWL2QfMl06AquMSxX7rboYf1-ZYB7XD6VW9YyM5FNYr6kEb7fpok_jFMI_o7BAY7BImZRNhnwYCrAOM-UL0H0yJoati8IfKIoVr0KrN6gtZs7sDQVQwAHmRr5cZjdjit-PeOr97GODgv3J0uTGRSpwaq8g4hRdFnsNUZ5huVEoLxe6NlA | string | ID令牌(JWT格式)，包含用户身份信息 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token 内省

**接口描述**

> OAuth 2.0 令牌内省（Token Introspection）。资源服务器将收到的 access_token 发送到此端点，验证令牌的有效性并获取令牌的元数据（如 sub、aud、exp、scope 等）。遵循 [RFC 7662](https://tools.ietf.org/html/rfc7662)。

**先调用/duca/oauth/authorize接口**


**接口URL**

> /duca/oauth2/introspect

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| token | {{access_token}} | string | 是 | access_token |


**响应示例**

* 成功(200)

```javascript
{
    "active": true,
    "sub": "DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s",
    "aud": [
        "DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s"
    ],
    "nbf": 1751203631,
    "iss": "http://localhost:8188/duca",
    "exp": 1752413232,
    "iat": 1751203632,
    "jti": "2ec69692-661f-4fec-98e4-38950ea748fc",
    "client_id": "DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s",
    "token_type": "Bearer"
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| active | true | boolean | 令牌是否有效 |
| sub | DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s | string | 令牌主体标识（Subject），即用户或客户端的唯一ID |
| aud | - | array | 令牌的受众（Audience）列表 |
| nbf | 1751203631 | number | 令牌生效时间（Not Before），Unix时间戳（秒） |
| iss | http://localhost:8188/duca | string | 令牌颁发者（Issuer） |
| exp | 1752413232 | number | 令牌过期时间（Expiration），Unix时间戳（秒） |
| iat | 1751203632 | number | 令牌签发时间（Issued At），Unix时间戳（秒） |
| jti | 2ec69692-661f-4fec-98e4-38950ea748fc | string | 令牌唯一标识（JWT ID） |
| client_id | DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s | string | 应用编号 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### Token 吊销

**接口描述**

> OAuth 2.0 令牌吊销（Token Revocation）。客户端主动使一个 access_token 或 refresh_token 失效，令牌被吊销后将无法再通过内省验证。遵循 [RFC 7009](https://tools.ietf.org/html/rfc7009)。

**先调用/duca/oauth/authorize接口**


**接口URL**

> /duca/oauth2/revoke

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| token | {{access_token}} | string | 是 | 待内省的访问令牌或刷新令牌 |


**响应示例**

* 成功(200)

```javascript
{
    "active": true,
    "sub": "DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s",
    "aud": [
        "DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s"
    ],
    "nbf": 1751203631,
    "iss": "http://localhost:8188/duca",
    "exp": 1752413232,
    "iat": 1751203632,
    "jti": "2ec69692-661f-4fec-98e4-38950ea748fc",
    "client_id": "DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s",
    "token_type": "Bearer"
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| active | true | boolean | 令牌是否有效 |
| sub | DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s | string | 令牌主体标识（Subject），即用户或客户端的唯一ID |
| aud | - | array | 令牌的受众（Audience）列表 |
| nbf | 1751203631 | number | 令牌生效时间（Not Before），Unix时间戳（秒） |
| iss | http://localhost:8188/duca | string | 令牌颁发者（Issuer） |
| exp | 1752413232 | number | 令牌过期时间（Expiration），Unix时间戳（秒） |
| iat | 1751203632 | number | 令牌签发时间（Issued At），Unix时间戳（秒） |
| jti | 2ec69692-661f-4fec-98e4-38950ea748fc | string | 令牌唯一标识（JWT ID） |
| client_id | DrBIoSS6Onqz8Ulbd0Owa1bpXAM0uG6s | string | 应用编号 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### PAR-1 推送授权请求

**接口描述**

> OAuth 2.0 推送授权请求（Pushed Authorization Requests, PAR）。客户端将授权参数通过安全的后端通道预先推送到此端点，获取一个 request_uri，后续授权请求仅传递该 URI 即可，避免敏感参数暴露在浏览器 URL 中。遵循 [RFC 9126](https://tools.ietf.org/html/rfc9126)。

**先调用/duca/oauth/authorize接口**


**接口URL**

> /duca/oauth2/par

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 应用编号 |
| client_secret | {{client_secret}} | string | 是 | 应用密钥 |
| response_type | code | string | 是 | 必须是code |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 授权成功后的回调地址 |
| scope | openid profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |
| state | xyz1223 | string | 否 | 客户端随机字符串，防CSRF，推荐使用 |
| nonce | 11222 | string | 否 | 客户端随机字符串，OIDC规范要求的ID Token防重放参数 |


**响应示例**

* 成功(200)

```javascript
{
    "request_uri": "urn:ietf:params:oauth:request_uri:V6M4-nLlxauOClIFGFLQP37XjBXz_idjq6bu7oXdKv4=___1751276966049",
    "expires_in": 29
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| request_uri | urn:ietf:params:oauth:request_uri:V6M4-nLlxauOClIFGFLQP37XjBXz_idjq6bu7oXdKv4=___1751276966049 | string | PAR请求URI，用于引用已推送的授权请求 |
| expires_in | 29 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### PAR-2 通过 request_uri 获取授权码(浏览器访问)

**接口描述**

> PAR 流程第二步。浏览器访问授权端点时不再传递敏感参数，仅携带 PAR-1 返回的 request_uri。授权服务器根据 request_uri 还原授权请求，用户登录授权后回调授权码。遵循 [RFC 9126](https://tools.ietf.org/html/rfc9126)。

**先调用/duca/oauth/authorize接口**


**接口URL**

> /duca/oauth2/authorize?client_id={{clientId}}&request_uri={{reuqest_uri}}

**请求方式**

> GET

**Content-Type**

> urlencoded

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 客户端ID/应用编号 |
| request_uri | {{reuqest_uri}} | string | 是 | PAR请求URI，由上一步PAR端点返回的唯一标识 |


**响应示例**

* 成功(200)

```javascript
{
    "request_uri": "urn:ietf:params:oauth:request_uri:V6M4-nLlxauOClIFGFLQP37XjBXz_idjq6bu7oXdKv4=___1751276966049",
    "expires_in": 29
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| request_uri | urn:ietf:params:oauth:request_uri:V6M4-nLlxauOClIFGFLQP37XjBXz_idjq6bu7oXdKv4=___1751276966049 | string | PAR请求URI，用于引用已推送的授权请求 |
| expires_in | 29 | number | 令牌有效期(秒) |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```


### PAR-3 通过授权码获取 Token

**接口描述**

> PAR 流程第三步。使用授权码兑换 access_token 和 id_token，与标准授权码模式的 Token 端点一致。


**接口URL**

> /duca/oauth2/token

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | authorization_code | string | 是 | 授权类型，固定为authorization_code（授权码模式） |
| redirect_uri | http://localhost:8187/duca/callback | string | 是 | 回调地址 |
| code | rJgo2E8bli1pULyDjn | string | 是 | 授权码 |


**响应示例**

* (404)

```javascript
{
	"access_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ",
	"refresh_token": "FuXYzlid3rbwuHqiEXNRtWeuM8U3pc4FD9CG-E2DXJA9xKv5n7T26bmjw5UBszV5UXfUn2sptSx0TBXE_hkNCg5ByoAs4DOHQDE39d7HLJe8MM3orinI4WnDeCBO23Np",
	"scope": "openid",
	"id_token": "eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDc2NjEsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5NzA4LCJpYXQiOjE3NTEyMDc5MDgsImp0aSI6ImEyMWE4Yzc0LTk0OWEtNGJkNi04YzhmLWFhYmYwYzQzNjNlMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.ShAxLD54A0Z3HYXJEt4yGRIJwt0bqyUGZykA_q2sUFljxNEs5CmVk4kUiW1ZGddxI60Gn7_E10OnFva743p23MDs0pDArwvZ4tmmcsWuYIn4OgBBA631J81Mm3buQHUyT76FSJo4eungyZeFbYCqHKvCb962ihS-EKb5LGe3w9MdxRaofjd9g19BhXqr6gMnNxPlWneREeql4-HUT6dG2tlpshGY0NzZrMyDjdhvd9uTLsJdM6XW9WAAYklFLnXmZuh0-XnA56Kmrw_KVxhTj-JPdD0ZhWwnybmq_aPGSS3pLQ1FyBsZI28ANgP4JNe0ONN76oUcCZWRNm073TmViA",
	"token_type": "Bearer",
	"expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwibmJmIjoxNzUxMjA3OTA4LCJzY29wZSI6WyJvcGVuaWQiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MTg4L2R1Y2EiLCJleHAiOjE3NTI0MTc1MDgsImlhdCI6MTc1MTIwNzkwOCwianRpIjoiMjU5ZGZkYzktZWRiMi00MjRiLWIxN2ItMDUwMjMzNTk4NWEzIn0.oawdoJyq5vCY2aR2XPh7TOvXOfMDoXwwHKL-YHwDnzetSJyFvm0DXSerHEGxd7cUD4Vr9QEAvBUtQ9OD1YBBaghYP0tgY4_nDya7896VBAR1f5Fci2A7N7ZWh998ZmO-eHV124e-c4kdCnZ-WrifC6zwAM31nrMWxnZEfoKkoWnlGk-Ql4vL6_w-Tt1Dl4GGucmfnIxFyicatU5_8N5gC6Sp3iB2fSoVusOyb6fx6Q1qf7XNpB3xbb_6UFK9bZgq2sHKMXKYIy7WGtszRNu-Ccs_QssIf3b-jisxOzeSqDi8FldpIOPjLynvqRZqvbVp5fnM4UV__MzyY4zCW_4PuQ | string | 访问令牌(JWT格式) |
| refresh_token | FuXYzlid3rbwuHqiEXNRtWeuM8U3pc4FD9CG-E2DXJA9xKv5n7T26bmjw5UBszV5UXfUn2sptSx0TBXE_hkNCg5ByoAs4DOHQDE39d7HLJe8MM3orinI4WnDeCBO23Np | string | 授权码获取token接口返回的refresh_token |
| scope | openid | string | 实际授予的授权范围 |
| id_token | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXpwIjoiRHJCSW9TUzZPbnF6OFVsYmQwT3dhMWJwWEFNMHVHNnMiLCJhdXRoX3RpbWUiOjE3NTEyMDc2NjEsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUxMjA5NzA4LCJpYXQiOjE3NTEyMDc5MDgsImp0aSI6ImEyMWE4Yzc0LTk0OWEtNGJkNi04YzhmLWFhYmYwYzQzNjNlMCIsInNpZCI6Ilo0YXN3X0RvVFVoZEdlQmxOQXV3Sl8zSVJNNVYxdEpqTFBCeTJGQTY3cEEifQ.ShAxLD54A0Z3HYXJEt4yGRIJwt0bqyUGZykA_q2sUFljxNEs5CmVk4kUiW1ZGddxI60Gn7_E10OnFva743p23MDs0pDArwvZ4tmmcsWuYIn4OgBBA631J81Mm3buQHUyT76FSJo4eungyZeFbYCqHKvCb962ihS-EKb5LGe3w9MdxRaofjd9g19BhXqr6gMnNxPlWneREeql4-HUT6dG2tlpshGY0NzZrMyDjdhvd9uTLsJdM6XW9WAAYklFLnXmZuh0-XnA56Kmrw_KVxhTj-JPdD0ZhWwnybmq_aPGSS3pLQ1FyBsZI28ANgP4JNe0ONN76oUcCZWRNm073TmViA | string | ID令牌(JWT格式)，包含用户身份信息 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### DEVICE-1 设备授权请求

**接口描述**

> OAuth 2.0 设备授权流程（Device Authorization Grant）第一步。无法使用浏览器的设备（如智能电视、IoT 设备）向此端点发起授权请求，获取 device_code 和 user_code，用户需在另一设备上访问 verification_uri 完成授权。遵循 [RFC 8628](https://tools.ietf.org/html/rfc8628)。



**接口URL**

> /duca/oauth2/device_authorization

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Basic {{authorzation}} | string | 是 | base64(clientId:clientSecret) |

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | {{clientId}} | string | 是 | 客户端ID/应用编号 |
| scope | openid profile | string | 是 | 请求的授权范围，多个scope用空格分隔 |


**响应示例**

* (404)

```javascript
{
	"user_code": "TKDD-PHTB",
	"device_code": "RygL-sxnY4DvBi1MiGQtkcLoi1rZFHo1yl4al6m36NKlGSqTZwi3KCQHKUj5-bRFiHd7FpIUaGgEZIHa9K_NWVhSfzGxgJxRsFir3cAkOF5owgFl41nB5TTdgl0BjpT_",
	"verification_uri_complete": "http://localhost:8188/duca/activate?user_code=TKDD-PHTB",
	"verification_uri": "http://localhost:8188/duca/activate",
	"expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| user_code | TKDD-PHTB | string | 用户验证码，用户在授权页面输入此码完成授权 |
| device_code | RygL-sxnY4DvBi1MiGQtkcLoi1rZFHo1yl4al6m36NKlGSqTZwi3KCQHKUj5-bRFiHd7FpIUaGgEZIHa9K_NWVhSfzGxgJxRsFir3cAkOF5owgFl41nB5TTdgl0BjpT_ | string | 设备授权码，用于轮询令牌端点 |
| verification_uri_complete | http://localhost:8188/duca/activate?user_code=TKDD-PHTB | string | 附带user_code的完整验证URL |
| verification_uri | http://localhost:8188/duca/activate | string | 用户验证页面URL |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Basic {{authorzation}} | string | 是 | base64(clientId:clientSecret) |


### DEVICE-2 用户授权(浏览器访问)

**接口描述**

> 设备授权流程第二步。用户在手机或电脑浏览器中访问 verification_uri，输入 DEVICE-1 返回的 user_code，确认授权后设备即可通过 device_code 轮询获取 Token。遵循 [RFC 8628](https://tools.ietf.org/html/rfc8628)。



**接口URL**

> duca/activate?client_id=vVXNNL4vPziXqimZmbXzI6eXaBuaqbCG&user_code=VPWS-NBKG

**请求方式**

> GET

**Content-Type**

> urlencoded

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| client_id | vVXNNL4vPziXqimZmbXzI6eXaBuaqbCG | string | 是 | 客户端ID/应用编号 |
| user_code | VPWS-NBKG | string | 是 | URL中没有携带的话需要在激活页面手动填写 |


**响应示例**

* (404)

```javascript
{
	"user_code": "TKDD-PHTB",
	"device_code": "RygL-sxnY4DvBi1MiGQtkcLoi1rZFHo1yl4al6m36NKlGSqTZwi3KCQHKUj5-bRFiHd7FpIUaGgEZIHa9K_NWVhSfzGxgJxRsFir3cAkOF5owgFl41nB5TTdgl0BjpT_",
	"verification_uri_complete": "http://localhost:8188/duca/activate?user_code=TKDD-PHTB",
	"verification_uri": "http://localhost:8188/duca/activate",
	"expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| user_code | TKDD-PHTB | string | 用户验证码，用户在授权页面输入此码完成授权 |
| device_code | RygL-sxnY4DvBi1MiGQtkcLoi1rZFHo1yl4al6m36NKlGSqTZwi3KCQHKUj5-bRFiHd7FpIUaGgEZIHa9K_NWVhSfzGxgJxRsFir3cAkOF5owgFl41nB5TTdgl0BjpT_ | string | 设备授权码，用于轮询令牌端点 |
| verification_uri_complete | http://localhost:8188/duca/activate?user_code=TKDD-PHTB | string | 附带user_code的完整验证URL |
| verification_uri | http://localhost:8188/duca/activate | string | 用户验证页面URL |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```


### DEVICE-3 轮询获取 Token

**接口描述**

> 设备授权流程第三步。设备使用 device_code 轮询令牌端点。用户在 DEVICE-2 完成授权前返回 authorization_pending，授权成功后返回 access_token。遵循 [RFC 8628](https://tools.ietf.org/html/rfc8628)。



**接口URL**

> /duca/oauth2/device_authorization

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Basic {{authorzation}} | string | 是 | 认证令牌/授权头 |

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| grant_type | urn:ietf:params:oauth:grant-type:device_code | string | 是 | 授权类型，固定为device_code（设备授权） |
| device_code | yybP5CqZ8OokpjLd4GhavtvrcTW3R_ERheTQg4q6QLRfxa6yS3djYG5ejSVGrMa1HH1CefTkEX9box08E_m-ZrgQvITTzVRj9h1fmHFfwt-dxOZhBFLVUFH3dIg_00jI | string | 是 | 设备授权码 DEVICE-1 返回 |


**响应示例**

* (404)

```javascript
{
  "access_token": "eyJraWQiOiIzYzNiMTZhMy05NDcwLTQ2YzYtYTAyMy02ODQ3ZDBhYmYyYzMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6InZWWE5OTDR2UHppWHFpbVptYlh6STZlWGFCdWFxYkNHIiwibmJmIjoxNzUxMjgzOTk3LCJzY29wZSI6WyJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUyNDkzNTk3LCJpYXQiOjE3NTEyODM5OTcsImp0aSI6IjdhODU3MTliLWFjMGItNDZjZS1iZDA0LWY4YTExYWU0MmZhMyJ9.TnyXbdn5SmBb9jGZxW7PuFh3S-wIc8WxwbPnEz1xbB6vehcF1pJgv6QTAdDtreseXYdEgroJVEOjEzEIcBkCNpkh44rNDAhwsOubwbTOjqGwlU7hWitum7rN2nAdKSxsKaLFpPrvy1BF8HPqF9mmLdfIcLVuM8hRGqJ_L-CBqJA-nlipNv-UH0WR-JHwYeU4IOq4KcNu9Xwbe55N9qQj6NENJBaUQfO1HK0uGbomntFfOt9jIp3qvJQCrZ2gEMbNhzyy8TGlmA3MPJPqZNwcPe2Vf170tkvbjvDPbIFAfnQZG1HyaYVz6pnZl3FnRbfP9V-Q6Yv8GlOqpFdJFOw7HQ",
  "refresh_token": "1qTVuhDVD92WQoK2eIztadLrN7Z8ee5abyifAl7NEvFVSNFbJjoIJyuiBypLvbRXshz2VFaK3Y29XEjGc9mFW8x_Y6zJ4FvcKOOR_XhoeH61ogwbLu4BWUDc5hF7NnxJ",
  "scope": "profile",
  "token_type": "Bearer",
  "expires_in": 1209600
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| access_token | eyJraWQiOiIzYzNiMTZhMy05NDcwLTQ2YzYtYTAyMy02ODQ3ZDBhYmYyYzMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImF1ZCI6InZWWE5OTDR2UHppWHFpbVptYlh6STZlWGFCdWFxYkNHIiwibmJmIjoxNzUxMjgzOTk3LCJzY29wZSI6WyJwcm9maWxlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODE4OC9kdWNhIiwiZXhwIjoxNzUyNDkzNTk3LCJpYXQiOjE3NTEyODM5OTcsImp0aSI6IjdhODU3MTliLWFjMGItNDZjZS1iZDA0LWY4YTExYWU0MmZhMyJ9.TnyXbdn5SmBb9jGZxW7PuFh3S-wIc8WxwbPnEz1xbB6vehcF1pJgv6QTAdDtreseXYdEgroJVEOjEzEIcBkCNpkh44rNDAhwsOubwbTOjqGwlU7hWitum7rN2nAdKSxsKaLFpPrvy1BF8HPqF9mmLdfIcLVuM8hRGqJ_L-CBqJA-nlipNv-UH0WR-JHwYeU4IOq4KcNu9Xwbe55N9qQj6NENJBaUQfO1HK0uGbomntFfOt9jIp3qvJQCrZ2gEMbNhzyy8TGlmA3MPJPqZNwcPe2Vf170tkvbjvDPbIFAfnQZG1HyaYVz6pnZl3FnRbfP9V-Q6Yv8GlOqpFdJFOw7HQ | string | 访问令牌(JWT格式) |
| refresh_token | 1qTVuhDVD92WQoK2eIztadLrN7Z8ee5abyifAl7NEvFVSNFbJjoIJyuiBypLvbRXshz2VFaK3Y29XEjGc9mFW8x_Y6zJ4FvcKOOR_XhoeH61ogwbLu4BWUDc5hF7NnxJ | string | 刷新令牌，用于获取新的access_token |
| scope | profile | string | 实际授予的授权范围 |
| token_type | Bearer | string | 令牌类型，固定为Bearer |
| expires_in | 1209600 | number | 令牌有效期(秒) |

* 失败(404)

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Basic {{authorzation}} | string | 是 | 认证令牌/授权头 |


### UserInfo 端点

**接口描述**

> OpenID Connect UserInfo 端点。客户端使用 access_token 获取当前登录用户的身份声明（Claims），如 sub、name、email、picture 等。scope 需包含 openid，返回标准 OIDC 声明及扩展属性。遵循 [OpenID Connect Core 1.0 Section 5.3](https://openid.net/specs/openid-connect-core-1_0.html#UserInfo)。

**access_token 是通过auth code 模式获取的
scope 需要包含openid**


**接口URL**

> /duca/userinfo

**请求方式**

> GET

**Content-Type**

> none

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 |  |


**响应示例**

* 成功(200)

```javascript
{
    "sub": "wanghailong",
    "email": "wanghailong@dayu-cloud.com",
    "email_verified": true,
    "phone_number": "18595808972",
    "phone_number_verified": true,
    "name": "wanghailong",
    "nickname": "王海龙",
    "picture": "/duca/static/avatar/7abd0001-b0c5-4982-9b77-e8e3454f3476.jpeg",
    "gender": null,
    "uuid": "ed0e4b27-0ff8-425e-84f7-424c90af71f3",
    "status": 1,
    "createDate": null,
    "updated_at": "2022-12-08 20:00:54",
    "group": []
}
```

| 参数名 | 示例值 | 参数类型 | 参数描述 |
| --- | --- | ---- | ---- |
| sub | wanghailong | string | 令牌主体标识（Subject），即用户或客户端的唯一ID |
| email | wanghailong@dayu-cloud.com | string | 邮箱 |
| email_verified | true | boolean | 邮箱是否已验证 |
| phone_number | 18595808972 | string | 手机号码（OIDC标准声明） |
| phone_number_verified | true | boolean | 手机号是否已验证 |
| name | wanghailong | string | 昵称 |
| nickname | 王海龙 | string | 昵称 |
| picture | /duca/static/avatar/7abd0001-b0c5-4982-9b77-e8e3454f3476.jpeg | string | 用户头像URL（OIDC标准声明） |
| gender | - | null | 性别（OIDC标准声明） |
| uuid | ed0e4b27-0ff8-425e-84f7-424c90af71f3 | string | 用户唯一标识 |
| status | 1 | number | 用户状态 |
| createDate | - | null | 创建时间 |
| updated_at | 2022-12-08 20:00:54 | string | 最后更新时间 |
| group | - | array | 用户所属的群组列表 |

* 失败

```javascript
{"code":404,"msg":"接口地址错误"}
```

**请求Header参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| Authorization | Bearer {{access_token}} | string | 是 |  |


### RP发起退出(POST)

**接口描述**

> OpenID Connect RP-Initiated Logout（依赖方发起退出）。客户端通过 POST 方式提交 id_token_hint，认证服务器验证后清除用户会话，并可重定向到 post_logout_redirect_uri。遵循 [OpenID Connect Session Management 1.0](https://openid.net/specs/openid-connect-session-1_0.html)。

**id_token_hint是通过授权码获取的id_token,退出请求成功后会重定向到post_logout_redirect_uri,参数post_logout_redirect_uri的值一定要是客户端设置的退出回调中的地址**


**接口URL**

> /duca/connect/logout

**请求方式**

> POST

**Content-Type**

> urlencoded

**请求Body参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| id_token_hint | eyJraWQiOiIzOTRkNDcxMS0wZjAxLTRkNWMtOTc5My0wNDExYTc4Y2MzZTMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3YW5naGFpbG9uZyIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg5MC9kdWNhIiwiYXZhdGFyIjoiL2R1Y2Evc3RhdGljL2F2YXRhci83YWJkMDAwMS1iMGM1LTQ5ODItOWI3Ny1lOGUzNDU0ZjM0NzYuanBlZyIsInVzZXJOYW1lIjoid2FuZ2hhaWxvbmciLCJ1dWlkIjoiZWQwZTRiMjctMGZmOC00MjVlLTg0ZjctNDI0YzkwYWY3MWYzIiwic2lkIjoielRycldaYkx2V1VDRVhITUVYTE1LbDk3Y0ctYkFLN1B1WTQ1YjdpbW5fRSIsImF1ZCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwicGhvbmUiOiIxODU5NTgwODk3MiIsImF6cCI6IkRyQklvU1M2T25xejhVbGJkME93YTFicFhBTTB1RzZzIiwiYXV0aF90aW1lIjoxNzYxMzg0MDY1LCJuYW1lIjoi546L5rW36b6ZIiwiaWQiOjEsImV4cCI6MTc2MTM4NTg3OSwiaWF0IjoxNzYxMzg0MDc5LCJqdGkiOiJmZTZjODI2OS0xMTE5LTQ1YmMtYmMzMS03NmEyMDY0ZTcxZTkiLCJlbWFpbCI6IndhbmdoYWlsb25nQGRheXUtY2xvdWQuY29tIn0.A1wGzssP630bGt0DtXLDoXMCQz2IRnfnGuMCQOhGiWXCTaTHYFMtkZdGEx_C1rRmYvt4BeQzqNC4vOhWrluCt3w857i2O5DNhfLcq6YeVXQrhCVu1_RnCWBedabAmN-NKCKngoVhVCa3XAbAYgPG7ufPJCKCM0YEMPP6FKX0A_OTxh0yKU3SE4mS1CHAlKkRnpUgsNtxp4DecntzwP8ugykGtPBUyLiZ2u-_Uaq3Mi_4jLe5GeNX-yV8R0Bu_BgugHv15MJRA-8nfxavP8I1qog03O9FQmkmAI4ThAyC8m4fsh5rLTe6xbpZq0f56jlj3rowzwceQlXKPCrPPFRSGA | string | 是 | id_token |
| client_id | {{clientId}} | string | 否 | 应用编号 |
| post_logout_redirect_uri | - | string | 否 | 退出后跳转回调 |
| state | - | string | 否 | 客户端随机字符串，防CSRF，推荐使用 |


### RP发起退出(GET)

**接口描述**

> OpenID Connect RP-Initiated Logout（依赖方发起退出）GET 方式。参数通过 Query String 传递，适用于将用户浏览器直接重定向到退出端点。

**id_token_hint是通过授权码获取的id_token,退出请求成功后会重定向到post_logout_redirect_uri,参数post_logout_redirect_uri的值一定要是客户端设置的退出回调中的地址**


**接口URL**

> /duca/connect/logout?id_token_hint={{id_token}}&client_id={{clientId}}&post_logout_redirect_uri=http://localhost:8187/duca/logout&state=aaa

**请求方式**

> GET

**Content-Type**

> urlencoded

**请求Query参数**

| 参数名 | 示例值 | 参数类型 | 是否必填 | 参数描述 |
| --- | --- | ---- | ---- | ---- |
| id_token_hint | {{id_token}} | string | 是 | id_token |
| client_id | {{clientId}} | string | 是 | 客户端ID/应用编号 |
| post_logout_redirect_uri | http://localhost:8187/duca/logout | string | 是 | 退出登录后重定向的URI |
| state | aaa | string | 是 | 客户端生成的随机字符串，用于防止CSRF攻击 |


  
