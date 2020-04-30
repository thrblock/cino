package com.thrblock.rectbase;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.function.VoidConsumer;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.util.structure.BoolBoxer;

/**
 * 2018年3月29日：这个设计过于复杂，不宜与理解与使用；另外事件处理上存在冗余
 * @author thrblock
 *
 */
public abstract class GLRectBase extends CinoComponent {

    protected GLRect base;
    
    protected boolean disable = false;


    protected List<VoidConsumer> movein = new LinkedList<>();
    protected List<VoidConsumer> moveout = new LinkedList<>();
    
    protected List<Consumer<MouseEvent>> pressed = new LinkedList<>();
    protected List<Consumer<MouseEvent>> released = new LinkedList<>();
    protected List<Consumer<MouseEvent>> clicked = new LinkedList<>();
    
    @Override
    public final void init() {
        autoShowHide();
        base = buildBase();
        afterBaseBuild();
    }

    protected void afterBaseBuild() {
        buildMoveInOut();
        buildMouseIO();
    }
    
    protected void buildMouseIO() {
        BoolBoxer hadPress = new BoolBoxer();
        autoShapePressed(base, e -> {
            if(disable) {
                return;
            }
            if(e.getButton() == MouseEvent.BUTTON1) {
                pressed.forEach(p -> p.accept(e));
                hadPress.setValue(true);
            }
        });
        addMouseMoveout(() -> hadPress.setValue(false));
        autoShapeReleased(base, e -> {
            if(disable) {
                return;
            }
            if(e.getButton() == MouseEvent.BUTTON1) {
                released.forEach(p -> p.accept(e));
                if(hadPress.getValue()) {
                    hadPress.setValue(false);
                    clicked.forEach(p -> p.accept(e));
                }
            }
        });
    }
    
    protected abstract GLRect buildBase();

    public boolean isVisible() {
        return base.isVisible();
    }

    public float getWidth() {
        return base.getWidth();
    }

    public float getHeight() {
        return base.getHeight();
    }

    public void setWidth(float w) {
        base.setWidth(w);
    }

    public void setHeight(float h) {
        base.setHeight(h);
    }

    public float getX() {
        return rootNode.getCentralX();
    }

    public float getY() {
        return rootNode.getCentralY();
    }

    public void setX(float x) {
        rootNode.setX(x);
    }

    public void setY(float y) {
        rootNode.setY(y);
    }

    protected void synRectStatus(GLRect aim, GLRect r) {
        r.setWidth(aim.getWidth());
        r.setHeight(aim.getHeight());
        r.setCentralX(aim.getCentralX());
        r.setCentralY(aim.getCentralY());
    }

    protected void synBaseStatus(GLRect r) {
        synRectStatus(base, r);
    }
    
    public void addMouseMovein(VoidConsumer m) {
        movein.add(m);
    }

    public void addMouseMoveout(VoidConsumer m) {
        moveout.add(m);
    }
    
    protected void buildMoveInOut() {
        BoolBoxer lastMouseInside = new BoolBoxer();
        auto(() -> {
            if (base.isMouseInside() && !lastMouseInside.getValue()) {
                lastMouseInside.setValue(true);
                movein.forEach(VoidConsumer::accept);
            } else if (!base.isMouseInside() && lastMouseInside.getValue()) {
                lastMouseInside.setValue(false);
                moveout.forEach(VoidConsumer::accept);
            }
        });
    }
    
    public void addMousePressed(Consumer<MouseEvent> press) {
        pressed.add(press);
    }

    public void addMouseReleased(Consumer<MouseEvent> release) {
        released.add(release);
    }

    public void addMouseClicked(Consumer<MouseEvent> click) {
        clicked.add(click);
    }

    public GLRect getBase() {
        return base;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
}
