package com.thrblock.cino.glfont;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * CinoTagFontInitor是一个已实现的名称转换实例,它定义了这样的格式：<br />
 * 名称被定义为<b>实现类</b>所在包的下一级包名<br />
 * 下一级包中存放着png切片，切片名称为对应文字的char值<br />
 * 除切片外，还存在着文字的范围记录font.ctag文件，内容为总长度，最小字符，最大字符及字符集和<br />
 * @author lizepu
 */
public abstract class CinoTagFontInitor extends GLFontInitor {
	@Override
	protected GLFontTexture getFontTexture(String name) {
		logger.info("generating glfont object for name:" + name);
		try(InputStream is = getClass().getResourceAsStream(name + "/font.ctag")) {
			DataInputStream dis = new DataInputStream(is);
			int charNumber = dis.readInt();
			logger.info("char number:" + charNumber);
			char[] chars = new char[charNumber];
			char min = dis.readChar();
			char max = dis.readChar();
			logger.info("max char:" + max + "("+(int)max+")");
			logger.info("min char:" + min + "("+(int)min+")");
			for(int i = 0;i < chars.length;i++) {
				chars[i] = dis.readChar();
			}
			logger.info("chars loaded:" + new String(chars));
			return new GLFontTexture(getClass(),name,chars,max,min);
		} catch (IOException e) {
			logger.info("IOException:" + e);
			return null;
		}
	}
}
