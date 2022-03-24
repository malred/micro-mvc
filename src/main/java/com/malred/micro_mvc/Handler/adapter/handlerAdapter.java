package com.malred.micro_mvc.Handler.adapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author malguy-wang sir
 * @create ---
 */
public interface handlerAdapter {
    //是否支持
    public boolean support(Object handler);
    //构造handler
    public Object handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}

