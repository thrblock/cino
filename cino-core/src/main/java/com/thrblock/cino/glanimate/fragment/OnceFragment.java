package com.thrblock.cino.glanimate.fragment;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 原始逻辑仅执行一次
 * 
 * @author lizepu
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OnceFragment extends AbstractFragment {
    private boolean runflag = true;

    private final IPureFragment src;

    @Override
    public void fragment() {
        if (runflag) {
            runflag = false;
            src.fragment();
        }
    }

    @Override
    public void reset() {
        runflag = true;
        src.reset();
    }

}
