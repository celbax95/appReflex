package bri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServiceProg implements Runnable {

	private Socket client;

	ServiceProg(Socket socket) {
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
			String ftp = "";
			out.println("adresse ftp : ");
			ftp = in.readLine();
			out.println("1 - Fournir un nouveau service \n"
					+ "2 - Mettre a jour un service \n"
					+ "3 - Changer adresse FTP \n"
					+ "4 - Demarrer / arreter un service \n"
					+ "5 - Desinstaller un service \n");
			int choix = Integer.parseInt(in.readLine());
			switch (choix){
				case 1 : 
					out.println("Entrez le nom de la classe a ajouter : ");
					out.println("");
					String className = "";
					className = in.readLine();
					if(className != "") {
						ServiceRegistry.addService(ftp, className);
					}
					break;
					
					
			}
			Class<?> c = null;
			Object o = null;

			// instancier le service numéro "choix" en lui passant la socket "client"
			// invoquer run() pour cette instance ou la lancer dans un thread à part
			try {

				c = (Class) (o = ServiceRegistry.getServiceClass(choix));
				o = c.getConstructor(Socket.class).newInstance(client);
				c.getMethod("run").invoke(o, (Object[]) null);

			} catch (Exception e) {
				e.printStackTrace();
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
