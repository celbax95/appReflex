package bri;

public class UploadedService {

	String auteur;
	Class<?> service;

	public UploadedService(String auteur, Class<?> service) {
		super();
		this.auteur = auteur;
		this.service = service;
	}

	public String getAuteur() {
		return auteur;
	}

	public Class<?> getService() {
		return service;
	}

	public void setService(Class<?> service) {
		this.service = service;
	}
}
