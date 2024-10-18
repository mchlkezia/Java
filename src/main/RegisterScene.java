package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;

public class RegisterScene {
	
	BorderPane bp;
	GridPane gp;
	Label lblEmail, lblPassword, lblConfirm, lblAge, lblGender, lblNationality;
	TextField tfEmail;
	PasswordField tfPassword, tfConfirm;
	Button btnRegister;
	Menu menu;
	MenuBar menuBar;
	MenuItem menuItemLogin, menuItemRegister;
	RadioButton maleRadio, femaleRadio;
	ToggleGroup genderGroup;
	ComboBox<String> countryBox;
	Spinner<Integer> ageSpinner;
	private Connect connect = Connect.getInstance();
    private ChangeScene changeScene;
    
    public Scene thisScene() {
    	bp = new BorderPane();
    	gp = new GridPane();
    	lblEmail = new Label("Email");
    	lblPassword = new Label("Password");
    	lblConfirm = new Label("Confirm Password");
    	lblAge = new Label("Age");
    	lblGender = new Label("Gender");
    	lblNationality = new Label("Nationality");
    	tfEmail = new TextField();
    	tfPassword = new PasswordField();
    	tfConfirm = new PasswordField();
    	ageSpinner = new Spinner<>();
    	maleRadio = new RadioButton("Male");
    	femaleRadio = new RadioButton("Female");
    	genderGroup = new ToggleGroup();
    	maleRadio.setToggleGroup(genderGroup);
    	maleRadio.setSelected(true);
    	femaleRadio.setToggleGroup(genderGroup);
    	btnRegister = new Button("Register");
    	menuBar = new MenuBar();
		menu = new Menu("Page");
		menuItemLogin = new MenuItem("Login");
		menuItemRegister = new MenuItem("Register");
		menu.getItems().addAll(menuItemLogin, menuItemRegister);
		menuBar.getMenus().add(menu);
		countryBox = new ComboBox<>();
    	countryBox.getItems().add("Indonesia");
    	countryBox.getItems().add("Singapore");
    	countryBox.getItems().add("America");
    	countryBox.getSelectionModel().selectFirst();
    	SpinnerValueFactory<Integer> ageSpinnerVal = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
    	ageSpinner.setValueFactory(ageSpinnerVal);
    	
    	gp.add(lblEmail, 0, 0);
        gp.add(tfEmail, 0, 1);
        gp.add(lblPassword, 0, 2);
        gp.add(tfPassword, 0, 3);
        gp.add(lblConfirm, 0, 4);
        gp.add(tfConfirm, 0, 5);
        gp.add(lblAge, 0, 6);
        gp.add(ageSpinner, 0, 7);
        gp.add(lblGender, 1, 0);
        gp.add(maleRadio, 1, 1);
        gp.add(femaleRadio, 1, 2);
        gp.add(lblNationality, 1, 3);
        gp.add(countryBox, 1, 4);
        gp.add(btnRegister, 1, 5);
    	
    	bp.setTop(menuBar);
    	bp.setCenter(gp);
    	gp.setHgap(10);
    	gp.setVgap(10);
    	gp.setAlignment(Pos.CENTER);    	
    	
    	btnRegister.setOnAction(e -> {
    		forRegister();
    		changeScene.loginScene();				
        });
    	
        menuItemLogin.setOnAction(e -> {
        	changeScene.loginScene();
        });
        
        menuItemRegister.setOnAction(e -> {
        	changeScene.registerScene();
        });
        
        return new Scene(bp, 888, 888);
    }
    
	public void forRegister() {
		try {
			String UserID = "";
			String email = tfEmail.getText();
			String password = tfPassword.getText();
			String confirmPassword = tfConfirm.getText();
			Integer age = ageSpinner.getValue();
			String gender = "";
			String nationality = countryBox.getValue();
			int userCount = userCount();
			UserID = "UA" + String.format("%03d", userCount + 1);
			
			if (emailExist(email)) {
				showAlert("Email is already registered.");
				return;
			}
			
			if (!email.endsWith("@gmail.com")) {
				showAlert("Email must end with '@gmail.com'.");
				return;
			}
			
			if (password.length() < 6) {
				showAlert("Password must contain minimum 6 characters");
				return;
			}
			
			if (!password.equals(confirmPassword)) {
				showAlert("Confirm Password must be the same as Password");
				return;
			}
			
			if (age < 1) {
				showAlert("age must be greater than 0");
				return;
			}
			
			if (nationality.isEmpty()) {
				showAlert("Nationality must be selected");
				return;
			}
			
			String Role = "User";
			String query = "INSERT INTO msuser " + " VALUES " + "('" + UserID + "', '" + email + "', '" + password
					+ "', " + "'" + age + "', '" + gender + "'," + "'" + nationality + "', '" + Role + "')";
			connect.executeUpdate(query);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Registration Successful");
			alert.setContentText("You will be redirected to the login page");
			alert.show();
			
			alert.setOnCloseRequest(event -> {
				tfEmail.clear();
				tfPassword.clear();
				tfConfirm.clear();
				ageSpinner.getValueFactory().setValue(0);
				genderGroup.selectToggle(null);
				countryBox.getSelectionModel().clearSelection();
			});
			
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean emailExist(String email) {
		try {
			String query = "SELECT * FROM msuser WHERE UserEmail = '" + email + "'";
			ResultSet resultSet = connect.execQuery(query);

			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int userCount() {
		int userCount = 0;
		
		try {
			String query = "SELECT COUNT(*) AS count FROM msuser";
			ResultSet resultSet = connect.execQuery(query);
			
			if (resultSet.next()) {
				userCount = resultSet.getInt("count");
			}
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userCount;
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setContentText(message);		
		alert.showAndWait();
	}
    
    public RegisterScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }
    
}
