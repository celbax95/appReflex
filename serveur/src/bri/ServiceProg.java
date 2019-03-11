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

	ServiceProg(Socket socket) {
		client = socket;
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

			Utilisateur u = null;
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

			out.println("1 - Fournir un nouveau service ##"
					+ "2 - Mettre a jour un service ##"
					+ "3 - Changer adresse FTP ##"
					+ "4 - Demarrer / arreter un service ##"
					+ "5 - Desinstaller un service ####");

			out.println("Choix : ");
			out.println("");

			// int choix = Integer.parseInt(in.readLine());
			in.readLine();
			int choix = 1;
			switch (choix){
			case 1 :
				out.println("##Entrez le nom du package a ajouter : ");
				out.println("");
				String className = "";
				className = in.readLine();
				className = "inv";
				if(className != "") {
					ServiceRegistry.addService(u.getFtp(), className);
					out.println("Service ajoute");
					out.println("");
				}
				break;


			}

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

}
