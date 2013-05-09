import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Dies ist das Haupt-Programm mit der Main-Methode
 * 
 * @author Samuel
 * 
 */

public class MultiChannel {
	
	private static MultiChannel multiChannel;
	private StoreUser storeuser;

	public static void main(String[] args) {
		System.out.println("Willkommen bei Multichannel");
		multiChannel = new MultiChannel();
		multiChannel.loadStoredUsers();
	}
	
	private void loadStoredUsers(){
		System.out.println("Gespeicherte User werden geladen...");
		storeuser = new StoreUser();
		storeuser.read();
		printUsers();
		System.out.println("Anzahl geladener User: " + storeuser.getAllUsersListMap().size());
	}
	
	private void printUsers(){
		for (Map.Entry<String, User> entry : storeuser.getAllUsersListMap().entrySet()){
			System.out.println("- " + entry.getValue().getUserName() + " (Inbox: " + calculateTotalMessageCount(entry.getKey()) + " Messages)");
		}
	}
	
	private int calculateTotalMessageCount(String user){
		return storeuser.getAllUsersListMap().get(user).getSmsBox().size() + storeuser.getAllUsersListMap().get(user).getMailBox().size() + storeuser.getAllUsersListMap().get(user).getPrintBox().size();
	}
}