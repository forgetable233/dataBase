package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceiveApply extends BasicFrame implements ActionListener {
    int uno;
    public ReceiveApply() {
        super();
        this.SetComponents();
    }

    public ReceiveApply(int x, int y, int _uno) {
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
