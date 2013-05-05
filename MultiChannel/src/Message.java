import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hier kommt unsere Doku und Beschreibung für diese Klasse hin.
 * 
 * @author Samuel
 * 
 */

public class Message {

	// Instanzvariablen
	private User recipient;
	private User sender;
	private String message;
	private String Date;
	private String id;

	// Constructor
	public Message(User recipient, User sender, String message, String date) {
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
		this.Date = date;
		this.setId(genID());
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

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
