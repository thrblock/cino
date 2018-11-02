package com.thrblock.game.demo.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.context.support.AbstractApplicationContext;

import com.thrblock.aria.music.MusicPlayer;
import com.thrblock.springcontext.CinoInitor;

public class SoundDemo {
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, InterruptedException {
		AbstractApplicationContext context = CinoInitor.getContextByXml();
		MusicPlayer player = context.getBean(MusicPlayer.class);
		player.initMusic(new File("/F:/1音乐/Scarlet Ballet/Scarlet Ballet.mp3"));
		player.play(-1);
	}
}
