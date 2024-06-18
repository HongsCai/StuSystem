package com.page;

import com.model.User;
import com.service.ManageHelper;
import javax.swing.*;
import java.awt.*;

/**
 * 登录界面
 * @author HongsCai
 * @date 2024/6/18
 */

public class LoginPage extends JFrame {
    private JComboBox<String> identityComboBox;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPage() {
        setTitle("学生成绩管理系统登录"); // 设置窗口标题
        setSize(400, 200); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭操作
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new GridLayout(4, 1)); // 使用网格布局管理器

        // 身份选择面板
        JPanel identityPanel = new JPanel();
        identityComboBox = new JComboBox<>(new String[]{"学生", "教师", "管理员"});
        identityPanel.add(new JLabel("身份:"));
        identityPanel.add(identityComboBox);

        // 用户名输入面板
        JPanel usernamePanel = new JPanel();
        usernameField = new JTextField(15);
        usernamePanel.add(new JLabel("用户名:"));
        usernamePanel.add(usernameField);

        // 密码输入面板
        JPanel passwordPanel = new JPanel();
        passwordField = new JPasswordField(15);
        passwordPanel.add(new JLabel("密  码:"));
        passwordPanel.add(passwordField);

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("登录");
        registerButton = new JButton("注册");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // 登录按钮监听器
        loginButton.addActionListener(e -> {
            String username = usernameField.getText(); // 获取用户名
            String password = new String(passwordField.getPassword()); // 获取密码
            String type = (String) identityComboBox.getSelectedItem(); // 获取身份类型

            // 校验输入
            if (username.equals("")) {
                JOptionPane.showMessageDialog(this, "用户名不能为空", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (password.equals("")) {
                JOptionPane.showMessageDialog(this, "密码不能为空", "", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 创建用户对象并进行登录验证
            User user = new User(username, password, type);
            if (new ManageHelper().Login(user)) {
                JOptionPane.showMessageDialog(this, "登录成功！");
                dispose();

                // 根据身份类型打开对应的页面
                if (identityComboBox.getSelectedItem().equals("学生")) {
                    new StudentPage(user);
                } else if (identityComboBox.getSelectedItem().equals("教师")) {
                    new TeacherPage(user);
                } else {
                    new AdminPage(user);
                }
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误！");
                // 清空输入框内容
                usernameField.setText("");
                passwordField.setText("");
            }
        });

        // 注册按钮监听器
        registerButton.addActionListener(e -> {
            new RegisterPage(); // 打开注册页面
            dispose(); // 关闭登录窗口
        });

        // 添加各面板到窗口
        add(identityPanel);
        add(usernamePanel);
        add(passwordPanel);
        add(buttonPanel);

        setVisible(true); // 显示窗口
    }

    public static void main(String[] args) {
        new LoginPage(); // 启动登录页面
    }
}