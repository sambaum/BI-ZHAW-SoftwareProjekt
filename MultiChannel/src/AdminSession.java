/**
 * Die Klasse kümmert sich um die User-Verwaltung. Es können User erasst un gelöscht werden.
 * Der User wird mit einem Menu durch die Aktivitäten geführt.
 */
import java.io.IOException;
import java.util.HashMap;


public class AdminSession extends MenuBasedClasses{
	
	public void startSession(){
		getStoreuser().read();
		System.out.println("\nWillkommen im Admin-Tool. Was möchten Sie tun?");
		chooseWhatToDo();
	}
	
	/**
	 * Start-Menu mit Auswahl
	 */
	public void chooseWhatToDo(){
		HashMap<String, String> initMenu = new HashMap<String, String>();
		initMenu.put("1", "User erfassen");
		initMenu.put("2", "User löschen");
		String antwort = "";
		while (initMenu.containsKey(antwort)==false){
			antwort = askAndGetAnswerWithList(initMenu, "\nWas möchten Sie tun?:");
			if (antwort.equals("1")){
				createUser();
			} else if(antwort.equals("2")){
				deleteuser();
			} else {
				System.out.println("Diese Auswahl gibt es nicht");
			}
		}
	}

	private void deleteuser() {
		// TODO Auto-generated method stub
	}

	/**
	 * Hier kann ein neue User erstellt werden.
	 * Infos werden druch den User eingegeben und anschliessend wird ein neues User-Objekt mit den gelieferten Angaben erstellt
	 */
	private void createUser() {
		System.out.println("\nEin neuer User wird erfasst...");
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
		chooseWhatToDo();
	}
}
