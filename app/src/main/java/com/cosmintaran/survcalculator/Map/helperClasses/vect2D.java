package com.cosmintaran.survcalculator.Map.helperClasses;

import org.jetbrains.annotations.NotNull;

public final class vect2D {

    private double x;
    private double y;

    public vect2D(double x, double y){
        this.x = x;
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        vect2D vect2D = (vect2D) o;
        return  Math.abs(x - vect2D.x) < 0.000000001 && Math.abs(y - vect2D.y) < 0.000000001;
    }

    public vect2D add(@NotNull vect2D vec){
        return new vect2D(x + vec.x, y + vec.y);
    }

    public vect2D subtraction(@NotNull vect2D vec){
        return new vect2D(vec.x - x, vec.y - y);
    }

    public vect2D multiply(@NotNull vect2D vec){
        return new vect2D(x * vec.x, y * vec.y);
    }

    public vect2D divide(@NotNull vect2D vec){
        return new vect2D(x / vec.x, y / vec.y);
    }

    public void scale(double val){
       x *= val;
       y *= val;
    }

    public void scaleX(double val){
        x*= val;
    }

    public void scaleY(double val){
        y*=val;
    }

    public void rotate(double rad){
        double c = Math.cos(rad);
        double s = Math.sin(rad);
        x = x * c - y * s;
        y = x * s + y * c;
    }

    public void normalize(){
        double length = length();
        if(length == 0) return;
        scale(1/length);
    }

    public double length(){
        return Math.sqrt(x*x + y*y);
    }

    public static double dotProduct(@NotNull vect2D v1, @NotNull vect2D v2){
        return (v1.x * v2.x) + (v1.y * v2.y);
    }

    public static double crossProduct(@NotNull vect2D v1, @NotNull vect2D v2){
        return (v1.x * v2.y) - (v1.y * v2.x);
    }

    public double dotProduct(@NotNull vect2D v2){
        return (x * v2.x) + (y * v2.y);
    }

    public double crossProduct( @NotNull vect2D v2){
        return (x * v2.y) - (y * v2.x);
    }

    public double getX(){return x;}
    public double getY(){return y;}
}
