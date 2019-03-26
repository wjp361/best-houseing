package com.house.besthouseing.common.context;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class LocalContext {

	public static final ThreadLocal<JSONObject> PARAMLOCAL = new ThreadLocal<JSONObject>();

	public static final ThreadLocal<String> REQUEST_PATH = new ThreadLocal<String>();

	public static final ThreadLocal<JSONObject> CHARGE_PARAM = new ThreadLocal<JSONObject>();

	public static final ThreadLocal<String> JSONSTR = new ThreadLocal<String>();

	public static final ThreadLocal<Map<String, Integer>> STOREROLLBACK = new ThreadLocal<>();

	/**
	 * 
	 * @return
	 */
	public static JSONObject getLocalParam() {
		JSONObject jsonO = PARAMLOCAL.get();
		return jsonO;
	}
	
	public static void setLocalParam(JSONObject jsonO) {
		PARAMLOCAL.set(jsonO);
	}

	/**
	 * 清除线程级缓存
	 */
	public static void clearLocalContext(){
		PARAMLOCAL.remove();
	}
}
