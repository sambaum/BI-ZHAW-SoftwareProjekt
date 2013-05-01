import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Argumente (Input): Objekt des Typs Message Return-Werte (Output): boolean
 * 
 * Diese Klasse nimmt StoreMessage Objekte entgegen und speichert diese in einem
 * File ab. Diese Klasse liest verhandene Files und erstellt daraus Objekte Die
 * Files haben folgende Namens-konvention:
 * message_[Message-Typ]_user_[Sende-Datum] Beispiel für einen File-Name eines
 * SMS von einem User Frodo: message_sms_frodo_20130501_235526
 * 
 * @author Samuel
 * 
 */
public class StoreMessage extends Storable {

	public boolean write(User user) throws IOException {
		FileWriter writer = null;
		ArrayList<String> userString = objectToString(user);
		String fileName = generateFileName(user);
		try {
			writer = new FileWriter(fileName);
			for (String fields : userString) {
				writer.write(fields);
				writer.write("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			writer.close();
		}
		return true;
	}

	public boolean read(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(User user) {
		File f = new File(generateFileName(user));
		return f.delete();
	}

	private ArrayList<String> objectToString(User user) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(user.getUserName());
		list.add(user.getGroup());
		list.add(user.getTel());
		list.add(user.getEmail());
		list.add(user.getPrinterName());
		return list;
	}
	
	private String generateFileName(User user){
		return "user_" + user.getUserName() + ".txt";
	}

}
