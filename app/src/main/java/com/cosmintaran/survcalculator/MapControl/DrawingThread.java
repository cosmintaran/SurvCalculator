package com.cosmintaran.survcalculator.MapControl;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.cosmintaran.survcalculator.MapControl.model.SceneFrame;

public class DrawingThread  extends Thread{

    private SceneFrame _sceneFrame;

    private SurfaceHolder surfaceHolder;
    private boolean isRunning = false;
    private long previousTime;
    private final int fps = 10;

    public DrawingThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        previousTime = System.currentTimeMillis();
    }

    public void setRunning(boolean run) {
        isRunning = run;
    }


    @Override
    public void run() {

        Canvas canvas = null;
        while (isRunning) {

            long currentTimeMillis = System.currentTimeMillis();
            long elapsedTimeMs = currentTimeMillis - previousTime;
            long sleepTimeMs = (long) (1000f/ fps - elapsedTimeMs);


            try {

                canvas = surfaceHolder.lockCanvas();

                if (canvas == null) {
                    Thread.sleep(1);

                    continue;

                }else if (sleepTimeMs > 0){

                    Thread.sleep(sleepTimeMs);

                }

                synchronized (surfaceHolder) {
                    _sceneFrame.drawOn(canvas);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    previousTime = System.currentTimeMillis();
                }
            }
        }
    }

    public void setSceneFrame ( SceneFrame sceneFrame) {
        synchronized (_sceneFrame){
        this._sceneFrame = sceneFrame;
        }
    }

}
