package controllers;

import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import stubs.SourceOfImageStub;
import utils.ImageUtil;

import javax.validation.constraints.DecimalMin;
import java.awt.image.BufferedImage;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void loadImg(@Required Integer x, @Required Integer y, @Required Integer w, @Required Integer h) {
        if (Validation.hasErrors()) {
            index();
        }
        byte[] bytes = (byte[]) Cache.get("currentImageBytes" + session.getId());
        BufferedImage image = ImageUtil.convertToImage(bytes);
        BufferedImage part = image.getSubimage(x, y, w, h);
        renderBinary(ImageUtil.convertToInputStream(part));
    }

    public static void actionProceed() {
        //Fulfill request from a client
        //... and obtain the response
        SourceOfImageStub sourceStub = (SourceOfImageStub) Cache.get("sourceStub" + session.getId());
        sourceStub.nextImage();
        byte[] bytes = sourceStub.getCurrentImageBytes();
        Cache.replace("currentImageBytes" + session.getId(), bytes);
    }

    public static void streaming(@Required Integer height, @DecimalMin("1") Integer numberOfThreads) {
        if (Validation.hasErrors()) {
            index();
        }
        SourceOfImageStub sourceStub = new SourceOfImageStub();
        byte[] bytes = sourceStub.getCurrentImageBytes();
        Cache.add("sourceStub" + session.getId(), sourceStub); //We store this just for demo
        Cache.add("currentImageBytes" + session.getId(), bytes);
        BufferedImage image = ImageUtil.convertToImage(bytes);
        renderArgs.put("fragments", ImageUtil.splitToFragments(image, height, numberOfThreads));
        renderArgs.put("numberOfThreads", numberOfThreads);

        render();
    }
}