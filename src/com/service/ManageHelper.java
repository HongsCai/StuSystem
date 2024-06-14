package com.service;

import com.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.SQLException;

public class ManageHelper {
    private JdbcHelper helper;

    public boolean Login(User user) {
        boolean flag = true;
        helper = new JdbcHelper();                                  // 创建与数据库通信的对象
        User newUser = helper.getUser(user);                        // 获得用户数据
        if (!user.getPassword().equals(newUser.getPassword())) {    // 比对密码与数据库中的对应密码是否一致
            flag = false;
        }
        return flag;
    }

    public boolean Register(User user) {
        helper = new JdbcHelper();
        boolean flag = helper.register(user);
        return flag;
    }

    public void allCourseQueryPage(DefaultTableModel model) {
        helper = new JdbcHelper();
        try {
            helper.allCourseQueryPage(model);
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

    public void stuDetailQueryPage(String sno, DefaultTableModel stuModel, DefaultTableModel scoreModel) {
        helper = new JdbcHelper();
        try {
            helper.stuDetailQueryPage(sno, stuModel, scoreModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadStudens(DefaultTableModel studensModel) {
        helper = new JdbcHelper();
        try {
            helper.loadStudens(studensModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void loadCourses(DefaultTableModel courseModel) {
        helper = new JdbcHelper();
        try {
            helper.loadCourses(courseModel);
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

    public void deleteCourse(String cno){
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

    public void addStudent(String name, String gender, String sno, String sdept) {
        helper = new JdbcHelper();
        try {
            helper.addStudent(name, gender, sno, sdept);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "失败", "警告", JOptionPane.WARNING_MESSAGE);
        }
    }
}
