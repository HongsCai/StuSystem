package com.model;

public class User {
    private String username;	// 用户名
    private String password;	// 密码
    private String type;        // 账号类型

    /**
     * 表示一个用户模型（用户名 密码 账号类型）
     * @author HongsCai
     * @date 2024/6/18
     */
    
    public User(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
    public User() {};

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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