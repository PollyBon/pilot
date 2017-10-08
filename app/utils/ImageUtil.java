package utils;

import models.Fragment;
import play.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {

    private static final String IMAGE_FORMAT = "jpg";

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
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        return is;
    }

    public static List<Fragment> splitToFragments(BufferedImage image, int height, int threads) {
        List<Fragment> fragments = new ArrayList<>();
        int viewPortOccurrences = image.getHeight() / height;
        viewPortOccurrences = viewPortOccurrences == 0 ? 1 : viewPortOccurrences;
        int partHeight = image.getHeight() / viewPortOccurrences / threads;
        int partOccurr = image.getHeight() / partHeight;
        int lastPartHeight = image.getHeight() - partHeight * (partOccurr - 1);
        for (int i = 0; i < partOccurr - 1; i++) {
            fragments.add(new Fragment(0, partHeight * i, partHeight, image.getWidth()));
        }
        fragments.add(new Fragment(0, partHeight * (partOccurr - 1), lastPartHeight, image.getWidth()));
        return fragments;
    }
}
