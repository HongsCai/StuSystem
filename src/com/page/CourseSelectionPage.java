package com.page;

import com.service.ManageHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class CourseSelectionPage extends JFrame {
    private DefaultTableModel courseModel;
    private JTable courseTable;
    private JButton addButton;
    private JTextField snoField;
    private ManageHelper helper;

    public CourseSelectionPage() {
        setTitle("课程选择");
        setLayout(new BorderLayout());
        setBounds(300, 300, 600, 400);

        // 表头
        String[] columnNames = {"课程号", "课程名", "教师", "学分"};
        courseModel = new DefaultTableModel(columnNames, 0);
        courseTable = new JTable(courseModel);
        JScrollPane scrollPane = new JScrollPane(courseTable);

        // 调整列宽
        TableColumn creditColumn = courseTable.getColumnModel().getColumn(3); // 第4列（学分）
        creditColumn.setPreferredWidth(50);
        creditColumn.setMinWidth(50);
        creditColumn.setMaxWidth(50);

        // 添加按钮和输入框
        JPanel inputPanel = new JPanel();
        JLabel snoLabel = new JLabel("学号:");
        snoField = new JTextField(10);
        addButton = new JButton("添加");
        inputPanel.add(snoLabel);
        inputPanel.add(snoField);
        inputPanel.add(addButton);

        // 添加组件到主框架
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // 加载课程数据
        helper = new ManageHelper();
        helper.loadCourses(courseModel);

        // 设置按钮事件
        addButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "请先选择一门课程");
                return;
            }
            String selectedCno = (String) courseModel.getValueAt(selectedRow, 0);
            helper.selectCourse(selectedCno, snoField.getText());
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}