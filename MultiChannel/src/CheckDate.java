import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Diese Klasse �berpr�ft ob ein String ein Datum ist.
 * 
 * @author Samuel
 */
public class CheckDate implements Check {

	/**
	 * TODO: Dokumentation! (Stefan)
	 */
	@Override
	public boolean check(String stringToCheck) {
		return isDate(stringToCheck);
	}

	/**
	 * TODO: Dokumentation! (Stefan)
	 */
	private boolean isDate(String stringToCheck) {
		try {
			// Check date validation
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			dateFormat.setLenient(false);
			dateFormat.parse(stringToCheck);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

}
