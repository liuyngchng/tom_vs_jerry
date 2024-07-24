/*
 * Created by JFormDesigner on Mon Jul 22 11:11:49 CST 2024
 */

package cn.metro.goldwyn.mayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Brainrain
 */
public class MyPanel extends JPanel {
    public MyPanel() {
        initComponents();
    }

    private void startGame() {
        this.requestFocus();
        int timer = 0;
        try {
            timer = Integer.parseInt(this.textField2.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "请输入数字计时内容", "输入内容错误",
                JOptionPane.ERROR_MESSAGE
            );
            throw new RuntimeException("计时器输入错误");

        }
         Drama.startDrama(this.label6, this.label5, this.textField2, this.button1, this.textField1);

    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
       this.startGame();
    }

    private void thisKeyPressed(KeyEvent e) {
        // TODO add your code here
//        System.out.println(String.format("key %c pressed ", e.getKeyCode()));
        char key = e.getKeyChar();

        if (Drama.isGameOver) {
            switch (key) {
                case 0x20:
//                case 'r':
                    this.startGame();
                    break;
            }
            System.out.println("Game over, nothing can be done!");
            return;
        }
        ActionJob actionJob;

        switch (key) {
            case 'a':
            case 's':
            case 'd':
            case 'w':

            case 'A':
            case 'S':
            case 'D':
            case 'W':
                Drama.isJerryNeedMove = true;
                actionJob = new ActionJob(e, this.label6);
                Drama.getThreadPool("jerry_job_").submit(actionJob);
                break;
            case 'j':
            case 'k':
            case 'l':
            case 'i':

            case 'J':
            case 'K':
            case 'L':
            case 'I':
                Drama.isTomNeedMove = true;
                actionJob = new ActionJob(e, this.label5);
                Drama.getThreadPool("tom_job_").submit(actionJob);
                break;
        }
        if(Drama.checkIfMeetCounterpart(this.label5, this.label6, this.button1, this.textField3, this.textField2)) {
            Drama.isGameOver = true;
            System.out.println("Tom 抓住了 Jerry, 游戏结束");
        }
    }

    private void thisKeyReleased(KeyEvent e) {
        // TODO add your code here
        ActionJob.stopMove(e);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        button1 = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        label6 = new JLabel();
        textField4 = new JTextField();

        //======== this ========
        setPreferredSize(new Dimension(1400, 600));
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                thisKeyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                thisKeyReleased(e);
            }
        });
        setLayout(null);

        //---- label1 ----
        label1.setText("Jerry \u5206\u6570\uff1a");
        add(label1);
        label1.setBounds(20, 20, 100, 30);

        //---- textField1 ----
        textField1.setText("0");
        textField1.setEnabled(false);
        textField1.setEditable(false);
        add(textField1);
        textField1.setBounds(125, 20, 55, 30);

        //---- label2 ----
        label2.setText("\u8ba1\u65f6\u5668\uff1a");
        add(label2);
        label2.setBounds(415, 20, 100, 30);

        //---- textField2 ----
        textField2.setText("15");
        add(textField2);
        textField2.setBounds(495, 20, 50, 30);

        //---- label3 ----
        label3.setText("Tom \u5206\u6570\uff1a");
        add(label3);
        label3.setBounds(760, 20, 100, 30);

        //---- textField3 ----
        textField3.setText("0");
        textField3.setEditable(false);
        textField3.setEnabled(false);
        add(textField3);
        textField3.setBounds(860, 20, 55, 30);

        //---- button1 ----
        button1.setText("\u5f00\u59cb\u6e38\u620f");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        add(button1);
        button1.setBounds(435, 60, 130, 40);

        //---- label4 ----
        label4.setText(" Powerd by Metro Goldwyn Mayer Co. Ltd. CN");
        add(label4);
        label4.setBounds(285, 505, 400, 30);

        //---- label5 ----
        label5.setText("tom");
        label5.setIcon(new ImageIcon(getClass().getResource("/tom.png")));
        label5.setVisible(false);
        add(label5);
        label5.setBounds(785, 220, 100, 100);

        //---- label6 ----
        label6.setText("jerry");
        label6.setIcon(new ImageIcon(getClass().getResource("/jerry.png")));
        label6.setVisible(false);
        add(label6);
        label6.setBounds(25, 220, 70, 100);

        //---- textField4 ----
        textField4.setText("\u79d2");
        textField4.setEditable(false);
        textField4.setEnabled(false);
        add(textField4);
        textField4.setBounds(545, 20, 50, 30);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JButton button1;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JTextField textField4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
