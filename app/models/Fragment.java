package models;

/**
 * Represents a piece of image with its coordinates.
 * */
public class Fragment {

    private int xCoord; //If we are going to split image in width too

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fragment fragment = (Fragment) o;

        if (xCoord != fragment.xCoord) return false;
        if (yCoord != fragment.yCoord) return false;
        if (height != fragment.height) return false;
        return width == fragment.width;
    }

    @Override
    public int hashCode() {
        int result = xCoord;
        result = 31 * result + yCoord;
        result = 31 * result + height;
        result = 31 * result + width;
        return result;
    }
}
