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
    }

    // 初始化各种参数
    public MainFrame(int x, int y) {
        super(x, y);

        // 定义按钮的位置
        register.setLocation(80, 100);
        register.setSize(90, 40);
        register.addActionListener(this);
        logIn.setLocation(230, 100);
        logIn.setSize(90, 40);
        logIn.addActionListener(this);

        label.setText("土地流转");
        label.setFont(new Font(null, Font.PLAIN, 25));
        label.setLocation(140, 20);
        label.setSize(120, 60);

        panel.add(register);
        panel.add(logIn);
        panel.add(label);

        System.out.print("Init finished");
    }

    public boolean logIn(String userName, String pwd) {
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.register) {
            frame.dispose();
            new Register(frameX, frameY);
        } else if (e.getSource() == this.logIn) {
            frame.dispose();
            new LogIn(frameX, frameY);
        }
    }
}
