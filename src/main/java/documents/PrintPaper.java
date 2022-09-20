package documents;

import java.util.Objects;

public class PrintPaper implements Cloneable {

    private String name;
    private int height;
    private int width;

    public PrintPaper(String name, int height, int width) {
        this.name = name;
        this.height = height;
        this.width = width;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return "PrintPaper{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintPaper printPaper = (PrintPaper) o;
        return Objects.equals(name, printPaper.name) && Objects.equals(height, printPaper.height) && Objects.equals(width, printPaper.width);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, width);
    }


}
