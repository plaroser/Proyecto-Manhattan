package models;

import java.io.Serializable;

public class Contacto implements Serializable {

	private static final long serialVersionUID = 7113840444049277238L;

	private int nTelf;
	private String name;
	private boolean isSpecial;

	public int getnTelf() {
		return nTelf;
	}

	public void setnTelf(int nTelf) {
		this.nTelf = nTelf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	@Override
	public String toString() {
		String salida = "";
		salida += "Nombre: " + name + "\n" + "No telefono: " + nTelf + "\n";
		if (isSpecial)
			salida += "Es contacto especial\n";
		return salida;
	}
}
