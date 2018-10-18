package com.thrblock.cino.debug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;

public class GLDebugHelper {
    private static final Logger LOG = LoggerFactory.getLogger(GLDebugHelper.class);

    public static void logIfError(GL gl) {
        int error = gl.glGetError();
        if (error != GL.GL_NO_ERROR) {
            String errorCode = Integer.toHexString(error);
            LOG.error("open gl error catched,error code is {}", errorCode);
        }
    }

    private GLDebugHelper() {
    }
}
