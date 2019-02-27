package utilisateur;

import java.util.List;
import java.util.Vector;

public class BDDUtilisateurs {
	private static List<Utilisateur> utilisateurs = new Vector<>();
	
	static {
		utilisateurs.add(new Utilisateur("test2","test2","qskjnbqrjkg"));
		utilisateurs.add(new Utilisateur("test","test","ftp://localhost:2121/"));
	}
	
	public Utilisateur connect(String login, String password) {
		for (Utilisateur u : utilisateurs) {
			if (u.getLogin().equals(login) && u.getPassword().equals(password))
				return u;
		}
		return null;
	}
	
	
	
	
}
