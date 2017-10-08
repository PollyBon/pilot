package models;


public class Fragment {

    private int xCoord;

    private int yCoord;

    private int height;

    private int width;

    public Fragment(int xCoord, int yCoord, int height, int width) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.height = height;
        this.width = width;
    }

    public String getParams() {
        return "x=" + xCoord +
                "&y=" + yCoord +
                "&h=" + height +
                "&w=" + width;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
