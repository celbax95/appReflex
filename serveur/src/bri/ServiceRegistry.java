package bri;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
	static {
		servicesClasses = null;
	}
	private static List<Class<?>> servicesClasses = new Vector<>();

	// cette classe est un registre de services
	// partagée en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	// ajoute une classe de service après contrôle de la norme BLTi
	public static void addService(String ftpURL, String cn) {
		try {
			URLClassLoader urlcl = new URLClassLoader(new URL[] { new URL(ftpURL) });

			Class<?> c = urlcl.loadClass(cn);

			if (verif(c)) {
				servicesClasses.add(c);
				System.out.println("Service ajouté !");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// renvoie la classe de service (numService -1)
	public static Class getServiceClass(int numService) {
		return servicesClasses.get(numService - 1);
	}

	// liste les activités présentes
	public static String toStringue() {
		String result = "Activités présentes :\n";
		for (int i = 0; i < servicesClasses.size(); i++) {
			try {
				result += (i + 1) + " - " + servicesClasses.get(i).getMethod("toStringue").invoke(null, (Object[]) null)
						+ "##";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static boolean verif(Class<?> c) {

		boolean tmp = Service.class.isAssignableFrom(c);

		if (!tmp) {
			System.out.println("La classe n'a pas l'interface Service");
			return tmp;
		}

		int mod = c.getModifiers();

		tmp = !Modifier.isAbstract(mod);

		if (!tmp) {
			System.out.println("La classe est abstract");
			return tmp;
		}

		try {
			c.getConstructor(Socket.class);
		} catch (Exception e) {
			System.out.println("Pas de constructeur avec un parametre socket");
		}

		Field[] flds = c.getDeclaredFields();
		tmp = false;
		for (Field f : flds) {
			if ((f.getType() == Socket.class) && (Modifier.isFinal(mod = f.getModifiers())) && Modifier.isFinal(mod))
				tmp = true;
		}
		if (!tmp) {
			System.out.println("Pas d'attribut private final socket");
			return tmp;
		}

		try {
			c.getMethod("toStringue");
		} catch (Exception e) {
			System.out.println("Pas de methode toStringue");
			return false;
		}

		return true;
	}

}
