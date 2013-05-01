/**
 * Dieses Interface schreibt die Struktur aller Klassen die Files lesen, schreiben und loeschen
 * @author Samuel
 * 
 */

public interface Storage {
	 boolean write(Storable objectToWrite);
	 boolean read(Storable objectToRead);
	 boolean delete(Storable objectToDelete);
}
