package com.malred.micro_mvc.Handler;

import com.malred.micro_mvc.RequestMapping;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Component
public class AnnotationHandlerMapping implements HandlerMapping{
    private Map<String,RequestMappingInfo> map = new HashMap<>();
    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        //获取所有方法,找到有RequestMapping的方法
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RequestMappingInfo info = createRequestMappingInfo(method,bean);
            map.put(info.getUrl(),info);//存入map
        }
        return true;
    }
    //定义一个对象
    private RequestMappingInfo createRequestMappingInfo(Method method,Object bean){
        RequestMappingInfo info = new RequestMappingInfo();
        if(method.isAnnotationPresent(RequestMapping.class)){//如果有RequestMapping注解
            info.setMethod(method);
            info.setObj(bean);
            info.setUrl(method.getDeclaredAnnotation(RequestMapping.class).value());
        }
        return info;
    }
}
