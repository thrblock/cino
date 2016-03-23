package com.thrblock.cino.glfont.buildin;

import org.springframework.stereotype.Component;

import com.thrblock.cino.glfont.CinoTagFontInitor;

@Component
public class BuildInFont extends CinoTagFontInitor{
	public static final String TIMESNEWROMAN_PLAIN_12 = "timesnewroman_plain_12";
	public static final String TIMESNEWROMAN_PLAIN_20 = "timesnewroman_plain_20";
	@Override
	protected String[] getName() {
		return new String[]{TIMESNEWROMAN_PLAIN_12,TIMESNEWROMAN_PLAIN_20};
	}
}
