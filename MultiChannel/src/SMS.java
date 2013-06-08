import java.util.Date;

/**
 * Die Klasse repr�sentiert eine SMS.
 * 
 * @author Samuel
 * 
 */
public class SMS extends Message {

	/**
	 * Standard-Konstruktor
	 * 
	 * @param recipient
	 * @param sender
	 * @param message
	 * @param date
	 */
	public SMS(User recipient, User sender, String message, Date date) {
		super(recipient, sender, message, date);
	}

	/**
	 * Kontruktor mit einem Zus�tzlichen Feld ID. Dieser Konstruktor wird
	 * ben�tigt um vorhanden User ein gespeicherten Files zu laden.
	 * Normalerweise wird die ID beim instanzieren generiert. Wird aber ein
	 * vorhandener User geladen, soll die vorhanden ID verwendet werden.
	 * 
	 * @param recipient
	 * @param sender
	 * @param message
	 * @param date
	 * @param id
	 */
	public SMS(User recipient, User sender, String message, Date date, String id) {
		super(recipient, sender, message, date, id);
	}
}
