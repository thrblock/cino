package com.thrblock.cino.glcomponentv2;

import java.util.LinkedList;
import java.util.List;

/**
 * 进度条<br />
 * 进度条以两个整形数据代表进度信息，即当前值与总值；<br />
 * 进度条保证两个整形值皆为非负数数并且总值>=当前值<br />
 * 使用时，请注意0/0的情况
 * @author zepu.li
 *
 */
public class FProgressBar {
    /**
     * 进度条数值变更
     * 
     * @author zepu.li
     *
     */
    @FunctionalInterface
    public static interface ProgressBarChanged {
        /**
         * 进度条数值变化，包括当前值与总值
         * 
         * @param oldValue
         *            旧数据
         * @param newValue
         *            新数据
         */
        public void valueChanged(int oldValue, int newValue);
    }

    private List<ProgressBarChanged> currentChange = new LinkedList<>();
    private List<ProgressBarChanged> totalChange = new LinkedList<>();
    private int current = 1;
    private int total = 1;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        setCurrent(current,true);
    }

    private void setCurrent(int current, boolean check) {
        if (this.current != current) {
            currentChange.forEach(e -> e.valueChanged(this.current, current));
        }
        this.current = current;
        if(check) {
            check();
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        setTotal(total, true);
    }

    private void setTotal(int total, boolean check) {
        if (this.total != total) {
            totalChange.forEach(e -> e.valueChanged(this.total, total));
        }
        this.total = total;
        if (check) {
            check();
        }
    }

    /**
     * 
     * @param current
     * @param total
     */
    public void setAll(int current, int total) {
        this.setCurrent(current,false);
        this.setTotal(total,false);
        check();
    }

    private void check() {
        if (current < 0 || total < 0 || current > total) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 进度变化监听
     * 
     * @param e
     */
    public void onCurrentChange(ProgressBarChanged e) {
        this.currentChange.add(e);
    }

    /**
     * 总数变化监听
     * 
     * @param e
     */
    public void onTotalChange(ProgressBarChanged e) {
        this.totalChange.add(e);
    }
}
