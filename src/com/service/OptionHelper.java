package com.service;

/**
 * 数据库SQL语句的处理分配
 * @author HongsCai
 * @date 2024/6/18
 */

public class OptionHelper {
    public static String loadCourseAllQuery(String option, String input) {
        String sql = "SELECT stu.name, stu.gender, stu.sno, course.cname, sc.score, course.cteacher " +
                "FROM sc " +
                "INNER JOIN stu ON sc.sno = stu.sno " +
                "INNER JOIN course ON sc.cno = course.cno ";
        switch (option) {
            case "姓名":
                sql += "WHERE stu.name LIKE '%" + input + "%'";
                break;
            case "性别":
                sql += "WHERE stu.gender LIKE '%" + input + "%'";
                break;
            case "学号":
                sql += "WHERE stu.sno LIKE '%" + input + "%'";
                break;
            case "课程名":
                sql += "WHERE course.cname LIKE '%" + input + "%'";
                break;
            case "教师":
                sql += "WHERE course.cteacher LIKE '%" + input + "%'";
                break;
        }
        return sql;
    }

    public static String loadStudent(String option, String input) {
        String sql = "SELECT * FROM stu ";
        switch (option) {
            case "学号":
                sql += "WHERE sno LIKE '%" + input + "%'";
                break;
            case "姓名":
                sql += "WHERE name LIKE '%" + input + "%'";
                break;
            case "性别":
                sql += "WHERE gender LIKE '%" + input + "%'";
                break;
            case "专业":
                sql += "WHERE major LIKE '%" + input + "%'";
                break;
        }
        return sql;
    }

    public static String loadCourse(String option, String input) {
        String sql = "SELECT * FROM course ";
        switch (option) {
            case "课程号":
                sql += "WHERE cno LIKE '%" + input + "%'";
                break;
            case "课程名":
                sql += "WHERE cname LIKE '%" + input + "%'";
                break;
            case "教师":
                sql += "WHERE cteacher LIKE '%" + input + "%'";
                break;
        }
        return sql;
    }
}