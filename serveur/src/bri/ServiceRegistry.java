package bri;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Vector;

public class ServiceRegistry {
	static {
		services = null;
	}
	private static List<UploadedService> services = new Vector<>();

	// cette classe est un registre de services
	// partag�e en concurrence par les clients et les "ajouteurs" de services,
	// un Vector pour cette gestion est pratique

	// ajoute une classe de service apr�s contr�le de la norme BLTi
	public static boolean addService(String ftpURL, String auteur, String packageName) {
		try {
			URLClassLoader urlcl = new URLClassLoader(new URL[] { new URL(ftpURL) });

			Class<?> c = urlcl.loadClass(packageName + ".Main");

			if (verif(c)) {
				Method m = c.getMethod("init", String.class);
				try {
					m.invoke(null, ftpURL);
					services.add(new UploadedService(auteur, c));
					System.out.println("Service ajout� !");
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Mauvais chargement du service !");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// renvoie la classe de service (numService -1)
	public static Class getServiceClass(int numService) {
		return services.get(numService - 1).getService();
	}

	@SuppressWarnings("resource")
	private static Class<?> loadService(String ftpURL, String packageName) throws Exception {
		return (new URLClassLoader(new URL[] { new URL(ftpURL) })).loadClass(packageName + ".Main");
	}

	public static boolean majService(int choix, String ftpURL) {
		choix--;
		try {
			String packageName = services.get(choix - 1).getService().getPackage().getName();

			Class<?> c = loadService(ftpURL, packageName);

			if (verif(c)) {
				Method m = c.getMethod("init", String.class);
				try {
					m.invoke(null, ftpURL);
					services.get(choix).setService(c);
					System.out.println("Service mis a jour !");
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("Mauvais chargement du service !");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int switchService(int choix) {
		int i = 0;
		try {
			i = services.get(choix - 1).switchOnOff();
		} catch (Exception e) {
			return 0;
		}
		return i;
	}
	// liste les activit�s pr�sentes
	public static String toStringue() {
		return toStringue(null, true);
	}

	// liste les activit�s pr�sentes pour un auteur
	public static String toStringue(String auteur) {
		return toStringue(auteur, false);
	}

	// liste les activit�s pr�sentes pour un auteur
	public static String toStringue(String auteur, boolean onlyOn) {
		String result = "Activit�s pr�sentes :####";
		for (int i = 0; i < services.size(); i++) {
			try {
				if ((auteur == null || services.get(i).getAuteur() == auteur) && (!onlyOn || services.get(i).isOn())) {
					result += "    " + (i + 1) + " - "
							+ services.get(i).getService().getMethod("toStringue").invoke(null, (Object[]) null);
					if (!onlyOn)
						result += " (" + (services.get(i).isOn() ? "ON" : "OFF") + ")";
					result += "##";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean uninstall(int choix) {
		try {
			services.remove(choix-1);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static boolean verif(Class<?> c) {

		boolean tmp = Service.class.isAssignableFrom(c);

		if (!tmp) {
			System.out.println("\nLa classe n'a pas l'interface Service");
			return tmp;
		}

		int mod = c.getModifiers();

		tmp = !Modifier.isAbstract(mod);

		if (!tmp) {
			System.out.println("\nLa classe est abstract");
			return tmp;
		}

		try {
			c.getConstructor(Socket.class);
		} catch (Exception e) {
			System.out.println("\nPas de constructeur avec un parametre socket");
		}

		Field[] flds = c.getDeclaredFields();
		tmp = false;
		for (Field f : flds) {
			if ((f.getType() == Socket.class) && (Modifier.isFinal(mod = f.getModifiers())) && Modifier.isFinal(mod))
				tmp = true;
		}
		if (!tmp) {
			System.out.println("\nPas d'attribut private final socket");
			return tmp;
		}

		try {
			c.getMethod("toStringue");
		} catch (Exception e) {
			System.out.println("\nPas de methode toStringue");
			return false;
		}
		try {
			boolean hasExceptionThrown = false;
			for (Class<?> e : c.getMethod("init", String.class).getExceptionTypes()) {
				if (e == Exception.class) {
					hasExceptionThrown = true;
					break;
				}
			}
			if (!hasExceptionThrown) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println("\nPas de methode init avec parametre String pouvant lever une Exception");
			return false;
		}
		return true;
	}
}
