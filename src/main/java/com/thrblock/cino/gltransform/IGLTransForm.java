package com.thrblock.cino.gltransform;

public interface IGLTransForm {

    GLTransform removeTransform(int layerIndex);

    GLTransform createTransform();

    GLTransform createTransform(boolean perspective);

    GLTransform createTransform(boolean perspective, double near, double far);

    void addBeforeLayer(GLTransform trans, int layerIndex);

    GLTransform getGLTransform(int layerIndex);

}
