package com.thrblock.cino.util.math;

import com.thrblock.cino.function.FloatUnaryOperator;

/**
 * 使用定义域限制一个（连续）函数<br />
 * 注意函数定义域中可能存在的的断开区间以及在断开区间的极限值，不建议使用此类函数（如在1/x中使用［－1，1］）
 * @author user
 *
 */
public class LimitedFloatFunction implements FloatUnaryOperator {
    private float domainMin;
    private float domainMax;
    private FloatUnaryOperator func;
    /**
     * 构造一个限制函数
     * @param ffunction 非限制函数
     * @param domainMin 最小定义域（包含）
     * @param domainMax 最大定义域（包含）
     */
    public LimitedFloatFunction(FloatUnaryOperator ffunction,float domainMin,float domainMax) {
        this.domainMin = domainMin;
        this.domainMax = domainMax;
        func = ffunction;
    }
    
    @Override
    public float applyAsFloat(float v) {
        if(v < domainMin ||v > domainMax) {
            throw new IllegalArgumentException("input out of domain:" + v);
        }
        return func.applyAsFloat(v);
    }

    /**
     * 获得定义域最小值
     * @return
     */
    public float getDomainMin() {
        return domainMin;
    }

    /**
     * 获得定义域最大值
     * @return
     */
    public float getDomainMax() {
        return domainMax;
    }
    
}
