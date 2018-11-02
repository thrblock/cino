package com.thrblock.cino.gltexture;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class FontsInCommon {
    private static final Logger LOG = LoggerFactory.getLogger(FontsInCommon.class);
    public static final String DIALOG = "Dialog";
    public static final String DIALOGINPUT = "DialogInput";
    /**
     * GNU Free Mono font
     */
    public static final String GNU_FREE_MONO = "Free Monospaced";

    @PostConstruct
    public void loadGnuFreeFont() {
        try {
            Resource resource = new ClassPathResource("gnufreefont/FreeMono.ttf");
            InputStream is = resource.getInputStream();
            Font f = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
        } catch (IOException | FontFormatException e) {
            LOG.warn("fail to load gnu free mono font:{}", e);
        }
    }
}
