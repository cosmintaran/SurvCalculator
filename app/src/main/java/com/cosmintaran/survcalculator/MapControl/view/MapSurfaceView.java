package com.cosmintaran.survcalculator.MapControl.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.cosmintaran.survcalculator.MapControl.model.SceneFrame;

public class MapSurfaceView extends View {

    private SceneFrame _sceneFrame;
    private Matrix _matrix;
    float [] transformMatrixValues = new float[9];
    private ScaleGestureDetector mScaleDetector;
    private float mLastTouchX;
    private float mLastTouchY;
    private static final int INVALID_POINTER_ID = -1;
    // The ‘active pointer’ is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;
    private boolean _wasMovement = false;
    private static final int TOUCH_TOLERANCE_DP = 3;
    private static float mTouchTolerance;


    public MapSurfaceView ( Context context ) {
        super(context);
        init();
    }

    public MapSurfaceView ( Context context , AttributeSet attrs ) {
        super(context , attrs);
        init();
    }

    public MapSurfaceView ( Context context , AttributeSet attrs , int defStyleAttr ) {
        super(context , attrs , defStyleAttr);
        init();
    }

    private void init ( ) {
        _sceneFrame = new SceneFrame(this);
        mScaleDetector =
                new ScaleGestureDetector(getContext() , new MapSurfaceView.ScaleListener());
        _matrix = new Matrix();
        _matrix.reset();
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);
        setLayerType(LAYER_TYPE_HARDWARE,null);
    }


    @Override
    protected void onMeasure ( int widthMeasureSpec , int heightMeasureSpec ) {
        super.onMeasure(widthMeasureSpec , heightMeasureSpec);
    }

    @Override
    protected void onDraw ( Canvas canvas ) {
        super.onDraw(canvas);
        canvas.drawARGB(255 , 156 , 155 , 152);
        canvas.concat(_matrix);
        _sceneFrame.drawOn(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent ( MotionEvent ev ) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mLastTouchX = ev.getX();
                mLastTouchY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    final float dx = x - mLastTouchX;
                    final float dy = y - mLastTouchY;
                    _matrix.postTranslate(dx,dy);
                }
                mLastTouchX = x;
                mLastTouchY = y;
                _wasMovement = true;
                postInvalidate();
                break;
            }

            case MotionEvent.ACTION_UP: {
                if (!_wasMovement) {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    float x = ev.getX(pointerIndex);
                    float y = ev.getY(pointerIndex);

                    _matrix.getValues(transformMatrixValues);

                    float touchX = (x*(1/transformMatrixValues[4]) - (transformMatrixValues[2]/transformMatrixValues[4]));
                    float touchY = (y*(1/transformMatrixValues[4]) -(transformMatrixValues[5]/transformMatrixValues[4]));

                    RectF f = _sceneFrame.selectAt(Math.abs(touchX) , Math.abs(touchY), mTouchTolerance);
                    if (f != null) {
                        //postInvalidate(Math.round(f.left - mTouchTolerance) , Math.round(f.top - mTouchTolerance) , Math.round(f.right + mTouchTolerance) , Math.round(f.bottom + mTouchTolerance));
                        postInvalidate();
                    }
                }
                _wasMovement = false;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }

            default:
                break;
        }
        return true;
    }

    public int dp2px ( int dp ) {
        Resources r = getResources();
        float px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , dp , r.getDisplayMetrics());
        return (int) px;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale ( ScaleGestureDetector detector ) {
            float scaleFactor = detector.getScaleFactor();
            // Don't let the object get too small or too large.
            _matrix.getValues(transformMatrixValues);
            if(transformMatrixValues[Matrix.MSCALE_X] + scaleFactor > 5.0f ){
                scaleFactor = 5.0f;
            }
            else if(transformMatrixValues[Matrix.MSCALE_X] + scaleFactor < 0.1f){
                scaleFactor = 0.1f;
            }
            _matrix.postScale(scaleFactor,scaleFactor,mLastTouchX,mLastTouchY);
            return true;
        }
    }
}
