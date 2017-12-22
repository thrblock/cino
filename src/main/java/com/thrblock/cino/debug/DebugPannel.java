package com.thrblock.cino.debug;

import java.awt.Color;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.util.charprocess.CharAreaConfig;
import com.thrblock.cino.util.charprocess.CharArrayInt;

@Component
@Lazy(true)
public class DebugPannel extends CinoComponent {
    @Value("${cino.frame.fps:60}")
    private int fps;

    @Value("${cino.debug.enable:false}")
    private boolean debug;

    private int debugPannelWidth = 50;
    private int debugPannelHeight = 50;

    private int count = 0;
    private long allTimeUse = 0;
    private long lastRegTime;

    private CharArrayInt arrayInt4fps;
    private CharArrayInt arrayInt4ovr;
    private CharArrayInt arrayInt4ft;

    @Override
    public void init() throws Exception {
        if (debug) {
            lastRegTime = System.currentTimeMillis();
            shapeFactory.setLayer(-1);
            buildPanel();
            buildFPS();
            buildOVR();
            buildOVD();

            autoShowHide();

            sceneRoot.setCentralX((float)(screenW - debugPannelWidth) / 2);
            sceneRoot.setCentralY((float)(screenH - debugPannelHeight) / 2);
        }
    }

    private void buildPanel() {
        GLRect rect = shapeFactory.buildGLRect(0, 0, debugPannelWidth, debugPannelHeight);
        rect.setAllPointColor(Color.GRAY);
        rect.setFill(true);
        rect.setAlpha(0.5f);
    }

    private void buildFPS() {
        char[] fpsC = new char[7];
        arrayInt4fps = new CharArrayInt(fpsC);
        arrayInt4fps.setFrontKeepStr("Fps:");
        arrayInt4fps.setByInt(0);
        shapeFactory.buildGLCharArea(0, 22, 50, new CharAreaConfig(fpsC)).setSimpleStyle(i -> i.setAllPointColor(Color.BLACK));
    }

    private void buildOVR() {
        char[] fpsC = new char[7];
        arrayInt4ovr = new CharArrayInt(fpsC);
        arrayInt4ovr.setFrontKeepStr("Ovr:");
        arrayInt4ovr.setBackKeepStr("%");
        arrayInt4ovr.setByInt(0);
        shapeFactory.buildGLCharArea(0, 3, 50, new CharAreaConfig(fpsC)).setSimpleStyle(i -> i.setAllPointColor(Color.BLACK));
    }

    private void buildOVD() {
        char[] fpsC = new char[7];
        arrayInt4ft = new CharArrayInt(fpsC);
        arrayInt4ft.setFrontKeepStr("Tu:");
        arrayInt4ft.setByInt(0);
        shapeFactory.buildGLCharArea(0, -18, 50, new CharAreaConfig(fpsC)).setSimpleStyle(i -> i.setAllPointColor(Color.BLACK));
    }

    public void noticeDrawCall(long timeUse) {
        if (!debug) {
            return;
        }
        if (count < fps) {
            count++;
            allTimeUse += timeUse;
        } else {
            long passRealTime = System.currentTimeMillis() - lastRegTime;
            this.lastRegTime = System.currentTimeMillis();
            count = 0;
            long second = passRealTime / 1000L;
            second = second == 0 ? 1 : second;
            int avgTimeCount = (int) (fps / second);
            long avgTimeUse = allTimeUse / fps;
            if (allTimeUse > 9999) {
                arrayInt4ft.setByInt(9999);
            } else {
                arrayInt4ft.setByInt((int) allTimeUse);
            }
            allTimeUse = 0;
            float avgTimeCent = avgTimeUse / (1000f / fps);
            int timeCent = (int) (avgTimeCent * 100);
            arrayInt4fps.setByInt(avgTimeCount);
            arrayInt4ovr.setByInt(timeCent);
        }
    }
}
