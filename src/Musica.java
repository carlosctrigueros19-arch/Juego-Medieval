import javax.sound.sampled.*;

public class Musica {

    private static Clip clipMusica;
    private static float volumen = 0.8f;
    private static boolean musicaActiva = true;
    private static boolean efectosActivos = true; // 🔥 FALTABA ESTO
    private static String archivoActual = "";

    // ── REPRODUCIR MÚSICA ───────────────────────────────
    public static void reproducirMusica(String archivo) {

        if (!musicaActiva) {
            archivoActual = archivo;
            return;
        }

        if (archivo.equals(archivoActual) && clipMusica != null && clipMusica.isRunning()) {
            return;
        }

        detenerMusica();
        archivoActual = archivo;

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(
                Musica.class.getResource("/audio/" + archivo)
            );

            if (stream == null) {
                System.out.println("No se encontró el audio: " + archivo);
                return;
            }

            clipMusica = AudioSystem.getClip();
            clipMusica.open(stream);

            ajustarVolumen(clipMusica, volumen);

            clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusica.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── DETENER MÚSICA ───────────────────────────────
    public static void detenerMusica() {
        try {
            if (clipMusica != null) {
                clipMusica.stop();
                clipMusica.close();
                clipMusica = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── TOGGLE MÚSICA (🔥 TE FALTABA) ─────────────────
    public static void toggleMusica() {
        musicaActiva = !musicaActiva;

        if (!musicaActiva) {
            detenerMusica();
            System.out.println("Música OFF");
        } else {
            System.out.println("Música ON");

            if (!archivoActual.isEmpty()) {
                String temp = archivoActual;
                archivoActual = "";
                reproducirMusica(temp);
            }
        }
    }

    // ── TOGGLE EFECTOS (🔥 TE FALTABA) ─────────────────
    public static void toggleEfectos() {
        efectosActivos = !efectosActivos;
        System.out.println("Efectos: " + (efectosActivos ? "ON" : "OFF"));
    }

    // ── VOLUMEN ───────────────────────────────
    public static void setVolumen(float v) {
        volumen = Math.max(0f, Math.min(1f, v));

        if (clipMusica != null) {
            ajustarVolumen(clipMusica, volumen);
        }
    }

    private static void ajustarVolumen(Clip clip, float vol) {
        try {
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log10(Math.max(vol, 0.0001)) * 20);
            fc.setValue(dB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ── MÉTODOS DE ESCENA ───────────────────────────────
    public static void musicaMenu()     { reproducirMusica("zona.wav"); }
    public static void musicaBosque()   { reproducirMusica("zona.wav"); }
    public static void musicaLago()     { reproducirMusica("zona.wav"); }
    public static void musicaTorre()    { reproducirMusica("zona.wav"); }

    public static void musicaCombate()  { reproducirMusica("combate.wav"); }
    public static void musicaJefe()     { reproducirMusica("combate.wav"); }

    public static void musicaFinal()    { reproducirMusica("final.wav"); }
    public static void musicaVictoria() { reproducirMusica("victoria.wav"); }
    public static void musicaDerrota()  { reproducirMusica("derrota.wav"); }
}