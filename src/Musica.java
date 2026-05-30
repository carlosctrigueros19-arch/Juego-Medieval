
	import javax.sound.sampled.*;
	import java.io.File;
	import java.io.IOException;

	/**
	 * Clase Musica — El Trono de la Oscuridad
	 *
	 * Maneja la reproducción de música y efectos de sonido con archivos .wav
	 * usando la librería javax.sound.sampled (incluida en Java, sin dependencias).
	 *
	 * IMPORTANTE — Archivos de audio necesarios (colócalos en la carpeta /audio):
	 *   audio/menu.wav        → música del menú principal
	 *   audio/bosque.wav      → música del Bosque Sombrío
	 *   audio/lago.wav        → música del Lago Mágico
	 *   audio/torre.wav       → música de la Torre de los Dragones
	 *   audio/combate.wav     → música de combate normal
	 *   audio/jefe.wav        → música de combate contra jefe
	 *   audio/final.wav       → música de batalla final
	 *   audio/victoria.wav    → música de victoria
	 *   audio/derrota.wav     → música de derrota
	 *   audio/sfx_ataque.wav  → sonido de ataque
	 *   audio/sfx_curacion.wav→ sonido de curación
	 *
	 * FORMATOS SOPORTADOS: .wav (recomendado), .aiff, .au
	 * NOTA: Java estándar NO soporta .mp3 sin librerías externas.
	 *       Convierte tus mp3 a wav con Audacity (gratis) o convertio.co
	 *
	 * ESTRUCTURA DE CARPETAS EN ECLIPSE:
	 *   MiProyecto/
	 *   ├── src/
	 *   │   └── (todos los .java)
	 *   └── audio/
	 *       └── (todos los .wav)
	 */
	public class Musica {

	    // ── Atributos ─────────────────────────────────────────────────────────
	    private static Clip   clipMusica;    // música de fondo en loop
	    private static Clip   clipEfecto;   // efectos de sonido cortos
	    private static float  volumen = 0.8f; // 0.0 = silencio, 1.0 = máximo
	    private static boolean musicaActiva = true;
	    private static boolean efectosActivos = true;

	    // Rutas de los archivos de audio
	    private static final String RUTA = "audio/";

	    // ── Reproducir música en loop ─────────────────────────────────────────
	    /**
	     * Reproduce un archivo .wav en loop continuo.
	     * Si ya había música, la detiene primero.
	     * @param archivo  nombre del archivo, ej: "menu.wav"
	     */
	    public static void reproducirMusica(String archivo) {
	        if (!musicaActiva) return;

	        detenerMusica(); // detener lo que suena antes

	        try {
	            File archivoAudio = new File(RUTA + archivo);
	            if (!archivoAudio.exists()) {
	                // Si no existe el archivo, continúa sin música (no crashea)
	                return;
	            }

	            AudioInputStream stream = AudioSystem.getAudioInputStream(archivoAudio);
	            clipMusica = AudioSystem.getClip();
	            clipMusica.open(stream);

	            // Ajustar volumen
	            ajustarVolumen(clipMusica, volumen);

	            // Reproducir en loop infinito
	            clipMusica.loop(Clip.LOOP_CONTINUOUSLY);
	            clipMusica.start();

	        } catch (UnsupportedAudioFileException e) {
	            // Formato no soportado — el juego sigue sin música
	        } catch (LineUnavailableException e) {
	            // Audio no disponible en este sistema
	        } catch (IOException e) {
	            // Error leyendo el archivo
	        }
	    }

	    // ── Reproducir efecto de sonido (sin loop) ────────────────────────────
	    /**
	     * Reproduce un sonido corto una sola vez (ataques, curaciones, etc.)
	     * @param archivo  nombre del archivo, ej: "sfx_ataque.wav"
	     */
	    public static void reproducirEfecto(String archivo) {
	        if (!efectosActivos) return;

	        try {
	            File archivoAudio = new File(RUTA + archivo);
	            if (!archivoAudio.exists()) return;

	            AudioInputStream stream = AudioSystem.getAudioInputStream(archivoAudio);
	            clipEfecto = AudioSystem.getClip();
	            clipEfecto.open(stream);
	            ajustarVolumen(clipEfecto, volumen);
	            clipEfecto.start();

	        } catch (Exception e) {
	            // Silencio si falla — el juego nunca se cae por audio
	        }
	    }

	    // ── Detener música ────────────────────────────────────────────────────
	    public static void detenerMusica() {
	        if (clipMusica != null && clipMusica.isRunning()) {
	            clipMusica.stop();
	            clipMusica.close();
	        }
	    }

	    // ── Pausar / reanudar ─────────────────────────────────────────────────
	    public static void pausarMusica() {
	        if (clipMusica != null && clipMusica.isRunning()) {
	            clipMusica.stop();
	        }
	    }

	    public static void reanudarMusica() {
	        if (clipMusica != null && !clipMusica.isRunning()) {
	            clipMusica.start();
	        }
	    }

	    // ── Ajustar volumen ───────────────────────────────────────────────────
	    /**
	     * @param nuevoVolumen  valor entre 0.0 (silencio) y 1.0 (máximo)
	     */
	    public static void setVolumen(float nuevoVolumen) {
	        volumen = Math.max(0.0f, Math.min(1.0f, nuevoVolumen));
	        if (clipMusica != null) ajustarVolumen(clipMusica, volumen);
	    }

	    private static void ajustarVolumen(Clip clip, float vol) {
	        try {
	            FloatControl control = (FloatControl)
	                clip.getControl(FloatControl.Type.MASTER_GAIN);
	            // Convertir de escala 0-1 a decibeles
	            float dB = (float) (Math.log10(Math.max(vol, 0.0001)) * 20);
	            control.setValue(Math.max(control.getMinimum(),
	                             Math.min(control.getMaximum(), dB)));
	        } catch (IllegalArgumentException e) {
	            // El control de volumen no está disponible en este sistema
	        }
	    }

	    // ── Activar / desactivar ──────────────────────────────────────────────
	    public static void toggleMusica() {
	        musicaActiva = !musicaActiva;
	        if (!musicaActiva) detenerMusica();
	        System.out.println("  Música: " + (musicaActiva ? "ON" : "OFF"));
	    }

	    public static void toggleEfectos() {
	        efectosActivos = !efectosActivos;
	        System.out.println("  Efectos de sonido: " + (efectosActivos ? "ON" : "OFF"));
	    }

	    // ── Métodos de conveniencia por escena ────────────────────────────────
	    public static void musicaMenu()    { reproducirMusica("menu.wav");    }
	    public static void musicaBosque()  { reproducirMusica("bosque.wav");  }
	    public static void musicaLago()    { reproducirMusica("lago.wav");    }
	    public static void musicaTorre()   { reproducirMusica("torre.wav");   }
	    public static void musicaCombate() { reproducirMusica("combate.wav"); }
	    public static void musicaJefe()    { reproducirMusica("jefe.wav");    }
	    public static void musicaFinal()   { reproducirMusica("final.wav");   }
	    public static void musicaVictoria(){ reproducirMusica("victoria.wav");}
	    public static void musicaDerrota() { reproducirMusica("derrota.wav"); }

	    public static void sfxAtaque()     { reproducirEfecto("sfx_ataque.wav");   }
	    public static void sfxCuracion()   { reproducirEfecto("sfx_curacion.wav"); }
	    public static void sfxError()      { reproducirEfecto("sfx_error.wav");    }
	}

}
