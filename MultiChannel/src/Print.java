import java.util.Date;

/**
 * Hier kommt unsere Doku und Beschreibung für diese Klasse hin.
 * 
 * @author Samuel
 * 
 */

public class Print extends Message {

	// Construktor
	public Print(User recipient, User sender, String message, Date date) {
		super(recipient, sender, message, date);
		// TODO Auto-generated constructor stub
	}
	
	// Construktor with id
	public Print(User recipient, User sender, String message, Date date, String id) {
		super(recipient, sender, message, date, id);
		// TODO Auto-generated constructor stub
	}

}
