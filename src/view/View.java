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
	private final String MENU_CARGAR_TELEFONO = "Introduce el número de telefono: ";
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
			else {
				System.out.println(ERROR_NUMERO_TELEFONO);

			}
		} while (!esCorrecto);
		return numero;
	}

	public int mostrarOpcionesEmpleado() {
		System.out.println("====================\nLista de opciones:\n====================\n"
				+ "1.- Editar mi información personal.\n" + "2.- Añadir contacto.\n" + "3.- Actualizar contacto.\n"
				+ "4.- Eliminar contacto.\n" + "5.- Ver contactos\n"
				+ "6.- Importar contactos de otra agenda.\n7.-Salir");
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
		System.out.println("Contacto guardado con exito!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return c;
	}

	public Contacto seleccionarContacto(Agenda a) {
		ArrayList<Contacto> lista = (ArrayList<Contacto>) a.getListaContactos();
		if (!lista.isEmpty()) {
			System.out.println("Introduce una opcion para seleccionar un contacto:");
			for (int i = 0; i < lista.size(); i++) {
				System.out.println(i + 1 + ".-" + lista.get(i).getName());
			}
			return lista.get(leerOpcionesMenu(lista.size()) - 1);
		} else {
			System.out.println("No hay contactos.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public void verContactos(Agenda a) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Contacto> lista = (ArrayList<Contacto>) a.getListaContactos();
		if (!lista.isEmpty()) {
			System.out.println("Lista de contactos:");
			for (int i = 0; i < lista.size(); i++) {
				System.out.println(i + 1 + ".-" + lista.get(i).getName());
			}
			System.out.println("Pulsa intro para continuar..");
			sc.nextLine();
		} else {
			System.out.println("No hay contactos.");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int menuDirector() {
		System.out.println("====================\nLista de opciones:\n====================\n"
				+ "1.- Ver todos los empleados y contactos.\n" + "2.- Ver contactos especiales de un empleado.\n"
				+ "3.- Ver empleados por Departamento.\n" + "4.- Ver empleados por grupo sanguineo.\n"
				+ "5.- Ver los efectivos de una operacion.");
		return leerOpcionesMenu(5);
	}

	public void imprimirAgenda(Agenda a) {
		System.out.println(a.toString());
	}

	public Agenda seleccionarAgenda(Agenda[] agendas) {
		String salida = "";
		salida += "====================\nSeleccione un empleado:\n====================\n----------\n";
		for (int i = 0; i < agendas.length; i++) {
			salida += i + 1 + ".- " + agendas[i].getEmpleado().getName() + "\n----------\n";
		}
		System.out.print(salida);
		int opcion = leerOpcionesMenu(agendas.length);
		return agendas[opcion - 1];
	}

	public void mostarContactosEspeciales(Agenda a) {
		String salida = "";
		boolean tieneEspeciales = false;
		salida += "Lista de contactos especiales:\n";
		for (Contacto c : a.getListaContactos()) {
			if (c.isSpecial()) {
				tieneEspeciales = true;
				salida += c.toString() + " \n";
			}
		}
		if (!tieneEspeciales) {
			salida = "No dispone de contactos especiales.";
		}
		System.out.println(salida);
	}

	public void mostrarEmpleadosPorDepartamento(Agenda[] agendas) {
		ArrayList<Agenda> lista = new ArrayList<>();
		for (Agenda a : agendas) {
			lista.add(a);
		}
		lista.sort(Agenda.porDepartamento);
		String departamentoActual = lista.get(0).getEmpleado().getDepartament();
		String salida = "====================\nEmpleados por departamento:\n====================\n" + "Departamento: "
				+ departamentoActual + "\n";
		for (Agenda a : lista) {
			if (!a.getEmpleado().getDepartament().equals(departamentoActual)) {
				departamentoActual = a.getEmpleado().getDepartament();
				salida += "Departamento: " + departamentoActual + "\n--------------------\n";
			}
			salida += a.getEmpleado().toString() + "\n---\n";
		}
		System.out.println(salida);
	}

	public void mostrarEmpleadosPorGrupoSanguineo(Agenda[] agendas) {
		ArrayList<Agenda> lista = new ArrayList<>();
		for (Agenda a : agendas) {
			lista.add(a);
		}
		lista.sort(Agenda.porGrupoSanguineo);
		String grupoActual = lista.get(0).getEmpleado().getStringGrupoSanguineo();
		String salida = "====================\nEmpleados por Grupo Sanguineo:\n====================\n"
				+ "************************\nGrupo Sanguineo: " + grupoActual + "\n************************\n";
		for (Agenda a : lista) {
			if (!a.getEmpleado().getStringGrupoSanguineo().equals(grupoActual)) {
				grupoActual = a.getEmpleado().getStringGrupoSanguineo();
				salida += "************************\nGrupo Sanguineo: " + grupoActual + "\n************************\n";
			}
			salida += a.getEmpleado().toString() + "\n--------------------------------------\n";
		}
		System.out.println(salida);
	}
}
