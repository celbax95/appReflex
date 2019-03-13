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

	private static String msgClassName = "Message";
	private static Class<?> msg;
	private static String userClassName = "User";
	private static Class<?> user;

	private final Socket client;

	public Main(Socket socket) {
		client = socket;
	}

	private String connexion(String login, String pass) {
		// TODO RECHERCHE DANS users ET RENVOIE login SI TROUVE, null SINON
		boolean userFound = false;
		for (Object u : users) {
			if (((User) u).getLogin().equals(login) && ((User) u).getPass().equals(pass)) {
				userFound = true;
			}
		}
		if (userFound)
			return login;
		return "";
	}
	
	//Surchage utilisé pour savoir si un destinataire
	//est présent dans la base
	private String connexion(String login) {
		// TODO RECHERCHE DANS users ET RENVOIE login SI TROUVE, null SINON
		boolean userFound = false;
		for (Object u : users) {
			if (((User) u).getLogin().equals(login)) {
				userFound = true;
			}
		}
		if (userFound)
			return login;
		return "";
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	private List<Object> getMsgFor(String login) {
		// TODO PARCOURIR messages ET RENVOYER SEULEMENT CEUX POUR L'UTILISATEUR
		List<Object> messageTmp = new ArrayList<>();
			if (messages.size() > 0) {
				for (Object m : messages) {
					if(((Message) m).getDest().equals(login)) {
						messageTmp.add((Message) m);
					}
				}
			}
		return messageTmp;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			String login = "";
			String pass = "";
			int choix = 0;
			boolean exit = false;
			do {
				out.println("##");
				out.println("1 - S'inscrire");
				out.println("##");
				out.println("2 - Se connecter");
				out.println("##");
				out.println("3 - Quitter");
				out.println("####");
				out.println("Choix : ");
				out.println("");
				choix = Integer.parseInt(in.readLine());
				switch (choix) {
					case 1 : 
						out.println("Veuillez vous enregistrer : ");
						out.println("##");
						out.println("Login : ");
						out.println("");
						login = in.readLine();
						out.println("Password : ");
						out.println("");
						pass = in.readLine();
						synchronized(users) {
							users.add(new User(login, pass));
						}
						break;
					case 2 : 
						out.println("Veuillez entrer vos identifiants : ");
						out.println("##");
						out.println("Login : ");
						out.println("");
						login = in.readLine();
						out.println("Password : ");
						out.println("");
						pass = in.readLine();
						// Connexion a un membre de users
						login = connexion(login, pass);
						if (login != null)
							exit = true;
						break;
					case 3 : 
						try {
							client.close();
						} catch (IOException e2) {
						}
						break;
					default : 
						out.println("Saisie invalide ! ");
						out.println("##");
						break;
				}
			}while(!exit);
			
			exit = false;
			String destinataire, objet, message;
			List<Object> messagerie = getMsgFor(login);
			do {
				out.println("##");
				out.println("1 - Consulter message");
				out.println("##");
				out.println("2 - Envoyer message");
				out.println("##");
				out.println("3 - Deconnexion");
				out.println("####");
				out.println("Choix : ");
				out.println("");
				choix = Integer.parseInt(in.readLine());
				out.println("##");
				switch (choix){
					case 1 :
						out.println("<=== Vos Messages ===>");
						out.println("##");
						 messagerie = getMsgFor(login);
						if (messagerie.size() > 0) {
							for (Object msg : messagerie) {
								out.println(msg.toString());
								out.println("##");
							}
						}else {
							out.println("Vous n'avez aucun message !");
							out.println("##");
						}
						break;
					case 2 :
						out.println("Destinataire : ");
						out.println("");
						if (!(destinataire = connexion(in.readLine())).equals("")) {
							out.println("##");
							out.println("Objet : ");
							out.println("");
							objet = in.readLine();
							out.println("##");
							out.println("Message : ");
							out.println("");
							message = in.readLine();
							out.println("##");
							messages.add(new Message(login, objet, destinataire, message));
						}else {
							out.println("Destinataire inexistant dans la base !");
							out.println("##");
						}
						break;
					case 3 :
						try {
							client.close();
						} catch (IOException e2) {
						}
						break;
					default :
						out.println("Saisie invalide ! ");
						out.println("##");
						break;
				}
			}while(!exit);
			
			try {
				client.close();
			} catch (IOException e2) {
			}


		} catch (IOException e) {
		}
	}

	public static void init(String path) throws Exception {
		URLClassLoader cl = new URLClassLoader(new URL[] { new URL(path) });

		msg = cl.loadClass(Main.class.getPackage().getName() + "." + msgClassName);
		user = cl.loadClass(Main.class.getPackage().getName() + "." + userClassName);
	}

	public static String toStringue() {
		return "Messagerie";
	}
}
