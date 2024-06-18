package com.page;

import com.service.ManageHelper;
import com.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * 学生课程成绩管理（查询）界面
 * @author HongsCai
 * @date 2024/6/18
 */

public class CourseAllQueryPage extends JFrame {
    private DefaultTableModel model;
    private JTable queryAll;
    private JScrollPane jScrollPane;
    private JComboBox queryList;
    private ManageHelper helper;
    private String option = "全部", input = "";

    public CourseAllQueryPage(User user) {
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        String[] columnname = {"姓名", "性别", "学号", "课程名", "分数", "教师"};
        model = new DefaultTableModel(columnname, 0);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        queryAll = new JTable(model);
        int[] columnWidths = {100, 50, 200, 200, 50, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = queryAll.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        jScrollPane = new JScrollPane(queryAll);
        p2.add(jScrollPane);

        jScrollPane.setPreferredSize(new Dimension(700, 450));
        queryAll.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        helper = new ManageHelper();
        helper.loadCourseAllQuery(model, option, input);

        // 关键词查询
        JPanel queryPanel = new JPanel();
        queryPanel.add(new JLabel("请输入查询信息:"));

        JTextField queryField = new JTextField(15);
        queryPanel.add(queryField);

        queryList = new JComboBox<>(new Vector<>(Arrays.asList("全部", "姓名", "性别", "学号", "课程名", "教师")));
        queryList.addItemListener( e-> queryField.setText(""));
        queryPanel.add(queryList);

        JButton queryButton = new JButton("查询");
        queryButton.addActionListener(e -> {
            option = (String) queryList.getSelectedItem();
            input = queryField.getText().trim();
            refreshTable();
        });
        queryPanel.add(queryButton);

        // 面板添加
        add(queryPanel, BorderLayout.NORTH);
        add(p1);
        add(p2);

        // 学生权限以上账号拥有
        if (!user.getType().equals("学生")) {
            setTitle("学生课程成绩管理");
            JPanel p3 = new JPanel();
            JButton editButton = new JButton("修改成绩");
            editButton.addActionListener(e -> {
                int selectedRow = queryAll.getSelectedRow();
                if (selectedRow != -1) {
                    // 获取选中行的数据
                    String sno = (String) model.getValueAt(selectedRow, 2);         // 学号列
                    String subject = (String) model.getValueAt(selectedRow, 3);     // 科目列

                    // 弹出输入框让用户输入新的分数
                    String newScoreString = JOptionPane.showInputDialog(CourseAllQueryPage.this, "请输入新的分数:", "修改成绩", JOptionPane.PLAIN_MESSAGE);
                    if (newScoreString != null && !newScoreString.isEmpty()) {
                        try {
                            int newScore = Integer.parseInt(newScoreString);
                            new ManageHelper().updateSc(sno, subject, newScore);
                            refreshTable();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(CourseAllQueryPage.this, "请输入有效的数字");
                        }
                    } else if (newScoreString != null)
                        JOptionPane.showMessageDialog(CourseAllQueryPage.this, "请输入有效的数字");
                } else {
                    JOptionPane.showMessageDialog(CourseAllQueryPage.this, "请先选择要修改的行");
                }
            });

            JButton deleteButton = new JButton("删除成绩");
            deleteButton.addActionListener(e -> {
                int selectedRow = queryAll.getSelectedRow();
                if (selectedRow != -1) {
                    int option = JOptionPane.showConfirmDialog(CourseAllQueryPage.this, "确定删除选中的行吗？", "确认删除", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        String sno = (String) model.getValueAt(selectedRow, 2); // 学号列
                        String subject = (String) model.getValueAt(selectedRow, 3); // 科目列
                        new ManageHelper().deleteSc(sno, subject);
                        refreshTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(CourseAllQueryPage.this, "请先选择要删除的行");
                }
            });

            p3.add(editButton);
            p3.add(deleteButton);

            add(p3);
        }
        else setTitle("学生课程成绩查询");
        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);                               // 清空表格数据
        helper.loadCourseAllQuery(model, option, input);    // 重新加载数据
    }
}