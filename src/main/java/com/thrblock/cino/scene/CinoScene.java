package com.thrblock.cino.scene;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import com.thrblock.cino.glfragment.IGLFragment;
import com.thrblock.cino.glfragment.IPureFragment;
import com.thrblock.cino.glshape.builder.GLNode;

@Deprecated
public class CinoScene extends AbstractCinoScene {
    private Consumer<GLNode> onEnable = node -> node.show();
    private Consumer<GLNode> onDestroy = node -> node.destroy();
    private Consumer<GLNode> onCovered = node -> node.hide();
    private Consumer<GLNode> onRecovered = node -> node.show();
    
    /**
     * keyEvent io 阻塞点
     */
    private List<BooleanSupplier> ioBlocker = new LinkedList<>();
    
    public final void rootEnable(Consumer<GLNode> enable) {
        this.onEnable = enable;
    }
    public final void rootDestroy(Consumer<GLNode> destroy) {
        this.onDestroy = destroy;
    }
    public final void rootCovered(Consumer<GLNode> covered) {
        this.onCovered = covered;
    }
    public final void rootRecovered(Consumer<GLNode> recovered) {
        this.onRecovered = recovered;
    }
    
    @Override
    public final void sceneEnable() {
        sceneFrag.remuse();
        onEnable.accept(sceneRoot);
        enable();
    }
    
    @Override
    public final void sceneDestroy() {
        sceneFrag.destroy();
        onDestroy.accept(sceneRoot);
        destroy();
    }
    
    @Override
    public final void sceneRecover() {
        sceneFrag.remuse();
        onRecovered.accept(sceneRoot);
        recover();
    }
    
    @Override
    public final void sceneCovered() {
        sceneFrag.pause();
        onCovered.accept(sceneRoot);
        covered();
    }
    
    /**
     * 构造一个片段逻辑 起开关伴随场景自动完成
     * @param frag
     */
    public void auto(IPureFragment frag) {
        sceneFrag.addFragment(new IGLFragment(){
            @Override
            public void fragment() {
                frag.fragment();
            }

            @Override
            public boolean isEnable() {
                return true;
            }

            @Override
            public boolean isDestory() {
                return false;
            }
        });
    }
    
    /**
     * 增加一个阻塞条件
     * @param sup
     */
    protected void addKeyEventBlocker(BooleanSupplier sup) {
        ioBlocker.add(sup);
    }
    
    /**
     * 当前是否阻塞
     * @return
     */
    protected boolean isCurrentBlockKeyEvent() {
        for(BooleanSupplier sub:ioBlocker) {
            if(sub.getAsBoolean()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public final void keyTyped(KeyEvent e){
        if(!isCurrentBlockKeyEvent()) {
            onKeyTyped(e);
        }
    }

    @Override
    public final void keyPressed(KeyEvent e){
        if(!isCurrentBlockKeyEvent()) {
            onKeyPressed(e);
        }
    }

    @Override
    public final void keyReleased(KeyEvent e){
        if(!isCurrentBlockKeyEvent()) {
            onKeyReleased(e);
        }
    }
    
    /**
     * 按键事件
     * @param e
     */
    public void onKeyTyped(KeyEvent e){
    }

    /**
     * 按下事件
     * @param e
     */
    public void onKeyPressed(KeyEvent e){
    }

    /**
     * 抬起事件
     * @param e
     */
    public void onKeyReleased(KeyEvent e){
    }
}
