package cn.metro.goldwyn.mayer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * A drama with 2 main characters, a mouse and a cat,
 * they are good friends, and just play games everyday.
 * Sometimes the cat had bad luck but he is not sad and the mouse
 * is smart but not bad. They are the best partner on the earth.
 */
public class Drama {


    // 大戏台
    private JFrame frame;

    private static int FRAME_WIDTH = 1000;

    private static int FRAME_HEIGHT = 600;

    // 每次移动的帧数
    public static int MOVE_PIXEL = 10;

    // 互相撞上的条件
    private static int MEET_EACH_OTHER = 50;

    // 规定的时间内 tom 没有成功抓捕，则 jerry得分
    public static int JERRY_ESCAPE_TIME = 15;

    private static String FRAME_TITLE = "The Mouse and Cat Drama (猫鼠大战) ";

    public static boolean isGameOver = false;

    public static Boolean isTomNeedMove = false;


    public static Boolean isJerryNeedMove = false;

    public static long starTime = System.currentTimeMillis();


    public Drama () {
        this.frame = new JFrame(Drama.FRAME_TITLE);
        final MyPanel myPanel = new MyPanel();
        this.frame.add(myPanel);
        this.frame.setSize(Drama.FRAME_WIDTH, Drama.FRAME_HEIGHT);
        System.out.println("frame width=" + Drama.FRAME_WIDTH + ", height=" + Drama.FRAME_HEIGHT);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.frame.pack();
        this.frame.setVisible(true);
    }


    /**
     * 检测是否撞墙
     */
    public static boolean ifCollideToWall(Point p) {
        if (p.getY() > Drama.FRAME_HEIGHT -120 || p.getY() < 120) {
            return true;
        } else if (p.getX() > Drama.FRAME_WIDTH - 100 || p.getX() < 5) {
            return true;
        }
        return false;
    }

    /**
     * 检测Tom 和 Jerry是否相遇了
     * @param actorLabel1
     * @param actorLabel2
     * @return
     */
    public static boolean checkIfMeetCounterpart(JLabel actorLabel1, JLabel actorLabel2, JButton startButton,
                                                 JTextField tomTextField, JTextField timerTextField) {
        Point point1 = actorLabel1.getLocation();
        Point point2 = actorLabel2.getLocation();
        if (Math.abs(point1.getX() - point2.getX()) < Drama.MEET_EACH_OTHER
                && Math.abs(point1.getY() - point2.getY()) < Drama.MEET_EACH_OTHER) {
            Drama.resetGame(startButton, timerTextField);
            Integer tomScore = Integer.parseInt(tomTextField.getText());
            tomScore++;
            tomTextField.setText(tomScore.toString());
            System.out.println("Tom 和 Jerry 会面了， tom 加分");
            return true;
        }
        return false;
    }

    public static void startDrama(JLabel jerry, JLabel tom, JTextField timerField,
        JButton startButton, JTextField jerryScoreTextField) {

        Drama.JERRY_ESCAPE_TIME = Integer.parseInt(timerField.getText());
        System.out.println("开始游戏");
        Drama.isGameOver = false;
        Drama.starTime = System.currentTimeMillis();
        jerry.setLocation(25, 220);
        tom.setLocation(785, 220);
        jerry.setVisible(true);         // Jerry 登场
        tom.setVisible(true);           // Tom 登场
        timerField.setEnabled(false);
        timerField.setEditable(false);

        startButton.setEnabled(false);     // 按键变灰，不可点击
        startButton.setText("游戏运行中");   // 设置游戏状态为进行中
        Drama.getThreadPool("timer_job_").submit(
                new TimerJob(jerryScoreTextField, timerField, startButton)
        );
    }


    public static ExecutorService getThreadPool(String threadNamePrefix) {
        final ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat( threadNamePrefix +"%d").build();
        return Executors.newFixedThreadPool(2, threadFactory);
    }

    public static void resetGame(JButton startButton, JTextField timerTextField) {
        timerTextField.setEditable(true);
        timerTextField.setEnabled(true);
        startButton.setText("重新开始");
        startButton.setEnabled(true);
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        new Drama();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Drama();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("地球上的2个大聪明 Tom 和 Jerry 来咯!");
    }
}
