package com.service;

import com.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class JdbcHelper implements JdbcConfig {
    //定义连接数据库所需要的对象
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection ct = null;


    public JdbcHelper() {
        try {
            Class.forName(DRIVER);
            ct = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获得数据库连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(User user) {
        User newUser = new User();
        try {
            if (user.getType().equals("学生")) ps = ct.prepareStatement("select * from user_stu where username=?");
            else if (user.getType().equals("教师")) ps = ct.prepareStatement("select * from user_tch where username=?");
            else ps = ct.prepareStatement("select * from user_admin where username=?");
            ps.setString(1, user.getUsername());
            rs = ps.executeQuery();
            if (rs.next()) {
                newUser.setUsername(rs.getString(1));       //设置用户名
                newUser.setPassword(rs.getString(2));       //设置密码
                newUser.setType(user.getType());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return newUser;
    }

    public boolean register(User user) {
        boolean flag = true;
        try {
            if (user.getType().equals("学生"))
                ps = ct.prepareStatement("insert into user_stu(username,password) values(?,?)");
            else if (user.getType().equals("教师"))
                ps = ct.prepareStatement("insert into user_tch(username,password) values(?,?)");
            else ps = ct.prepareStatement("insert into user_admin(username,password) values(?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            if (ps.executeUpdate() != 1) {
                flag = false;
            }
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }
        close();
        return flag;
    }

    public boolean updatePassword(User user, String new_Password) throws SQLException {
        boolean flag = true;
        if (user.getType().equals("学生"))
            ps = ct.prepareStatement("update user_stu set password=? where username=?");
        else if (user.getType().equals("教师"))
            ps = ct.prepareStatement("update user_tch set password=? where username=?");
        else ps = ct.prepareStatement("update user_admin set password=? where username=?");
        ps.setString(1, new_Password);
        ps.setString(2, user.getUsername());
        if (ps.executeUpdate() != 1) flag = false;
        close();
        return flag;
    }

    public void allCourseQueryPage(DefaultTableModel model) throws SQLException {
        String sql = "SELECT stu.name, stu.gender, stu.sno, course.cname, sc.score, course.cteacher FROM stu LEFT JOIN sc ON stu.sno = sc.sno LEFT JOIN course ON sc.cno = course.cno";
        ps = ct.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String sno = rs.getString("sno");
            String subject = rs.getString("cname");
            Integer score = (Integer) rs.getObject("score");
            String teacher = rs.getString("cteacher");
            if (score == null) {
                model.addRow(new Object[]{name, gender, sno, subject, "", teacher});
            } else {
                model.addRow(new Object[]{name, gender, sno, subject, score, teacher});
            }
        }
        close();
    }

    public void deleteSc(String sno, String subject) throws SQLException {
        String sql = "SELECT cno FROM course WHERE cname = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, subject);
        rs = ps.executeQuery();
        String cno;
        if (rs.next()) {
            cno = rs.getString("cno");
        } else {
            JOptionPane.showMessageDialog(null, "删除失败");
            return;
        }

        sql = "DELETE FROM sc WHERE sno = ? AND cno = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, sno);
        ps.setString(2, cno);
        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "删除成功");
        } else {
            JOptionPane.showMessageDialog(null, "删除失败");
        }
        close();
    }

    public void updateSc(String sno, String subject, int newScore) throws SQLException {
        String sql = "SELECT cno FROM course WHERE cname = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, subject);
        rs = ps.executeQuery();
        String cno;
        if (rs.next()) {
            cno = rs.getString("cno");
        } else {
            JOptionPane.showMessageDialog(null, "更新失败");
            return;
        }

        sql = "UPDATE sc SET score = ? WHERE sno = ? AND cno = ?";
        ps = ct.prepareStatement(sql);
        ps.setInt(1, newScore);
        ps.setString(2, sno);
        ps.setString(3, cno);

        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "更新成功");
        } else {
            JOptionPane.showMessageDialog(null, "更新失败");
        }
        close();
    }

    public void stuDetailQueryPage(String sno, DefaultTableModel stuModel, DefaultTableModel scoreModel) throws SQLException {
        String sql = "SELECT name, gender, sdept FROM stu WHERE sno = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, sno);
        rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String sdept = rs.getString("sdept");
            System.out.println(name);
            stuModel.addRow(new Object[]{name, gender, sdept});
        }

        sql = "SELECT course.cname, sc.score, course.cteacher FROM sc JOIN course ON sc.cno = course.cno WHERE sc.sno = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, sno);
        rs = ps.executeQuery();
        while (rs.next()) {
            String subject = rs.getString("cname");
            Integer score = (Integer) rs.getObject("score");
            String teacher = rs.getString("cteacher");
            if (score == null) scoreModel.addRow(new Object[]{subject, "", teacher});
            else scoreModel.addRow(new Object[]{subject, score, teacher});
        }
        close();
    }

    public void loadCourses(DefaultTableModel courseModel) throws SQLException {
        ps = ct.prepareStatement("SELECT * FROM course"); // 从 course 表中加载数据
        rs = ps.executeQuery();
        while (rs.next()) {
            String cno = rs.getString("cno");
            String cname = rs.getString("cname");
            String cteacher = rs.getString("cteacher");
            int credit = rs.getInt("credit");
            courseModel.addRow(new Object[]{cno, cname, cteacher, credit});
        }
        close();
    }

    public void selectCourse(String cno, String sno) throws SQLException {
        ps = ct.prepareStatement("INSERT INTO sc (sno, cno, score) VALUES (?, ?, null)");
        ps.setString(1, sno);
        ps.setString(2, cno);

        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "选课成功");
        } else {
            JOptionPane.showMessageDialog(null, "选课失败");
        }
        close();
    }

    public void addCourse(String cno, String cname, String cteacher, int credit) throws SQLException {
        String sql = "INSERT INTO course (cno, cname, cteacher, credit) VALUES (?, ?, ?, ?)";
        ps = ct.prepareStatement(sql);
        ps.setString(1, cno);
        ps.setString(2, cname);
        ps.setString(3, cteacher);
        ps.setInt(4, credit);

        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "添加课程成功");
        } else {
            JOptionPane.showMessageDialog(null, "添加课程失败");
        }
        close();
    }

    public void deleteCourse(String cno) throws SQLException {
        // 删除 `course` 表中的课程记录
        String sql = "DELETE FROM course WHERE cno = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, cno);
        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "课程删除成功");
        } else {
            JOptionPane.showMessageDialog(null, "课程删除失败");
        }
        close();
    }

    public void loadStudens(DefaultTableModel studensModel) throws SQLException {
        ps = ct.prepareStatement("SELECT * FROM stu");
        rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString("name");
            String gender = rs.getString("gender");
            String sno = rs.getString("sno");
            String sdept = rs.getString("sdept");
            studensModel.addRow(new Object[]{name, gender, sno, sdept});
        }
        close();
    }

    public void deleteStudent(String sno) throws SQLException {
        String sql = "DELETE FROM stu WHERE sno = ?";
        ps = ct.prepareStatement(sql);
        ps.setString(1, sno);
        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "删除学生成功");
        } else {
            JOptionPane.showMessageDialog(null, "删除学生失败");
        }
        close();
    }

    public void addStudent(String name, String gender, String sno, String sdept) throws SQLException {

        String sql = "INSERT INTO stu (name, gender, sno, sdept) VALUES (?, ?, ?, ?)";
        ps = ct.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, gender);
        ps.setString(3, sno);
        ps.setString(4, sdept);

        int n = ps.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "添加学生成功");
        } else {
            JOptionPane.showMessageDialog(null, "添加学生失败");
        }
        close();
    }

    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (ct != null) ct.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}