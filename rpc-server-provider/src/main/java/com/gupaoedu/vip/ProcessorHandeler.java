package com.gupaoedu.vip;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author cheng.huaxing
 * @date 2019-06-07
 */
public class ProcessorHandeler implements Runnable {

//    private Object service;
    private Socket socket;
    private Map<String, Object> handleMap;

    public ProcessorHandeler(Map<String, Object> handleMap, Socket socket) {
//        this.service = service;
        this.socket = socket;
        this.handleMap = handleMap;
    }

    @Override
    public void run() {
        // 在线程中处理请求
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            // 反序列化获取请求对象
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            // 反射调用远程调用的方法
            Object result = invoke(rpcRequest);
            // 返回结果给调用端
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Object invoke(RpcRequest rpcRequest) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object service = handleMap.get(rpcRequest.getClassName());
        if (service == null) {
            return new RuntimeException("service not found:" + rpcRequest.getClassName());
        }

        Object[] args = rpcRequest.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        Class clazz = Class.forName(rpcRequest.getClassName());
        // 反射获取请求的方法
        Method method = clazz.getMethod(rpcRequest.getMethodName(), types);
        return method.invoke(service, args);
    }
}
