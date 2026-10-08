package planificador;

public class SJF extends Planificacion{

    @Override
    protected void elegirProceso() {

        int indiceMasCorto = 0;

        // buscar indiceMasCorto
        for (int i=1; i<listos.size(); i++) {
            if (listos.get(i).getRestante() < listos.get(indiceMasCorto).getRestante()) {
                indiceMasCorto = i;
            }
        }

        cpu = listos.remove(indiceMasCorto);
        cpu.setEstado(EstadoProceso.EJECUCION);

    }
}
