package com.thrblock.cino.glcomponentv2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * 列表叙述了一种线性空间结构
 * @author thrblock
 */
public class FList extends FComponent {
    /**
     * 索引变化事件
     * @author zepu.li
     */
    @FunctionalInterface
    public static interface IndexChange {
        /**
         * 索引变化
         * @param before 之前的索引
         * @param after 当前的索引
         */
        public void onIndexChange(int before,int after);
    }
    private List<IndexChange> indexChangeEvents = new LinkedList<>();
    private List<IntConsumer> sizeChangeEvents = new LinkedList<>();
    private ArrayList<FListItem> items = new ArrayList<>();
    private int currentIndex = -1;
    private boolean loop = false;
    
    /**
     * 是否循环
     * @param loop 是否循环
     */
    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * 选择下一个
     */
    public void selectNext() {
        if(loop) {
            int before = currentIndex;
            items.get(currentIndex).reject();
            currentIndex = (currentIndex + 1) % items.size();
            items.get(currentIndex).select();
            indexChangeEvents.forEach(e -> e.onIndexChange(before, currentIndex));
        } else if(currentIndex + 1 < items.size()) {
            items.get(currentIndex).reject();
            currentIndex++;
            items.get(currentIndex).select();
            indexChangeEvents.forEach(e -> e.onIndexChange(currentIndex - 1, currentIndex));
        }
    }
    
    /**
     * 选择前一个
     */
    public void selectPrevious() {
        if(loop) {
            int before = currentIndex;
            items.get(currentIndex).reject();
            if(currentIndex == 0) {
                currentIndex = items.size() - 1;
            } else {
                currentIndex --;
            }
            items.get(currentIndex).select();
            indexChangeEvents.forEach(e -> e.onIndexChange(before, currentIndex));
        } else if(currentIndex > 0) {
            items.get(currentIndex).reject();
            currentIndex--;
            items.get(currentIndex).select();
            indexChangeEvents.forEach(e -> e.onIndexChange(currentIndex + 1, currentIndex));
        }
    }
    
    /**
     * 选择指定索引
     * @param index
     */
    public void select(int index) {
        if(index != currentIndex) {
            int before = currentIndex;
            items.get(currentIndex).reject();
            currentIndex = index;
            items.get(currentIndex).select();
            indexChangeEvents.forEach(e -> e.onIndexChange(before, currentIndex));
        }
    }
    
    /**
     * 增加一个列表项
     * @param item 列表项
     */
    public void addItem(FListItem item) {
        items.add(item);
        sizeChangeEvents.forEach(e->e.accept(items.size()));
        addSubComponent(item);
        if(currentIndex == -1) {
            currentIndex = 0;
            items.get(currentIndex).select();
        }
    }
    
    /**
     * 挂载索引变化监听逻辑
     * @param ic索引变化监听逻辑
     */
    public void onIndexChange(IndexChange ic) {
        this.indexChangeEvents.add(ic);
    }
    
    /**
     * 挂载元素数量变化监听逻辑
     * @param sc
     */
    public void onSizeChange(IntConsumer sc) {
        this.sizeChangeEvents.add(sc);
    }
    
    /**
     * 激活选择的模式
     */
    public void activitedCurrent() {
        items.get(currentIndex).activited();
    }
}
