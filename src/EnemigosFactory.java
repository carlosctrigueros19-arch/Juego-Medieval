
	/**
	 * Fábrica de enemigos — El Trono de la Oscuridad
	 *
	 * Centraliza la creación de todos los enemigos del juego.
	 * Los enemigos comunes escalan con el nivel de zona (1=Bosque, 2=Lago, 3=Torre).
	 * Los jefes tienen estadísticas fijas y ataques especiales cada 3 turnos.
	 *
	 * USO:
	 *   Enemigo e  = EnemigosFactory.crearEnemigo("sombra", 1);   // zona Bosque
	 *   Enemigo e2 = EnemigosFactory.crearEnemigo("sombra", 2);   // zona Lago, más fuerte
	 *   Enemigo j  = EnemigosFactory.crearJefe(1);                 // jefe del Bosque
	 */
	public class EnemigosFactory {

	    // ── Factor de escala por zona ─────────────────────────────────────────
	    // Zona 1 = x1.0  |  Zona 2 = x1.4  |  Zona 3 = x1.8
	    private static final double[] ESCALA = { 0, 1.0, 1.4, 1.8 };

	    // ── Enemigos comunes (aparecen en las 3 zonas escalados) ──────────────

	    /**
	     * Crea un enemigo común según su tipo y zona.
	     * @param tipo  "sombra" | "raiz" | "criatura" | "dragon_menor"
	     * @param zona  1 (Bosque), 2 (Lago), 3 (Torre)
	     */
	    public static Enemigo crearEnemigo(String tipo, int zona) {
	        double e = ESCALA[Math.max(1, Math.min(3, zona))];

	        switch (tipo.toLowerCase()) {

	            case "sombra":
	                // Criatura rápida y ágil, baja defensa pero daño decente
	                return new Enemigo(
	                    "Criatura de Sombra",
	                    "Una silueta oscura nacida de la corrupción",
	                    escalar(60, e),   // vida
	                    escalar(22, e),   // ataque
	                    escalar(5,  e),   // defensa
	                    15,               // maná recompensa
	                    false
	                );

	            case "raiz":
	                // Tanque lento, alta defensa, bajo daño
	                return new Enemigo(
	                    "Raíz Corrompida",
	                    "Raíz gigante animada por magia oscura",
	                    escalar(90, e),
	                    escalar(15, e),
	                    escalar(18, e),
	                    20,
	                    false
	                );

	            case "criatura":
	                // Equilibrada, daño y vida moderados
	                return new Enemigo(
	                    "Bestia Corrompida",
	                    "Animal salvaje consumido por la oscuridad",
	                    escalar(75, e),
	                    escalar(28, e),
	                    escalar(10, e),
	                    18,
	                    false
	                );

	            case "dragon_menor":
	                // Solo en Torre (zona 3), daño alto
	                return new Enemigo(
	                    "Dragón Menor",
	                    "Cría de dragón corrompida por el Trono",
	                    escalar(100, e),
	                    escalar(35, e),
	                    escalar(12, e),
	                    25,
	                    false
	                );

	            default:
	                // Enemigo genérico por si se pasa un tipo desconocido
	                return new Enemigo(
	                    "Espectro",
	                    "Una presencia oscura sin forma definida",
	                    escalar(55, e),
	                    escalar(20, e),
	                    escalar(8,  e),
	                    12,
	                    false
	                );
	        }
	    }

	    // ── Jefes de zona ─────────────────────────────────────────────────────

	    /**
	     * Crea el jefe correspondiente a cada zona.
	     * @param zona  1 = Bosque Sombrío
	     *              2 = Lago Mágico
	     *              3 = Torre de los Dragones
	     *              4 = Jefe final
	     */
	    public static Enemigo crearJefe(int zona) {
	        switch (zona) {

	            case 1: // ── Jefe del Bosque Sombrío ──
	                return new Enemigo(
	                    "Señor de las Raíces",
	                    "Guardián ancestral del bosque corrompido",
	                    200,   // vida
	                    25,    // ataque
	                    15,    // defensa
	                    50,    // maná recompensa
	                    true
	                );

	            case 2: // ── Jefe del Lago Mágico ──
	                return new Enemigo(
	                    "Criatura Acuática Corrompida",
	                    "Gigante del lago deformado por energía del Trono",
	                    220,
	                    30,
	                    18,
	                    55,
	                    true
	                );

	            case 3: // ── Jefe de la Torre ──
	                return new Enemigo(
	                    "Dragón Guardián Antiguo",
	                    "El más viejo y poderoso de los dragones corrompidos",
	                    250,
	                    35,
	                    20,
	                    70,
	                    true
	                );

	            case 4: // ── Jefe Final ──
	                // Si el jugador es Princesa o Caballero → enfrenta al Hechicero
	                // Si el jugador es Hechicero → enfrenta a Princesa+Caballero
	                // Esta versión es el Hechicero como enemigo final
	                return new Enemigo(
	                    "El Hechicero Desterrado",
	                    "Su poder alcanza el máximo con los 3 fragmentos reunidos",
	                    250,
	                    40,
	                    10,
	                    0,    // sin recompensa — es el final
	                    true
	                );

	            default:
	                return crearJefe(1);
	        }
	    }

	    /**
	     * Devuelve el jefe final alternativo cuando el jugador ES el Hechicero.
	     * En ese caso enfrenta a la Princesa y el Caballero aliados.
	     */
	    public static Enemigo crearJefeFinalHechicero() {
	        return new Enemigo(
	            "Princesa & Caballero Unidos",
	            "Juntos canalizan la luz sagrada del Cetro y Escalibur",
	            420,
	            55,
	            30,
	            0,
	            true
	        );
	    }

	    // ── Arrays de enemigos por zona ───────────────────────────────────────

	    /**
	     * Devuelve la lista de enemigos comunes para una zona en orden de aparición.
	     * Se usan en Combate.java para iterar los encuentros.
	     */
	    public static Enemigo[] enemigosDeZona(int zona) {
	        switch (zona) {
	            case 1: // Bosque — 3 encuentros antes del jefe
	                return new Enemigo[]{
	                    crearEnemigo("sombra",   1),
	                    crearEnemigo("raiz",     1),
	                    crearEnemigo("criatura", 1)
	                };
	            case 2: // Lago — 3 encuentros
	                return new Enemigo[]{
	                    crearEnemigo("criatura", 2),
	                    crearEnemigo("sombra",   2),
	                    crearEnemigo("raiz",     2)
	                };
	            case 3: // Torre — 3 encuentros con dragones
	                return new Enemigo[]{
	                    crearEnemigo("dragon_menor", 3),
	                    crearEnemigo("sombra",       3),
	                    crearEnemigo("dragon_menor", 3)
	                };
	            default:
	                return new Enemigo[]{ crearEnemigo("sombra", 1) };
	        }
	    }

	    // ── Utilidad interna ──────────────────────────────────────────────────
	    /** Aplica el factor de escala y devuelve el entero redondeado. */
	    private static int escalar(int base, double factor) {
	        return (int) Math.round(base * factor);
	    }
	}


