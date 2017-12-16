package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import enums.GruposSanguineos;
import enums.TipoDeArchivos;
import models.Agenda;
import models.Contacto;
import models.Empleado;

public class View {

	private final String MENU_PRINCIPAL = "----\nMenu princial\n----\n1.- Iniciar sesión como director.\n2.- Iniciar sesion como empleado.";
	private final String INTRODUCE_OPCION = "Introduce la opción deseada: ";
	private final String MENU_CARGAR_TELEFONO = "\n----\nIniciar sesión\n----\nIntroduce tu número de telefono: ";
	private final String ERROR_OPCION_ERRONEA = "ERROR: Opcion no disponible.";
	private final String ERROR_NUMERO_TELEFONO = "ERROR: Numero de telefono no valido.";

	private final Scanner sc = new Scanner(System.in);

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

	public int leerTelefono() {
		boolean esCorrecto;
		int numero = 0;
		do {
			System.out.println(MENU_CARGAR_TELEFONO);
			String aux = sc.next();
			// Expresion regular que busca 9 digitos
			Matcher m = Pattern.compile("^[\\d]{9}$").matcher(aux);
			esCorrecto = m.find();
			if (esCorrecto)
				numero = Integer.parseInt(aux);
			else
				System.out.println(ERROR_NUMERO_TELEFONO);
		} while (!esCorrecto);
		return numero;
	}

	public void mostrarOpcionesEmpleado() {
		System.out.println("opciones empleado");
	}

	public Agenda crearAgenda() {
		System.out.println("----\nCreando agenda:\n----");
		System.out.print("Introduce tu nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Introduce tus apellidos: ");
		String apellidos = sc.nextLine();
		System.out.print("Introduce tu fecha de nacimiento: ");
		String fnacimiento = sc.nextLine();
		System.out.print("Introduce tu departamento: ");
		String departamento = sc.nextLine();
		System.out.print("Introduce tu fecha de inicio: ");
		String fInicio = sc.nextLine();
		ArrayList<String> listaCodigos = new ArrayList<>();
		ArrayList<Contacto> listaContactos = new ArrayList<>();
		int nTel = leerTelefono();
		GruposSanguineos grupoSan = leerGrupoSanguineo();
		TipoDeArchivos tipo = leerTipoDeArchivo();
		Empleado a = new Empleado(nTel, nombre, apellidos, fnacimiento, departamento, fInicio, listaCodigos,
				grupoSan.toString());
		return new Agenda(a, listaContactos, tipo.toString());
	}

	private TipoDeArchivos leerTipoDeArchivo() {
		System.out.println("Selecciona tu tipo de archivo:");
		TipoDeArchivos[] tipos = TipoDeArchivos.values();
		for (int i = 0; i < tipos.length; i++) {
			System.out.println(i + 1 + tipos[i].toString());
		}
		return tipos[leerOpcionesMenu(tipos.length) - 1];
	}

	private GruposSanguineos leerGrupoSanguineo() {
		System.out.println("Introduce tu grupo sanguineo:");
		GruposSanguineos[] grupos = GruposSanguineos.values();
		for (int i = 0; i < grupos.length; i++) {
			System.out.println(i + 1 + grupos[i].toString());
		}
		return grupos[leerOpcionesMenu(grupos.length) - 1];
	}

}
