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
	private final String MENU_CARGAR_TELEFONO = "Introduce tu número de telefono: ";
	private final String ERROR_OPCION_ERRONEA = "ERROR: Opcion no disponible.";
	private final String ERROR_NUMERO_TELEFONO = "ERROR: Numero de telefono no valido.";

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
		Scanner sc = new Scanner(System.in);
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
		Scanner sc = new Scanner(System.in);
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

	public int mostrarOpcionesEmpleado() {
		System.out.println("Elige la opcion deseada:\n" + "1.- Editar mi información personal.\n"
				+ "2.- Añadir contacto.\n" + "3.- Actualizar contacto.\n" + "4.- Eliminar contacto.\n"
				+ "5.- Importar contactos de otra agenda.\n6.-Salir");
		return leerOpcionesMenu(6);
	}

	public Agenda crearAgenda(int nTelefono) {
		System.out.println("Crear agenda...");
		ArrayList<Contacto> listaContactos = new ArrayList<>(5);
		return editarInformacion(nTelefono, listaContactos);
	}

	public Agenda editarInformacion(int nTelefono, ArrayList<Contacto> listaContactos) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce tu nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Introduce tus apellidos: ");
		String apellidos = sc.nextLine();
		System.out.print("Introduce tu fecha de nacimiento: ");
		String fnacimiento = sc.nextLine();
		System.out.print("Introduce tu departamento: ");
		String departamento = sc.nextLine();
		System.out.print("Introduce tu fecha de inicio: ");
		String fInicio = sc.nextLine();
		GruposSanguineos grupoSan = leerGrupoSanguineo();
		TipoDeArchivos tipo = leerTipoDeArchivo();
		ArrayList<String> listaCodigos = leerListaCodigos();
		Empleado a = new Empleado(nTelefono, nombre, apellidos, fnacimiento, departamento, fInicio, listaCodigos,
				grupoSan.toString());
		System.out.println("Agenda guardad con exito!");
		return new Agenda(a, listaContactos, tipo.toString());
	}

	private TipoDeArchivos leerTipoDeArchivo() {
		System.out.println("Selecciona tu tipo de archivo:");
		TipoDeArchivos[] tipos = TipoDeArchivos.values();
		for (int i = 0; i < tipos.length; i++) {
			System.out.println(i + 1 + ".- " + tipos[i].name());
		}
		return tipos[leerOpcionesMenu(tipos.length) - 1];
	}

	private ArrayList<String> leerListaCodigos() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Creando lista de codigos de operaciones.\n" + "");
		ArrayList<String> listaCodigos = new ArrayList<>();
		boolean esCorrecto = false, salir = false;
		do {
			System.out.println("Codigo de ejemplo: \"ABC-123\"");
			System.out.print("Introduce un codigo o pulsa intro para terminar: ");
			String aux = sc.nextLine();
			Matcher m = Pattern.compile("^\\D{3}[-]\\d{3}$").matcher(aux);
			esCorrecto = m.find();
			if (esCorrecto) {
				listaCodigos.add(aux.toUpperCase());
			} else if (aux.equals("")) {
				salir = true;
			} else {
				System.out.println("ERROR: Debes introducir un codigo o dejarlo vacio y pulsar intro.");
			}
		} while (!salir);
		return listaCodigos;
	}

	private GruposSanguineos leerGrupoSanguineo() {

		System.out.println("Introduce tu grupo sanguineo:");
		GruposSanguineos[] grupos = GruposSanguineos.values();
		for (int i = 0; i < grupos.length; i++) {
			System.out.println(i + 1 + ".- " + grupos[i].toString());
		}
		return grupos[leerOpcionesMenu(grupos.length) - 1];
	}

	public Contacto crearContacto() {
		Scanner sc = new Scanner(System.in);
		Contacto c = new Contacto();
		System.out.println("Introduce el nombre del contacto:");
		String nombre = sc.nextLine();
		c.setName(nombre);
		int numero = leerTelefono();
		c.setnTelf(numero);
		System.out.println("¿Es contacto especial?\nIntroduce \"S\" para si o cualquier otra cosa para no:");
		String aux = sc.nextLine();
		boolean esEpecial = aux.toUpperCase().equals("S");
		c.setSpecial(esEpecial);
		return c;
	}

	public Contacto seleccionarContacto(Agenda a) throws InterruptedException {
		ArrayList<Contacto> lista = (ArrayList<Contacto>) a.getListaContactos();
		if (!lista.isEmpty()) {
			System.out.println("Introduce una opcion para seleccionar un contacto:");
			for (int i = 0; i < lista.size(); i++) {
				System.out.println(i + 1 + ".-" + lista.get(i).getName());
			}
			return lista.get(leerOpcionesMenu(lista.size())-1);
		} else {
			System.out.println("No hay contactos.");
			Thread.sleep(1500);
			return null;
		}
	}

	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}
}
