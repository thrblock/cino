package com.thrblock.cino.glanimate;

import java.util.function.BooleanSupplier;

/**
 * 一个纯粹的片段逻辑 函数式接口<br />
 * 本来等同于Runnable 但是使用Runnable会产生异步相关的不必要的联想<br />
 * 后来增加的default方法<br />
 * @author lizepu
 */
@FunctionalInterface
public interface IPureFragment {
    static class DelayFragment implements IPureFragment {
        private int count;
        private int countReg = 0;
        private IPureFragment pure;
        public DelayFragment(int count,IPureFragment pure) {
            this.count = count;
            this.pure = pure;
        }
        
        @Override
        public void fragment() {
            countReg ++;
            if(countReg >= count) {
                pure.fragment();
                countReg = 0;
            }
        }
        
    }
    /**
     * 执行逻辑内容
     */
    public void fragment();

    public default IPureFragment mergeCondition(BooleanSupplier condition) {
        return () -> {
            if (condition.getAsBoolean()) {
                fragment();
            }
        };
    }
    
    /**
     * 使逻辑以指定的间隔执行 相当于跳帧操作
     * @param delay 要跳的帧数
     * @return
     */
    public default IPureFragment mergeDelay(int delay) {
        return new DelayFragment(delay,this);
    }
}
