/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ogl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

class GLES20TriangleRenderer implements GLSurfaceView.Renderer {

    public GLES20TriangleRenderer(Context context) {
        mContext = context;
        mTriangleVertices = ByteBuffer.allocateDirect(mTriangleVerticesData.length
                * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTriangleVertices.put(mTriangleVerticesData).position(0);
    }

    public void onDrawFrame(GL10 glUnused) {
        // Ignore the passed-in GL10 interface, and use the GLES20
        // class's static methods instead.
    	
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(mProgram);
        checkGlError("glUseProgram");

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);
        GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgram, "sTexture"), 0);
        //GLES20.glUniform1i(location, x)
        
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID2);
        GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgram, "sTexture2"), 1);

        mTriangleVertices.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false,
                TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        
        mTriangleVertices.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false,
                TRIANGLE_VERTICES_DATA_STRIDE_BYTES, mTriangleVertices);
        GLES20.glEnableVertexAttribArray(maTextureHandle);

//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
//        Matrix.setRotateM(mMMatrix, 0, angle, 0, 0, 1.0f);
//        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
//        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

//        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
//        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, 4, GLES20.GL_UNSIGNED_BYTE, mTriangleVertices);
        checkGlError("glDrawArrays");
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        // Ignore the passed-in GL10 interface, and use the GLES20
        // class's static methods instead.
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
//        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Ignore the passed-in GL10 interface, and use the GLES20
        // class's static methods instead.
        mProgram = createProgram(mVertexShader, mFragmentShader);
        if (mProgram == 0) {
            return;
        }
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        checkGlError("glGetAttribLocation aPosition");
        if (maPositionHandle == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        maTextureHandle = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
        checkGlError("glGetAttribLocation aTextureCoord");
        if (maTextureHandle == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }

//        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
//        checkGlError("glGetUniformLocation uMVPMatrix");
//        if (muMVPMatrixHandle == -1) {
//            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
//        }

        /*
         * Create our texture. This has to be done each time the
         * surface is created.
         */
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
//                GLES20.GL_LINEAR);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
//                GLES20.GL_LINEAR);
//
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
//                GLES20.GL_CLAMP_TO_EDGE);
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
//                GLES20.GL_CLAMP_TO_EDGE);

        
        int[] textures = new int[2];
        GLES20.glGenTextures(2, textures, 0);
        
        mTextureID = textures[0];
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID);
        
     // parameters
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
				GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER,
				GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);
		
        InputStream is = mContext.getResources()
            .openRawResource(R.raw.psb2);
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch(IOException e) {
                // Ignore.
            }
        }

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        bitmap = null;

        // --------------------------------------------------------
        mTextureID2 = textures[1];
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID2);
        
     // parameters
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
				GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
				GLES20.GL_TEXTURE_MAG_FILTER,
				GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_REPEAT);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_REPEAT);
		
        InputStream is2 = mContext.getResources()
            .openRawResource(R.raw.rc);
        Bitmap bitmap2;
        try {
            bitmap2 = BitmapFactory.decodeStream(is2);
        } finally {
            try {
                is2.close();
            } catch(IOException e) {
                // Ignore.
            }
        }
        
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap2, 0);
        bitmap2.recycle();
        bitmap2 = null;
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureID2);
//        
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
//                GLES20.GL_NEAREST);
//        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
//                GLES20.GL_TEXTURE_MAG_FILTER,
//                GLES20.GL_LINEAR);
//
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
//                GLES20.GL_REPEAT);
//        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
//                GLES20.GL_REPEAT);
//        
//        is = mContext.getResources()
//                .openRawResource(R.raw);
//            Bitmap bitmap;
//            try {
//                bitmap = BitmapFactory.decodeStream(is);
//            } finally {
//                try {
//                    is.close();
//                } catch(IOException e) {
//                    // Ignore.
//                }
//            }
//        Matrix.setLookAtM(mVMatrix, 0, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    private static final int FLOAT_SIZE_BYTES = 4;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 5 * FLOAT_SIZE_BYTES;
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    private final float[] mTriangleVerticesData = {
            // X,     Y,   Z,             U, V
            -1.0f, -1.0f,   0,         0.0f, 1.0f,
            -1.0f,  1.0f,   0,         0.0f, 0.0f,
             1.0f, -1.0f,   0,         1.0f, 1.0f,
             1.0f,  1.0f,   0,    	   1.0f, 0.0f};//0.5f,  1.61803399f };

    private FloatBuffer mTriangleVertices;

    private final String mVertexShader =
        "uniform mat4 uMVPMatrix;\n" +
        "attribute vec4 aPosition;\n" +
        "attribute vec2 aTextureCoord;\n" +
        "varying vec2 vTextureCoord;\n" +
        "void main() {\n" +
        "  gl_Position = aPosition;\n" +
        "  vTextureCoord = aTextureCoord;\n" +
        "}\n";

    private final String mFragmentShader =
