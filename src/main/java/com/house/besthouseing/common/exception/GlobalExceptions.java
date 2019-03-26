package com.house.besthouseing.common.exception;

import com.house.besthouseing.common.base.BaseResBean;
import com.house.besthouseing.common.context.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author 小黑
 */
@ControllerAdvice
public class GlobalExceptions {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptions.class);

    @ExceptionHandler
    @ResponseBody
    public Object jsonHandler(Exception e){
        log.error(e.getMessage());
        BaseResBean baseResBean = new BaseResBean();
        baseResBean.setCode(Constants.RUNNINGEXCEPTIOIN_CODE);
        baseResBean.setMessage(Constants.RUNNINGEXCEPTIOIN_MSG);
        return baseResBean;
    }

}
