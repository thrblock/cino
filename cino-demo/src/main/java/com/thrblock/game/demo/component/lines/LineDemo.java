package com.thrblock.game.demo.component.lines;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLNode;
import com.thrblock.cino.lintersection.AbstractVector;
import com.thrblock.cino.lintersection.InstersectionResultHolder;
import com.thrblock.cino.lintersection.LIntersectionCounter;
import com.thrblock.cino.util.math.CRand;

@Component
public class LineDemo extends CinoComponent {
    static final int LINE_NUM = 16;

    private LIntersectionCounter<AbstractVector> lines = new LIntersectionCounter<>();
    private InstersectionResultHolder<AbstractVector> insRes = new InstersectionResultHolder<>();

    @Override
    public void init() throws Exception {
        GLNode n = shapeFactory.createNewNode();
        GLRect bg = shapeFactory.buildGLRect(0, 0, 800f, 600f);
        bg.setFill(true);
        bg.setAllPointColor(Color.DARK_GRAY);
        for (int i = 0; i < LINE_NUM; i++) {
            float x1 = CRand.getRandomNum(-360, 360);
            float y1 = CRand.getRandomNum(-260, 260);
            float x2 = CRand.getRandomNum(-360, 360);
            float y2 = CRand.getRandomNum(-260, 260);
            GLLine line = shapeFactory.buildGLLine(x1, y1, x2, y2);
            line.setAllPointColor(CRand.getRandomColdColor());
            line.setLineWidth(3.0f);
            lines.addLine(convert(line));
        }
        
        //4 vectors
        GLNode fourLine = shapeFactory.createNode();
        AbstractVector[] vecs = new  AbstractVector[4];
        vecs[0] = convert(shapeFactory.buildGLLine(0, 0, 0, 300f));
        vecs[1] = convert(shapeFactory.buildGLLine(0, 0, 0, -300f));
        vecs[2] = convert(shapeFactory.buildGLLine(0, 0, -300, 0));
        vecs[3] = convert(shapeFactory.buildGLLine(0, 0, 300, 0));
        shapeFactory.backtrack();
        
        //4 sign
        GLRect[] signs = new GLRect[4];
        for(int i = 0;i < signs.length;i++) {
            signs[i] = shapeFactory.buildGLRect(0, 0, 5, 5);
            signs[i].setFill(true);
            signs[i].setAllPointColor(Color.GRAY);
        }
        auto(() -> {
            if(keyIO.isKeyDown(KeyEvent.VK_W)) {
                fourLine.setYOffset(2f);
            } else if(keyIO.isKeyDown(KeyEvent.VK_S)) {
                fourLine.setYOffset(-2f);
            }
            if(keyIO.isKeyDown(KeyEvent.VK_A)) {
                fourLine.setXOffset(-2f);
            } else if(keyIO.isKeyDown(KeyEvent.VK_D)) {
                fourLine.setXOffset(2f);
            }
        });
        
        auto(() -> {
            for(int i = 0;i < vecs.length;i++) {
                lines.intersection(vecs[i], insRes);
                if(insRes.isIntersect()) {
                    signs[i].setAllPointColor(Color.GREEN);
                    signs[i].setCentralX(insRes.getX());
                    signs[i].setCentralY(insRes.getY());
                } else {
                    signs[i].setCentralX(-300 + i * (signs[i].getWidth() + 5));
                    signs[i].setCentralY(290);
                    signs[i].setAllPointColor(Color.GRAY);
                }
            }
        });
        
        onActivited(n::show);
        onDeactivited(n::hide);
    }

    private AbstractVector convert(GLLine line) {
        return new AbstractVector() {
            @Override
            public float getStartX() {
                return line.getPointX(0);
            }

            @Override
            public float getStartY() {
                return line.getPointY(0);
            }

            @Override
            public float getEndX() {
                return line.getPointX(1);
            }

            @Override
            public float getEndY() {
                return line.getPointY(1);
            }

        };
    }
}
