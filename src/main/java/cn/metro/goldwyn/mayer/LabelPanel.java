package cn.metro.goldwyn.mayer;

import javax.swing.*;
import java.awt.*;

public class LabelPanel extends JPanel {

    private JLabel label;

    public LabelPanel() {
        this.label = new JLabel("hello label");
        this.add(label);
        this.label.setBounds(new Rectangle(new Point(10, 100), this.label.getPreferredSize()));
    }





}
