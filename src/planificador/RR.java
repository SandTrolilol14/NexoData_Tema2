package planificador;

public class RR extends Planificacion{

    private int quantum;

    public RR(int quantum) {
        this.quantum = quantum;
    }

    @Override
    protected void elegirProceso() {
        cpu = listos.remove(0);
        cpu.setEstado(EstadoProceso.EJECUCION);
        tiempoEnCPU = 0;
    }

    @Override
    protected boolean debeIrse() {
        return tiempoEnCPU == quantum;
    }
}
