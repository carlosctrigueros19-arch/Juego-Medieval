
	/**
	 * Personaje: El Hechicero desterrado
	 * Rol: Villano ofensivo / control del campo
	 * Arma: Magia oscura + fragmentos del Trono
	 * 
	 * Máximo daño mágico. Sus habilidades son devastadoras
	 * pero consumen mucho maná. Juega con alto riesgo/alta recompensa.
	 */
	public class Hechicero extends Personaje {

	    // ── Atributos exclusivos ──────────────────────────────────────────────
	    private int fragmentosReunidos;   // 0-3, aumenta poder con cada zona
	    private int nivelCorrupcion;      // sube con ciertos hechizos (narrativo)

	    // ── Constructor ───────────────────────────────────────────────────────
	    public Hechicero(String nombre) {
	        super(nombre,
	              100,   // vidaMax  — el más frágil
	              140,   // manaMax  — el mayor reservorio de maná
	              65,    // ataque mágico base
	              10,    // defensa baja
	              "Villano ofensivo / Control del campo",
	              "Magia oscura del Trono");
	        this.fragmentosReunidos = 0;
	        this.nivelCorrupcion    = 0;
	    }

	    // ── Menú de habilidades ───────────────────────────────────────────────
	    @Override
	    public String[] getMenuHabilidades() {
	        return new String[]{
	            "1. Explosión oscura  (costo: 25 maná) — daño masivo al objetivo",
	            "2. Cadenas mágicas   (costo: 20 maná) — inmoviliza, reduce defensa",
	            "3. Fuego de sombra   (costo: 15 maná) — daño + efecto quemadura",
	            "4. Absorber energía  (costo:  0 maná) — roba maná del enemigo",
	            "5. Ataque normal     (sin costo)      — proyectil oscuro básico"
	        };
	    }

	    // ── Habilidades ───────────────────────────────────────────────────────
	    @Override
	    public String usarHabilidad(int opcion, Object objetivo) {
	        Enemigo enemigo = (objetivo instanceof Enemigo) ? (Enemigo) objetivo : null;

	        // Bonus de daño por fragmentos reunidos
	        int bonusFragmento = fragmentosReunidos * 10;

	        switch (opcion) {

	            case 1: // Explosión oscura
	                if (!gastarMana(25)) return "¡Maná insuficiente para Explosión oscura!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                int danoExplosion = 70 + bonusFragmento;
	                enemigo.recibirDanoMagico(danoExplosion);
	                nivelCorrupcion++;
	                return nombre + " desata una explosión de energía oscura causando "
	                     + danoExplosion + " de daño a " + enemigo.getNombre()
	                     + ". [Corrupción: " + nivelCorrupcion + "]";

	            case 2: // Cadenas mágicas
	                if (!gastarMana(20)) return "¡Maná insuficiente para Cadenas mágicas!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                int reduccion = 15;
	                enemigo.reducirDefensa(reduccion);
	                int danoCadenas = 30 + bonusFragmento;
	                enemigo.recibirDanoMagico(danoCadenas);
	                return nombre + " invoca cadenas oscuras sobre " + enemigo.getNombre()
	                     + ". Defensa reducida en " + reduccion + " y recibe " + danoCadenas + " de daño.";

	            case 3: // Fuego de sombra
	                if (!gastarMana(15)) return "¡Maná insuficiente para Fuego de sombra!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                int danoFuego = 40 + bonusFragmento;
	                enemigo.recibirDanoMagico(danoFuego);
	                enemigo.aplicarQuemadura(8);   // daño por turno
	                return nombre + " lanza fuego de sombra a " + enemigo.getNombre()
	                     + " causando " + danoFuego + " de daño + quemadura (8 por turno).";

	            case 4: // Absorber energía (sin costo)
	                if (enemigo == null) return "No hay objetivo válido.";
	                int manaRobado = 20;
	                recuperarMana(manaRobado);
	                int danoAbsorcion = 20;
	                enemigo.recibirDanoMagico(danoAbsorcion);
	                return nombre + " absorbe la energía de " + enemigo.getNombre()
	                     + ". Recupera " + manaRobado + " maná y causa " + danoAbsorcion + " de daño.";

	            case 5: // Ataque normal
	                if (enemigo == null) return "No hay objetivo válido.";
	                return atacarNormal(enemigo);

	            default:
	                return "Opción inválida.";
	        }
	    }

	    // ── Reunir fragmento (se llama al completar una zona) ─────────────────
	    public void reunirFragmento() {
	        if (fragmentosReunidos < 3) {
	            fragmentosReunidos++;
	            ataque += 10;   // cada fragmento potencia el ataque
	            System.out.println("  [" + nombre + " reúne el fragmento "
	                + fragmentosReunidos + "/3. Poder oscuro aumentado!]");
	        }
	    }

	    public int getFragmentos() { return fragmentosReunidos; }

	    // ── Historia ──────────────────────────────────────────────────────────
	    @Override
	    public void mostrarHistoria() {
	        System.out.println();
	        System.out.println("  ══════════════════════════════════════════════════");
	        System.out.println("   HISTORIA — EL HECHICERO DESTERRADO");
	        System.out.println("  ══════════════════════════════════════════════════");
	        System.out.println("  Fuiste expulsado de Moonhollow por intentar");
	        System.out.println("  controlar el Trono de la Oscuridad. Durante");
	        System.out.println("  años en el exilio aprendiste magia prohibida,");
	        System.out.println("  invocaciones y rituales olvidados.");
	        System.out.println();
	        System.out.println("  La noche que volviste, el Trono reaccionó a tu");
	        System.out.println("  presencia y se fracturó en tres fragmentos.");
	        System.out.println("  Ahora debes reunirlos antes que nadie te detenga.");
	        System.out.println("  ══════════════════════════════════════════════════");
	        System.out.println();
	    }

	    // ── Finales ───────────────────────────────────────────────────────────
	    @Override
	    public void mostrarFinal(boolean victoria) {
	        System.out.println();
	        if (victoria) {
	            System.out.println("  ★ FINAL OSCURO — VICTORIA DEL HECHICERO ★");
	            System.out.println("  Reuniste los tres fragmentos y reconstruiste");
	            System.out.println("  el Trono de la Oscuridad. Moonhollow cayó.");
	            System.out.println("  La oscuridad cubre el cielo eternamente.");
	            System.out.println("  Tu venganza fue absoluta.");
	        } else {
	            System.out.println("  ✦ FINAL DE DERROTA ✦");
	            System.out.println("  La princesa y el caballero te derrotaron.");
	            System.out.println("  El Trono quedó fragmentado para siempre.");
	            System.out.println("  Moonhollow sobrevivió... y tú desapareciste");
	            System.out.println("  en la oscuridad de la que nunca debiste salir.");
	        }
	        System.out.println();
	    }
	}

