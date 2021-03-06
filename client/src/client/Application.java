package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Ce client se connecte � un serveur dont le protocole est
 * menu-choix-question-r�ponse client-r�ponse service
 * il n'y a qu'un �change (pas de boucle)
 * la r�ponse est saisie au clavier en String
 *
 * Le protocole d'�change est suffisant pour le tp4 avec ServiceInversion
 * ainsi que tout service qui pose une question, traite la donn�e du client et envoie sa r�ponse
 * mais est bien sur susceptible de (nombreuses) am�liorations
 */
class Application {
	private final static int PORT_SERVICE = 3000;
	private final static String HOST = "localhost";

	public static void main(String[] args) {
		Socket s = null;
		try {

			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("1 - Serveur Programmeur \n2 - Serveur Amateur\n\nChoix : ");

			s = new Socket(HOST, (clavier.readLine().equals("1")?2000:3000));

			BufferedReader sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter sout = new PrintWriter(s.getOutputStream(), true);

			System.out.println("\nConnect� au serveur " + s.getInetAddress() + ":" + s.getPort());

			String line;
			boolean exit = false;
			while (!exit) {
				line = sin.readLine();

				if (line.equals("EXIT0")) {
					exit = true;
					continue;
				}

				while (!line.equals("")) {
					System.out.print(line.replaceAll("##", "\n"));
					line = sin.readLine();
				}
				sout.println(clavier.readLine());
			}

		} catch (Exception e) {
			System.err.println("Fin de la connexion");
		}
		try {
			if (s != null)
				s.close();
		} catch (IOException e2) {
			;
		}
		System.out.println("\n-- Fin de la connexion --");
	}
}
