package xmlobs;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bri.Service;

public class Main implements Service {

	private class Balise {
		private String name;
		private Map<String, Balise> inners;
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

		public Map<String, Balise> getInners() {
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

	public Main(Socket socket) {
		client = socket;
	}

	private Balise analyseXML(String file, Balise balise) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("##Tapez le path du fichier XML à analyser (FTP compris) : ");

			out.println("");

			String fileName = in.readLine();

			URL u = new URL(fileName);
			Scanner sc = new Scanner(u.openStream());
			String file = "";

			while (sc.hasNextLine()) {
				file += sc.nextLine();
			}

			Balise b = analyseXML(file, new Balise("root", null));

			client.close();
		} catch (IOException e) {
		}
	}

	public static void init(String path) {

	}

	public static String toStringue() {
		return "Analyse XML";
	}
}
