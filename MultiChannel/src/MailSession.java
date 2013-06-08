import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * Diese Klasse handelt die Mail-Sesion ab. Die Mail-Session ist Benutzer-Spezifisch. Von hier aus kann man seine
 * Nachrichten lesen oder neue Nachrichten schicken.
 */
public class MailSession extends MenuBasedClasses {

	private User user;

	/**
	 * Konstruktor: Gespeicherte Daten werden ausgelesen
	 * 
	 * @param user
	 *            (Entspricht der Identität des aktuellen Benutzers). Bei Verschicken von Nachrichten ist dieser der
	 *            Sender)
	 */
	public MailSession(User user) {
		super();
		this.user = user;
		getStoreuser().read();
	}

	/**
	 * Die Mail-Session wird gestartet und der User wird mit seinem Namen begrüsst. Die Dinge die er hier tun kann,
	 * werden Ihm angezeigt.
	 */
	public void startSession() {
		System.out.println("\nSie sind angemeldet als User: " + user.getUserName());
		chooseWhatToDo();
	}

	/**
	 * Menu anzeigen und entscheidung auswerten
	 */
	public void chooseWhatToDo() {
		TreeMap<String, String> initMenu = new TreeMap<String, String>();
		initMenu.put("1", "Nachrichten abrufen");
		initMenu.put("2", "Nachricht verschicken");
		initMenu.put("3", "Nachricht an Gruppe verschicken");
		initMenu.put("4", "Zurück zu Hauptmenu");
		// menuPrinter(initMenu);
		// System.out.println(initMenu);
		String antwort = askAndGetAnswerWithList(initMenu, "Was möchten Sie tun?:");
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
	 * Alle Nachrichten eines User werden hier ausgegeben. Dem User wird die Wahl gestellt ob er seine gelesenen
	 * Nachrichten löschen möchte. Anschliessend geht es zurück zum Hauptmenu
	 */
	public void readMessages() {
		for (String zeile : user.getFullInbox()) {
			System.out.println(zeile);
		}
		TreeMap<String, String> auswahl = new TreeMap<String, String>();
		auswahl.put("1", "Ja");
		auswahl.put("2", "Nein");
		if (user.getFullInbox().size() > 2) {
			String antwort = askAndGetAnswerWithList(auswahl, "Möchten Sie gelesene Nachrichen löschen?");
			if (antwort.equals("1")) {
				// Nachrichen werden gelöscht
				user.clearAllMessages();
				getStoreuser().removeAllMessagesOfUser(user);
			} else if (antwort.equals("2")) {
				// Nachrichten werden NICHT gelöscht
			} else {
				// Nachrichten werden NICHT gelöscht
				System.out.println("Ihre Antwort war ungültig, Nachrichten werden nicht gelöscht");
			}
		}
		chooseWhatToDo();
	}

	/**
	 * Datumseingabe und Prüfung
	 */
	private Date getDateForNewMessage() {
		String antwortTemp = askAndGetAnswer("Geben Sie das Versandsdatum an (Format: dd.MM.yyyy)");
		Date date = new Date();
		try {
			if (new CheckDate().check(antwortTemp) == true) {
				return new SimpleDateFormat("dd.MM.yyyy").parse(antwortTemp);
			} else {
				System.out.println("Sie haben kein gültiges Versandsdatum angegeben. Die Nachricht wird sofort versendet");
				return new SimpleDateFormat("dd.MM.yyyy").parse("01.01.2000");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Eingabe des Nachrichten-Typs (ob SMS/MMS, Mail oder Print)
	 */
	private String getMessageTypeForNewMessage() {
		TreeMap<String, String> messageTypes = new TreeMap<String, String>();
		messageTypes.put("1", "SMS/MMS");
		messageTypes.put("2", "Mail");
		messageTypes.put("3", "Print");
		menuPrinter(messageTypes);
		return askAndGetAnswer("Wählen Sie ihr Übertragungsmedium");
	}

	/**
	 * Methode um eine neue Nachricht an einen einzelnen Benutzer zu schicken.
	 */
	public void sendNewMessageToSingelUser() {
		String antwortTemp = askAndGetAnswerWithList(getStoreuser().getUserNumberedList(), "Wählen Sie den Empfänger");
		User recipient = getStoreuser().getUserMap().get(getStoreuser().getUserNumberedList().get(antwortTemp));
		String message = askAndGetAnswer("Geben Sie ihre Nachricht ein");
		Date date = getDateForNewMessage();
		String messageType = getMessageTypeForNewMessage();
		sendMessage(recipient, user, message, date, messageType);
		chooseWhatToDo();
	}

	/**
	 * Methode welche die Nachricht verschickt und speichert.
	 */
	private void sendMessage(User recipient, User user, String message, Date date, String messageType) {
		if (messageType.equals("1")) {
			recipient.addSMS(new SMS(recipient, user, message, date));
		}
		if (messageType.equals("2")) {
			recipient.addMail(new Mail(recipient, user, message, date));
		}
		if (messageType.equals("3")) {
			recipient.addPrint(new Print(recipient, user, message, date));
		}
		try {
			getStoreuser().write(recipient);
			System.out.println("Ihre Nachricht an " + recipient.getUserName() + " wurde erfolgreich verschickt");
		} catch (IOException e) {
			System.out.println("Ihre Nachricht an " + recipient.getUserName() + " konnte nicht verschickt werden");
			// e.printStackTrace();
		}
	}

	/**
	 * Nacht wird an eine Gruppe verschickt und kann mehrer Empfänger haben.
	 */
	private void sendNewMessageToGroup() {
		TreeMap<String, String> gruppen = new TreeMap<String, String>();
		Integer i = 1;
		for (String gruppe : getStoreuser().getAllExistingGroups()) {
			gruppen.put(i.toString(), gruppe);
			i++;
		}
		String recipient_group = gruppen.get(askAndGetAnswerWithList(gruppen, "Wählen Sie aus folgenden Gruppen"));
		if (getStoreuser().getAllExistingGroups().contains(recipient_group)) {
			String message = askAndGetAnswer("Geben Sie ihre Nachricht ein");
			Date date = getDateForNewMessage();
			String messageType = getMessageTypeForNewMessage();
			for (Map.Entry<String, User> entry : getStoreuser().getUserMap().entrySet()) {
				if (entry.getValue().getGroup().equals(recipient_group)) {
					sendMessage(entry.getValue(), user, message, date, messageType);
				}
			}
			chooseWhatToDo();
		} else {
			System.out.println("Der user existiert nicht..");
			sendNewMessageToGroup();
		}
	}
}
