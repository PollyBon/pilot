package utils;
import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ImageUtil {

    private static final String IMAGE_FORMAT = "jpeg";

    public static BufferedImage convertToImage(byte[] bytes) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            Logger.error(e, "Conversion to image failed");
        }
        return image;
    }

    public static InputStream convertToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, IMAGE_FORMAT, os);
        } catch (IOException e) {
            Logger.error(e, "Conversion to InputStream failed");
        }
        return new ByteArrayInputStream(os.toByteArray());
    }
}
