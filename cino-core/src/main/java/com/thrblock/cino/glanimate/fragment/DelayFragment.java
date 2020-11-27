package com.thrblock.cino.glanimate.fragment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DelayFragment extends AbstractFragment {
    private int count;
    private int countReg = 0;
    private IPureFragment pure;

    public DelayFragment(int count, IPureFragment pure) {
        this.count = count;
        this.pure = pure;
    }

    @Override
    public void fragment() {
        countReg++;
        if (countReg >= count) {
            countReg = 0;
            pure.fragment();
        }
    }

    @Override
    public void reset() {
        this.countReg = 0;
        pure.reset();
    }
}