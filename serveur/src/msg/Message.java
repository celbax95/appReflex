package msg;

public class Message {

	private String exp;
	private String obj;
	private String dest;
	private String msg;

	public Message(String exp, String obj, String dest, String msg) {
		this.exp = exp;
		this.obj = obj;
		this.dest = dest;
		this.msg = msg;
	}

	public String getDest() {
		return dest;
	}

	public String getExp() {
		return exp;
	}

	public String getMsg() {
		return msg;
	}

	public String getObj() {
		return obj;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		String UImessage = "";
		UImessage += "- Expediteur : " + exp + "##" +
					"- Destinataire : " + dest + "##" +
					"- Objet : " + obj + "##" +
					"- Message : " + msg + "##";
		return UImessage;
	}
}
