package com.page;

import com.service.ManageHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StuDetailQueryPage extends JFrame {
    JLabel id = new JLabel("请输入学生的学号");
    JTextField textid = new JTextField(15);
    JButton confirm = new JButton("确定");
    JButton cancel = new JButton("取消");
    JLabel labelstu = new JLabel("学生信息表");
    JLabel labelscore = new JLabel("成绩信息表");

    JPanel p1 = new JPanel(new FlowLayout());
    JPanel p2 = new JPanel(new FlowLayout());
    JPanel p3 = new JPanel(new BorderLayout());
    JPanel p4 = new JPanel(new BorderLayout());
    String[] stucolumnname = {"姓名", "性别", "院系"};
    String[] scorecolumnname = {"学科", "分数", "教师"};

    public StuDetailQueryPage() {
        setLayout(new BorderLayout(10, 10));
        setTitle("详细学生信息查询");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultTableModel stuModel = new DefaultTableModel();
        DefaultTableModel scoreModel = new DefaultTableModel();

        stuModel.setColumnIdentifiers(stucolumnname);
        scoreModel.setColumnIdentifiers(scorecolumnname);

        JTable stutable = new JTable(stuModel);
        JTable scoretable = new JTable(scoreModel);
        JScrollPane scrollstu = new JScrollPane(stutable);
        JScrollPane scrollscore = new JScrollPane(scoretable);

        // 设置p1布局和组件
        p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p1.add(id);
        p1.add(textid);

        // 设置p2布局和组件
        p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(confirm);
        p2.add(cancel);

        // 设置p3布局和组件
        p3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p3.add(labelstu, BorderLayout.NORTH);
        p3.add(scrollstu, BorderLayout.CENTER);

        // 设置p4布局和组件
        p4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p4.add(labelscore, BorderLayout.NORTH);
        p4.add(scrollscore, BorderLayout.CENTER);

        // 主窗口布局
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(p1, BorderLayout.NORTH);
        topPanel.add(p2, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(p3, BorderLayout.CENTER);
        add(p4, BorderLayout.SOUTH);

        confirm.addActionListener(e -> {
            stuModel.setRowCount(0);
            scoreModel.setRowCount(0);
            new ManageHelper().stuDetailQueryPage(textid.getText(), stuModel, scoreModel);
        });
        cancel.addActionListener(e -> dispose());
        setLocationRelativeTo(null);
        setVisible(true);
    }
}