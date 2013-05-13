/**
 * Diese Klasse überprüft ob ein String eine Telefon-Nummer ist
 * @author Samuel
 * 
 */

public class CheckTel implements Check{

	@Override
	public boolean check(String stringToCheck) {
		// TODO Auto-generated method stub
		return stringToCheck.matches("\\b[0-9]*\\b");
	}

}
