package frame;

import database.DBManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class MyApply implements ActionListener {

    int frameX;

    int frameY;

    JFrame frame = new JFrame();

    int uno;

    Vector<String> LTypes = new Vector<String>();

    Vector<String> TTypes = new Vector<String>();

    Vector<String> locations = new Vector<String>();
    Vector<String> state = new Vector<>();
    Vector<Integer> prices = new Vector<Integer>();

    Vector<Integer> LNOs = new Vector<Integer>();

    Vector<Integer> UNOs = new Vector<>();

    JPanel mainPanel = new JPanel();

    public MyApply() {
        super();
        this.SetComponents();
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

    public MyApply(int x, int y, int _uno) {
        frameX = x;
        frameY = y;
        uno = _uno;
        DBManage manage = new DBManage();
        manage.getMyApply(uno, LTypes, TTypes, locations, state, prices, LNOs, UNOs);
        InitFrame();
        this.SetComponents();
    }

    void SetComponents() {
        String[] columnNames = {"土地类型", "转让类型", "转让价格", "地址", "状态"};
        int landNum = LNOs.size();
        Object[][] rowData = new Object[landNum][5];
        for (int i = 0; i < landNum; i++) {
            rowData[i][0] = LTypes.get(i);
            rowData[i][1] = TTypes.get(i);
            rowData[i][2] = prices.get(i);
            rowData[i][3] = locations.get(i);
            rowData[i][4] = state.get(i);
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
        _return.setBounds(511, 8, 63, 23);

        JButton withdraw = new JButton("撤回");
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(mainPanel, "请选择一条记录", "系统提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    DBManage manage = new DBManage();
                    int LNO = LNOs.get(row);
                    int UNO = UNOs.get(row);
                    manage.withdrawApply(uno, LNO);
                    JOptionPane.showMessageDialog(mainPanel, "撤回成功", "系统提示", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    new MyApply(600, 337, uno);
                }
            }


        });
        withdraw.setBounds(438, 8, 63, 23);

        mainPanel.add(_return);
        mainPanel.add(withdraw);
        mainPanel.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        new MyApply(600, 337, 1);
    }
}
