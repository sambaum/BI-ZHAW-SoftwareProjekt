import java.util.HashMap;


public class AdminSession extends MenuBasedClasses{
	
	public void startSession(){
		getStoreuser().read();
		System.out.println("Willkommen im Admin-Tool. Was m�chten Sie tun?");
		chooseWhatToDo();
	}
	
	public void chooseWhatToDo(){
		HashMap<String, String> initMenu = new HashMap<String, String>();
		initMenu.put("1", "User erfassen");
		initMenu.put("2", "User l�schen");
		//initMenu.put("3", "User bearbeiten");
		String antwort = askAndGetAnswerWithList(initMenu, "Was m�chten Sie tun?:");
	}
}
