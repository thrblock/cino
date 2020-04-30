package com.thrblock.game.demo.component.lines;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.lintersection.AbstractVector;
import com.thrblock.cino.lintersection.InstersectionResultHolder;
import com.thrblock.cino.lintersection.LIntersectionCounter;
import com.thrblock.cino.lintersection.SupplierVector;

//@Component
public class LineAreaDemo extends CinoComponent {
    private LIntersectionCounter<AbstractVector> lines = new LIntersectionCounter<>();
    private InstersectionResultHolder<AbstractVector> insRes = new InstersectionResultHolder<>();

    @Override
    public void init() throws Exception {
        autoShowHide();
        GLRect bg = shapeFactory.buildGLRect(0, 0, 800f, 600f);
        bg.setFill(true);
        bg.setAllPointColor(Color.DARK_GRAY);
        
        buildLine(-200,-100, 200,-100);
        buildLine( 200,-100, 400,-300);

        buildRect();
    }
    
    private void buildRect() {
        GLRect rect = shapeFactory.buildGLRect(0, 0, 10,10);
        rect.setFill(true);
        rect.setAllPointColor(Color.GREEN);
        
        GLRect foot = shapeFactory.buildGLRect(0, -3.5f, 3,3);
        foot.setFill(true);
        foot.setAllPointColor(Color.RED);
        
        SupplierVector vec = new SupplierVector(rect::getCentralX,rect::getCentralY,rect::getCentralX,()->rect.getCentralY() - 50f);
        
        auto(()->{
            if(keyIO.isKeyDown(KeyEvent.VK_DOWN)) {
                rootNode.setYOffset(-1);
            } else if(keyIO.isKeyDown(KeyEvent.VK_UP)) {
                rootNode.setYOffset( 1);
            }
            if(keyIO.isKeyDown(KeyEvent.VK_LEFT)) {
                rootNode.setXOffset(-2.0f);
            } else if(keyIO.isKeyDown(KeyEvent.VK_RIGHT)) {
                rootNode.setXOffset( 2.0f);
            }
            lines.intersection(vec, insRes);
            if(insRes.isIntersect()) {
                float dx = insRes.getX() - foot.getCentralX();
                float dy = insRes.getY() - foot.getCentralY();
                rootNode.setXOffset(dx);
                rootNode.setYOffset(dy);
            }
        });
    }
    

    private void buildLine(float x1,float y1,float x2,float y2) {
        GLLine line = shapeFactory.buildGLLine(x1, y1, x2, y2);
        line.setLineWidth(2.0f);
        lines.addLine(convert(line));
    }

    private GLVector convert(GLLine line) {
        return new GLVector(line);
    }
}
