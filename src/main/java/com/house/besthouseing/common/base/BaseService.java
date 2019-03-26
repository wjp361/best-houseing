package com.house.besthouseing.common.base;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseService {
    private static final Log log = LogFactory.getLog(BaseService.class);
    
    protected void printParam(Object b) {
        log.info("service层传入参数-->" + JSONObject.toJSONString(b));
    }
    
    protected void printResult(Object b) {
    	log.info("service层返回结果-->" + JSONObject.toJSONString(b));
    }
}
