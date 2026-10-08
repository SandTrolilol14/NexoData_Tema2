package planificador;

public class FCFS extends Planificacion{

    @Override
    protected void elegirProceso() {
       cpu = listos.remove(0);

       cpu.setEstado(EstadoProceso.EJECUCION);
    }
}
