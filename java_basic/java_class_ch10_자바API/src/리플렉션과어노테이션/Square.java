package 리플렉션과어노테이션;

public class Square {
    private int bottomLine;
    private int height;
    private String squareName;


    public static int staticGetArea(int bottomLine, int height) {
        return bottomLine * height;
    }

    public int getArea() {
        return bottomLine * height;
    }
    public int getBottomLine() {
        return bottomLine;
    }

    public void setBottomLine(int bottomLine) {
        this.bottomLine = bottomLine;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSquareName() {
        return squareName;
    }

    public void setSquareName(String squareName) {
        this.squareName = squareName;
    }
}
