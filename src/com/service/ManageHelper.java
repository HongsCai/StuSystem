package com.service;

import com.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;

/**
 * 封装与数据库交互的操作及其异常处理
 * @author HongsCai
 * @date 2024/6/18
 */

public class ManageHelper {
    private JdbcHelper helper;

    public boolean Login(User user) {
        boolean flag = false;
        helper = new JdbcHelper();                                      // 创建与数据库通信的对象
        try {
            User newUser = helper.getUser(user);                        // 获得用户数据
            if (user.getPassword().equals(newUser.getPassword()))       // 比对密码与数据库中的对应密码是否一致
                flag = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
        return flag;
    }

    public boolean Register(User user) {
        helper = new JdbcHelper();
        boolean flag = false;
        try {
            flag = helper.register(user);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
        return flag;
    }

    public void loadCourseAllQuery(DefaultTableModel model, String option, String input) {
        helper = new JdbcHelper();
        try {
            helper.loadCourseAllQuery(model, option, input);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void deleteSc(String sno, String subject) {
        helper = new JdbcHelper();
        try {
            helper.deleteSc(sno, subject);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void updateSc(String sno, String subject, int newScore) {
        helper = new JdbcHelper();
        try {
            helper.updateSc(sno, subject, newScore);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean updatePassword(User user, String newPassword) {
        helper = new JdbcHelper();  // 创建与数据库通信的对象
        boolean flag = true;
        try {
            flag = helper.updatePassword(user, newPassword);
        } catch (SQLException e) {
            flag = false;
        }
        return flag;
    }

    public void loadStuDetailQuery(String sno, DefaultTableModel stuModel, DefaultTableModel scoreModel) {
        helper = new JdbcHelper();
        try {
            helper.loadStuDetailQuery(sno, stuModel, scoreModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadStudens(DefaultTableModel studensModel, String option, String input) {
        helper = new JdbcHelper();
        try {
            helper.loadStudent(studensModel, option, input);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadCourse(DefaultTableModel courseModel, String option, String input) {
        helper = new JdbcHelper();
        try {
            helper.loadCourse(courseModel, option, input);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void selectCourse(String cno, String sno) {
        helper = new JdbcHelper();
        try {
            helper.selectCourse(cno, sno);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addCourse(String cno, String cname, String cteacher, int credit) {
        helper = new JdbcHelper();
        try {
            helper.addCourse(cno, cname, cteacher, credit);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void deleteCourse(String cno) {
        helper = new JdbcHelper();
        try {
            helper.deleteCourse(cno);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void deleteStudent(String sno) {
        helper = new JdbcHelper();
        try {
            helper.deleteStudent(sno);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addStudent(String name, String gender, String sno, String major) {
        helper = new JdbcHelper();
        try {
            helper.addStudent(name, gender, sno, major);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }
}
