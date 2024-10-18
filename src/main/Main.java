package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) {
        ChangeScene changeScene = new ChangeScene(primaryStage);
        changeScene.loginScene();
//        Untuk ke halaman Admin -> changeScene.manageProductScene();
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
