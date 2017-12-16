package models;

import java.io.Serializable;
import java.util.List;

import enums.GruposSanguineos;

public class Empleado implements Serializable {

	private static final long serialVersionUID = -815500801588612332L;
	
	private Integer nTel;
	private String name, surname, birthDate, departament, startDate;
	private List<String> listCode;
	private GruposSanguineos gruposSanguineos;

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
		return gruposSanguineos;
	}

	public void setGruposSanguineos(GruposSanguineos gruposSanguineos) {
		this.gruposSanguineos = gruposSanguineos;
	}

}
