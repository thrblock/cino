package com.thrblock.cino.glanimate.fragment;

public class DelayFragment implements IPureFragment {
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
            pure.fragment();
            countReg = 0;
        }
    }

    @Override
    public void reset() {
        this.countReg = 0;
        pure.reset();
    }
}