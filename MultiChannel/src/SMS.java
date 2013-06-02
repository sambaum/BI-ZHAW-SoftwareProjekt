import java.util.Date;

/**
 * Hier kommt unsere Doku und Beschreibung für diese Klasse hin.
 * @author Samuel
 * 
 */

public class SMS extends Message{

	//Constructor
	public SMS(User recipient, User sender, String message, Date date) {
		super(recipient, sender, message, date);
		// TODO Auto-generated constructor stub
	}
	
	//Constructor mit id
	public SMS(User recipient, User sender, String message, Date date, String id) {
		super(recipient, sender, message, date, id);
		// TODO Auto-generated constructor stub
	}

}
