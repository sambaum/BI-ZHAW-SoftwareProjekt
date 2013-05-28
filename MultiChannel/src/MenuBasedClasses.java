import java.util.HashMap;
import java.util.Map;

public class MenuBasedClasses {

	private StoreUser storeuser; //Variable für das Iteragieren mit Usern

	public MenuBasedClasses() {
		storeuser = new StoreUser();
	}

	public void menuPrinter(HashMap<String, String> menuToPrint) {
		for (Map.Entry<String, String> entry : storeuser.getUserNumberedList().entrySet()) {
			System.out.println("[" + entry.getKey() + "] " + entry.getValue());
		}
	}

	public StoreUser getStoreuser() {
		return storeuser;
	}

}
