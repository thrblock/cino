package com.thrblock.cino.glanimate.fragment;

public abstract class AbstractFragment implements IPureFragment {

    protected IPureFragment ref;

    @Override
    public void referance(IPureFragment ref) {
        this.ref = ref;
    }

}
