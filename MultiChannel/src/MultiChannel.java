import java.util.Map;
import java.util.Scanner;

/**
 * Abgabetermin: Mi-12.06.2013 Dies ist das Haupt-Programm mit der Main-Methode
 * 
 * @author Samuel
 * 
 */

public class MultiChannel {

	private static MultiChannel multiChannel;
	private StoreUser storeuser;
	private Scanner leser;
	private MailSession mailSession;

	public MultiChannel() {
		storeuser = new StoreUser();
		leser = new Scanner(System.in);
	}

	public static void main(String[] args) {
		System.out.println("Willkommen bei Multichannel");
		multiChannel = new MultiChannel();
		multiChannel.loadStoredUsers();
		multiChannel.startMenu();
	}

	private void startMenu() {
		System.out.println("Wählen Sie Ihre Oberfläche: \n [1] User-Administration \n [2] Mail-Programm");
		System.out.print("> "); // Eingabeaufforderung
		String eingabezeile = leser.nextLine();
		if (eingabezeile.equals("1")) {
			System.out.println("User-Administration wird gestartet...");
		} else if (eingabezeile.equals("2")) {
			System.out.println("Mail-Programm wird gestartet...");
			User sessionUser = userAuswahl();
			System.out.println("Sie sind angemeldet als User: " + sessionUser.getUserName());
		} else {
			System.out.println("Ihre Eingabe ist falsch, geben Sie entweder 1 oder 2 ein");
			startMenu();
		}
	}

	private User userAuswahl() {
		if (storeuser.getUserMap().size() > 0) {
			System.out.print("Folgende User sind vorhanden: ");
			for (Map.Entry<String, User> entry : storeuser.getUserMap().entrySet()) {
				System.out.print(entry.getValue().getUserName() + ", ");
			}
			System.out.println("\n> "); // Eingabeaufforderung2
			String eingabezeile = leser.nextLine();
			return storeuser.getUserMap().get(eingabezeile);
		} else {
			System.out
					.println("Es sind keine user vorhanden, erfassen Sie zuerst mindestens einen user. Zurück zum Hauptmenu...");
			startMenu();
			return null;
		}
	}

	private void loadStoredUsers() {
		System.out.println("Gespeicherte User werden geladen...");
		storeuser.read();
		printUsers();
		System.out.println("Anzahl geladener User: " + storeuser.getUserMap().size());
	}

	private void printUsers() {
		for (Map.Entry<String, User> entry : storeuser.getUserMap().entrySet()) {
			System.out.println("- " + entry.getValue().getUserName() + " (Inbox: "
					+ calculateTotalMessageCount(entry.getKey()) + " Messages)");
		}
	}

	private int calculateTotalMessageCount(String user) {
		return storeuser.getUserMap().get(user).getSmsBox().size()
				+ storeuser.getUserMap().get(user).getMailBox().size()
				+ storeuser.getUserMap().get(user).getPrintBox().size();
	}
}
