package org.apache.commons.imaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.*;

import static org.junit.jupiter.api.Assertions.*;

public class ColorToolsTest {

    private ColorTools colorTools;
    private BufferedImage testImage;
    private ColorSpace sRGBColorSpace;
    private ColorSpace linearRGBColorSpace;
    private ICC_Profile sRGBProfile;
    private ICC_Profile linearRGBProfile;

    @BeforeEach
    public void setUp() {
        colorTools = new ColorTools();
        testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        sRGBColorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        linearRGBColorSpace = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);
        sRGBProfile = ICC_Profile.getInstance(ColorSpace.CS_sRGB);
        linearRGBProfile = ICC_Profile.getInstance(ColorSpace.CS_LINEAR_RGB);
    }

    @Test
    public void testConvertBetweenColorSpaces() {
        BufferedImage convertedImage = colorTools.convertBetweenColorSpaces(testImage, sRGBColorSpace, linearRGBColorSpace);
        assertNotNull(convertedImage);
        assertEquals(linearRGBColorSpace, convertedImage.getColorModel().getColorSpace());
    }



    @Test
    public void testDeriveColorModelWithComponentColorModelAndForceNoAlpha() {
        ColorModel colorModel = testImage.getColorModel();
        if (colorModel instanceof ComponentColorModel) {
            ColorModel derivedColorModel = colorTools.deriveColorModel(colorModel, linearRGBColorSpace, true);
            assertFalse(derivedColorModel.hasAlpha());
        } else {
            fail("The test image does not use a ComponentColorModel.");
        }
    }

    @Test
    public void testDeriveColorModelWithDirectColorModel() {
        int[] nBits = {8, 8, 8, 8};
        int[] bOffs = {0, 1, 2, 3};
        ColorModel colorModel = new DirectColorModel(32, 0xff0000, 0xff00, 0xff, 0xff000000);
        SampleModel sampleModel = new SinglePixelPackedSampleModel(DataBuffer.TYPE_INT, 100, 100, bOffs);
        DataBuffer dataBuffer = new DataBufferInt(100 * 100);
        WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, null);
        BufferedImage directColorImage = new BufferedImage(colorModel, raster, false, null);

        ColorModel derivedColorModel = colorTools.deriveColorModel(directColorImage.getColorModel(), linearRGBColorSpace, false);
        assertNotNull(derivedColorModel);
        assertTrue(derivedColorModel instanceof DirectColorModel);
    }

    @Test
    public void testDeriveColorModelThrowsExceptionForUnknownColorModelType() {
        ColorModel colorModel = new IndexColorModel(8, 256, new byte[256], new byte[256], new byte[256]);
        assertThrows(ImagingOpException.class, () -> colorTools.deriveColorModel(colorModel, linearRGBColorSpace, false));
    }

    @Test
    public void testRelabelColorSpaceWithColorModel() {
        ColorModel colorModel = new ComponentColorModel(linearRGBColorSpace, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        assertDoesNotThrow(() -> colorTools.relabelColorSpace(testImage, colorModel));
    }

    @Test
    public void testRelabelColorSpaceWithICCProfile() {
        assertDoesNotThrow(() -> colorTools.relabelColorSpace(testImage, sRGBProfile));
    }

}
