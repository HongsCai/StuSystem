package com.page;

import com.service.ManageHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class CourseSelectionPage extends JFrame {
    private DefaultTableModel courseModel;
    private JTable courseTable;
    private JButton addButton, queryButton;
    private JTextField snoField, queryField;
    private ManageHelper helper;
    private JComboBox queryList;
    private String option = "全部", input = "";


    public CourseSelectionPage() {
        setTitle("学生选课");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setLocationRelativeTo(null);

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

        // 加载课程数据
        helper = new ManageHelper();
        helper.loadCourse(courseModel, option, input);

        // 询问搜索课程
        JPanel queryPanel = new JPanel();
        queryPanel.add(new JLabel("请输入查询信息:"));
        queryField = new JTextField(15);
        queryPanel.add(queryField);

        queryList = new JComboBox<String>(new Vector<>(Arrays.asList("全部", "课程号", "课程名", "教师")));
        queryList.addItemListener(e -> queryField.setText(""));
        queryPanel.add(queryList);

        queryButton = new JButton("查询");
        queryButton.addActionListener(e -> {
            option = (String) queryList.getSelectedItem();
            input = queryField.getText().trim();
            refreshTable();
        });
        queryPanel.add(queryButton);

        // 添加组件到主框架
        add(queryPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // 设置按钮事件
        addButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                String selectedCno = (String) courseModel.getValueAt(selectedRow, 0);
                helper.selectCourse(selectedCno, snoField.getText());
            }
            else JOptionPane.showMessageDialog(this, "请先选择一门课程");
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void refreshTable() {
        courseModel.setRowCount(0);                             // 清空表格数据
        helper.loadCourse(courseModel, option, input);          // 重新加载数据
    }
}