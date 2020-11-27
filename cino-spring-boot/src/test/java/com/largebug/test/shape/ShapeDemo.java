package com.largebug.test.shape;

import java.awt.Color;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.thrblock.cino.annotation.BootComponent;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.concept.Line;
import com.thrblock.cino.concept.Point;
import com.thrblock.cino.concept.Polygon;
import com.thrblock.cino.concept.Rect;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLPolygonShape;
import com.thrblock.cino.glshape.GLTriangle;
import com.thrblock.cino.gltexture.GLIOTexture;
import com.thrblock.cino.vec.Vec2;

@Component
@BootComponent
public class ShapeDemo extends CinoComponent {
    @Override
    public void init() throws Exception {
        autoShowHide();
        // 点
        rootNode().glPoint(new Point(25, 12)).setColor(Color.RED);
        // 线
        rootNode().glLine(new Line(new Vec2(-50, -50), new Vec2(50, 50))).setAllPointColor(Color.GREEN);
        // 三角形
        GLTriangle t = rootNode().glTriangle(-50, 0, 0, 100, 50, 0);
        t.setYOffset(200);
        t.setPointColor(0, Color.RED);
        t.setPointColor(1, Color.GREEN);
        t.setPointColor(2, Color.BLUE);
        t.setFill(true);
        // 矩形
        rootNode().glRect(new Rect(0, 0, 100, 100)).setAllPointColor(Color.CYAN);
        // 多边形
        GLPolygonShape<Polygon> p = rootNode().glPolygon(new Vec2(-50, -50), new Vec2(-60, -40), new Vec2(-70, -30),
                new Vec2(-80, 0), new Vec2(0, 0));
        p.setXOffset(400);
        p.setFill(true);
        p.setAllPointColor(Color.DARK_GRAY);
        // 多边形（拟合椭圆）
        rootNode().glOval(0, 0, 400, 100, 64).setXOffset(-300);
        // 纹理贴图
        GLImage image = rootNode().glImage(200,200);
        ClassPathResource res = new ClassPathResource("./dwn.png");
        image.setTexture(new GLIOTexture(res.getInputStream(), "png"));
        image.setYOffset(-200);
    }
}
