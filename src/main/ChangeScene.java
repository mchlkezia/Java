package main;

import javafx.stage.Stage;

public class ChangeScene {
	
    private Stage primaryStage;
    
    public void registerScene() {
        RegisterScene registerScene = new RegisterScene(this);
        primaryStage.setScene(registerScene.thisScene());
        primaryStage.setTitle("Register");
    }
    
    public void loginScene() {
        LoginScene loginScene = new LoginScene(this);
        primaryStage.setScene(loginScene.thisScene());
    }
    
    public void homeScene() {
    	HomeScene homeScene = new HomeScene(this);
    	primaryStage.setScene(homeScene.thisScene());
    	primaryStage.setTitle("Home");
    }
    
    public void cartScene() {
    	CartScene cartScene = new CartScene(this);
    	primaryStage.setScene(cartScene.thisScene());
    	primaryStage.setTitle("Cart");
    }
    
    public void historyScene() {
    	HistoryScene historyScene = new HistoryScene(this);
    	primaryStage.setScene(historyScene.thisScene());
    	primaryStage.setTitle("History");
    }
    
    public void checkoutScene() {
    	CheckoutScene checkoutScene = new CheckoutScene(this);
    	primaryStage.setScene(checkoutScene.thisScene());
    	primaryStage.setTitle("");
    }

    public void viewHistoryScene() {
    	ViewHistoryScene viewHistoryScene = new ViewHistoryScene(this);
    	primaryStage.setScene(viewHistoryScene.thisScene());
    	primaryStage.setTitle("My History");
    }
    
    public void manageProductScene() {
    	ManageProductScene manageProductScene = new ManageProductScene(this);
    	primaryStage.setScene(manageProductScene.thisScene());
    	primaryStage.setTitle("Manage Product");
    }
    
    public Stage primaryStage() {
        return primaryStage;
    }
    
    public ChangeScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
}
