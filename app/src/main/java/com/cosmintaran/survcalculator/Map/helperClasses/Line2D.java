package com.cosmintaran.survcalculator.Map.helperClasses;

import org.jetbrains.annotations.NotNull;

public final class Line2D {

    private static final double TOLERANCE = 0.0001;

    private SrvPoint2D startPoint;
    private SrvPoint2D endPoint;
    private double length;
    private double slope;
    private double intercept;


    public Line2D(SrvPoint2D startPoint, SrvPoint2D endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        double deltaX = startPoint.X - endPoint.X;
        double deltaY = startPoint.Y - endPoint.Y;
        length = Math.sqrt((deltaX * deltaX + deltaY * deltaY));
        slope = deltaY / deltaX;
        intercept = startPoint.Y - startPoint.X * slope;
    }

    public double getLength() {
        return length;
    }

    public double getSlope() {
        return slope;
    }

    public double getIntercept() {
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

    public LineIntersectionResult lineIntersection ( Line2D line2 ) {

        //check if this line is vertical
        if(Math.abs(startPoint.X - endPoint.X) < TOLERANCE){

            double x = startPoint.X;
            double dx = Math.abs(x - line2.startPoint.X);
            double y;
            if((line2.getSlope() > 0 && x < startPoint.X) ||
             line2.getSlope() < 0 && x > startPoint.X){
                y = (dx*line2.getSlope()) - line2.startPoint.Y;
            }
            else {
                y = (dx*line2.getSlope()) + line2.startPoint.Y;
            }
            return new LineIntersectionResult(x,y,getTypeIntersection(line2,x,y));
        }
        else if(Math.abs(line2.startPoint.X - line2.endPoint.X) < TOLERANCE){

            double x = line2.startPoint.X;
            double dx = Math.abs(x - startPoint.X);
            double y;

            if((getSlope() > 0 && x < line2.startPoint.X) ||
                    getSlope() < 0 && x > line2.startPoint.X){
                y = (dx*getSlope()) - startPoint.Y;
            }
            else{
                y = (dx*getSlope()) + startPoint.Y;
            }

            return new LineIntersectionResult(x,y,getTypeIntersection(line2,x,y));
        }

        double slopeThis = getSlope();
        double interceptThis = getIntercept();
        double slope = line2.getSlope();
        double intercept = line2.getIntercept();

        if(Math.abs(slope - slopeThis ) < TOLERANCE) {
            return new LineIntersectionResult(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,TypeIntersection.NoIntersection);
        }
        else{
            double left = slope + (-1 * slopeThis);
            double right = interceptThis + (-1 * intercept);
            double x = right / left;
            double y = slopeThis * x + interceptThis;
            TypeIntersection type = getTypeIntersection(line2 , x , y);
            return new LineIntersectionResult(x,y,type);
        }
    }

    @NotNull
    private TypeIntersection getTypeIntersection ( Line2D line2 , double x , double y ) {

        TypeIntersection type;
        double x1 = Math.abs(x);
        double y1 = Math.abs(y);

        if(Math.abs(x1 - startPoint.X) < TOLERANCE && Math.abs(y1 - startPoint.Y) < TOLERANCE ||
                Math.abs(x1 - endPoint.X) < TOLERANCE && Math.abs(y1 - endPoint.Y) < TOLERANCE ||
                Math.abs(x1 - line2.startPoint.X) < TOLERANCE && Math.abs(y1 - line2.startPoint.Y) < TOLERANCE ||
                Math.abs(x1 - line2.endPoint.X) < TOLERANCE && Math.abs(y1 - line2.endPoint.Y) < TOLERANCE){
            type = TypeIntersection.ExtremityIntersection;
        }

        else if(x1 < Math.max(startPoint.X, endPoint.X) && x1 > Math.min(startPoint.X, endPoint.X) &&
                y1 <Math.max(startPoint.Y, endPoint.Y) && y1 > Math.min(startPoint.Y, endPoint.Y) &&
                x1 < Math.max(line2.startPoint.X, line2.endPoint.X) && x1 > Math.min(line2.startPoint.X, line2.endPoint.X) &&
                y1 <Math.max(line2.startPoint.Y, line2.endPoint.Y) && y1 > Math.min(line2.startPoint.Y, line2.endPoint.Y))
        {
            type = TypeIntersection.BoundedIntersection;
        }

        else {
            type = TypeIntersection.UnboundedIntersection;
        }

        return type;
    }


}
