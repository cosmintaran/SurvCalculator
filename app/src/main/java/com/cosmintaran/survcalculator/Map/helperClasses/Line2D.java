package com.cosmintaran.survcalculator.Map.helperClasses;

import org.jetbrains.annotations.NotNull;

public final class Line2D {

    private static final double TOLERANCE = 0.00001;

    private final SrvPoint2D startPoint;
    private final SrvPoint2D endPoint;
    private final double length;
    private final double slope;
    private final double intercept;
    private SrvPoint2D pt;


    public Line2D ( SrvPoint2D startPoint , SrvPoint2D endPoint ) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;

        double deltaX = startPoint.X - endPoint.X;
        double deltaY = startPoint.Y - endPoint.Y;
        length = Math.sqrt((deltaX * deltaX + deltaY * deltaY));
        slope = deltaY / deltaX;
        intercept = startPoint.Y - startPoint.X * slope;
    }

    public double getLength ( ) {
        return length;
    }

    public double getSlope ( ) {
        return slope;
    }

    public double getIntercept ( ) {
        return intercept;
    }

    public double distanceToPoint ( SrvPoint2D pt ) {
        return pt.distanceTo(pointIntersection(pt));
    }

    public SrvPoint2D pointIntersection(SrvPoint2D pt ) {
        //find line equation of perpendicular line which goes trough pt
        Line2D perpLine = null;
        if(Double.isInfinite(getSlope()) || Double.isNaN(getSlope())){
            perpLine = new Line2D(pt,new SrvPoint2D(pt.X + 2, pt.Y));
        }
        else{
            double slopePrime = -1 / getSlope();
            double interceptPrime = pt.Y - pt.X * slopePrime;
            perpLine = new Line2D(pt,new SrvPoint2D(pt.X + 2,(pt.X + 2)* slopePrime + interceptPrime));
        }

        /*double left = slopePrime + (-1 * getSlope());
        double right = getIntercept() + (-1 * interceptPrime);
        double x = right / (left);
        double y = getSlope() * x + getIntercept();
        return new SrvPoint2D(x , y);*/
        LineIntersectionResult rez = lineIntersection(perpLine);
        return new SrvPoint2D(rez.getX(),rez.getY());
    }

    public boolean isPointOnLine ( SrvPoint2D pt ) {
        vect2D vec1 = pt.subtract(startPoint);
        vect2D vec2 = pt.subtract(endPoint);
        double det = vec1.getX() * vec2.getY() - vec2.getX() * vec1.getY();
        return Math.abs(det) < 0.001; //area of the dot product is twice as size of area of triangle
    }

    public SrvPoint2D getStartPoint ( ) {
        return startPoint;
    }

    public SrvPoint2D getEndPoint ( ) {
        return endPoint;
    }

    /*public LineIntersectionResult lineIntersection ( Line2D line2 ) {

        //check if this line is vertical
        if (Math.abs(startPoint.X - endPoint.X) < TOLERANCE) {

            double x = startPoint.X;
            double dx = Math.abs(x - line2.startPoint.X);
            double y;
            if ((line2.getSlope() > 0 && x < startPoint.X) ||
                    line2.getSlope() < 0 && x > startPoint.X) {
                y = (dx * line2.getSlope()) - line2.startPoint.Y;
            } else {
                y = (dx * line2.getSlope()) + line2.startPoint.Y;
            }
            return new LineIntersectionResult(x , y , getTypeIntersection(line2 , x , y));
        } else if (Math.abs(line2.startPoint.X - line2.endPoint.X) < TOLERANCE) {

            double x = line2.startPoint.X;
            double dx = Math.abs(x - startPoint.X);
            double y;

            if ((getSlope() > 0 && x < line2.startPoint.X) ||
                    getSlope() < 0 && x > line2.startPoint.X) {
                y = (dx * getSlope()) - startPoint.Y;
            } else {
                y = (dx * getSlope()) + startPoint.Y;
            }

            return new LineIntersectionResult(x , y , getTypeIntersection(line2 , x , y));
        }

        double slopeThis = getSlope();
        double interceptThis = getIntercept();
        double slope = line2.getSlope();
        double intercept = line2.getIntercept();

        if (Math.abs(slope - slopeThis) < TOLERANCE) {
            return new LineIntersectionResult(Double.NEGATIVE_INFINITY , Double.NEGATIVE_INFINITY , TypeIntersection.NoIntersection);
        } else {
            double left = slope + (-1 * slopeThis);
            double right = interceptThis + (-1 * intercept);
            double x = right / left;
            double y = slopeThis * x + interceptThis;
            TypeIntersection type = getTypeIntersection(line2 , x , y);
            return new LineIntersectionResult(x , y , type);
        }
    }*/

    /*public LineIntersectionResult gaussElimination ( Line2D line2 ) {

        double[][] gaussMAtrix = new double[2][3];

        if (getSlope() > 0 || getSlope() < 0) {
            gaussMAtrix[0][0] = getSlope() * -1;
            gaussMAtrix[0][1] = 1;
            gaussMAtrix[0][2] = getIntercept();
            gaussMAtrix[1][0] = line2.getSlope() * -1;
            gaussMAtrix[1][1] = 1;
            gaussMAtrix[1][2] = line2.getIntercept();
        } else {
            gaussMAtrix[0][0] = line2.getSlope() * -1;
            gaussMAtrix[0][1] = 1;
            gaussMAtrix[0][2] = line2.getIntercept();
            gaussMAtrix[1][0] = getSlope() * -1;
            gaussMAtrix[1][1] = 1;
            gaussMAtrix[1][2] = getIntercept();
        }

        //step1
        double m1;

        if (gaussMAtrix[1][0] > 0 && gaussMAtrix[0][0] > 0 ||
                gaussMAtrix[1][0] < 0 && gaussMAtrix[0][0] < 0) {
            m1 = gaussMAtrix[1][0];
        } else {
            m1 = gaussMAtrix[1][0] * -1;
        }

        double m2 = gaussMAtrix[0][0];
        gaussMAtrix[0][0] *= m1;
        gaussMAtrix[0][1] *= m1;
        gaussMAtrix[0][2] *= m1;

        gaussMAtrix[1][0] *= m2;
        gaussMAtrix[1][1] *= m2;
        gaussMAtrix[1][2] *= m2;

        gaussMAtrix[1][0] += gaussMAtrix[0][0];
        gaussMAtrix[1][1] += gaussMAtrix[0][1];
        gaussMAtrix[1][2] += gaussMAtrix[0][2];

        m1 = gaussMAtrix[0][0];
        m2 = gaussMAtrix[1][1];

        if (m1 != 1) {
            gaussMAtrix[0][0] *= 1 / m1;
            gaussMAtrix[0][1] *= 1 / m1;
            gaussMAtrix[0][2] *= 1 / m1;
        }

        if (m2 != 1) {
            gaussMAtrix[1][0] *= 1 / m2;
            gaussMAtrix[1][1] *= 1 / m2;
            gaussMAtrix[1][2] *= 1 / m2;
        }

        double x = 0, y = 0;

        y = gaussMAtrix[1][2];
        x = (getIntercept() + (-1 * y)) / (getSlope() * -1);

        return new LineIntersectionResult(x , y , getTypeIntersection(line2,x,y));
    }*/

    @NotNull
    private TypeIntersection getTypeIntersection ( Line2D line2 , double x , double y ) {

        TypeIntersection type;
        double x1 = Math.abs(x);
        double y1 = Math.abs(y);

        if ((Math.abs(startPoint.X - endPoint.X) < TOLERANCE || Math.abs(line2.startPoint.X - line2.endPoint.X) < TOLERANCE) &&
                y1 < Math.max(startPoint.Y , endPoint.Y) && y1 > Math.min(startPoint.Y , endPoint.Y) &&
                y1 < Math.max(line2.startPoint.Y , line2.endPoint.Y) && y1 > Math.min(line2.startPoint.Y , line2.endPoint.Y)) {

            type = TypeIntersection.BoundedIntersection;

        }else if((Math.abs(startPoint.Y - endPoint.Y) < TOLERANCE || Math.abs(line2.startPoint.Y - line2.endPoint.Y) < TOLERANCE) &&
                x1 < Math.max(startPoint.X , endPoint.X) && x1 > Math.min(startPoint.X , endPoint.X) &&
                x1 < Math.max(line2.startPoint.X , line2.endPoint.X) && x1 > Math.min(line2.startPoint.X , line2.endPoint.X)){
            type = TypeIntersection.BoundedIntersection;

        } else if (Math.abs(x1 - startPoint.X) < TOLERANCE && Math.abs(y1 - startPoint.Y) < TOLERANCE ||
                Math.abs(x1 - endPoint.X) < TOLERANCE && Math.abs(y1 - endPoint.Y) < TOLERANCE ||
                Math.abs(x1 - line2.startPoint.X) < TOLERANCE && Math.abs(y1 - line2.startPoint.Y) < TOLERANCE ||
                Math.abs(x1 - line2.endPoint.X) < TOLERANCE && Math.abs(y1 - line2.endPoint.Y) < TOLERANCE) {
            type = TypeIntersection.ExtremityIntersection;
        } else if (x1 <= Math.max(startPoint.X , endPoint.X) && x1 >= Math.min(startPoint.X , endPoint.X) &&
                y1 < Math.max(startPoint.Y , endPoint.Y) && y1 > Math.min(startPoint.Y , endPoint.Y) &&
                x1 <= Math.max(line2.startPoint.X , line2.endPoint.X) && x1 >= Math.min(line2.startPoint.X , line2.endPoint.X) &&
                y1 < Math.max(line2.startPoint.Y , line2.endPoint.Y) && y1 > Math.min(line2.startPoint.Y , line2.endPoint.Y)) {
            type = TypeIntersection.BoundedIntersection;
        } else {
            type = TypeIntersection.UnboundedIntersection;
        }

        return type;
    }

    public LineIntersectionResult lineIntersection ( Line2D line2 ) {

        double[][] mat = new double[2][2];
        fillMatrix(line2, mat);
        double det = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];

        if(Math.abs(det) < TOLERANCE)
            return new LineIntersectionResult(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, TypeIntersection.NoIntersection);

        double[][] matTransp = new double[2][2];
        matTransp[0][0] = mat[0][0];
        matTransp[0][1] = mat[1][0];
        matTransp[1][0] = mat[0][1];
        matTransp[1][1] = mat[1][1];

        //Inverse matrix
        mat[0][0] =  1/det * matTransp[1][1];
        mat[0][1] = -1/det * matTransp[1][0];
        mat[1][0] = -1/det * matTransp[0][1];
        mat[1][1] =  1/det * matTransp[0][0];


        double[] vect = new double[]{ Double.isInfinite(getIntercept()) || Double.isNaN(getIntercept())? startPoint.X  : getIntercept() ,
                (Double.isInfinite(line2.getIntercept()) || Double.isNaN(line2.getIntercept())) ? line2.startPoint.X : line2.getIntercept() };
        double[] rez = new double[2];

        for (int i = 0; i < 2; ++i){
            for (int j = 0; j < 2; ++j){
                rez[i] += vect[j]*mat[i][j];
            }
        }
        return new LineIntersectionResult(rez[0],rez[1],getTypeIntersection(line2,rez[0],rez[1]));
    }

    public SrvPoint2D pointAtDistance(double dist){

        SrvPoint2D point = null;
        if(Double.isInfinite(getSlope()) || Double.isNaN(getSlope())){
            point = new SrvPoint2D(startPoint.X + dist, startPoint.Y);
        }
        else{
            point = new SrvPoint2D(startPoint.X + 2,(startPoint.X + 2)* getSlope() + getIntercept());
        }
        return point;
    }


    private void fillMatrix(Line2D line2, double[][] mat) {
        if(Double.isInfinite(getSlope()) || Double.isNaN(getSlope())){
            mat[0][0] = 1 ;
            mat[0][1] = 0;
        }
        else {
            mat[0][0] = getSlope() * -1;
            mat[0][1] = 1;
        }
        if(Double.isInfinite(line2.getSlope()) || Double.isNaN(line2.getSlope())){
            mat[1][0] = 1;
            mat[1][1] = 0;
        }
        else {
            mat[1][0] = line2.getSlope() * -1;
            mat[1][1] = 1;
        }
    }


}
