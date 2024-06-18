package com.service;

/**
 * Jdbc配置 数据库账号及密码
 * @author HongsCai
 * @date 2024/6/18
 */

public interface JdbcConfig {
    String DRIVER = "com.mysql.cj.jdbc.Driver";
    String URL = "jdbc:mysql://localhost:3306/studata?characterEncoding=utf8";
    String USERNAME = "root";
    String PASSWORD = "123456";
}