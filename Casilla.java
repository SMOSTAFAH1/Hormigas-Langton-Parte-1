package hl;

public class Casilla {
    private int color;

    public Casilla() {
        this.color = 0;
    }

    public void cambiarColor() {
        color = (color == 0) ? 1 : 0;
    }

    public int color() {
        return color;
    }
}