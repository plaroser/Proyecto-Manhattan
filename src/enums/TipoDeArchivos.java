package enums;

public enum TipoDeArchivos {
	json(".json"), xml(".xml"), serializado(".spr");

	private String value;

	private TipoDeArchivos(String value) {
		this.value = value;
	}

	public static TipoDeArchivos getTipo(String extension) {
		for (TipoDeArchivos element : TipoDeArchivos.values()) {
			if(element.toString().equals(extension)) {
				return element;
			}
		}
		return null;
	}
	
	public String toString() {
		return this.value;
	}
}
