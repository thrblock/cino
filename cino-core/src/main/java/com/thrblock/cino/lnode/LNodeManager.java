package com.thrblock.cino.lnode;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.thrblock.cino.fbo.GLFrameBufferObjectManager;
import com.thrblock.cino.gllifecycle.CycleArray;
import com.thrblock.cino.gltransform.GLTransform;
import com.thrblock.cino.gltransform.GLTransformManager;

@Component
public class LNodeManager {

    private CycleArray<LNode> roots = new CycleArray<>(LNode[]::new);

    @Autowired
    private GLFrameBufferObjectManager fboManager;
    
    @Autowired
    private GLTransformManager transformManager;

    @Autowired
    private ShapeBeanFactory shapeFactory;

    private GLTransform defTransform;

    @PostConstruct
    void init() {
        this.defTransform = transformManager.generateGLTransform();
    }

    public LNode createRootNode() {
        LNode node = new LNode(fboManager, shapeFactory, transformManager);
        node.setTransform(defTransform);
        roots.safeAdd(node);
        Arrays.sort(roots.safeHold());
        return node;
    }

    public LNode createSubNode(LNode parent) {
        return new LNode(parent);
    }

    public void destroyRootNode(LNode n) {
        roots.safeRemove(n);
        Arrays.sort(roots.safeHold());
    }

    public void drawAllNode(GL2 gl2) {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        LNode[] array = roots.safeHold();
        for (int i = 0; i < array.length; i++) {
            LNode crt = array[i];
            crt.drawShape(gl2);
        }
        gl2.glFlush();
    }

}
