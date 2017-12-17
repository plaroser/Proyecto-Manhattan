package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import models.Agenda;
import models.Contacto;
import view.View;

/**
 * 
 * @author Sergio Pla
 *
 */
public class Controller {

	private View view;
	private int nTelefono;
	private Agenda agenda;
	private int telefono;
	private File archivo;

	public void startProgram() {
		File theDir = new File(Agenda.RUTA_AGENDAS);
		agenda = null;
		if (!theDir.exists()) {
			try {
				theDir.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		view = new View();

		switch (view.mostarMenuPrincipal()) {
		case 1:
			opcionesDirector();
			break;
		case 2:
			opcionesEmpleado();
			break;
		}
	}

	private void opcionesEmpleado() {
		// Lee el telefono
		telefono = view.leerTelefono();
		// Busca el archivo en el dispositivo
		archivo = buscarAgenda(telefono);
		// Establece el numero de telefono
		this.nTelefono = telefono;

		// Si ha encontrado el archivo
		if (archivo != null)
			agenda = Agenda.cargarAgendaDeArchivo(archivo);
		boolean salir = false;
		do {
			// Su la agenda esta creada
			if (agenda != null) {
				// Muestra el menu de empleado
				int opcion = view.mostrarOpcionesEmpleado();
				switch (opcion) {
				
				case 1:
					// 1.- Editar mi información personal.\n"
					agenda = view.editarInformacion(telefono, (ArrayList<Contacto>) agenda.getListaContactos());
					break;
				case 2:
					// 2.- Añadir contacto.
					aniadirContacto();
					break;
				case 3:
					// 3.- Actualizar contacto.
					actualizarContacto();
					break;
				case 4:
					// 4.- Eliminar contacto.
					eliminarContacto();
					break;
				case 5:
					// 5.- Ver contactos
					view.verContactos(agenda);
					break;
				case 6:
					// 6.- Importar contactos de otra agenda.
					importarContactos();
					break;

				case 7:
					salir = true;
					break;
				default:
					break;
				}
			} else {
				// Crear la agenda
				agenda = view.crearAgenda(nTelefono);
			}
			Agenda.guardarAgenda(agenda);
		} while (!salir);
	}

	private void importarContactos() {
		telefono = view.leerTelefono();
		archivo = buscarAgenda(telefono);
		if (archivo != null) {
			Agenda nuevaAgenda = Agenda.cargarAgendaDeArchivo(archivo);
			if (!nuevaAgenda.getListaContactos().isEmpty()) {
				for (Contacto contacto : nuevaAgenda.getListaContactos()) {
					agenda.getListaContactos().add(contacto);
				}
				view.mostrarMensaje("Contactos importados con exito!");
			} else
				view.mostrarMensaje("No contiene contactos");
		} else {
			view.mostrarMensaje("ERROR: Agenda no encontrada");
		}
	}

	private void eliminarContacto() {
		Contacto c = view.seleccionarContacto(agenda);
		if (c != null) {
			int indice = agenda.getListaContactos().indexOf(c);
			agenda.getListaContactos().remove(indice);
			view.mostrarMensaje("Contacto eliminado con exito!");
		}
	}

	private void actualizarContacto() {
		Contacto c = view.seleccionarContacto(agenda);
		if (c != null) {
			int indice = agenda.getListaContactos().indexOf(c);
			c = view.crearContacto();
			agenda.getListaContactos().set(indice, c);
		}
	}

	private void aniadirContacto() {
		ArrayList<Contacto> listaContactos = (ArrayList<Contacto>) agenda.getListaContactos();
		Contacto c = view.crearContacto();
		if (c.isSpecial()) {
			int contadorEspeciales = 0;
			for (Contacto contacto : listaContactos) {
				if (contacto.isSpecial())
					contadorEspeciales++;
			}
			if (contadorEspeciales >= 5) {
				c.setSpecial(false);
				view.mostrarMensaje("Lista de contactos especiales llena. Se guardara como contacto normal");
			}
		}
		listaContactos.add(c);
	}

	private File buscarAgenda(int numeroTelefono) {
		class Lector extends SimpleFileVisitor<Path> {
			public File agenda = null;

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				File archivo = new File(file.toString());
				String nombre = archivo.getName();
				String numero = String.valueOf(numeroTelefono);
				if (nombre.contains(numero) && agenda == null)
					agenda = archivo;
				return FileVisitResult.CONTINUE;
			}
		}
		Lector l = new Lector();
		try {
			Path path = Paths.get(Agenda.RUTA_AGENDAS);
			Files.walkFileTree(path, l);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return l.agenda;
	}

	private Agenda[] buscarListaDeAgendas() {
		class Lector extends SimpleFileVisitor<Path> {
			public ArrayList<Agenda> lista = new ArrayList<>();

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				File archivo = new File(file.toString());
				Agenda a = Agenda.cargarAgendaDeArchivo(archivo);
				if (a != null) {
					lista.add(a);
				}
				return FileVisitResult.CONTINUE;
			}
		}
		Lector l = new Lector();
		try {
			Path path = Paths.get(Agenda.RUTA_AGENDAS);
			Files.walkFileTree(path, l);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return l.lista.toArray(new Agenda[l.lista.size()]);
	}

	private void opcionesDirector() {
		Agenda[] agendas = buscarListaDeAgendas();
		// Si encuentra agendas
		if (agendas.length > 0) {
			boolean salir = false;
			do {
				int opcion = view.menuDirector();
				switch (opcion) {
				// 1.- Ver todos los empleados y contactos.
				case 1:
					for (Agenda a : agendas) {
						view.imprimirAgenda(a);
					}
					break;
				// 2.- Ver contactps especiales de un empleado.
				case 2:
					Agenda agendaSeleccionada = view.seleccionarAgenda(agendas);
					view.mostarContactosEspeciales(agendaSeleccionada);
					break;
				// 3.- Ver empleados por Departamento.
				case 3:
					view.mostrarEmpleadosPorDepartamento(agendas);
					break;
				// 4.- Ver empleados por grupo sanguineo.
				case 4:
					view.mostrarEmpleadosPorGrupoSanguineo(agendas);
					break;
				// 5.- Ver los efectivos de una operacion.
				case 5:
					view.seleccionarOperacion(agendas);
					break;
				case 6:
					salir = true;
					break;
				default:
					break;
				}
			} while (!salir);
			// Si no encuentra agendas
		} else {
			view.mostrarMensaje("No exixten agendas.");
		}
	}
}
