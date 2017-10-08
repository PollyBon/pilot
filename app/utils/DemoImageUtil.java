package utils;

import play.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * We only use this methods in the demo to make a stubs for production flow.
 */
public class DemoImageUtil {

    private static String[] images = new String[] {
            "public/images/large1.jpg",
            "public/images/large2.jpg",
            "public/images/large3.jpg"
    };

    private static int counter = 0;
    private static String currentImage;

    public static void switchToNewImage() {
        currentImage = images[counter++ % 3];
    }

    public static byte[] obtainImage() {
        Path path = Paths.get(currentImage);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            Logger.error(e, "Image reading failed");
        }
        return data;
    }

    public static int getNumberOfThreads() {
        return 6;
    }
}
