import java.io.IOException;
import java.util.HashMap;


public class AdminSession extends MenuBasedClasses{
	
	public void startSession(){
		getStoreuser().read();
		System.out.println("Willkommen im Admin-Tool. Was möchten Sie tun?");
		chooseWhatToDo();
	}
	
	public void chooseWhatToDo(){
		HashMap<String, String> initMenu = new HashMap<String, String>();
		initMenu.put("1", "User erfassen");
		initMenu.put("2", "User löschen");
		String antwort = askAndGetAnswerWithList(initMenu, "Was möchten Sie tun?:");
		if (antwort.equals("1")){
			createUser();
		} else if(antwort.equals("2")){
			deleteuser();
		}
	}

	private void deleteuser() {
		// TODO Auto-generated method stub
	}

	private void createUser() {
		System.out.println("Ein neuer User wird erfasst...");
		String username = askAndGetAnswer("Usernamen:");
		String group = askAndGetAnswer("Gruppe:");
		String tel = askAndGetAnswer("Telefon-Nummer:");
		String email = askAndGetAnswer("E-Mail:");
		String printer = askAndGetAnswer("Drucker-Namen");
		try {
			getStoreuser().write(new User(username, group, tel, email, printer));
		} catch (IOException e) {
			System.out.println("Bei speichern des Users ist ein Fehler aufgetreten");
			e.printStackTrace();
		}
	}
}
