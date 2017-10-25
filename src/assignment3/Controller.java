package assignment3;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Controller class for the Character Editor.
 * 
 * @author leggy (Lachlan Healey)
 */
public class Controller implements Initializable {

	private Model model;
	private ObservableList<String> names;
	private ObservableList<String> traits;
	private ObservableList<String> powers;
	private String imgPath;
	private Alert alert;

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
	private ListView<String> charTraits;
	@FXML
	private TextField newTrait;
	@FXML
	private Button btnAddTrait;
	@FXML
	private Button btnDeleteTrait;
	@FXML
	private TextField charPowerLevel;
	@FXML
	private ListView<String> charSuperPowers;
	@FXML
	private TextField newPower;
	@FXML
	private Button btnAddPower;
	@FXML
	private Button btnDeletePower;
	@FXML
	private Button btnUpdateChar;

	public Controller() {
		this.model = new Model();
		names = FXCollections.observableArrayList();
		traits = FXCollections.observableArrayList();
		powers = FXCollections.observableArrayList();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		handleLoadAction();
		handleSaveAction();
		handleCreateDBAction();
		handleSearchAction();
		handleDeleteCharAction();
		handleCreateNCharAction();
		handelCreateSCharAction();
		handleChangeImgAction();
		handleAddTraitAction();
		handleDeleteTraitAction();
		handleAddPowerAction();
		handleDeletePowerAction();
		handleUpdateCharAction();
		showSeletedChar();
	}

