import javax.sound.sampled.*;
import java.io.File;

/**
 * Clase Musica — El Trono de la Oscuridad
 *
 * Archivos necesarios en carpeta /audio:
 *   zona.wav      → menú principal + las 3 zonas
 *   combate.wav   → combates normales y jefes
 *   final.wav     → batalla final
 *   victoria.wav  → victoria
 *   derrota.wav   → game over
 */
public class Musica {

    private static Clip    clipMusica;
    private static float   volumen        = 0.8f;
    private static boolean musicaActiva   = true;
    private static boolean efectosActivos = true;
    private static String  archivoActual  = "";  // recuerda qué estaba sonando
    private static final String RUTA      = "audio/";

    // ── Reproducir música en loop ─────────────────────────────────────────
    public static void reproducirMusica(String archivo) {
        if (!musicaActiva) {
            archivoActual = archivo;  // guarda para cuando se reactive
            return;
        }

        // Si ya está sonando el mismo archivo, no reiniciar
        if (archivo.equals(archivoActual) && clipMusica != null
                && clipMusica.isRunning()) return;

        detenerMusica();
        archivoActual = archivo;

        try {
            File f = new File(RUTA + archivo);
            if (!f.exists()) return;
            AudioInputStream stream = AudioSystem.getAudioInputStream(f);
            clipMusica = AudioSystem.getClip();
            clipMusica.open(stream);
            ajustarVolumen(clipMusica, volumen);
            clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusica.start();
        } catch (Exception e) {
            // El juego nunca se cae por audio
        }
    }

    // ── Detener ───────────────────────────────────────────────────────────
    public static void detenerMusica() {
        try {
            if (clipMusica != null) {
                if (clipMusica.isRunning()) clipMusica.stop();
                clipMusica.close();
                clipMusica = null;
            }
        } catch (Exception e) {}
    }

    // ── Toggle música — ahora sí reactiva correctamente ───────────────────
    public static void toggleMusica() {
        musicaActiva = !musicaActiva;
        if (!musicaActiva) {
            detenerMusica();
            System.out.println("  Música: OFF");
        } else {
            System.out.println("  Música: ON");
            // Retoma el archivo que estaba sonando antes de apagar
            if (!archivoActual.isEmpty()) {
                String retomar = archivoActual;
                archivoActual  = "";  // limpiar para forzar reinicio
                reproducirMusica(retomar);
            }
        }
    }

    public static void toggleEfectos() {
        efectosActivos = !efectosActivos;
        System.out.println("  Efectos: " + (efectosActivos ? "ON" : "OFF"));
    }

    // ── Volumen ───────────────────────────────────────────────────────────
    public static void setVolumen(float v) {
        volumen = Math.max(0.0f, Math.min(1.0f, v));
        if (clipMusica != null) ajustarVolumen(clipMusica, volumen);
    }

    private static void ajustarVolumen(Clip clip, float vol) {
        try {
            FloatControl fc = (FloatControl)
                clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float)(Math.log10(Math.max(vol, 0.0001)) * 20);
            fc.setValue(Math.max(fc.getMinimum(), Math.min(fc.getMaximum(), dB)));
        } catch (Exception e) {}
    }

    // ── Métodos por escena ────────────────────────────────────────────────
    public static void musicaMenu()     { reproducirMusica("zona.wav");    }  // menú usa zona.wav
    public static void musicaBosque()   { reproducirMusica("zona.wav");    }
    public static void musicaLago()     { reproducirMusica("zona.wav");    }
    public static void musicaTorre()    { reproducirMusica("zona.wav");    }
    public static void musicaCombate()  { reproducirMusica("combate.wav"); }
    public static void musicaJefe()     { reproducirMusica("combate.wav"); }
    public static void musicaFinal()    { reproducirMusica("final.wav");   }
    public static void musicaVictoria() { reproducirMusica("victoria.wav");}
    public static void musicaDerrota()  { reproducirMusica("derrota.wav"); }
}