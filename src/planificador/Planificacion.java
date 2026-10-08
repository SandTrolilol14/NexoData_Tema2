package planificador;

import java.util.ArrayList;
import java.util.List;

public abstract class Planificacion {
    List<Proceso> pendientes = new ArrayList<>();
    List<Proceso> listos = new ArrayList<>();
    Proceso cpu = null;
    int tiempo = 0;
    int tiempoEnCPU = 0;

    // Variables para Gantt, traza y métricas
    List<String> gantt = new ArrayList<>();
    int cambiosContexto = 0;
    Proceso cpuAnterior = null;
    boolean trazaActiva = false;

    protected abstract void elegirProceso();

    protected boolean debeIrse() { return false; }

    public void simular(boolean traza) {
        this.trazaActiva = traza;

        if (trazaActiva) System.out.println("Traza de estados:");

        while (!pendientes.isEmpty() || !listos.isEmpty() || cpu != null) {

            // llegadas
            List<Proceso> lleganAhora = new ArrayList<>();
            for (Proceso p : pendientes) {
                if (p.getLlegada() == tiempo) lleganAhora.add(p);
            }
            for (Proceso p : lleganAhora) {
                if (trazaActiva) System.out.println("t=" + tiempo + " " + p.getNombre() + "\t" + p.getEstado() + " -> LISTO (llega al sistema)");
                p.setEstado(EstadoProceso.LISTO);
                listos.add(p);
                pendientes.remove(p);
            }

            // salidas
            if (cpu != null) {
                if (cpu.getRestante() == 0) {
                    cpu.setEstado(EstadoProceso.TERMINADO);
                    cpu.setFin(tiempo);
                    cpu.setRetorno(cpu.getFin() - cpu.getLlegada());
                    cpu.setEspera(cpu.getRetorno() - cpu.getRafaga());
                    cpu = null;
                } else if (debeIrse()) {
                    if (trazaActiva) System.out.println("t=" + tiempo + " " + cpu.getNombre() + "\tEJECUCION -> LISTO (agota el quantum)");
                    cpu.setEstado(EstadoProceso.LISTO);
                    listos.add(cpu);
                    cpu = null;
                }
            }

            // PASO 3: ELECCIÓN
            if (cpu == null && !listos.isEmpty()) {
                elegirProceso();
                tiempoEnCPU = 0; // Reiniciamos el reloj interno de la CPU para el RR

                if (trazaActiva) System.out.println("t=" + tiempo + " " + cpu.getNombre() + "\tLISTO -> EJECUCION (el planificador lo elige)");

                // Métrica de respuesta (solo si es su primera vez en la CPU)
                if (cpu.getRafaga() == cpu.getRestante()) {
                    cpu.setRespuesta(tiempo - cpu.getLlegada());
                }

                // Sumamos cambio de contexto (se ignora la primera carga de la CPU)
                if (cpuAnterior != null && cpuAnterior != cpu) {
                    cambiosContexto++;
                }
                cpuAnterior = cpu;
            }

            // PASO 4: EJECUCIÓN Y GANTT
            if (cpu != null) {
                gantt.add(cpu.getNombre());
                cpu.setRestante(cpu.getRestante() - 1);
                tiempoEnCPU++;
            } else if (!pendientes.isEmpty() || !listos.isEmpty()) {
                gantt.add("-"); // CPU ociosa
            }

            tiempo++;

        }
    }

    // Metodo generador de la tabla
    public void imprimirResultados(List<Proceso> originales) {
        System.out.print("\nt  ");
        for (int i = 0; i < gantt.size(); i++) System.out.printf("%2d ", i);
        System.out.print("\nCPU");
        for (String s : gantt) System.out.printf("%2s ", s);
        System.out.println("\n");

        System.out.println("Proceso | Llegada | Ráfaga | Fin | Retorno | Espera | Respuesta");
        double sumaRetorno = 0, sumaEspera = 0, sumaRespuesta = 0;

        for (Proceso p : originales) {
            System.out.printf("%7s | %7d | %6d | %3d | %7d | %6d | %9d\n",
                    p.getNombre(), p.getLlegada(), p.getRafaga(), p.getFin(), p.getRetorno(), p.getEspera(), p.getRespuesta());
            sumaRetorno += p.getRetorno();
            sumaEspera += p.getEspera();
            sumaRespuesta += p.getRespuesta();
        }

        System.out.printf("\nMedias: retorno %.2f | espera %.2f | respuesta %.2f\n",
                (sumaRetorno / originales.size()), (sumaEspera / originales.size()), (sumaRespuesta / originales.size()));
        System.out.println("Cambios de contexto: " + cambiosContexto + "\n");
    }
}
