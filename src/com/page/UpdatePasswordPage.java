package com.page;

import com.model.User;
import com.service.ManageHelper;
import javax.swing.*;
import java.awt.*;

/**
 * 用户修改密码的界面
 * @author HongsCai
 * @date 2024/6/18
 */

public class UpdatePasswordPage extends JFrame {
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private User user;

    public UpdatePasswordPage(User user) {
        setTitle("修改密码");
        this.user = user;
        setLayout(new GridLayout(5, 1, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 创建并添加每行的 JPanel
        JPanel oldPasswordPanel = new JPanel();
        oldPasswordField = new JPasswordField(15);
        oldPasswordPanel.add(new JLabel("原密码:"), BorderLayout.WEST);
        oldPasswordPanel.add(oldPasswordField, BorderLayout.CENTER);

        JPanel newPasswordPanel = new JPanel();
        newPasswordField = new JPasswordField(15);
        newPasswordPanel.add(new JLabel("新密码:"), BorderLayout.WEST);
        newPasswordPanel.add(newPasswordField, BorderLayout.CENTER);

        JPanel confirmPasswordPanel = new JPanel();
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordPanel.add(new JLabel("确认密码:"), BorderLayout.WEST);
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);


        JButton confirmButton = new JButton("确认");
        confirmButton.addActionListener(e -> {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (oldPassword.isEmpty()) {
                JOptionPane.showMessageDialog(UpdatePasswordPage.this, "原密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(UpdatePasswordPage.this, "新密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(UpdatePasswordPage.this, "确认密码不能为空！", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (newPassword.equals(confirmPassword)) {
                ManageHelper helper = new ManageHelper();
                if (helper.Login(user)) {
                    if (helper.updatePassword(user, newPassword)) {
                        JOptionPane.showMessageDialog(UpdatePasswordPage.this, "修改密码成功！");
                        dispose();
                    }
                    else JOptionPane.showMessageDialog(UpdatePasswordPage.this, "修改密码失败！");
                } else {
                    JOptionPane.showMessageDialog(UpdatePasswordPage.this, "原密码不正确！", "", JOptionPane.WARNING_MESSAGE);
                    resetFields();
                }
            } else {
                JOptionPane.showMessageDialog(UpdatePasswordPage.this, "两次密码不一致", "", JOptionPane.WARNING_MESSAGE);
                resetFields();
            }
        });

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        add(oldPasswordPanel);
        add(newPasswordPanel);
        add(confirmPasswordPanel);
        add(buttonPanel);

        setVisible(true);
    }

    private void resetFields() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }
}