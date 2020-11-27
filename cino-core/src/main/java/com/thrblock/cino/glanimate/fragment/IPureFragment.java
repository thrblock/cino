package com.thrblock.cino.glanimate.fragment;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

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

    /**
     * 内态值复位
     */
    public default void reset() {
    }

    public default void referance(IPureFragment ref) {
    }

    public default AndThenFragment andThen(IPureFragment then) {
        AndThenFragment result = new AndThenFragment(this, then);
        referance(result);
        then.referance(result);
        return result;
    }

    public default AndThenFragment andAfter(IPureFragment prev) {
        AndThenFragment result = new AndThenFragment(prev, this);
        referance(result);
        prev.referance(result);
        return result;
    }

    public default ConditionFragment withCondition(BooleanSupplier condition) {
        ConditionFragment result = new ConditionFragment(condition, this, null);
        referance(result);
        return result;
    }

    public default ConditionFragment withConditionOrElse(BooleanSupplier condition, IPureFragment elseDo) {
        ConditionFragment result = new ConditionFragment(condition, this, elseDo);
        referance(result);
        elseDo.referance(result);
        return result;
    }

    public default ConditionFragment asElse(BooleanSupplier condition, IPureFragment then) {
        ConditionFragment result = new ConditionFragment(condition, then, this);
        referance(result);
        then.referance(result);
        return result;
    }
    
    public default OnceFragment runOnece() {
        OnceFragment result = new OnceFragment(this);
        referance(result);
        return result;
    }

    /**
     * 使逻辑以指定的间隔执行 相当于跳帧操作
     * 
     * @param delay 要跳的帧数
     * @return
     */
    public default DelayFragment delay(int delay) {
        DelayFragment result = new DelayFragment(delay, this);
        referance(result);
        return result;
    }

    public default SwitchFragment wrapSwitch() {
        SwitchFragment result = new SwitchFragment(this);
        referance(result);
        return result;
    }

    public default IPureFragment rootReferance() {
        IPureFragment current = this;
        while (AbstractFragment.class.isInstance(current)) {
            IPureFragment next = AbstractFragment.class.cast(current).ref;
            if(next != null) {
                current = next;
            } else {
                return current;
            }
        }
        return current;
    }
    
    public default IPureFragment withSelfOperation(Consumer<IPureFragment> cons) {
        AbstractFragment result =  new AbstractFragment() {
            @Override
            public void fragment() {
                IPureFragment.this.fragment();
                cons.accept(IPureFragment.this);
            }
        };
        result.referance(this);
        return result;
    }
    
    public default IPureFragment withRootOperation(Consumer<IPureFragment> cons) {
        AbstractFragment result = new AbstractFragment() {
            @Override
            public void fragment() {
                IPureFragment.this.fragment();
                cons.accept(rootReferance());
            }
        };
        result.referance(this);
        return result;
    }

    public static IPureFragment of(Runnable r) {
        return new AbstractFragment() {
            @Override
            public void fragment() {
                r.run();
            }
        };
    }
}
