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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Reproducer {

    private Clip clip;
    private Path cancionActual;

    /**
     * Reproduce un archivo de audio desde una ruta relativa
     * o absoluta del sistema.
     *
     * @param ruta ruta del archivo de audio
     */
    public synchronized void reproducir(Path ruta) {

        if (ruta == null) {
            System.err.println(
                    "La ruta de la canción no puede ser null."
            );
            return;
        }

        Path archivo = ruta
                .toAbsolutePath()
                .normalize();

        if (!Files.exists(archivo)) {
            System.err.println(
                    "El archivo no existe: " + archivo
            );
            return;
        }

        if (!Files.isRegularFile(archivo)) {
            System.err.println(
                    "La ruta no corresponde a un archivo: " + archivo
            );
            return;
        }

        if (!Files.isReadable(archivo)) {
            System.err.println(
                    "No hay permisos para leer el archivo: " + archivo
            );
            return;
        }

        // Liberamos la canción anterior antes de cargar la nueva
        cerrarClipActual();

        try (AudioInputStream audio =
                     AudioSystem.getAudioInputStream(archivo.toFile())) {

            clip = AudioSystem.getClip();
            clip.open(audio);

            cancionActual = archivo;

            // Inicia desde el principio
            clip.setFramePosition(0);
            clip.start();

            System.out.println(
                    "Reproduciendo: " + archivo.getFileName()
            );

        } catch (UnsupportedAudioFileException e) {
            System.err.println(
                    "El formato de audio no es compatible: " + archivo
            );
            cerrarClipActual();

        } catch (LineUnavailableException e) {
            System.err.println(
                    "El dispositivo de audio no está disponible."
            );
            cerrarClipActual();

        } catch (IOException e) {
            System.err.println(
                    "No se pudo leer el archivo: " + archivo
            );
            cerrarClipActual();
        }
    }

    /**
     * Permite enviar la ruta como String.
     */
    public void reproducir(String ruta) {
        reproducir(Path.of(ruta));
    }

    /**
     * Pausa la reproducción conservando la posición.
     */
    public synchronized void pausar() {
        if (clip != null && clip.isOpen() && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Continúa desde la posición actual.
     */
    public synchronized void continuar() {
        if (clip != null
                && clip.isOpen()
                && !clip.isRunning()
                && clip.getFramePosition() < clip.getFrameLength()) {

            clip.start();
        }
    }

    /**
     * Detiene la reproducción y regresa al inicio.
     */
    public synchronized void detener() {
        if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.setFramePosition(0);
        }
    }

    /**
     * Cierra la canción y libera los recursos de audio.
     */
    public synchronized void cerrar() {
        cerrarClipActual();
    }

    /**
     * Indica si existe una canción reproduciéndose.
     */
    public synchronized boolean estaReproduciendo() {
        return clip != null
                && clip.isOpen()
                && clip.isRunning();
    }

    /**
     * Obtiene la canción cargada actualmente.
     */
    public synchronized Path getCancionActual() {
        return cancionActual;
    }

    /**
     * Devuelve la duración total en microsegundos.
     */
    public synchronized long getDuracionMicrosegundos() {
        if (clip == null || !clip.isOpen()) {
            return 0;
        }

        return clip.getMicrosecondLength();
    }

    /**
     * Devuelve la posición actual en microsegundos.
     */
    public synchronized long getPosicionMicrosegundos() {
        if (clip == null || !clip.isOpen()) {
            return 0;
        }

        return clip.getMicrosecondPosition();
    }

    /**
     * Cambia la posición de reproducción.
     */
    public synchronized void cambiarPosicion(long microsegundos) {
        if (clip == null || !clip.isOpen()) {
            return;
        }

        // Impide establecer una posición negativa o superior a la duración
        long posicionValida = Math.max(
                0,
                Math.min(
                        microsegundos,
                        clip.getMicrosecondLength()
                )
        );

        clip.setMicrosecondPosition(posicionValida);
    }

    /**
     * Método interno para liberar el Clip actual.
     */
    private void cerrarClipActual() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
        }

        cancionActual = null;
    }
}