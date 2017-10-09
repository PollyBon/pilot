package stubs;

import play.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * We only use this class in the demo to make a stubs for production flow.
 */
public class SourceOfImageStub implements Serializable{

    private static String[] images = new String[] {
            "public/images/large1.jpg",
            "public/images/large2.jpg",
            "public/images/large3.jpg"
    };

    private int counter;
    private byte[] currentImageBytes;

    public SourceOfImageStub() {
        counter = 0;
        currentImageBytes = obtainImage(images[0]);
    }

    public void nextImage() {
        String nextName = images[counter++ % 3];
        currentImageBytes = obtainImage(nextName);
    }

    private byte[] obtainImage(String name) {
        Path path = Paths.get(name);
        byte[] data = null;
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            Logger.error(e, "Image reading failed");
        }
        return data;
    }

    public byte[] getCurrentImageBytes() {
        return currentImageBytes;
    }
}
