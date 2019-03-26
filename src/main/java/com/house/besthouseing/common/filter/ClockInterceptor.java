package com.house.besthouseing.common.filter;

import com.house.besthouseing.common.context.Constants;
import com.house.besthouseing.common.context.LocalContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @version 2014-8-19
 */
public class ClockInterceptor extends HandlerInterceptorAdapter {

    private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
    private static final Log log = LogFactory.getLog(ClockInterceptor.class);
    private static Queue<String> queue = new ConcurrentLinkedQueue<>(); //并发计数队列
    private static final String FLAG = "i";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        long beginTime = System.currentTimeMillis();//1、开始时间
        startTimeThreadLocal.set(beginTime);        //线程绑定变量（该数据只有当前请求的线程可见）
        log.info("----------------计时开始：" + new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime) + "----------------");
        queue.add(FLAG);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

        LocalContext.PARAMLOCAL.remove();
        LocalContext.CHARGE_PARAM.remove();
        LocalContext.JSONSTR.remove();
        LocalContext.STOREROLLBACK.remove();
        long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis();    //2、结束时间
        queue.poll();
        log.info("----------------计时结束：" + new SimpleDateFormat("hh:mm:ss.SSS").format(endTime) + "  耗时：" + (endTime - beginTime) + "毫秒     并发量：" + queue.size() + "----------------");
        super.afterCompletion(request, response, handler, ex);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || Constants.UNKOWN_TIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKOWN_TIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKOWN_TIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKOWN_TIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKOWN_TIP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || Constants.UNKOWN_TIP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
