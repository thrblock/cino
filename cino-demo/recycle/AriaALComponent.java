package com.thrblock.game.demo.component;

import java.awt.Color;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.aria.al.ALListener;
import com.thrblock.aria.al.ALService;
import com.thrblock.aria.data.MusicData;
import com.thrblock.aria.decoder.SPIDecoder;
import com.thrblock.aria.source.ALSource;
import com.thrblock.aria.source.ALSourceFactory;
import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.io.KeyEvent;
import com.thrblock.cino.util.structure.FloatBoxer;
import com.thrblock.rectbase.button.GLTextButton;
import com.thrblock.rectbase.container.GLContainer;

@Component
public class AriaALComponent extends CinoComponent {

    @Autowired
    ALSourceFactory alfactory;

    @Autowired
    ALService alservice;
    
    @Autowired
    ALListener allistener;

    @Override
    public void init() throws Exception {
        autoShowHide();
        GLRect bg = shapeFactory.buildGLRect(0, 0, screenW, screenH);
        bg.setFill(true);
        bg.setAllPointColor(Color.GRAY);

        GLRect listener = shapeFactory.buildGLRect(0, 0, 20, 20);
        listener.setFill(true);
        listener.setAllPointColor(Color.YELLOW);
        listener.setRadian((float) Math.PI / 4);

        GLOval sourceL = shapeFactory.buildGLOval(0, 0, 20, 20, 12);
        sourceL.setAllPointColor(Color.GREEN);
        sourceL.setFill(true);

        GLOval sourceR = shapeFactory.buildGLOval(0, 0, 20, 20, 12);
        sourceR.setAllPointColor(Color.RED);
        sourceR.setFill(true);

        SPIDecoder decoder = new SPIDecoder();
        MusicData musicL = new MusicData(decoder, new File("/D:/work/GoldWave/MusicWorkSpace/RS_L.mp3"));
        musicL.setAutoReload(true);
        MusicData musicR = new MusicData(decoder, new File("/D:/work/GoldWave/MusicWorkSpace/RS_R.mp3"));
        musicR.setAutoReload(true);

        ALSource alsourceL = alfactory.generateALSource(false);
        ALSource alsourceR = alfactory.generateALSource(false);

        alsourceL.initData(musicL);
        alsourceL.setReferenceDistance(100f);
        alsourceL.setRolloffFactor(10f);
        alsourceL.setDopplerFactor(0.01f);

        alsourceR.initData(musicR);
        alsourceR.setReferenceDistance(100f);
        alsourceR.setRolloffFactor(10f);
        alsourceR.setDopplerFactor(0.01f);

        onActivited(() -> {
            alsourceL.play();
            alservice.check();
            alsourceR.play();
        });

        FloatBoxer theta = new FloatBoxer();
        FloatBoxer rad = new FloatBoxer();
        FloatBoxer spd = new FloatBoxer();
        spd.setValue(0.015f);
        rad.setValue(100f);
        auto(() -> {
            theta.setValue(theta.getValue() + spd.getValue());
            sourceL.setCentralX(rad.getValue() * (float) Math.cos(theta.getValue()));
            sourceL.setCentralY(rad.getValue() * (float) Math.sin(theta.getValue()));
            alsourceL.setPosition2D(sourceL.getCentralX(), sourceL.getCentralY());
            alsourceL.setVelocity2D(
                    -theta.getValue() * rad.getValue() * (float) Math.sin(theta.getValue()),
                    theta.getValue() * rad.getValue() * (float) Math.cos(theta.getValue()));

            sourceR.setCentralX(rad.getValue() * (float) Math.cos(theta.getValue() + Math.PI / 12));
            sourceR.setCentralY(rad.getValue() * (float) Math.sin(theta.getValue() + Math.PI / 12));
            alsourceR.setPosition2D(sourceR.getCentralX(), sourceR.getCentralY());
            alsourceR.setVelocity2D(
                    -theta.getValue() * rad.getValue() * (float) Math.sin(theta.getValue() + Math.PI / 12),
                    theta.getValue() * rad.getValue() * (float) Math.cos(theta.getValue() + Math.PI / 12));
            
            if (keyIO.isKeyDown(KeyEvent.VK_UP)) {
                listener.setYOffset(1f);
            } else if (keyIO.isKeyDown(KeyEvent.VK_DOWN)) {
                listener.setYOffset(-1f);
            }
            if (keyIO.isKeyDown(KeyEvent.VK_RIGHT)) {
                listener.setXOffset(1f);
            } else if (keyIO.isKeyDown(KeyEvent.VK_LEFT)) {
                listener.setXOffset(-1f);
            }
            
            allistener.setPosition2D(listener.getCentralX(), listener.getCentralY());

            alservice.check();
        });

        GLContainer container = injectInstance(new GLContainer(600, 50));
        container.setY(-250);

        GLTextButton addRangeBtn = injectInstance(new GLTextButton("Add Range", 100, 25));
        addRangeBtn.addMouseClicked(e -> spd.setValue(spd.getValue() * 1.314f));
        container.add(addRangeBtn);

        GLTextButton reduceRangeBtn = injectInstance(new GLTextButton("Reduce Range", 100, 25));
        reduceRangeBtn.addMouseClicked(e -> spd.setValue(spd.getValue() / 1.314f));
        container.add(reduceRangeBtn);

    }
}
