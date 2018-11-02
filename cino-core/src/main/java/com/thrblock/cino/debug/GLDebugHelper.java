package com.thrblock.cino.debug;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;

public class GLDebugHelper {

    private static final Logger LOG = LoggerFactory.getLogger(GLDebugHelper.class);

    private static boolean debug = false;
    private static int[] version = { 2, 0 };

    public static void enable(int[] v) {
        debug = true;
        System.arraycopy(v, 0, version, 0, version.length);
    }

    public static void logIfError(GL gl) {
        logIfError(gl, null);
    }

    public static void logIfError(GL gl, Object refdata) {
        if (!debug) {
            return;
        }
        int error = gl.glGetError();
        if (error != GL.GL_NO_ERROR) {
            String errorCode = Integer.toHexString(error);
            if (refdata != null) {
                LOG.error("open gl error catched,error code is {} detial data:{}", errorCode, refdata);
            } else {
                LOG.error("open gl error catched,error code is {} no detial data found", errorCode);
            }
            if (version[0] * 10 + version[1] >= 43) {
                String messageLog = getMessageLog(gl.getGL4());
                LOG.error("gl message log catched:{}", messageLog);
            }
        }
    }

    private static String getMessageLog(GL4 gl4) {
        int count = 1;
        int buffSize = 1024 * 16;
        IntBuffer sources = IntBuffer.allocate(count);
        IntBuffer types = IntBuffer.allocate(count);
        IntBuffer ids = IntBuffer.allocate(count);
        IntBuffer severities = IntBuffer.allocate(count);
        IntBuffer lengths = IntBuffer.allocate(count);
        ByteBuffer messageLog = ByteBuffer.allocate(buffSize);
        gl4.glGetDebugMessageLog(count, buffSize, sources, types, ids, severities, lengths, messageLog);
        return fromBuffer(messageLog);
    }
    
    private static String fromBuffer(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            return new String(byteBuffer.array(),
                byteBuffer.arrayOffset() + byteBuffer.position(),
                byteBuffer.remaining());
        } else {
            final byte[] b = new byte[byteBuffer.remaining()];
            byteBuffer.duplicate().get(b);
            return new String(b);
        }
    }

    private GLDebugHelper() {
    }
}
