package com.thrblock.ui;

public abstract class AbstractItem extends AbstractUIComponent {
	protected String info = "item";
	private EventPerformed cursorOn;
	private EventPerformed cursorOff;
	private EventPerformed onItemSelect;
	
	public void setInfo(String info) {
		this.info = info;
		update();
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setCursorOn(EventPerformed cursorOn) {
		this.cursorOn = cursorOn;
	}
	
	public void setCursorOff(EventPerformed cursorOff) {
		this.cursorOff = cursorOff;
	}
	
	public void setOnItemSelect(EventPerformed onItemSelect) {
		this.onItemSelect = onItemSelect;
	}

	public void cursorOn() {
		if(cursorOn != null) {
			cursorOn.perform();
		}
	}
	
	public void cursorOff() {
		if(cursorOff != null) {
			cursorOff.perform();
		}
	}
	
	public void onItemSelect() {
		if(onItemSelect != null) {
			onItemSelect.perform();
		}
	}
}