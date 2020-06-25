package com.cosmintaran.survcalculator.Map.drawable;


import com.cosmintaran.survcalculator.Map.helperClasses.Line2D;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;

public class DrawableLine extends DrawableEntity{

    private Line2D mLine;


    public DrawableLine(Line2D line,String label){
        super(label);
        mLine = line;
    }

    public SrvPoint2D getStartPoint(){ return mLine.getStartPoint(); }
    public SrvPoint2D getEndPoint() {return  mLine.getEndPoint();}


}
