package com.gupaoedu.vip.service;

import com.gupaoedu.vip.dto.UserDTO;

/**
 * 定义对外开放的接口服务
 *
 * @author cheng.huaxing
 * @date 2019-06-07
 */
public interface TestService {
    String sayHello(String context);

    String saveUser(UserDTO userDTO);
}
