package com.house.besthouseing.common;

import com.house.besthouseing.common.util.SecretUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BestHouseingApplicationTests {

    @Test
    public void contextLoads() {
//        String secret = "mc";
        String paramter = "{\"orderId\":132,\"userId\":1,\"userName\":\"admin\",\"invoiceId\":21,\"loginName\":\"admin\"}";

//        String sign = SecretUtil.MD5(secret + paramter + secret);
//        String param = paramter;
        System.out.println(paramter);
        String s = postString("http://localhost:10060/best-houseing/test/teseA", paramter);
        System.out.println(s);

    }


    /**
     * post方式请求
     *
     * @return
     */
    public static String postString(String url, String jsonStr) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> entity = new HttpEntity<String>(jsonStr, headers);
        return restTemplate.postForObject(url, entity, String.class);
    }

}
