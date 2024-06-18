package com.page;

import com.service.ManageHelper;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * 学生详细信息界面
 * @author HongsCai
 * @date 2024/6/18
 */

public class StuDetailQueryPage extends JFrame {
    private DefaultTableModel stuModel;
    private DefaultTableModel scoreModel;

    public StuDetailQueryPage(String sno) {
        setLayout(new BorderLayout(10, 10));
        setTitle("详细信息");
        setSize(600, 590);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] stucolumnname = {"姓名", "性别", "学号", "专业"};
        String[] scorecolumnname = {"学科", "分数", "教师"};

        stuModel = new DefaultTableModel(stucolumnname, 0);
        scoreModel = new DefaultTableModel(scorecolumnname, 0);

        JTable stutable = new JTable(stuModel);
        JTable scoretable = new JTable(scoreModel);
        JScrollPane scrollstu = new JScrollPane(stutable);
        JScrollPane scrollscore = new JScrollPane(scoretable);

        new ManageHelper().loadStuDetailQuery(sno, stuModel, scoreModel);

        JPanel p1 = new JPanel(new BorderLayout());
        JPanel p2 = new JPanel(new BorderLayout());

        // 设置p1布局和组件
        p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p1.add(new JLabel("学生信息"), BorderLayout.NORTH);
        p1.add(scrollstu, BorderLayout.CENTER);

        // 设置p2布局和组件
        p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.add(new JLabel("课程信息"), BorderLayout.NORTH);
        p2.add(scrollscore, BorderLayout.CENTER);

        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}