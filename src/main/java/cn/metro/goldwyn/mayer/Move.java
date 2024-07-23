package cn.metro.goldwyn.mayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 控制图形按照键盘的输入控制进行移动
 */
public class Move  implements Runnable {
    private KeyEvent keyEvent;

    private JLabel jLabel;

    public Move(KeyEvent keyEvent, JLabel jLabel) {
        this.keyEvent = keyEvent;
        this.jLabel = jLabel;
    }
    @Override
    public void run() {
        char key =  this.keyEvent.getKeyChar();
        Point p = new Point(0, 0);
        switch (key) {
            case 'a':
            case 'j':
                p = this.jLabel.getLocation();
                p.setLocation(p.getX() - Drama.MOVE_PIXEL, p.getY());
                break;
            case 'd':
            case 'l':
                p = this.jLabel.getLocation();
                p.setLocation(p.getX() + Drama.MOVE_PIXEL, p.getY());
                break;
            case 's':
            case 'k':
                p = this.jLabel.getLocation();
                p.setLocation(p.getX(), p.getY() + Drama.MOVE_PIXEL);
                break;
            case 'w':
            case 'i':
                p = this.jLabel.getLocation();
                p.setLocation(p.getX(), p.getY() - Drama.MOVE_PIXEL);
                break;
        }
        if (Drama.ifCollideToWall(p)) {
            System.out.print(
                String.format(
                    "\r[%s] 大聪明 %s collide to Wall(脑袋撞墙了), (%s, %s) !!!",
                    Thread.currentThread().getId(), jLabel.getText(), p.getX(), p.getY()
                )
            );
        } else {
            this.jLabel.setLocation(p);
            System.out.print(
                String.format(
                    "\r[%s]%s moved to(移动到) (%s, %s)",
                    Thread.currentThread().getId(), jLabel.getText(), p.getX(), p.getY()
                )
            );
        }
    }
}
