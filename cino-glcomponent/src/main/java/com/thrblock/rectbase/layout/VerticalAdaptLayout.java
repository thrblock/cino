package com.thrblock.rectbase.layout;

import java.util.ArrayList;

import com.thrblock.rectbase.GLRectBase;

public class VerticalAdaptLayout extends GLRectBasedLayout {

    ArrayList<GLRectBase> rects = new ArrayList<>();

    @Override
    public void manage(GLRectBase rect, Object data) {
        rects.add(rect);
    }

    @Override
    public void noticeCalc() {
        float totalH = 0;
        for (int i = 0; i < rects.size(); i++) {
            totalH += rects.get(i).getHeight();
        }
        float sp = (base.getHeight() - totalH) / (rects.size() + 1);
        GLRectBase prev = base;
        for (int i = 0; i < rects.size(); i++) {
            GLRectBase rb = rects.get(i);
            if(i == 0) {
                rb.getBase().topOfInner(prev.getBase(), sp);
            } else {
                rb.getBase().bottomOf(prev.getBase(),sp);
            }
            prev = rb;
        }
    }

}
