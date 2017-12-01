package com.airhockey.android.data;

import android.opengl.GLES20;

import com.airhockey.android.Constants;

import static android.opengl.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by hejiancheng on 2017/12/1.
 */

public class VertexArray {
    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData) {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    public void setVertexAttribPointer(int dataOffset, int attributeLocation, int componnentCount, int stride) {
        floatBuffer.position(dataOffset);
        glVertexAttribPointer(attributeLocation, componnentCount, GL_FLOAT, false, stride, floatBuffer);
        glEnableVertexAttribArray(attributeLocation);
        floatBuffer.position(0);
    }
}
