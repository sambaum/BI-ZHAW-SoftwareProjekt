/**
 * Diese Klasse beschäftig sich mit der Mail Session. Die Mail-Session ist Benutzer-Spezifisch. Von hier aus kann man seine Nachrichten lesen oder eine Nachricht schicken.
 * 
 */
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class MailSession extends MenuBasedClasses {

	private User user;

	/**
	 * Konstruktor
	 * @param user
	 */
	public MailSession(User user) {
		super();
		this.user = user;
		//System.out.println(getStoreuser().getUserNumberedList());
	}

	/**
	 * Starten der Session. Wird aus der Klasse MultiChannel aufgerufen um die Session zu starten
	 */
	public void startSession() {
		System.out.println("Sie sind angemeldet als User: " + user.getUserName());
		chooseWhatToDo();
	}
	
	public void chooseWhatToDo(){
		HashMap<String, String> initMenu = new HashMap<String, String>();
		initMenu.put("1", "Nachrichten abrufen");
		initMenu.put("2", "Nachricht verschicken");
		initMenu.put("3", "Zurück zu Hauptmenu");
		// menuPrinter(initMenu);
		// System.out.println(initMenu);
		String antwort = askAndGetAnswerWithList(initMenu, "Was möchten Sie tun?:");
		if (antwort.equals("1")) {
			for (String zeile : user.getFullInbox()) {
				System.out.println(zeile);
				// TODO gelesene Nachrichten müssen gelöscht werden
			}
		} else if (antwort.equals("2")) {
			sendNewMessage();
		} else if (antwort.equals("3")) {
			return;
		} else {
			System.out.println("Ihre Antwort war ungültig");
		}
	}

	public void sendNewMessage() {
		getStoreuser().read(); //TODO, nur workaround, müsste eigentlich auch ohne funktionieren
		String antwort = askAndGetAnswerWithList(getStoreuser().getUserNumberedList(), "Wählen Sie den Empfänger");
		User recipient = getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(antwort));
		String message = askAndGetAnswer("Geben Sie ihre Nachricht ein");
		antwort = askAndGetAnswer("Geben Sie das Versandsdatum an (Format: dd.MM.yyyy)");
		Date date = new Date();
		if (new CheckDate().check(antwort) == true) {
			try {
				date = new SimpleDateFormat("dd.MM.yyyy").parse(antwort);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Sie haben kein gültiges Versandsdatum angegeben. Die Nachricht wird sofort versendet");
			try {
				date = new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		HashMap<String, String> messageTypes = new HashMap<String, String>();
		messageTypes.put("1", "SMS");
		messageTypes.put("2", "Mail");
		messageTypes.put("3", "Print");
		menuPrinter(messageTypes);
		antwort = askAndGetAnswer("Wählen Sie ihr Übertragungsmedium");
		if (antwort.equals("1")) {
			user.addSMS(new SMS(recipient, user, message, date));
		}
		if (antwort.equals("2")) {
			user.addMail(new Mail(recipient, user, message, date));
		}
		if (antwort.equals("3")) {
			user.addPrint(new Print(recipient, user, message, date));
		}
		try {
			getStoreuser().write(user);
			System.out.println("Ihre Nachricht wurde erfolgreich verschickt");
		} catch (IOException e) {
			System.out.println("Ihre Nachricht konnte nicht verschickt werden");
			//e.printStackTrace();
		}
		chooseWhatToDo();
	}
}
