package com.gupaoedu.vip;

import com.gupaoedu.vip.service.TestService;
import com.gupaoedu.vip.service.impl.TestServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        /**
         * 服务需要发布调用端才能发现，在微服务中服务发布到注册中心，调用端通过网关进行调用
         */
        // 使用注解发布服务
//        // 发布的接口服务
//        TestService testService = new TestServiceImpl();
//
//        // 进行发布操作
//        RpcProxyServer rpcProxyServer = new RpcProxyServer();
//        rpcProxyServer.publisher(testService, 8080);

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext) context).start();
    }
}
