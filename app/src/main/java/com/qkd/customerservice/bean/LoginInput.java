package com.qkd.customerservice.bean;

/**
 * Created on 12/17/20 09:33
 * .
 *
 * @author yj
 * @org 浙江房超信息科技有限公司
 */
public class LoginInput {
    private String username;
    private String password;

    public LoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}