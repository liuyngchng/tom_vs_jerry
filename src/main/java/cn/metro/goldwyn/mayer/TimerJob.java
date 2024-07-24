package cn.metro.goldwyn.mayer;

import javax.swing.*;
import java.util.Date;

public class TimerJob implements Runnable {

    private JTextField jerryScoreTextField;

    private JTextField timerTextField;

    private JButton button;

    public TimerJob(JTextField jerryScoreTextField, JTextField timerTextField, JButton button) {
        this.jerryScoreTextField = jerryScoreTextField;
        this.timerTextField = timerTextField;
        this.button = button;
    }
    @Override
    public void run() {
        while (true) {
            if(Drama.isGameOver) {
                System.out.println("timer = 30s, game over");
                Drama.resetStartButton(this.button);
                return;
            }
            int timediff = (int)(System.currentTimeMillis() - Drama.starTime) /1000;
            if(timediff > Drama.JERRY_ESCAPE_TIME) {
                System.out.println(String.format("timer = %s秒, jerry 加分, 重置计时器", Drama.JERRY_ESCAPE_TIME));
                Integer score = Integer.parseInt(this.jerryScoreTextField.getText());
                score++;
                this.jerryScoreTextField.setText(score.toString());
                this.timerTextField.setText(String.format("%s秒", Drama.JERRY_ESCAPE_TIME));
                Drama.starTime = System.currentTimeMillis();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final String txt = String.format("%s秒",Drama.JERRY_ESCAPE_TIME- timediff);
            if (!this.timerTextField.getText().equals(txt)) {
                this.timerTextField.setText(txt);
                System.out.println("set timer as " + txt);
            }
        }
    }
}
