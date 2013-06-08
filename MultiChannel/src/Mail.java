import java.util.Date;

/**
 * Die Klasse repräsentiert eine E-Mail
 * 
 * @author Samuel ...
 */
public class Mail extends Message {

	/**
	 * Standard-Konstruktor
	 * 
	 * @param recipient
	 * @param sender
	 * @param message
	 * @param date
	 */
	public Mail(User recipient, User sender, String message, Date date) {
		super(recipient, sender, message, date);
	}

	/**
	 * Kontruktor mit einem Zusätzlichen Feld ID. Dieser Konstruktor wird
	 * benötigt um vorhanden User ein gespeicherten Files zu laden.
	 * Normalerweise wird die ID beim instanzieren generiert. Wird aber ein
	 * vorhandener User geladen, soll die vorhanden ID verwendet werden.
	 * 
	 * @param recipient
	 * @param sender
	 * @param message
	 * @param date
	 * @param id
	 */
	public Mail(User recipient, User sender, String message, Date date, String id) {
		super(recipient, sender, message, date, id);
	}
}
