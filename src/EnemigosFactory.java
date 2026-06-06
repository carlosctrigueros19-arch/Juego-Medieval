
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

		/**
		 * Fábrica de enemigos — El Trono de la Oscuridad
		 * 
		 * crearEnemigo()       → enemigos para Princesa / Caballero (criaturas corrompidas)
		 * crearEnemigoHechicero() → enemigos para el Hechicero (fuerzas del reino)
		 * enemigosDeZonaHechicero() → lista por zona para el Hechicero
		 */
		

		    private static final double[] ESCALA = { 0, 1.0, 1.4, 1.8 };

		    // ══════════════════════════════════════════════════════════════════════
		    //  ENEMIGOS ORIGINALES — Princesa y Caballero (sin cambios)
		    // ══════════════════════════════════════════════════════════════════════

		    public static Enemigo crearEnemigo(String tipo, int zona) {
		        double e = ESCALA[Math.max(1, Math.min(3, zona))];
		        switch (tipo.toLowerCase()) {
		            case "sombra":
		                return new Enemigo("Criatura de Sombra",
		                    "Una silueta oscura nacida de la corrupción",
		                    escalar(60, e), escalar(22, e), escalar(5, e), 15, false);
		            case "raiz":
		                return new Enemigo("Raíz Corrompida",
		                    "Raíz gigante animada por magia oscura",
		                    escalar(90, e), escalar(15, e), escalar(18, e), 20, false);
		            case "criatura":
		                return new Enemigo("Bestia Corrompida",
		                    "Animal salvaje consumido por la oscuridad",
		                    escalar(75, e), escalar(28, e), escalar(10, e), 18, false);
		            case "dragon_menor":
		                return new Enemigo("Dragón Menor",
		                    "Cría de dragón corrompida por el Trono",
		                    escalar(100, e), escalar(35, e), escalar(12, e), 25, false);
		            default:
		                return new Enemigo("Espectro",
		                    "Una presencia oscura sin forma definida",
		                    escalar(55, e), escalar(20, e), escalar(8, e), 12, false);
		        }
		    }

		    // ══════════════════════════════════════════════════════════════════════
		    //  ENEMIGOS DEL HECHICERO — Fuerzas del reino que lo persiguen
		    // ══════════════════════════════════════════════════════════════════════

		    /**
		     * Crea un enemigo del reino para el Hechicero.
		     * @param tipo  "guardian" | "explorador" | "patrulla" | "mago_reino"
		     *              | "caballero_explorador" | "arquera" | "capitan"
		     *              | "paladin" | "escoltas"
		     * @param zona  1 (Bosque), 2 (Lago), 3 (Torre)
		     */
		    public static Enemigo crearEnemigoHechicero(String tipo, int zona) {
		        double e = ESCALA[Math.max(1, Math.min(3, zona))];

		        switch (tipo.toLowerCase()) {

		            // ── ZONA 1: Bosque Sombrío ─────────────────────────────────
		            case "guardian":
		                // Tanque lento, alta defensa — primer obstáculo del reino
		                return new Enemigo(
		                    "Guardián del Bosque",
		                    "Soldado del reino que protege las rutas del bosque",
		                    escalar(80, e),   // vida alta
		                    escalar(16, e),   // ataque bajo
		                    escalar(20, e),   // defensa muy alta
		                    18,
		                    false
		                );

		            case "explorador":
		                // Rápido y ágil, bajo daño — explorador enviado a vigilar
		                return new Enemigo(
		                    "Explorador del Reino",
		                    "Rastreador élite enviado a capturar al hechicero",
		                    escalar(55, e),   // vida baja
		                    escalar(20, e),   // daño moderado
		                    escalar(8,  e),   // poca armadura
		                    15,
		                    false
		                );

		            case "patrulla":
		                // Equilibrado — soldado estándar del reino
		                return new Enemigo(
		                    "Patrulla Real",
		                    "Soldados del reino en misión de captura",
		                    escalar(70, e),
		                    escalar(22, e),
		                    escalar(12, e),
		                    16,
		                    false
		                );

		            // ── ZONA 2: Lago Mágico ────────────────────────────────────
		            case "mago_reino":
		                // Frágil pero daño mágico alto — rival directo del Hechicero
		                return new Enemigo(
		                    "Mago del Reino",
		                    "Hechicero de la luz enviado a contrarrestar tu magia",
		                    escalar(60, e),
		                    escalar(30, e),   // daño mágico alto
		                    escalar(6,  e),   // sin armadura
		                    20,
		                    false
		                );

		            case "caballero_explorador":
		                // Más armado que el explorador de zona 1
		                return new Enemigo(
		                    "Caballero Explorador",
		                    "Caballero con armadura ligera y espada sagrada",
		                    escalar(75, e),
		                    escalar(25, e),
		                    escalar(16, e),
		                    20,
		                    false
		                );

		            case "arquera":
		                // Daño a distancia, baja defensa
		                return new Enemigo(
		                    "Arquera Élite",
		                    "Arquera del reino, dispara flechas encantadas",
		                    escalar(58, e),
		                    escalar(28, e),
		                    escalar(7,  e),
		                    18,
		                    false
		                );

		            // ── ZONA 3: Torre de los Dragones ─────────────────────────
		            case "capitan":
		                // Máxima resistencia — veterano de guerra
		                return new Enemigo(
		                    "Capitán de la Guardia",
		                    "Veterano curtido en batalla, leal a la corona",
		                    escalar(95, e),
		                    escalar(28, e),
		                    escalar(22, e),
		                    25,
		                    false
		                );

		            case "paladin":
		                // Daño sagrado + se cura — peligroso a largo plazo
		                return new Enemigo(
		                    "Paladín Sagrado",
		                    "Guerrero bendecido que canaliza luz sagrada",
		                    escalar(80, e),
		                    escalar(32, e),
		                    escalar(18, e),
		                    25,
		                    false
		                );

		            case "escoltas":
		                // Dos atacantes ligeros — mayor presión de daño
		                return new Enemigo(
		                    "Escoltas Reales",
		                    "Dúo de guardias reales con espadas gemelas",
		                    escalar(65, e),
		                    escalar(35, e),   // daño muy alto (son dos)
		                    escalar(10, e),
		                    22,
		                    false
		                );

		            default:
		                return new Enemigo(
		                    "Soldado del Reino",
		                    "Un soldado enviado a detener al hechicero",
		                    escalar(65, e),
		                    escalar(20, e),
		                    escalar(10, e),
		                    14,
		                    false
		                );
		        }
		    }

		    // ── Jefes de zona para el Hechicero ──────────────────────────────────

		    /**
		     * Jefes que enfrenta el Hechicero en cada zona.
		     * El jefe final (zona 4) se obtiene con crearJefeFinalHechicero().
		     */
		    public static Enemigo crearJefeHechicero(int zona) {
		        switch (zona) {

		            case 1: // ── Jefe del Bosque: Comandante Real ──
		                return new Enemigo(
		                    "Comandante Real",
		                    "Líder de las tropas del reino, porta estandarte sagrado",
		                    210,   // vida
		                    28,    // ataque
		                    18,    // defensa
		                    50,    // maná recompensa
		                    true
		                );

		            case 2: // ── Jefe del Lago: Gran Mago del Reino ──
		                return new Enemigo(
		                    "Gran Mago del Reino",
		                    "Archimago que conoce todos los hechizos de luz",
		                    190,   // más frágil pero muy peligroso
		                    38,    // ataque mágico muy alto
		                    12,
		                    60,
		                    true
		                );

		            case 3: // ── Jefe de la Torre: Guardián Celestial ──
		                return new Enemigo(
		                    "Guardián Celestial",
		                    "Ser de luz convocado por la corona para detener la oscuridad",
		                    240,
		                    33,
		                    25,
		                    70,
		                    true
		                );

		            case 4: // ── Jefe Final: Princesa y Caballero ──
		                return crearJefeFinalHechicero();

		            default:
		                return crearJefeHechicero(1);
		        }
		    }

		    // ── Arrays de enemigos por zona (Hechicero) ──────────────────────────

		    /**
		     * Lista de enemigos del reino que enfrenta el Hechicero en cada zona.
		     * Se usan en Combate.java cuando el personaje es Hechicero.
		     */
		    public static Enemigo[] enemigosDeZonaHechicero(int zona) {
		        switch (zona) {
		            case 1: // Bosque — 3 encuentros con soldados del reino
		                return new Enemigo[]{
		                    crearEnemigoHechicero("explorador", 1),
		                    crearEnemigoHechicero("patrulla",   1),
		                    crearEnemigoHechicero("guardian",   1)
		                };
		            case 2: // Lago — 3 encuentros, presión mágica y militar
		                return new Enemigo[]{
		                    crearEnemigoHechicero("arquera",              2),
		                    crearEnemigoHechicero("mago_reino",           2),
		                    crearEnemigoHechicero("caballero_explorador", 2)
		                };
		            case 3: // Torre — 3 encuentros con élite del reino
		                return new Enemigo[]{
		                    crearEnemigoHechicero("escoltas", 3),
		                    crearEnemigoHechicero("capitan",  3),
		                    crearEnemigoHechicero("paladin",  3)
		                };
		            default:
		                return new Enemigo[]{ crearEnemigoHechicero("explorador", 1) };
		        }
		    }

		    // ══════════════════════════════════════════════════════════════════════
		    //  JEFES ORIGINALES — Princesa y Caballero (sin cambios)
		    // ══════════════════════════════════════════════════════════════════════

		    public static Enemigo crearJefe(int zona) {
		        switch (zona) {
		            case 1: return new Enemigo("Señor de las Raíces",
		                "Guardián ancestral del bosque corrompido",
		                200, 25, 15, 50, true);
		            case 2: return new Enemigo("Criatura Acuática Corrompida",
		                "Gigante del lago deformado por energía del Trono",
		                220, 30, 18, 55, true);
		            case 3: return new Enemigo("Dragón Guardián Antiguo",
		                "El más viejo y poderoso de los dragones corrompidos",
		                250, 35, 20, 70, true);
		            case 4: return new Enemigo("El Hechicero Desterrado",
		                "Su poder alcanza el máximo con los 3 fragmentos reunidos",
		                250, 40, 10, 0, true);
		            default: return crearJefe(1);
		        }
		    }

		    public static Enemigo crearJefeFinalHechicero() {
		        return new Enemigo(
		            "Princesa & Caballero Unidos",
		            "Juntos canalizan la luz sagrada del Cetro y Escalibur",
		            420, 55, 30, 0, true
		        );
		    }

		    private static int escalar(int base, double factor) {
		        return (int) Math.round(base * factor);
		    }
		}