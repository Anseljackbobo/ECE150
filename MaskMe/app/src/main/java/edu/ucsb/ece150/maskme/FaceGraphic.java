/*
 * Copyright (C) The Android Open Source Project
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

package edu.ucsb.ece150.maskme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import edu.ucsb.ece150.maskme.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
public class FaceGraphic extends GraphicOverlay.Graphic {

    //public enum MaskType{
    //    FIRST, SECOND
    //}

    private FaceTrackerActivity.MaskType maskType;
    private volatile Face mFace;
    private Context context;
    //private int maskType = 0;


    FaceGraphic(GraphicOverlay overlay, Context context) {
        super(overlay);
        this.context = context;

    }


    void setMaskType( FaceTrackerActivity.MaskType maskType) {
        this.maskType = maskType;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }
        Bitmap mask;
        if(maskType == maskType.SECOND){
            mask = BitmapFactory.decodeResource(context.getResources(),R.drawable.mask2);
        }else{
            mask = BitmapFactory.decodeResource(context.getResources(),R.drawable.mask1);
        }



        float x = translateX(face.getPosition().x + face.getWidth()/2.0f);
        float y = translateY(face.getPosition().y + face.getHeight()/2.0f);
        float deltaX = scaleX(face.getWidth()/2)*1.4f;
        float deltaY = scaleY(face.getHeight()/2)*1.4f;

        int left = (int)(x-deltaX);
        int top = (int)(y-deltaY);
        int right =(int)(x+deltaX);
        int bottom = (int)(y+deltaY);


        // [TODO] Draw real time masks for a single face
        Rect destBounds = new Rect(left,top, right, bottom);
        canvas.drawBitmap(mask, null, destBounds, null);
    }
}
