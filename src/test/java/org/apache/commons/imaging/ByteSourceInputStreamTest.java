package org.apache.commons.imaging;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class     ByteSourceInputStreamTest {

    public static final String ICO_IMAGE_FILE = "ico\\1\\Oregon Scientific DS6639 - DSC_0307 - small.ico";
    public static final int ICO_IMAGE_WIDTH = 300;
    public static final int ICO_IMAGE_HEIGHT = 225;

    @Test
    public void testReadFromStream() throws IOException, ImagingException {

        final String imagePath = FilenameUtils.separatorsToSystem(ICO_IMAGE_FILE);
        final File imageFile = new File(ImagingTestConstants.TEST_IMAGE_FOLDER, imagePath);
        try (BufferedInputStream imageStream = new BufferedInputStream(new FileInputStream(imageFile))) {
            // InputStreamByteSource is created inside of following method
            final BufferedImage bufferedImage = Imaging.getBufferedImage(imageStream, ICO_IMAGE_FILE);

            assertEquals(bufferedImage.getWidth(), ICO_IMAGE_WIDTH);
            assertEquals(bufferedImage.getHeight(), ICO_IMAGE_HEIGHT);
        }
    }
    
    @Test
    public void testReadFromStreamWithMock() throws IOException, ImagingException {
        File file = new File("src/test/resources/IMAGING-136/1402522741337.jpg");
        final byte[] imageData = new byte[(int) file.length()];
        try (InputStream fileInputStream = Files.newInputStream(file.toPath())) {
            fileInputStream.read(imageData); // Read the file's contents into imageData
        }
        final InputStream mockInputStream = new ByteArrayInputStream(imageData);
        
        final BufferedImage bufferedImage = Imaging.getBufferedImage(mockInputStream, "mock_image.jpg");
        
        assertEquals(bufferedImage.getWidth(), 680);
        assertEquals(bufferedImage.getHeight(), 241);
    }
}