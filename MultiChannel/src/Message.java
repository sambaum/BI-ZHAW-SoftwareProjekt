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

	// Constructor
	public Message(User recipient, User sender, String message, String date) {
		this.recipient = recipient;
		this.sender = sender;
		this.message = message;
		this.Date = date;
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
	
	

}
