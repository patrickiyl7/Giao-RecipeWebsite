package edu.northeastern.cs5500.recipe.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.imageio.ImageIO;

public class Tools {
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("MM-dd-yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Encode image to Base64 string
     *
     * @param <BufferedImage>
     * @param image The image to Base64 encode
     * @param type jpeg, bmp, ...
     * @return Base64 encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedImageBytes = encoder.encode(imageBytes);
            imageString = new String(encodedImageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
}
