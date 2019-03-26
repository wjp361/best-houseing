package com.house.besthouseing.common.exception;


import com.alibaba.fastjson.JSONObject;
import com.house.besthouseing.common.base.BaseResBean;


public class ExceptionError extends Exception {

    private static final long serialVersionUID = -8167269590487280495L;

    private String code;
    private String message;

    public ExceptionError() {
        super();
    }

    public ExceptionError(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseResBean toResBean() {
        BaseResBean b = new BaseResBean();
        b.setCode(this.code);
        b.setMessage(this.message);
        return b;
    }

    @Override
    public String toString() {
        BaseResBean b = new BaseResBean();
        b.setCode(this.code);
        b.setMessage(this.message);
        return JSONObject.toJSONString(b);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

}
