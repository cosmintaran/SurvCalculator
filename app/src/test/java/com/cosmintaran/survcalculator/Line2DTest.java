package com.cosmintaran.survcalculator;

import com.cosmintaran.survcalculator.Map.helperClasses.Line2D;
import com.cosmintaran.survcalculator.Map.helperClasses.LineIntersectionResult;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;
import com.cosmintaran.survcalculator.Map.helperClasses.TypeIntersection;
import org.junit.Test;
import static org.junit.Assert.*;

public class Line2DTest {
    @Test
    public void lineEquation1(){
        Line2D l1 = new Line2D(new SrvPoint2D(4,5),new SrvPoint2D(17,13));
        //Slope
        assertTrue(Math.abs(l1.getSlope() - 0.6153846153846154) < 0.0000001);
        //intercept
        assertTrue(Math.abs(l1.getIntercept() - 2.5384615384615383) < 0.0000001);
        //length
        assertTrue(Math.abs(l1.getLength() - 15.264337522473747) < 0.0000001);
    }

    @Test
    public void lineEquation2(){
        Line2D l1 = new Line2D(new SrvPoint2D(3,6),new SrvPoint2D(3,2));
        //Slope
        assertTrue(l1.getSlope() == Double.NEGATIVE_INFINITY || l1.getSlope() == Double.POSITIVE_INFINITY
        || l1.getSlope() == Double.NaN);
        //intercept
        assertTrue(l1.getIntercept() == Double.NEGATIVE_INFINITY || l1.getIntercept() == Double.POSITIVE_INFINITY
                || l1.getIntercept() == Double.NaN);
        //length
        assertTrue(l1.getLength() == 4);
    }

    @Test
    public void lineEquation3(){
        Line2D l1 = new Line2D(new SrvPoint2D(20,6),new SrvPoint2D(40,6));
        //Slope
        assertTrue(l1.getSlope() == 0);
        //intercept
        assertTrue(l1.getIntercept() == l1.getStartPoint().Y);
        //length
        assertTrue(l1.getLength() == 20);
    }

    @Test
    public void lineIntersection_oblique_bounded(){
        Line2D l1 = new Line2D(new SrvPoint2D(33.4100,52.7500),new SrvPoint2D(77.3300,9.8300));
        Line2D l2 = new Line2D(new SrvPoint2D(50.0000,10.0000),new SrvPoint2D(75.4300,50.4000));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.BoundedIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 60.3424) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 26.4308) < 0.0001);
    }

    @Test
    public void lineIntersection_oblique_extremity(){
        Line2D l1 = new Line2D(new SrvPoint2D(33.4100,52.7500),new SrvPoint2D(77.3300,9.8300));
        Line2D l2 = new Line2D(new SrvPoint2D(33.4100,52.7500),new SrvPoint2D(86.4400,87.2300));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.ExtremityIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 33.4100) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 52.7500) < 0.0001);
    }


    @Test
    public void lineIntersection_oblique_unbounded(){
        Line2D l1 = new Line2D(new SrvPoint2D(33.4100,52.7500),new SrvPoint2D(77.3300,9.8300));
        Line2D l2 = new Line2D(new SrvPoint2D(33.4100,93.1092),new SrvPoint2D(86.4400,127.6000));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.UnboundedIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() -  8.6137) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 76.9817) < 0.0001);
    }

    @Test
    public void lineIntersection_oblique_unbounded2(){
        Line2D l1 = new Line2D(new SrvPoint2D(33.4100,52.7500),new SrvPoint2D(77.3300,9.8300));
        Line2D l2 = new Line2D(new SrvPoint2D(119.5700,3.2100),new SrvPoint2D(155.0500,71.8800));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.UnboundedIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 107.6709) < 0.0001);
        assertTrue(Math.abs(r1.getY() - -19.8201) < 0.0001);
    }


    @Test
    public void lineIntersection_vertical_extremity_negativeSlope(){
        Line2D l1 = new Line2D(new SrvPoint2D(61.91,126.07),new SrvPoint2D(61.91,-64.88));
        Line2D l2 = new Line2D(new SrvPoint2D(61.91,126.07),new SrvPoint2D(199.64,44.33));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.ExtremityIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 61.9100) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 126.0700) < 0.0001);
    }

    @Test
    public void lineIntersection_vertical_extremity_positiveSlope(){
        Line2D l1 = new Line2D(new SrvPoint2D(61.91,126.07),new SrvPoint2D(61.91,-64.88));
        Line2D l2 = new Line2D(new SrvPoint2D(61.91,126.07),new SrvPoint2D(185.8400,209.3400));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.ExtremityIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 61.9100) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 126.0700) < 0.0001);

    }

    @Test
    public void lineIntersection_vertical_bounded_positiveSlope(){
        Line2D l1 = new Line2D(new SrvPoint2D(61.91,126.07),new SrvPoint2D(61.91,-64.88));
        Line2D l2 = new Line2D(new SrvPoint2D(-11.7300,-20.4600),new SrvPoint2D(225.60,139.00));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.BoundedIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 61.9100) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 29.018) < 0.0001);

    }

    @Test
    public void lineIntersection_vertical_unbounded_positiveSlope(){
        Line2D l1 = new Line2D(new SrvPoint2D(456876.7400,236562.6800),new SrvPoint2D(456876.7400,235907.9600));
        Line2D l2 = new Line2D(new SrvPoint2D(456915.1100,236721.4500),new SrvPoint2D(457408.3000,237108.1400));
        LineIntersectionResult r1 = l1.lineIntersection(l2);
        LineIntersectionResult r2 = l2.lineIntersection(l1);
        assertTrue(r1.getType() == r2.getType() && r1.getType() == TypeIntersection.UnboundedIntersection);
        assertTrue( Math.abs(r1.getX() - r2.getX()) < 0.00001);
        assertTrue( Math.abs(r1.getY() - r2.getY()) < 0.00001);
        assertTrue(Math.abs(r1.getX() - 456876.7400) < 0.0001);
        assertTrue(Math.abs(r1.getY() - 236691.3657) < 0.0001);
    }

}
