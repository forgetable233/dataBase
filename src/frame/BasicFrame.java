package frame;

import java.awt.*;
import javax.swing.*;

public abstract class BasicFrame {
    int frameX;
    int frameY;

    JFrame frame = new JFrame("土地流转");

    JPanel panel = new JPanel(null);


    public BasicFrame() {
        frameX = 400;
        frameY = 200;
        InitFrame();
    }

    public BasicFrame(int x, int y) {
        frameX = x;
        frameY = y;
        InitFrame();
    }

    abstract void SetComponents();

    protected void InitFrame() {
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(frameX, frameY);

        frame.setContentPane(panel);
        frame.setResizable(false);
    }
}
