package com.cosmintaran.survcalculator.Map.helperClasses;

public final class Line2D {

    private SrvPoint2D startPoint;
    private SrvPoint2D endPoint;
    private double length;
    private double slope;
    private double intercept;


    public Line2D(SrvPoint2D startPoint, SrvPoint2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        length = Double.NEGATIVE_INFINITY;
        slope = Double.NEGATIVE_INFINITY;
        intercept = Double.NEGATIVE_INFINITY;
    }

    public double getLength() {
        if (length == Double.NEGATIVE_INFINITY) {
            double deltaX = startPoint.X - endPoint.X;
            double deltaY = startPoint.Y - endPoint.Y;
            length = Math.sqrt((deltaX * deltaX + deltaY * deltaY));
        }
        return length;
    }

    public double getSlope() {

        if (slope == Double.NEGATIVE_INFINITY) {
            double deltaX = startPoint.X - endPoint.X;
            double deltaY = startPoint.Y - endPoint.Y;
            slope = deltaY / deltaX;
        }
        return slope;
    }

    public double getIntercept() {
        if (intercept == Double.NEGATIVE_INFINITY) {
            intercept = startPoint.Y - startPoint.X * getSlope();
        }
        return intercept;
    }

    public double distanceToPoint(SrvPoint2D pt) {
        return pt.distanceTo(PointIntersection(pt));
    }

    public SrvPoint2D PointIntersection(SrvPoint2D pt) {
        //find line equation of perpendicular line which goes trough pt
        double slopePrime = -1 / getSlope();
        double interceptPrime = pt.Y - pt.X * slopePrime;
        double left = slopePrime + (-1 * getSlope());
        double right = getIntercept() + (-1 * interceptPrime);
        double x = right / (left);
        double y = getSlope() * x + getIntercept();
        return new SrvPoint2D(x, y);
    }

    public boolean isPointOnLine(SrvPoint2D pt) {
        vect2D vec1 = pt.subtract(startPoint);
        vect2D vec2 = pt.subtract(endPoint);
        double det = vec1.getX() * vec2.getY() - vec2.getX() * vec1.getY();
        return Math.abs(det) < 0.001;
    }

    public SrvPoint2D getStartPoint() {
        return startPoint;
    }

    public SrvPoint2D getEndPoint() {
        return endPoint;
    }

}
