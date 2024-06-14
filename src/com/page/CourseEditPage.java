package com.page;

import com.service.ManageHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class CourseEditPage extends JFrame {
    private DefaultTableModel courseModel;
    private JTable courseTable;
    private ManageHelper helper;

    public CourseEditPage() {
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

        helper = new ManageHelper();

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

            int option = JOptionPane.showConfirmDialog(this, panel, "添加课程", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                String cno = cnoField.getText();
                String cname = cnameField.getText();
                String cteacher = cteacherField.getText();
                int credit = Integer.parseInt(creditField.getText());
                helper.addCourse(cno, cname, cteacher, credit);
                courseModel.addRow(new Object[]{cno, cname, cteacher, credit});

            }
        });

        // 删除按钮
        JButton deleteButton = new JButton("删除课程");
        deleteButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                int option = JOptionPane.showConfirmDialog(this, "确定删除选中的课程吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    String cno = (String) courseModel.getValueAt(selectedRow, 0);
                    helper.deleteCourse(cno);
                    courseModel.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先选择要删除的课程");
            }
        });

        // 添加组件到主框架
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        add(scrollPane, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);

        // 加载课程数据
        helper.loadCourses(courseModel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setVisible(true);

    }
}