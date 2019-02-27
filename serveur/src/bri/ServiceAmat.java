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
			out.println(ServiceRegistry.toStringue() + "\nTapez le numéro de service désiré :");
			int choix = Integer.parseInt(in.readLine());

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
