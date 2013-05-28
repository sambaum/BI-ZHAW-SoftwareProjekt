import java.util.HashMap;

public class MailSession {

	private User user;
	private HashMap<Integer, String> actionList;

	public MailSession(User user) {
		this.user = user;
	}

	public void startSession() {
		System.out.println("Sie sind angemeldet als User: " + user.getUserName());
	}

}
