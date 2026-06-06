
/**
 * Utilidad para mostrar barras de vida y maná en consola. Ejemplo de salida:
 * Vida : [████████████░░░░░░░░] 80/100 Maná : [████░░░░░░░░░░░░░░░░] 30/140
 */
public class BarraVida {

	/**
	 * Genera una barra de vida con bloques de color ASCII.
	 * 
	 * @param actual   valor actual
	 * @param maximo   valor máximo
	 * @param longitud número de bloques totales en la barra
	 * @return String con la barra formateada
	 */
	public static String generar(int actual, int maximo, int longitud) {
		int llenos = (int) Math.round((double) actual / maximo * longitud);
		llenos = Math.max(0, Math.min(longitud, llenos));

		StringBuilder barra = new StringBuilder("[");
		for (int i = 0; i < longitud; i++) {
			barra.append(i < llenos ? "█" : "░");
		}
		barra.append("] ");
		barra.append(actual).append("/").append(maximo);

		// Indicador de estado crítico
		double porcentaje = (double) actual / maximo;
		if (porcentaje <= 0.25) {
			barra.append(" ¡CRÍTICO!");
		} else if (porcentaje <= 0.5) {
			barra.append(" Bajo");
		}

		// Relleno para alinear el borde derecho de la caja
		String resultado = barra.toString();
		int espacios = 36 - resultado.length();
		for (int i = 0; i < espacios; i++)
			resultado += " ";
		return resultado;
	}

	/**
	 * Barra de maná (misma lógica, etiqueta diferente).
	 */
	public static String generarMana(int actual, int maximo, int longitud) {

	    int llenos = (int) Math.round((double) actual / maximo * longitud);
	    llenos = Math.max(0, Math.min(longitud, llenos));

	    StringBuilder barra = new StringBuilder("[");

	    for (int i = 0; i < longitud; i++) {
	        barra.append(i < llenos ? "█" : "░");
	    }

	    barra.append("] ");
	    barra.append(actual).append("/").append(maximo);

	    return barra.toString();
	
	
	}

	/**
	 * Versión compacta para mostrar junto al nombre del enemigo.
	 */
	public static String generarCompacto(int actual, int maximo, int longitud) {
		int llenos = (int) Math.round((double) actual / maximo * longitud);
		llenos = Math.max(0, Math.min(longitud, llenos));
		StringBuilder barra = new StringBuilder("[");
		for (int i = 0; i < longitud; i++) {
			barra.append(i < llenos ? "█" : "░");
		}
		barra.append("] ").append(actual).append("/").append(maximo);
		return barra.toString();
	}
}
