package frame;

import database.DBManage;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

public class MyPost implements ActionListener {
    int frameX;

    int frameY;

    JFrame frame = new JFrame();

    int uno;

    Vector<String> LTypes = new Vector<String>();

    Vector<String> TTypes = new Vector<String>();

    Vector<String> locations = new Vector<String>();
    Vector<Integer> applyNum = new Vector<Integer>();
    Vector<Integer> prices = new Vector<Integer>();

    Vector<Integer> LNOs = new Vector<Integer>();

    JPanel mainPanel = new JPanel();

    public MyPost() {
        super();
        this.SetComponents();
    }

    public MyPost(int x, int y, int _uno) {
        frameX = x;
        frameY = y;
        uno = _uno;
        this.GetAllMyPosts();
        InitFrame();
        this.SetComponents();
    }

    private void GetAllMyPosts() {
        DBManage manage = new DBManage();
        manage.getMyPost(uno, LTypes, TTypes, locations, applyNum, prices, LNOs);
        System.out.println("finish get my post");
    }

    protected void InitFrame() {
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(frameX, frameY);
        frame.setResizable(false);

        mainPanel.setLayout(null);
        frame.setContentPane(mainPanel);
    }

    void SetComponents() {
        String[] columnNames = {"土地类型", "转让类型", "转让价格", "地址", "申请人数"};
        int landNum = LNOs.size();
        Object[][] rowData = new Object[landNum][5];
        for (int i = 0; i < landNum; i++) {
            rowData[i][0] = LTypes.get(i);
            rowData[i][1] = TTypes.get(i);
            rowData[i][2] = prices.get(i);
            rowData[i][3] = locations.get(i);
            rowData[i][4] = applyNum.get(i);
        }
        JTable table = new JTable(rowData, columnNames);
        // 表格内
        table.setForeground(Color.BLACK);
        table.setFont(new Font(null, Font.PLAIN, 14));
        table.setSelectionForeground(Color.DARK_GRAY);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setGridColor(Color.GRAY);

        // 表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列

        // 设置行高
        table.setRowHeight(30);

        // 第一列列宽设置为40
        table.getColumnModel().getColumn(0).setPreferredWidth(40);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 39, 564, 232);

        JButton _return = new JButton("返回");
        _return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new UserFrame(400, 350, uno);
            }
        });
        _return.setBounds(365, 8, 63, 23);

        JButton delete = new JButton("删除");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(mainPanel, "请选择一条记录", "系统提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    DBManage manage = new DBManage();
                    int LNO = LNOs.get(row);
                    manage.deleteLand(LNO);
                    JOptionPane.showMessageDialog(mainPanel, "删除成功", "系统提示", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    new MyPost(600, 337, uno);
                }
            }
        });
        delete.setBounds(438, 8, 63, 23);
        JButton change = new JButton("修改");
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(mainPanel, "请选择一条记录", "系统提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    frame.dispose();
                    String lType = LTypes.get(row);
                    String tType = TTypes.get(row);
                    String location = locations.get(row);
                    int price = prices.get(row);
                    int LNO = LNOs.get(row);
                    frame.dispose();
                    new PostLand(400, 350, uno, lType, tType, location, price, LNO);
                }
            }
        });
        change.setBounds(511, 8, 63, 23);

        mainPanel.add(_return);
        mainPanel.add(delete);
        mainPanel.add(change);
        mainPanel.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        MyPost test = new MyPost(600, 337, 1);
    }
}
