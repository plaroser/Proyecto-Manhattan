package enums;

/**
 * Lista de Tipos de archivos
 * 
 * @author Sergio Pla Rojas
 *
 */
public enum TipoDeArchivos {
	json(".json"), xml(".xml"), serializado(".spr");

	private String value;

	private TipoDeArchivos(String value) {
		this.value = value;
	}

	/**
	 * Busca TipoDeArchivo segun un string
	 * 
	 * @param extension
	 *            String a buscar
	 * @return EL tipo de archivo encontrado o null si no lo encuentra
	 */
	public static TipoDeArchivos getTipo(String extension) {
		for (TipoDeArchivos element : TipoDeArchivos.values()) {
			if (element.toString().equals(extension)) {
				return element;
			}
		}
		return null;
	}

	public String toString() {
		return this.value;
	}
}
