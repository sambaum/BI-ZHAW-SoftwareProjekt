/**
 * Diese Klasse beschäftig sich mit der Mail Session. Die Mail-Session ist Benutzer-Spezifisch. Von hier aus kann man seine Nachrichten lesen oder eine Nachricht schicken.
 * 
 */
import java.util.HashMap;

public class MailSession extends MenuBasedClasses{

	private User user;
	private HashMap<Integer, String> actionList;

	/**
	 * Konstruktor
	 * 
	 * @param user
	 */
	public MailSession(User user) {
		super();
		this.user = user;
	}

	/**
	 * Starten der Session. Wird aus der Klasse MultiChannel aufgerufen um die
	 * Session zu starten
	 */
	public void startSession() {
		System.out.println("Sie sind angemeldet als User: " + user.getUserName());
	}

}
