package com.jmeter.aliyun.sign.constant;

import java.nio.charset.Charset;

/**
 * @Author zhangmaosong
 * @create 2021-12-01 17:59
 */
public class CharsetConstants {

    /** <pre>ISO-8859-1 字符集 </pre> */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /** <pre>UTF-8 字符集</pre> */
    public static final String UTF_8 = "UTF-8";

    /** <pre>GBK 字符集</pre> */
    public static final String GBK = "GBK";


    /** ISO-8859-1 */
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);

    /** UTF-8 */
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);

    /** GBK */
    public static final Charset CHARSET_GBK = Charset.forName(GBK);

}
