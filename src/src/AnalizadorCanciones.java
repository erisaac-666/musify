/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

/**
 *
 * @author erisaac666
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public class AnalizadorCanciones {

    // Formatos que el programa reconocerá como archivos de música
    private static final Set<String> EXTENSIONES_AUDIO = Set.of(
            "mp3",
            "wav",
            "aac",
            "m4a",
            "flac",
            "ogg"
    );

    /**
     * Busca canciones dentro de una carpeta y todas sus subcarpetas.
     *
     * @param carpeta carpeta principal que se analizará
     * @return lista con las rutas de las canciones encontradas
     @throws IOException si ocurre un error al leer la carpeta
     */
    public static List<Path> buscarCanciones(Path carpeta)
            throws IOException {

        if (carpeta == null) {
            throw new IllegalArgumentException(
                    "La carpeta no puede ser null."
            );
        }

        // Convertimos la ruta a absoluta y eliminamos elementos como "." o ".."
        carpeta = carpeta.toAbsolutePath().normalize();

        if (!Files.exists(carpeta)) {
            throw new IllegalArgumentException(
                    "La carpeta no existe: " + carpeta
            );
        }

        if (!Files.isDirectory(carpeta)) {
            throw new IllegalArgumentException(
                    "La ruta no corresponde a una carpeta: " + carpeta
            );
        }

        /*
         * Files.walk() recorre la carpeta principal y todas
         * las subcarpetas que encuentre dentro de ella.
         */
        try (Stream<Path> archivos = Files.walk(carpeta)) {
            return archivos
                    .filter(Files::isRegularFile)
                    .filter(AnalizadorCanciones::esArchivoDeAudio)
                    .sorted((ruta1, ruta2) ->
                            ruta1.getFileName()
                                    .toString()
                                    .compareToIgnoreCase(
                                            ruta2.getFileName().toString()
                                    )
                    )
                    .toList();
        }
    }

    /**
     * Comprueba si el archivo tiene una extensión de audio admitida.
     */
    private static boolean esArchivoDeAudio(Path archivo) {
        String nombreArchivo = archivo
                .getFileName()
                .toString()
                .toLowerCase(Locale.ROOT);

        int posicionPunto = nombreArchivo.lastIndexOf('.');

        // No tiene extensión o termina con un punto
        if (posicionPunto == -1
                || posicionPunto == nombreArchivo.length() - 1) {
            return false;
        }

        String extension = nombreArchivo.substring(posicionPunto + 1);

        return EXTENSIONES_AUDIO.contains(extension);
    }
}
