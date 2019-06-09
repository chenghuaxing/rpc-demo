package com.gupaoedu.vip;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.gupaoedu.vip.service.TestService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cheng.huaxing
 * @date 2019-06-07
 */
public class RpcProxyServer {
    // 定义线程池处理服务请求
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("rpc-server-pool-%d").build();
    private ExecutorService executorService = new ThreadPoolExecutor(5, 20, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), namedThreadFactory);

    /**
     * 指定服务发布到指定端口
     *
     * @param testService 服务
     * @param port        端口
     */
    public void publisher(TestService testService, int port) {
        // 服务端接收请求
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
//                executorService.execute(new ProcessorHandeler(testService, socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
