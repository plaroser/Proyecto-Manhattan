package main;

import java.io.IOException;

import controller.Controller;

/**
 * 
 * @author Sergio Pla
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		// Empleado e = new Empleado(666666666, "nombre", "Apellidos", "DD/MM/AAAA",
		// "Departamento", "startDate",
		// new ArrayList<String>(), GruposSanguineos.A_NEGATIVO.toString());
		// Agenda a = new Agenda(e, new ArrayList<Contacto>(),
		// TipoDeArchivos.json.toString());
		//
		// XStream xstream = new XStream((new JsonHierarchicalStreamDriver()));
		// xstream.setMode(XStream.NO_REFERENCES);
		// xstream.alias(Agenda.ALIAS, Agenda.class);
		// String json = xstream.toXML(a);
		// Writer writer = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream("c:\\agendas\\666666666.json")));
		// writer.write(json);
		// writer.close();
		Controller controller = new Controller();
		try {
			controller.startProgram();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
