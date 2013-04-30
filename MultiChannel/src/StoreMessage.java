/**
 * Argumente (Input): Objekt des Typs Message
 * Return-Werte (Output): boolean
 * 
 * Diese Klasse nimmt StoreMessage Objekte entgegen und speichert diese in einem File ab.
 * Diese Klasse liest verhandene Files und erstellt daraus Objekte
 * Die Files haben folgende Namens-konvention:
 * message_[Message-Typ]_user_[Sende-Datum]
 * Beispiel für einen File-Name eines SMS von einem User Frodo:
 * message_sms_frodo_20130501_235526
 * 
 * @author Samuel
 * 
 */
public class StoreMessage extends Storable implements Storage{

	@Override
	public void write(Storable objectToWrite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void read(Storable objectToRead) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Storable objectToDelete) {
		// TODO Auto-generated method stub
		
	}

}
