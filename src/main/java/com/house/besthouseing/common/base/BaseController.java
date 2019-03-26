package com.house.besthouseing.common.base;

import com.alibaba.fastjson.JSONObject;
import com.house.besthouseing.common.context.Constants;
import com.house.besthouseing.common.exception.ExceptionError;
import com.house.besthouseing.common.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseController {
    protected static final Log log = LogFactory.getLog(BaseController.class);

    protected void exceptionError(BaseResBean b, ExceptionError e) {
        b.setCode(e.getCode());
        b.setMessage(e.getMsg());
    }

    protected void exception(BaseResBean b) {
        b.setCode(Constants.RUNNINGEXCEPTIOIN_CODE);
        b.setMessage(Constants.RUNNINGEXCEPTIOIN_MSG);
    }

    protected void printResult(BaseResBean b) {
        log.info("返回结果-->" + JSONObject.toJSONString(b));
    }

    private Integer[] getAjaxIds(String str, String separator) {
        Integer[] ids = null;
        if (str != null) {
            String[] strs = str.split(separator);
            ids = new Integer[strs.length];
            int i = 0;

            for (int length = strs.length; i < length; ++i) {
                ids[i] = Integer.valueOf(strs[i]);
            }
        }

        return ids;
    }

    protected List<Integer> getAjaxIds(String id) {
        return (List<Integer>) (StringUtils.isBlank(id) ? new ArrayList<Integer>(0) : Arrays.asList(this.getAjaxIds(id, ",")));
    }

}
