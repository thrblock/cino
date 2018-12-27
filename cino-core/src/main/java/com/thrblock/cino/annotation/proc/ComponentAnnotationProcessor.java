package com.thrblock.cino.annotation.proc;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;

@Component
public class ComponentAnnotationProcessor {
    public void asSub(CinoComponent master, CinoComponent sub) {
        master.registerSub(sub);
    }
}
