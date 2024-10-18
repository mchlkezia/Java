package main;

import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class HomeScene {
	
	BorderPane bp;
	GridPane gp;
	Label lblHeader, lblProductName, lblBrand, lblPrice, lblTotalPrice;
	Button btnAdd;
	Menu menu;
	MenuBar menuBar;
	MenuItem menuItemHome, menuItemCart, menuItemHistory, menuItemLogout;
	Spinner<Integer> amountSpinner;
	TableView<Product> userTable;
	TableColumn<Product, String> name, brand;
	TableColumn<Product, Integer> stock, price;
	private ObservableList<Product> product;
	private Connect connect = Connect.getInstance();
	private ChangeScene sceneManager;
	
	public Scene thisScene() {
		bp = new BorderPane();
		gp = new GridPane();
		menuBar = new MenuBar();
		menu = new Menu("Page");
		menuItemHome = new MenuItem("Home");
		menuItemCart = new MenuItem("Cart");
		menuItemHistory = new MenuItem("History");
		menuItemLogout = new MenuItem("Logout");
		menu.getItems().addAll(menuItemHome, menuItemCart, menuItemHistory, menuItemLogout);
		menuBar.getMenus().add(menu);
    	lblHeader = new Label("Product List");
    	lblProductName = new Label("Product Name : ");
    	lblBrand = new Label("Product Brand : ");
    	lblPrice = new Label("Price : ");
    	userTable = new TableView<>();
    	name = new TableColumn<>("Name");
    	brand = new TableColumn<>("Brand");
    	stock = new TableColumn<>("Stock");
    	price = new TableColumn<>("Price");
    	amountSpinner = new Spinner<>();
    	SpinnerValueFactory<Integer> amountSpinnerValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 0);
    	amountSpinner.setValueFactory(amountSpinnerValue);
    	lblTotalPrice = new Label("Total Price : ");
    	btnAdd = new Button("Add To Cart");
    	product = FXCollections.observableArrayList();
    	
    	gp.add(lblHeader, 0, 0);
    	gp.add(userTable, 0, 1); 
    	gp.add(lblProductName, 1, 0);
    	gp.add(lblBrand, 1, 1);
    	gp.add(lblPrice, 1, 2);
    	gp.add(amountSpinner, 1, 3);
    	gp.add(lblTotalPrice, 1, 4);
    	gp.add(btnAdd, 1, 5);
    	
    	bp.setTop(menuBar);
    	bp.setCenter(gp);
    	gp.setHgap(10);
    	gp.setVgap(10);
    	
    	lblHeader.setFont(Font.font(20));
    	
    	name.setCellValueFactory(new PropertyValueFactory<Product, String>("Name"));
    	brand.setCellValueFactory(new PropertyValueFactory<Product, String>("Brand"));
    	stock.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Stock"));
    	price.setCellValueFactory(new PropertyValueFactory<Product, Integer>("Price"));
    	
    	name.setPrefWidth(75);
    	brand.setPrefWidth(75);
    	stock.setPrefWidth(75);
    	price.setPrefWidth(75);
    	
    	userTable.getColumns().addAll(name, brand, stock, price);
    	userTable.setItems(product);
    	
        VBox vbox = new VBox(30);
        vbox.getChildren().addAll(lblProductName, lblBrand, lblPrice, amountSpinner, lblTotalPrice, btnAdd);
        vbox.setAlignment(Pos.CENTER_LEFT);

        VBox vbox2 = new VBox(10);
        vbox2.getChildren().addAll(lblHeader, userTable);
        vbox2.setAlignment(Pos.CENTER_LEFT);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(vbox2, vbox);
        hbox.setAlignment(Pos.CENTER);
        bp.setCenter(hbox);
    	
    	getData();
        setEvent();
    	
    	btnAdd.setOnAction(e -> {
            if (validation()) {
                sceneManager.cartScene();
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
		userTable.getItems().clear();
		try {
			String query = "SELECT * FROM msproduct";
			connect.rs = connect.execQuery(query);
			while (connect.rs.next()) {
				String name = connect.rs.getString("ProductName");
				String brand = connect.rs.getString("ProductMerk");
				Integer stock = connect.rs.getInt("ProductStock");
				Integer price = connect.rs.getInt("ProductPrice");
				
				userTable.getItems().add(new Product(name, brand, stock, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setEvent() {
		userTable.setOnMouseClicked(e-> {
			TableSelectionModel<Product> tableSelectionModel = userTable.getSelectionModel();
			
			if (tableSelectionModel.isEmpty()) {
				return;
			}
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Product selectedProduct = tableSelectionModel.getSelectedItem();
			lblProductName.setText("Product Name : " + selectedProduct.getName());
			lblBrand.setText("Product Brand : " + selectedProduct.getBrand());
			lblPrice.setText("Price : " + selectedProduct.getPrice());
			
		    amountSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
		    	Integer total = 0;
		    	Integer quantity = 0;
		    	quantity = amountSpinner.getValue();
		    	total = selectedProduct.getPrice() * quantity;
		        lblTotalPrice.setText("Total Price : " + total);
		    });
		});
	}
	
	private boolean validation() {
    	Alert alert = new Alert(AlertType.WARNING);
	    alert.setHeaderText("Warning");
	    
	    if (userTable.getSelectionModel().isEmpty()) {
	        alert.setContentText("Please Choose 1 Item");
	        alert.show();
	        return false;
	    }
	    
	    return true;
    }
	
	public HomeScene(ChangeScene sceneManager) {
		this.sceneManager = sceneManager;
	}

}
