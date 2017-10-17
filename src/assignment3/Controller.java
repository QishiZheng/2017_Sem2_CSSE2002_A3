package assignment3;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller class for the Character Editor.
 * @author leggy (Lachlan Healey)
 */
public class Controller implements Initializable{
	
	private Model model;
	private ObservableList<String> names = FXCollections.observableArrayList();
	
	@FXML
	private Button btnLoad;
	@FXML
	private Button btnSave;
	@FXML
	private TextField TextBoxFileName;
	@FXML
	private Button btnCreateFile;
	@FXML
	private TextField TextBoxCharName;
	@FXML
	private Button btnSearchChar;
	@FXML
	private Button btnDeleteChar;
	@FXML
	private ListView<String> charList;
	@FXML 
	private TextField TextBoxCreateCharName;
	@FXML
	private TextField TextBoxCreateCharDesc;
	@FXML
	private TextField TextBoxCreateCharPwrLevel;
	@FXML
	private Button btnCreateNChar;
	@FXML
	private Button btnCreateSChar;
	@FXML
	private Label labelCharName;
	@FXML
	private ImageView charImg;
	@FXML 
	private Button btnChangeImg;
	@FXML 
	private TextField charDesc;
	@FXML 
	private TextField charTraits;
	@FXML 
	private TextField charPowerLevel;
	@FXML 
	private TextField charSuperPowers;
	@FXML 
	private Button btnUpdateChar;
	
	public Controller() {
		this.model = new Model();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		handleLoadAction();
		handleSaveAction();
		//handleCreateDBAction();
		handleSearchAction();
		handleDeleteAction();
		handleCreateNCharAction();
		handelCreateSCharAction();
		handleChangeImgAction();
		handleUpdateCharAction();
	}

	private void handleLoadAction() {
		btnLoad.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				try {
					for (String name:model.loadDB()) {
						if(!(names.contains(name)) && !(name.isEmpty())) {
							names.add(name);
						}
					}
					charList.setItems(names);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void handleSaveAction() {
		btnSave.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				try {
					model.saveDB();
					System.out.println("after save:" + model.getDB().toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	
	private void handleSearchAction() {
		btnSearchChar.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
	}
	
	private void handleDeleteAction() {
		btnDeleteChar.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				String seletedName = charList.getSelectionModel().getSelectedItem();
				model.deleteChar(seletedName);
				names.remove(seletedName);
				charList.setItems(names);
				System.out.println("After deletion"+model.getDB().toString());
			}
		});
		
	}
	
	private void handleCreateNCharAction() {
		btnCreateNChar.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				String charName = TextBoxCreateCharName.getText();
				String charDesc = TextBoxCreateCharDesc.getText();
				Character newChar = new Character(charName, charDesc);
				model.addNormalChar(newChar);
				names.add(charName);
				charList.setItems(names);
				TextBoxCreateCharName.clear();
				TextBoxCreateCharDesc.clear();
			}
		});
	}
	
	public void handelCreateSCharAction() {
		btnCreateSChar.setOnAction(new EventHandler<ActionEvent>()  {
			@Override
			public void handle(ActionEvent event) {
				String charName = TextBoxCreateCharName.getText();
				String charDesc = TextBoxCreateCharDesc.getText();
				int charPwrLv = Integer.parseInt(TextBoxCreateCharPwrLevel.getText());
				SuperCharacter newChar;
				try {
					newChar = new SuperCharacter(charName, charDesc, charPwrLv);
					model.addNormalChar(newChar);
					names.add(charName);
					charList.setItems(names);
				} catch (IllegalPowerRankingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				TextBoxCreateCharName.clear();
				TextBoxCreateCharDesc.clear();
				TextBoxCreateCharPwrLevel.clear();
			}
		});
	}
	
	public void handleChangeImgAction() {
		
	}
	
	public void handleUpdateCharAction() {
		
	}
	
	
//	private void handleCreateDBAction() {
//		btnCreateFile.setOnAction(new EventHandler<ActionEvent>()  {
//			@Override
//			public void handle(ActionEvent event) {
//				try {
//					
//					ObservableList<String> names = FXCollections.observableArrayList();
//					for (String name:model.createDB(TextBoxFileName.getText())) {
//						names.add(name);
//					}
//					charList.setItems(names);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	

}
