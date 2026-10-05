package planificador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public static List<Proceso> cargarProceso(String rutaArchivo) {
        List<Proceso> listaProcesos = new ArrayList<>();

        try {
            List<String> lineas = Files.readAllLines(Path.of(rutaArchivo));

            for (int i = 0; i < lineas.size(); i++) {
                    String lineaActual = lineas.get(i).trim();

                    if (lineaActual.isEmpty() || lineaActual.startsWith("#")) {
                        continue;
                    }

                    String[] datos = lineaActual.split(";");

                    if (datos.length != 3) {
                        throw new IllegalArgumentException("Línea mal formada(" + (i + 1) + "): se esperan 3 datos");
                    }

                    String nombre = datos[0].trim();
                    int llegada = Integer.parseInt(datos[1].trim());
                    int rafaga = Integer.parseInt(datos[2].trim());

                    if ( llegada < 0) {
                        throw new IllegalArgumentException("Error en la línea " + (i + 1) + ": La llegada no puede ser negativo");
                    }

                    if (rafaga <= 0) {
                        throw new IllegalArgumentException("Error en la línea " + (i + 1) + ": La ráfaga debe ser mayor a 0");
                    }

                    listaProcesos.add(new Proceso(nombre, llegada, rafaga));
            }
        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.err.println("Se esperan números enteros");
            System.exit(1);
        }

        return listaProcesos;
    }
}
