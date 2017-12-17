package models;

import java.io.Serializable;
import java.util.List;

import enums.GruposSanguineos;

public class Empleado implements Serializable {

	private static final long serialVersionUID = -815500801588612332L;

	private Integer nTel;
	private String name, surname, birthDate, departament, startDate;
	private List<String> listCode;
	private String gruposSanguineo;

	public Empleado(Integer nTel, String name, String surname, String birthDate, String departament, String startDate,
			List<String> listCode, String gruposSanguineo) {
		super();
		this.nTel = nTel;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.departament = departament;
		this.startDate = startDate;
		this.listCode = listCode;
		this.gruposSanguineo = gruposSanguineo;
	}

	public Integer getnTel() {
		return nTel;
	}

	public void setnTel(Integer nTel) {
		this.nTel = nTel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public List<String> getListCode() {
		return listCode;
	}

	public void setListCode(List<String> listCode) {
		this.listCode = listCode;
	}

	public GruposSanguineos getGruposSanguineos() {
		return GruposSanguineos.valueOf(gruposSanguineo);
	}

	public void setGruposSanguineos(GruposSanguineos gruposSanguineo) {
		this.gruposSanguineo = gruposSanguineo.toString();
	}

	@Override
	public String toString() {
		String salida = "";
		salida += "Empleado: " + name + "\n" + "Apellidos: " + surname + "\n" + "Numero de telefono: " + nTel + "\n"
				+ "F nacimiento: " + birthDate + "\n" + "Departamento: " + departament + "\n"
				+ "F de inicio en el servicio: " + startDate + "\nLista de codigos:\n---------";
		for (String s : listCode) {
			salida += "\t-" + s + "\n";
		}
		salida += "---------\nGrupo sanguineo: " + gruposSanguineo + "\n";
		return salida;
	}

}
