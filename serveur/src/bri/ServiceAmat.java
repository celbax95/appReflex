package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServiceAmat implements Runnable {

	private Socket client;

	ServiceAmat(Socket socket) {
		client = socket;
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			boolean exit = false;
			while (!exit) {

				out.println("##" + ServiceRegistry.toStringue()
				+ "##Tapez le num�ro de service d�sir� (0 : Deconnexion): ");
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

				if (choix == 0) {
					out.println("EXIT0");
					exit = true;
					continue;
				}

				Class<?> c = null;
				Object o = null;

				// instancier le service num�ro "choix" en lui passant la socket "client"
				// invoquer run() pour cette instance ou la lancer dans un thread � part
				try {
					c = (Class) (o = ServiceRegistry.getServiceClass(choix));
					o = c.getConstructor(Socket.class).newInstance(client);
					c.getMethod("run").invoke(o, (Object[]) null);

					out.println("##-- Fin du Service --##");

				} catch (Exception e) {
					out.println("Choix invalide####");
				}

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
