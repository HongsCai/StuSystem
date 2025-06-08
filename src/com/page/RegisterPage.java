package com.page;

import com.model.User;
import com.service.ManageHelper;
import javax.swing.*;
import java.awt.*;

/**
 * 注册界面
 * @author HongsCai
 * @date 2024/6/18
 */

public class RegisterPage extends JFrame {
    private JComboBox<String> identityComboBox;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterPage() {
        setTitle("注册");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        JPanel identityPanel = new JPanel();
        identityComboBox = new JComboBox<>(new String[]{"学生", "教师"});
        identityPanel.add(new JLabel("身份:"));
        identityPanel.add(identityComboBox);

        JPanel usernamePanel = new JPanel();
        usernameField = new JTextField(15);
        usernamePanel.add(new JLabel("用户名:"));
        usernamePanel.add(usernameField);

        JPanel passwordPanel = new JPanel();
        passwordField = new JPasswordField(15);
        passwordPanel.add(new JLabel("密  码:"));
        passwordPanel.add(passwordField);

        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordPanel.add(new JLabel("确认密码:"));
        confirmPasswordPanel.add(confirmPasswordField);

        JPanel buttonPanel = new JPanel();
        registerButton = new JButton("注册");
        cancelButton = new JButton("取消");
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        add(identityPanel);
        add(usernamePanel);
        add(passwordPanel);
        add(confirmPasswordPanel);
        add(buttonPanel);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();                               // 获得用户名
            String password = new String(passwordField.getPassword());                      // 获得密码
            String confirmPassword = new String(confirmPasswordField.getPassword());        // 获得确认密码
            String type = (String) identityComboBox.getSelectedItem();                      // 获得用户类型

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "确认密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "两次密码不一致！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User user = new User(username, password, type);                     //创建用户对象
            ManageHelper helper = new ManageHelper();                           //创建数据库业务处理对象
            if (helper.Register(user)) {                                        //注册处理
                JOptionPane.showMessageDialog(this, "注册成功！");
                dispose();                              // 关闭当前窗口
                new LoginPage();                        // 返回登陆页面
            } else {
                JOptionPane.showMessageDialog(this, "注册失败！");
                resetFields();
            }
        });
        cancelButton.addActionListener(e -> {
            new LoginPage();
            dispose();
        });


        Image icon = new ImageIcon(StudentPage.class.getResource("/resources/images/icon.png")).getImage();
        setIconImage(icon);

        setVisible(true);
    }
    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
