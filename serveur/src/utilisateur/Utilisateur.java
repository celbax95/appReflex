package utilisateur;

public class Utilisateur {
	private String login;
	private String password;
	private String ftp;
	
	public Utilisateur(String login, String password, String ftp) {
		super();
		this.login = login;
		this.password = password;
		this.ftp = ftp;
	}

	public String getFtp() {
		return ftp;
	}

	public void setFtp(String ftp) {
		this.ftp = ftp;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
}
