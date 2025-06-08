package com.page;

import com.service.ManageHelper;
import com.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * 学生信息管理（查询）界面
 * @author HongsCai
 * @date 2024/6/18
 */

public class StuAllQueryPage extends JFrame {
    private JTable studentTable;
    private DefaultTableModel studentModel;
    private JScrollPane studentScrollPane;
    private JComboBox queryList;
    private ManageHelper helper;
    private String option = "全部", input = "";

    public StuAllQueryPage(User user) {
        setSize(760, 550);
        setLayout(new BorderLayout());

        String[] studentColumnNames = {"姓名", "性别", "学号", "专业"};
        studentModel = new DefaultTableModel(studentColumnNames, 0);
        studentTable = new JTable(studentModel);
        studentScrollPane = new JScrollPane(studentTable);

        helper = new ManageHelper();
        helper.loadStudens(studentModel, option, input);

        JPanel queryPanel = new JPanel();
        queryPanel.add(new JLabel("请输入查询信息:"));
        JTextField queryField = new JTextField(15);
        queryPanel.add(queryField);

        queryList = new JComboBox<String>(new Vector<>(Arrays.asList("全部", "学号", "姓名", "性别", "专业")));
        queryList.addItemListener(e -> queryField.setText(""));
        queryPanel.add(queryList);

        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(e -> {
            input = queryField.getText();
            option = (String) queryList.getSelectedItem();
            refreshTable();
        });
        queryPanel.add(queryButton);

        JButton detailButton = new JButton("详细信息");
        detailButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                String sno = (String) studentModel.getValueAt(selectedRow, 2);
                new StuDetailQueryPage(sno);
            } else
                JOptionPane.showMessageDialog(StuAllQueryPage.this, "请先选择要查看详细信息的学生");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailButton);

        if (!user.getType().equals("学生")) {
            setTitle("学生信息管理");
            JButton addButton = new JButton("添加学生");
            addButton.addActionListener(e -> {
                JPanel panel = new JPanel(new GridLayout(4, 2));

                JTextField nameField = new JTextField();
                JTextField genderField = new JTextField();
                JTextField snoField = new JTextField();
                JTextField majorField = new JTextField();

                panel.add(new JLabel("学生姓名："));
                panel.add(nameField);
                panel.add(new JLabel("学生性别："));
                panel.add(genderField);
                panel.add(new JLabel("学生学号："));
                panel.add(snoField);
                panel.add(new JLabel("学生专业："));
                panel.add(majorField);

                int option = JOptionPane.showConfirmDialog(StuAllQueryPage.this, panel, "添加学生", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    String gender = genderField.getText();
                    String sno = snoField.getText();
                    String major = majorField.getText();
                    if (name != null && gender != null && sno != null && major != null) {
                        new ManageHelper().addStudent(name, gender, sno, major);
                        refreshTable();
                    } else JOptionPane.showMessageDialog(StuAllQueryPage.this, "请正确输入");
                }
            });

            JButton deleteButton = new JButton("删除学生");
            deleteButton.addActionListener(e -> {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(StuAllQueryPage.this, "确定删除选中的学生吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String sno = (String) studentModel.getValueAt(selectedRow, 2); // 学号列
                        new ManageHelper().deleteStudent(sno);
                        refreshTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(StuAllQueryPage.this, "请先选择要删除的行");
                }
            });

            buttonPanel.add(addButton);
            buttonPanel.add(deleteButton);
        } else setTitle("学生信息查询");

        add(queryPanel, BorderLayout.NORTH);
        add(studentScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Image icon = new ImageIcon(StudentPage.class.getResource("/resources/images/icon.png")).getImage();
        setIconImage(icon);

        setVisible(true);
    }

    private void refreshTable() {
        studentModel.setRowCount(0);                                   // 清空表格数据
        helper.loadStudens(studentModel, option, input);               // 重新加载数据
    }
}