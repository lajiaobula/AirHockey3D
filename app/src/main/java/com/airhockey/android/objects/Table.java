package com.airhockey.android.objects;

import com.airhockey.android.Constants;
import com.airhockey.android.data.VertexArray;
import com.airhockey.android.programs.TextureShaderProgram;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;

/**
 * Created by hejiancheng on 2017/12/1.
 */

public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    private final VertexArray vertexArray;
    private static final float[] VERTEX_DATA = {
            // order of coordinates: x ,y ,z

            // triangle fan
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    public Table() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureShaderProgram) {
        vertexArray.setVertexAttribPointer(0, textureShaderProgram.getPositionAttributeLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        vertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT, textureShaderProgram.getTextureCoorindatesAttribureLocation(),TEXTURE_COORDINATES_COMPONENT_COUNT,STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
    }
}
