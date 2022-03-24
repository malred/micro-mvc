package com.malred.micro_mvc.Handler;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Component
public class BeanIdHandler implements HandlerMapping{
    private Map<String,Object> map = new HashMap<>();
    @Override
    public Object getHandler(String url) {
        return map.get(url);
    }
    //初始化后的后置处理器,获得bean名(是url地址)
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(beanName.startsWith("/")) map.put(beanName,bean);
        return false;
    }
}
