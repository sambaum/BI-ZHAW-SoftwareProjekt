import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Die Super-Klasse hat eine Sammlung von Methoden welche durch andere Menu-Basiert Klassen oft verwendet werden
 * 
 * storeuser: Variable f�r das interagieren mit Usern. Diese ist static damit alle Objekt auf den gleichen user-Store
 * zugreiffen. Mehrfaches instanzieren eines user-Stores w�rde zu ungewollten effektven f�hren. leser: Um die Eingabe
 * des Users zu verarbeiten
 */
public class MenuBasedClasses {

	private static StoreUser storeuser;
	private Scanner leser;

	/**
	 * Konstruktor (standard)
	 */
	public MenuBasedClasses() {
		storeuser = new StoreUser();
		leser = new Scanner(System.in);
	}

	/*
	 * Generische Methode um ein Menu darzustellen. Das Menu wird in form einer Liste �bergeben
	 */
	public void menuPrinter(TreeMap<String, String> menuToPrint) {
		for (Map.Entry<String, String> entry : menuToPrint.entrySet()) {
			System.out.println("[" + entry.getKey() + "] " + entry.getValue());
		}
	}

	/**
	 * Simple Methode um den Benutzer um eine Eingabe zu bitten.
	 * 
	 * @param frage
	 *            Die Frage wird der Methode �bergeben
	 * @return Die Antwort wird als String zur�ckgegeben
	 */
	public String askAndGetAnswer(String frage) {
		System.out.println(frage);
		System.out.print("> "); // Eingabeaufforderung
		return getLeser().nextLine(); // Eingabe lesen
	}

	/**
	 * Die Methode nimmt eine Liste mit Auswahlm�glichkeiten entgegen und gibt Eingabe/Antwort des Benutzers zur�ck.
	 * Zudem wird sichergestellt, dass es die Auswahl auch wirklich gibt und so lange gefragt bis der Benutzer eine
	 * g�ltige Eingabe macht.
	 */
	public String askAndGetAnswerWithList(TreeMap<String, String> list, String frage) {
		String antwort = "";
		do {
			System.out.println("\n" + frage);
			menuPrinter(list);
			System.out.print("> "); // Eingabeaufforderung
			antwort = getLeser().nextLine();
			if (list.containsKey(antwort) == false) {
				System.out.println("\nDie Auswal " + antwort + " gibt es nicht, bitte W�hlen Sie zwischen 1 und " + list.size() + " aus\n");
			}
		} while (list.containsKey(antwort) == false);
		return antwort; // Eingabe lesen
	}

	//Getter & Setter
	
	public StoreUser getStoreuser() {
		return storeuser;
	}

	public Scanner getLeser() {
		return leser;
	}
}
