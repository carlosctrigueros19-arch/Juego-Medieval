
	/**
 * Clase abstracta base para todos los personajes del juego.
 * Define atributos comunes y métodos que cada subclase debe implementar.
 */
public abstract class Personaje {

    // ── Atributos comunes ──────────────────────────────────────────────────
    protected String nombre;
    protected int    vidaMax;
    protected int    vidaActual;
    protected int    manaMax;
    protected int    manaActual;
    protected int    ataque;
    protected int    defensa;
    protected String rol;          // "Soporte", "Tanque", "Villano ofensivo"
    protected String arma;         // nombre del arma principal

    // ── Constructor ───────────────────────────────────────────────────────
    public Personaje(String nombre, int vidaMax, int manaMax,
                     int ataque, int defensa, String rol, String arma) {
        this.nombre      = nombre;
        this.vidaMax     = vidaMax;
        this.vidaActual  = vidaMax;
        this.manaMax     = manaMax;
        this.manaActual  = manaMax;
        this.ataque      = ataque;
        this.defensa     = defensa;
        this.rol         = rol;
        this.arma        = arma;
    }

    // ── Métodos abstractos (cada subclase los implementa) ─────────────────

    /**
     * Muestra el menú de habilidades del personaje y devuelve
     * el nombre de la habilidad elegida para el log de combate.
     * @param objetivo  el Personaje (o Enemigo) que recibirá el efecto
     * @return          descripción de la acción realizada
     */
    public abstract String usarHabilidad(int opcion, Object objetivo);

    /**
     * Lista de habilidades disponibles para mostrar en menú.
     * Cada String contiene: "N. Nombre  (costo X maná)"
     */
    public abstract String[] getMenuHabilidades();

    /**
     * Historia/perspectiva propia del personaje (narración).
     */
    public abstract void mostrarHistoria();

    /**
     * Final de la historia según el resultado del juego.
     * @param victoria true = final bueno, false = final oscuro
     */
    public abstract void mostrarFinal(boolean victoria);

    // ── Ataque normal (compartido por todos) ──────────────────────────────
    /**
     * Ataque físico básico sin costo de maná.
     * Quita siempre 25 de vida fijos ignorando defensa.
     * Además regenera 5 de maná al jugador por cada golpe normal.
     */
    public String atacarNormal(Enemigo objetivo) {
        int dano = 25;
        objetivo.recibirDanoMagico(dano);   // ignora defensa, siempre 25
        recuperarMana(5);                   // regenera 5 de maná por golpe
        return nombre + " ataca con " + arma + " y causa " + dano
             + " de daño. (+" + 5 + " maná)";
    }

    // ── Recibir daño ──────────────────────────────────────────────────────
    public void recibirDano(int dano) {
        int danoReal = Math.max(1, dano - this.defensa);
        vidaActual   = Math.max(0, vidaActual - danoReal);
    }

    /** Versión que ignora la defensa (para daño mágico puro). */
    public void recibirDanoMagico(int dano) {
        vidaActual = Math.max(0, vidaActual - dano);
    }

    // ── Curación ──────────────────────────────────────────────────────────
    public void curar(int cantidad) {
        vidaActual = Math.min(vidaMax, vidaActual + cantidad);
    }

    // ── Maná ──────────────────────────────────────────────────────────────
    public boolean gastarMana(int costo) {
        if (manaActual >= costo) {
            manaActual -= costo;
            return true;
        }
        return false;   // maná insuficiente
    }

    public void recuperarMana(int cantidad) {
        manaActual = Math.min(manaMax, manaActual + cantidad);
    }

    // ── Estado ────────────────────────────────────────────────────────────
    public boolean estaVivo() {
        return vidaActual > 0;
    }

    // ── Mostrar estado en consola ─────────────────────────────────────────
    public void mostrarEstado() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.printf ("║  %-36s║%n", nombre + " — " + rol);
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf ("║  Vida  : %s%n", BarraVida.generar(vidaActual, vidaMax, 20));
        System.out.printf ("║  Maná  : %s%n", BarraVida.generarMana(manaActual, manaMax, 20));
        System.out.printf ("║  ATK %-4d  DEF %-4d               ║%n", ataque, defensa);
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public String getNombre()     { return nombre; }
    public int    getVidaActual() { return vidaActual; }
    public int    getVidaMax()    { return vidaMax; }
    public int    getManaActual() { return manaActual; }
    public int    getAtaque()     { return ataque; }
    public int    getDefensa()    { return defensa; }
    public String getRol()        { return rol; }
}