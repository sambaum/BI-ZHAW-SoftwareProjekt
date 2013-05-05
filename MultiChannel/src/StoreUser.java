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
 * ab. Es werden die dazugehörigen Messages ebenfalls abgespeichert. Alles Files
 * sind in einem Text-Format und können mit einem normalen Text-Editor geöffnet
 * und selber editiert werden.
 * 
 * Diese Klasse liest verhandene Files und erstellt daraus Objekte. Die Files
 * haben folgende Namens-konvention: message_[Message-Typ]_user_[Sende-Datum]
 * Beispiel für einen File-Name eines SMS von einem User Frodo:
 * message_sms_frodo_20130501_235526
 * 
 * @author Samuel
 * 
 */
public class StoreUser {

	//Variable welche nach dem Einlesen eine Liste von allen eingelesenen Usern enthält. Zu erkennen mit dem userNamen
	private HashMap<String, User> AllUsersListMap;
	private ArrayList<String> AllUserNameList;
	
	public StoreUser() {
		this.AllUsersListMap = new HashMap<String, User>();
		this.AllUserNameList = new ArrayList<String>();
	}

	//User wird in ein File gespeichert. Jedes Feld ist eine Zeile
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

	//Message wird in ein File gespeichert. Jedes Feld ist eine Zeile
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

	//User werden gelesen und instanziert, dazugehörige Messages instanziert und zugewiesen.
	public boolean read() {
		reviveAllUsers();
		
		for (String userName: AllUserNameList){
			User currentUser = AllUsersListMap.get(userName);
			reviveAllMessagesOfUser(currentUser);
		}		
		return true;
	}

	private void reviveAllUsers() {
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
			AllUsersListMap.put(newUser.getUserName(), newUser);
			AllUserNameList.add(newUser.getUserName());
		}
	}

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
				SMS newSMS = new SMS(user, returnUserObjectFromUserName(singleMessage.get(1)), singleMessage.get(2), singleMessage.get(3));
				user.addSMS(newSMS);
			}
			if (fileName.contains("Mail")) {
				Mail newMail = new Mail(user, returnUserObjectFromUserName(singleMessage.get(1)), singleMessage.get(2), singleMessage.get(3));
				user.addMail(newMail);
			}
			if (fileName.contains("Print")) {
				Print newPrint = new Print(user, returnUserObjectFromUserName(singleMessage.get(1)), singleMessage.get(2), singleMessage.get(3));
				user.addPrint(newPrint);
			}
		}
	}
	
	User returnUserObjectFromUserName(String userName){
		return AllUsersListMap.get(userName);
	}

	public boolean delete(User user) {
		File f = new File(generateFileNameUser(user));
		return f.delete();
	}
	
//	public boolean cleanDirectory() {
//		File dir = new File(getDirectoryName());
//		if (dir.isDirectory()){
//			return dir.delete();
//			}
//		else{
//			return false;
//		}
//	}

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
}
