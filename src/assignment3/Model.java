package assignment3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Model class for the Character Editor
 * @author leggy (Lachlan Healey)
 */
public class Model {
	private CharacterDatabase db;
	/**
	 * Contructs an new CharacterDatabase with file "database1.dat" 
	 */
	public Model() {
		db  = new CharacterDatabase("database1.dat");
	}
	
	/**
	 * Creates a new database file with the given file name
	 * @param dbName given file name 
	 */
	public void createNewDB(String dbName) {
		db = new CharacterDatabase(dbName);
	}
	/**
	 * Returns this database.
	 * @return this database.
	 */
	public CharacterDatabase getDB() {
		return db;
	}
	
	/**
	 * Load the database with default data file, and returns a list of Character names.
	 * @return list of Character names.
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public List<String> loadDB() throws FileNotFoundException, Exception {
		db.load();
		return db.getCharacterNames();
	}

	/**
	 * Save the database to database file
	 * @throws IOException
	 */
	public void saveDB() throws IOException {
		db.save();
	}
	
	/**
	 * Adds a new normal character to database.
	 * @param nc, normal character.
	 */
	public void addNormalChar(Character nc) {
		db.add(nc);
	}
	
	/**
	 * Adds a new super character to database.
	 * @param nc, super character.
	 */
	public void addSuperChar(SuperCharacter sc) {
		db.add(sc);
	}
	
	/**
	 * Deletes the character with the given name for database
	 * @param name, character name to be deleted.
	 */
	public void deleteChar(String name) {
		if (name!= null ) {
			db.remove(db.search(name));
		}
	}
	
	/**
	 * Find and return a clone of character with given character name.
	 * @param name, given character name for searching
	 * @return a clone of the character with given name
	 */
	public Character search(String name) {
		return db.search(name);
	}
	
	/**
	 * Change image of this character with the given new image path
	 * @param c, the character needs to change image
	 * @param imagePath, the path of new image
	 */
	public void changeImage(Character c, String imagePath) {
		c.setImagePath(imagePath);
	}
	
	/**
	 * Updates the given character 
	 * @param c, given character needs to be updated
	 */
	public void updateChar(Character c) {
		if (db.search(c.name) != null) {
			db.remove(c);
			db.add(c);
		}
	}
}
