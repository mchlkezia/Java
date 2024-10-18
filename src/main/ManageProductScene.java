package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ManageProductScene {
	
	BorderPane bp;
	GridPane gp, gp1;
	FlowPane fp;
	Label lblHeader, lblProductName, lblBrand, lblPrice, lblSelectedProduct, lblAddStock, lblDelete;
	TextField tfProductName, tfPrice;
	ComboBox<String> cbBrand;
	Button btnAddProduct, btnAddStock, btnDelete;
	Spinner<Integer> sAddStock;
	Menu menu;
	MenuBar menuBar;
	MenuItem menuItemManageProduct, menuItemViewHistory, menuItemLogout;
	TableView<Product> adminTable;
	TableColumn<Product, String> name, brand;
	TableColumn<Product, Integer> stock, price;
	private ObservableList<Product> product;
	private Connect connect = Connect.getInstance();
	private ChangeScene sceneManager;
	
	public Scene thisScene() {
		bp = new BorderPane();
		gp = new GridPane();
		gp1 = new GridPane();
		fp = new FlowPane();
		lblHeader = new Label("Product List");  
		lblProductName = new Label("Product Name");
		lblBrand = new Label("Product Brand");
		lblPrice = new Label("Product Price");
		lblSelectedProduct = new Label("Name   : ");
		lblAddStock = new Label("Add Stock");
		lblDelete = new Label("Delete Product");
		tfProductName = new TextField();
		tfPrice = new TextField();
		cbBrand = new ComboBox<>();
		cbBrand.getItems().add("Yonex");
		cbBrand.getItems().add("Li-Ning");
		cbBrand.getItems().add("Victor");
		cbBrand.getSelectionModel().selectFirst();
		btnAddStock = new Button("Add Stock");
		btnDelete = new Button("Delete");
		btnAddProduct = new Button("Add Product");
		menu = new Menu("Admin");
		menuBar = new MenuBar();
		menuItemManageProduct = new MenuItem("Manage Product");
		menuItemViewHistory = new MenuItem("View History");
		menuItemLogout = new MenuItem("Logout");
		menuBar.getMenus().add(menu);
		menu.getItems().addAll(menuItemManageProduct, menuItemViewHistory, menuItemLogout);
		sAddStock = new Spinner<>();
		SpinnerValueFactory<Integer> sAddStockValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
		sAddStock.setValueFactory(sAddStockValue);
		adminTable = new TableView<>();
		product = FXCollections.observableArrayList();
		name = new TableColumn<>("Name");
		brand = new TableColumn<>("Brand");
		stock = new TableColumn<>("Stock");
		price = new TableColumn<>("Price");
		
		bp.setCenter(fp);
		bp.setTop(menuBar);
		
		gp.add(lblHeader, 0, 0);
		gp.add(adminTable, 0, 1);
		gp.add(lblSelectedProduct, 0, 2);
		gp.add(lblAddStock, 0, 3);
		gp.add(lblDelete, 1, 3);
		gp.add(sAddStock, 0, 4);
		gp.add(btnAddStock, 0, 5);
		gp.add(btnDelete, 1, 5);

		gp1.add(lblProductName, 1, 1);
		gp1.add(tfProductName, 1, 2);
		gp1.add(lblBrand, 1, 3);
		gp1.add(cbBrand, 1, 4);
		gp1.add(lblPrice, 1, 5);
		gp1.add(tfPrice, 1, 6);
		gp1.add(btnAddProduct, 1, 7);

		fp.getChildren().addAll(gp, gp1);
		
		fp.setVgap(15);
		fp.setHgap(15);
		fp.setAlignment(Pos.CENTER);
		
		gp.setVgap(10);
		gp1.setVgap(10);
		
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
		price.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		lblHeader.setFont(Font.font(20));
		lblSelectedProduct.setFont(Font.font(15));
		adminTable.setPrefHeight(244);
		btnAddStock.setPrefWidth(120);
		btnDelete.setPrefWidth(80);
		
		adminTable.getColumns().addAll(name, brand, stock, price);
		adminTable.setItems(product);
		
		VBox vbox1 = new VBox(10);
		vbox1.getChildren().addAll(lblHeader, adminTable);
		vbox1.setAlignment(Pos.CENTER);
		
		VBox vbox2 = new VBox(10);
		vbox2.getChildren().addAll(lblProductName, tfProductName, lblBrand, cbBrand, lblPrice, tfPrice, btnAddProduct);
		vbox2.setAlignment(Pos.CENTER_LEFT);
		
		HBox hbox1 = new HBox(10);
		hbox1.getChildren().addAll(vbox1, vbox2);
		hbox1.setAlignment(Pos.CENTER);
		
		VBox vbox5 = new VBox(25);
		vbox5.getChildren().addAll(hbox1, lblSelectedProduct);
		vbox5.setAlignment(Pos.CENTER);
		
		VBox vbox3 = new VBox(10);
		vbox3.getChildren().addAll(lblAddStock, sAddStock, btnAddStock);
		vbox3.setAlignment(Pos.CENTER);
		
		VBox vbox6 = new VBox(50);
		vbox6.getChildren().addAll(lblDelete, btnDelete);
		vbox6.setAlignment(Pos.CENTER);
		
		HBox hbox2 = new HBox(40);
		hbox2.getChildren().addAll(vbox3, vbox6);
		hbox2.setAlignment(Pos.CENTER);
		
		VBox vbox4 = new VBox(25);
		vbox4.getChildren().addAll(vbox5, hbox2);
		vbox4.setAlignment(Pos.CENTER);
		
		bp.setCenter(vbox4);
		
		getData();
		insertData();
		setAction();
		
		btnAddProduct.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				addProduct();
			}
        });
		
		btnAddStock.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				addStock();
			}
		});
    	
		btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				ObservableList<Product> name = adminTable.getSelectionModel().getSelectedItems();
				String query = String.format("DELETE FROM msproduct\n" + "WHERE ProductName = '%s'\n", name);
				connect.executeUpdate(query);
				getData();
			}
		});
				
    	menuItemManageProduct.setOnAction(e -> {
    		sceneManager.manageProductScene();
        });
    	
    	menuItemViewHistory.setOnAction(e -> {
    		sceneManager.viewHistoryScene();
    	});
    	
    	menuItemLogout.setOnAction(e -> {
    		sceneManager.loginScene();
    	});
    	
		return new Scene(bp, 888, 888);
	}
	
	private void getData() {
		adminTable.getItems().clear();
		try {
			String query = "SELECT * FROM msproduct";
			connect.rs = connect.execQuery(query);
			while (connect.rs.next()) {
				String name = connect.rs.getString("ProductName");
				String brand = connect.rs.getString("ProductMerk");
				Integer stock = connect.rs.getInt("ProductStock");
				Integer price = connect.rs.getInt("ProductPrice");
				
				adminTable.getItems().add(new Product(name, brand, stock, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertData() {
		btnAddProduct.setOnMouseClicked(e -> {
			String name = tfProductName.getText();
			String brand = cbBrand.getValue();
			Integer stock = sAddStock.getValue();
			Integer price = tfPrice.getAnchor();
			
			String query = String.format("INSERT INTO msproduct VALUES('%s', '%s', %d, %d)", name, brand, stock, price);
			connect.executeUpdate(query);
			refreshData();
		});
	}
	
	private void refreshData() {
		product.clear();
		getData();
	}
	
	private void setAction() {
		adminTable.setOnMouseClicked(e-> {
			TableSelectionModel<Product> tableSelectionModel = adminTable.getSelectionModel();
			
			if (tableSelectionModel.isEmpty()) {
				return;
			}
			
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Product selectedProduct = tableSelectionModel.getSelectedItem();
			lblSelectedProduct.setText("Name   : " + selectedProduct.getName());
		});
	}
	
	private void addProduct() {
		Integer price = Integer.parseInt(tfPrice.getText());
		Product newProduct = new Product(tfProductName.getText(), cbBrand.getValue(), null, price);
		product.add(newProduct);
		tfProductName.clear();
		tfPrice.clear();
	}
	
	private void addStock() {
		btnAddStock.setOnMouseClicked(e -> {
			Product selectedProduct = adminTable.getSelectionModel().getSelectedItem();
			if (selectedProduct == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please Select Product");
			} else {
				Integer stock = sAddStock.getValue();
				Integer stock2 = selectedProduct.getStock();
				Integer newStock = stock + stock2;
				String query = String.format("UPDATE msproduct\n" + "SET ProductStock = %d\n" + "WHERE ProductName = '%s'\n", name, stock); 
				PreparedStatement ps = connect.prepareStatement(query);
				selectedProduct.setStock(newStock);
				adminTable.refresh();
			}
		});	  
	}
	
	public ManageProductScene(ChangeScene sceneManager) {
		this.sceneManager = sceneManager;
	}
	
}
