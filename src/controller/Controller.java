package controller;

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
			opcionesEmpleado();
			break;
		}
	}

	private void opcionesDirector() {
		// TODO Auto-generated method stub
		
	}

	private void opcionesEmpleado() {

	}

}
