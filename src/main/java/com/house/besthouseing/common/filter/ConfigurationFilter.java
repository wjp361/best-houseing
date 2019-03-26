package com.house.besthouseing.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.house.besthouseing.common.context.LocalContext;
import com.house.besthouseing.common.redis.service.RedisClientTemplate;
import com.house.besthouseing.common.util.BeanUtil;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

/**
 * @author: wl
 * @Description: 1
 * @Date: 2018/10/16 16:39
 * @Modified By:
 */
@Configuration
public class ConfigurationFilter {

    private static final Log log = LogFactory.getLog(ContextFilter.class);
    private static final Map<String, Boolean> excludePath = new HashMap<>();
    /**
     * 外来请求，非{'jsonStr':'','sign':''}格式
     */
    private static final Map<String, Boolean> outmap = new HashMap<>();
    private static final String FLAG = "i";
    private static Queue<String> queue = new ConcurrentLinkedQueue<>(); //并发计数队列

    private static final RedisClientTemplate redisClientTemplate = (RedisClientTemplate) BeanUtil.getBean("redisClientTemplate");
    
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean<ContextFilter> testFilterRegistration() {
        FilterRegistrationBean<ContextFilter> registration = new FilterRegistrationBean<ContextFilter>();
        registration.setFilter(new ContextFilter());//添加过滤器
        registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
        registration.setName("ContextFilter");//设置优先级
        registration.setOrder(1);//设置优先级
        return registration;
    }

    public class ContextFilter implements Filter {

        @Override
        public void destroy() {
            // Do nothing because of X and Y.
        }

        @Override
        public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
                throws IOException, ServletException {
			//1、开始时间
        	long beginTime = System.currentTimeMillis();
    		queue.add(FLAG);
    		log.info("===============计时开始："+new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime)+"===============");
    		
    		HttpServletRequest request = (HttpServletRequest) arg0;
    		String path = request.getServletPath();
    		log.info("request servlet path ->" + path);
    		
    		String suffix = path.substring(path.lastIndexOf("/")+1);
    		//过滤支付地址
    		if("wxNotify.htm".equals(suffix) || "alipayNotify.htm".equals(suffix)){
    			arg2.doFilter(arg0, arg1);
    		} else {
    			String jsonStr = this.getRequestBodyJson(arg0);
    			log.info("请求参数-->"+jsonStr);
    			if(StringUtils.isNotBlank(jsonStr)){

    				JSONObject jsonO = JSONObject.parseObject(jsonStr);
    				//验证请求是否合法
//    				if(!legalCheck((HttpServletResponse)arg1, jsonO))return;
//    				String string = jsonO.getString("jsonStr").substring(2);
//    				JSONObject jsonObj = JSONObject.parseObject(string.replaceAll("\r\n", "\\\\r\\\\n"));
    				JSONObject jsonObj = JSONObject.parseObject(jsonStr.replaceAll("\r\n", "\\\\r\\\\n"));
    				jsonObj.put("ip_address", request.getRemoteAddr());
    				LocalContext.setLocalParam(jsonObj);
    			}
    			//token验证
//    			if(excludePath.containsKey(path) || returnResponse((ServletResponse) arg1))
    			arg2.doFilter(arg0, arg1);
    			//清除线程级缓存
    			LocalContext.clearLocalContext();
    		}
			//2、结束时间
    		long endTime = System.currentTimeMillis();
    		queue.poll();
    		log.info("===========计时结束："+new SimpleDateFormat("hh:mm:ss.SSS").format(endTime)+"  耗时："+(endTime - beginTime)+"毫秒    并发量："+queue.size()+" ==========="); 	
        }

        @Override
        public void init(FilterConfig arg0) throws ServletException {
//            //外来请求
//            outmap.put("webhooksNodify.do", true);
//            outmap.put("queryConfig.do", true);
//            outmap.put("areaTest.do", true);
//            outmap.put("queryAreas.do", true);
//        	//支付回调接口
//    		excludePath.put("/pay/alipayNotify.htm", true);
//    		excludePath.put("/pay/wxNotify.htm", true);
//    		excludePath.put("/afterSalePay/alipayNotify.htm", true);
//    		excludePath.put("/afterSalePay/wxNotify.htm", true);
        }

