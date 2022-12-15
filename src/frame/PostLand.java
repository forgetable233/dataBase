package frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class PostLand extends BasicFrame implements ActionListener, ItemListener {
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

    JComboBox<String> landType = new JComboBox<String>(allLandTypes);

    JComboBox<String> transType = new JComboBox<String>(allTransTypes);

    JComboBox<String> locationType = new JComboBox<String>(allLocations);
    JTextField price = new JTextField();

    int uno;

    public PostLand() {
        super();
        this.SetComponents();
    }

    public PostLand(int x, int y, int uno) {
        super(x, y);
        this.SetComponents();
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

        price.setEditable(true);
        price.setLocation(140, 205);
        price.setSize(120, 30);

        _return.setLocation(80, 250);
        _return.setSize(Types.BUTTON_SIZE);

        submit.setLocation(230, 250);
        submit.setSize(Types.BUTTON_SIZE);
        JLabel landLabel = new JLabel();
        JLabel transLabel = new JLabel();
        JLabel locationLabel = new JLabel();


        panel.add(landType);
        panel.add(transType);
        panel.add(locationType);
        panel.add(price);
        panel.add(_return);
        panel.add(submit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == landType) {
            if (e.getStateChange() == ItemEvent.SELECTED) {

            }
        } else if (e.getSource() == transType) {
            if (e.getStateChange() == ItemEvent.SELECTED) {

            }
        } else if (e.getSource() == locationType) {
            if (e.getStateChange() == ItemEvent.SELECTED) {

            }
        }
    }
}
