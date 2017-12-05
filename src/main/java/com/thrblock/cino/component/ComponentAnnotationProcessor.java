package com.thrblock.cino.component;

import org.springframework.stereotype.Component;

@Component
public class ComponentAnnotationProcessor {
    public void asSub(CinoComponent master,CinoComponent sub) {
        master.onActivited(sub::activited);
        master.onDeactivited(sub::deactivited);
    }
}
