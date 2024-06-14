package com.page;

import com.service.ManageHelper;
import com.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class AllCourseQueryPage extends JFrame {
    DefaultTableModel model = new DefaultTableModel();
    JTable queryAll = new JTable(model);
    JScrollPane jScrollPane = new JScrollPane(queryAll);
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();


    public AllCourseQueryPage(User user) {
        setTitle("所有学生选课查询");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnname = {"姓名", "性别", "学号", "科目", "分数", "教师"};
        model.setColumnIdentifiers(columnname);
        p2.add(jScrollPane);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(p1);
        add(p2);

        int[] columnWidths = {100, 50, 200, 200, 50, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = queryAll.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        jScrollPane.setPreferredSize(new Dimension(700, 450));
        queryAll.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        new ManageHelper().allCourseQueryPage(model);

        if (!user.getType().equals("学生")) {
            JPanel p3 = new JPanel();
            JButton editButton = new JButton("修改成绩");
            editButton.addActionListener(e -> {
                int selectedRow = queryAll.getSelectedRow();
                if (selectedRow != -1) {
                    // 获取选中行的数据
                    String sno = (String) model.getValueAt(selectedRow, 2); // 学号列
                    String subject = (String) model.getValueAt(selectedRow, 3); // 科目列

                    // 弹出输入框让用户输入新的分数
                    String newScoreString = JOptionPane.showInputDialog(AllCourseQueryPage.this,
                            "请输入新的分数:", "修改成绩", JOptionPane.PLAIN_MESSAGE);
                    if (newScoreString != null && !newScoreString.isEmpty()) {
                        try {
                            int newScore = Integer.parseInt(newScoreString);
                            new ManageHelper().updateSc(sno, subject, newScore);
                            dispose();
                            new AllCourseQueryPage(user);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(AllCourseQueryPage.this, "请输入有效的数字");
                        }
                    } else {
                        JOptionPane.showMessageDialog(AllCourseQueryPage.this, "请输入新的分数");
                    }
                } else {
                    JOptionPane.showMessageDialog(AllCourseQueryPage.this, "请先选择要修改的行");
                }
            });

            JButton deleteButton = new JButton("删除课程");
            deleteButton.addActionListener(e -> {
                int selectedRow = queryAll.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(this, "确定删除选中的行吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String sno = (String) model.getValueAt(selectedRow, 2); // 学号列
                        String subject = (String) model.getValueAt(selectedRow, 3); // 科目列
                        new ManageHelper().deleteSc(sno, subject);
                        dispose();
                        new AllCourseQueryPage(user);
                    }
                } else {
                    JOptionPane.showMessageDialog(AllCourseQueryPage.this, "请先选择要删除的行");
                }
            });

            p3.add(editButton);
            p3.add(deleteButton);
            add(p3);
        }

        setVisible(true);
    }
}