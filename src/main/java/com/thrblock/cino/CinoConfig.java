package com.thrblock.cino;

public abstract class CinoConfig {
	public abstract int getScreenWidth();
	public abstract int getScreenHeight();
	public abstract int getFPS();
	
	public static CinoConfig getDefault() {
		return new CinoConfig(){
			@Override
			public int getScreenWidth() {
				return 800;
			}

			@Override
			public int getScreenHeight() {
				return 600;
			}

			@Override
			public int getFPS() {
				return 60;
			}
			
		};
	}
}
