package com.jmeter.aliyun.sign.constant;


/**
 * @Author zhangmaosong
 * @create 2021-12-01 17:41
 */
public class AliyunGatewayHttpConstants {

    //===============
    //XXX HTTP Schema常量
    //===============
    /** <pre>HTTP</pre> */
    public static final String HTTP = "http://";
    /** <pre>HTTPS</pre> */
    public static final String HTTPS = "https://";


    /** <pre>签名算法HmacSha256</pre> */
    public static final String HMAC_SHA256 = "HmacSHA256";

    /** <pre>默认-连接池获取到连接的超时时间-毫秒数</pre> */
    public static final int POOLING_REQUESTT_TIMEOUT = 2000;

    /** <pre>默认-建立连接的超时-毫秒数</pre> */
    public static final int HTTP_REQUEST_CONNECT_TIMEOUT = 3000;

    /** <pre>默认-获取数据的超时时间-毫秒数</pre> */
    public static final int HTTP_REQUEST_SOCKET_TIMEOUT = 30 * 1000;

    /** <pre>参与签名的系统Header前缀,只有指定前缀的Header才会参与到签名中</pre> */
    public static final String CA_HEADER_TO_SIGN_PREFIX_SYSTEM = "X-Ca-";











    //===============
    //XXX 系统HTTP头常量-参与加签
    //===============
    /** <pre>签名Header</pre> */
    public static final String X_CA_SIGNATURE = "X-Ca-Signature";

    /** <pre>所有参与签名的Header</pre> */
    public static final String X_CA_SIGNATURE_HEADERS = "X-Ca-Signature-Headers";

    /** <pre>请求时间戳</pre> */
    public static final String X_CA_TIMESTAMP = "X-Ca-Timestamp";

    /** <pre>请求放重放Nonce,15分钟内保持唯一,建议使用UUID</pre> */
    public static final String X_CA_NONCE = "X-Ca-Nonce";

    /** <pre>APP KEY</pre> */
    public static final String X_CA_KEY = "X-Ca-Key";

    //===============
    //XXX HTTP头常量
    //===============
    /** <pre>请求Header Accept</pre> */
    public static final String HTTP_HEADER_ACCEPT = "Accept";
    /** <pre>请求Body内容MD5 Header</pre> */
    public static final String HTTP_HEADER_CONTENT_MD5 = "Content-MD5";
    /** <pre>请求Header Content-Type</pre> */
    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    /** <pre>请求Header UserAgent</pre> */
    public static final String HTTP_HEADER_USER_AGENT = "User-Agent";
    /** <pre>请求Header Date</pre> */
    public static final String HTTP_HEADER_DATE = "Date";



    //===============
    //XXX 常用HTTP Content-Type常量
    //===============
    /** <pre>表单类型Content-Type</pre> */
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded; charset=UTF-8";
    /** <pre>流类型Content-Type</pre> */
    public static final String CONTENT_TYPE_STREAM = "application/octet-stream; charset=UTF-8";
    /** <pre>JSON类型Content-Type</pre> */
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    /** <pre>XML类型Content-Type</pre> */
    public static final String CONTENT_TYPE_XML = "application/xml; charset=UTF-8";
    /** <pre>文本类型Content-Type</pre> */
    public static final String CONTENT_TYPE_TEXT = "application/text; charset=UTF-8";

    //===============
    //XXX 常用HTTP 方法常量
    //===============

    /** <pre>GET</pre> */
    public static final String GET = "GET";

    /** <pre>POST</pre> */
    public static final String POST = "POST";

    /** <pre>PUT</pre> */
    public static final String PUT = "PUT";

    /** <pre>DELETE</pre> */
    public static final String DELETE = "DELETE";
}