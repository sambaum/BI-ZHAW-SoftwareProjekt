import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuBasedClasses {

	// Variable für das interagieren mit Usern. Diese ist static damit alle
	// Objekt auf den gleichen user-Store zugreiffen. Mehrfaches instanzieren
	// eines user-Stores würde vermutlich zu ungewollten effektven führen.
	private static StoreUser storeuser;
	private Scanner leser; //Um die Eingabe des Users zu verarbeiten

	public MenuBasedClasses() {
		storeuser = new StoreUser();
		leser = new Scanner(System.in);
	}

	public void menuPrinter(HashMap<String, String> menuToPrint) {
		for (Map.Entry<String, String> entry : menuToPrint.entrySet()) {
			System.out.println("[" + entry.getKey() + "] " + entry.getValue());
		}
	}
	
	public String askAndGetAnswer(String frage){
		System.out.println(frage);
		System.out.print("> "); // Eingabeaufforderung
		return getLeser().nextLine(); //Eingabe lesen
	}
	
	public String askAndGetAnswerWithList(HashMap<String, String> list, String frage){
		System.out.println(frage);
		menuPrinter(list);
		System.out.print("> "); // Eingabeaufforderung
		return getLeser().nextLine(); //Eingabe lesen
	}

	public StoreUser getStoreuser() {
		return storeuser;
	}

	public Scanner getLeser() {
		return leser;
	}
}
