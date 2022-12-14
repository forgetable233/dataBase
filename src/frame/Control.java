package frame;

import java.util.*;
import javax.swing.*;

public class Control {
    int default_x;
    int default_y;
    public Control() {
        default_x = 400;
        default_y = 200;
        MainFrame mainFrame = new MainFrame(default_x, default_y);
    }
}
