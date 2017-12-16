package models;

import java.io.Serializable;
import java.util.List;

public class Agenda implements Serializable {

	private static final long serialVersionUID = 943295793237719639L;

	private Empleado empleado;
	private List<Contacto> listaContactos;

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public List<Contacto> getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(List<Contacto> listaContactos) {
		this.listaContactos = listaContactos;
	}

}
