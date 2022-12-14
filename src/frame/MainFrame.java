package frame;

import java.awt.*;
public class MainFrame {

    Frame mainFrame;

    public MainFrame(){
        mainFrame = new Frame("main frame");
    }

    public MainFrame(int x, int y) {
        new MainFrame();
        mainFrame = new Frame("main frame");
        mainFrame.setSize(400, 400);
        mainFrame.setLocation(200, 200);
        mainFrame.setResizable(true);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Frame f = new Frame("test frame");
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setResizable(true);
        f.setVisible(true);
    }
}
