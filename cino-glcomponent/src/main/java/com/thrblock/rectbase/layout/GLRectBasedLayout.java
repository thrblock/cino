package com.thrblock.rectbase.layout;

import com.thrblock.rectbase.GLRectBase;

public abstract class GLRectBasedLayout {
    protected GLRectBase base;
    public abstract void manage(GLRectBase rect,Object data);
    public abstract void noticeCalc();
    public void setBase(GLRectBase base) {
        this.base = base;
    }
}
