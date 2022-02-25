package com.example.hakaton2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
    private SurfaceHolder surfaceHolder;

    private volatile boolean running = true;

    public DrawThread(Context context, SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop(){
        running = false;
    }

    private boolean change = false;
    private boolean firstTime = true;

    public void click(){
        change = !change;
        firstTime = true;
    }

    @Override
    public void run() {
        while(running){
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null){
                try{
                    int radius = 100;
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(0, 0, canvas.getHeight(), canvas.getHeight(), paint);
                    
                    paint.setColor(Color.GREEN);
                    canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2 - 300, radius, paint);
                    if(change){
                        if(firstTime) {
                            sleep(1000);
                            paint.setColor(Color.BLACK);
                            paint.setTextSize(50);
                            canvas.drawText("1", canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            surfaceHolder.lockCanvas();
                            sleep(1000);
                            canvas.drawText("2", canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                            surfaceHolder.unlockCanvasAndPost(canvas);
                            surfaceHolder.lockCanvas();
                            sleep(1000);
                            canvas.drawText("3", canvas.getWidth() / 2, canvas.getHeight() / 2, paint);
                            firstTime = false;
                        }
                        paint.setColor(Color.RED);
                        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2 - 300, radius, paint);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
