package model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarksTest {
    @Test
    public void testGrade(){
        MarkedStatus mark1 = new MarkedStatus(0);
        MarkedStatus mark2 = new MarkedStatus(5);
        MarkedStatus mark3 = new MarkedStatus(5);

        mark1.setGrade(1);
        mark2.setGrade(10);
        mark3.setGrade(5);

        BigDecimal val1 = mark1.getValue();
        BigDecimal val2 = mark2.getValue();
        BigDecimal val3 = mark3.getValue();

        assertEquals(BigDecimal.valueOf(.01), val1);
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP), val2);
        assertEquals(BigDecimal.valueOf(3.49485).setScale(2, RoundingMode.HALF_UP), val3);
        assertTrue(mark1.getStatus());
        assertTrue(mark1.getStatus());
        assertTrue(mark1.getStatus());
    }
    @Test
    public void testSlab(){
        MarkedStatus mark1 = new MarkedStatus(5);

        mark1.setGrade(10);
        mark1.slabComic();

        BigDecimal val1 = mark1.getValue();

        assertEquals(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP), val1);
    }

    @Test
    public void testOneSignature(){
        MarkedStatus mark1 = new MarkedStatus(5);

        mark1.setGrade(10);
        mark1.setSignature("Alex Eng");

        BigDecimal val1 = mark1.getValue();

        mark1.setSignature("Charles Von Goins");

        BigDecimal val2 = mark1.getValue();

        mark1.setSignature("Alex Eng");

        BigDecimal val3 = mark1.getValue();

        assertEquals(BigDecimal.valueOf(5.25), val1);
        assertEquals(BigDecimal.valueOf(5.50).setScale(2, RoundingMode.HALF_UP), val2);
        assertEquals(BigDecimal.valueOf(5.50).setScale(2, RoundingMode.HALF_UP), val3);
    }

    @Test
    public void testSigAuth(){
        MarkedStatus mark1 = new MarkedStatus(5);

        mark1.setGrade(10);
        mark1.setGrade(10);
        mark1.setSignature("Alex Eng");
        mark1.setSignature("Charles Von Goins");
        mark1.setSignature("Evan Wright");
        mark1.setSignature("Josiah Claudio");
        mark1.authenticateSignatures(1);

        BigDecimal val1 = mark1.getValue();

        mark1.authenticateSignatures(2);
        BigDecimal val2 = mark1.getValue();

        assertEquals(BigDecimal.valueOf(7).setScale(2, RoundingMode.HALF_UP), val1);

        assertEquals(BigDecimal.valueOf(9).setScale(2, RoundingMode.HALF_UP), val2);

    }

    @Test
    public void testFullCalculation(){
        MarkedStatus mark1 = new MarkedStatus(5);
        MarkedStatus mark2 = new MarkedStatus(10);
        MarkedStatus mark3 = new MarkedStatus(5);

        mark1.setGrade(10);
        mark2.setGrade(5);
        mark3.setGrade(10);

        mark1.slabComic();
        mark2.slabComic();

        mark1.setSignature("Alex Eng");
        mark1.setSignature("Josiah Claudio");
        mark2.setSignature("Evan Wright");
        mark2.setSignature("Charles Von Goins");
        mark3.setSignature("Alex Eng");
        mark3.setSignature("Josiah Claudio");

        mark1.authenticateSignatures(2);
        mark2.authenticateSignatures(1);
        mark3.authenticateSignatures(2);

        mark3.slabComic();

        BigDecimal val1 = mark1.getValue();
        BigDecimal val2 = mark2.getValue();
        BigDecimal val3 = mark3.getValue();

        assertEquals(BigDecimal.valueOf(12.5).setScale(2, RoundingMode.HALF_UP), val1);
        assertEquals(BigDecimal.valueOf(16.076).setScale(2, RoundingMode.HALF_UP), val2);
        assertEquals(BigDecimal.valueOf(12.5).setScale(2, RoundingMode.HALF_UP), val3);

    }

}
