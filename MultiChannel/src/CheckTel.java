/**
 * Diese Klasse überprüft ob ein String eine Telefon-Nummer ist
 * @author Samuel
 * 
 */

public class CheckTel implements Check{

	/**
	 * TODO: Dokumentation!
	 */
	@Override
	public boolean check(String stringToCheck) {
		return stringToCheck.matches("\\b[0-9]*\\b");
	}

}
