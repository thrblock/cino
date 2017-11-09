package com.thrblock.cino.io;

/**
 * KeyListener for both NEWT and AWT
 * @author thrblock
 *
 */
public interface KeyListener {
    /**
     * keyPressed for newt or awt
     */
    public void keyPressed(KeyEvent e);

    /**
     * keyReleased for newt or awt
     */
    public void keyReleased(KeyEvent e);
}
