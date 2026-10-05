package planificador;

public class Proceso {
    private String nombre;
    private int llegada;
    private int rafaga;
    private int restante;
    private EstadoProceso estado;
    private int fin;
    private int retorno;
    private int espera;
    private int respuesta;

    public Proceso(String nombre, int llegada, int rafaga) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.estado = EstadoProceso.NUEVO;
        this.restante = rafaga;
    }




    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLlegada() {
        return llegada;
    }

    public void setLlegada(int llegada) {
        this.llegada = llegada;
    }

    public int getRafaga() {
        return rafaga;
    }

    public void setRafaga(int rafaga) {
        this.rafaga = rafaga;
    }

    public int getRestante() {
        return restante;
    }

    public void setRestante(int trestante) {
        this.restante = trestante;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }

    public int getFin() {
        return this.fin;
    }

    public void setFin (int fin) {
        this.fin = fin;
    }

    public int getRetorno() {
        return this.retorno;
    }

    public void setRetorno (int retorno) {
        this.retorno = retorno;
    }

    public int getEspera() {
        return this.espera;
    }

    public void setEspera (int espera) {
        this.espera = espera;
    }

    public int getRespuesta() {
        return this.respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }
}
