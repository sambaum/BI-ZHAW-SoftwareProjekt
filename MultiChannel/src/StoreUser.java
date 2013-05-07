import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Argumente (Input): Objekt des Typs Message Return-Werte (Output): boolean
 * 
 * Diese Klasse nimmt User Objekte entgegen und speichert diese in einem File
 * ab. Es werden die dazugehoerigen Messages ebenfalls abgespeichert. Alles
 * Files sind in einem Text-Format und koennen mit einem normalen Text-Editor
 * geoeffnet und selber editiert werden.
 * 
 * Diese Klasse liest verhandene Files und erstellt daraus Objekte. Die Files
 * haben folgende Namens-konvention: message_[Message-Typ]_user_[Sende-Datum]
 * Beispiel fuer einen File-Name eines SMS von einem User Frodo:
 * message_sms_frodo_20130501_235526
 * 
 * @author Samuel
 * 
 */
public class StoreUser {

	// Variablen welche nach dem Einlesen eine Liste von allen eingelesenen
	// Usern enthaelt. Zu erkennen mit dem userName.
	private HashMap<String, User> allUsersListMap;
	// Array List mit allen Usern. Sie hat die gleiche Reihenfolge wie
	// allUsersListMap, ist aber Iterierbar.
	private ArrayList<String> allUserNameList;

	public StoreUser() {
		this.allUsersListMap = new HashMap<String, User>();
		this.allUserNameList = new ArrayList<String>();
	}

	/**
	 * Methode: write
	 * User wird in ein File gespeichert. Jedes Feld ist eine Zeile.
	 * Zeile 1: username
	 * Zeile 2: Gruppe
	 * Zeile 3: Mobiltelefon
	 * Zeile 4: Email
	 * Zeile 5: Druckername
	 * @return
	 * Gibt bei erfolg true zurück
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
	 * Methode: messageWriter (wird von Methode write aufgerufen)
	 * Nachricht wird in ein File gespeichert. Jedes Feld ist eine Zeile.
	 * Zeile 1: sender (Absender)
	 * Zeile 2: recepient (Empfänger)
	 * Zeile 3: Nachricht
	 * Zeile 4: Versands-Datum
	 * @return
	 * Gibt bei erfolg true zurück
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
	 * Methode: read
	 * User werden aus den Files gelesen und instanziert. Dazugehoerige Messages instanziert und zugewiesen.
	 * Die einzelnen Aktionen sind in den Methoden reviveAllUsers und reviveAllMessagesOfUser implementiert.
	 * @return
	 * Gibt alle User in Form eine Hashmap zurück. Das Schlüsselwort um einen User in der Hashmap zu finden ist der username
	 */
	public HashMap<String, User> read() {
		reviveAllUsers();
		for (String userName : allUserNameList) {
			User currentUser = allUsersListMap.get(userName);
			reviveAllMessagesOfUser(currentUser);
		}
		return allUsersListMap;
	}

	/**
	 * Methode: reviveAllUsers
	 * Alle User die in einem File gespeichert sind, werden instanziert
	 * @return
	 * Gibt alle User in Form eine Hashmap zurück. Das Schlüsselwort um einen User in der Hashmap zu finden ist der username
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
			User newUser = new User(singleUser.get(0), singleUser.get(1),
					singleUser.get(2), singleUser.get(3), singleUser.get(4));
			allUsersListMap.put(newUser.getUserName(), newUser);
			allUserNameList.add(newUser.getUserName());
		}
		return allUsersListMap;
	}

	/**
	 * List ein einzelnes File und füllt ein Array ab. Jede Zeile ist ein Eintrag im Array
	 * @param fileName
	 * @return
	 * Array mit allen Zeile des Files
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return singeFileContent;
	}

	/**
	 * Instanziert alle Message Objecte eines users und fuegt diese der Inbox hinzu.
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

			if (fileName.contains("SMS")) {
				SMS newSMS = new SMS(user,
						returnUserObjectFromUserName(singleMessage.get(1)),
						singleMessage.get(2), singleMessage.get(3));
				user.addSMS(newSMS);
			}
			if (fileName.contains("Mail")) {
				Mail newMail = new Mail(user,
						returnUserObjectFromUserName(singleMessage.get(1)),
						singleMessage.get(2), singleMessage.get(3));
				user.addMail(newMail);
			}
			if (fileName.contains("Print")) {
				Print newPrint = new Print(user,
						returnUserObjectFromUserName(singleMessage.get(1)),
						singleMessage.get(2), singleMessage.get(3));
				user.addPrint(newPrint);
			}
		}
	}

	/**
	 * Diese Methode stellen den Link zwichen Map und Array dar. Da eine Map nicht Iterierbar ist, ist dies notwendig
	 * @param userName
	 * @return
	 */
	User returnUserObjectFromUserName(String userName) {
		return allUsersListMap.get(userName);
	}

	/**
	 * Löscht einen User
	 * @param user
	 * @return
	 */
	public boolean delete(User user) {
		File f = new File(generateFileNameUser(user));
		return f.delete();
		//TODO: Muss noch aus dem Array/Map entfernt werden
		
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
		return getDirectoryName() + getUserPrefix() + user.getUserName()
				+ ".txt";
	}

	private String generateFileNameMessage(User user, Message message) {
		return getDirectoryName() + getMessagePrefix() + user.getUserName()
				+ "_" + message.getClass() + "_" + message.getDate() + "_"
				+ message.getId() + ".txt";
	}

	private String getDirectoryName() {
		String dirName = "workding_dir/";
		File dir = new File(dirName);
		dir.mkdir();
		return dirName;
	}

	private String getMessagePrefix() {
		String messagePrefix = "message_";
		return messagePrefix;
	}

	private String getUserPrefix() {
		String messagePrefix = "user_";
		return messagePrefix;
	}

	// getter und setters
	public HashMap<String, User> getAllUsersListMap() {
		return allUsersListMap;
	}

	public ArrayList<String> getAllUserNameList() {
		return allUserNameList;
	}
}
