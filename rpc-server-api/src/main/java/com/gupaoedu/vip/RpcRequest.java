package com.gupaoedu.vip;

import java.io.Serializable;

/**
 * rpc请求封装
 * @author cheng.huaxing
 * @date 2019-06-07
 */
public class RpcRequest implements Serializable {
    private String className;

    private String methodName;

    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
