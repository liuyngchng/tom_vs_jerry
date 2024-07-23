package cn.metro.goldwyn.mayer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 控制图形按照键盘的输入控制进行角色的动作
 */
public class ActionJob implements Runnable {

    private KeyEvent keyEvent;
    private JLabel label;

    public ActionJob(KeyEvent keyEvent, JLabel label) {
        this.keyEvent = keyEvent;
        this.label = label;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Point p = new Point(0, 0);
            switch (this.keyEvent.getKeyChar()) {
                case 'a':
                case 'j':
                    p = this.label.getLocation();
                    p.setLocation(p.getX() - Drama.MOVE_PIXEL, p.getY());
                    break;
                case 'd':
                case 'l':
                    p = this.label.getLocation();
                    p.setLocation(p.getX() + Drama.MOVE_PIXEL, p.getY());
                    break;
                case 's':
                case 'k':
                    p = this.label.getLocation();
                    p.setLocation(p.getX(), p.getY() + Drama.MOVE_PIXEL);
                    break;
                case 'w':
                case 'i':
                    p = this.label.getLocation();
                    p.setLocation(p.getX(), p.getY() - Drama.MOVE_PIXEL);
                    break;
            }
            if (Drama.ifCollideToWall(p)) {
                System.out.print(
                    String.format(
                        "\r[%s] 大聪明 %s 脑袋撞墙了, (%s, %s) !!!",
                        Thread.currentThread().getId(), this.label.getText(), p.getX(), p.getY()
                    )
                );
                switch (this.label.getText()) {
                    case "tom":
                        Drama.isTomNeedMove = false;
                        break;
                    case "jerry":
                        Drama.isJerryNeedMove = false;
                        break;
                    default:
                        System.out.println("unknown label to set flag" + this.label.getText());
                        break;
                }
            } else {
                this.label.setLocation(p);
                System.out.println(
                    String.format(
                        "[%s]%s 移动到 (%s, %s)",
                        Thread.currentThread().getId(), this.label.getText(), p.getX(), p.getY()
                    )
                );
            }
            switch (this.label.getText()) {
                case "tom":
                    if (!Drama.isTomNeedMove) {
//                        System.out.println("tom ctrl key released, task finish");
                        return;
                    }
                    break;
                case "jerry":
                    if (!Drama.isJerryNeedMove) {
//                        System.out.println("jerry ctrl key released, task finish");
                        return;
                    }
                    break;
                default:
                    System.out.println("unknown label to check flag" + this.label.getText());
                    break;
            }
        }
    }



    public static void stopMove(KeyEvent e) {
        System.out.println("key_released , " + e.getKeyChar());
        char key = e.getKeyChar();
        switch (key) {
            default:
                System.out.println("do_nothing for key release " + e.getKeyCode());
                break;
            case 'a':
            case 's':
            case 'd':
            case 'w':
                Drama.isJerryNeedMove =false;
                break;
            case 'j':
            case 'k':
            case 'l':
            case 'i':
                Drama.isTomNeedMove =false;
                break;
        }
    }
}
