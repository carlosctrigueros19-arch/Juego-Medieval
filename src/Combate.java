
	import java.util.Scanner;

	/**
	 * Clase Combate — El Trono de la Oscuridad
	 *
	 * Gestiona el sistema de combate por turnos entre el jugador y un enemigo.
	 * Flujo de un turno:
	 *   1. Mostrar estado de ambos
	 *   2. Jugador elige acción (menú de habilidades)
	 *   3. Se aplica la acción
	 *   4. Si el enemigo sigue vivo → ataca al jugador
	 *   5. Se procesan efectos de estado (quemadura, etc.)
	 *   6. Verificar si alguien murió
	 *
	 * Al terminar devuelve true (jugador ganó) o false (jugador perdió).
	 */
	public class Combate {

	    // ── Atributos ─────────────────────────────────────────────────────────
	    private Personaje jugador;
	    private Enemigo   enemigo;
	    private Scanner   scanner;

	    // ── Constructor ───────────────────────────────────────────────────────
	    public Combate(Personaje jugador, Enemigo enemigo, Scanner scanner) {
	        this.jugador = jugador;
	        this.enemigo = enemigo;
	        this.scanner = scanner;
	    }

	    // ── Iniciar combate ───────────────────────────────────────────────────
	    /**
	     * Ejecuta el combate completo y devuelve el resultado.
	     * @return true si el jugador ganó, false si fue derrotado
	     */
	    public boolean iniciar() {
	        mostrarIntro();

	        while (jugador.estaVivo() && enemigo.estaVivo()) {

	            // ── Mostrar estado ────────────────────────────────────────────
	            mostrarEstadoCombate();

	            // ── Turno del jugador ─────────────────────────────────────────
	            String resultado = turnoJugador();
	            System.out.println();
	            System.out.println("  → " + resultado);

	            // Verificar si el enemigo cayó
	            if (!enemigo.estaVivo()) break;

	            pausarBreve();

	            // ── Turno del enemigo ─────────────────────────────────────────
	            System.out.println();
	            String ataqueEnemigo = enemigo.atacar(jugador);
	            System.out.println(ataqueEnemigo);

	            // ── Efectos de estado ─────────────────────────────────────────
	            String efectos = enemigo.procesarEfectos();
	            if (!efectos.isEmpty()) System.out.print(efectos);

	            // Actualizar escudo de la Princesa si aplica
	            if (jugador instanceof Princesa) {
	                ((Princesa) jugador).actualizarEscudo();
	            }

	            pausarBreve();
	        }

	        // ── Resultado ─────────────────────────────────────────────────────
	        return mostrarResultado();
	    }

	    // ── Turno del jugador ─────────────────────────────────────────────────
	    private String turnoJugador() {
	        System.out.println();
	        System.out.println("  ╔══════════════════════════════════════╗");
	        System.out.println("  ║        ¿QUÉ HARÁS?                   ║");
	        System.out.println("  ╠══════════════════════════════════════╣");

	        String[] habilidades = jugador.getMenuHabilidades();
	        for (String h : habilidades) {
	            System.out.printf("  ║  %-36s║%n", h);
	        }
	        System.out.println("  ╚══════════════════════════════════════╝");
	        System.out.print("  Tu elección: ");

	        int opcion = leerEntero(1, habilidades.length);
	        return jugador.usarHabilidad(opcion, enemigo);
	    }

	    // ── Mostrar estado de ambos ───────────────────────────────────────────
	    private void mostrarEstadoCombate() {
	        System.out.println();
	        separarLinea();
	        jugador.mostrarEstado();
	        System.out.println();
	        enemigo.mostrarEstado();
	        separarLinea();
	    }

	    // ── Intro del combate ─────────────────────────────────────────────────
	    private void mostrarIntro() {
	        System.out.println();
	        separarLinea();
	        if (enemigo.esJefe()) {
	            System.out.println("  ★ ¡COMBATE DE JEFE! ★");
	        } else {
	            System.out.println("  ⚔  ¡COMBATE!");
	        }
	        System.out.println("  " + jugador.getNombre()
	                         + "  VS  " + enemigo.getNombre());
	        System.out.println("  " + enemigo.getDescripcion());
	        separarLinea();
	        pausar();
	    }

	    // ── Resultado final del combate ───────────────────────────────────────
	    private boolean mostrarResultado() {
	        System.out.println();
	        separarLinea();
	        if (jugador.estaVivo()) {
	            System.out.println("  ★ ¡VICTORIA! ★");
	            System.out.println("  " + enemigo.getNombre() + " ha sido derrotado.");
	            System.out.printf ("  Recuperas %d de maná.%n", enemigo.getManaRecompensa());
	            jugador.recuperarMana(enemigo.getManaRecompensa());
	            separarLinea();
	            pausar();
	            return true;
	        } else {
	            System.out.println("  ✦ HAS SIDO DERROTADO ✦");
	            System.out.println("  " + enemigo.getNombre()
	                             + " te ha vencido. Moonhollow cae en oscuridad...");
	            separarLinea();
	            pausar();
	            return false;
	        }
	    }

	    // ── Utilidades ────────────────────────────────────────────────────────
	    private int leerEntero(int min, int max) {
	        while (true) {
	            try {
	                int valor = Integer.parseInt(scanner.nextLine().trim());
	                if (valor >= min && valor <= max) return valor;
	                System.out.print("  Elige entre " + min + " y " + max + ": ");
	            } catch (NumberFormatException e) {
	                System.out.print("  Entrada inválida. Ingresa un número: ");
	            }
	        }
	    }

	    private void separarLinea() {
	        System.out.println("  ══════════════════════════════════════════════");
	    }

	    private void pausar() {
	        System.out.print("\n  [Presiona Enter para continuar...]");
	        scanner.nextLine();
	    }

	    private void pausarBreve() {
	        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
	    }
	}