	/**
	 * load default database and display all character in list view
	 */
	private void handleLoadAction() {
		btnLoad.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (!model.loadDB().isEmpty()) {
						for (String name : model.loadDB()) {
							if (!(names.contains(name)) && !(name.isEmpty())) {
								names.add(name);
							}
						}
						charList.setItems(names);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * save all changes to database file
	 */
	private void handleSaveAction() {
		btnSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					model.saveDB();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * create a new empty db and clear character list view
	 */
	private void handleCreateDBAction() {
		btnCreateFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					model.createNewDB(TextBoxFileName.getText() + ".dat");
					names.clear();
					charList.setItems(names);
					TextBoxFileName.clear();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * search the name given in search box and display character info if found
	 * in database
	 */
	private void handleSearchAction() {
		btnSearchChar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String searchName = TextBoxCharName.getText();
				Character selectedChar = model.search(searchName);
				if (selectedChar != null) {
					labelCharName.setText(selectedChar.getName());
					charImg.setImage(new Image(selectedChar.getImagePath()));
					charDesc.setText(selectedChar.getDescription());

					traits.clear();
					for (String trait : selectedChar.getTraits()) {
						traits.add(trait);
					}
					charTraits.setItems(traits);
					if (selectedChar instanceof SuperCharacter) {
						charPowerLevel.setText(((SuperCharacter) selectedChar).getPowerRanking() + "");

						powers.clear();
						for (String power : ((SuperCharacter) selectedChar).getPowers()) {
							powers.add(power);
						}
						charSuperPowers.setItems(powers);

					} else {
						charPowerLevel.setText("0");

						powers.clear();
						charSuperPowers.setItems(powers);
					}
					TextBoxCharName.clear();
				} else {
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Character With name " + searchName + " Was Not Found!");
					alert.setContentText("Please try other names.");

					alert.showAndWait();
				}
			}
		});

	}

	/**
	 * delete selected character in listView from database
	 */
	private void handleDeleteCharAction() {
		btnDeleteChar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String selectedName = charList.getSelectionModel().getSelectedItem();
				if (selectedName == null) {
					alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Please Select a Character to delete!");

					alert.showAndWait();
				}
				model.deleteChar(selectedName);
				names.remove(selectedName);
				charList.setItems(names);
			}
		});

	}

	/**
	 * Create and add a normal character to database
	 */
	private void handleCreateNCharAction() {
		btnCreateNChar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String charName = TextBoxCreateCharName.getText();
				String charDesc = TextBoxCreateCharDesc.getText();
				if (charName.isEmpty() || charName == null) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Character Name Cannot Be Null or Empty!");
					alert.setContentText("Please enter a valid name!");

					alert.showAndWait();
				} else if (charDesc.isEmpty() || charDesc == null) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Character Description Cannot Be Null or Empty!");
					alert.setContentText("Please enter a valid description!");

					alert.showAndWait();
				} else if (!TextBoxCreateCharPwrLevel.getText().isEmpty()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Power Level Should Not Be Entered!");
					alert.setContentText("You are creating a normal character! Please leave Power Level empty!");

					alert.showAndWait();
				} else {
					Character newChar = new Character(charName, charDesc);
					model.addNormalChar(newChar);
					names.add(charName);
					charList.setItems(names);
					TextBoxCreateCharName.clear();
					TextBoxCreateCharDesc.clear();
				}

			}
		});
	}

	/**
	 * Create and add a super character to database
	 */
	private void handelCreateSCharAction() {
		btnCreateSChar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String charName = TextBoxCreateCharName.getText();
				String charDesc = TextBoxCreateCharDesc.getText();

				if (TextBoxCreateCharPwrLevel.getText().isEmpty() || TextBoxCreateCharPwrLevel.getText() == null
						|| !(isInt(TextBoxCreateCharPwrLevel.getText()))) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Super Character Power Level Cannot be empty!");
					alert.setContentText("Please enter a Power Level between 0 and 10!");

					alert.showAndWait();
				} else {
					int charPwrLv = Integer.parseInt(TextBoxCreateCharPwrLevel.getText());
					SuperCharacter newChar;
					try {
						if (charName.isEmpty() || charName == null) {
							alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Character Name Cannot Be Null or Empty!");
							alert.setContentText("Please enter a valid name!");

							alert.showAndWait();
						} else if (charDesc.isEmpty() || charDesc == null) {
							alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setHeaderText("Character Description Cannot Be Null or Empty!");
							alert.setContentText("Please enter a valid description!");

							alert.showAndWait();
						}

						newChar = new SuperCharacter(charName, charDesc, charPwrLv);
						model.addNormalChar(newChar);
						names.add(charName);
						charList.setItems(names);
						TextBoxCreateCharName.clear();
						TextBoxCreateCharDesc.clear();
						TextBoxCreateCharPwrLevel.clear();
					} catch (IllegalPowerRankingException e) {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Super Character Power Level Invalid!");
						alert.setContentText("Please enter a Power Level between 0 and 10!");

						alert.showAndWait();
					}

				}
			}
		});
	}

	/**
	 * Add trait to this character
	 */
	private void handleAddTraitAction() {
		btnAddTrait.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String trait = newTrait.getText();
				if (trait.isEmpty() || trait == null) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Character Trait Cannot Be Null or Empty!");
					alert.setContentText("Please enter a valid Trait!");

					alert.showAndWait();
				} else {
					traits.add(trait);
					charTraits.setItems(traits);
					newTrait.clear();
				}
			}
		});
	}

	/**
	 * delete selected trait
	 */
	private void handleDeleteTraitAction() {
		btnDeleteTrait.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String trait = charTraits.getSelectionModel().getSelectedItem();
				if (trait == null) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Trait Cannot Be Null!");
					alert.setContentText("Pelease Select A Trait To Delete!");

					alert.showAndWait();
				} else {
					traits.remove(trait);
					charTraits.setItems(traits);
				}
			}
		});
	}

	/**
	 * add power to this character
	 */
	private void handleAddPowerAction() {
		btnAddPower.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String power = newPower.getText();
				if (power.isEmpty() || power == null) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Character Super Power Cannot Be Null or Empty!");
					alert.setContentText("Please enter a valid Super Power!");

					alert.showAndWait();
				} else {
					powers.add(power);
					charSuperPowers.setItems(powers);
					newPower.clear();
				}
			}
		});
	}

	/**
	 * Delete selected power from this character
	 */
	private void handleDeletePowerAction() {
		btnDeletePower.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String power = charSuperPowers.getSelectionModel().getSelectedItem();
				if (power == null) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Super Power Cannot Be Null!");
					alert.setContentText("Pelease Select A Super Power To Delete!");

					alert.showAndWait();
				} else {
					powers.remove(power);
					charSuperPowers.setItems(powers);
				}
			}
		});
	}

	/**
	 * change image of this character using file chooser
	 */
	private void handleChangeImgAction() {
		btnChangeImg.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Image File");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
				File selectedFile = fileChooser.showOpenDialog(new Stage());
				if (selectedFile != null) {
					// imgPath = selectedFile.toURI().toString();
					Image selectedImage = new Image(selectedFile.toURI().toString());
					charImg.setImage(selectedImage);

					imgPath = selectedFile.toURI().toString();
				}
			}
		});
	}

	/**
	 * update all changes user made to this character
	 */
	private void handleUpdateCharAction() {
		btnUpdateChar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// create a new normal with user entered details if the
				// character is a normal character
				if (!(model.search(labelCharName.getText()) instanceof SuperCharacter)) {
					if (!charPowerLevel.getText().isEmpty()) {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Power Level And Power Should Not Be Entered!");
						alert.setContentText(
								"This is a normal character! Please leave Power Level and Super Power empty!");

						alert.showAndWait();
						charPowerLevel.clear();
						powers.clear();
						charSuperPowers.setItems(powers);
					}

					Character charToUpdate = new Character(labelCharName.getText(), charDesc.getText());
					for (String trait : traits) {
						charToUpdate.addTrait(trait);
					}
					charToUpdate.setImagePath(imgPath);
					model.updateChar(charToUpdate);
				} else {
					// creates a new super character with user entered details
					try {
						SuperCharacter superCharToUpdate = new SuperCharacter(labelCharName.getText(),
								charDesc.getText(), Integer.parseInt(charPowerLevel.getText()));
						// add all traits
						for (String trait : traits) {
							superCharToUpdate.addTrait(trait);
						}

						superCharToUpdate.setImagePath(imgPath);

						// adds all powers
						for (String power : powers) {
							superCharToUpdate.addPower(power);
						}
						model.updateChar(superCharToUpdate);
					} catch (NumberFormatException | IllegalPowerRankingException e) {
						alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Super Character Power Level Invalid!");
						alert.setContentText("Please enter a Power Level between 0 and 10!");

						alert.showAndWait();
					}
				}

			}
		});
	}

	/**
	 * Shows selected Character information at right-hand side.
	 */
	private void showSeletedChar() {
		charList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldName, String newName) {
				Character selectedChar = model.search(newName);
				if (selectedChar != null) {
					String[] charInfo = selectedChar.toString().split(System.getProperty("line.separator"));
					labelCharName.setText(charInfo[0]);
					charImg.setImage(new Image(selectedChar.getImagePath()));
					charDesc.setText(charInfo[1]);

					imgPath = selectedChar.getImagePath();

					traits.clear();
					for (String trait : selectedChar.getTraits()) {
						traits.add(trait);
					}
					charTraits.setItems(traits);
					if (selectedChar instanceof SuperCharacter) {
						charPowerLevel.setText(((SuperCharacter) selectedChar).getPowerRanking() + "");

						powers.clear();
						for (String power : ((SuperCharacter) selectedChar).getPowers()) {
							powers.add(power);
						}
						charSuperPowers.setItems(powers);

					} else {
						charPowerLevel.setText("");

						powers.clear();
						charSuperPowers.setItems(null);
					}
				}
				// System.out.println("Selected char: " + newName);
			}
		});
	}

	/**
	 * Check if given string is a integer.
	 * 
	 * @param s,
	 *            given string.
	 * @return true if the string can be parsed to int.
	 */
	private boolean isInt(String s) {
		try {
			int i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
