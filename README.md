# #web框架功能

![image.png](https://cdn.nlark.com/yuque/0/2022/png/26091703/1648083418873-99535d05-da9b-4a6f-bce4-714b546bdccc.png#averageHue=%23f8f8f7&clientId=u2beb578d-abb7-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=102&id=u318dc354&margin=%5Bobject%20Object%5D&name=image.png&originHeight=80&originWidth=538&originalType=binary&ratio=1&rotation=0&showTitle=false&size=34789&status=done&style=none&taskId=u5d21675c-a22d-4210-b86c-dc60d633a9f&title=&width=685)
# #servlet规范

![image.png](https://cdn.nlark.com/yuque/0/2022/png/26091703/1648083357504-bd70e24b-f67c-4040-9fe8-c5dd8acccf13.png#averageHue=%23eae9e3&clientId=u2beb578d-abb7-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=348&id=u092c4938&margin=%5Bobject%20Object%5D&name=image.png&originHeight=664&originWidth=1532&originalType=binary&ratio=1&rotation=0&showTitle=false&size=627187&status=done&style=none&taskId=uad936c2c-9cfe-464b-a5f8-98a4fc2a62b&title=&width=802)![image.png](https://cdn.nlark.com/yuque/0/2022/png/26091703/1648083612280-476fc1c8-606c-4ca2-a27b-15bcf788a36d.png#averageHue=%23d9d5d0&clientId=u2beb578d-abb7-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=82&id=u0bf9925e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=83&originWidth=700&originalType=binary&ratio=1&rotation=0&showTitle=false&size=52613&status=done&style=none&taskId=u6a27cdfb-f9ac-4553-8fd1-ab866501ed9&title=&width=695)
# #开始
## 依赖
```xml
    <dependencies>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.13.RELEASE</version>
        </dependency>
        <!--返回json需要处理器-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.79</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.3.1</version>
            </plugin>
        </plugins>
    </build>
```
## servlet类只是普通的类,只有交给容器(如tomcat)管理,才被作为servlet,才会获得java和web提供的参数
### servlet类(作为程序的主入口)
```java
public class servlet extends HttpServlet {
    private Map<String,Object> map;//存url和对应处理逻辑
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doget");
    }
}
```
### web.xml
```xml
    <!--把类放入容器-->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>com.malred.micro_mvc.servlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <!--默认接收的请求地址,/就是接收所有-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```
## url的路由问题  :  这个时候,所有请求都被这个servlet(入口)接收,如果进行处理,就需要很多的判断(if(contain(.do)等等)和逻辑,就会很混乱和死板
### 可以设置一个map<url,>,存放url和对应处理逻辑(类或方法),但是要存类还是方法?
### 类处理请求的实现方法![image.png](https://cdn.nlark.com/yuque/0/2022/png/26091703/1648086650911-761cbba0-c5ee-438c-b193-49b5d02c7f5e.png#averageHue=%232e2d2c&clientId=u1a438116-8539-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=146&id=ubb1c87dc&margin=%5Bobject%20Object%5D&name=image.png&originHeight=257&originWidth=1438&originalType=binary&ratio=1&rotation=0&showTitle=false&size=188971&status=done&style=none&taskId=u9bc0ad4d-7dd9-4deb-8e35-20059545f80&title=&width=818)
# #url指定 : 注解或id
## 可以使用RequestMapping,映射器,规定哪种url对应类或方法
```java
@Target(ElementType.METHOD) //适用范围:加在方法上
@Retention(RetentionPolicy.RUNTIME) //运行时生效
public @interface RequestMapping {
    String value() default "";//指定url
}
```
## spring的controller和component一样,但是springmvc中,controller就有所不同
```java
@Controller
public class myController {
    @RequestMapping("/test")
    public String test(){
        System.out.println("tttt");
        return "test";
    }
}
```
## servlet处理器
```java
public class myServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet处理器");
    }
}
```
## 把处理器注册到容器,使用spring-servlet.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                          http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
                          http://www.springframework.org/schema/util  http://www.springframework.org/schema/util/spring-util.xsd
                          http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                          http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    <!--扫描bean-->
    <context:component-scan base-package="com.malred"/>
    <!--注册servlet处理器,id是它的url-->
    <bean id="/myServlet" class="com.malred.micro_mvc.myServlet"/> 
</beans>
```
## 这两种方式获得的url指定,都需要存入map,此时需要映射器
# #映射器:维护url和处理器的路由关系,一个映射器处理一种逻辑
```java
public interface HandlerMapping extends InstantiationAwareBeanPostProcessor {
    Object getHandler(String url);
}
```
## 通过id来处理url的映射器
```java
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
```
## 通过注解
```java
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
```
### 封装了url处理逻辑的类
```java
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
```
## 配置spring容器
```xml
    <!--把类放入容器-->
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>com.malred.micro_mvc.servlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-servlet.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <!--默认接收的请求地址,/就是接收所有-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```
## 接下来就能在初始化方法里创建容器
```java
public class servlet extends HttpServlet {
    private String contextConfig;
    private Collection<HandlerMapping> handlerMappings;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandlerMapping(req);
        //适配器模式 }
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //获取配置文件的参数(和tomcat和servlet的知识有关)
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext(config.getInitParameter("contextConfigLocation"));
        //获取映射器集合
        Map<String, HandlerMapping> handlerMappingMap = context.getBeansOfType(HandlerMapping.class);
        handlerMappings = handlerMappingMap.values();//返回此map中包含的值的集合视图
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
}
```
### 如果下方的applicationcontext的文根定义了,那么就会包含在URI![image.png](https://cdn.nlark.com/yuque/0/2022/png/26091703/1648090998033-7337b634-97d2-444b-a13f-682999eda1df.png#averageHue=%233c4245&clientId=u1a438116-8539-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=390&id=u9dc87d8d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=722&originWidth=1360&originalType=binary&ratio=1&rotation=0&showTitle=false&size=196044&status=done&style=none&taskId=u38a57d4d-a40a-4d5a-a469-f661c328e37&title=&width=735)
## 限制有两种处理方法,需要适配器模式
## ![image.png](https://cdn.nlark.com/yuque/0/2022/png/26091703/1648091266790-b03a7756-93b1-4ff6-9ec9-bb52b7cbc52d.png#averageHue=%23f9f9f9&clientId=u1a438116-8539-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=130&id=ud780d144&margin=%5Bobject%20Object%5D&name=image.png&originHeight=113&originWidth=328&originalType=binary&ratio=1&rotation=0&showTitle=false&size=22233&status=done&style=none&taskId=uc59697d1-4b93-4354-83e3-909113bf9b5&title=&width=376)
```java
public interface handlerAdapter {
    //是否支持
    public boolean support(Object handler);
    //构造handler
    public Object handler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception;
}
```
```java
@Component
public class servletHandlerAdapter implements handlerAdapter{
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
```
```java
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
```
## 参数绑定的注解
```java
@Target(ElementType.PARAMETER)//加在参数上
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    String value() default "";
}
```
## 在servlet类(dispatcher控制器)里获取适配器
```java
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
                    return adapter;
                }
            }
        }
        return null;
    }
}
```
# #spring九大核心组件,这里实现了两个(核心中的核心)
