package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.util.Vector;

import database.DBManage;


public class MainFrame extends BasicFrame implements ActionListener {
    JButton register = new JButton("注册");
    JButton logIn = new JButton("登录");

    JLabel label = new JLabel();

    public MainFrame() {
        super();
        this.SetComponents();
    }

    // 初始化各种参数
    public MainFrame(int x, int y) {
        super(x, y);

        this.SetComponents();
//        System.out.println("Init finished");
    }

    public boolean logIn(String userName, String pwd) {
        return false;
    }

    @Override
    void SetComponents() {
        // 定义按钮的位置
        register.setLocation(80, 100);
        register.setSize(Types.BUTTON_SIZE);
        register.addActionListener(this);
        logIn.setLocation(230, 100);
        logIn.setSize(Types.BUTTON_SIZE);
        logIn.addActionListener(this);

        label.setText("土地流转");
        label.setFont(Types.MAIN_TEXT_STYLE);
        label.setLocation(140, 20);
        label.setSize(Types.MAIN_LABEL_SIZE);

        panel.add(register);
        panel.add(logIn);
        panel.add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.register) {
            frame.dispose();
            new LogIn(frameX, frameY, Type.REGISTER);
        } else if (e.getSource() == this.logIn) {
            frame.dispose();
            new LogIn(frameX, frameY, Type.LOGIN);
        }
    }
}
