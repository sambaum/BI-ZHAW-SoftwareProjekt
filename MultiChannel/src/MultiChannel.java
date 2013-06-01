import java.util.Map;
import java.util.Scanner;

/**
 * Abgabetermin: Mi-12.06.2013 Dies ist das Haupt-Programm mit der Main-Methode.
 * Von Hier aus werden entweder eine Admin-Session oder eine Mail-Session
 * gestartet
 * 
 * @author Samuel
 * 
 */

public class MultiChannel extends MenuBasedClasses{

	private static MultiChannel multiChannel;
	//private Scanner leser; //Um die Eingabe des Users zu verarbeiten

	/**
	 * Konstruktor.
	 * 
	 */
	public MultiChannel() {
		super();
		//leser = new Scanner(System.in);
	}

	public static void main(String[] args) {
		System.out.println("Willkommen bei Multichannel");
		multiChannel = new MultiChannel();
		multiChannel.loadStoredUsers();
		multiChannel.startMenu();
	}

	/**
	 * Das erste Menu welches dem User gezeigt wird. Von hier aus kann er
	 * zwischen Admin- und Mail-Session entscheien. Die entsprechenden Sessions
	 * werden hier gestartet
	 */
	private void startMenu() {
		System.out.println("W�hlen Sie Ihre Oberfl�che: \n [1] User-Administration \n [2] Mail-Programm");
		System.out.print("> "); // Eingabeaufforderung
		String eingabezeile = getLeser().nextLine(); //Eingabe lesen
		//Eingabe auswerten
		if (eingabezeile.equals("1")) {
			System.out.println("User-Administration wird gestartet...");
			AdminSession adminSession = new AdminSession();
			adminSession.startSession();
		} else if (eingabezeile.equals("2")) {
			System.out.println("Mail-Programm wird gestartet...");
			User sessionUser = userAuswahl();
			MailSession mailSession = new MailSession(sessionUser);
			mailSession.startSession();
		} else {
			System.out.println("Ihre Eingabe ist falsch, geben Sie entweder 1 oder 2 ein");
			startMenu();
		}
	}

	/**
	 * Wenn sich der User f�r eine Mail-Session entscheidet wird hier der User
	 * ausgew�hlt. Dem Benutzer wird eine Liste mit bereits vorhandenen Usern zu
	 * Auswahl gestellt
	 * 
	 * @return User-Objekt
	 */
	private User userAuswahl() {
		if (getStoreuser().getUserMap().size() > 0) {
			System.out.println("Bitten geben Sie Ihren Usernamen an. Folgende User stehen zu Wahl: ");
			menuPrinter(getStoreuser().getUserNumberedList()); // Ein  Liste mit vorhanden User wird dargestellt
			System.out.println("\n> "); // Eingabeaufforderung
			String eingabezeile = getLeser().nextLine(); //Eingabe lesen
			return getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(eingabezeile)); //Ausgew�hltes User-Objekt zur�ckgeben
		} else {
			System.out
					.println("Es sind keine user vorhanden, erfassen Sie zuerst mindestens einen user. Zur�ck zum Hauptmenu..."); //Entsprechende Meldung anzeigen wenn keine bestehen User vorhanden sind 
			startMenu(); //Zur�ck zum Hauptmenu
			return null;
		}
	}

	/**
	 * In Files abgespeicherte User mit Ihren Nachrichten werden hier wieder ins
	 * Leben gerufen
	 */
	private void loadStoredUsers() {
		System.out.println("Gespeicherte User werden geladen...");
		getStoreuser().read(); // Objekt werden erstellt
		printUsers(); // User werden ausgegeben
		System.out.println("Anzahl geladener User: " + getStoreuser().getUserMap().size()); //Anzahl gelesener User wird ausgegeben
	}

	/**
	 * Eine sch�n formatierte Ausgabe wird generiert der geladenen Users wird
	 * erstellt
	 */
	private void printUsers() {
		for (Map.Entry<String, User> entry : getStoreuser().getUserMap().entrySet()) {
			System.out.println("- " + entry.getValue().getUserName() + " (Inbox: "
					+ calculateTotalMessageCount(entry.getKey()) + " Messages)");
		}
	}

	/**
	 * Methode um die Gesamtanzahl von Nachrichten eines Users zu ermitteln.
	 * 
	 * @param user
	 * @return Integer mit Anzahl Messages
	 */
	private int calculateTotalMessageCount(String user) {
		return getStoreuser().getUserMap().get(user).getSmsBox().size()
				+ getStoreuser().getUserMap().get(user).getMailBox().size()
				+ getStoreuser().getUserMap().get(user).getPrintBox().size();
	}
}
