package com.thrblock.game.demo.component;

import java.awt.Color;
import java.io.File;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.debug.DebugPannel;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.GLIOTexture;
import com.thrblock.cino.gltexture.GLTexture;
import com.thrblock.cino.util.structure.Point2D;
import com.thrblock.cino.util.structure.SupplierFactory;
import com.thrblock.rectbase.button.GLImgButton;
import com.thrblock.rectbase.button.GLTextButton;
import com.thrblock.rectbase.checkbox.GLCheckBox;
import com.thrblock.rectbase.container.GLContainer;
import com.thrblock.rectbase.layout.VerticalAdaptLayout;
import com.thrblock.rectbase.progressbar.GLProgressBar;
import com.thrblock.rectbase.radio.GLRadio;
import com.thrblock.rectbase.radio.RadioGroup;
import com.thrblock.rectbase.slider.GLSliderBar;

@Component
public class RectBasedUI extends CinoComponent {
    
    @Autowired
    DebugPannel pannel;
    
    @Override
    public void init() throws Exception {
        onActivited(pannel::activited);
        autoShowHide();

        buildBackground();
        buildTextButton();
        buildImgButton();
        buildCheckBox();
        buildRadioGroup();

        GLContainer container = injectInstance(new GLContainer(120, 320, new VerticalAdaptLayout()));
        container.setX(200);
        container.setY(100);

        GLTexture normalFace = new GLIOTexture(new File("./res/faceNormal.png"));
        GLTexture faceDown = new GLIOTexture(new File("./res/faceDown.png"));

        GLImgButton imgButton = injectInstance(new GLImgButton(normalFace, faceDown, 30, 30));

        GLTextButton b1 = injectInstance(new GLTextButton("Button1", 100, 30));
        GLTextButton b2 = injectInstance(new GLTextButton("Button2", 100, 30));
        GLTextButton b3 = injectInstance(new GLTextButton("Button3", 100, 30));
        GLTextButton b4 = injectInstance(new GLTextButton("Button4", 100, 30));

        GLProgressBar progressBar = injectInstance(new GLProgressBar(100, 20));
        progressBar.setProgress(0.3f);
        
        GLSliderBar slideBar = injectInstance(new GLSliderBar(100, 20));
        slideBar.setProgress(0.75f);
        
        container.add(imgButton);
        container.add(b1);
        container.add(b2);
        container.add(b3);
        container.add(b4);
        container.add(progressBar);
        container.add(slideBar);
        slideBar.addProgressChange(progressBar::setProgress);
        
        Supplier<Point2D> p2d = SupplierFactory.beizerSmooth(200, 100, -200, 100, 600);
        auto(p2d,p -> {
            container.setX(p.getX());
            container.setY(p.getY());
        });
    }

    private void buildRadioGroup() {
        RadioGroup group = new RadioGroup();
        GLRadio radio = injectInstance(new GLRadio(16));
        radio.setCheck(true);
        radio.setY(-100);
        radio.setX(50);
        group.addRadioToGroup(radio);

        radio = injectInstance(new GLRadio(16));
        radio.setY(-100);
        radio.setX(80);
        group.addRadioToGroup(radio);

        radio = injectInstance(new GLRadio(16));
        radio.setY(-100);
        radio.setX(110);
        group.addRadioToGroup(radio);
    }

    private void buildCheckBox() {
        GLCheckBox checkBox = injectInstance(new GLCheckBox(16));
        checkBox.setY(-100);
        checkBox.setCheck(true);
    }

    private void buildImgButton() {
        GLTexture normalFace = new GLIOTexture(new File("./res/faceNormal.png"));
        GLTexture faceDown = new GLIOTexture(new File("./res/faceDown.png"));
        GLTexture faceFail = new GLIOTexture(new File("./res/faceFail.png"));

        GLImgButton imgButton = injectInstance(new GLImgButton(normalFace, faceDown, 30, 30));
        imgButton.addMouseClicked(e -> imgButton.setButtonUp(faceFail));
        imgButton.addMouseMovein(() -> logger.info("img move in!"));
        imgButton.addMouseMoveout(() -> logger.info("img move out!"));
        imgButton.setY(100);
    }

    private void buildTextButton() {
        GLTextButton textButton = injectInstance(new GLTextButton("Button", 100, 30));
        textButton.setX(-100);
        textButton.addMouseClicked(e -> logger.info("clicked !"));
        textButton.addMouseMovein(() -> logger.info("move in!"));
        textButton.addMouseMoveout(() -> logger.info("move out!"));
    }

    private void buildBackground() {
        GLRect bg = shapeFactory.buildGLRect(0, 0, screenW, screenW);
        bg.setAllPointColor(Color.GRAY);
        bg.setFill(true);
    }
}
