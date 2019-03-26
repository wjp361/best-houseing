package com.house.besthouseing.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: liliya
 * @Description: 各个模块公共工具配置,注意不要加入业务耦合项
 * @Date: Created in 2018/4/12 15:11
 * @Modified:
 */
@SuppressWarnings("all")
public class CommonUtil {
	
	/**
	 * 序列化
	 * @param value
	 * @return
	 */
	public static byte[] serialize(Object value){
		if(value == null){
			throw new NullPointerException("Can't serialize null");
		}
		ObjectOutputStream os = null;
		ByteArrayOutputStream bos = null;
		try {
			if (value != null){
				bos = new ByteArrayOutputStream();
				os = new ObjectOutputStream(bos);
				os.writeObject(value);
				os.close();
				bos.close();
				return bos.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 反序列化
	 * @param in
	 * @return
	 */
	public static Object deserialize(byte[] in){
		ByteArrayInputStream bais = null;
		try {
			if (in != null && in.length > 0){
				bais = new ByteArrayInputStream(in);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * JSONObject 判断数字类型参数
	 * @param obj
	 * @return
	 */
	public static boolean isNumbericNull(Object obj){
		
		if(obj == null || obj.equals(null))return true;
		
		if(!(obj instanceof Integer || obj instanceof Double || obj instanceof Float))return true;
		
		return false;
	}
	/**
	 * JSONObject 判断String类型参数
	 * @param obj
	 * @return
	 * @throws ExceptionError 
	 */
	public static boolean isNull(Object obj){
		
		if(obj == null || obj.equals(null))return true;
		if(!(obj instanceof String))return true;
		
		if(StringUtils.isBlank((String)obj)){
			return true;
		}else{
			return false;
		}
	}
    /**
     * 获取当天的前几天、后几天(需要JDK8优化)
     * @param dayNum
     * @return
     */
    public static Date getSelectDate(Integer dayNum){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,dayNum);
        date = calendar.getTime();
        return date;
    }


    /**
     * 处理(LIST中只包括相同类型对象)List中(实体属性/MAP的KEY和VALUE)的null值,现在只处理String和Integer
     * 如果obj或其中的子对象为更为复杂的对象,那么需要单独处理,例如用这个类中的processingXXXXAttributes的方法进行递归操作。
     * @param obj
     * @param type	model为处理list中的实体对象,map为处理list中的MAP对象
     */
    public static void processingEntityAttributesInfo(Object obj,String type){

        if(null == obj){
            return;
        }

        if(obj instanceof List){

            List list = (List) obj;

            /** 去除list中的所有null对象 */
            list.remove(Collections.singleton(null));

            /** 实体对象类型 */
            if("model".equals(type)){
                for (int i = 0; i < list.size(); i++) {

                    /** list中的某个实体 */
                    processingEntityAttributes(list.get(i));

                }
                /** MAP类型 */
            }else if("map".equals(type)){
                for (int i = 0; i < list.size(); i++) {

                    /** list中的某个MAP对象 */
                    processingMapAttributes(list.get(i));

                }
                /** 其他以后再加 */
            }else{//

            }
            /** 其他obj的类型只支持实体对象,不包括其他基本数据类型的封装类 */
        }else{
            processingEntityAttributes(obj);
        }
    }

    /**
     * 处理MAP中的null值,去除KEY为null的数据,以及处理value为null的数据为空字符串
     * @param obj
     */
    public static void processingMapAttributes(Object obj){

        if(null == obj){
            return;
        }

        Map map = (Map)obj;

        Iterator<Map.Entry<String,Object>> entryIterator = map.entrySet().iterator();

        while(entryIterator.hasNext()){

            Map.Entry<String,Object> entry = entryIterator.next();
            //要注意空指针问题
            String key = entry.getKey();
            Object val = entry.getValue();
            if(null == key){
                entryIterator.remove();
            }else{
                if(null == val){
                    entry.setValue("");
                }
            }
        }
    }

    /**
     * 处理实体属性中的null值,现在只处理实体属性中的String为空字符串和Integer为0
     * @param obj
     */
    public static void processingEntityAttributes(Object obj){
        try {
            if(null == obj){
                return;
            }
            Field[] fields = obj.getClass().getDeclaredFields();//实体中的所有属性
            for (int j = 0; j < fields.length; j++) {
                if(!fields[j].isAccessible()){
                    fields[j].setAccessible(true);//可以访问私有属性
                }
                String type = fields[j].getGenericType().toString();//属性类型
                Object fieldObj = fields[j].get(obj);//属性值

                if(fieldObj == null){
                    if("class java.lang.Integer".equals(type)){
                        fields[j].set(obj,0);
                    }else if("class java.lang.String".equals(type)){
                        fields[j].set(obj,"");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前字符串形式的时间,格式:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDateStr(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 通过实现类获取实现接口中的所有自定义方法
     * @param bean
     * @return
     */
    public static Map<String,String> getClassInterfacesMethods(Object bean){

        Map<String,String> map = new HashMap<>();
        if(bean == null){
            return map;
        }
        try {
            //格式为:class所在目录(比如com.xx.test)加@yy
            String beanObjPath = bean.toString();
            int realBeanPathIndex = beanObjPath.indexOf("@");
            String realBeanPath = beanObjPath.substring(0,realBeanPathIndex);
            Class<?> clazz = Class.forName(realBeanPath);
            Class<?>[] implClazzs = clazz.getInterfaces();
            for(Class<?> implClazz : implClazzs ){
                Method[] methods = implClazz.getDeclaredMethods();
                for (Method method : methods){
                    String interfaceMethodName = method.getName();
                    map.put(interfaceMethodName,interfaceMethodName);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    
    /**
	 * 分页初始化
	 * @param map
	 */
	public static void pageInit(Map<String, Object> map){
		int page = (int) map.get("page");
		int size = (int) map.get("size");
		page = page == 0 ? 1 : page;
		Integer start = (page-1) * size;
		map.put("page_size", size);
		map.put("page_start", start);
	}
	

}
