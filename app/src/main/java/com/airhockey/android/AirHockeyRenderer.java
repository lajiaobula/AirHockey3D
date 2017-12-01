package com.airhockey.android;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.airhockey.android.objects.Mallet;
import com.airhockey.android.objects.Table;
import com.airhockey.android.programs.ColorShaderProgram;
import com.airhockey.android.programs.TextureShaderProgram;
import com.airhockey.android.util.MatrixHelper;
import com.airhockey.android.util.TextureHelper;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by hejiancheng on 2017/9/1.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int BYTES_PER_FLOAT = 4;
//    private final FloatBuffer vertexData;
    private int program;
    private static final String U_COLOR = "u_Color";
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private static final String A_COLOR = "a_Color";
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    private int aColorLocation;
    private static final String U_MATRIX = "u_Matrix";
    private int uMatrixLocation;
    private final float[] modelMatrix = new float[16];
    private final Context context;
    private final float[] projectionMatrix = new float[16];
    private Table table;
    private Mallet mallet;
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    private int texture;

    public AirHockeyRenderer(Context context) {
        this.context = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        /*// 将屏幕背景设置成黑色
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        String vertexShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader
                .readTextFileFromResource(context, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        glUseProgram(program);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_POSITION_LOCATION.
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation,
                POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);

        glEnableVertexAttribArray(aPositionLocation);

        // Bind our data, specified by the variable vertexData, to the vertex
        // attribute at location A_COLOR_LOCATION.
        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation,
                COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);

        glEnableVertexAttribArray(aColorLocation);*/

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        table = new Table();
        mallet = new Mallet();
        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);
        texture= TextureHelper.loadTexture(context,R.mipmap.wenli01);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        glViewport(0, 0, width, height);

        /*
        final float aspectRatio = width > height ?
            (float) width / (float) height :
            (float) height / (float) width;

        if (width > height) {
            // Landscape
            orthoM(projectionMatrix, 0,
                -aspectRatio, aspectRatio,
                -1f, 1f,
                -1f, 1f);
        } else {
            // Portrait or square
            orthoM(projectionMatrix, 0,
                -1f, 1f,
                -aspectRatio, aspectRatio,
                -1f, 1f);
        }
        */
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width
                / (float) height, 1f, 10f);

        /*
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, 0f, 0f, -2f);
        */

        setIdentityM(modelMatrix, 0);

        translateM(modelMatrix, 0, 0f, 0f, -2f);
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
        final float[] temp = new float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        /*// Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);

        // Assign the matrix
        glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);

        // Draw the table.
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // Draw the center dividing line.
        glDrawArrays(GL_LINES, 6, 2);

        // Draw the first mallet.
        glDrawArrays(GL_POINTS, 8, 1);

        // Draw the second mallet.
        glDrawArrays(GL_POINTS, 9, 1);*/
        textureShaderProgram.useProgram();
        textureShaderProgram.setUniforms(projectionMatrix,texture);
        table.bindData(textureShaderProgram);
        table.draw();

        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorShaderProgram);
        mallet.draw();

    }
}
