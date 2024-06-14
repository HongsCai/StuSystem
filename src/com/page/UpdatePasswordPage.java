package com.page;

import com.model.User;
import com.service.ManageHelper;

import javax.swing.*;
import java.awt.*;

public class UpdatePasswordPage extends JFrame {
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmPasswordLabel;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton confirmButton;
    private JButton cancelButton;
    private User user;

    public UpdatePasswordPage(User user) {
        setTitle("修改密码");
        this.user = user;
        this.setLayout(new GridLayout(5, 1, 10, 10));
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建并添加每行的 JPanel
        JPanel oldPasswordPanel = new JPanel();
        oldPasswordLabel = new JLabel("原密码:");
        oldPasswordField = new JPasswordField(15);
        oldPasswordPanel.add(oldPasswordLabel, BorderLayout.WEST);
        oldPasswordPanel.add(oldPasswordField, BorderLayout.CENTER);

        JPanel newPasswordPanel = new JPanel();
        newPasswordLabel = new JLabel("新密码:");
        newPasswordField = new JPasswordField(15);
        newPasswordPanel.add(newPasswordLabel, BorderLayout.WEST);
        newPasswordPanel.add(newPasswordField, BorderLayout.CENTER);

        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordPanel.add(confirmPasswordLabel, BorderLayout.WEST);
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("确认");
        confirmButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (oldPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "原密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "新密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "确认密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPassword.equals(confirmPassword)) {
                ManageHelper helper = new ManageHelper();
                if (helper.Login(user)) {
                    if (helper.updatePassword(user, newPassword)) {
                        JOptionPane.showMessageDialog(this, "修改密码成功！");
                        dispose();
                    }
                    else JOptionPane.showMessageDialog(this, "修改密码失败！");
                } else {
                    JOptionPane.showMessageDialog(this, "原密码不正确！", "", JOptionPane.WARNING_MESSAGE);
                    resetFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "两次密码不一致", "", JOptionPane.WARNING_MESSAGE);
                resetFields();
            }
        });

        cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        this.add(oldPasswordPanel);
        this.add(newPasswordPanel);
        this.add(confirmPasswordPanel);
        this.add(new JPanel());
        this.add(buttonPanel);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void resetFields() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}