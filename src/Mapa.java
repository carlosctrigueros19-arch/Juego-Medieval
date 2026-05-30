
	/**
 * Clase Mapa — El Trono de la Oscuridad
 *
 * Muestra el mapa ASCII de Moonhollow en consola.
 * Marca la ubicación actual del jugador con ► AQUÍ
 * Las zonas se desbloquean conforme avanza la historia.
 *
 * Zonas:
 *   0 = Castillo (inicio)
 *   1 = Bosque Sombrío
 *   2 = Lago Mágico
 *   3 = Torre Antigua de los Dragones
 *   4 = Núcleo del Trono (batalla final)
 */
public class Mapa {

    // ── Atributos ─────────────────────────────────────────────────────────
    private int       zonaActual;
    private boolean[] desbloqueadas;

    private static final String[] NOMBRES = {
        "Castillo de Moonhollow",
        "Bosque Sombrío",
        "Lago Mágico",
        "Torre de los Dragones",
        "Núcleo del Trono"
    };

    private static final String[] ICONOS = {
        "🏰", "🌲", "🌊", "🏔", "🌑"
    };

    // ── Constructor ───────────────────────────────────────────────────────
    public Mapa() {
        this.zonaActual    = 0;
        this.desbloqueadas = new boolean[]{ true, false, false, false, false };
    }

    // ── Avanzar a zona ────────────────────────────────────────────────────
    public void avanzarA(int zona) {
        if (zona >= 0 && zona < desbloqueadas.length) {
            desbloqueadas[zona] = true;
            zonaActual          = zona;
        }
    }

    public int    getZonaActual()         { return zonaActual; }
    public String getNombreZonaActual()   { return NOMBRES[zonaActual]; }

    // ── Mapa completo ─────────────────────────────────────────────────────
    public void mostrar() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║          MAPA — REINO DE MOONHOLLOW              ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.println("  ║                                                  ║");
        System.out.println("  ║          [Torre de los Dragones]                 ║");
        System.out.println("  ║                 🏔  zona 3                       ║");
        System.out.println("  ║                    |                             ║");
        System.out.println("  ║  [Bosque]——[Castillo Moonhollow]——[Lago Mágico]  ║");
        System.out.println("  ║   🌲 z1        🏰 zona 0          🌊 zona 2      ║");
        System.out.println("  ║                    |                             ║");
        System.out.println("  ║             [Núcleo del Trono]                   ║");
        System.out.println("  ║                 🌑 zona 4                        ║");
        System.out.println("  ║                                                  ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");

        for (int i = 0; i < NOMBRES.length; i++) {
            String estado;
            if (i == zonaActual) {
                estado = "► AQUÍ";
            } else if (desbloqueadas[i]) {
                estado = "✓ Visitada";
            } else {
                estado = "? Desconocida";
            }
            System.out.printf("  ║  %s Zona %d: %-24s %-12s║%n",
                ICONOS[i], i, NOMBRES[i], estado);
        }

        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    // ── Ubicación compacta ────────────────────────────────────────────────
    /**
     * Muestra una línea con la zona actual — se llama antes de cada encuentro.
     * Siempre lee zonaActual en el momento de llamarse, nunca queda desactualizada.
     */
    public void mostrarUbicacion() {
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.printf ("  ║  📍 %-34s║%n",
            "Ubicación: " + ICONOS[zonaActual] + " " + NOMBRES[zonaActual]);
        System.out.println("  ╚══════════════════════════════════════╝");
    }
}