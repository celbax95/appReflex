package examples;

import java.io.*;
import java.net.*;

import bri.Service;

public class SerciceXML implements Service {

	private final Socket client;

	public SerciceXML(Socket socket) {
		client = socket;
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	@Override
	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
		PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

		out.println("##Tapez le nom du fichier XML à analyser : ");

		out.println("");

		String fileName = in.readLine();

		client.close();
		}
		catch (IOException e) {
			//Fin du service d'inversion
		}
	}

	public static String toStringue() {
		return "Inversion de texte";
	}
}
