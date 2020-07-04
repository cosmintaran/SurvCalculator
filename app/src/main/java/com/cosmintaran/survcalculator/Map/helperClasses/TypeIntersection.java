package com.cosmintaran.survcalculator.Map.helperClasses;

public enum TypeIntersection{
    NoIntersection, //lines are parallel
    ExtremityIntersection, //intersection point coincide with one of the segment point
    UnboundedIntersection, //intersection point is not on the segment
    BoundedIntersection //intersection point is on the segment

}
