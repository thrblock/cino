package com.thrblock.rectbase.radio;

import java.util.LinkedList;
import java.util.List;

public class RadioGroup {
    private List<GLRadio> radios = new LinkedList<>();

    public void addRadioToGroup(GLRadio radio) {
        radios.forEach(r -> {
            r.addOnCheck(() -> radio.setCheck(false));
            radio.addOnCheck(() -> r.setCheck(false));
        });
        radios.add(radio);
    }
}
