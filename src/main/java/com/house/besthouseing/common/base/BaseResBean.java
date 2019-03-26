package com.house.besthouseing.common.base;

import com.house.besthouseing.common.context.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "统一返回数据上层模型", description = "统一返回数据格式")
public class BaseResBean implements Serializable {

    private static final long serialVersionUID = -2900447975545013981L;

    @ApiModelProperty(value = "返回编码", dataType = "string", example = "10000", required = true)
    private String code = Constants.SUCCESS_CODE;

    @ApiModelProperty(value = "返回说明信息", dataType = "string", example = "成功", required = true)
    private String message = Constants.SUCCESS_MSG;

    @ApiModelProperty(value = "返回查询数据", dataType = "string", example = "{}")
    private Object data;

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
