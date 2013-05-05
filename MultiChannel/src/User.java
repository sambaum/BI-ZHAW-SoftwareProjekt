import java.util.ArrayList;

/**
 * Die Klasse hat praktisch nur Datenfelder. Zusätzlich noch ein paar einfache
 * Methoden um die Datenfelder zu bearbeiten.
 * 
 * @author Samuel
 * 
 */

public class User {

	// Instanzvariablen
	private String userName;
	private String group;
	private String tel;
	private String email;
	private String printerName;
	private ArrayList<SMS> smsBox;
	private ArrayList<Mail> mailBox;
	private ArrayList<Print> printBox;

	// Konstruktor
	public User(String userName, String group, String tel, String email,
			String printerName) {
		this.userName = userName;
		this.group = group;
		this.tel = tel;
		this.email = email;
		this.printerName = printerName;
		this.smsBox = new ArrayList<SMS>();
		this.mailBox = new ArrayList<Mail>();
		this.printBox = new ArrayList<Print>();
	}

	public void addSMS(SMS sms) {
		smsBox.add(sms);
	}

	public void addPrint(Print print) {
		printBox.add(print);
	}

	public void addMail(Mail mail) {
		mailBox.add(mail);
	}

	// getter & setter section
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public ArrayList<SMS> getSmsBox() {
		return smsBox;
	}

	public void setSmsBox(ArrayList<SMS> smsBox) {
		this.smsBox = smsBox;
	}

	public ArrayList<Mail> getMailBox() {
		return mailBox;
	}

	public void setMailBox(ArrayList<Mail> mailBox) {
		this.mailBox = mailBox;
	}

	public ArrayList<Print> getPrintBox() {
		return printBox;
	}

	public void setPrintBox(ArrayList<Print> printBox) {
		this.printBox = printBox;
	}
}