package frame;

import database.DBManage;

import java.awt.*;
import java.awt.event.*;
import javax.print.attribute.standard.PrinterInfo;
import javax.swing.*;

import static java.util.Arrays.binarySearch;

public class PostLand extends BasicFrame implements ActionListener, ItemListener, FocusListener {

    boolean flag = false;

    int lno;

    JButton _return = new JButton("返回");

    JButton submit = new JButton("提交");
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
    JTextField priceText = new JTextField();

    int uno;

    public PostLand() {
        super();
        this.SetComponents();
    }

    public PostLand(int x, int y, int _uno) {
        super(x, y);
        uno = _uno;
        this.SetComponents();
    }

    public PostLand(int x, int y, int _uno, String LType, String TTYpe, String location, int price, int LNO) {
        frameX = x;
        frameY = y;
        uno = _uno;
        flag = true;
        lno = LNO;
        InitFrame();
        this.SetComponents();
        this.setDefault(LType, TTYpe, location, price);
    }

    private int getIndex(String[] list, String tar) {
        for (int i = 0; i < list.length; i++) {
            if (tar.equals(list[i])) {
                return i;
            }
        }
        return -1;
    }

    public void setDefault(String LType, String TTYpe, String location, int price) {
        int lIndex = getIndex(allLandTypes, LType);
        int tIndex = getIndex(allTransTypes, TTYpe);
        int locationIndex = getIndex(allLocations, location);
        System.out.println(locationIndex);
        String priceStr = new String(String.valueOf(price));
        landType.setSelectedIndex(lIndex);
        transType.setSelectedIndex(tIndex);
        locationType.setSelectedIndex(locationIndex);
        priceText.setText(priceStr);
    }

    @Override
    void SetComponents() {
        landType.setEditable(false);
        landType.setLocation(140, 55);
        landType.setSize(120, 30);
        landType.addItemListener(this);

        transType.setEditable(false);
        transType.setLocation(140, 105);
        transType.setSize(120, 30);
        transType.addItemListener(this);

        locationType.setEditable(false);
        locationType.setLocation(140, 155);
        locationType.setSize(120, 30);
        locationType.addItemListener(this);

        priceText.setEditable(true);
        priceText.setLocation(140, 205);
        priceText.setSize(120, 30);

        _return.setLocation(80, 250);
        _return.setSize(Types.BUTTON_SIZE);
        _return.addActionListener(this);

        submit.setLocation(230, 250);
        submit.setSize(Types.BUTTON_SIZE);
        submit.addActionListener(this);

        JLabel landLabel = new JLabel();
        JLabel transLabel = new JLabel();
        JLabel locationLabel = new JLabel();
        JLabel priceLabel = new JLabel();

        landLabel.setText("土地类型");
        landLabel.setFont(Types.TEXT_STYLE);
        landLabel.setLocation(30, 60);
        landLabel.setSize(Types.SMALL_LABEL_SIZE);

        transLabel.setText("流转类型");
        transLabel.setFont(Types.TEXT_STYLE);
        transLabel.setLocation(30, 110);
        transLabel.setSize(Types.SMALL_LABEL_SIZE);

        locationLabel.setText("土地位置");
        locationLabel.setFont(Types.TEXT_STYLE);
        locationLabel.setLocation(30, 160);
        locationLabel.setSize(Types.SMALL_LABEL_SIZE);

        priceLabel.setText("流转价格");
        priceLabel.setFont(Types.TEXT_STYLE);
        priceLabel.setLocation(30, 210);
        priceLabel.setSize(Types.SMALL_LABEL_SIZE);

        panel.add(landType);
        panel.add(transType);
        panel.add(locationType);
        panel.add(priceText);
        panel.add(_return);
        panel.add(submit);
        panel.add(landLabel);
        panel.add(transLabel);
        panel.add(locationLabel);
        panel.add(priceLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _return) {
            if (!flag) {
                frame.dispose();
                new UserFrame(400, 350, uno);
            } else {
                frame.dispose();
                new MyPost(600, 337, uno);
            }
        } else if (e.getSource() == submit) {
            DBManage manage = new DBManage();
            String tarLandType = (String) landType.getSelectedItem();
            String tarTransType = (String) transType.getSelectedItem();
            String tarLocationType = (String) locationType.getSelectedItem();
            int price;
            System.out.println(priceText.getText());
            try {
                price = Integer.parseInt(priceText.getText());
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
                return;
            }
            if (!flag) {
                int lno = manage.postLand(uno, tarLandType, tarTransType, tarLocationType, price);
                if (lno > 0) {
                    PrintInfo("发布成功", "发布结果");
                    frame.dispose();
                    new UserFrame(400, 350, uno);
                } else {
                    PrintInfo("发布失败", "发布结果");
                }
            } else {
                manage.changeLand(lno, tarLandType, tarTransType, tarLocationType, price);
                JOptionPane.showMessageDialog(this.panel, "修改成功", "系统提示", JOptionPane.WARNING_MESSAGE);
                frame.dispose();
                new MyPost(600, 337, uno);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        String temp = priceText.getText();
        if (temp.equals(priceText)) {
            priceText.setText("");
            priceText.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        String temp = priceText.getText();
        if (temp.equals("")) {
            priceText.setForeground(Color.GRAY);
            priceText.setText("1234");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    void PrintInfo(String info, String title) {
        JLabel label = new JLabel();
        int output = JOptionPane.showConfirmDialog(null, info, title, JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            label.setText("yes");
        }
    }
}
