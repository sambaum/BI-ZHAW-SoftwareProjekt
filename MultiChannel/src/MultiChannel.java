import java.util.Map;

/**
 * Abgabetermin:  Mi-12.06.2013
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
		System.out.println("Anzahl geladener User: " + storeuser.getUserMap().size());
	}
	
	private void printUsers(){
		for (Map.Entry<String, User> entry : storeuser.getUserMap().entrySet()){
			System.out.println("- " + entry.getValue().getUserName() + " (Inbox: " + calculateTotalMessageCount(entry.getKey()) + " Messages)");
		}
	}
	
	private int calculateTotalMessageCount(String user){
		return storeuser.getUserMap().get(user).getSmsBox().size() + storeuser.getUserMap().get(user).getMailBox().size() + storeuser.getUserMap().get(user).getPrintBox().size();
	}
}
