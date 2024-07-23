package cn.metro.goldwyn.mayer;

import javax.swing.*;

public class MoveElement implements Comparable {

    private Character character;

    private JLabel label;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        return this.getLabel().hashCode();
    }

    @Override
    public int compareTo(Object o) {

        if(this.hashCode() == o.hashCode()) {
            return 0;
        } else {
            return -1;
        }
    }
}
