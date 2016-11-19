package com.thrblock.cino.util.math;

import com.thrblock.cino.function.FloatUnaryOperator;

/**
 * @author user
 * 
 */
public class LimitedFloatProjector implements FloatUnaryOperator {
    private LimitedFloatFunction func;
    private float fmin = Float.MAX_VALUE;
    private float fmax = Float.MIN_VALUE;
    
    private float rangeAlpha = 1.0f;
    private float rangeMin = 0f;
    private boolean projectRangeMX = false;
    
    private boolean projectRangeScale = false;
    private float scaleAlpha = 1.0f;
    
    private float domainAlpha = 1.0f;
    private float domainMin;
    private boolean projectDomain = false;
    /**
     * @param lfc
     */
    public LimitedFloatProjector(LimitedFloatFunction lfc) {
        this.func = lfc;
    }
    /**
     * 值域线性投影
     * @param min
     * @param max
     * @param detStep
     */
    public void rangeAt(float min,float max,float detStep) {
        if(min >= max) {
            throw new IllegalArgumentException("min must < max");
        }
        this.fmin = Float.MAX_VALUE;
        this.fmax = Float.MIN_VALUE;
        for(float i = func.getDomainMin();i < func.getDomainMax();i += detStep) {
            float v = func.applyAsFloat(i);
            if(v > fmax) {
                fmax = v;
            }
            if(v < fmin) {
                fmin = v;
            }
        }
        this.rangeAlpha = (max - min)/(fmax - fmin);
        this.rangeMin = min;
        projectRangeMX = true;
    }
    
    /**值域线性投影，并使用100个点进行值域探测
     * @param min 投影最小值
     * @param max 投影最大值
     */
    public void rangeAt(float min,float max) {
        rangeAt(min,max,(max - min) / 100);
    }
    
    public void rangeScale(float times) {
        this.projectRangeScale = true;
        this.scaleAlpha = times;
    }
    
    public void domainAt(float min,float max) {
        this.projectDomain = true;
        this.domainMin = min;
        this.domainAlpha = (max - min)/(func.getDomainMax() - func.getDomainMin());
    }
    
    @Override
    public float applyAsFloat(float v) {
        float vAlpha;
        if(projectDomain) {
            vAlpha = (v - func.getDomainMin()) / domainAlpha + domainMin;
        } else {
            vAlpha = v;
        }
        if(projectRangeMX) {
            return (func.applyAsFloat(vAlpha) - fmin) * rangeAlpha + rangeMin;
        } else if(projectRangeScale) {
            return func.applyAsFloat(vAlpha) * scaleAlpha;
        } else {
            return func.applyAsFloat(vAlpha);
        }
    }
}
