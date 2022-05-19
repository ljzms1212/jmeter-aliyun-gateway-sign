package com.jmeter.aliyun.sign.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author zhangmaosong
 * @create 2021-12-01 20:40
 */
@SpringBootTest
public class SignUtilTest {

    @Test
    void sign() {

        Map<String, String> headerMap = new SignUtil ().sign (
                "111", "1000", "POST", "/ttt", new HashMap<> (), "customerId=1&temp=2 ", null);

        assertNotNull (headerMap);

        for (String key : headerMap.keySet ()) {

            System.out.println ("Key = " + key + ",Value = " + headerMap.get (key));

        }
    }

}