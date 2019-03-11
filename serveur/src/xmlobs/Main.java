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
		try {
			URLClassLoader cl = new URLClassLoader(new URL[] { new URL(path) });

			balise = cl.loadClass(Main.class.getPackage().getName() + "." + baliseClassName);
			System.out.println("LOADED");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String toStringue() {
		return "Analyse XML";
	}
}
