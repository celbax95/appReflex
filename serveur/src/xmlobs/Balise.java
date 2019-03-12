package xmlobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Balise {
	private String name;
	private List<Object> inners;
	private Map<String, String> attributes;

	private String content;

	private Object upper;

	public Balise(String name, Balise upper) {
		this.name = name;
		this.upper = upper;
		this.content = "";
		this.inners = new ArrayList<>();
		this.attributes = new HashMap<>();
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public String getContent() {
		return content;
	}


	public List<Object> getInners() {
		return inners;
	}

	public String getName() {
		return name;
	}

	public Object getUpper() {
		return upper;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setInners(List<Object> inners) {
		this.inners = inners;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUpper(Object upper) {
		this.upper = upper;
	}

	@Override
	public String toString() {
		String s = "";
		for (Object b : inners) {
			s += ((Balise) b).toString(1);
		}
		return s.substring(0, s.length() - 1);
	}

	private String toString(int in) {
		String s = name;

		if (!attributes.isEmpty()) {
			s += "(";

			for (Map.Entry<String, String> e : attributes.entrySet()) {
				s += e.getKey() + "=\"" + e.getValue() + "\", ";
			}
			s = s.substring(0, s.length() - 2);
			s += ")";
		}
		if (!content.equals("") || !inners.isEmpty()) {
			s += " {";
		}
		if (!content.equals("")) {
			for (int i = 0; i < in; i++)
				s += "    ";
			s += "\n\"" + content + "\"";
		}
		if (!inners.isEmpty()) {
			s += "\n";
			for (Object b : inners) {
				for (int i = 0; i < in; i++)
					s += "    ";
				s += ((Balise) b).toString(in + 1);
			}
			for (int i = 1; i < in; i++)
				s += "  ";
			s += "}";
		}
		return s + "\n";
	}
}