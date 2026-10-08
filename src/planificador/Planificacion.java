package planificador;

import java.util.ArrayList;
import java.util.List;

public abstract class Planificacion {
    List<Proceso> pendientes = new ArrayList<>();
    List<Proceso> listos = new ArrayList<>();
    Proceso cpu = null;
    int tiempo = 0;

    protected abstract void elegirProceso();

    void simular() {
        while (!pendientes.isEmpty() || !listos.isEmpty() || cpu != null) {

            // llegadas
            for (int i = pendientes.size() -1; i >= 0; i--) {
                Proceso p = pendientes.get(i);
                if (p.getLlegada() == tiempo) {
                    p.setEstado(EstadoProceso.LISTO);
                    listos.add(p);
                    pendientes.remove(i);
                }
            }

            // salidas
            if (cpu != null && cpu.getRestante() == 0) {
                cpu.setEstado(EstadoProceso.TERMINADO);
                cpu.setFin(tiempo);

                cpu.setRetorno(cpu.getFin() - cpu.getLlegada());
                cpu.setEspera(cpu.getRetorno() - cpu.getRafaga());

                cpu = null;
            }

            // elección
            if (cpu == null && !listos.isEmpty()) {
                elegirProceso();
            }

            // ejecución
            if (cpu != null) {
                cpu.setRestante(cpu.getRestante() -1);
            }

            tiempo++;

        }
    }
}
