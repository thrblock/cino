package com.thrblock.game.demo.component;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL2;
import com.thrblock.cino.b2dplugin.B2dBinder;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.gllayer.GLFrameBufferObject;
import com.thrblock.cino.gllayer.IGLFrameBufferObjectManager;
import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.GLIOTexture;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.cino.shader.GLProgram;
import com.thrblock.cino.shader.GLShader;
import com.thrblock.cino.shader.data.GLCommonUniform;
import com.thrblock.cino.shader.data.GLUniformFloat;
import com.thrblock.cino.util.math.CRand;
import com.thrblock.cino.util.structure.SupplierFactory;

/**
 * 
 * @author zepu.li
 *
 */
@Component
public class SmtPhy extends CinoComponent {
    private static final int FUNNY_NUMBER = 48;
    private static final float FUNNY_RADIS = 60f;

    private Body[] bds = new Body[FUNNY_NUMBER];

    @Autowired
    private B2dBinder binder;

    @Autowired
    private IGLFrameBufferObjectManager postProcess;

    @Autowired
    private GLCommonUniform commonsUniform;

    @Autowired
    private DebugPannel fps;

    @Override
    public void init() throws IOException {
        autoKeyPushPop();
        
        GLShader vex = new GLShader(GL2.GL_VERTEX_SHADER, new File("./shadersV2/demo/Vertex.txt"));
        GLShader frgA = new GLShader(GL2.GL_FRAGMENT_SHADER, new File("./shadersV2/demo/Frag_graylize"));
        GLShader frgB = new GLShader(GL2.GL_FRAGMENT_SHADER, new File("./shadersV2/demo/Frag_shake"));
        GLProgram programA = new GLProgram(vex, frgA);
        GLUniformFloat graylize = new GLUniformFloat("factor");
        programA.bindDataAsFloat(graylize);
        var sp = SupplierFactory.cycleOfSin(60 * 5);
        auto(() -> graylize.setValue((float) Math.abs(sp.get())));
        GLProgram programB = new GLProgram(vex, frgB);

        Vec2 gravity = new Vec2(0, -10f);
        World world = new World(gravity);

        binder.setWorld(world);
        binder.mappingScreenHeight(100f);
        binder.setRestitution(0.8f);
        binder.setFragmentManager(compAni);

        GLRect backGround = shapeFactory.buildGLRect(0, 0, 800f, 600f);
        backGround.setFill(true);
        backGround.setAllPointColor(Color.GRAY);

        GLRect bottom = shapeFactory.buildGLRect(0, -275, 800, 50);
        bottom.setAllPointColor(Color.DARK_GRAY);
        bottom.setFill(true);

        GLRect left = shapeFactory.buildGLRect(-375, 0, 50, 600);
        left.setAllPointColor(Color.DARK_GRAY);
        left.setFill(true);

        GLRect right = shapeFactory.buildGLRect(375, 0, 50, 600);
        right.setAllPointColor(Color.DARK_GRAY);
        right.setFill(true);

        binder.bindRectToRect(bottom.exuviate(), false);
        binder.bindRectToRect(left.exuviate(), false);
        binder.bindRectToRect(right.exuviate(), false);

        GLTexture funny = new GLIOTexture(getClass().getResourceAsStream("haha.png"), "png");
        shapeFactory.setLayer(1);
        for (int i = 0; i < FUNNY_NUMBER; i++) {
            GLImage img = shapeFactory.buildGLImage(0, 0, FUNNY_RADIS, FUNNY_RADIS, funny);
            img.setCentralY(300 + i * FUNNY_RADIS);
            img.setCentralX(CRand.getRandomNum(-300, 300));
            bds[i] = binder.bindRectToCircle(img.exuviate(), true);
        }

        shapeFactory.setLayer(2);
        GLTexture googlePng = new GLIOTexture(new File("./google.png"));
        shapeFactory.buildGLImage(0, 0, 240f, 88f, googlePng);

        GLFrameBufferObject fboA = postProcess.generateLayerFBO(1);
        GLFrameBufferObject fboB = postProcess.generateLayerFBO(1);
        auto(() -> world.step(1f / frame.getFramesPerSecond(), 6, 2));
        auto(commonsUniform.setCommonUniform(programA));
        auto(commonsUniform.setCommonUniform(programB));

        onActivited(() -> {
            fps.activited();
            fboA.setGLProgram(programA);
            fboB.setGLProgram(programB);
        });
        onDeactivited(() -> {
            fps.deactivited();
            fboA.setGLProgram(null);
            fboB.setGLProgram(null);
            reset();
        });
    }

    private void reset() {
        for (int i = 0; i < FUNNY_NUMBER; i++) {
            float y = (300 + i * FUNNY_RADIS) / binder.getMeterToPixel();
            float x = CRand.getRandomNum(-150, 150) / binder.getMeterToPixel();
            bds[i].setTransform(new Vec2(x, y), 0);
            bds[i].setAngularVelocity(0);
            bds[i].setLinearVelocity(new Vec2(0, 0));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            reset();
        }
        if (e.getKeyCode() == KeyEvent.VK_B) {
            deactivited();
        }
    }
}
