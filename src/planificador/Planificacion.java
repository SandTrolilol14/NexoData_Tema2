package planificador;

import java.util.List;

public abstract class Planificacion {
    List<Proceso> Pendientes;
    List<Proceso> Listos;
    String cpu = null;
    int tiempo = 0;

    protected abstract void elegirProceso();

    void simular() {
        while (!Pendientes.isEmpty()) {

        }
    }
}
