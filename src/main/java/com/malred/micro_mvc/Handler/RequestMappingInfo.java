package com.malred.micro_mvc.Handler;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Component
public class RequestMappingInfo {
    private String url;
    private Object obj;//处理url的bean
    private Method method;//处理url的方法
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Object getObj() { return obj; }
    public void setObj(Object obj) { this.obj = obj; }
    public Method getMethod() { return method; }
    public void setMethod(Method method) { this.method = method; }
}
