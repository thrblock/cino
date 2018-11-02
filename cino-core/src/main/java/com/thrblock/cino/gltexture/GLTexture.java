package com.thrblock.cino.gltexture;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.texture.Texture;
import com.thrblock.cino.glinitable.GLInitializable;

/**
 * OpenGL 纹理对象，一般来讲，纹理可由外部图像资源文件、流 与 内存图像缓冲区加载而来；<br />
 * 通常情况下，由资源转化为纹理的过程耗时较长（相对绘制周期而言）因此使用大型纹理时推荐进行预加载<br />
 * 
 * @author zepu.li
 */
public interface GLTexture extends GLInitializable {
	/**
	 * 活的纹理，当纹理不存在时使用指定的gl上下文进行加载
	 * 
	 * @param gl
	 * @return
	 */
	public Texture getTexture(GL gl);
}
