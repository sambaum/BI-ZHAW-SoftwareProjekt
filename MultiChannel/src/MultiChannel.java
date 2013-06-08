import java.util.Map;
import java.util.TreeMap;

/**
 * Dies ist das Haupt-Programm mit der Main-Methode. Von Hier aus wird entweder eine Admin-Session oder eine
 * Mail-Session gestartet.
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
	 * Main-Methode. Es wird eine neue Instanz des Programmst erstellt, Die vorhanden User aus den Files geladen und das
	 * Menu angezeigt.
	 */
	public static void main(String[] args) {
		System.out.println("\nWillkommen bei Multichannel");
		multiChannel = new MultiChannel();
		multiChannel.loadStoredUsers();
		multiChannel.startMenu();
	}

	/**
	 * Das erste Menu welches dem User gezeigt wird. Von hier aus kann er zwischen Admin- und Mail-Session entscheiden.
	 * Die entsprechenden Sessions werden hier gestartet
	 */
	private void startMenu() {
		TreeMap<String, String> mainMenu = new TreeMap<String, String>();
		mainMenu.put("1", "User-Administration");
		mainMenu.put("2", "Mail-Programm");
		mainMenu.put("3", "Domo-User erstellen");
		String antwort = askAndGetAnswerWithList(mainMenu, "W�hlen Sie Ihre Oberfl�che:");
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
				System.out.println("Beim erstellen der User ist ein Fehler aufgetreten. Zur�ck zum Hauptmenu.");
				startMenu();
			}
		}
	}

	/**
	 * Wenn sich der User f�r eine Mail-Session entscheidet wird hier der User ausgew�hlt. Dem Benutzer wird eine Liste
	 * mit bereits vorhandenen Usern zu Auswahl gestellt.
	 * 
	 * @return User-Objekt
	 */
	private User userAuswahl() {
		if (getStoreuser().getUserMap().size() > 0) {
			String antwort = askAndGetAnswerWithList(getStoreuser().getUserNumberedList(), "Bitten geben Sie Ihren Usernamen an. Folgende User stehen zu Wahl: ");
			menuPrinter(getStoreuser().getUserNumberedList()); // Ein Liste mit vorhanden User wird dargestellt
			return getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(antwort)); // Ausgew�hltes
		} else {
			// Entsprechende Meldung anzeigen wenn keine bestehen User vorhanden sind
			System.out.println("Es sind keine user vorhanden, erfassen Sie zuerst mindestens einen user. Zur�ck zum Hauptmenu...");
			startMenu(); // Zur�ck zum Hauptmenu
			return null;
		}
	}

	/**
	 * Die in Files abgespeicherten User mit ihren Nachrichten werden hier erstellt.
	 */
	private void loadStoredUsers() {
		System.out.println("Gespeicherte User werden geladen...");
		getStoreuser().read(); // Objekt werden erstellt
		printUsers(); // User werden ausgegeben
		// Anzahl gelesener User wird ausgegeben
		System.out.println("Anzahl geladener User: " + getStoreuser().getUserMap().size());
	}

	/**
	 * Beim starten des Programmst werden vorhandene User eingelesen. Diese werden hier dargestellt und die Anzahl an
	 * Nachrichten in der Inbox werden dargestellt.
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
