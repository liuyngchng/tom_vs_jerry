package cn.metro.goldwyn.mayer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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

    private static String FRAME_TITLE = "The Mouse and Cat Drama (猫鼠大战) ";

    public static boolean isGameOver = false;

    public static Boolean isTomNeedMove = false;


    public static Boolean isJerryNeedMove = false;


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

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Drama();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Tom and Jerry are coming(地球上的2个大聪明来咯)!");
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
     * @param label1
     * @param label2
     * @return
     */
    public static boolean checkIfMeetCounterpart(JLabel label1, JLabel label2) {
        Point point1 = label1.getLocation();
        Point point2 = label2.getLocation();
        if (Math.abs(point1.getX() - point2.getX()) < Drama.MEET_EACH_OTHER
                && Math.abs(point1.getY() - point2.getY()) < Drama.MEET_EACH_OTHER) {
            return true;
        }
        return false;
    }

    public static void resetDrama(JLabel jerry, JLabel tom) {
        Drama.isGameOver = false;
        jerry.setLocation(25, 220);
        tom.setLocation(785, 220);
    }


    public static ExecutorService getThreadPool(String threadNamePrefix) {
        final ThreadFactory threadFactory =
                new ThreadFactoryBuilder().setNameFormat( threadNamePrefix +"%d").build();
        return Executors.newFixedThreadPool(2, threadFactory);
    }
}
