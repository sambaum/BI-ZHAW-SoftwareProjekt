import java.io.*;
import java.util.TreeMap;

/**
 * Die Klasse k�mmert sich um die User-Verwaltung. Es k�nnen User erasst un gel�scht werden. Der User wird mit einem
 * Menu durch die Aktivit�ten gef�hrt.
 */
public class AdminSession extends MenuBasedClasses{

	/**
	 * Einstieg in die User-Session.
	 */
	public void startSession() {
		getStoreuser().read();
		System.out.println("\nWillkommen im Admin-Tool");
		chooseWhatToDo();
	}

	/**
	 * Start-Menu
	 */
	public void chooseWhatToDo() {
		TreeMap<String, String> initMenu = new TreeMap<String, String>();
		initMenu.put("1", "User erfassen");
		initMenu.put("2", "User l�schen");
		initMenu.put("3", "Zur�ck zum Hauptmenu");
		String antwort = "";
		antwort = askAndGetAnswerWithList(initMenu, "Was m�chten Sie tun?");
		if (antwort.equals("1")) {
			createUser();
		} else if (antwort.equals("2")) {
			deleteuser();
		} else if (antwort.equals("3")) {
			return;
		}

	}

	/**
	 * Ein Auswahl vorhandener User wird angezeigt und kann gel�scht werden
	 */
	private void deleteuser() {
		// TODO User l�schen
	}

	/**
	 * Hier kann ein neuer User erstellt werden. Details werden durch den User eingegeben und anschliessend wird ein
	 * neues User-Objekt mit den gelieferten Angaben erstellt.
	 */
	private void createUser() {
		System.out.println("\nEin neuer User wird erfasst...");
		String username = askAndGetAnswer("Usernamen:");
		String group = askAndGetAnswer("Gruppe:");
		String tel = askAndGetAnswer("Telefon-Nummer:");
		String email = askAndGetAnswer("E-Mail:");
		String printer = askAndGetAnswer("Drucker-Namen");
		if (
				!new CheckAlphaNum().check(username) || 
				!new CheckAlphaNum().check(group) || 
				!new CheckTel().check(tel) || 
				!new CheckEmail().check(email) || 
				!new CheckAlphaNum().check(printer) 
		){
			System.out.println("Fehlerhafte Eingabe. Der User wurde nicht erstellt");
		}
		else{
			try {
				getStoreuser().write(new User(username, group, tel, email, printer));
				System.out.println("Der User" + username + "wurde erfolgreich erstellt");
			} catch (IOException e) {
				System.out.println("Bei speichern des Users ist ein Fehler aufgetreten");
				e.printStackTrace();
			}
		}
		chooseWhatToDo();
	}
}
