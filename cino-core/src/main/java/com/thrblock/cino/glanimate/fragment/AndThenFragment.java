package com.thrblock.cino.glanimate.fragment;

import lombok.Data;

@Data
public class AndThenFragment implements IPureFragment {
    private final IPureFragment src;
    private final IPureFragment then;

    @Override
    public void fragment() {
        src.fragment();
        then.fragment();
    }

    @Override
    public void reset() {
        src.reset();
        then.reset();
    }
}
