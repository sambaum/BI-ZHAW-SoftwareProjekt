/**
 * Diese Klasse �berpr�ft ob ein String alphanumerisch ist
 * @author Samuel
 * 
 */

public class CheckAlphaNum implements Check{

	/**
	 * Die Methode �berpr�ft ob ein Sting nur Alphanumerische Zeichen enth�lt.
	 */
	@Override
	public boolean check(String stringToCheck) {
		// TODO Auto-generated method stub
		return stringToCheck.matches("\\b[a-zdjA-Z0-9]*\\b");
	}

}
