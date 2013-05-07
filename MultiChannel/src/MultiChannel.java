import java.util.ArrayList;
import java.util.HashMap;

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
		for(String user : storeuser.getAllUserNameList()){
			System.out.println("- " + storeuser.getAllUsersListMap().get(user).getUserName() + " (Inbox: " + calculateTotalMessageCount(storeuser.getAllUsersListMap().get(user).getUserName()) + " Messages)");
		}
	}
	
	private int calculateTotalMessageCount(String user){
		return storeuser.getAllUsersListMap().get(user).getSmsBox().size() + storeuser.getAllUsersListMap().get(user).getMailBox().size() + storeuser.getAllUsersListMap().get(user).getPrintBox().size();
	}
}
