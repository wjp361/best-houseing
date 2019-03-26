package com.house.besthouseing.common.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: 小黑
 * @Date: 2019/3/18 14:54
 * @Description:
 */
public class BaseMcApplicationUtil {

    /**
     * 校验所有请求对象及字段
     * @param jsonObject 全局JSON对象
     * @param params 需要从上述JSON对象中校验的所有字段
     * @param ifCheckColumn 是否校验params中的字段,true校验,false不校验
     * @return
     */
    public static Boolean checkRequestParams(JSONObject jsonObject, String[] params, Boolean ifCheckColumn){

        Boolean flag = true;
        if(null != jsonObject){
            if(ifCheckColumn){
                if(params.length > 0){
                    for (String param : params){
                        if(null == jsonObject.get(param) || StringUtils.isBlank(jsonObject.get(param).toString())){
                            flag = false;
                            break;
                        }
                    }
                }else{
                    flag = false;
                }
            }
        }else{
            flag = false;
        }
        return flag;
    }
}
