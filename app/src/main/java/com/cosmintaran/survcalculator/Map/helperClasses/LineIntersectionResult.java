package com.cosmintaran.survcalculator.Map.helperClasses;

public class LineIntersectionResult {

    private double X;
    private double Y;
    private TypeIntersection Type;

    public LineIntersectionResult ( double x , double y , TypeIntersection type ) {
        X = x;
        Y = y;
        Type = type;
    }

    public double getX ( ) {
        return X;
    }

    public double getY ( ) {
        return Y;
    }

    public TypeIntersection getType ( ) {
        return Type;
    }
}
