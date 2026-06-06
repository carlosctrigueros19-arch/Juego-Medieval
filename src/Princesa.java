
	/**
	 * Personaje: Princesa de Moonhollow
	 * Rol: Soporte / magia sagrada
	 * Arma: Cetro de la Luz
	 * 
	 * Especialista en curación, escudos y purificación.
	 * Sus habilidades tienen costos de maná moderados.
	 */
	public class Princesa extends Personaje {

	    // ── Atributo exclusivo ─────────────────────────────────────────────────
	    private boolean escudoActivo;   // true cuando el escudo mágico está vigente
	    private int     escudoTurnos;   // turnos restantes del escudo

	    // ── Constructor ───────────────────────────────────────────────────────
	    public Princesa(String nombre) {
	        super(nombre,
	              120,   // vidaMax
	              100,   // manaMax
	              35,    // ataque
	              20,    // defensa
	              "Soporte / Magia sagrada",
	              "Cetro de la Luz");
	        this.escudoActivo = false;
	        this.escudoTurnos = 0;
	    }

	    // ── Menú de habilidades ───────────────────────────────────────────────
	    @Override
	    public String[] getMenuHabilidades() {
	        return new String[]{
	            "1. Curación          (costo: 20 maná) — restaura 40 PV",
	            "2. Escudo mágico     (costo: 25 maná) — reduce daño 2 turnos",
	            "3. Luz cegadora      (costo: 15 maná) — daño sagrado al enemigo",
	            "4. Purificación      (costo: 30 maná) — cura + elimina debuffs",
	            "5. Ataque normal     (sin costo)      — golpe con el cetro"
	        };
	    }

	    // ── Habilidades ───────────────────────────────────────────────────────
	    @Override
	    public String usarHabilidad(int opcion, Object objetivo) {
	        Enemigo enemigo = (objetivo instanceof Enemigo) ? (Enemigo) objetivo : null;

	        switch (opcion) {

	            case 1: // Curación
	                if (!gastarMana(20)) return "¡Maná insuficiente para Curación!";
	                int curacion = 40;
	                curar(curacion);
	                return nombre + " canaliza luz sagrada y recupera " + curacion + " PV. "
	                     + "(Vida actual: " + vidaActual + "/" + vidaMax + ")";

	            case 2: // Escudo mágico
	                if (!gastarMana(25)) return "¡Maná insuficiente para Escudo mágico!";
	                escudoActivo = true;
	                escudoTurnos = 2;
	                defensa += 15;   // bonus temporal
	                return nombre + " activa un escudo de luz. ¡Defensa aumentada por 2 turnos!";

	            case 3: // Luz cegadora
	                if (!gastarMana(15)) return "¡Maná insuficiente para Luz cegadora!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                int danoLuz = 50;
	                enemigo.recibirDanoMagico(danoLuz);
	                return nombre + " dispara un destello del Cetro causando "
	                     + danoLuz + " de daño sagrado a " + enemigo.getNombre() + ".";

	            case 4: // Purificación
	                if (!gastarMana(30)) return "¡Maná insuficiente para Purificación!";
	                curar(30);
	                // Restaura defensa si el escudo había terminado antes
	                return nombre + " purifica el aire. Recupera 30 PV y elimina efectos negativos.";

	            case 5: // Ataque normal
	                if (enemigo == null) return "No hay objetivo válido.";
	                return atacarNormal(enemigo);

	            default:
	                return "Opción inválida.";
	        }
	    }

	    // ── Lógica del escudo (llamar al final de cada turno) ─────────────────
	    public void actualizarEscudo() {
	        if (escudoActivo) {
	            escudoTurnos--;
	            if (escudoTurnos <= 0) {
	                escudoActivo = false;
	                defensa -= 15;   // quitar bonus temporal
	                System.out.println("  [El escudo mágico de " + nombre + " se ha disipado.]");
	            }
	        }
	    }

	    public boolean tieneEscudo() { return escudoActivo; }

	    // ── Historia ──────────────────────────────────────────────────────────
	    @Override
	    public void mostrarHistoria() {
	        System.out.println();
	        System.out.println("  ══════════════════════════════════════════════════");
	        System.out.println("   HISTORIA — LA PRINCESA DE MOONHOLLOW");
	        System.out.println("  ══════════════════════════════════════════════════");
	        System.out.println("  La noche en que el Trono se fracturó, fuiste");
	        System.out.println("  despertada por explosiones y gritos dentro del");
	        System.out.println("  castillo. Pasillos agrietados, guardias luchando");
	        System.out.println("  contra sombras vivas... y tú, sola.");
	        System.out.println();
	        System.out.println("  Encontraste el Cetro de la Luz en el Lago Mágico.");
	        System.out.println("  Ahora no eres solo una sobreviviente.");
	        System.out.println("  Tienes el poder para salvar Moonhollow.");
	        System.out.println("  ══════════════════════════════════════════════════");
	        System.out.println();
	    }

	    // ── Finales ───────────────────────────────────────────────────────────
	    @Override
	    public void mostrarFinal(boolean victoria) {
	        System.out.println();
	        if (victoria) {
	            System.out.println("  ★ FINAL LUMINOSO ★");
	            System.out.println("  Usaste el poder completo del Cetro para debilitar");
	            System.out.println("  la corrupción del Trono. El caballero pudo dar el");
	            System.out.println("  golpe final. Moonhollow fue salvado.");
	            System.out.println("  Tu nombre quedó grabado en las piedras del reino.");
	        } else {
	            System.out.println("  ✦ FINAL OSCURO ✦");
	            System.out.println("  El hechicero completó el Trono. Fuiste encerrada");
	            System.out.println("  en un calabozo dimensional donde la oscuridad");
	            System.out.println("  consume todo lentamente...");
	        }
	        System.out.println();
	    }
	}
