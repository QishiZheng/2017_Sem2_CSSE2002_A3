package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Model class for the Character Editor
 * @author leggy (Lachlan Healey)
 */
public class Model {
	private CharacterDatabase db;
	
	public Model() {
		db  = new CharacterDatabase("database1.dat");
	}
	
	public CharacterDatabase getDB() {
		return db;
	}
	
	public List<String> loadDB() throws FileNotFoundException, Exception {
		List<String> names = new ArrayList<String>();
		db.load();
		Scanner sc = new Scanner(db.toString());
		sc.nextLine();//read the first line from the text file
		while (sc.hasNext()) {
			String line = sc.nextLine();
			//System.out.println(line);
			names.add(line.trim());
			}
		return names;
	}

	public void saveDB() throws IOException {
		db.save();
	}
	
//	public void createDB(String dbName) throws FileNotFoundException, Exception {
//		File newFile = new File(dbName);
//		newFile.createNewFile();
//	}
	
	public void addNormalChar(Character nc) {
		db.add(nc);
	}
	
	public void addSuperChar(SuperCharacter sc) {
		db.add(sc);
	}
	
	public void deleteChar(String name) {
		db.remove(db.search(name));
	}
	
	public Character search(String name) {
		return db.search(name);
	}
	
	public void changeImage(Character c, String imagePath) {
		c.setImagePath(imagePath);
	}
	
	public void updateChar(Character c) {
		if (db.search(c.name) != null) {
			db.remove(c);
			db.add(c);
		}
	}
}
