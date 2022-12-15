package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import database.DBManage;

enum Type {
    LOGIN,
    REGISTER;
}

public class LogIn extends BasicFrame implements ActionListener {
    JButton _return = new JButton("返回");
    JButton submit = new JButton("提交");
    JTextField userName = new JTextField();
    JTextField pwd = new JTextField();

    Type type;

    public LogIn(Type _type) {
        super();
        type = _type;
        SetComponents();
    }

    public LogIn(int x, int y, Type _type) {
        super(x, y);
        type = _type;
        SetComponents();
    }

    @Override
    void SetComponents() {
        _return.setLocation(80, 100);
        _return.setSize(Types.BUTTON_SIZE);
        _return.addActionListener(this);

        submit.setLocation(230, 100);
        submit.setSize(Types.BUTTON_SIZE);
        submit.addActionListener(this);

        userName.setEditable(true);
        userName.setLocation(140, 20);
        userName.setSize(120, 30);
        pwd.setEditable(true);
        pwd.setLocation(140, 60);
        pwd.setSize(120, 30);

        JLabel userNameText = new JLabel();
        JLabel pwdText = new JLabel();

        userNameText.setText("用户名：");
        userNameText.setFont(Types.TEXT_STYLE);
        userNameText.setLocation(40, 25);
        userNameText.setSize(Types.SMALL_LABEL_SIZE);
        pwdText.setText("密码：");
        pwdText.setFont(Types.TEXT_STYLE);
        pwdText.setLocation(65, 65);
        pwdText.setSize(80, 20);

        panel.add(_return);
        panel.add(submit);
        panel.add(userName);
        panel.add(pwd);
        panel.add(userNameText);
        panel.add(pwdText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            String userName = this.userName.getText();
            String pwd = this.pwd.getText();
            DBManage manage = new DBManage();

            if (type == Type.LOGIN) {
                System.out.println(userName);
                System.out.println(pwd);
                int uno = -1;
                if ((uno = manage.useLogin(userName, pwd)) > 0) {
                    PrintInfo("登录成功", "登录成功");
                    frame.dispose();
                    new UserFrame(frameX, frameY, uno);
                } else {
                    PrintInfo("登录失败，用户名或密码错误", "错误信息");
                    frame.dispose();
                    new MainFrame(frameX, frameY);
                }
            } else {
                if (manage.userRegister(userName, pwd)) {
                    PrintInfo("注册成功请重新登录", "注册成功");
                    frame.dispose();
                    new LogIn(frameX, frameY, Type.LOGIN);
                } else {
                    PrintInfo("注册失败，用户名重复", "错误信息");
                    frame.dispose();
                    new MainFrame(frameX, frameY);
                }
            }
        } else if (e.getSource() == _return) {
            frame.dispose();
            new MainFrame(frameX, frameY);
        }
    }

    void PrintInfo(String info, String title) {
        JLabel label = new JLabel();
        int output = JOptionPane.showConfirmDialog(null, info, title, JOptionPane.YES_NO_OPTION);
        if (output == JOptionPane.YES_OPTION) {
            label.setText("yes");
        }
    }
}
