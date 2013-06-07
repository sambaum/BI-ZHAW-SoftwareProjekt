import java.util.HashMap;
import java.util.Map;

/**
 * Abgabetermin: Mi-12.06.2013 Dies ist das Haupt-Programm mit der Main-Methode. Von Hier aus werden entweder eine
 * Admin-Session oder eine Mail-Session gestartet
 * 
 * @author Samuel
 * 
 */
public class MultiChannel extends MenuBasedClasses {

	private static MultiChannel multiChannel;

	/**
	 * Konstruktor.
	 */
	public MultiChannel() {
		super();
	}

	/**
	 * Main-Methode Es wird eine neue Instanz des Programmst erstellt, Die vorhanden User aus den Files geladen und das
	 * Menu angezeigt.
	 */
	public static void main(String[] args) {
		System.out.println("\nWillkommen bei Multichannel");
		multiChannel = new MultiChannel();
		multiChannel.loadStoredUsers();
		multiChannel.startMenu();
	}

	/**
	 * Das erste Menu welches dem User gezeigt wird. Von hier aus kann er zwischen Admin- und Mail-Session entscheien.
	 * Die entsprechenden Sessions werden hier gestartet
	 */
	private void startMenu() {
		HashMap<String, String> mainMenu = new HashMap<String, String>();
		mainMenu.put("1", "User-Administration");
		mainMenu.put("2", "Mail-Programm");
		mainMenu.put("3", "Domo-User erstellen");
		String antwort = askAndGetAnswerWithList(mainMenu, "Wählen Sie Ihre Oberfläche:");
		// Eingabe auswerten
		if (antwort.equals("1")) {
			System.out.println("\nUser-Administration wird gestartet...");
			AdminSession adminSession = new AdminSession();
			adminSession.startSession();
			startMenu();
		} else if (antwort.equals("2")) {
			System.out.println("\nMail-Programm wird gestartet...");
			User sessionUser = userAuswahl();
			MailSession mailSession = new MailSession(sessionUser);
			mailSession.startSession();
			startMenu();
		} else if (antwort.equals("3")) {
			System.out.println("\nDemo-User werden erstellt");
			Demo demoUser = new Demo();
			if (demoUser.createDemoUsers()) {
				System.out.println("User wurden erfolgreich erstellt. Das Programm wird beendet");
				System.exit(0);
			} else {
				System.out.println("Beim erstellen der User ist ein Fehler aufgetreten. Zurück zum Hauptmenu.");
				startMenu();
			}
		} 
	}

	/**
	 * Wenn sich der User für eine Mail-Session entscheidet wird hier der User ausgewählt. Dem Benutzer wird eine Liste
	 * mit bereits vorhandenen Usern zu Auswahl gestellt
	 * 
	 * @return User-Objekt
	 */
	private User userAuswahl() {
		if (getStoreuser().getUserMap().size() > 0) {
			String antwort = askAndGetAnswerWithList(getStoreuser().getUserNumberedList(), "Bitten geben Sie Ihren Usernamen an. Folgende User stehen zu Wahl: ");
			menuPrinter(getStoreuser().getUserNumberedList()); // Ein Liste mit vorhanden User wird dargestellt
			return getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(antwort)); // Ausgewähltes
		} else {
			// Entsprechende Meldung anzeigen wenn keine bestehen User vorhanden sind
			System.out.println("Es sind keine user vorhanden, erfassen Sie zuerst mindestens einen user. Zurück zum Hauptmenu...");
			startMenu(); // Zurück zum Hauptmenu
			return null;
		}
	}

	/**
	 * In Files abgespeicherte User mit Ihren Nachrichten werden hier wieder ins Leben gerufen
	 */
	private void loadStoredUsers() {
		System.out.println("Gespeicherte User werden geladen...");
		getStoreuser().read(); // Objekt werden erstellt
		printUsers(); // User werden ausgegeben
		// Anzahl gelesener User wird ausgegeben
		System.out.println("Anzahl geladener User: " + getStoreuser().getUserMap().size());
	}

	/**
	 * Eine schön formatierte Ausgabe wird generiert der geladenen Users wird erstellt
	 */
	private void printUsers() {
		for (Map.Entry<String, User> entry : getStoreuser().getUserMap().entrySet()) {
			System.out.println("- " + entry.getValue().getUserName() + " (Inbox: " + calculateTotalMessageCount(entry.getKey()) + " Messages)");
		}
	}

	/**
	 * Methode um die Gesamtanzahl von Nachrichten eines Users zu ermitteln.
	 * 
	 * @param user
	 * @return Integer mit Anzahl Messages
	 */
	private int calculateTotalMessageCount(String user) {
		return getStoreuser().getUserMap().get(user).getSmsBox().size() + getStoreuser().getUserMap().get(user).getMailBox().size()
				+ getStoreuser().getUserMap().get(user).getPrintBox().size();
	}
}
