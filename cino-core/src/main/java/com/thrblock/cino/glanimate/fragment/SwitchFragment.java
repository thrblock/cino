package com.thrblock.cino.glanimate.fragment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对原始逻辑增加一个独立的开关
 * 
 * @author lizepu
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SwitchFragment extends AbstractFragment {
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
