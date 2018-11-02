package com.thrblock.ui;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractList extends AbstractUIComponent {
    protected List<AbstractItem> itemList = new ArrayList<>();
    private int currentIndex = -1;
    public void addItem(AbstractItem item) {
        add(item);
        itemList.add(item);
        if(currentIndex == -1) {
            currentIndex = 0;
            item.cursorOn();
        }
    }
    
    public boolean moveTo(int index) {
        if(index >= 0 && index < itemList.size() && index != currentIndex) {
            AbstractItem old = itemList.get(currentIndex);
            old.cursorOff();
            currentIndex = index;
            AbstractItem next = itemList.get(currentIndex);
            next.cursorOn();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean moveNext() {
        if(currentIndex < itemList.size() - 1) {
            AbstractItem old = itemList.get(currentIndex);
            old.cursorOff();
            currentIndex += 1;
            AbstractItem next = itemList.get(currentIndex);
            next.cursorOn();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean movePrevious() {
        if(currentIndex > 0) {
            AbstractItem old = itemList.get(currentIndex);
            old.cursorOff();
            currentIndex -= 1;
            AbstractItem next = itemList.get(currentIndex);
            next.cursorOn();
            return true;
        } else {
            return false;
        }
    }
    
    public boolean selectCurrent() {
        if(currentIndex != -1) {
            AbstractItem item = itemList.get(currentIndex);
            item.onItemSelect();
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void init() {
    }
    
    @Override
    public void show() {
        itemList.forEach((e) -> e.show());
    }
    
    @Override
    public void hide() {
        itemList.forEach((e) -> e.hide());
    }
    
    @Override
    public void destory() {
        itemList.forEach((e) -> e.destory());
    }
}