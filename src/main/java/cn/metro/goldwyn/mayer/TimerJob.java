package cn.metro.goldwyn.mayer;

import javax.swing.*;
import java.util.Date;

public class TimerJob implements Runnable {

    private JTextField textField;

    private JButton button;

    public TimerJob(JTextField textField, JButton button) {
        this.textField = textField;
        this.button = button;
    }
    @Override
    public void run() {
        while (true) {
            int timediff = (int)(System.currentTimeMillis() - Drama.starTime) /1000;
            if(timediff > 30 || Drama.isGameOver) {
                System.out.println("timer = 30s, game over");
                Drama.isGameOver = true;
                this.button.setText("重新开始");
                this.button.setEnabled(true);
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final String txt = String.format("%s秒",30- timediff);
            this.textField.setEnabled(true);
            this.textField.setText(txt);
            this.textField.setEnabled(false);
            System.out.println("set timer as " + txt);
        }
    }
}
