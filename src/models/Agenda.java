package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import enums.TipoDeArchivos;

public class Agenda implements Serializable {

	public static final String ALIAS = "Agenda";
	public static final String RUTA_AGENDAS = "c:\\agendas\\";
	public static final Comparator<Agenda> porDepartamento = (Agenda a1, Agenda a2) -> a1.getEmpleado().getDepartament()
			.compareTo(a2.getEmpleado().getDepartament());
	public static final Comparator<Agenda> porGrupoSanguineo = (Agenda a1, Agenda a2) -> a1.getEmpleado()
			.getStringGrupoSanguineo().compareTo(a2.getEmpleado().getStringGrupoSanguineo());

	private static final long serialVersionUID = 943295793237719639L;

	private Empleado empleado;
	private List<Contacto> listaContactos;
	private String tipoDeArchivo;

	// private static XStream xstreamXML = new XStream();

	public Agenda(Empleado empleado, List<Contacto> listaContactos, String tipoDeArchivo) {
		super();
		this.empleado = empleado;
		this.listaContactos = listaContactos;
		this.tipoDeArchivo = tipoDeArchivo;
	}

	public TipoDeArchivos getTipoDeArchivo() {
		return TipoDeArchivos.getTipo(tipoDeArchivo);
	}

	public void setTipoDeArchivo(TipoDeArchivos tipoDeArchivo) {
		this.tipoDeArchivo = tipoDeArchivo.toString();
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
		String[] s = archivo.getName().split("\\.(?=[^\\.]+$)");
		String extension = s[s.length - 1];
		switch (TipoDeArchivos.getTipo("." + extension)) {
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
		XStream xstreamXML = new XStream(new DomDriver());
		xstreamXML.alias("Contacto", Contacto.class);
		xstreamXML.alias("Empleado", Empleado.class);
		xstreamXML.alias("Agenda", Agenda.class);
		Agenda agenda = null;
		try {
			String xml = new String(Files.readAllBytes(archivo.toPath()));
			agenda = (Agenda) xstreamXML.fromXML(xml);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return agenda;
	}

	private static Agenda cargarAgendaJSon(File archivo) {

		XStream xstreamJSon = new XStream(new JettisonMappedXmlDriver());
		xstreamJSon.alias(Agenda.ALIAS, Agenda.class);
		xstreamJSon.alias("Contacto", Contacto.class);
		xstreamJSon.alias("Empleado", Empleado.class);

		Agenda agenda = null;
		try {
			String json = new String(Files.readAllBytes(archivo.toPath()));
			agenda = (Agenda) xstreamJSon.fromXML(json);
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

	public static void guardarAgenda(Agenda a) {
		switch (a.getTipoDeArchivo()) {
		case json:
			guardarAgendaJSon(a);
		case xml:
			guardarAgendaXML(a);
		case serializado:
			guardarAgendaJSer(a);
		default:
			break;
		}
	}

	private static boolean guardarAgendaXML(Agenda a) {
		boolean esCorrecto = false;
		if (a != null && a.getTipoDeArchivo() == TipoDeArchivos.xml) {
			XStream xstreamXML = new XStream();
			xstreamXML.alias(Agenda.ALIAS, Agenda.class);
			xstreamXML.alias("Contacto", Contacto.class);
			xstreamXML.alias("Empleado", Empleado.class);
			Writer writerXML = null;
			try {
				writerXML = new FileWriter(
						Agenda.RUTA_AGENDAS + a.getEmpleado().getnTel() + a.getTipoDeArchivo().toString(), false);
				xstreamXML.alias(Agenda.ALIAS, Agenda.class);
				writerXML.write(xstreamXML.toXML(a));
				writerXML.close();
				esCorrecto = true;
			} catch (IOException e) {
				e.printStackTrace();
				esCorrecto = false;
			}
		}
		return esCorrecto;
	}

	private static boolean guardarAgendaJSon(Agenda a) {
		boolean esCorrecto = false;
		if (a != null && a.getTipoDeArchivo() == TipoDeArchivos.json) {
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			Writer writerXML = null;
			xstream.setMode(XStream.NO_REFERENCES);
			xstream.alias(Agenda.ALIAS, Agenda.class);
			xstream.alias("Contacto", Contacto.class);
			xstream.alias("Empleado", Empleado.class);
			try {
				writerXML = new FileWriter(
						Agenda.RUTA_AGENDAS + a.getEmpleado().getnTel() + a.getTipoDeArchivo().toString(), false);
				writerXML.write(xstream.toXML(a));
				writerXML.close();
				esCorrecto = true;

			} catch (Exception e) {
				esCorrecto = false;
			}
		}
		return esCorrecto;
	}

	private static boolean guardarAgendaJSer(Agenda a) {
		boolean esCorrecto = false;
		if (a != null && a.getTipoDeArchivo() == TipoDeArchivos.serializado) {
			try {
				writeObject(Agenda.RUTA_AGENDAS + a.getEmpleado().getnTel() + a.tipoDeArchivo.toString(), a);
				esCorrecto = true;
			} catch (Exception e) {
				e.printStackTrace();
				esCorrecto = false;
			}
		}
		return esCorrecto;
	}

	private static <T extends Serializable> void writeObject(String pathDest, T object)
			throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathDest));
		try {
			out.writeObject(object);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@Override
	public String toString() {
		String salida = "";
		salida += empleado.toString() + "Lista de contactos: \n----------\n";

		for (Contacto contacto : listaContactos) {
			salida += "\t" + contacto.toString();
		}
		salida += "----------\n";
		return salida;
	}

}
