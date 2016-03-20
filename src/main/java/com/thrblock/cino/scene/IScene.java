package com.thrblock.cino.scene;

public interface IScene {
	public void init();
	public void show();
	public void hide();
	public void destory();
	public IScene nextScene();
}
