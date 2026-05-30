
	import java.util.Scanner;

/**
 * GameEngine — El Trono de la Oscuridad
 * Orquesta el flujo completo del juego con música integrada.
 */
public class Gameengine {

    private Personaje jugador;
    private Mapa      mapa;
    private Scanner   scanner;

    public Gameengine(Personaje jugador, Scanner scanner) {
        this.jugador = jugador;
        this.mapa    = new Mapa();
        this.scanner = scanner;
    }

    public void iniciar() {
        limpiarPantalla();
        introduccion();

        if (!ejecutarZona(1)) { mostrarFinalOscuro(); return; }
        if (!ejecutarZona(2)) { mostrarFinalOscuro(); return; }
        if (!ejecutarZona(3)) { mostrarFinalOscuro(); return; }

        narrativaPreBatallaFinal();
        boolean victoria = batallaFinal();

        if (victoria) mostrarFinalVictoria();
        else          mostrarFinalOscuro();
    }

    // ── Ejecutar zona completa ─────────────────────────────────────────────
    private boolean ejecutarZona(int zona) {
        mapa.avanzarA(zona);
        limpiarPantalla();
        mapa.mostrar();
        pausar();

        // Música de la zona
        switch (zona) {
            case 1: Musica.musicaBosque(); break;
            case 2: Musica.musicaLago();   break;
            case 3: Musica.musicaTorre();  break;
        }

        narrarLlegada(zona);
        pausar();

        // Enemigos comunes
        Enemigo[] comunes = EnemigosFactory.enemigosDeZona(zona);
        for (int i = 0; i < comunes.length; i++) {
            limpiarPantalla();
            // Siempre mostramos la zona correcta antes de cada encuentro
            mapa.mostrarUbicacion();
            System.out.println("  Encuentro " + (i + 1) + " de " + comunes.length
                             + " — " + mapa.getNombreZonaActual());
            pausar();

            Musica.musicaCombate();
            Combate combate = new Combate(jugador, comunes[i], scanner);
            boolean gano    = combate.iniciar();

            if (!gano) return false;

            // Restaurar música de zona tras el combate
            switch (zona) {
                case 1: Musica.musicaBosque(); break;
                case 2: Musica.musicaLago();   break;
                case 3: Musica.musicaTorre();  break;
            }

            jugador.curar(15);
            System.out.println("\n  Recuperas 15 PV tras el combate.");
            pausar();
        }

        // Jefe de zona
        limpiarPantalla();
        narrarPreJefe(zona);
        pausar();

        Musica.musicaJefe();
        Enemigo jefe     = EnemigosFactory.crearJefe(zona);
        Combate cJefe    = new Combate(jugador, jefe, scanner);
        boolean ganoJefe = cJefe.iniciar();

        if (!ganoJefe) return false;

        narrarPostJefe(zona);

        if (jugador instanceof Hechicero) {
            ((Hechicero) jugador).reunirFragmento();
        }

        jugador.curar(40);
        System.out.println("\n  Recuperas 40 PV tras derrotar al jefe.");
        pausar();
        return true;
    }

    // ── Batalla final ──────────────────────────────────────────────────────
    private boolean batallaFinal() {
        mapa.avanzarA(4);
        limpiarPantalla();
        mapa.mostrar();
        pausar();

        Musica.musicaFinal();

        Enemigo jefeFinal = (jugador instanceof Hechicero)
            ? EnemigosFactory.crearJefeFinalHechicero()
            : EnemigosFactory.crearJefe(4);

        Combate combateFinal = new Combate(jugador, jefeFinal, scanner);
        return combateFinal.iniciar();
    }

