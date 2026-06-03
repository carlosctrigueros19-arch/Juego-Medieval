import java.util.Scanner;

/**
 * Clase principal — El Trono de la Oscuridad
 */
public class Main {

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			mostrarPortada();
			pausar();
			menuPrincipal();
		} catch (Exception e) {
			System.out.println("\n  Ocurrió un error inesperado. Reiniciando menú...");
			menuPrincipal();
		} finally {
			Musica.detenerMusica();
			if (scanner != null)
				scanner.close();
		}
	}

	// ── Menú principal ────────────────────────────────────────────────────
	static void menuPrincipal() {
		Musica.musicaMenu();
		boolean salir = false;

		while (!salir) {
			System.out.println();
			System.out.println("  ╔══════════════════════════════════════╗");
			System.out.println("  ║     EL TRONO DE LA OSCURIDAD         ║");
			System.out.println("  ╠══════════════════════════════════════╣");
			System.out.println("  ║  1. Nueva partida                    ║");
			System.out.println("  ║  2. Historia del mundo               ║");
			System.out.println("  ║  3. Créditos                         ║");
			System.out.println("  ║  4. Opciones de audio                ║");
			System.out.println("  ║  5. Salir                            ║");
			System.out.println("  ╚══════════════════════════════════════╝");
			System.out.print("  Elige una opción (1-5): ");

			int opcion = leerEntero(1, 5);

			switch (opcion) {
			case 1:
				nuevaPartida();
				break;
			case 2:
				mostrarLore();
				break;
			case 3:
				mostrarCreditos();
				break;
			case 4:
				opcionesAudio();
				break;
			case 5:
				System.out.println("\n  La oscuridad aguarda... Hasta pronto.\n");
				salir = true;
				break;
			}
		}
	}

	// ── Nueva partida ─────────────────────────────────────────────────────
	static void nuevaPartida() {
		Musica.detenerMusica();
		limpiarPantalla();

		System.out.println();
		System.out.println("  ══════════════════════════════════════════════");
		System.out.println("   EL REINO DE MOONHOLLOW ESTÁ EN PELIGRO.");
		System.out.println("   El Trono de la Oscuridad se ha fracturado.");
		System.out.println("   Sus tres fragmentos han salido disparados");
		System.out.println("   hacia el Bosque Sombrío, el Lago Mágico");
		System.out.println("   y la Torre Antigua de los Dragones.");
		System.out.println("  ══════════════════════════════════════════════");
		pausar();

		String nombre = "";
		while (nombre.isEmpty()) {
			System.out.print("  Ingresa tu nombre, héroe: ");
			try {
				nombre = scanner.nextLine().trim();
				if (nombre.isEmpty()) {
					System.out.println("  ⚠ El nombre no puede estar vacío. Intenta de nuevo.");
				}
			} catch (Exception e) {
				nombre = "Héroe";
			}
		}

		Personaje jugador = seleccionarPersonaje(nombre);
		limpiarPantalla();
		jugador.mostrarHistoria();
		pausar();

		Gameengine engine = new Gameengine(jugador, scanner);
		engine.iniciar();
		Musica.musicaMenu();
	}

	// ── Selección de personaje ────────────────────────────────────────────
	static Personaje seleccionarPersonaje(String nombre) {
		limpiarPantalla();
		boolean seleccionValida = false;
		Personaje jugador = null;

		while (!seleccionValida) {
			System.out.println();
			System.out.println("  ╔══════════════════════════════════════════════╗");
			System.out.println("  ║        ELIGE TU PERSONAJE                    ║");
			System.out.println("  ╠══════════════════════════════════════════════╣");
			System.out.println("  ║  1. Princesa   — Soporte / Magia sagrada     ║");
			System.out.println("  ║     Vida: 120  Maná: 100  ATK: 35  DEF: 20   ║");
			System.out.println("  ║     Arma: Cetro de la Luz                    ║");
			System.out.println("  ╠══════════════════════════════════════════════╣");
			System.out.println("  ║  2. Caballero  — Tanque / Combate físico     ║");
			System.out.println("  ║     Vida: 150  Maná:  60  ATK: 55  DEF: 35   ║");
			System.out.println("  ║     Arma: Escalibur                          ║");
			System.out.println("  ╠══════════════════════════════════════════════╣");
			System.out.println("  ║  3. Hechicero  — Villano ofensivo            ║");
			System.out.println("  ║     Vida: 100  Maná: 140  ATK: 65  DEF: 10   ║");
			System.out.println("  ║     Arma: Magia oscura del Trono             ║");
			System.out.println("  ╚══════════════════════════════════════════════╝");
			System.out.print("  Tu elección (1-3): ");

			int opcion = leerEntero(1, 3);

			switch (opcion) {
			case 1:
				jugador = new Princesa(nombre);
				seleccionValida = true;
				break;
			case 2:
				jugador = new Caballero(nombre);
				seleccionValida = true;
				break;
			case 3:
				jugador = new Hechicero(nombre);
				seleccionValida = true;
				break;
			}
		}

		System.out.println("\n  Has elegido: " + jugador.getNombre() + " — " + jugador.getRol());
		pausar();
		return jugador;
	}

	// ── Opciones de audio ─────────────────────────────────────────────────
	static void opcionesAudio() {
		limpiarPantalla();
		boolean salir = false;

		while (!salir) {
			System.out.println();
			System.out.println("  ╔══════════════════════════════════════╗");
			System.out.println("  ║        OPCIONES DE AUDIO             ║");
			System.out.println("  ╠══════════════════════════════════════╣");
			System.out.println("  ║  1. Activar / desactivar música      ║");
			System.out.println("  ║  2. Activar / desactivar efectos     ║");
			System.out.println("  ║  3. Subir volumen                    ║");
			System.out.println("  ║  4. Bajar volumen                    ║");
			System.out.println("  ║  5. Volver al menú                   ║");
			System.out.println("  ╚══════════════════════════════════════╝");
			System.out.print("  Elige una opción (1-5): ");

			int op = leerEntero(1, 5);
			switch (op) {
			case 1:
				Musica.toggleMusica();
				break;
			case 2:
				Musica.toggleEfectos();
				break;
			case 3:
				Musica.setVolumen(0.9f);
				System.out.println("  Volumen alto.");
				break;
			case 4:
				Musica.setVolumen(0.4f);
				System.out.println("  Volumen bajo.");
				break;
			case 5:
				salir = true;
				break;
			}
		}
	}

	// ── Lore ──────────────────────────────────────────────────────────────
	static void mostrarLore() {
		limpiarPantalla();
		System.out.println();
		System.out.println("  ══════════════════════════════════════════════");
		System.out.println("   EL REINO DE MOONHOLLOW");
		System.out.println("  ══════════════════════════════════════════════");
		System.out.println("  Hace siglos, Moonhollow era el reino más mágico");
		System.out.println("  del mundo. Sus bosques vibraban con energía");
		System.out.println("  natural y sus lagos respondían a la luna.");
		System.out.println();
		System.out.println("  En su centro existía el Trono de la Oscuridad:");
		System.out.println("  una fuente de poder capaz de traer equilibrio");
		System.out.println("  o destrucción absoluta, sellada por guardianes");
		System.out.println("  que comprendieron su peligro a tiempo.");
		System.out.println();
		System.out.println("  Hasta que un hechicero desterrado regresó");
		System.out.println("  para reclamar lo que creía suyo...");
		System.out.println("  ══════════════════════════════════════════════");
		pausar();
	}

	// ── Créditos ──────────────────────────────────────────────────────────
	static void mostrarCreditos() {
		limpiarPantalla();
		System.out.println();
		System.out.println("  ══════════════════════════════════════════════");
		System.out.println("   CRÉDITOS");
		System.out.println("  ══════════════════════════════════════════════");
		System.out.println("  Proyecto: Resolución de Problemas");
		System.out.println("  Instituto Nacional de Sonzacate");
		System.out.println("  Prof. Kevin Antonio Valenzuela");
		System.out.println();
		System.out.println("  Desarrollado en Java — Eclipse IDE");
		System.out.println("  Historia: El Trono de la Oscuridad");
		System.out.println("  ══════════════════════════════════════════════");
		pausar();
	}

	// ── Portada ───────────────────────────────────────────────────────────
	static void mostrarPortada() {
		String AMA = "\u001B[33m"; // amarillo
		String AZU = "\u001B[96m"; // azul brillante
		String NEG = "\u001B[1m"; // negrita
		String RES = "\u001B[0m"; // reset

		limpiarPantalla();
		System.out.println();

		System.out.println(AMA + NEG + " ___  _     _____  ___  ___  _  _  ___   ");
		System.out.println(AMA + NEG + "| __|| |   |_   _|| _ \\/ _ \\| \\| |/ _ \\ ");
		System.out.println(AMA + NEG + "| _| | |__   | |  |   / (_) | .` | (_) | ");
		System.out.println(AMA + NEG + "|___||____|  |_|  |_|\\_\\___/|_|\\_|\\___/  ");
		System.out.println(AMA + NEG + "  ___  ___                          ");
		System.out.println(AMA + NEG + " |   \\| __|                  ");
		System.out.println(AMA + NEG + " | |) | _|                  ");
		System.out.println(AMA + NEG + " |___/|___|                 ");

		System.out.println(AMA + NEG + "  ___  ___  ___  _   _ ___  ___ ___   _    ___  ");
		System.out.println(AMA + NEG + " / _ \\/ __|/ __|| | | | _ \\|_ _|   \\ /_\\  |   \\ ");
		System.out.println(AMA + NEG + "| (_) \\__ \\ (__ | |_| |   / | || |) / _ \\ | |) |");
		System.out.println(AMA + NEG + " \\___/|___/\\___| \\___/|_|\\_|___|___/_/ \\_\\|___/ ");

		System.out.println(AMA + NEG + "🌑  Reino de Moonhollow  ⚔️");

		System.out.println();
		System.out.println(AMA + NEG + "   \"El poder del Trono puede salvar... o destruir\"" + RES);
		System.out.println();
	}

	// ── Utilidades ────────────────────────────────────────────────────────
	static int leerEntero(int min, int max) {
		while (true) {
			try {
				String linea = scanner.nextLine();
				if (linea == null || linea.trim().isEmpty()) {
					System.out.print("  ⚠ Debes ingresar un número entre " + min + " y " + max + ": ");
					continue;
				}
				int valor = Integer.parseInt(linea.trim());
				if (valor >= min && valor <= max) {
					return valor;
				} else {
					System.out.print("  ⚠ Opción inválida. Elige entre " + min + " y " + max + ": ");
				}
			} catch (NumberFormatException e) {
				System.out.print("  ⚠ Eso no es un número. Ingresa entre " + min + " y " + max + ": ");
			} catch (Exception e) {
				System.out.print("  ⚠ Error de lectura. Intenta de nuevo: ");
			}
		}
	}

	static void pausar() {
		System.out.print("\n  [Presiona Enter para continuar...]");
		try {
			scanner.nextLine();
		} catch (Exception e) {
		}
	}

	static void limpiarPantalla() {
		for (int i = 0; i < 40; i++)
			System.out.println();
	}
}