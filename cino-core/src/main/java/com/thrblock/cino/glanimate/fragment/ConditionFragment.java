package com.thrblock.cino.glanimate.fragment;

import java.util.function.BooleanSupplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ConditionFragment extends AbstractFragment {
    private final BooleanSupplier sup;
    private final IPureFragment then;
    private final IPureFragment elseDo;

    @Override
    public void fragment() {
        if (sup.getAsBoolean()) {
            then.fragment();
        } else if (elseDo != null) {
            elseDo.fragment();
        }
    }

    @Override
    public void reset() {
        then.reset();
        if (elseDo != null) {
            elseDo.reset();
        }
    }
}
