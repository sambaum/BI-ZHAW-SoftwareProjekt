import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
public class StoreUser {

	public boolean write(User user) throws IOException {
		FileWriter writer = null;
		ArrayList<String> userString = objectToStringUser(user);
		String fileName = generateFileNameUser(user);
		try {
			writer = new FileWriter(fileName);
			for (String fields : userString) {
				writer.write(fields);
				writer.write("\n");
			}
			messageWriter(user);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			writer.close();
		}
		return true;
	}

	private boolean messageWriter(User user) throws IOException {
		ArrayList<Message> messageList = new ArrayList<Message>();
		messageList.addAll(user.getSmsBox());
		messageList.addAll(user.getMailBox());
		messageList.addAll(user.getPrintBox());
		FileWriter writer = null;
		for (Message message : messageList) {
			ArrayList<String> messageString = objectToStringMessage(message);
			String fileName = generateFileNameMessage(user, message);
			try {
				writer = new FileWriter(fileName);
				for (String fields : messageString) {
					writer.write(fields);
					writer.write("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				writer.close();
			}
		}
		return true;
	}

	public boolean read(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean delete(User user) {
		File f = new File(generateFileNameUser(user));
		return f.delete();
	}

	private ArrayList<String> objectToStringUser(User user) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(user.getUserName());
		list.add(user.getGroup());
		list.add(user.getTel());
		list.add(user.getEmail());
		list.add(user.getPrinterName());
		return list;
	}

	private ArrayList<String> objectToStringMessage(Message message) {
		ArrayList<String> list = new ArrayList<String>();
		list.add((message.getRecipient()).getUserName());
		list.add((message.getSender()).getUserName());
		list.add(message.getMessage());
		list.add(message.getDate());
		return list;
	}

	private String generateFileNameUser(User user) {
		return "user_" + user.getUserName() + ".txt";
	}

	private String generateFileNameMessage(User user, Message message) {
		return "message_" + user.getUserName() + "_" + message.getClass()
				+ message.getDate() + ".txt";
	}
}
