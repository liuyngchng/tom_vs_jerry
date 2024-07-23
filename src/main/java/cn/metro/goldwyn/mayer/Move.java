package cn.metro.goldwyn.mayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.KeyPair;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 控制图形按照键盘的输入控制进行移动
 */
public class Move  implements Runnable {

    // Set of currently pressed keys
    private static final ConcurrentLinkedQueue<MoveElement> pressedKeys = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Point p = new Point(0, 0);
            if (Move.pressedKeys.isEmpty()) {
//                System.out.println("nothing done for pressed keys");
                continue;
            }

            Iterator<MoveElement> it = Move.pressedKeys.iterator();
            while (it.hasNext()) {
                MoveElement moveElement = it.next();
                switch (moveElement.getCharacter()) {
                    case 'a':
                    case 'j':
                        p = moveElement.getLabel().getLocation();
                        p.setLocation(p.getX() - Drama.MOVE_PIXEL, p.getY());
                        break;
                    case 'd':
                    case 'l':
                        p = moveElement.getLabel().getLocation();
                        p.setLocation(p.getX() + Drama.MOVE_PIXEL, p.getY());
                        break;
                    case 's':
                    case 'k':
                        p = moveElement.getLabel().getLocation();
                        p.setLocation(p.getX(), p.getY() + Drama.MOVE_PIXEL);
                        break;
                    case 'w':
                    case 'i':
                        p = moveElement.getLabel().getLocation();
                        p.setLocation(p.getX(), p.getY() - Drama.MOVE_PIXEL);
                        break;
                }
                if (Drama.ifCollideToWall(p)) {
                    System.out.print(
                        String.format(
                            "\r[%s] 大聪明 %s collide to Wall(脑袋撞墙了), (%s, %s) !!!",
                            Thread.currentThread().getId(), moveElement.getLabel().getText(), p.getX(), p.getY()
                        )
                    );
                } else {
                    moveElement.getLabel().setLocation(p);
                    System.out.print(
                        String.format(
                            "\r[%s]%s moved to(移动到) (%s, %s)",
                            Thread.currentThread().getId(), moveElement.getLabel().getText(), p.getX(), p.getY()
                        )
                    );
                }
                it.remove();
            }
        }
    }


    public static void action(KeyEvent keyEvent, JLabel jLabel) {
        final MoveElement moveElement = new MoveElement();
        moveElement.setCharacter(keyEvent.getKeyChar());
        moveElement.setLabel(jLabel);
        Move.pressedKeys.add(moveElement);

    }

    public static void releaseKey(KeyEvent keyEvent) {
//        Move.pressedKeys.remove(keyEvent.getKeyChar());
    }
}
