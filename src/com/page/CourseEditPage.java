package com.page;

import com.service.ManageHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class CourseEditPage extends JFrame {
    private DefaultTableModel courseModel;
    private JTable courseTable;
    private JComboBox queryList;
    private ManageHelper helper;
    private String option = "全部", input = "";

    public CourseEditPage() {
        setTitle("学生课程管理");
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

        // 加载课程数据
        helper = new ManageHelper();
        helper.loadCourse(courseModel, option, input);

        JPanel buttonPanel = new JPanel();

        // 添加按钮
        JButton addButton = new JButton("添加课程");
        addButton.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(4, 2));

            JTextField cnoField = new JTextField();
            JTextField cnameField = new JTextField();
            JTextField cteacherField = new JTextField();
            JTextField creditField = new JTextField();

            panel.add(new JLabel("课程号："));
            panel.add(cnoField);
            panel.add(new JLabel("课程名："));
            panel.add(cnameField);
            panel.add(new JLabel("教师："));
            panel.add(cteacherField);
            panel.add(new JLabel("学分："));
            panel.add(creditField);

            int option = JOptionPane.showConfirmDialog(CourseEditPage.this, panel, "添加课程", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String cno = cnoField.getText();
                String cname = cnameField.getText();
                String cteacher = cteacherField.getText();
                int credit = Integer.parseInt(creditField.getText());
                helper.addCourse(cno, cname, cteacher, credit);
                courseModel.addRow(new Object[]{cno, cname, cteacher, credit});
            }
        });
        buttonPanel.add(addButton);

        // 删除按钮
        JButton deleteButton = new JButton("删除课程");
        deleteButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                int option = JOptionPane.showConfirmDialog(CourseEditPage.this, "确定删除选中的课程吗？", "确认删除", JOptionPane.YES_NO_OPTION,  JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    String cno = (String) courseModel.getValueAt(selectedRow, 0);
                    helper.deleteCourse(cno);
                    refreshTable();
                }
            } else {
                JOptionPane.showMessageDialog(CourseEditPage.this, "请先选择要删除的课程");
            }
        });
        buttonPanel.add(deleteButton);

        // 查询
        JPanel queryPanel = new JPanel();
        queryPanel.add(new JLabel("请输入查询信息:"));

        JTextField queryField = new JTextField(15);
        queryPanel.add(queryField);

        queryList = new JComboBox<String>(new Vector<>(Arrays.asList("全部", "科目", "课程名", "教师")));
        queryList.addItemListener(e -> queryField.setText(""));
        queryPanel.add(queryList);

        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(e -> {
            option = queryField.getText();
            input = queryField.getText().trim();
            refreshTable();
        });
        queryPanel.add(queryButton);

        add(queryPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void refreshTable() {
        courseModel.setRowCount(0);                             // 清空表格数据
        helper.loadCourse(courseModel, option, input);          // 重新加载数据
    }
}