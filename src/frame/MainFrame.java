package frame;

import java.awt.*;
public class MainFrame {

    int mainFrameX;

    int mainFrameY;

    Frame mainFrame;

    public MainFrame(){
        mainFrame = new Frame("main frame");
    }

    public MainFrame(int x, int y) {
        mainFrame = new Frame("main frame");
        mainFrame.setSize(x, y);
        mainFrame.setLocation(200, 200);
//        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
        mainFrameX = x;
        mainFrameY = y;
        InitButtons();
        mainFrame.setLayout(null);
        System.out.print("Init finished");
    }

    private void InitButtons() {
        Button register = new Button("注册");
        Button logIn = new Button("登录");
        register.setBounds(10, 10, 10, 10);
        logIn.setBounds(20, 20, 10, 10);
        mainFrame.add(register);
        mainFrame.add(logIn);
        System.out.print("button added\n");
    }
}
