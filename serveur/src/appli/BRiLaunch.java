package appli;

import java.net.URLClassLoader;
import java.util.Scanner;

import bri.ServeurAmat;
import bri.ServeurProg;

public class BRiLaunch {
	private final static int PORT_PROG = 2000;
	private final static int PORT_AMAT = 3000;

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner clavier = new Scanner(System.in);

		// URLClassLoader sur ftp
		URLClassLoader urlcl = null;

		System.out.println("Bienvenue dans votre gestionnaire dynamique d'activité BRi");
		System.out.println("Pour ajouter une activité, celle-ci doit être présente sur votre serveur ftp");
		System.out.println("A tout instant, en tapant le nom de la classe, vous pouvez l'intégrer");
		System.out.println("Les clients se connectent au serveur 3000 pour lancer une activité");

		new Thread(new ServeurProg(PORT_PROG)).start();
		new Thread(new ServeurAmat(PORT_AMAT)).start();
		String tmp = "ftp://localhost:2121/class/";
	}
}
