package controllers;

import play.mvc.Controller;
import utils.DemoImageUtil;
import utils.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Application extends Controller {

    public static void index() throws IOException {
        render();
    }

    public static void loadImg() {
        byte[] bytes = DemoImageUtil.obtainImage();
        BufferedImage image = ImageUtil.convertToImage(bytes);
        BufferedImage part = image.getSubimage(Integer.parseInt(request.params.get("x")),
                Integer.parseInt(request.params.get("y")),
                Integer.parseInt(request.params.get("w")),
                Integer.parseInt(request.params.get("h")));
        renderBinary(ImageUtil.convertToInputStream(part));
    }

    public static void actionProceed() {
        DemoImageUtil.switchToNewImage();
    }

    public static void streaming(Integer height) {
        session.put("viewPortHeight", height);
        DemoImageUtil.switchToNewImage();
        byte[] bytes = DemoImageUtil.obtainImage();
        BufferedImage image = ImageUtil.convertToImage(bytes);
        int numberOfThreads = DemoImageUtil.getNumberOfThreads();
        renderArgs.put("fragments", ImageUtil.splitToFragments(image, height, numberOfThreads));
        renderArgs.put("numberOfThreads", numberOfThreads);

        render();
    }
}