package com.thrblock.cino.glanimate.fragment;

import lombok.Data;

/**
 * 对原始逻辑增加一个独立的开关
 * 
 * @author lizepu
 *
 */
@Data
public class SwitchFragment implements IPureFragment {
    private boolean enable;

    private final IPureFragment src;

    @Override
    public void fragment() {
        if (enable) {
            src.fragment();
        }
    }

    public void enable() {
        this.enable = true;
    }

    public void disable() {
        this.enable = false;
    }

    @Override
    public void reset() {
        enable = false;
        src.reset();
    }

}
