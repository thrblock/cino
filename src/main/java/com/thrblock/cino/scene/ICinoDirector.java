package com.thrblock.cino.scene;

public interface ICinoDirector {
	public void initRootScene(ICinoScene scene);
	public void pushScene(ICinoScene scene);
	public void replaceScene(ICinoScene scene);
	public void popScene();
	public void popAllScene();
	
	public void end();
	public void pause();
	public void resume();
}
