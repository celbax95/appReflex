package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import utilisateur.BDDUtilisateurs;
import utilisateur.Utilisateur;

class ServiceProg implements Runnable {

	private Socket client;

	private Utilisateur u;

	ServiceProg(Socket socket) {
		client = socket;
		u = null;
	}

	private void addService() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println("##Entrez le nom du package a ajouter : ");
		out.println("");
		String pkgeName = "";
		pkgeName = in.readLine();
		pkgeName = "xmlobs";
		if(pkgeName != "") {
			if (ServiceRegistry.addService(u.getFtp(), u.getLogin(), pkgeName))
				out.println("##Service ajoute####");
			else
				out.println("##Erreur d'ajout du service####");
		}

	}

	private void changeFTP() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	private void majService() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);

		out.println("##" + ServiceRegistry.toStringue(u.getLogin()) + "##Quel service voulez vous mettre a jour ? : ");
		out.println("");
		int choix = 0;
		boolean b = false;
		while (!b) {
			try {
				choix = Integer.parseInt(in.readLine());
				b = true;
			} catch (Exception e) {
				out.println("");
			}
		}
		out.println("Mise a jour du service...####");
		if (ServiceRegistry.majService(choix, u.getFtp()))
			out.println("Service mis a jour !####");
		else
			out.println("Echec de la mise a jour");
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			do {
				String login = "", password = "";

				out.println("##Login : ");
				out.println("");
				login = in.readLine();
				login = "test";
				out.println("Password : ");
				out.println("");
				password = in.readLine();
				password = "test";

				u = BDDUtilisateurs.connect(login, password);

				if (u == null)
					out.println("##Erreur de connexion !##");

			} while (u == null);

			out.println("##Connecte####Bienvenue " + u.getLogin() + "##");
			out.println("Votre serveur FTP est " + u.getFtp() + "####");

			boolean exit = false;
			do {

				out.println("1 - Fournir un nouveau service##" + "2 - Mettre a jour un service##"
						+ "3 - Changer adresse FTP##" + "4 - Demarrer / arreter un service##"
						+ "5 - Desinstaller un service##" + "6 - Deconnexion####");

				out.println("Choix : ");
				out.println("");

				boolean b = false;
				int choix = 0;
				while (!b) {
					try {
						choix = Integer.parseInt(in.readLine());
						b = true;
					} catch (Exception e) {
						out.println("");
					}
				}
				// in.readLine();
				// int choix = 1;

				switch (choix) {
				case 1:
					addService();
					break;
				case 2:
					majService();
					break;
				case 3:
					changeFTP();
					break;
				case 4:
					uninstallService();
					break;
				case 5:
					switchService();
					break;
				default:
					exit = true;
					out.println("EXIT0");
				}

			} while (!exit);

		} catch (IOException e) {
			// Fin du service
		}

		try {
			client.close();
		} catch (IOException e2) {
		}
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();
	}

	private void switchService() throws IOException {
		// TODO Auto-generated method stub

	}

	private void uninstallService() throws IOException {
		// TODO Auto-generated method stub

	}

}
