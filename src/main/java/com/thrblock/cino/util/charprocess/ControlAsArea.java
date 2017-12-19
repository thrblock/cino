package com.thrblock.cino.util.charprocess;

import com.thrblock.cino.glshape.GLImage;

/**
 * ControlAsArea
 * <p>
 * 左对齐
 * <p>
 * 自动换行
 * <p>
 * 识别换行符
 * <p>
 * 提供行级相位调整
 * <p>
 * 超出部分不显示
 * 
 * @author zepu.li
 */
public class ControlAsArea extends PositionSynchronizer {

    @Override
    public void synPosition() {
        int startIndex = 0;
        int endIndex = nextLineOrEnd(startIndex);
        int currentLineNumber = 1;
        while (check(currentLineNumber) && endIndex != -1) {
            positionFirst(startIndex,currentLineNumber);
            for (int i = startIndex + 1; i <= endIndex; i++) {
                imgs[i].rightOf(imgs[i - 1]);
            }
            startIndex = endIndex + 1;
            endIndex = nextLineOrEnd(startIndex);
            currentLineNumber++;
        }
        for(int i = startIndex;i < imgs.length;i++) {
            imgs[i].setTexture(GLImage.EMPTY_TEXTURE);
        }
    }

    private void positionFirst(int startIndex, int currentLineNumber) {
        imgs[startIndex].sameStatusOf(rect);
        float imgH = imgs[startIndex].getHeight();
        float imgW = imgs[startIndex].getWidth();
        float h = (rect.getHeight() - imgH) / 2 - (currentLineNumber - 1) * imgH;
        float l = (rect.getWidth() - imgW) / 2;
        if(l == 0) {
            imgs[startIndex].topOfInner(rect,(currentLineNumber - 1) * imgH);
        } else {
            float rad = (float) Math.atan(h / l);
            float r = (float) Math.sqrt(Math.pow(h, 2) + Math.pow(l, 2));
            float xoffset = (float) (- r * Math.cos(rect.getRadian() - rad));
            float yoffset = (float) (- r * Math.sin(rect.getRadian() - rad));
            imgs[startIndex].setXOffset(xoffset);
            imgs[startIndex].setYOffset(yoffset);
        }
    }

    private boolean check(int currentLineNumber) {
        return imgs[0].getHeight() * currentLineNumber <= rect.getHeight();
    }

    private int nextLineOrEnd(int start) {
        if (start >= src.length) {
            return -1;
        }
        float w = 0;
        for (int i = start; i < src.length; i++) {
            w += imgs[i].getWidth();
            if(w > rect.getWidth()) {
                return i - 1;
            }
            if (src[i] == '\n' || src[i] == '\0') {
                return i;
            }
        }
        return src.length - 1;
    }

}
