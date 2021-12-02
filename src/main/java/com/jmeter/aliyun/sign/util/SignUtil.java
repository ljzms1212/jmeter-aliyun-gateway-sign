package com.jmeter.aliyun.sign.util;

import com.jmeter.aliyun.sign.constant.AliyunGatewayHttpConstants;
import com.jmeter.aliyun.sign.constant.CharsetConstants;
import com.jmeter.aliyun.sign.constant.SymbolConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * 签名工具
 */
public class SignUtil {

    /**
     * @param appKey 阿里云应用key
     * @param appSecret 阿里云应用密码
     * @param method http 请求谓词
     * @param path 请求地址
     * @param headers 头部信息
     * @param querys 请求参数，可不传
     * @return 返回阿里云网关鉴权必要 header 信息
     * */
    public static Map<String, String> sign(
            String appKey,
            String appSecret,
            String method,
            String path,
            Map<String, String> headers,
            Map<String, String> querys) {

        //body 可不参与加密
        headers.put(AliyunGatewayHttpConstants.X_CA_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        headers.put(AliyunGatewayHttpConstants.X_CA_NONCE, UUID.randomUUID().toString());
        headers.put(AliyunGatewayHttpConstants.X_CA_KEY, appKey);
        headers.put(AliyunGatewayHttpConstants.X_CA_SIGNATURE,
                SignUtil.sign(appSecret, method, path, headers, querys, null, null));

        return headers;
    }

    /**
     * 计算签名
     *
     * @param secret               APP密钥
     * @param method               HttpMethod
     * @param path
     * @param headers
     * @param querys
     * @param bodys
     * @param signHeaderPrefixList 自定义参与签名Header前缀
     * @return 签名后的字符串
     */
    private static String sign(
            String secret,
            String method,
            String path,
            Map<String, String> headers,
            Map<String, String> querys,
            Map<String, String> bodys,
            List<String> signHeaderPrefixList) {
        try {
            Mac hmacSha256 = Mac.getInstance(AliyunGatewayHttpConstants.HMAC_SHA256);
            byte[] keyBytes = secret.getBytes(CharsetConstants.UTF_8);
            hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length, AliyunGatewayHttpConstants.HMAC_SHA256));
            byte[] doFinal = hmacSha256.doFinal(buildStringToSign(method, path, headers, querys, bodys, signHeaderPrefixList).getBytes(CharsetConstants.UTF_8));
//            log.debug("doFinal.length={}",doFinal==null?0:doFinal.length);
            return new String(Base64.encodeBase64(doFinal), CharsetConstants.UTF_8);
        } catch (Exception e) {
//            log.error("计算签名异常：secret={},method={},path={},headers={},querys={},bodys={},signHeaderPrefixList={},详情错误:",
//                    secret,method,path,headers,querys,bodys,signHeaderPrefixList,e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 构建待签名字符串
     *
     * @param method
     * @param path
     * @param headers
     * @param querys
     * @param bodys
     * @param signHeaderPrefixList
     * @return
     */
    private static String buildStringToSign(String method, String path,
                                            Map<String, String> headers,
                                            Map<String, String> querys,
                                            Map<String, String> bodys,
                                            List<String> signHeaderPrefixList) {
        StringBuilder sb = new StringBuilder();

        sb.append(method.toUpperCase()).append(SymbolConstants.LF);
        if (null != headers) {
            if (null != headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_ACCEPT)) {
                sb.append(headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_ACCEPT));
            }
            sb.append(SymbolConstants.LF);
            if (null != headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_CONTENT_MD5)) {
                sb.append(headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_CONTENT_MD5));
            }
            sb.append(SymbolConstants.LF);
            if (null != headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_CONTENT_TYPE)) {
                sb.append(headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_CONTENT_TYPE));
            }
            sb.append(SymbolConstants.LF);
            if (null != headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_DATE)) {
                sb.append(headers.get(AliyunGatewayHttpConstants.HTTP_HEADER_DATE));
            }
        }
        sb.append(SymbolConstants.LF);
        sb.append(buildHeaders(headers, signHeaderPrefixList));
        sb.append(buildResource(path, querys, bodys));

        return sb.toString();
    }

    /**
     * 构建待签名Path+Query+BODY
     *
     * @param path
     * @param querys
     * @param bodys
     * @return 待签名
     */
    private static String buildResource(String path, Map<String, String> querys, Map<String, String> bodys) {
        StringBuilder sb = new StringBuilder();

        if (!StringUtils.isBlank(path)) {
            sb.append(path);
        }
        Map<String, String> sortMap = new TreeMap<String, String>();
        if (null != querys) {
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (!StringUtils.isBlank(query.getKey())) {
                    sortMap.put(query.getKey(), query.getValue());
                }
            }
        }

        if (null != bodys) {
            for (Map.Entry<String, String> body : bodys.entrySet()) {
                if (!StringUtils.isBlank(body.getKey())) {
                    sortMap.put(body.getKey(), body.getValue());
                }
            }
        }

        StringBuilder sbParam = new StringBuilder();
        for (Map.Entry<String, String> item : sortMap.entrySet()) {
            if (!StringUtils.isBlank(item.getKey())) {
                if (0 < sbParam.length()) {
                    sbParam.append(SymbolConstants.SPE3);
                }
                sbParam.append(item.getKey());
                if (!StringUtils.isBlank(item.getValue())) {
                    sbParam.append(SymbolConstants.SPE4).append(item.getValue());
                }
            }
        }
        if (0 < sbParam.length()) {
            sb.append(SymbolConstants.SPE5);
            sb.append(sbParam);
        }

        return sb.toString();
    }

    /**
     * 构建待签名Http头
     *
     * @param headers              请求中所有的Http头
     * @param signHeaderPrefixList 自定义参与签名Header前缀
     * @return 待签名Http头
     */
    private static String buildHeaders(Map<String, String> headers, List<String> signHeaderPrefixList) {
        StringBuilder sb = new StringBuilder();

        if (null != signHeaderPrefixList) {
            signHeaderPrefixList.remove(AliyunGatewayHttpConstants.X_CA_SIGNATURE);
            signHeaderPrefixList.remove(AliyunGatewayHttpConstants.HTTP_HEADER_ACCEPT);
            signHeaderPrefixList.remove(AliyunGatewayHttpConstants.HTTP_HEADER_CONTENT_MD5);
            signHeaderPrefixList.remove(AliyunGatewayHttpConstants.HTTP_HEADER_CONTENT_TYPE);
            signHeaderPrefixList.remove(AliyunGatewayHttpConstants.HTTP_HEADER_DATE);

            List<String> signHeaderPrefixListNew = new ArrayList<>(signHeaderPrefixList);
            Collections.sort(signHeaderPrefixListNew);
            if (null != headers) {
                Map<String, String> sortMap = new TreeMap<String, String>();
                sortMap.putAll(headers);
                StringBuilder signHeadersStringBuilder = new StringBuilder();
                for (Map.Entry<String, String> header : sortMap.entrySet()) {
                    if (isHeaderToSign(header.getKey(), signHeaderPrefixListNew)) {
                        sb.append(header.getKey());
                        sb.append(SymbolConstants.SPE2);
                        if (!StringUtils.isBlank(header.getValue())) {
                            sb.append(header.getValue());
                        }
                        sb.append(SymbolConstants.LF);
                        if (0 < signHeadersStringBuilder.length()) {
                            signHeadersStringBuilder.append(SymbolConstants.SPE1);
                        }
                        signHeadersStringBuilder.append(header.getKey());
                    }
                }
                headers.put(AliyunGatewayHttpConstants.X_CA_SIGNATURE_HEADERS, signHeadersStringBuilder.toString());
            }
        }

        return sb.toString();
    }

    /**
     * Http头是否参与签名 return
     */
    private static boolean isHeaderToSign(String headerName, List<String> signHeaderPrefixList) {
        if (StringUtils.isBlank(headerName)) {
            return false;
        }

        if (headerName.startsWith(AliyunGatewayHttpConstants.CA_HEADER_TO_SIGN_PREFIX_SYSTEM)) {
            return true;
        }

        if (null != signHeaderPrefixList) {
            for (String signHeaderPrefix : signHeaderPrefixList) {
                if (headerName.equalsIgnoreCase(signHeaderPrefix)) {
                    return true;
                }
            }
        }

        return false;
    }
}