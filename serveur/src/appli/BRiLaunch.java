package appli;

import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurProg;
import bri.ServiceRegistry;

public class BRiLaunch {
	private final static int PORT_SERVICE = 3000;

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);

		// URLClassLoader sur ftp
		URLClassLoader urlcl = null;

		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activit� BRi");
		System.out.println("Pour ajouter une activit�, celle-ci doit �tre pr�sente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'int�grer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activit�");

		new Thread(new ServeurProg(PORT_SERVICE)).start();
		String tmp = "ftp://localhost:2121/class/";
		System.out.println("Le nom du ftp : " + tmp);
		String ftpName = tmp;// clavier.nextLine();

		do {
			try {
				tmp = "examples.ServiceInversion";
				System.out.println("Un nom de service : " + tmp);
				String className = tmp;// clavier.next();
				ServiceRegistry.addService(ftpName, className);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (false);
	}
}
