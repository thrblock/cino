package com.thrblock.cino.gltexture;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jogamp.opengl.GL;

/**
 * 
 * @author zepu.li
 *
 */
@Component
public class CharTextureGenerater {
    private static final Logger LOG = LoggerFactory.getLogger(CharTextureGenerater.class);
    
    private static final BufferedImage BUFFERED_IMAGE = new BufferedImage(1, 1,BufferedImage.TYPE_INT_ARGB);
    private static final Graphics2D G2D = (Graphics2D) BUFFERED_IMAGE.getGraphics();
    
    private Map<Font,FontGenNode> fontMap = new HashMap<>();
    
    private static class FontPair {
        private final Font f;
        private final char[] preLoad;
        public FontPair(Font f,char[] preLoad) {
            this.f = f;
            this.preLoad = preLoad;
        }
        @Override
        public int hashCode() {
            return f.hashCode() ^ preLoad.hashCode();
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof FontPair) {
                FontPair ano = (FontPair) obj;
                return f.equals(ano.f) && Arrays.equals(preLoad, ano.preLoad);
            }
            return false;
        }
    }
    
    private Set<FontPair> swap = new HashSet<FontPair>();
    private Semaphore sp = new Semaphore(1);
    
    /**
     * 创建字体生成节点
     * @param f 字体对象
     * @return 字体生成节点，当不存在时实例化新值
     */
    public FontGenNode getFontGenNode(Font f) {
        if(!fontMap.containsKey(f)) {
            LOG.info("Generate FontGenNode for font:" + f.toString());
            fontMap.put(f, new FontGenNode(G2D.getFontMetrics(f)));
        }
        return fontMap.get(f);
    }
    
    /**
     * 预加载纹理
     * @param f 指定的字体
     * @param src 指定的内容
     */
    public void loadBatch(Font f,char[] src) {
        sp.acquireUninterruptibly();
        swap.add(new FontPair(f,src));
        sp.release();
    }
    
    /**
     * 预加载纹理
     * @param f 指定的字体
     * @param src 指定的内容
     */
    public void loadBatch(Font f,String src) {
        this.loadBatch(f, src.toCharArray());
    }
    
    /**
     * 生成纹理
     * @param gl
     */
    public void parseTexture(GL gl) {
        if(!swap.isEmpty()) {
            sp.acquireUninterruptibly();
            for(FontPair fp:swap) {
                FontGenNode fg = getFontGenNode(fp.f);
                fg.load(gl, fp.preLoad);
            }
            swap.clear();
            sp.release();
        }
    }
}
