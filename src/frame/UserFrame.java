package frame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserFrame extends BasicFrame implements ActionListener {
    JButton postLand = new JButton("提交土地");

    JButton selectLand = new JButton("查找土地");

    JButton myPost = new JButton("我的发布");

    JButton myApply = new JButton("我的申请");

    JButton receiveApplies = new JButton("收到申请");

    JButton returnMain = new JButton("退出登录");

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
        postLand.setLocation(80, 110);
        postLand.setSize(Types.BUTTON_SIZE);
        postLand.addActionListener(this);

        selectLand.setLocation(230, 110);
        selectLand.setSize(Types.BUTTON_SIZE);
        selectLand.addActionListener(this);

        myPost.setLocation(80, 180);
        myPost.setSize(Types.BUTTON_SIZE);
        myPost.addActionListener(this);

        myApply.setLocation(230, 180);
        myApply.setSize(Types.BUTTON_SIZE);
        myApply.addActionListener(this);

        receiveApplies.setLocation(80, 240);
        receiveApplies.setSize(Types.BUTTON_SIZE);
        receiveApplies.addActionListener(this);

        returnMain.setLocation(230, 240);
        returnMain.setSize(Types.BUTTON_SIZE);
        returnMain.addActionListener(this);

        label.setText("欢迎使用");
        label.setFont(Types.TEXT_STYLE);
        label.setLocation(150, 20);
        label.setSize(Types.MAIN_LABEL_SIZE);

        panel.add(postLand);
        panel.add(selectLand);
        panel.add(label);
        panel.add(myApply);
        panel.add(myPost);
        panel.add(receiveApplies);
        panel.add(returnMain);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == postLand) {
            frame.dispose();
            new PostLand(400, 350, uno);
        } else if (e.getSource() == selectLand) {
            frame.dispose();
            new SelectLand(600, 337, uno);
        } else if (e.getSource() == myPost) {
            frame.dispose();
            new MyPost(600, 337, uno);
        } else if (e.getSource() == myApply) {
            frame.dispose();
            new MyApply(600, 337, uno);
        } else if (e.getSource() == receiveApplies) {

        } else if (e.getSource() == returnMain) {
            frame.dispose();
            new MainFrame(400, 200);
        }
    }
}
