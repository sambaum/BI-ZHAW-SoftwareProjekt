/**
 * Dieses Interface schreibt die Struktur aller Klassen die Files lesen, schreiben und loeschen
 * @author Samuel
 * 
 */

public interface Storage {
	 void write(Storable objectToWrite);
	 void read(Storable objectToRead);
	 void delete(Storable objectToDelete);
}
