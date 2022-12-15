package frame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserFrame extends BasicFrame implements ActionListener {
    JButton postLand = new JButton("提交土地");

    JButton selectLand = new JButton("查找土地");

    JLabel label = new JLabel();

    int uno = -1;

    public UserFrame() {
        super();
        this.SetComponents();
    }

    public UserFrame(int x, int y, int _uno) {
        super(x, y);
        uno = _uno;
        this.SetComponents();
    }

    @Override
    void SetComponents() {
        postLand.setLocation(80, 100);
        postLand.setSize(Types.BUTTON_SIZE);
        postLand.addActionListener(this);

        selectLand.setLocation(230, 100);
        selectLand.setSize(Types.BUTTON_SIZE);
        selectLand.addActionListener(this);

        label.setText("欢迎使用");
        label.setFont(Types.TEXT_STYLE);
        label.setLocation(140, 20);
        label.setSize(Types.MAIN_LABEL_SIZE);

        panel.add(postLand);
        panel.add(selectLand);
        panel.add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == postLand) {
            frame.dispose();
            new PostLand(400, 350, uno);
        }
    }
}
