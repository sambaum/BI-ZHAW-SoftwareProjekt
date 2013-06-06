/**
 * Diese Klasse überprüft ob ein String alphanumerisch ist
 * @author Samuel
 * 
 */

public class CheckAlphaNum implements Check{

	/**
	 * Die Methode überprüft ob ein Sting nur Alphanumerische Zeichen enthält.
	 */
	@Override
	public boolean check(String stringToCheck) {
		// TODO Auto-generated method stub
		return stringToCheck.matches("\\b[a-zdjA-Z0-9]*\\b");
	}

}
