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

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ConfigurationInfo;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * This sample shows how to check for OpenGL ES 2.0 support at runtime, and then
 * use either OpenGL ES 1.0 or OpenGL ES 2.0, as appropriate.
 */
public class MainActivity extends Activity implements OnClickListener {
	private ImageView mView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.surfView);//new GLSurfaceView(this);
        mGLSurfaceView.setOnClickListener(this);
        mView = (ImageView) findViewById(R.id.imgView);
        mView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.raw.psb2)));
        mView.setOnClickListener(this);
        
        if (detectOpenGLES20()) {
            // Tell the surface view we want to create an OpenGL ES 2.0-compatible
            // context, and set an OpenGL ES 2.0-compatible renderer.
            mGLSurfaceView.setEGLContextClientVersion(2);
            mGLSurfaceView.setRenderer(new GLES20TriangleRenderer(this));
        } else {
            // Set an OpenGL ES 1.x-compatible renderer. In a real application
            // this renderer might approximate the same output as the 2.0 renderer.
            //mGLSurfaceView.setRenderer(new TriangleRenderer(this));
        }
        //setContentView(mGLSurfaceView);
    }

    private boolean detectOpenGLES20() {
        ActivityManager am =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }

    @Override
    protected void onResume() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        // Ideally a game should implement onResume() and onPause()
        // to take appropriate action when the activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private GLSurfaceView mGLSurfaceView;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgView: {
			mGLSurfaceView.setVisibility(View.VISIBLE);
			break;
		}
		
		case R.id.surfView: {
			mGLSurfaceView.setVisibility(View.INVISIBLE);
			break;
		}
		}
	}

}
