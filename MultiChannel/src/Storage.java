/**
 * Dieses Interface schreibt die Struktur aller Klassen die Files lesen, schreiben und loeschen
 * @author Samuel
 * 
 */

public interface Storage {
	abstract void write(Storable objectToWrite);
	abstract void read(Storable objectToRead);
	abstract void delete(Storable objectToDelete);
}
