package com.thrblock.cino.glfont.buildin;

import org.springframework.stereotype.Component;

import com.thrblock.cino.glfont.CinoTagFontInitor;

/**
 * BuildInFont 内置字体<br />
 * 包含了TIMES NEW ROMAN的12号及20号字字母母及符号的图片切片，不含字体文件
 * @author lizepu
 *
 */
@Component
public class BuildInFont extends CinoTagFontInitor{
	public static final String TIMESNEWROMAN_PLAIN_12 = "timesnewroman_plain_12";
	public static final String TIMESNEWROMAN_PLAIN_20 = "timesnewroman_plain_20";
	@Override
	protected String[] getName() {
		return new String[]{TIMESNEWROMAN_PLAIN_12,TIMESNEWROMAN_PLAIN_20};
	}
}
