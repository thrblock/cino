package com.thrblock.cino.glanimate;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

/**
 * GLAnimate 抽象动画<br />
 * 抽象动画实现，包括了enable及destory的默认实现<br />
 * 初始化时将自动加入绘制后的执行队列中
 * 
 * @author lizepu
 */
public class GLAnimate {
    private boolean enable = false;
    private boolean destory = false;

    private ArrayList<IPureFragment> frags = new ArrayList<>();
    private int index = -1;
    private IPureFragment finish;

    GLAnimate() {
    }

    public GLAnimate add(BooleanSupplier boolfrag) {
        return this.add(() -> {
            if (boolfrag.getAsBoolean()) {
                next();
            }
        });
    }

    public GLAnimate add(IPureFragment frag) {
        frags.add(frag);
        if (index == -1) {
            index = 0;
        }
        return this;
    }

    public GLAnimate addOnce(IPureFragment frag) {
        BooleanSupplier boolfrag = () -> {
            frag.fragment();
            return true;
        };
        add(boolfrag);
        return this;
    }

    public GLAnimate addDelay(int delay) {
        BooleanSupplier delaySup = new BooleanSupplier() {
            int crt = 0;
            int count = delay;

            @Override
            public boolean getAsBoolean() {
                if (crt < count) {
                    crt++;
                    return false;
                } else {
                    crt = 0;
                    return true;
                }
            }
        };
        add(delaySup);
        return this;
    }

    public boolean isDestory() {
        return destory;
    }

    public boolean isEnable() {
        return enable;
    }

    /**
     * 开启此片段逻辑
     */
    public void enable() {
        index = 0;
        this.enable = true;
    }

    /**
     * 关闭此片段逻辑
     */
    public void disable() {
        this.enable = false;
    }

    /**
     * 销毁此片段逻辑
     */
    public void destory() {
        this.destory = true;
    }

    public void animate() {
        if (!checkIndex()) {
            disable();
            if (finish != null) {
                finish.fragment();
            }
        } else {
            frags.get(index).fragment();
        }
    }

    public GLAnimate whenFinish(IPureFragment finish) {
        this.finish = finish;
        return this;
    }

    /**
     * 切换到下一逻辑，若不存在会自动停止
     */
    public void next() {
        this.index++;
    }

    /**
     * 切换下一逻辑，若不存在则切换至第一逻辑
     */
    public void loop() {
        this.index = (index + 1) % frags.size();
    }

    private boolean checkIndex() {
        return index >= 0 && index < frags.size();
    }
}
