package com.malred.micro_mvc;

import com.malred.micro_mvc.Handler.HandlerMapping;
import com.malred.micro_mvc.Handler.RequestMappingInfo;
import com.malred.micro_mvc.Handler.adapter.handlerAdapter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

/**
 * @author malguy-wang sir
 * @create ---
 */
public class servlet extends HttpServlet {
    private String contextConfig;
    private Collection<HandlerMapping> handlerMappings;
    private Collection<handlerAdapter> handlerAdapters;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandlerMapping(req);
        //适配器模式
        handlerAdapter adapter = getHandlerAdapter(handler);
        Object result = null;
        try {
            result = adapter.handler(req, resp, handler);//处理
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter writer = resp.getWriter();
        writer.println(result);//向页面输出json内容
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //获取配置文件的参数(和tomcat和servlet的知识有关)
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(config.getInitParameter("contextConfigLocation"));
        //获取映射器集合
        Map<String, HandlerMapping> handlerMappingMap = context.getBeansOfType(HandlerMapping.class);
        handlerMappings = handlerMappingMap.values();//返回此map中包含的值的集合视图
        //获取适配器集合
        Map<String, handlerAdapter> handlerAdapterMap = context.getBeansOfType(handlerAdapter.class);
        handlerAdapters = handlerAdapterMap.values();//返回此map中包含的值的集合视图
    }
    //获取映射器
    private Object getHandlerMapping(HttpServletRequest req){
        if(handlerMappings!=null){
            for (HandlerMapping mapping : handlerMappings) {
                //获取处理器里的map的url对应的值(RequestMappingInfo或bean)
                Object handler = mapping.getHandler(req.getRequestURI());
                return handler;
            }
        }
        return null;
    }
    //获取适配器
    private handlerAdapter getHandlerAdapter(Object handler){
        if(handlerAdapters!=null){
            for (handlerAdapter adapter : handlerAdapters) {
                //是否支持
                boolean support = adapter.support(handler);
                if(support){
//                    Object obj = ((RequestMappingInfo) handler).getObj();
                    return adapter;
                }
            }
        }
        return null;
    }
}
