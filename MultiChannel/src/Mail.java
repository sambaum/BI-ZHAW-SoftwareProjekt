import java.util.Date;

/**
 * Hier kommt unsere Doku und Beschreibung f�r diese Klasse hin.
 * @author Samuel
 * ...
 */

public class Mail extends Message{

	//constructor
	public Mail(User recipient, User sender, String message, Date date) {
		super(recipient, sender, message, date);
		// TODO Auto-generated constructor stub
	}
	
	//constructor mit id
	public Mail(User recipient, User sender, String message, Date date, String id) {
		super(recipient, sender, message, date, id);
		// TODO Auto-generated constructor stub
	}

}
