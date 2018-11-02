package com.thrblock.game.demo.component;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLLine;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.glshape.factory.GLNode;
import com.thrblock.cino.util.math.CMath;
import com.thrblock.cino.util.math.CubeBezier;
import com.thrblock.cino.util.structure.Point2D;

@Component
public class BezierDemo extends CinoComponent {
    static final float RECTW = 400f;
    static final float CONTROLW = 10f;
    static final float FLAGW = 3f;
    static final float SPEED = 2.5f;
    static final int PRECISION = 100;
    private Point2D p1 = new Point2D();
    private Point2D p2 = new Point2D();

    private GLRect[] flags = new GLRect[PRECISION];
    private GLRect[] flagFits = new GLRect[PRECISION];

    @Override
    public void init() throws Exception {
        GLNode root = shapeFactory.createNode();

        GLRect back = shapeFactory.buildGLRect(0, 0, RECTW, RECTW);
        back.setFill(true);
        back.setAllPointColor(Color.GRAY);

        buildControlC1();
        buildControlC2();
        buildBezier();

        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_I) || keyIO.isKeyDown(KeyEvent.VK_K)
                    || keyIO.isKeyDown(KeyEvent.VK_J) || keyIO.isKeyDown(KeyEvent.VK_L)
                    || keyIO.isKeyDown(KeyEvent.VK_W) || keyIO.isKeyDown(KeyEvent.VK_S)
                    || keyIO.isKeyDown(KeyEvent.VK_A) || keyIO.isKeyDown(KeyEvent.VK_D)) {
                activitedProcess();
            }
        });
        onActivited(() -> {
            root.show();
            keyIO.pushKeyListener(this);
        });
        onDeactivited(() -> {
            root.show();
            keyIO.popKeyListener();
        });

        shapeFactory.backtrack();
    }

    private void buildBezier() {
        for (int i = 0; i < PRECISION; i++) {
            flags[i] = shapeFactory.buildGLRect(0, -RECTW / 2, FLAGW, FLAGW);
            flags[i].setCentralX(RECTW * i / PRECISION - 200f);
            flags[i].setAllPointColor(Color.RED);
            flags[i].setFill(true);
            flags[i].setAlpha(0.5f);
            
            flagFits[i] = shapeFactory.buildGLRect(0, -RECTW / 2, FLAGW, FLAGW);
            flagFits[i].setCentralX(RECTW * i / PRECISION - 200f);
            flagFits[i].setAllPointColor(Color.BLUE);
            flagFits[i].setFill(true);
            flagFits[i].setAlpha(0.5f);
        }
    }

    private void activitedProcess() {
        CubeBezier b = new CubeBezier(p1, p2);
        Point2D res = new Point2D();
        for (int i = 0; i < PRECISION; i++) {
            float t = (float) i / PRECISION;
            b.computeBezier(res, t);
            flags[i].setCentralY(res.getY() * RECTW - RECTW / 2);
            flags[i].setCentralX(res.getX() * RECTW - RECTW / 2);
            flagFits[i].setCentralY(b.bezierY(t) * RECTW - RECTW / 2);
            flagFits[i].setCentralX(b.bezierX(t) * RECTW - RECTW / 2);
        }

    }

    private void buildControlC1() {
        GLRect c1 = shapeFactory.buildGLRect(0, 0, CONTROLW, CONTROLW);
        c1.setFill(true);
        c1.setAllPointColor(Color.GREEN);

        GLLine line = shapeFactory.buildGLLine(-RECTW / 2, -RECTW / 2, 1f, 1f);
        line.setLineWidth(3.0f);
        line.setAllPointColor(Color.BLACK);

        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_W)) {
                c1.setCentralY(CMath.clamp(c1.getCentralY() + SPEED, -RECTW / 2, RECTW / 2));
            } else if (keyIO.isKeyDown(KeyEvent.VK_S)) {
                c1.setCentralY(CMath.clamp(c1.getCentralY() - SPEED, -RECTW / 2, RECTW / 2));
            }
            if (keyIO.isKeyDown(KeyEvent.VK_D)) {
                c1.setCentralX(CMath.clamp(c1.getCentralX() + SPEED, -RECTW / 2, RECTW / 2));
            } else if (keyIO.isKeyDown(KeyEvent.VK_A)) {
                c1.setCentralX(CMath.clamp(c1.getCentralX() - SPEED, -RECTW / 2, RECTW / 2));
            }

            p1.setX((c1.getCentralX() + RECTW / 2) / RECTW);
            p1.setY((c1.getCentralY() + RECTW / 2) / RECTW);
            line.setEndX(c1.getCentralX());
            line.setEndY(c1.getCentralY());
        });
    }

    private void buildControlC2() {
        GLRect c2 = shapeFactory.buildGLRect(0, 0, CONTROLW, CONTROLW);
        c2.setFill(true);
        c2.setAllPointColor(Color.BLUE);

        GLLine line = shapeFactory.buildGLLine(RECTW / 2, RECTW / 2, 1f, 1f);
        line.setLineWidth(3.0f);
        line.setAllPointColor(Color.BLACK);

        auto(() -> {
            if (keyIO.isKeyDown(KeyEvent.VK_I)) {
                c2.setCentralY(CMath.clamp(c2.getCentralY() + SPEED, -RECTW / 2, RECTW / 2));
            } else if (keyIO.isKeyDown(KeyEvent.VK_K)) {
                c2.setCentralY(CMath.clamp(c2.getCentralY() - SPEED, -RECTW / 2, RECTW / 2));
            }
            if (keyIO.isKeyDown(KeyEvent.VK_L)) {
                c2.setCentralX(CMath.clamp(c2.getCentralX() + SPEED, -RECTW / 2, RECTW / 2));
            } else if (keyIO.isKeyDown(KeyEvent.VK_J)) {
                c2.setCentralX(CMath.clamp(c2.getCentralX() - SPEED, -RECTW / 2, RECTW / 2));
            }
            p2.setX((c2.getCentralX() + RECTW / 2) / RECTW);
            p2.setY((c2.getCentralY() + RECTW / 2) / RECTW);
            line.setEndX(c2.getCentralX());
            line.setEndY(c2.getCentralY());
        });
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            activitedProcess();
        }
    }
}
