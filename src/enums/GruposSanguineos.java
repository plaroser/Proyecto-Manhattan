package enums;

/**
 * Lista de grupos sanguineos
 * 
 * @author Sergio Pla Rojas
 *
 */
public enum GruposSanguineos {

	// A+,A-,B+,B-,AB+,AB-,O+,O-
	A_POSITIVO("A+"), A_NEGATIVO("A-"), B_POSITIVO("B+"), B_NEGATIVO("B-"), AB_POSITIVO("AB+"), AB_NEGATIVO(
			"AB-"), CERO_POSITIVO("0+"), CERO_NEGATIVO("0-");
	private String value;

	private GruposSanguineos(String value) {
		this.value = value;
	}

	public String toString() {
		return this.value;
	}
}
