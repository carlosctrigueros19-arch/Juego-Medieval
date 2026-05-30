
	/**
	 * Personaje: Caballero de Moonhollow
	 * Rol: Tanque / combate físico
	 * Arma: Escalibur
	 * 
	 * Alta defensa y ataques físicos potentes.
	 * Sus habilidades consumen poca maná pero generan gran daño.
	 */
	public class Caballero extends Personaje {

	    // ── Atributo exclusivo ─────────────────────────────────────────────────
	    private boolean cargaActiva;    // true cuando golpe cargado está listo
	    private int     turnosCarga;    // turnos cargando antes de golpe

	    // ── Constructor ───────────────────────────────────────────────────────
	    public Caballero(String nombre) {
	        super(nombre,
	              150,   // vidaMax  — el más resistente
	              60,    // manaMax  — usa poca magia
	              55,    // ataque   — el más fuerte físicamente
	              35,    // defensa
	              "Tanque / Combate físico",
	              "Escalibur");
	        this.cargaActiva  = false;
	        this.turnosCarga  = 0;
	    }

	    // ── Menú de habilidades ───────────────────────────────────────────────
	    @Override
	    public String[] getMenuHabilidades() {
	        return new String[]{
	            "1. Golpe cargado     (costo: 20 maná) — daño x2 pero pierdes turno",
	            "2. Onda de energía   (costo: 15 maná) — daño a todos los rivales",
	            "3. Romper barrera    (costo: 10 maná) — ignora defensa del enemigo",
	            "4. Postura defensiva (costo: 10 maná) — sube defensa este turno",
	            "5. Ataque normal     (sin costo)      — golpe directo con Escalibur"
	        };
	    }

	    // ── Habilidades ───────────────────────────────────────────────────────
	    @Override
	    public String usarHabilidad(int opcion, Object objetivo) {
	        Enemigo enemigo = (objetivo instanceof Enemigo) ? (Enemigo) objetivo : null;

	        switch (opcion) {

	            case 1: // Golpe cargado
	                if (!gastarMana(20)) return "¡Maná insuficiente para Golpe cargado!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                int danoGolpe = Math.max(1, (ataque * 2) - enemigo.getDefensa());
	                enemigo.recibirDano(danoGolpe);
	                return nombre + " canaliza toda la energía de Escalibur y golpea por "
	                     + danoGolpe + " de daño a " + enemigo.getNombre() + "!";

	            case 2: // Onda de energía
	                if (!gastarMana(15)) return "¡Maná insuficiente para Onda de energía!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                int danoOnda = 45;
	                enemigo.recibirDanoMagico(danoOnda);
	                return nombre + " libera una onda de energía roja causando "
	                     + danoOnda + " de daño mágico a " + enemigo.getNombre() + ".";

	            case 3: // Romper barrera
	                if (!gastarMana(10)) return "¡Maná insuficiente para Romper barrera!";
	                if (enemigo == null)  return "No hay objetivo válido.";
	                // Ignora completamente la defensa del enemigo
	                int danoRuptura = ataque + 10;
	                enemigo.recibirDanoMagico(danoRuptura);
	                return nombre + " rompe las defensas con Escalibur causando "
	                     + danoRuptura + " de daño ignorando armadura a " + enemigo.getNombre() + ".";

	            case 4: // Postura defensiva
	                if (!gastarMana(10)) return "¡Maná insuficiente para Postura defensiva!";
	                defensa += 20;
	                return nombre + " adopta postura defensiva. ¡Defensa aumentada en 20 este turno!";

	            case 5: // Ataque normal
	                if (enemigo == null) return "No hay objetivo válido.";
	                return atacarNormal(enemigo);

	            default:
	                return "Opción inválida.";
	        }
	    }

	    // ── Historia ──────────────────────────────────────────────────────────
	    @Override
	    public void mostrarHistoria() {
	        System.out.println();
	        System.out.println("  ══════════════════════════════════════════════");
	        System.out.println("   HISTORIA — EL CABALLERO DE MOONHOLLOW");
	        System.out.println("  ══════════════════════════════════════════════");
	        System.out.println("  No eres noble ni mago. Todo lo que tienes lo");
	        System.out.println("  ganaste con disciplina y entrenamiento.");
	        System.out.println("  La noche del caos, defendiste las murallas");
	        System.out.println("  mientras el reino ardía desde adentro.");
	        System.out.println();
	        System.out.println("  En el Bosque Sombrío encontraste el primer");
	        System.out.println("  fragmento del Trono. Tu espada reaccionó a su");
	        System.out.println("  poder: Escalibur despertó.");
	        System.out.println("  ══════════════════════════════════════════════");
	        System.out.println();
	    }

	    // ── Finales ───────────────────────────────────────────────────────────
	    @Override
	    public void mostrarFinal(boolean victoria) {
	        System.out.println();
	        if (victoria) {
	            System.out.println("  ★ FINAL HEROICO ★");
	            System.out.println("  Cuando la princesa debilitó la corrupción del Trono,");
	            System.out.println("  canalizaste toda la energía de Escalibur y diste");
	            System.out.println("  el golpe final. Moonhollow fue salvado.");
	            System.out.println("  Nunca abandonaste la misión.");
	        } else {
	            System.out.println("  ✦ FINAL OSCURO ✦");
	            System.out.println("  El hechicero fue demasiado poderoso. Escalibur");
	            System.out.println("  se apagó lentamente mientras la oscuridad");
	            System.out.println("  consumía el último reino de luz...");
	        }
	        System.out.println();
	    }
	}


