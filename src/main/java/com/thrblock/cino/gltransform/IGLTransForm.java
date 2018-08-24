package com.thrblock.cino.gltransform;

public interface IGLTransForm {

    GLTransform removeTransform(int layerIndex);

    GLTransform createTransform();

    void addBeforeLayer(GLTransform trans, int layerIndex);

    GLTransform getGLTransform(int layerIndex);

}
