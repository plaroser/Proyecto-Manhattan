package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import models.Agenda;
import view.View;

/**
 * 
 * @author Sergio Pla
 *
 */
public class Controller {

	View view;

	public void startProgram() {
		view = new View();

		switch (view.mostarMenuPrincipal()) {
		case 1:
			opcionesDirector();
			break;
		case 2:
			leerNumeroTelefono();
			break;
		}
	}

	private void leerNumeroTelefono() {
		File archivo = buscarAgenda(view.leerTelefono());
		Agenda agenda = null;
		if (archivo != null)
			agenda = Agenda.cargarAgendaDeArchivo(archivo);
		if (agenda != null) {
			view.mostrarOpcionesEmpleado();
		} else {
			view.crearAgenda();
		}
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

	private void opcionesEmpleado() {
		view.mostrarOpcionesEmpleado();
	}

}
