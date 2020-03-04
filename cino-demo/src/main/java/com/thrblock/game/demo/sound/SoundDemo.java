package com.thrblock.game.demo.sound;

import java.io.File;


import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.aria.music.MusicPlayer;
import com.thrblock.springcontext.CinoInitor;

public class SoundDemo {
	public static void main(String[] args) {
		AbstractApplicationContext context = CinoInitor.getContextByXml();
		var player = context.getBean(MusicPlayer.class);
		player.initMusic(new File("/D:/ms.mp3"));
		player.play(-1);
	}
}
