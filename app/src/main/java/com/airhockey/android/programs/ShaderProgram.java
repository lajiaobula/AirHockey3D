package com.airhockey.android.programs;

import android.content.Context;

import com.airhockey.android.TextResourceReader;
import com.airhockey.android.util.ShaderHelper;
import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;
/**
 * Created by hejiancheng on 2017/12/1.
 */

public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT="u_TextureUnit";
    protected static final String A_POSITION ="a_Positon";
    protected static final String A_COLOR ="a_Color";
    protected static final String A_TEXTURE_COORDINATES="a_TextureCoordinates";
    protected final int program;

    protected ShaderProgram(Context context, int verShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, verShaderResourceId), TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
    }
    public void useProgram(){
        glUseProgram(program);
    }

}
