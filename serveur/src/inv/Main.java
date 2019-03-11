package inv;

import java.io.*;
import java.net.*;

import bri.Service;

// rien à ajouter ici
public class Main implements Service {

	private final Socket client;

	public Main(Socket socket) {
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
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);

			out.println("##Tapez un texte à inverser : ");

			out.println("");

			String line = in.readLine();

			String invLine = new String (new StringBuffer(line).reverse());

			out.println("##" + invLine + "##");
		} catch (IOException e) {
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void init(String url) throws Exception {

	}

	public static String toStringue() {
		return "Inversion de texte";
	}
}
