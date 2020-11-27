package com.thrblock.cino.glanimate.fragment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AndThenFragment extends AbstractFragment {
    private final IPureFragment src;
    private final IPureFragment then;
    private IPureFragment ref;

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
