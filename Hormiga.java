package hl;

public class Hormiga implements IHormiga {
    private int x, y;
    private Orientacion orientacion;
    private EstadoHormiga estado = EstadoHormiga.GIRAR;

    public Hormiga() {
        this.x = 0;
        this.y = 0;
        this.orientacion = Orientacion.IZQUIERDA;
    }

    @Override
    public void mover(int incX, int incY) {
        x += incX;
        y += incY;
    }

    @Override
    public Giro girar(ICuadricula cuadricula) {
        if (x < 0 || x >= Cuadricula.DIM || y < 0 || y >= Cuadricula.DIM) {
            orientacion = orientacion.girarMediaVuelta();
            return Giro.MEDIA_VUELTA;
        }
        Casilla casillaActual = cuadricula.casilla(x, y);
        if (casillaActual.color() == 0) {
            orientacion = orientacion.girarIzquierda();
            return Giro.IZQUIERDA;
        } else if (casillaActual.color() == 1) {
            orientacion = orientacion.girarDerecha();
            return Giro.DERECHA;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void cambiarColor(ICuadricula cuadricula) {
        if (x < 0 || x >= Cuadricula.DIM || y < 0 || y >= Cuadricula.DIM) {
            return;
        }
        Casilla casillaActual = cuadricula.casilla(x, y);
        casillaActual.cambiarColor();
    }

    @Override
    public void avanzar() {
        orientacion.mover(this);
    }

    @Override
    public void tick(ICuadricula cuadricula) {
        switch (estado) {
            case GIRAR:
                girar(cuadricula);
                estado = EstadoHormiga.CAMBIAR_COLOR;
                break;
            case CAMBIAR_COLOR:
                cambiarColor(cuadricula);
                estado = EstadoHormiga.AVANZAR;
                break;
            case AVANZAR:
                avanzar();
                estado = EstadoHormiga.GIRAR;
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public int[] coordenadas() {
        return new int[] { x, y };
    }

    @Override
    public String rutaDeLaImagen() {
        return "res/ant_1.png";
    }

    @Override
    public int getAnguloEnGrados() {
        return orientacion.getAnguloEnGrados();
    }

}

enum EstadoHormiga {
    GIRAR,
    CAMBIAR_COLOR,
    AVANZAR
}