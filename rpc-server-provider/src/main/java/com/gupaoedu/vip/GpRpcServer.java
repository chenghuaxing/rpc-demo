package com.gupaoedu.vip;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gupaoedu.vip.annotation.RpcService;
import com.gupaoedu.vip.service.TestService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cheng.huaxing
 * @date 2019-06-07
 */
@Component
public class GpRpcServer implements ApplicationContextAware, InitializingBean {
    // 定义线程池处理服务请求
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("rpc-server-pool-%d").build();
    private ExecutorService executorService = new ThreadPoolExecutor(5, 20, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);

    private Map<String, Object> handleMap = new ConcurrentHashMap<>();
    private int port;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 服务端接收请求
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandeler(handleMap, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (!CollectionUtils.isEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String serviceName = rpcService.value().getName();
                port = rpcService.port();
                handleMap.put(serviceName, serviceBean);
            }
        }
    }
}
