package com.thrblock.cino.gltexture;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class CinoTagFontInitor extends GLFontInitor {

	@Override
	protected GLFontTexture getFontTexture() {
		logger.info("generating glfont object...");
		try(InputStream is = getClass().getResourceAsStream("font.ctag")) {
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
			return new GLFontTexture(getClass(),chars,max,min);
		} catch (IOException e) {
			logger.info("IOException:" + e);
			return null;
		}
	}
}
