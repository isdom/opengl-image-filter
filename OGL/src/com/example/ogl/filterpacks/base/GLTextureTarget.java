/*
 * Copyright (C) 2011 The Android Open Source Project
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


package com.example.ogl.filterpacks.base;

import com.example.ogl.filterfw.core.Filter;
import com.example.ogl.filterfw.core.FilterContext;
import com.example.ogl.filterfw.core.Frame;
import com.example.ogl.filterfw.core.FrameFormat;
import com.example.ogl.filterfw.core.GLFrame;
import com.example.ogl.filterfw.core.GenerateFieldPort;
import com.example.ogl.filterfw.format.ImageFormat;

public class GLTextureTarget extends Filter {

    @GenerateFieldPort(name = "texId")
    private int mTexId;

    public GLTextureTarget(String name) {
        super(name);
    }

    @Override
    public void setupPorts() {
        addMaskedInputPort("frame", ImageFormat.create(ImageFormat.COLORSPACE_RGBA));
    }

    @Override
    public void process(FilterContext context) {
        // Get input frame
        Frame input = pullInput("frame");

        FrameFormat format = ImageFormat.create(input.getFormat().getWidth(),
                                                input.getFormat().getHeight(),
                                                ImageFormat.COLORSPACE_RGBA,
                                                FrameFormat.TARGET_GPU);

        Frame frame = context.getFrameManager().newBoundFrame(format, GLFrame.EXISTING_TEXTURE_BINDING, mTexId);

        // Copy to our texture frame
        frame.setDataFromFrame(input);
        frame.release();
    }
}
