package xmlobs;

import java.util.HashMap;
import java.util.Map;

public class Balise {
	private String name;
	private Map<String, Balise> inners;
	private Map<String, String> attributes;
	private Balise upper;

	public Balise(String name, Balise upper) {
		this.name = name;
		this.upper = upper;
		this.inners = new HashMap<>();
		this.attributes = new HashMap<>();
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public Map<String, Balise> getInners() {
		return inners;
	}

	public String getName() {
		return name;
	}

	public Balise getUpper() {
		return upper;
	}
}