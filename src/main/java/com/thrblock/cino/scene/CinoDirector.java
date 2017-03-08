package com.thrblock.cino.scene;

import java.util.Deque;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.io.IKeyControlStack;

@Component
public class CinoDirector implements ICinoDirector {
    @Autowired
    private IKeyControlStack keyStack;
    
    private enum CinoDirectorStatus{WAIT,RUNNING,PAUSED,END}
    private CinoDirectorStatus status = CinoDirectorStatus.WAIT;
    private Deque<ICinoScene> sceneStack = new LinkedList<>();
    
    @Override
    public void initRootScene(ICinoScene scene) {
        if(sceneStack.isEmpty() && status == CinoDirectorStatus.WAIT) {
            pushScene(scene);
            keyStack.pushKeyListener(scene);
            status = CinoDirectorStatus.RUNNING;
        }
    }

    @Override
    public void pushScene(ICinoScene scene) {
        ICinoScene pre = sceneStack.peek();
        if(pre != null) {
            pre.covered();
        }
        sceneStack.push(scene);
        keyStack.pushKeyListener(scene);
        scene.enable();
    }

    @Override
    public void replaceScene(ICinoScene scene) {
        popScene();
        pushScene(scene);
    }

    @Override
    public void popScene() {
        ICinoScene scene = sceneStack.pop();
        keyStack.popKeyListener();
        scene.destory();
        sceneStack.peek().recover();
    }

    @Override
    public void popAllScene() {
        while(!sceneStack.isEmpty()){
            popScene();
        }
    }
    
    @Override
    public void end() {
        if(status != CinoDirectorStatus.END) {
            status = CinoDirectorStatus.END;
            popAllScene();
        }
    }

    @Override
    public void pause() {
        if(status == CinoDirectorStatus.RUNNING) {
            status = CinoDirectorStatus.PAUSED;
        }
    }

    @Override
    public void resume() {
        if(status == CinoDirectorStatus.PAUSED) {
            status = CinoDirectorStatus.RUNNING;
        }
    }
}