    // ══════════════════════════════════════════════════════════════════════
    //  NARRACIONES
    // ══════════════════════════════════════════════════════════════════════
    private void introduccion() {
        separarLinea();
        if (jugador instanceof Princesa) {
            System.out.println("  La noche de la ruptura del Trono fuiste despertada");
            System.out.println("  por explosiones. Pasillos agrietados, guardias");
            System.out.println("  cayendo... Lograste escapar del castillo.");
            System.out.println("  Viste tres luces oscuras cruzar el cielo.");
            System.out.println("  Decidiste no huir. Si Moonhollow moría,");
            System.out.println("  tú lucharías para salvarlo.");
        } else if (jugador instanceof Caballero) {
            System.out.println("  Estabas en las murallas cuando el cielo se oscureció.");
            System.out.println("  Criaturas emergieron de las sombras. Luchaste horas");
            System.out.println("  protegiendo civiles hasta entender la verdad:");
            System.out.println("  el reino se consumía desde dentro.");
            System.out.println("  Una luz cayó sobre el Bosque Sombrío.");
            System.out.println("  Fuiste hacia allí. Escalibur te espera.");
        } else {
            System.out.println("  Regresaste a Moonhollow después de años en el exilio.");
            System.out.println("  Cuando intentaste reconectarte con el Trono,");
            System.out.println("  explotó fracturándose en tres fragmentos.");
            System.out.println("  No importa. Si los reúnes, el Trono será tuyo.");
            System.out.println("  Y esta vez nadie podrá desterrarte.");
        }
        separarLinea();
        pausar();
    }

    private void narrarLlegada(int zona) {
        separarLinea();
        switch (zona) {
            case 1:
                System.out.println("  BOSQUE SOMBRÍO");
                if (jugador instanceof Princesa) {
                    System.out.println("  El camino al bosque está lleno de criaturas.");
                    System.out.println("  La niebla reduce la visión a pocos pasos.");
                    System.out.println("  Sientes la energía corrompida vibrar en el aire.");
                } else if (jugador instanceof Caballero) {
                    System.out.println("  El bosque ha cambiado. Las raíces se mueven solas.");
                    System.out.println("  Algo dentro de ti responde al poder del fragmento.");
                    System.out.println("  Escalibur comienza a brillar con energía roja.");
                } else {
                    System.out.println("  Las criaturas del bosque no te atacan.");
                    System.out.println("  Algunas parecen... obedecerte.");
                    System.out.println("  El primer fragmento está aquí. Lo sientes.");
                }
                break;
            case 2:
                System.out.println("  LAGO MÁGICO");
                if (jugador instanceof Princesa) {
                    System.out.println("  El lago brilla con energía sagrada.");
                    System.out.println("  Bajo el agua se mueven sombras lentas.");
                    System.out.println("  Un altar antiguo en el centro te llama.");
                } else if (jugador instanceof Caballero) {
                    System.out.println("  El lago ha cambiado desde la última vez.");
                    System.out.println("  La princesa está en algún lugar de esta zona.");
                    System.out.println("  Deben reunirse antes de que sea tarde.");
                } else {
                    System.out.println("  El lago rechaza tu presencia.");
                    System.out.println("  El agua se agita y símbolos luminosos aparecen.");
                    System.out.println("  Tendrás que corromperlo para llegar al fragmento.");
                }
                break;
            case 3:
                System.out.println("  TORRE ANTIGUA DE LOS DRAGONES");
                if (jugador instanceof Princesa) {
                    System.out.println("  La torre está rodeada de fuego oscuro.");
                    System.out.println("  Dragones corrompidos sobrevuelan las ruinas.");
                    System.out.println("  El tercer fragmento está aquí. Y quizás el hechicero.");
                } else if (jugador instanceof Caballero) {
                    System.out.println("  Estructuras destruidas, trampas mágicas, dragones.");
                    System.out.println("  Este es el lugar más peligroso del reino.");
                    System.out.println("  Pero Escalibur puede con todo.");
                } else {
                    System.out.println("  Solo un fragmento más y el Trono será tuyo.");
                    System.out.println("  Los dragones son el último obstáculo.");
                    System.out.println("  Nada te detendrá.");
                }
                break;
        }
        separarLinea();
    }

