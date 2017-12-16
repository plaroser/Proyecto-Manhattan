package view;

import java.util.Scanner;

public class View {

	private final String MENU_PRINCIPAL = "----\nMenu princial\n----\n1.- Iniciar sesión como director.\n2.- Iniciar sesion como empleado.",
			INTRODUCE_OPCION = "Introduce la opción deseada: ", ERROR_OPCION_ERRONEA = "ERROR: Opcion no disponible.";

	private Scanner sc = new Scanner(System.in);

	/**
	 * Muestra el menu principal y lee la opcion elegida
	 * 
	 * @return opcion elegida
	 */
	public int mostarMenuPrincipal() {
		System.out.println(MENU_PRINCIPAL);
		return leerOpcionesMenu(2);
	}

	/**
	 * Lee por teclado las opciones de un menu
	 * 
	 * @param opcionesMaximas
	 *            opciones maximas disponibles
	 * @return opcion seleccionada
	 */
	private int leerOpcionesMenu(int opcionesMaximas) {
		boolean esCorrecto;
		int opcion = 0;
		String aux = null;
		do {
			esCorrecto = false;
			System.out.print(INTRODUCE_OPCION);
			aux = sc.next();
			try {
				opcion = Integer.parseInt(aux);
			} catch (Exception e) {
				esCorrecto = false;
			}
			esCorrecto = opcion > 0 && opcion <= opcionesMaximas;
			if (!esCorrecto)
				System.out.println(ERROR_OPCION_ERRONEA);
		} while (!esCorrecto);
		return opcion;
	}

}
