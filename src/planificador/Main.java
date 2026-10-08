package planificador;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * PSP · Tema 2 · Simulador de planificación para NexoData
 *
 * Punto de entrada. Este fichero YA ESTÁ HECHO: solo lee y comprueba los argumentos.
 * No cambies cómo se leen: el profesor ejecutará tu programa siempre así:
 *
 *   java planificador.Main <fichero.csv> <fcfs|sjf|rr|todos> [quantum] [--traza]
 *
 * Todo lo demás (modelo del proceso, lectura del CSV, algoritmos, métricas,
 * informe por consola...) lo diseñas y programas tú en este mismo paquete.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java planificador.Main <fichero.csv> <fcfs|sjf|rr|todos> [quantum] [--traza]");
            System.exit(1);
        }
        Path fichero = Path.of(args[0]);
        String algoritmo = args[1].toLowerCase();
        boolean traza = List.of(args).contains("--traza");
        int quantum = 2;
        if (args.length >= 3 && !args[2].startsWith("--")) {
            try {
                quantum = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("El quantum debe ser un número entero: " + args[2]);
                System.exit(1);
            }
        }
        if (!Files.exists(fichero)) {
            System.err.println("No encuentro el fichero " + fichero.toAbsolutePath()
                    + "\nComprueba el «Working directory» de la configuración de ejecución.");
            System.exit(1);
        }
        if (!List.of("fcfs", "sjf", "rr", "todos").contains(algoritmo)) {
            System.err.println("Algoritmo desconocido: " + algoritmo + " (usa fcfs, sjf, rr o todos)");
            System.exit(1);
        }

        System.out.println("Fichero: " + fichero + " | algoritmo: " + algoritmo
                + " | quantum: " + quantum + " | traza: " + traza);

        // TODO (tareas 1 a 3): a partir de aquí, lee los procesos del fichero,
        // simula el algoritmo o algoritmos pedidos y muestra los resultados.
        // Cuando lo tengas, borra el println de arriba y este comentario.
        List<Proceso> misProcesos = LectorCSV.cargarProceso(fichero.toString());

        if (algoritmo.equals("fcfs") || algoritmo.equals("todos")) {
            System.out.println("=== FCFS ===");
            FCFS simuladorFCFS = new FCFS();
            List<Proceso> copia = clonarLista(misProcesos);
            simuladorFCFS.pendientes.addAll(copia);
            simuladorFCFS.simular(traza);
            simuladorFCFS.imprimirResultados(copia);
        }

        if (algoritmo.equals("sjf") || algoritmo.equals("todos")) {
            System.out.println("=== SJF ===");
            SJF simuladorSJF = new SJF();
            List<Proceso> copia = clonarLista(misProcesos);
            simuladorSJF.pendientes.addAll(copia);
            simuladorSJF.simular(traza);
            simuladorSJF.imprimirResultados(copia);
        }

        if (algoritmo.equals("rr") || algoritmo.equals("todos")) {
            System.out.println("=== Round Robin (q=" + quantum + ") ===");
            RR simuladorRR = new RR(quantum);
            List<Proceso> copia = clonarLista(misProcesos);
            simuladorRR.pendientes.addAll(copia);
            simuladorRR.simular(traza);
            simuladorRR.imprimirResultados(copia);
        }
    }

    // Pega este método clonador justo antes de la ÚLTIMA llave '}' de tu archivo Main
    public static List<Proceso> clonarLista(List<Proceso> originales) {
        List<Proceso> copia = new ArrayList<>();
        for (Proceso p : originales) {
            copia.add(new Proceso(p.getNombre(), p.getLlegada(), p.getRafaga()));
        }
        return copia;
    }
}
