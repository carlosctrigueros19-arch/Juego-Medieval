
	/**
	 * Clase Enemigo — El Trono de la Oscuridad
	 *
	 * Representa a cualquier criatura que el jugador enfrenta.
	 * Los enemigos base escalan de fuerza según la zona (nivel 1-3).
	 * Los jefes se crean con el constructor especial o con EnemigosFactory.
	 *
	 * Efectos de estado soportados:
	 *   - Quemadura : daño fijo por turno (aplicado por Hechicero)
	 *   - Defensa reducida : baja la defensa temporalmente (Cadenas mágicas)
	 */
	public class Enemigo {

	    // ── Atributos ─────────────────────────────────────────────────────────
	    private String nombre;
	    private String descripcion;      // texto narrativo breve
	    private int    vidaMax;
	    private int    vidaActual;
	    private int    ataque;
	    private int    defensa;
	    private int    defensaOriginal;  // para restaurar tras reducción
	    private int    manaRecompensa;   // maná que recupera el jugador al vencerlo
	    private int    turno;            // contador interno de turnos

	    // ── Efectos de estado ─────────────────────────────────────────────────
	    private int quemaduraDano;       // daño por turno; 0 = sin quemadura
	    private int defensaReducida;     // cantidad que se redujo (para restaurar)

	    // ── Es jefe ───────────────────────────────────────────────────────────
	    private boolean esJefe;

	    // ── Constructor estándar ──────────────────────────────────────────────
	    public Enemigo(String nombre, String descripcion,
	                   int vidaMax, int ataque, int defensa,
	                   int manaRecompensa, boolean esJefe) {
	        this.nombre          = nombre;
	        this.descripcion     = descripcion;
	        this.vidaMax         = vidaMax;
	        this.vidaActual      = vidaMax;
	        this.ataque          = ataque;
	        this.defensa         = defensa;
	        this.defensaOriginal = defensa;
	        this.manaRecompensa  = manaRecompensa;
	        this.esJefe          = esJefe;
	        this.quemaduraDano   = 0;
	        this.defensaReducida = 0;
	        this.turno           = 0;
	    }

	    // ── Recibir daño ──────────────────────────────────────────────────────
	    /** Daño físico: descuenta defensa. */
	    public void recibirDano(int dano) {
	        int real = Math.max(1, dano - defensa);
	        vidaActual = Math.max(0, vidaActual - real);
	    }

	    /** Daño mágico: ignora defensa. */
	    public void recibirDanoMagico(int dano) {
	        vidaActual = Math.max(0, vidaActual - Math.max(1, dano));
	    }

	    // ── Efectos de estado ─────────────────────────────────────────────────
	    /** Aplica quemadura (daño por turno). Se acumula si ya tenía. */
	    public void aplicarQuemadura(int danoPorTurno) {
	        quemaduraDano += danoPorTurno;
	    }

	    /** Reduce defensa temporalmente (Cadenas mágicas). */
	    public void reducirDefensa(int cantidad) {
	        defensaReducida += cantidad;
	        defensa = Math.max(0, defensa - cantidad);
	    }

	    /** Restaura la defensa al valor original. */
	    public void restaurarDefensa() {
	        defensa = defensaOriginal;
	        defensaReducida = 0;
	    }

	    /**
	     * Procesa los efectos de estado al inicio del turno del enemigo.
	     * Devuelve un String con lo que ocurrió (para el log de combate).
	     */
	    public String procesarEfectos() {
	        StringBuilder log = new StringBuilder();
	        if (quemaduraDano > 0) {
	            vidaActual = Math.max(0, vidaActual - quemaduraDano);
	            log.append("  [Quemadura] ").append(nombre)
	               .append(" sufre ").append(quemaduraDano).append(" de daño.\n");
	            // La quemadura se reduce un punto por turno hasta apagarse
	            quemaduraDano = Math.max(0, quemaduraDano - 1);
	        }
	        return log.toString();
	    }

	    // ── Ataque del enemigo ────────────────────────────────────────────────
	    /**
	     * El enemigo ataca al jugador.
	     * Los jefes tienen un ataque especial cada 3 turnos.
	     * @return descripción del ataque para el log
	     */
	    public String atacar(Personaje objetivo) {
	        turno++;
	        String log;

	        if (esJefe && turno % 3 == 0) {
	            // Ataque especial del jefe (ignora defensa)
	            int danoEspecial = ataque + 20;
	            objetivo.recibirDanoMagico(danoEspecial);
	            log = "  ¡" + nombre + " usa ATAQUE ESPECIAL y causa "
	                + danoEspecial + " de daño mágico a " + objetivo.getNombre() + "!";
	        } else {
	            // Ataque normal
	            int danoNormal = Math.max(1, ataque - objetivo.getDefensa());
	            objetivo.recibirDano(danoNormal);
	            log = "  " + nombre + " ataca y causa " + danoNormal
	                + " de daño a " + objetivo.getNombre() + ".";
	        }
	        return log;
	    }

	    // ── Estado ────────────────────────────────────────────────────────────
	    public boolean estaVivo() { return vidaActual > 0; }

	    /** Muestra la ficha del enemigo con barra de vida. */
	    public void mostrarEstado() {
	        System.out.println("  ┌─────────────────────────────────────┐");
	        System.out.printf ("  │  %-35s│%n", (esJefe ? "★ JEFE: " : "  ") + nombre);
	        System.out.printf ("  │  %-35s│%n", descripcion);
	        System.out.println("  ├─────────────────────────────────────┤");
	        System.out.printf ("  │  Vida: %-30s│%n",
	            BarraVida.generarCompacto(vidaActual, vidaMax, 15)
	            + "  ATK:" + ataque + " DEF:" + defensa);
	        if (quemaduraDano > 0)
	            System.out.printf("  │  %-35s│%n", "🔥 Quemadura: " + quemaduraDano + "/turno");
	        System.out.println("  └─────────────────────────────────────┘");
	    }

	    // ── Getters ───────────────────────────────────────────────────────────
	    public String getNombre()       { return nombre; }
	    public String getDescripcion()  { return descripcion; }
	    public int    getVidaActual()   { return vidaActual; }
	    public int    getVidaMax()      { return vidaMax; }
	    public int    getAtaque()       { return ataque; }
	    public int    getDefensa()      { return defensa; }
	    public int    getManaRecompensa(){ return manaRecompensa; }
	    public boolean esJefe()         { return esJefe; }
	}

}
