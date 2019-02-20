package appli;

import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurAmat;
import bri.ServeurProg;

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
		new Thread(new ServeurAmat(PORT_SERVICE)).start();
		String tmp = "ftp://localhost:2121/class/";
	}
}
