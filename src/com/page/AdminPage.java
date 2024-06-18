package com.page;

import com.model.User;

import javax.swing.*;
import java.awt.*;
/**
 * 管理员主界面
 * @author HongsCai
 * @date 2024/6/18
 */
public class AdminPage extends JFrame {
    private JMenuBar menuBar; // 应用菜单条
    private JMenu infoMenu, courseMenu, accountMenu; // 学生、课程和账户管理菜单
    private JMenuItem manageStudentInfoItem, changePasswordItem, exitItem, selectCourseItem, viewAllCoursesItem; // 菜单项

    public AdminPage(User user) {
        setTitle("学生成绩管理系统：" + user.getType() + " " + user.getUsername()); // 设置窗口标题
        menuBar = new JMenuBar(); // 初始化菜单条
        setJMenuBar(menuBar); // 将菜单条设置到窗口

        // 创建菜单
        infoMenu = new JMenu("学生管理"); // 学生管理菜单
        courseMenu = new JMenu("课程管理"); // 课程管理菜单
        accountMenu = new JMenu("账户管理"); // 账户管理菜单

        // 将菜单添加到菜单条
        menuBar.add(infoMenu);
        menuBar.add(courseMenu);
        menuBar.add(accountMenu);

        // 学生管理菜单项
        manageStudentInfoItem = new JMenuItem("学生信息管理");

        // 为菜单项添加事件监听器
        manageStudentInfoItem.addActionListener(e -> {
            new StuAllQueryPage(user); // 打开所有学生查询页面，传入当前用户对象
        });

        // 将菜单项添加到学生管理菜单
        infoMenu.add(manageStudentInfoItem);

        // 课程管理菜单项
        selectCourseItem = new JMenuItem("学生课程管理");
        viewAllCoursesItem = new JMenuItem("学生课程成绩管理");

        // 为菜单项添加事件监听器
        selectCourseItem.addActionListener(e -> {
            new CourseEditPage(); // 打开选课编辑页面
        });
        viewAllCoursesItem.addActionListener(e -> {
            new CourseAllQueryPage(user); // 打开所有选课查询页面，传入当前用户对象
        });

        // 将菜单项添加到课程管理菜单
        courseMenu.add(selectCourseItem);
        courseMenu.add(viewAllCoursesItem);

        // 账户管理菜单项
        changePasswordItem = new JMenuItem("修改密码");
        exitItem = new JMenuItem("退出账号");

        // 为菜单项添加事件监听器
        changePasswordItem.addActionListener(e -> {
            new UpdatePasswordPage(user); // 打开修改密码页面，传入当前用户对象
        });
        exitItem.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            new LoginPage(); // 打开登录页面
        });

        // 将菜单项添加到账户管理菜单
        accountMenu.add(changePasswordItem);
        accountMenu.add(exitItem);

        setSize(800, 600); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置默认关闭操作
        setLocationRelativeTo(null); // 设置窗口居中显示

        // 设置窗口背景图片
        Image image = new ImageIcon(StudentPage.class.getResource("/resources/images/picture.png")).getImage();
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        });
        setVisible(true); // 显示窗口
    }
}