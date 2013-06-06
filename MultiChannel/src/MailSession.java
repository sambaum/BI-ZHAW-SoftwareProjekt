/**
 * Diese Klasse beschäftig sich mit der Mail Session. Die Mail-Session ist Benutzer-Spezifisch. Von hier aus kann man seine Nachrichten lesen oder eine Nachricht schicken.
 */

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MailSession extends MenuBasedClasses {

	private User user;

	/**
	 * Konstruktor Gespeicherte Daten werden ausgelesen
	 * 
	 * @param user
	 */
	public MailSession(User user) {
		super();
		this.user = user;
		getStoreuser().read();
	}

	/**
	 * Die Mail-Session wird gestartet und der User wird mit seinem Namen
	 * begrüsst. Die Dinge die er hier tun kann, werden Ihm angezeigt. Session
	 * zu starten
	 */
	public void startSession() {
		System.out.println("Sie sind angemeldet als User: "
				+ user.getUserName());
		chooseWhatToDo();
	}

	/**
	 * Menu und Entscheidung was man Tun will.
	 */
	public void chooseWhatToDo() {
		HashMap<String, String> initMenu = new HashMap<String, String>();
		initMenu.put("1", "Nachrichten abrufen");
		initMenu.put("2", "Nachricht verschicken");
		initMenu.put("3", "Nachricht an Gruppe verschicken");
		initMenu.put("4", "Zurück zu Hauptmenu");
		// menuPrinter(initMenu);
		// System.out.println(initMenu);
		String antwort = askAndGetAnswerWithList(initMenu,
				"Was möchten Sie tun?:");
		if (antwort.equals("1")) {
			readMessages();
		} else if (antwort.equals("2")) {
			sendNewMessageToSingelUser();
		} else if (antwort.equals("3")) {
			sendNewMessageToGroup();
		} else if (antwort.equals("4")) {
			return;
		} else {
			System.out.println("Ihre Antwort war ungültig");
		}
	}

	/**
	 * Alle Nachrichten eines User werden hier ausgegeben. Dem User wird die
	 * Wahl gestellt ob er seine gelesenen Nachrichten löschen möchte.
	 * Anschliessend geht es zurück zum Hauptmenu
	 */
	public void readMessages() {
		for (String zeile : user.getFullInbox()) {
			System.out.println(zeile);
		}
		HashMap<String, String> auswahl = new HashMap<String, String>();
		auswahl.put("1", "Ja");
		auswahl.put("2", "Nein");
		if (user.getFullInbox().size() > 2) {
			String antwort = askAndGetAnswerWithList(auswahl,
					"Möchten Sie gelesene Nachrichen löschen?");
			if (antwort.equals("1")) {
				// Nachrichen werden gelöscht
				user.clearAllMessages();
				getStoreuser().removeAllMessagesOfUser(user);
			} else if (antwort.equals("2")) {
				// Nachrichten werden NICHT gelöscht
			} else {
				// Nachrichten werden NICHT gelöscht
				System.out
						.println("Ihre Antwort war ungültig, Nachrichten werden nicht gelöscht");
			}
		}
		chooseWhatToDo();
	}

	private Date getDateForNewMessage() {
		String antwortTemp = askAndGetAnswer("Geben Sie das Versandsdatum an (Format: dd.MM.yyyy)");
		Date date = new Date();
		if (new CheckDate().check(antwortTemp) == true) {
			try {
				return new SimpleDateFormat("dd.MM.yyyy").parse(antwortTemp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.out
					.println("Sie haben kein gültiges Versandsdatum angegeben. Die Nachricht wird sofort versendet");
			try {
				return new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	private String getMessageTypeForNewMessage() {
		HashMap<String, String> messageTypes = new HashMap<String, String>();
		messageTypes.put("1", "SMS");
		messageTypes.put("2", "Mail");
		messageTypes.put("3", "Print");
		menuPrinter(messageTypes);
		return askAndGetAnswer("Wählen Sie ihr Übertragungsmedium");
	}

	public void sendNewMessageToSingelUser() {
		String antwortTemp = askAndGetAnswerWithList(getStoreuser()
				.getUserNumberedList(), "Wählen Sie den Empfänger");
		User recipient = getStoreuser().getUserMap().get(
				getStoreuser().getUserNumberedList().get(antwortTemp));
		String message = askAndGetAnswer("Geben Sie ihre Nachricht ein");
		Date date = getDateForNewMessage();
		String messageType = getMessageTypeForNewMessage();
		sendMessage(recipient, user, message, date, messageType);
		chooseWhatToDo();
	}

	private void sendMessage(User recipient, User user, String message,
			Date date, String messageType) {
		if (messageType.equals("1")) {
			user.addSMS(new SMS(recipient, user, message, date));
		}
		if (messageType.equals("2")) {
			user.addMail(new Mail(recipient, user, message, date));
		}
		if (messageType.equals("3")) {
			user.addPrint(new Print(recipient, user, message, date));
		}
		try {
			getStoreuser().write(user);
			System.out.println("Ihre Nachricht an " + user
					+ " wurde erfolgreich verschickt");
		} catch (IOException e) {
			System.out.println("Ihre Nachricht an " + user
					+ " konnte nicht verschickt werden");
			// e.printStackTrace();
		}
	}

	private void sendNewMessageToGroup() {

		String GruppenString = "(";
		for (String gruppe : getStoreuser().getAllExistingGroups()) {
			GruppenString = GruppenString + ", " + gruppe;
		}
		GruppenString = GruppenString + ")";

		String recipient_group = askAndGetAnswer("Wählen Sie aus folgenden Gruppen und geben die die gewünschte Gruppe ein: "
				+ GruppenString); // TODO muss gruppe sein
		// User recipient =
		// getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(antwortTemp));
		String message = askAndGetAnswer("Geben Sie ihre Nachricht ein");
		Date date = getDateForNewMessage();
		String messageType = getMessageTypeForNewMessage();
		for (Map.Entry<String, User> entry : getStoreuser().getUserMap()
				.entrySet()) {
			if (entry.getValue().getGroup().equals(recipient_group)) {
				sendMessage(entry.getValue(), user, message, date, messageType);
			}
		}
		chooseWhatToDo();
	}
}
