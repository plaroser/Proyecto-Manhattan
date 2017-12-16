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

	View view;
	private int nTelefono;

	public void startProgram() {
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
		int telefono = view.leerTelefono();
		// Busca el archivo en el dispositivo
		File archivo = buscarAgenda(telefono);
		// Establece el numero de telefono
		this.nTelefono = telefono;
		Agenda agenda = null;
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
					ArrayList<Contacto> listaContactos = (ArrayList<Contacto>) agenda.getListaContactos();
					listaContactos.add(view.crearContacto());
					break;
				case 3:
					// 3.- Actualizar contacto.
					Contacto c = view.seleccionarContacto(agenda);
					if (c != null) {
						int indice = agenda.getListaContactos().indexOf(c);
						c = view.crearContacto();
						agenda.getListaContactos().set(indice, c);
					}
					break;
				case 4:
					// 4.- Eliminar contacto.
					c = view.seleccionarContacto(agenda);
					if (c != null) {
						int indice = agenda.getListaContactos().indexOf(c);
						agenda.getListaContactos().remove(indice);
						view.mostrarMensaje("Contacto eliminado con exito!");
					}
					break;
				case 5:
					// 5.- Ver contactos
					view.verContactos(agenda);
					break;
				case 6:
					// 6.- Importar contactos de otra agenda.
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
			Path path = Paths.get("c:\\agendas");
			Files.walkFileTree(path, l);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return l.agenda;
	}

	private void opcionesDirector() {

	}
}
