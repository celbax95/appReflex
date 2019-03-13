package bri;

public class UploadedService {

	private String auteur;
	private Class<?> service;
	private boolean on;

	public UploadedService(String auteur, Class<?> service) {
		super();
		this.auteur = auteur;
		this.service = service;
		on = true;
	}

	public String getAuteur() {
		return auteur;
	}

	public Class<?> getService() {
		return service;
	}

	public boolean isOn() {
		return on;
	}

	public void setService(Class<?> service) {
		this.service = service;
	}

	public void switchOn() {
		if (on)
			on = false;
		else
			on = true;
	}
}
