package main;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;

public class LoginScene {
	
	BorderPane bp;
	GridPane gp;
	Label lblEmail, lblPassword;
	TextField tfEmail;
	PasswordField tfPassword;
	Button btnLogin;
	Menu menu;
	MenuBar menuBar;
	MenuItem menuItemLogin, menuItemRegister;
	private Connect connect = Connect.getInstance();
    private ChangeScene changeScene;
    
    public Scene thisScene() {
        bp = new BorderPane();
        gp = new GridPane();
        lblEmail = new Label("Email");
		lblPassword = new Label("Password");
		tfEmail = new TextField();
		tfPassword = new PasswordField();
		btnLogin = new Button("Login");
		menuBar = new MenuBar();
		menu = new Menu("Page");
		menuItemLogin = new MenuItem("Login");
		menuItemRegister = new MenuItem("Register");
		menu.getItems().addAll(menuItemLogin, menuItemRegister);
		menuBar.getMenus().add(menu);
		
		gp.add(lblEmail, 0, 0);
        gp.add(tfEmail, 0, 1);
        gp.add(lblPassword, 0, 2);
        gp.add(tfPassword, 0, 3);
        gp.add(btnLogin, 0, 4);
		
		bp.setTop(menuBar);
		bp.setCenter(gp);
		gp.setVgap(10);
        gp.setHgap(10);
        gp.setAlignment(Pos.CENTER);
        
        tfEmail.setMinWidth(200);
        tfPassword.setMinWidth(200);
        btnLogin.setMaxWidth(100);
        
        btnLogin.setOnAction(e -> {
            if (validation()) {
            	changeScene.homeScene();
            }
        });
        
        menuItemLogin.setOnAction(e -> {
        	changeScene.loginScene();
        });
        
        menuItemRegister.setOnAction(e -> {
        	changeScene.registerScene();
        });
        
        return new Scene(bp, 888, 888);
    }
    
    private Role credentials(String email, String password) {
    	if (email.equals("admin@gmail.com") && password.equals("admin1234")) {
			return new Role("Admin");
		} else if (email.equals("boodi@gmail.com") && password.equals("user1234")) {
			return new Role("User");
		} else {
			return null;
		}
    }
    
	private boolean validation() {
    	Alert alert = new Alert(AlertType.WARNING);
	    alert.setHeaderText("Warning");
	    
	    String Email = tfEmail.getText();
	    String Pass = tfPassword.getText();
	    Role role = credentials(Email, Pass);
	    
	    if (tfEmail.getText().isEmpty() || tfPassword.getText().isEmpty()) {
	        alert.setContentText("Email or Password must be Filled");
	        alert.show();
	        return false;
	    }
	    
	    if (!tfEmail.getText().endsWith("@gmail.com")) {
			alert.setContentText("Email must end with @gmail.com");
		}
	    
	    if (role != null) {
			if (role.equals("Admin")) {
				changeScene.homeScene();
			} else if (role.equals("User")) {
				changeScene.manageProductScene();
			}
		}
	    
	    return true;
    }
	
    public LoginScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }
        
}
