package frame;

import database.DBManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

public class MyPost extends BasicFrame implements ActionListener {
    int uno;

    Vector<String> LTypes = new Vector<String>();

    Vector<String> TTypes = new Vector<String>();


    Vector<Integer> applyNum = new Vector<Integer>();
    Vector<Integer> prices = new Vector<Integer>();

    Vector<Integer> LNOs = new Vector<Integer>();
    public MyPost() {
        super();
        this.SetComponents();
    }

    public MyPost(int x, int y, int _uno) {
        super(x, y);
        uno = _uno;
        this.GetAllMyPosts();
        this.SetComponents();
    }

    private void GetAllMyPosts() {
        DBManage manage = new DBManage();
        manage.getMyPost(uno, LTypes, TTypes, applyNum, prices, LNOs);
    }

    @Override
    void SetComponents() {
        String[] columnNames = {"土地类型", "转让类型", "转让价格", "申请人数"};
        int landNum = LNOs.size();
        Object[][] rowData = new Object[landNum][4];
        for (int i = 0; i < landNum; i++) {
            rowData[i][0] = LTypes.get(i);
            rowData[i][1] = TTypes.get(i);
            rowData[i][2] = prices.get(i);
            rowData[i][3] = applyNum.get(i);
        }
        System.out.println(rowData[0][1]);
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

        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(240, 170));
        table.setLocation(80, 50);
        table.setSize(240, 200);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
//        frame.pack();
        System.out.println("finish");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
