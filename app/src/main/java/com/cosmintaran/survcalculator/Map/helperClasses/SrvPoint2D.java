package com.cosmintaran.survcalculator.Map.helperClasses;

public final class SrvPoint2D {
    public double X = 0.0;
    public double Y = 0.0;
    public double Z = 0.0;

    public SrvPoint2D(double x, double y){
        X = x;
        Y = y;
    }

    public SrvPoint2D(){}

    public double distanceTo(SrvPoint2D pt){
        double deltaX = X - pt.X;
        double deltaY = Y - pt.Y;
        return Math.sqrt((deltaX * deltaX + deltaY * deltaY));
    }


    public vect2D add(SrvPoint2D pt) {
        return new vect2D(pt.X + X , pt.Y + Y);
    }

    public vect2D subtract(SrvPoint2D pt) {
        return new vect2D(pt.X - X , pt.Y - Y);
    }

    public SrvPoint2D add(vect2D pt) {
        return new SrvPoint2D(pt.getX() + X , pt.getY() + Y);
    }

    public SrvPoint2D subtract(vect2D pt) {
        return new SrvPoint2D( X - pt.getX() , Y - pt.getY());
    }
}
