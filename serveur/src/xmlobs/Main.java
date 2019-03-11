package xmlobs;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bri.Service;

public class Main implements Service {

	private static String baliseClassName = "Balise";

	private static Class<?> balise;
	private final Socket client;

	public Main(Socket socket) {
		client = socket;
	}

	private Object analyse(String file) {
		try {
			Object b = balise.getConstructor(String.class, balise).newInstance("FILE", null);

			file = file.trim();



		} catch (Exception e) {
		}
		return null;
	}

	private Balise analyseXML(String file, Balise balise) {
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
			fileName = "ftp://localhost:2121/test.xml";

			URL u = new URL(fileName);
			Scanner sc = new Scanner(u.openStream());
			String file = "";

			while (sc.hasNextLine()) {
				file += sc.nextLine();
			}

			file = file.replaceAll("\t", "");

			Object balises = analyse(file);

			System.out.println(file);

			client.close();
		} catch (IOException e) {
		}
	}

	public static void init(String path) throws Exception {
		URLClassLoader cl = new URLClassLoader(new URL[] { new URL(path) });

		balise = cl.loadClass(Main.class.getPackage().getName() + "." + baliseClassName);
	}

	public static String toStringue() {
		return "Analyse XML";
	}
}
