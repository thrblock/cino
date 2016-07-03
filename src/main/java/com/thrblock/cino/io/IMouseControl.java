package com.thrblock.cino.io;

/**
 * 鼠标抽象层 <br />
 * 这个层的抽象意味着面向桌面模型了 并不该出现这样不纯粹的东西...
 * @author lizepu
 */
public interface IMouseControl {
    /**
     * @return 获得当前鼠标x坐标
     */
    public int getMouseX();
    /**
     * @return 获得当前鼠标y坐标
     */
    public int getMouseY();
}
