/**
 * Diese Klasse �berpr�ft ob ein String eine Telefon-Nummer ist
 * @author Samuel
 * 
 */
public class CheckTel implements Check{

		
	/**
	 * TODO: Dokumentation! (Stefan)
	 */
	@Override
	public boolean check(String stringToCheck) {
		return stringToCheck.matches("\\b[0-9]*\\b");
	}
}
