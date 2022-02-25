package com.example.hakaton2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    public DrawView(Context context){
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.requestStop();
        boolean retry = true;
        while(retry){
            try {
                drawThread.join();
                retry = false;
            }catch (InterruptedException e){

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        drawThread.click();
        return false;
    }
}
