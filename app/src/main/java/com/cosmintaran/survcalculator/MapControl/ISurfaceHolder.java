package com.cosmintaran.survcalculator.MapControl;

import android.graphics.Canvas;

public interface ISurfaceHolder {

    void unlockCanvasAndPost(Canvas canvas);
    Canvas lockCanvas();
}
