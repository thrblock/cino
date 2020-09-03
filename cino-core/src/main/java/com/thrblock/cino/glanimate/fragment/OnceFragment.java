package com.thrblock.cino.glanimate.fragment;

import lombok.Data;

/**
 * 原始逻辑仅执行一次
 * 
 * @author lizepu
 *
 */
@Data
public class OnceFragment implements IPureFragment {
    private boolean enable = true;

    private final IPureFragment src;

    @Override
    public void fragment() {
        if (enable) {
            src.fragment();
            enable = false;
        }
    }

    @Override
    public void reset() {
        enable = true;
        src.reset();
    }

}
