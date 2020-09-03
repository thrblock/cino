package com.thrblock.cino.glanimate.fragment;

import java.util.function.BooleanSupplier;

/**
 * 一个纯粹的片段逻辑 函数式接口<br />
 * 本来等同于Runnable 但是使用Runnable会产生异步相关的不必要的联想<br />
 * 后来增加的default方法<br />
 * 
 * @author lizepu
 */
@FunctionalInterface
public interface IPureFragment {
    /**
     * 执行逻辑内容
     */
    public void fragment();

    public default void reset() {
    }

    public default AndThenFragment andThen(IPureFragment then) {
        return new AndThenFragment(this, then);
    }

    public default AndThenFragment asThen(IPureFragment prev) {
        return new AndThenFragment(prev, this);
    }

    public default ConditionFragment whenThen(BooleanSupplier condition) {
        return new ConditionFragment(condition, this, null);
    }

    public default ConditionFragment whenThenElse(BooleanSupplier condition, IPureFragment elseDo) {
        return new ConditionFragment(condition, this, elseDo);
    }

    public default ConditionFragment whenElseThen(BooleanSupplier condition, IPureFragment then) {
        return new ConditionFragment(condition, then, this);
    }

    public default OnceFragment runOnece() {
        return new OnceFragment(this);
    }

    /**
     * 使逻辑以指定的间隔执行 相当于跳帧操作
     * 
     * @param delay 要跳的帧数
     * @return
     */
    public default DelayFragment delay(int delay) {
        return new DelayFragment(delay, this);
    }

    public default SwitchFragment wrapSwitch() {
        return new SwitchFragment(this);
    }

    public static IPureFragment of(Runnable r) {
        return r::run;
    }
}
