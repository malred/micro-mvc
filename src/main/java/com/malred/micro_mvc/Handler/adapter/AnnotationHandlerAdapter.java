package com.malred.micro_mvc.Handler.adapter;

import com.malred.micro_mvc.Handler.RequestMappingInfo;
import com.malred.micro_mvc.RequestParam;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Component
public class AnnotationHandlerAdapter implements handlerAdapter{
    @Override
    public boolean support(Object handler) {
        return handler instanceof RequestMappingInfo;
    }
    @Override
    public Object handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        Map<String, String[]> parameterMap = req.getParameterMap();//请求携带的参数
        RequestMappingInfo mappingInfo = (RequestMappingInfo) handler;
        Method method = mappingInfo.getMethod();//获取方法
        Parameter[] parameters = method.getParameters();//获取形参列表
        //反射需要的参数列表
        Object[] paramValues = new Object[parameters.length];
        //对两组参数进行匹配
        for (int i = 0; i < parameters.length; i++) {
            for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
                if(stringEntry.getKey().equals(parameters[i].getAnnotation(RequestParam.class).value())){
                    //如果请求携带的参数和RequestParam注解指定的参数一至
                    paramValues[i] = stringEntry.getValue()[0];
                }
            }
        }
        //反射调用方法
        return method.invoke(mappingInfo.getObj(),paramValues);
    }
}
