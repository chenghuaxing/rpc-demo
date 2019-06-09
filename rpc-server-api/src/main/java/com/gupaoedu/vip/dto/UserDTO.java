package com.gupaoedu.vip.dto;

/**
 * @author cheng.huaxing
 * @date 2019-06-07
 */
public class UserDTO {
    private String name;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
