import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Diese Klasse nimmt User Objekte entgegen und speichert diese in einem File ab. Es werden die dazugehoerigen Messages
 * ebenfalls abgespeichert. Alles Files sind in einem Text-Format und koennen mit einem normalen Text-Editor geoeffnet
 * und selber editiert werden.
 * 
 * Diese Klasse liest vorhandene Files und erstellt daraus Objekte. Die Files haben folgende Namens-konvention:
 * message_user_[Message-Typ]_[Sende-Datum]_ID Beispiel fuer einen File-Name eines SMS von einem User Frodo:
 * message_frodo_SMS_02.01.2013_20130607181629
 * 
 * @author Samuel
 * 
 */
public class StoreUser {

	private HashMap<String, User> userMap;
	private SimpleDateFormat sdf;

	/**
	 * userMap: Variablen welche eine Liste von allen vorhandenen Usern enthaelt. Zu erkennen mit dem "userName". sdf
	 * dient zur formattierung des Datums.
	 */
	public StoreUser() {
		this.userMap = new HashMap<String, User>();
		this.sdf = new SimpleDateFormat("dd.MM.yyyy");
	}

	/**
	 * Methode: write User wird in ein File gespeichert. Jedes Feld ist eine Zeile. Zeile 1: username Zeile 2: Gruppe
	 * Zeile 3: Mobiltelefon Zeile 4: Email Zeile 5: Druckername
	 * 
	 * @return Gibt bei erfolg true zurück
	 */
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

	/**
	 * Methode: messageWriter (wird von Methode write aufgerufen) Nachricht wird in ein File gespeichert. Jedes Feld ist
	 * eine Zeile. Zeile 1: sender (Absender) Zeile 2: recepient (Empfänger) Zeile 3: Nachricht Zeile 4: Versands-Datum
	 * 
	 * @return Gibt bei erfolg true zurück
	 */
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

	/**
	 * Methode: read User werden aus den Files gelesen und instanziert. Dazugehoerige Messages instanziert und
	 * zugewiesen. Die einzelnen Aktionen sind in den Methoden reviveAllUsers und reviveAllMessagesOfUser implementiert.
	 * 
	 * @return Gibt alle User in Form eine Hashmap zurück. Das Schlüsselwort um einen User in der Hashmap zu finden ist
	 *         der username
	 */
	public HashMap<String, User> read() {
		reviveAllUsers();
		for (Map.Entry<String, User> entry : userMap.entrySet()) {
			reviveAllMessagesOfUser(entry.getValue());
		}
		return userMap;
	}

