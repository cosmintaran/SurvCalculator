package com.cosmintaran.survcalculator.Map.drawable;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;

public class DrawablePoint extends DrawableEntity {

    private SrvPoint2D mPoint;

    public DrawablePoint(SrvPoint2D pt, String label){
        super(label);
        mPoint = pt;
    }

    public double getX(){ return (float)mPoint.X;}
    public double getY(){ return (float)mPoint.Y;}

}
