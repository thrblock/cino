package com.thrblock.cino.lintersection;

import com.thrblock.cino.function.FloatSupplier;

public class SupplierVector extends AbstractVector{
    private FloatSupplier sx;
    private FloatSupplier sy;
    private FloatSupplier ex;
    private FloatSupplier ey;
    public SupplierVector(FloatSupplier sx,FloatSupplier sy,FloatSupplier ex,FloatSupplier ey) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
    }
    @Override
    public float getStartX() {
        return sx.get();
    }

    @Override
    public float getStartY() {
        return sy.get();
    }

    @Override
    public float getEndX() {
        return ex.get();
    }

    @Override
    public float getEndY() {
        return ey.get();
    }

}
