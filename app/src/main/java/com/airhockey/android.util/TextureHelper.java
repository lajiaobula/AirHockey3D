package com.airhockey.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static android.opengl.GLES20.*;
import static android.opengl.Matrix.*;
import static android.opengl.GLUtils.*;


/**
 * Created by hejiancheng on 2017/9/19.
 */

public class TextureHelper {
    public static final String TAG = "TextureHelper";

    public static int loadTexture(Context context, int resId) {
        final int[] textureObjIds = new int[1];
        glGenTextures(1, textureObjIds, 0);
        if (textureObjIds[0] == 0) {
            if (LoggerConfig.ON) {
                Log.d(TAG, "Could not generate a new Opengl Texture object");
            }
            return 0;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        if (bitmap == null) {
            if (LoggerConfig.ON) {
                Log.d(TAG, "Resources ID " + resId + " could not be decode");
            }
            glDeleteTextures(1, textureObjIds, 0);
            return 0;
        }

        glBindTexture(GL_TEXTURE_2D, textureObjIds[0]);
// 设置默认纹理过滤参数
        // 缩小的情况
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        // 放大的情况
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        // 立即释放位图
        bitmap.recycle();
        glGenerateMipmap(GL_TEXTURE_2D);
        // 解除与纹理对象的绑定
        glBindTexture(GL_TEXTURE_2D, 0);
        return textureObjIds[0];
    }
}
