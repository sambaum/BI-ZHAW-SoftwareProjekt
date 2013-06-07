/**
 * TODO: Doku
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuBasedClasses {

	// Variable für das interagieren mit Usern. Diese ist static damit alle
	// Objekt auf den gleichen user-Store zugreiffen. Mehrfaches instanzieren
	// eines user-Stores würde vermutlich zu ungewollten effektven führen.
	private static StoreUser storeuser;
	private Scanner leser; // Um die Eingabe des Users zu verarbeiten

	/**
	 * TODO: Doku
	 */
	public MenuBasedClasses() {
		storeuser = new StoreUser();
		leser = new Scanner(System.in);
	}

	/*
	 * TODO: Doku
	 */
	public void menuPrinter(HashMap<String, String> menuToPrint) {
		for (Map.Entry<String, String> entry : menuToPrint.entrySet()) {
			System.out.println("[" + entry.getKey() + "] " + entry.getValue());
		}
	}

	/**
	 * TODO: Doku
	 * 
	 * @param frage
	 * @return
	 */
	public String askAndGetAnswer(String frage) {
		System.out.println(frage);
		System.out.print("> "); // Eingabeaufforderung
		return getLeser().nextLine(); // Eingabe lesen
	}

	/**
	 * Die Methode nimmt eine Liste mit Auswahlmöglichkeiten entgegen und gibt Eingabe/Antwort des Benutzers zurück.
	 * Zudem wird sichergestellt, dass es die Auswahl auch wirklich gibt.
	 */
	public String askAndGetAnswerWithList(HashMap<String, String> list, String frage) {
		String antwort = "";
		//Collections.sort((List<T>) list);
		do {
			System.out.println("\n" + frage);
			menuPrinter(list);
			System.out.print("> "); // Eingabeaufforderung
			antwort = getLeser().nextLine();
			if(list.containsKey(antwort) == false){
				System.out.println("\nDie Auswal " + antwort +" gibt es nicht, bitte Wählen Sie zwischen 1 und " + list.size() + " aus\n");
			}
		} while (list.containsKey(antwort) == false);
		return antwort; // Eingabe lesen
	}

	public StoreUser getStoreuser() {
		return storeuser;
	}

	public Scanner getLeser() {
		return leser;
	}
}
