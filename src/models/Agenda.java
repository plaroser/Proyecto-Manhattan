package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import enums.TipoDeArchivos;

public class Agenda implements Serializable {

	private static final long serialVersionUID = 943295793237719639L;

	private Empleado empleado;
	private List<Contacto> listaContactos;
	private TipoDeArchivos tipoDeArchivo;
	private final static XStream xstreamXML = new XStream();
	private static XStream xstreamJSon = new XStream(new JettisonMappedXmlDriver());

	public TipoDeArchivos getTipoDeArchivo() {
		return tipoDeArchivo;
	}

	public void setTipoDeArchivo(TipoDeArchivos tipoDeArchivo) {
		this.tipoDeArchivo = tipoDeArchivo;
	}

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

	public static Agenda cargarAgendaDeArchivo(File archivo) {
		String[] s = archivo.getName().split(".");
		String extension = s[s.length - 1];
		switch (TipoDeArchivos.getTipo(extension)) {
		case json:
			return cargarAgendaJSon(archivo);

		case xml:
			return cargarAgendaXML(archivo);

		case serializado:

			return cargarAgendaSerializado(archivo);
		default:
			return null;
		}
	}

	private static Agenda cargarAgendaXML(File archivo) {
		Agenda agenda = null;
		try {
			agenda = (Agenda) xstreamXML.fromXML(new String(Files.readAllBytes(archivo.toPath())));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return agenda;
	}

	private static Agenda cargarAgendaJSon(File archivo) {

		xstreamJSon.alias("product", Agenda.class);
		Agenda agenda = null;
		try {
			agenda = (Agenda) xstreamJSon.fromXML(new String(Files.readAllBytes(archivo.toPath())));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return agenda;
	}

	private static Agenda cargarAgendaSerializado(File archivo) {
		Agenda a;
		try {
			a = (Agenda) readObject(archivo.getPath());
		} catch (Exception e) {
			return null;
		}
		return a;
	}

	private static Object readObject(String pathSrc) throws FileNotFoundException, IOException {
		Object o = null;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(pathSrc));
		try {
			o = in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return o;
	}

}
