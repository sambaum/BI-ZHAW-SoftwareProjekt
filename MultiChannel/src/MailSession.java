/**
 * Diese Klasse besch�ftig sich mit der Mail Session. Die Mail-Session ist Benutzer-Spezifisch. Von hier aus kann man seine Nachrichten lesen oder eine Nachricht schicken.
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
	 * 
	 * @param user
	 */
	public MailSession(User user) {
		super();
		this.user = user;
		//System.out.println(getStoreuser().getUserNumberedList());
		getStoreuser().read();
	}

	/**
	 * Starten der Session. Wird aus der Klasse MultiChannel aufgerufen um die Session zu starten
	 */
	public void startSession() {
		System.out.println("Sie sind angemeldet als User: " + user.getUserName());
		chooseWhatToDo();
	}

	public void chooseWhatToDo() {
		HashMap<String, String> initMenu = new HashMap<String, String>();
		initMenu.put("1", "Nachrichten abrufen");
		initMenu.put("2", "Nachricht verschicken");
		initMenu.put("3", "Zur�ck zu Hauptmenu");
		// menuPrinter(initMenu);
		// System.out.println(initMenu);
		String antwort = askAndGetAnswerWithList(initMenu, "Was m�chten Sie tun?:");
		if (antwort.equals("1")) {
			readMessages();
		} else if (antwort.equals("2")) {
			sendNewMessage();
		} else if (antwort.equals("3")) {
			return;
		} else {
			System.out.println("Ihre Antwort war ung�ltig");
		}
	}

	public void readMessages() {
		for (String zeile : user.getFullInbox()) {
			System.out.println(zeile);
		}
		HashMap<String, String> auswahl = new HashMap<String, String>();
		auswahl.put("1", "Ja");
		auswahl.put("2", "Nein");
		if (user.getFullInbox().size() > 2) {
			String antwort = askAndGetAnswerWithList(auswahl, "M�chten Sie gelesene Nachrichen l�schen?");
			if (antwort.equals("1")) {
				//Nachrichen werden gel�scht
				user.clearAllMessages();
				getStoreuser().removeAllMessagesOfUser(user);
			} else if (antwort.equals("2")) {
				//Nachrichten werden NICHT gel�scht
			} else {
				//Nachrichten werden NICHT gel�scht
				System.out.println("Ihre Antwort war ung�ltig, Nachrichten werden nicht gel�scht");
			}
		}
		chooseWhatToDo();
	}

	public void sendNewMessage() {
		//getStoreuser().read(); //TODO, nur workaround, m�sste eigentlich auch ohne funktionieren
		String antwort = askAndGetAnswerWithList(getStoreuser().getUserNumberedList(), "W�hlen Sie den Empf�nger");
		User recipient = getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(antwort));
		String message = askAndGetAnswer("Geben Sie ihre Nachricht ein");
		antwort = askAndGetAnswer("Geben Sie das Versandsdatum an (Format: dd.MM.yyyy)");
		Date date = new Date();
		if (new CheckDate().check(antwort) == true) {
			System.out.println(antwort); //Debug
			try {
				date = new SimpleDateFormat("dd.MM.yyyy").parse(antwort);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Sie haben kein g�ltiges Versandsdatum angegeben. Die Nachricht wird sofort versendet");
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
		antwort = askAndGetAnswer("W�hlen Sie ihr �bertragungsmedium");
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
