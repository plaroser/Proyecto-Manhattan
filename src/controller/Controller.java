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
		int telefono = view.leerTelefono();
		File archivo = buscarAgenda(telefono);
		this.nTelefono = telefono;
		Agenda agenda = null;
		if (archivo != null)
			agenda = Agenda.cargarAgendaDeArchivo(archivo);
		if (agenda != null) {
			int opcion = view.mostrarOpcionesEmpleado();
			switch (opcion) {
			case 1:
				agenda = view.editarInformacion(telefono, (ArrayList<Contacto>) agenda.getListaContactos());
				break;
			case 2:
				agenda.getListaContactos().add(view.crearContacto());
				break;
			case 3:
				
				break;
			case 4:
				break;
			case 5:
				break;
			default:
				break;
			}
		} else {
			agenda = view.crearAgenda(nTelefono);
		}
		Agenda.guardarAgenda(agenda);
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
		// TODO Auto-generated method stub

	}

}
