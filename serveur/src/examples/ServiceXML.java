package examples;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import bri.Service;

public class SerciceXML implements Service {

	private class Balise {
		private String name;
		private Map<String, String> inners;
		private Map<String, String> attributes;
		private Balise upper;

		public Balise(String name, Balise upper) {
			this.name = name;
			this.upper = upper;
			this.inners = new HashMap<>();
			this.attributes = new HashMap<>();
		}

		public Map<String, String> getAttributes() {
			return attributes;
		}

		public Map<String,String> getInners() {
			return inners;
		}

		public String getName() {
			return name;
		}

		public Balise getUpper() {
			return upper;
		}
	}

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
		}
	}

	public static String toStringue() {
		return "Analyse XML";
	}
}
