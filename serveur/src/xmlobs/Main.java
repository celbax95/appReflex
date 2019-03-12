package xmlobs;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import bri.Service;

public class Main implements Service {

	private static String baliseClassName = "Balise";

	private static Class<?> balise;
	private final Socket client;

	public Main(Socket socket) {
		client = socket;
	}

	private Object[] analyse(String file, Object b) {
		try {
			if (b == null)
				file = "<FILE>" + file + "</FILE>";

			int inf = 0, sup = 0, spa = 0, ssup = 0, ind = 0;

			System.out.println("\" " + file + " \"");

			inf = file.indexOf("<");
			spa = file.indexOf(" ");
			sup = file.indexOf(">");
			ssup = file.indexOf("/>");

			ind = file.length();
			spa = spa < 0 ? ind : spa;
			sup = sup < 0 ? ind : sup;
			ssup = ssup < 0 ? ind : ssup;

			if (inf < 0 || (inf > sup) || (inf > ssup))
				System.out.println("ERREUR");

			// Get smaller
			ind = sup < spa ? sup : spa;
			ind = ind < ssup ? ind : ssup;

			String name = file.substring(inf + 1, ind);
			assert (file.matches(".*<\\/ *balise *.*"));

			b = balise.getConstructor(String.class, balise).newInstance(name, b);

			@SuppressWarnings("unchecked")
			Map<String, String> attr = (Map<String, String>) balise.getMethod("getAttributes").invoke(b,
					(Object[]) null);
			@SuppressWarnings("unchecked")
			Map<String, Object> inn = (Map<String, Object>) balise.getMethod("getInners").invoke(b, (Object[]) null);

			file = file.substring(ind);

			boolean bool = (spa < sup && spa < ssup);
			while (bool) {
				file = file.trim();

				inf = 0;
				ind = file.indexOf("=\"");
				String attrName = file.substring(inf, ind);

				file = file.substring(ind + 2);
				inf = 0;
				ind = file.indexOf("\"");
				String value = file.substring(inf, ind);

				attr.put(attrName, value);

				file = file.substring(ind + 1).trim();

				ind = file.length();
				sup = file.indexOf(">");
				ssup = file.indexOf("/>");
				sup = sup < 0 ? ind : sup;
				ssup = ssup < 0 ? ind : ssup;

				if (sup == 0 || ssup == 0)
					bool = false;
			}

			if (sup < ssup) {
				file = file.substring(1).trim();
				while (file.indexOf("</") != 0) {

					inf = file.indexOf("<");

					if (inf == 0) {
						Object[] ret = analyse(file, b);
						file = (String) ret[1];
						inn.put(name, ret[0]);
					} else {
						String content = file.substring(0, inf).trim();
						balise.getMethod("setContent", String.class).invoke(b, content);
						file = file.substring(inf);
					}
				}
				sup = file.indexOf(">");
				if (file.substring(2, sup).trim().equals(name)) {
					file = file.substring(sup + 1);
					return new Object[] { b, file };
				}
			} else if (ssup < sup) {
				file = file.trim().substring(2);
				return new Object[] { b, file };
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void finalize() throws Throwable {
		client.close();
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			out.println("##Tapez le path du fichier XML à analyser (FTP compris) : ");
			out.println("");

			String fileName = in.readLine();
			fileName = "ftp://localhost:2121/test.xml";

			URL u = new URL(fileName);
			Scanner sc = new Scanner(u.openStream());
			String file = "";

			while (sc.hasNextLine())
				file += sc.nextLine();

			Object b = null;
			try {
				b = balise.getConstructor(String.class, balise).newInstance("FILE", null);
			} catch (Exception e) {
			}

			Object balises = analyse(file.replaceAll("\t", "").trim(), null)[0];

			out.println("##");
			out.println(balises.toString().replaceAll("\n", "##"));
			out.println("##");

		} catch (IOException e) {
		}
	}

	public static void init(String path) throws Exception {
		URLClassLoader cl = new URLClassLoader(new URL[] { new URL(path) });

		balise = cl.loadClass(Main.class.getPackage().getName() + "." + baliseClassName);
	}

	public static String toStringue() {
		return "Analyse XML";
	}
}
