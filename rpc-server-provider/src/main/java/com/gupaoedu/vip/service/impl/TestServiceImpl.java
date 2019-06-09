package com.gupaoedu.vip.service.impl;

import com.gupaoedu.vip.annotation.RpcService;
import com.gupaoedu.vip.dto.UserDTO;
import com.gupaoedu.vip.service.TestService;

/**
 * @author cheng.huaxing
 * @date 2019-06-07
 */
@RpcService(value = TestService.class, port = 8080)
public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String context) {
        System.out.println("say hello " + context);
        return "hello " + context;
    }

    @Override
    public String saveUser(UserDTO userDTO) {
        System.out.println("save user " + userDTO.toString());
        return null;
    }
}
