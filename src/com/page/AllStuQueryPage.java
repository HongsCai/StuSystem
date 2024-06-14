package com.page;

import com.service.ManageHelper;
import com.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AllStuQueryPage extends JFrame {
    private JTable studentTable;
    private DefaultTableModel studentModel;
    private JScrollPane studentScrollPane;

    public AllStuQueryPage(User user) {
        setTitle("所有学生信息查询");
        setSize(760, 550);
        setLayout(new BorderLayout());

        String[] studentColumnNames = {"姓名", "性别", "学号", "院系"};
        studentModel = new DefaultTableModel(studentColumnNames, 0);

        studentTable = new JTable(studentModel);

        studentScrollPane = new JScrollPane(studentTable);

        add(studentScrollPane, BorderLayout.CENTER);

        new ManageHelper().loadStudens(studentModel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (!user.getType().equals("学生")) {
            JPanel buttonPanel = new JPanel();

            JButton addButton = new JButton("添加学生");
            addButton.addActionListener(e -> addStudentAction(e));

            JButton deleteButton = new JButton("删除学生");
            deleteButton.addActionListener(e -> deleteStudentAction(e));

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        setVisible(true);
    }

    private void addStudentAction(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "请输入学生姓名:");
        String gender = JOptionPane.showInputDialog(this, "请输入学生性别:");
        String sno = JOptionPane.showInputDialog(this, "请输入学生学号:");
        String sdept = JOptionPane.showInputDialog(this, "请输入学生院系:");

        if (name != null && gender != null && sno != null && sdept != null) {
            new ManageHelper().addStudent(name, gender, sno, sdept);
            refreshTable();
        }
    }

    private void deleteStudentAction(ActionEvent e) {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            int option = JOptionPane.showConfirmDialog(this, "确定删除选中的学生吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                String sno = (String) studentModel.getValueAt(selectedRow, 2); // 学号列
                new ManageHelper().deleteStudent(sno);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要删除的行");
        }
    }

    private void refreshTable() {
        studentModel.setRowCount(0); // 清空表格数据
        new ManageHelper().loadStudens(studentModel); // 重新加载数据
    }
}