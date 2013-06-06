import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Diese Klasse überprüft ob ein String ein Datum ist.
 * @author Samuel
 * 
 */

public class CheckDate implements Check{

	/**
	 * TODO: Dokumentation!
	 */
	@Override
	public boolean check(String stringToCheck) {
		return isDate(stringToCheck);
	}

	/**
	 * TODO: Dokumentation!
	 */
	private boolean isDate(String stringToCheck) {
		try {
		      //Check date validation
		      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		      dateFormat.setLenient(false);
		      dateFormat.parse(stringToCheck);
		      return true;
		}
		catch (ParseException e) {
		      return false;
		}
	}
	
}
