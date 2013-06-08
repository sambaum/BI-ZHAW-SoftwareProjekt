/**
 * Diese Klasse �berpr�ft ob ein String eine E-mail Adresse ist
 * 
 * @author Samuel
 * 
 */
public class CheckEmail implements Check {

	/**
	 * TODO: Dokumentation! (Stefan)
	 */
	@Override
	public boolean check(String stringToCheck) {
		return stringToCheck.matches("\\b[a-zdjA-Z0-9._%+-]*@[a-zA-Z0-9.-]*\\.[a-zA-Z]{2,4}\\b");
	}
}