//    	"precision mediump float;\n" +
//    	"uniform sampler2D sTexture;\n" +
//        "varying vec2 vTextureCoord;\n" +
//        "void main() {\n" +
//        "  const vec2 lo = vec2(0.0, 0.0);\n" +
//        "  const vec2 hi = vec2(1.0, 1.0);\n" +
//        "  const vec4 black = vec4(0.0, 0.0, 0.0, 1.0);\n" +
//        "  bool out_of_bounds =\n" +
//        "    any(lessThan(vTextureCoord, lo)) ||\n" +
//        "    any(greaterThan(vTextureCoord, hi));\n" +
//        "  if (out_of_bounds) {\n" +
//        "    gl_FragColor = black;\n" +
//        "  } else {\n" +
//        "    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
//        "  }\n" +
//        "}\n";
    
    		// toGray
//        "precision mediump float;\n" +
//        "varying vec2 vTextureCoord;\n" +
//        "uniform sampler2D sTexture;\n" +
//        "uniform sampler2D sTexture2;\n" + 
//        "void main() {\n" +
//        "  vec4 color = texture2D(sTexture, vTextureCoord);\n" +
//        "  float y = dot(color, vec4(0.299, 0.587, 0.114, 0));\n" +
//        "  gl_FragColor = vec4(y, y, y, 1.0);\n" + //texture2D(sTexture, vTextureCoord);\n" +
//        "}\n";
    
//    "precision mediump float;\n" +
//    "uniform sampler2D sTexture;\n" +
//    "uniform sampler2D sTexture2;\n" +
//    "varying vec2 vTextureCoord;\n" +
//    "void main() {\n" +
//    "  const float range = 0.2;\n" +
//    "  const float inv_max_dist = 222.0;\n" +
//    "  const float shade = 0.3;\n" +
//    "  const float slope = 1.0;\n" +
//    "  vec2 scale = vec2(0.3, 0.7);\n" +
//    "  vec2 coord = vTextureCoord - vec2(0.5, 0.5);\n" +
//    "  float dist = length(coord * scale);\n" +
//    "  float lumen = shade / (1.0 + exp((dist * inv_max_dist - range) * slope)) + (1.0 - shade);\n" +
//    "  vec4 color = texture2D(sTexture, vTextureCoord);\n" +
//    "  gl_FragColor = vec4(color.rgb * lumen, 1.0);\n" +
//    "}\n";
    		
    		// fisheye
    		"precision mediump float;\n" +
            "uniform sampler2D sTexture;\n" +
            "varying vec2 vTextureCoord;\n" +
            "void main() {\n" +
            "  vec2 scale = vec2(0.75, 0.1);\n" +
            "  float alpha = 0.45;\n" +
            "  float radius2 = 9.90;\n" +
            "  float factor = 2.1;\n" +
            "  const float m_pi_2 = 1.570963;\n" +
            "  vec2 coord = vTextureCoord - vec2(0.5, 0.5);\n" +
            "  float dist = length(coord * scale);\n" +
            "  float radian = m_pi_2 - atan(alpha * sqrt(radius2 - dist * dist), dist);\n" +
            "  float scalar = radian * factor / dist;\n" +
            "  vec2 new_coord = coord * scalar + vec2(0.5, 0.5);\n" +
            "  gl_FragColor = texture2D(sTexture, new_coord);\n" +
            "}\n";
        //"  color.r = color.r * 0.5 + color2.r * 0.5;\n" + 
        //"  color.g = color.g * 0.5 + color2.g * 0.5;\n" + 
        //"  color.b = color.b * 0.5 + color2.b * 0.5;\n" + 
        //"  color = vec3(" +
        //"		  (color2.r, vec2(color.r, 0.86666)).r);\n" +
        //"         (color2.g, vec2(color.g, 0.5)).g," +
        //"         (color2.b, vec2(color.b, 0.83333)).b);" +
//        "  color = vec3(dot(vec3(0.3, 0.6, 0.1), color));\n" +
//        "  color = vec3(texture2D(sTexture2, vec2(color.r, 0.16666)).r);\n" +
//        "  if(color.r > 255) color.r = 255;\n" +
//        "  if(color.r < 0) color.r = 0;\n" +
//        "  if(color.g > 255) color.r = 255;\n" +
//        "  if(color.g < 0) color.r = 0;\n" + 
//        "  if(color.b > 255) color.r = 255;\n" +
//        "  if(color.b < 0) color.r = 0;\n" + 
        
//    private float[] mMVPMatrix = new float[16];
//    private float[] mProjMatrix = new float[16];
//    private float[] mMMatrix = new float[16];
//    private float[] mVMatrix = new float[16];

    private int mProgram;
    private int mTextureID;
    private int mTextureID2;
//    private int muMVPMatrixHandle;
    private int maPositionHandle;
    private int maTextureHandle;

    private Context mContext;
    private static String TAG = "GLES20TriangleRenderer";
}
