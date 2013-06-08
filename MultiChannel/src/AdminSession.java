import java.io.IOException;
import java.util.TreeMap;

/**
 * Die Klasse kümmert sich um die User-Verwaltung. Es können User erasst un gelöscht werden. Der User wird mit einem
 * Menu durch die Aktivitäten geführt.
 */
public class AdminSession extends MenuBasedClasses {

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
		initMenu.put("2", "User löschen");
		initMenu.put("3", "Zurück zum Hauptmenu");
		String antwort = "";
		antwort = askAndGetAnswerWithList(initMenu, "Was möchten Sie tun?");
		if (antwort.equals("1")) {
			createUser();
		} else if (antwort.equals("2")) {
			deleteuser();
		} else if (antwort.equals("3")) {
			return;
		}

	}

	/**
	 * Ein Auswahl vorhandener User wird angezeigt und kann gelöscht werden
	 */
	private void deleteuser() {
		// TODO User löschen
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
		try {
			getStoreuser().write(new User(username, group, tel, email, printer));
		} catch (IOException e) {
			System.out.println("Bei speichern des Users ist ein Fehler aufgetreten");
			e.printStackTrace();
		}
		chooseWhatToDo();
	}
}
