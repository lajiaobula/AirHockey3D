package com.airhockey.android.objects;

import com.airhockey.android.Constants;
import com.airhockey.android.data.VertexArray;
import com.airhockey.android.programs.ColorShaderProgram;
import com.airhockey.android.programs.TextureShaderProgram;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;

/**
 * Created by hejiancheng on 2017/12/1.
 */

public class Mallet {
    private static final int POSTITION_COMPONENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSTITION_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    private static final float[] VERTEX_DATA = {
            0f, -0.4f, 0f, 0f, 1f,
            0f, 0.4f, 1f, 0f, 0f
    };
    private final VertexArray vertexArray;

    public Mallet() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(), POSTITION_COMPONENT_COUNT, STRIDE);
        vertexArray.setVertexAttribPointer(POSTITION_COMPONENT_COUNT, colorShaderProgram.getColorAttributeLocation(), COLOR_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, 2);
    }
}
