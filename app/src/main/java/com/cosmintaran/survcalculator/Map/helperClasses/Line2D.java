package com.cosmintaran.survcalculator.Map.helperClasses;

import org.jetbrains.annotations.NotNull;

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
            if(deltaX != 0)
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

    public LineIntersectionResult lineIntersection ( Line2D line2 ) {

        double slopeThis = getSlope();
        double interceptThis = getIntercept();
        double slope = line2.getSlope();
        double intercept = line2.getIntercept();

        //one vertical line
         if((slope == Double.NEGATIVE_INFINITY || slopeThis ==  Double.NEGATIVE_INFINITY) ||
                (slope != Double.NEGATIVE_INFINITY && slopeThis ==  Double.NEGATIVE_INFINITY)){


             if(slopeThis == Double.NEGATIVE_INFINITY){
                 double x = startPoint.X;
                 double dx = Math.abs(x - line2.startPoint.X);
                 double y = (dx*slope) - line2.startPoint.Y;
                 return new LineIntersectionResult(x,y,getTypeIntersection(line2,x,y));
             }
             else{

                 double x = line2.startPoint.X;
                 double dx = Math.abs(x - line2.startPoint.X);
                 double y = (dx*slope) - startPoint.Y;
                 return new LineIntersectionResult(x,y,getTypeIntersection(line2,x,y));
             }

        }
         //parallel lines
         else if((slope == Double.NEGATIVE_INFINITY && slopeThis ==  Double.NEGATIVE_INFINITY) ||
                 Math.abs(slope - slopeThis ) < 0.000001){
             return new LineIntersectionResult(Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY,TypeIntersection.NoIntersection);
         }
        //no vertical lines
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
        if(Math.abs(x - startPoint.X) < 0.0000001 && Math.abs(y - startPoint.Y) < 0.0000001 ||
                Math.abs(x - endPoint.X) < 0.0000001 && Math.abs(y - endPoint.Y) < 0.0000001 ||
                Math.abs(x - line2.startPoint.X) < 0.0000001 && Math.abs(y - line2.startPoint.Y) < 0.0000001 ||
                Math.abs(x - line2.endPoint.X) < 0.0000001 && Math.abs(y - line2.endPoint.Y) < 0.0000001){
            type = TypeIntersection.ExtremityIntersection;
        }
        else if(x < Math.max(startPoint.X, endPoint.X) && x > Math.min(startPoint.X, endPoint.X) &&
        y <Math.max(startPoint.Y, endPoint.Y) && y > Math.min(startPoint.Y, endPoint.Y) &&
                x < Math.max(line2.startPoint.X, line2.endPoint.X) && x > Math.min(line2.startPoint.X, line2.endPoint.X) &&
                y <Math.max(line2.startPoint.Y, line2.endPoint.Y) && y > Math.min(line2.startPoint.Y, line2.endPoint.Y))
        {
            type = TypeIntersection.BoundedIntersection;
        }

        else {
            type = TypeIntersection.UnboundedIntersection;
        }
        return type;
    }


}
