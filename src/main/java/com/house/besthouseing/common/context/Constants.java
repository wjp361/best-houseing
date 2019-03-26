package com.house.besthouseing.common.context;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 *  * 类名称:   Constants
 *  * 类描述:   存放项目中所有常量
 *  * 创建人:   wanglei
 *  * 创建时间:  2017年12月15日 上午9:25:15
 *  *
 *  
 */
public class Constants {

    public static final ResourceLoader resourceLoader = new DefaultResourceLoader();

    public static final String UNKOWN_TIP = "unknown";

    /**
     * redis手机验证码键
     */
    public static final String MC_PHONE_CODE = "MC_PHONE_CODE";
    public static final String SEND_PHONE_CODE_ERROOR = "发送失败,请您稍后再试";
    public static final String LOGIN_NAME_EXISTED = "用户名已存在";
    public static final String PHONE_NUMBER_EXISTED = "该手机号已被注册";
    /**
     * redis生成客户编号记录
     */
    public static String CUSTOMER_NO = "MC_CHANNEL_CUSTOMER_NO";

    public static final String CHANNEL_NO_ERROR = "渠道商编码不存在";

    public static final String ADDRESS_COUNT_EXCESSIVE = "收货地址不能超过三个";

    public static final String LOGIN_NAME_IS_PHONE = "[0-9]{11}";

    public static final String APP_CONFIG_EXIST = "主配置不存在";

    public static final String PHONE_CODE_ERROR = "验证码错误";

    /*
	 * TOKEN前缀
	 */
	public static final String USERTOKEN_PREFIX = "UTOKEN_";
    /**
     * 购物车修改锁前缀.
     */
    public static String MC_CAR_EDIT_LOCK_PREFIX = "mc_car_edit_lock_prefix";
    /**
     * 购物车添加锁前缀.
     */
    public static String MC_CAR_ADD_LOCK_PREFIX = "mc_car_add_lock_prefix";
    /**
     * 购物车前缀.
     */
    public static String CAR_PREFIX = "car_";

    /**
     * redis商品入库单流水号key
     */
    public static String GOODS_WAREHOUSE_ORDER_IN = "goodsWarehouseOrderIn_";
    /**
     * 客户订单流水码前缀
     */
    public static final String ORDER_RUNNING_NUMBER = "order_running_number_";
	
    //服务间调用header头
    public static final String FEIGN_MS = "micro-service";
    public static final String FEIGN_HD = "Authorization";

    public static final String SUCCESS_CODE = "10000";
    public static final String SUCCESS_MSG = "成功";

    public static final String RUNNINGEXCEPTIOIN_CODE = "10001";
    public static final String RUNNINGEXCEPTIOIN_MSG = "失败";

    public static final String ERROR_PARAM_CODE = "10002";
    public static final String ERROR_PARAM_MSG = "参数错误";
    
	public static final String ERROR_ILLEGALREQUEST_CODE = "10006";
	public static final String ERROR_ILLEGALREQUEST_MSG = "非法请求";
	
	public static final String ERROR_TOKEN_RESULT = "10014";
	public static final String ERROR_TOKEN_MESSAGE = "token已过期";

    public static final String ERROR_LOGIN_RESULT = "10015";
    public static final String ERROR_LOGIN_PASSWORD = "用户名或密码错误";

    public static final String ERROR_COURIER_RESULT = "10016";
    public static final String ERROR_COURIER_MESSAGE = "物流信息有误！";

    public static final String ERROR_QCOURIER_RESULT = "10017";
    public static final String ERROR_QCOURIER_MESSAGE = "物流信息查询失败！";

    public static final String ERROR_GOODSSKUSALE_RESULT = "10018";
    public static final String ERROR_GOODSSKUSALE_MESSAGE = "该规格已下架！";

    public static final String ERROR_TIMEOUT_RESULT = "10019";
    public static final String ERROR_TIMEOUT_MESSAGE = "处理业务超时";

    public static final String ERROR_ORDERPRICE_RESULT = "10020";
    public static final String ERROR_ORDERPRICE_MESSAGE = "订单金额异常！";

    public static final String ERROR_ORDERGOODS_RESULT = "10021";
    public static final String ERROR_ORDERGOODS_MESSAGE = "订单商品异常！";

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

    //Job枚举
    public enum CodeStatus {
        //访问成功
        CODE_10000("10000"),
        //访问方法有误
        CODE_10002("10002"),
        //访问参数有误
        CODE_10003("10003"),
        //访问请求超时
        CODE_10004("10004");

        private final String code;

        private CodeStatus(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static String getResultJson(String statusCode, String message, String path, String data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", statusCode);
        jsonObject.put("message", (message == null || message.isEmpty()) ? "error" : message);
        jsonObject.put("path", path);
        jsonObject.put("data", data);
        return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
    }


}
