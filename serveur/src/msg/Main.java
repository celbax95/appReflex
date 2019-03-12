package msg;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import bri.Service;

public class Main implements Service {

	private static List<Object> messages;

	private static List<Object> users;

	static {
		messages = new ArrayList<>();
		users = new ArrayList<>();
	}

	private static String msgClassName = "Balise";
	private static Class<?> msg;
	private static String userClassName = "Balise";
	private static Class<?> user;

	private final Socket client;

	public Main(Socket socket) {
		client = socket;
	}

	private String connexion(String login, String pass) {
		// TODO RECHERCHE DANS users ET RENVOIE login SI TROUVE, null SINON
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	private List<Object> getMsgFor(String login) {
		// TODO PARCOURIR messages ET RENVOYER SEULEMENT CEUX POUR L'UTILISATEUR
		return null;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			String login = "";
			String pass = "";

			// Connexion a un membre de users
			login = connexion(login, pass);

			/*
			 * Menu - Consulter msg - Envoyer msg
			 */

			// Consulter msg
			List<Object> msg = getMsgFor(login);

			for (Object m : msg) {
				out.println(m.toString() + "\n");
			}

			// Envoyer msg recup expediteur, destinataire,objet, massage
			// Ajouter a messages

		} catch (IOException e) {
		}
	}

	public static void init(String path) throws Exception {
		URLClassLoader cl = new URLClassLoader(new URL[] { new URL(path) });

		msg = cl.loadClass(Main.class.getPackage().getName() + "." + msgClassName);
		user = cl.loadClass(Main.class.getPackage().getName() + "." + userClassName);
	}

	public static String toStringue() {
		return "Analyse XML";
	}
}
