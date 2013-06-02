import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hier kommt unsere Doku und Beschreibung fuer diese Klasse hin.
 * 
 * @author Samuel
 * 
 */

public class Message {

	// Instanzvariablen
	private User recipient;
	private User sender;
	private String message;
	private Date date;
	private String id;

	// Constructor
	public Message(User recipient, User sender, String message, Date date) {
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.setId(genID());
	}
	
	// Constructor mit ID
	public Message(User recipient, User sender, String message, Date date, String id) {
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.id = id;
	}

	private String genID(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		return sdfDate.format(now);
	}
	
	//getters and setters
	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
