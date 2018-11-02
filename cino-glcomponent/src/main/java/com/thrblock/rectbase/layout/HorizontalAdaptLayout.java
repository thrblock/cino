package com.thrblock.rectbase.layout;

import java.util.ArrayList;

import com.thrblock.rectbase.GLRectBase;

public class HorizontalAdaptLayout extends GLRectBasedLayout {

    ArrayList<GLRectBase> rects = new ArrayList<>();

    @Override
    public void manage(GLRectBase rect, Object data) {
        rects.add(rect);
    }

    @Override
    public void noticeCalc() {
        float totalW = 0;
        for (int i = 0; i < rects.size(); i++) {
            totalW += rects.get(i).getWidth();
        }
        float sp = (base.getWidth() - totalW) / (rects.size() + 1);
        GLRectBase prev = base;
        for (int i = 0; i < rects.size(); i++) {
            GLRectBase rb = rects.get(i);
            if(i == 0) {
                rb.getBase().leftOfInner(prev.getBase(), sp);
            } else {
                rb.getBase().rightOf(prev.getBase(),sp);
            }
            prev = rb;
        }
    }

}
