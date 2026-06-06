
/**
 * Clase Mapa — El Trono de la Oscuridad
 *
 * Muestra el mapa ASCII de Moonhollow en consola. Marca la ubicación actual del
 * jugador con ► AQUÍ Las zonas se desbloquean conforme avanza la historia.
 *
 * Zonas: 0 = Castillo (inicio) 1 = Bosque Sombrío 2 = Lago Mágico 3 = Torre
 * Antigua de los Dragones 4 = Núcleo del Trono (batalla final)
 */
public class Mapa {

    // ── Atributos ─────────────────────────────────────────────────────────
    private int zonaActual;
    private boolean[] desbloqueadas;

    private static final String[] NOMBRES = {
        "Castillo de Moonhollow",
        "Bosque Sombrío",
        "Lago Mágico",
        "Torre de los Dragones",
        "Núcleo del Trono"
    };

    private static final String[] ICONOS = {
        "[C]", "[B]", "[L]", "[T]", "[N]"
    };

    // ── Constructor ───────────────────────────────────────────────────────
    public Mapa() {
        zonaActual = 0;
        desbloqueadas = new boolean[] {
            true, false, false, false, false
        };
    }

    // ── Avanzar a zona ────────────────────────────────────────────────────
    public void avanzarA(int zona) {
        if (zona >= 0 && zona < desbloqueadas.length) {
            desbloqueadas[zona] = true;
            zonaActual = zona;
        }
    }

    public int getZonaActual() {
        return zonaActual;
    }

    public String getNombreZonaActual() {
        return NOMBRES[zonaActual];
    }

    // ── Mostrar mapa ──────────────────────────────────────────────────────
    public void mostrar() {

        System.out.println();
        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.println("│                MAPA — REINO DE MOONHOLLOW                  │");
        System.out.println("├────────────────────────────────────────────────────────────┤");
        System.out.println("│                                                            │");
        System.out.println("│              [Torre de los Dragones]                       │");
        System.out.println("│                       z3                                   │");
        System.out.println("│                        |                                   │");
        System.out.println("│ [Bosque]——[Castillo Moonhollow]——[Lago Mágico]             │");
        System.out.println("│    z1             z0                 z2                    │");
        System.out.println("│                        |                                   │");
        System.out.println("│               [Núcleo del Trono]                           │");
        System.out.println("│                       z4                                   │");
        System.out.println("│                                                            │");
        System.out.println("├────────────────────────────────────────────────────────────┤");

        for (int i = 0; i < NOMBRES.length; i++) {

            String estado;

            if (i == zonaActual) {
                estado = "► AQUÍ";
            } else if (desbloqueadas[i]) {
                estado = "✓ Visitada";
            } else {
                estado = "? Desconocida";
            }

            System.out.printf(
            	    "│ %-3s Zona %-1d %-24s %-14s         │%n",
            	    ICONOS[i],
            	    i,
            	    NOMBRES[i],
            	    estado
            	);
            
            
            
        }

        System.out.println("└────────────────────────────────────────────────────────────┘");
        System.out.println();
    }

    // ── Mostrar ubicación actual ─────────────────────────────────────────
    public void mostrarUbicacion() {

        System.out.println("┌────────────────────────────────────────────────────────────┐");
        System.out.printf(
            "│ %-58s │%n",
            "Ubicación: " + ICONOS[zonaActual] + " " + NOMBRES[zonaActual]
        );
        System.out.println("└────────────────────────────────────────────────────────────┘");
    }
}