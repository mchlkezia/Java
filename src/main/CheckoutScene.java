package main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CheckoutScene {
	
	BorderPane bp;
	GridPane gp, gp1;
	Label lblHeader, lblList, lblProduct1, lblProduct2, lblProduct3, lblCourier, lblInsurance, lblTotalPrice;
	ComboBox<String> comboBox;
	CheckBox checkBox;
	Button btnCheckout;
	private ChangeScene sceneManager;
	
	public Scene thisScene() {
		bp = new BorderPane();
		gp = new GridPane();
		gp1 = new GridPane();
		lblHeader = new Label("Transaction Card");
		lblList = new Label("List");
		lblProduct1 = new Label("Astrox 99 : 50000000");
		lblProduct2 = new Label("Thruster HMR : 7500000");
		lblProduct3 = new Label("Tectonic 7 : 4400000");
		lblCourier = new Label("Courier");
		comboBox = new ComboBox<>();
		comboBox.getItems().addAll("J&E", "SiCepat", "Wahana", "TIKI");
		comboBox.getSelectionModel().selectFirst();
		checkBox = new CheckBox();
		lblInsurance = new Label("Use Insurance");
		lblTotalPrice = new Label("Total Price : 61900000");
		btnCheckout = new Button("Checkout");
		
    	gp1.add(lblHeader, 0, 0);
    	gp.add(lblList, 0, 1);
    	gp.add(lblProduct1, 0, 2);
    	gp.add(lblProduct2, 0, 3);
    	gp.add(lblProduct3, 0, 4);
    	gp.add(lblCourier, 0, 5);
    	gp.add(comboBox, 0, 6);
    	gp.add(checkBox, 0, 7);
    	gp.add(lblInsurance, 1, 7);
    	gp.add(lblTotalPrice, 0, 9);
    	gp.add(btnCheckout, 0, 10);
		
    	gp1.setAlignment(Pos.CENTER);
    	bp.setTop(gp1);
    	gp.setAlignment(Pos.CENTER);
    	bp.setCenter(gp);
    	gp.setHgap(10);
    	gp.setVgap(10);
    	
    	HBox hbox1 = new HBox(5);
        hbox1.getChildren().addAll(checkBox, lblInsurance);
        hbox1.setAlignment(Pos.CENTER);

        VBox vbox1 = new VBox();
        vbox1.getChildren().addAll(lblProduct1, lblProduct2, lblProduct3);
        vbox1.setAlignment(Pos.CENTER);

        VBox vbox2 = new VBox(15);
        vbox2.getChildren().addAll(lblCourier, comboBox);
        vbox2.setAlignment(Pos.CENTER);

        VBox vbox3 = new VBox(15);
        vbox3.getChildren().addAll(lblList, vbox1);
        vbox3.setAlignment(Pos.CENTER);

        VBox vbox4 = new VBox(15);
        vbox4.getChildren().addAll(hbox1, lblTotalPrice, btnCheckout);
        vbox4.setAlignment(Pos.CENTER);

        VBox vbox5 = new VBox(15);
        vbox5.getChildren().addAll(vbox3, vbox2, vbox4);
        vbox5.setAlignment(Pos.CENTER);

        gp.add(vbox5, 0, 0);
    	
        lblHeader.setFont(Font.font(15));
        lblList.setFont(Font.font(15));
        lblProduct1.setFont(Font.font(15));
        lblProduct2.setFont(Font.font(15));
        lblProduct3.setFont(Font.font(15));
        lblCourier.setFont(Font.font(15));
        lblInsurance.setFont(Font.font(15));
        lblTotalPrice.setFont(Font.font(15));
        btnCheckout.setPrefWidth(200);
        
        gp1.setStyle("-fx-background-color: #4a4e50\r\n;");
        lblHeader.setStyle("-fx-text-fill: #FFFFFF\r\n;");
        gp.setStyle("-fx-background-color: #81a5bb\r\n;");
        
    	btnCheckout.setOnAction(e -> {
    		if (validation()) {
    			sceneManager.cartScene();
    		}
    	});
    	
		return new Scene(bp, 888, 888);
	}
	
	private boolean validation() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setHeaderText("Are you sure want to Checkout all the item?");
	    alert.setContentText("Need Confirmation");
	    alert.show();
		
		return true;
	}
	
	public CheckoutScene(ChangeScene sceneManager) {
		this.sceneManager = sceneManager;
	}
	
}
