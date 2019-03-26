package com.house.besthouseing.models.test.testCtl;

import com.alibaba.fastjson.JSONObject;
import com.house.besthouseing.common.base.BaseController;
import com.house.besthouseing.common.base.BaseResBean;
import com.house.besthouseing.common.context.LocalContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: genggeng
 * @create: 2019-03-26 14:20
 */
@RestController
@RequestMapping("test")
public class TestCtl extends BaseController {

    @RequestMapping("teseA")
    public BaseResBean testA(){
        BaseResBean b = new BaseResBean();
        JSONObject jsonO = LocalContext.getLocalParam();
        b.setData(jsonO);
        System.out.println(jsonO);
        return b;
    }
}
