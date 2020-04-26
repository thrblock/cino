package com.thrblock.game.demo.component.lines;

import java.awt.Color;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLShapeNode;
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
        autoShowHide();
        GLRect bg = shapeFactory.buildGLRect(0, 0, screenW, screenH);
        bg.setFill(true);
        bg.setAllPointColor(Color.DARK_GRAY);
        for (int i = 0; i < LINE_NUM; i++) {
            float x1 = CRand.getRandomNum(-screenW / 2, screenW / 2);
            float y1 = CRand.getRandomNum(-screenH / 2, screenH / 2);
            float x2 = CRand.getRandomNum(-screenW / 2, screenW / 2);
            float y2 = CRand.getRandomNum(-screenH / 2, screenH / 2);
            GLLine line = shapeFactory.buildGLLine(x1, y1, x2, y2);
            line.setAllPointColor(CRand.getRandomColdColor());
            line.setLineWidth(3.0f);
            lines.addLine(convert(line));
        }
        
        GLShapeNode fourLine = shapeFactory.nodeSession()
        .glLine(0, 0, 0, 300f)
        .glLine(0, 0, 0, -300f)
        .glLine(0, 0, -300, 0)
        .glLine(0, 0, 300, 0)
        .then(line -> line.setAllPointColor(Color.ORANGE))
        .consumeStream(this::buildVectors, GLLine.class)
        .get();
        
        auto(() -> {
            fourLine.setCentralX(mouseIO.getMouseX(shapeFactory.getLayer()));
            fourLine.setCentralY(mouseIO.getMouseY(shapeFactory.getLayer()) + 150f);
        });
        
    }

    private void buildVectors(Stream<GLLine> s) {
        AbstractVector[] vecs = s.map(this::convert).toArray(AbstractVector[]::new);
        //4 sign
        GLRect[] signs = new GLRect[4];
        for(int i = 0;i < signs.length;i++) {
            signs[i] = shapeFactory.buildGLRect(0, 0, 5, 5);
            signs[i].setFill(true);
            signs[i].setAllPointColor(Color.GRAY);
        }
        
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
