package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyApply extends BasicFrame implements ActionListener {
    int uno;
    public MyApply() {
        super();
        this.SetComponents();
    }

    public MyApply(int x, int y, int _uno) {
        super(x, y);
        uno = _uno;
        this.SetComponents();
    }

    @Override
    void SetComponents() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