	/**
	 * Methode: reviveAllUsers Alle User die in einem File gespeichert sind, werden instanziert
	 * 
	 * @return Gibt alle User in Form eine Hashmap zurück. Das Schlüsselwort um einen User in der Hashmap zu finden ist
	 *         der username
	 */
	private HashMap<String, User> reviveAllUsers() {
		File dir = new File(getDirectoryName());
		final String filterUser = getUserPrefix();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(filterUser);
			}
		};
		ArrayList<String> listOfAllUserFiles = new ArrayList<String>();
		if (dir.isDirectory()) {
			String[] dirInhalt = dir.list(filter);
			for (int i = 0; i < dirInhalt.length; i++) {
				listOfAllUserFiles.add(dirInhalt[i]);
			}
		}
		for (String fileName : listOfAllUserFiles) {
			ArrayList<String> singleUser = new ArrayList<String>();
			singleUser = readTextFileToArray(fileName);
			User newUser = new User(singleUser.get(0), singleUser.get(1), singleUser.get(2), singleUser.get(3), singleUser.get(4));
			userMap.put(newUser.getUserName(), newUser);
		}
		return userMap;
	}

	/**
	 * List ein einzelnes File und füllt ein Array ab. Jede Zeile ist ein Eintrag im Array
	 * 
	 * @param fileName
	 * @return Array mit allen Zeile des Files
	 */
	private ArrayList<String> readTextFileToArray(String fileName) {
		ArrayList<String> singeFileContent = new ArrayList<String>();
		try {
			File f = new File(getDirectoryName() + fileName);
			FileReader fileReader = new FileReader(f);
			BufferedReader reader = new BufferedReader(fileReader);
			String row = null;

			while ((row = reader.readLine()) != null) {
				singeFileContent.add(row);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singeFileContent;
	}

	public void removeAllMessagesOfUser(User user) {
		File dir = new File(getDirectoryName());
		final String filterUser = getMessagePrefix() + user.getUserName();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(filterUser);
			}
		};
		// ArrayList<String> allMessagesString = new ArrayList<String>();
		if (dir.isDirectory()) {
			String[] dirInhalt = dir.list(filter);
			for (int i = 0; i < dirInhalt.length; i++) {
				File f = new File(getDirectoryName() + dirInhalt[i]);
				if (f.delete() == false) {
					System.out.println("Die Nachrichten konnten nicht gelöscht werden");
				} else {
					System.out.println("Nachricht " + f.getName() + " gelöscht!");
				}
			}
		}
	}

	/**
	 * Instanziert alle Message Objekte eines users und fuegt diese der Inbox hinzu.
	 * 
	 * @param user
	 */
	private void reviveAllMessagesOfUser(User user) {
		File dir = new File(getDirectoryName());
		final String filterUser = getMessagePrefix() + user.getUserName();
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.contains(filterUser);
			}
		};
		ArrayList<String> allMessagesString = new ArrayList<String>();
		if (dir.isDirectory()) {
			String[] dirInhalt = dir.list(filter);
			for (int i = 0; i < dirInhalt.length; i++) {
				allMessagesString.add(dirInhalt[i]);
			}
		}
		for (String fileName : allMessagesString) {
			ArrayList<String> singleMessage = readTextFileToArray(fileName);
			try {
				if (fileName.contains("SMS")) {
					SMS newSMS = new SMS(user, userMap.get(singleMessage.get(1)), singleMessage.get(2), new SimpleDateFormat("dd.MM.yyyy").parse(singleMessage.get(3)),
							singleMessage.get(4));
					user.addSMS(newSMS);
				}
				if (fileName.contains("Mail")) {
					Mail newMail = new Mail(user, userMap.get(singleMessage.get(1)), singleMessage.get(2), new SimpleDateFormat("dd.MM.yyyy").parse(singleMessage.get(3)),
							singleMessage.get(4));
					user.addMail(newMail);
				}
				if (fileName.contains("Print")) {
					Print newPrint = new Print(user, userMap.get(singleMessage.get(1)), singleMessage.get(2), new SimpleDateFormat("dd.MM.yyyy").parse(singleMessage.get(3)),
							singleMessage.get(4));
					user.addPrint(newPrint);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Löscht einen User
	 * 
	 * @param user
	 * @return
	 */
	public boolean delete(User user) {
		File f = new File(generateFileNameUser(user));
		@SuppressWarnings("unused")
		boolean success = f.delete();
		if (success = true) {
			userMap.remove(user);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * TODO: Doku
	 * 
	 * @param user
	 * @return
	 */
	private ArrayList<String> objectToStringUser(User user) {
		ArrayList<String> list = new ArrayList<String>();
		list.add(user.getUserName());
		list.add(user.getGroup());
		list.add(user.getTel());
		list.add(user.getEmail());
		list.add(user.getPrinterName());
		return list;
	}

	/**
	 * TODO: Doku
	 * 
	 * @param message
	 * @return
	 */
	private ArrayList<String> objectToStringMessage(Message message) {
		ArrayList<String> list = new ArrayList<String>();
		list.add((message.getRecipient()).getUserName());
		list.add((message.getSender()).getUserName());
		list.add(message.getMessage());
		list.add(sdf.format(message.getDate()));
		list.add(message.getId());
		return list;
	}

	/**
	 * TODO: Doku
	 * 
	 * @param user
	 * @return
	 */
	private String generateFileNameUser(User user) {
		return getDirectoryName() + getUserPrefix() + user.getUserName() + ".txt";
	}

	/**
	 * TODO: Doku
	 * 
	 * @param user
	 * @param message
	 * @return
	 */
	private String generateFileNameMessage(User user, Message message) {
		return getDirectoryName() + getMessagePrefix() + user.getUserName() + "_" + message.getClass().getName() + "_" + sdf.format(message.getDate()) + "_" + message.getId()
				+ ".txt";
	}

	/**
	 * TODO: Doku
	 * 
	 * @return
	 */
	private String getDirectoryName() {
		String dirName = "workding_dir/";
		File dir = new File(dirName);
		dir.mkdir();
		return dirName;
	}

	/**
	 * TODO: Doku
	 * 
	 * @return
	 */
	public TreeMap<String, String> getUserNumberedList() {
		TreeMap<String, String> numberedList = new TreeMap<String, String>();
		Integer number = 1;
		for (Map.Entry<String, User> entry : userMap.entrySet()) {
			numberedList.put(number.toString(), entry.getKey());
			number++;
		}
		return numberedList;
	}

	/**
	 * TODO: Doku
	 * 
	 * @return
	 */
	public HashSet<String> getAllExistingGroups() {
		HashSet<String> listOfGroups = new HashSet<String>();
		for (Map.Entry<String, User> entry : userMap.entrySet()) {
			listOfGroups.add(entry.getValue().getGroup());
		}
		return listOfGroups;
	}

	/**
	 * TODO: Doku
	 * 
	 * @return
	 */
	private String getMessagePrefix() {
		String messagePrefix = "message_";
		return messagePrefix;
	}

	/**
	 * TODO: Doku
	 * 
	 * @return
	 */
	private String getUserPrefix() {
		String messagePrefix = "user_";
		return messagePrefix;
	}

	// getter und setters
	public HashMap<String, User> getUserMap() {
		return userMap;
	}
}
