package controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import stubs.SourceOfImageStub;
import utils.ImageUtil;
import java.awt.image.BufferedImage;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void loadImg(@Required Integer x, @Required Integer y, @Required Integer w, @Required Integer h) {
        if (Validation.hasErrors()) {
            index();
        }
        SourceOfImageStub sourceStub = (SourceOfImageStub) Cache.get("sourceStub" + session.getId());
        BufferedImage image = ImageUtil.convertToImage(sourceStub.getCurrentImageBytes());
        BufferedImage part = image.getSubimage(x, y, w, h);
        renderBinary(ImageUtil.convertToInputStream(part));
    }

    public static JsonObject actionProceed() {
        //Fulfill request from a client
        //... and obtain the response
        SourceOfImageStub sourceStub = (SourceOfImageStub) Cache.get("sourceStub" + session.getId());
        sourceStub.nextImage();
        BufferedImage image = ImageUtil.convertToImage(sourceStub.getCurrentImageBytes());
        JsonObject object = new JsonObject();
        object.add("height", new JsonPrimitive(image.getHeight()));
        object.add("width", new JsonPrimitive(image.getWidth()));
        return object;
    }

    public static void streaming() {
        SourceOfImageStub sourceStub = new SourceOfImageStub();
        Cache.add("sourceStub" + session.getId(), sourceStub); //We store this just for demo
        byte[] bytes = sourceStub.getCurrentImageBytes();
        BufferedImage image = ImageUtil.convertToImage(bytes);
        renderArgs.put("height", image.getHeight());
        renderArgs.put("width", image.getWidth());

        render();
    }
}