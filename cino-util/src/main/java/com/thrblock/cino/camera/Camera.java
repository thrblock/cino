package com.thrblock.cino.camera;

import com.thrblock.cino.function.FloatSupplier;
import com.thrblock.cino.gltransform.GLTransform;
import com.thrblock.cino.util.math.CMath;

public class Camera {
    private FloatSupplier dstX = () -> 0;
    private FloatSupplier dstY = () -> 0;

    private float focusSpeed = 15.0f;
    private float smoothRange = focusSpeed * 5;
    private float speedMini = focusSpeed * 0.2f;

    private GLTransform transform;

    public Camera(GLTransform transform) {
        this.transform = transform;
    }

    public float getFocusSpeed() {
        return focusSpeed;
    }

    public void setFocusSpeed(float focusSpeed) {
        this.focusSpeed = focusSpeed;
    }

    public float getSmoothRange() {
        return smoothRange;
    }

    public void setSmoothRange(float smoothRange) {
        this.smoothRange = smoothRange;
    }

    public float getSpeedMini() {
        return speedMini;
    }

    public void setSpeedMini(float speedMini) {
        this.speedMini = speedMini;
    }

    public void bind(FloatSupplier dstX, FloatSupplier dstY) {
        this.dstX = dstX;
        this.dstY = dstY;
    }

    public boolean focusSmoothly() {
        return focusLayerSmoothly();
    }

    private boolean focusLayerSmoothly() {
        float aimX = dstX.get();
        float aimY = dstY.get();
        float currentFocusX = -transform.getTranslateX();
        float currentFocusY = -transform.getTranslateY();
        float dist = CMath.getDistance(currentFocusX, currentFocusY, aimX, aimY);
        float spd;
        if (dist < smoothRange) {
            spd = dist * focusSpeed / smoothRange;
            if (spd <= speedMini) {
                spd = speedMini;
            }
        } else {
            spd = focusSpeed;
        }
        if (dist > speedMini) {
            float theta = CMath.getQuadrantTheta(currentFocusX, currentFocusY, aimX, aimY);
            float sinV = (float) Math.sin(theta);
            float cosV = (float) Math.cos(theta);
            transform.setTranslateX(-(currentFocusX + spd * cosV));
            transform.setTranslateY(-(currentFocusY + spd * sinV));
            return false;
        } else {
            transform.setTranslateX(-aimX);
            transform.setTranslateY(-aimY);
            return true;
        }
    }

    public boolean focusWithFixSpeed() {
        return focusLayerWithFixSpeed();
    }

    private boolean focusLayerWithFixSpeed() {
        float currentFocusX = -transform.getTranslateX();
        float currentFocusY = -transform.getTranslateY();
        float aimX = dstX.get();
        float aimY = dstY.get();
        float theta = CMath.getQuadrantTheta(currentFocusX, currentFocusY, aimX, aimY);
        float dst = CMath.getDistance(currentFocusX, currentFocusY, aimX, aimY);
        if (dst < focusSpeed) {
            transform.setTranslateX(-aimX);
            transform.setTranslateY(-aimY);
            return true;
        } else {
            float sinV = (float) Math.sin(theta);
            float cosV = (float) Math.cos(theta);
            transform.setTranslateX(-(currentFocusX + focusSpeed * cosV));
            transform.setTranslateY(-(currentFocusY + focusSpeed * sinV));
            return false;
        }
    }

    public void clearOffset() {
        transform.setTranslateX(0);
        transform.setTranslateY(0);
    }

    public void focusOn(float x, float y) {
        transform.setTranslateX(-x);
        transform.setTranslateY(-y);
    }

}