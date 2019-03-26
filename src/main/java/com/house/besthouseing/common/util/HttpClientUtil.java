package com.house.besthouseing.common.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * http请求工具类
 * @author genggeng
 *
 */
public class HttpClientUtil {
	
	/**
	 * httpclient
	 * @param url
	 * @return
	 */
	public static JSONObject postJsonObj(String url, JSONObject jsonO){
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());

		HttpEntity<String> entity = new HttpEntity<String>(jsonO.toString(),headers);
		String jsonS = restTemplate.postForObject(url, entity, String.class);
		return JSONObject.parseObject(jsonS);
	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
}
