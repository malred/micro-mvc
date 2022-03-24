package com.malred.micro_mvc.Handler.adapter;

import com.malred.micro_mvc.Handler.adapter.handlerAdapter;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author malguy-wang sir
 * @create ---
 */
@Component
public class servletHandlerAdapter implements handlerAdapter {
    @Override
    public boolean support(Object handler) {
        return handler instanceof Servlet;//是不是servlet类型
    }
    @Override
    public Object handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        ((Servlet)handler).service(req,resp);//调用其处理方法
        return null;
    }
}
