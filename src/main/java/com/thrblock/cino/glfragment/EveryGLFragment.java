package com.thrblock.cino.glfragment;

public class EveryGLFragment extends ForeverFragment {
    private int count;
    private int countReg = 0;
    public EveryGLFragment(int count,IPureFragment pure) {
        super(pure);
        this.count = count;
    }
    @Override
    public void fragment() {
        countReg ++;
        if(countReg >= count) {
            pure.fragment();
            countReg = 0;
        }
    }

}
