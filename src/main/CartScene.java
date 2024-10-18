package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CartScene implements EventHandler<ActionEvent> {
	
	BorderPane bp;
	GridPane gp, gp1;
	FlowPane fp;
	Label lblHeader, lblProductName, lblBrand, lblPrice, lblTotalPrice;
	Button btnCheckout, btnDelete;
	Menu menu;
	MenuBar menuBar;
	MenuItem menuItemHome, menuItemCart, menuItemHistory, menuItemLogout;
	TableView<Cart> cartTable;
	TableColumn<Cart, String> name, brand;
	TableColumn<Cart, Integer> price, quantity, total;
	private String id = null;
	private ObservableList<Cart> product;
	private Connect connect = Connect.getInstance();
	private ChangeScene sceneManager;
	
	public Scene thisScene() {
		bp = new BorderPane();
		gp = new GridPane();
		gp1 = new GridPane();
		fp = new FlowPane();
		lblHeader = new Label("Your Cart List");
    	lblProductName = new Label("Name		: ");
    	lblBrand = new Label("Brand		: ");
    	lblPrice = new Label("Price			: ");
    	lblTotalPrice = new Label("Total Price	: ");
    	cartTable = new TableView<>();
    	name = new TableColumn<>("Name");
    	brand = new TableColumn<>("Brand");
    	price = new TableColumn<>("Price");
    	quantity = new TableColumn<>("Quality");
    	total = new TableColumn<>("Total");
    	btnCheckout = new Button("Checkout");
    	btnDelete = new Button("Delete Product");
		menuBar = new MenuBar();
		menu = new Menu("Page");
		menuItemHome = new MenuItem("Home");
		menuItemCart = new MenuItem("Cart");
		menuItemHistory = new MenuItem("History");
		menuItemLogout = new MenuItem("Logout");
		menu.getItems().addAll(menuItemHome, menuItemCart, menuItemHistory, menuItemLogout);
		menuBar.getMenus().add(menu);
    	product = FXCollections.observableArrayList();
		
    	gp.add(lblHeader, 0, 0);
    	gp.add(cartTable, 0, 1);
    	gp.add(btnCheckout, 0, 2);
    	gp.add(btnDelete, 0, 3);
    	
    	gp1.add(lblProductName, 1, 2);
    	gp1.add(lblBrand, 1, 3);
    	gp1.add(lblPrice, 1, 4);
    	gp1.add(lblTotalPrice, 1, 5);
    	
    	fp.getChildren().add(gp);
    	fp.getChildren().add(gp1);
    	
    	bp.setTop(menuBar);
    	bp.setCenter(fp);
    	gp.setVgap(10);
    	gp1.setVgap(20);
		fp.setVgap(10);
		fp.setHgap(10);
		fp.setAlignment(Pos.CENTER);
    	
		lblHeader.setFont(Font.font(20));
        btnCheckout.setPrefWidth(450);
        btnDelete.setPrefWidth(450);
        
    	name.setCellValueFactory(new PropertyValueFactory<Cart, String>("Name"));
    	brand.setCellValueFactory(new PropertyValueFactory<Cart, String>("Brand"));
    	price.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("Price"));
    	quantity.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("Quantity"));
    	total.setCellValueFactory(new PropertyValueFactory<Cart, Integer>("Total"));
    	total.setCellValueFactory(data -> {
            Cart item = data.getValue();
            int totalprice = item.gethasil();
            return new SimpleObjectProperty<>(totalprice);
        });
    	
    	name.setPrefWidth(82);
    	brand.setPrefWidth(82);
    	price.setPrefWidth(82);
    	quantity.setPrefWidth(82);
    	total.setPrefWidth(82);
    	
    	cartTable.getColumns().addAll(name, brand, price, quantity, total);
    	cartTable.setItems(product);
    	
        VBox vbox1 = new VBox(15);
        vbox1.getChildren().addAll(btnCheckout, btnDelete);
        vbox1.setAlignment(Pos.CENTER);
        
        VBox vbox2 = new VBox(15);
        vbox2.getChildren().addAll(fp, vbox1);
        vbox2.setAlignment(Pos.CENTER);
        
        bp.setCenter(vbox2);
        
    	getData();
    	setEvent();
        
        btnCheckout.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
	        	validation();
	    	    sceneManager.checkoutScene();
			}
		});
        
		btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
	    		validation();
			}
		});
    	
    	menuItemHome.setOnAction(e -> {
    		sceneManager.homeScene();
        });
    	
    	menuItemCart.setOnAction(e -> {
    		sceneManager.cartScene();
    	});
    	
    	menuItemHistory.setOnAction(e -> {
    		sceneManager.historyScene();
    	});
    	
    	menuItemLogout.setOnAction(e -> {
    		sceneManager.loginScene();
    	});
    	
    	return new Scene(bp, 888, 888);
	}
	
	private void getData() {
		try (PreparedStatement preparedStatement = connect.prepareStatement(
				"SELECT msproduct.*, carttable.quantity FROM msproduct JOIN carttable ON msproduct.ProductID = carttable.ProductID;");
				ResultSet rs = preparedStatement.executeQuery()) {
			
			while (rs.next()) {
				String name = rs.getString("ProductName");
				String brand = rs.getString("ProductMerk");
				int price = rs.getInt("ProductPrice");
				int qty = rs.getInt("Quantity");
				cartTable.getItems().add(new Cart(id, name, brand, price, qty));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setEvent() {
		cartTable.setOnMouseClicked(e-> {
			TableSelectionModel<Cart> tableSelectionModel = cartTable.getSelectionModel();
			
			if (tableSelectionModel.isEmpty()) {
				return;
			}
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Cart selectedCart = tableSelectionModel.getSelectedItem();
			lblProductName.setText("Name : " + selectedCart.getName());
            lblBrand.setText("Brand : " + selectedCart.getBrand());
            lblPrice.setText("Price  : " + selectedCart.getPrice());
            lblTotalPrice.setText("Total Price  : " + selectedCart.gethasil());
		});
	}
	
	private boolean validation() {
    	Alert alert = new Alert(AlertType.WARNING);
	    alert.setHeaderText("Warning");
	    
	    if (cartTable.getSelectionModel().isEmpty()) {
	        alert.setContentText("Please insert item to your cart");
	        alert.show();
	        return false;
	    }
	    
	    return true;
    }
	
	public CartScene(ChangeScene sceneManager) {
		this.sceneManager = sceneManager;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		
	}
	
}