        /**
         * 功能描述 按照正则表达式放行url
         *
         * @param path
         * @return boolean
         * @author wl
         * @date 2018/10/16 05:28
         */
        public boolean urlFilterLogic(Map<String, String> map, String path) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                if (Pattern.matches(map.get(key), path)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 获取请求参数
         *
         * @param request
         * @return
         */
        private String getRequestBodyJson(ServletRequest request) {
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader reader = request.getReader();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                return null;
            }
            return sb.toString();
        }
    }

    static class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final byte[] body;

        public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            body = toByteArray(request.getInputStream());
        }

        private byte[] toByteArray(InputStream in) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                    // Do nothing because of X and Y.
                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }
    }
    /**
	 * 验证合法性
	 * @param rquest
	 * @param jsonO
	 * @return
	 */
//	private boolean legalCheck(HttpServletResponse response, JSONObject jsonO){
//		BaseResBean b = new BaseResBean();
//		boolean flag = true;
//		if(CommonUtil.isNull(jsonO.get("jsonStr")) || CommonUtil.isNull(jsonO.get("sign"))){
//			b.setCode(Constants.ERROR_PARAM_CODE);
//			b.setMessage(Constants.ERROR_PARAM_MSG);
//			flag = false;
//		}else{
//			String secret = PropertiesUtil.getProperties("house").getProperty("secret");
//			if(jsonO.getString("sign").equals(SecretUtil.MD5(jsonO.getString("jsonStr")+secret))){
//				flag = true;
//			}else{
//				b.setCode(Constants.ERROR_ILLEGALREQUEST_CODE);
//				b.setMessage(Constants.ERROR_ILLEGALREQUEST_MSG);
//				flag = false;
//			}
//		}
//		if(!flag){
//			response.reset();
//			response.setContentType("application/json");
//			response.setCharacterEncoding("utf-8");
//			try {
//				String s = JSONObject.toJSONString(b);
//				log.info("非法请求->"+s);
//				response.getWriter().print(s);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return flag;
//	}
	/**
	 * 验证必传参数及验证token
	 * @param response
	 * @return
	 */
//	public boolean returnResponse(ServletResponse response){
//		BaseResBean b = new BaseResBean();
//		JSONObject jsonO = LocalContext.getLocalParam();
//		boolean flag = true;
//		if(jsonO != null){
//			if(CommonUtil.isNull(jsonO.get("nowu_id"))
//					|| CommonUtil.isNull(jsonO.get("user_token"))
//					|| CommonUtil.isNull(jsonO.get("ver_num"))
//					|| CommonUtil.isNumbericNull(jsonO.get("company_id"))
//					|| CommonUtil.isNull(jsonO.get("client"))
//					){
//				log.info(">>>>>>>>>>>>[参数错误]");
//				b.setCode(Constants.ERROR_PARAM_CODE);
//				b.setMessage(Constants.ERROR_PARAM_MSG);
//				flag = false;
//			}else{
//				if(!jsonO.getString("user_token").equals(redisClientTemplate.get(Constants.USERTOKEN_PREFIX+jsonO.getString("nowu_id")))){
//					log.info(">>>>>>>>>>>>[token过期],[现token：]"+redisClientTemplate.get(Constants.USERTOKEN_PREFIX+jsonO.getString("nowu_id")));
//					b.setCode(Constants.ERROR_TOKEN_RESULT);
//					b.setMessage(Constants.ERROR_TOKEN_MESSAGE);
//					flag = false;
//				}
//			}
//			if(!flag){
//				response.reset();
//				response.setContentType("application/json");
//		        response.setCharacterEncoding("utf-8");
//				try {
//					response.getWriter().print(JSONObject.toJSONString(b));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return flag;
//	}
}
