package org.apache.commons.imaging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPixelDensity {

    @Test
    public void testCreateFromPixelsPerCentimetre() {
        PixelDensity density = PixelDensity.createFromPixelsPerCentimetre(10, 20);
        assertTrue(density.isInCentimetres());
        assertEquals(10, density.getRawHorizontalDensity());
        assertEquals(20, density.getRawVerticalDensity());
    }

    @Test
    public void testCreateFromPixelsPerInch() {
        PixelDensity density = PixelDensity.createFromPixelsPerInch(300, 600);
        assertTrue(density.isInInches());
        assertEquals(300, density.getRawHorizontalDensity());
        assertEquals(600, density.getRawVerticalDensity());
    }

    @Test
    public void testCreateFromPixelsPerMetre() {
        PixelDensity density = PixelDensity.createFromPixelsPerMetre(11811, 23622);
        assertTrue(density.isInMetres());
        assertEquals(11811, density.getRawHorizontalDensity());
        assertEquals(23622, density.getRawVerticalDensity());
    }

    @Test
    public void testCreateUnitless() {
        PixelDensity density = PixelDensity.createUnitless(72, 144);
        assertTrue(density.isUnitless());
        assertEquals(72, density.getRawHorizontalDensity());
        assertEquals(144, density.getRawVerticalDensity());
    }

    @Test
    public void testHorizontalDensityCentimetres() {
        PixelDensity density = PixelDensity.createFromPixelsPerInch(2.54, 5.08);
        assertEquals(1, density.horizontalDensityCentimetres());
    }

    @Test
    public void testHorizontalDensityInches() {
        PixelDensity density = PixelDensity.createFromPixelsPerCentimetre(1, 2);
        assertEquals(2.54, density.horizontalDensityInches(), 0.01);
    }

    @Test
    public void testHorizontalDensityMetres() {
        PixelDensity density = PixelDensity.createFromPixelsPerInch(254, 508);
        assertEquals(1, density.horizontalDensityMetres(), 0.01);
    }

    @Test
    public void testVerticalDensityCentimetres() {
        PixelDensity density = PixelDensity.createFromPixelsPerInch(2.54, 5.08);
        assertEquals(2, density.verticalDensityCentimetres());
    }

    @Test
    public void testVerticalDensityInches() {
        PixelDensity density = PixelDensity.createFromPixelsPerCentimetre(1, 2);
        assertEquals(5.08, density.verticalDensityInches(), 0.01);
    }

    @Test
    public void testVerticalDensityMetres() {
        PixelDensity density = PixelDensity.createFromPixelsPerInch(254, 508);
        assertEquals(2, density.verticalDensityMetres(), 0.01);
    }
}