    private void narrarPreJefe(int zona) {
        separarLinea();
        switch (zona) {
            case 1:
                System.out.println("  ★ Al fondo del bosque sientes una presencia enorme.");
                System.out.println("  El guardián ancestral bloquea el acceso al núcleo.");
                break;
            case 2:
                System.out.println("  ★ El altar del lago tiembla. El agua se eleva.");
                System.out.println("  Una criatura gigante emerge desde las profundidades.");
                break;
            case 3:
                System.out.println("  ★ En el núcleo de la torre descansa el último fragmento.");
                System.out.println("  El dragón guardián más antiguo del reino te espera.");
                break;
        }
        separarLinea();
    }

    private void narrarPostJefe(int zona) {
        System.out.println();
        separarLinea();
        switch (zona) {
            case 1:
                System.out.println(jugador instanceof Hechicero
                    ? "  Tomas el primer fragmento. Tu poder aumenta.\n  La oscuridad a tu alrededor se expande."
                    : "  El bosque recupera parte de su calma.\n  Aún quedan dos fragmentos que encontrar.");
                break;
            case 2:
                System.out.println(jugador instanceof Hechicero
                    ? "  El segundo fragmento es tuyo. El lago se oscurece.\n  El Trono comienza a responder a tu presencia."
                    : "  La energía del lago se estabiliza levemente.\n  Solo queda la Torre de los Dragones.");
                break;
            case 3:
                System.out.println(jugador instanceof Hechicero
                    ? "  Los tres fragmentos están reunidos en tus manos.\n  Solo falta completar el ritual en el núcleo."
                    : "  Tomaste el tercer fragmento...\n  Entonces una voz familiar resuena en la torre.\n  El hechicero ha llegado.");
                break;
        }
        separarLinea();
    }

    private void narrativaPreBatallaFinal() {
        limpiarPantalla();
        separarLinea();
        if (jugador instanceof Hechicero) {
            System.out.println("  NÚCLEO DEL TRONO");
            System.out.println("  El ritual está casi completo.");
            System.out.println("  Pero la princesa y el caballero llegan para detenerte.");
            System.out.println("  Derrótalos y el reino será tuyo para siempre.");
        } else {
            System.out.println("  NÚCLEO DEL TRONO");
            System.out.println("  El hechicero está frente al Trono fracturado.");
            System.out.println("  La oscuridad cubre Moonhollow entero.");
            System.out.println("  Esta es tu última oportunidad.");
        }
        separarLinea();
        pausar();
    }

    // ══════════════════════════════════════════════════════════════════════
    //  FINALES
    // ══════════════════════════════════════════════════════════════════════
    private void mostrarFinalVictoria() {
        limpiarPantalla();
        Musica.musicaVictoria();
        separarLinea();
        System.out.println("  ★★★  FINAL  ★★★");
        separarLinea();
        jugador.mostrarFinal(true);
        System.out.println("  FIN — El Trono de la Oscuridad");
        System.out.println("  Instituto Nacional de Sonzacate");
        separarLinea();
        pausar();
        Musica.detenerMusica();
    }

    private void mostrarFinalOscuro() {
        limpiarPantalla();
        Musica.musicaDerrota();
        separarLinea();
        System.out.println("  ✦✦✦  GAME OVER  ✦✦✦");
        separarLinea();
        jugador.mostrarFinal(false);
        System.out.println("  La oscuridad consume Moonhollow...");
        System.out.println("  FIN — El Trono de la Oscuridad");
        separarLinea();
        pausar();
        Musica.detenerMusica();
    }

    // ── Utilidades ─────────────────────────────────────────────────────────
    private void separarLinea() {
        System.out.println("  ══════════════════════════════════════════════");
    }

    private void pausar() {
        System.out.print("\n  [Presiona Enter para continuar...]");
        try { scanner.nextLine(); } catch (Exception e) {}
    }

    private void limpiarPantalla() {
        for (int i = 0; i < 40; i++) System.out.println();
    }
}