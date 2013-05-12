import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Diese Klasse überprüft ob ein String ein Datum ist.
 * @author Samuel
 * 
 */

public class CheckDate implements Check{

	@Override
	public boolean check(String stringToCheck) {
		// TODO Auto-generated method stub
		return isDate(stringToCheck);
	}

	private boolean isDate(String stringToCheck) {
		// TODO Auto-generated method stub
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
