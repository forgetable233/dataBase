package frame;

import database.DBManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SelectLand implements ActionListener {
    boolean flag = false;
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

    Vector<Integer> UNOs = new Vector<>();

    JPanel mainPanel = new JPanel();

    final String[] allLandTypes = new String[]{"耕地", "林地", "园地",
            "草地", "养殖用地", "住宅用地",
            "工矿存储", "商服用地", "公共用地",
            "农房", "水域", "其他"};

    final String[] allTransTypes = new String[]{"转让", "租让", "转包", "互换", "入股", "合作"};

    final String[] allLocations = new String[]{"河北省", "山西省", "辽宁省",
            "吉林省", "黑龙江省", "江苏省",
            "浙江省", "安徽省", "福建省",
            "江西省", "山东省", "河南省",
            "湖北省", "湖南省", "广东省",
            "海南省", "四川省", "贵州省",
            "云南省", "陕西省", "甘肃省",
            "青海省", "台湾省", "内蒙古自治区",
            "广西壮族自治区", "西藏自治区", "宁夏回族自治区",
            "新疆维吾尔自治区", "北京市", "天津市",
            "上海市", "重庆市", "香港特别行政区", "澳门特别行政区"};

    JComboBox<String> landType = new JComboBox<>(allLandTypes);

    JComboBox<String> transType = new JComboBox<>(allTransTypes);

    JComboBox<String> locationType = new JComboBox<>(allLocations);

    public SelectLand() {
        this.SetComponents();
    }

    public SelectLand(int x, int y, int _uno) {
        frameX = x;
        frameY = y;
        uno = _uno;
        DBManage temp = new DBManage();
        temp.getAllLands(LTypes, TTypes, locations, applyNum, prices, LNOs, UNOs, uno);
        InitFrame();
        this.SetComponents();
    }

    public SelectLand(int x, int y, int _uno, String tarLand, String tarTrans, String tarLocation) {
        frameX = x;
        frameY = y;
        uno = _uno;
        DBManage temp = new DBManage();
        temp.getTarLands(tarLand, tarTrans, tarLocation, LTypes, TTypes, locations, applyNum, prices, LNOs, UNOs);
        InitFrame();
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
        _return.setBounds(511, 8, 63, 23);

        JButton delete = new JButton("申请");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(mainPanel, "请选择一条记录", "系统提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    DBManage manage = new DBManage();
                    int LNO = LNOs.get(row);
                    int UNO = UNOs.get(row);
                    manage.submitApply(LNO, uno, UNO);
                    // TODO 不可重复申请以及申请自己的判断
                    JOptionPane.showMessageDialog(mainPanel, "申请成功", "系统提示", JOptionPane.WARNING_MESSAGE);
                    frame.dispose();
                    new SelectLand(600, 337, uno);
                }
            }


        });
        delete.setBounds(438, 8, 63, 23);

        JButton search = new JButton("查询");
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tarLand = (String) landType.getSelectedItem();
                String tarTrans = (String) transType.getSelectedItem();
                String tarLocation = (String) locationType.getSelectedItem();
                frame.dispose();
                new SelectLand(frameX, frameY, uno, tarLand, tarTrans, tarLocation);
            }
        });
        search.setBounds(250, 8, 63, 23);

        landType.setBounds(88, 8, 63, 23);
        transType.setBounds(15, 8, 63, 23);
        locationType.setBounds(161, 8, 63, 23);

        mainPanel.add(_return);
        mainPanel.add(delete);
        mainPanel.add(search);
        mainPanel.add(landType);
        mainPanel.add(transType);
        mainPanel.add(locationType);
        mainPanel.add(scrollPane);
    }

    public static void main(String[] args) {
        new SelectLand(600, 337, 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
