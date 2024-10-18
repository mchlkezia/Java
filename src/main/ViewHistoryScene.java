package main;

import java.sql.Date;
import java.sql.SQLException;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class ViewHistoryScene {
	
	BorderPane bp;
	GridPane gp;
	ScrollPane sp;
	Label lblMyTransaction, lblTransactionDetail, lblTotalPrice;
	Menu menu;
	MenuBar menuBar;
	MenuItem menuItemManageProduct, menuItemViewHistory, menuItemLogout;
	TableView<History1> tableMyTransaction;
	TableColumn<History1, String> transactionId, userEmail;
	TableColumn<History1, Date> date;
	TableView<History2> tableTransactionDetail;
	TableColumn<History2, String> id, name;
	TableColumn<History2, Integer> price, quantity, totalPrice;
	private ObservableList<History1> history1;
	private ObservableList<History2> history2;
	private Connect connect = Connect.getInstance();
	private ChangeScene sceneManager;
	
	public Scene thisScene() {
		bp = new BorderPane();
		gp = new GridPane();
		sp = new ScrollPane();
		lblMyTransaction = new Label("My Trasaction");
		lblTransactionDetail = new Label("Transaction Detail");
		lblTotalPrice = new Label("Total Price : ");
		menuBar = new MenuBar();
		menu = new Menu("Admin");
		menuItemManageProduct = new MenuItem("Manage Product");
		menuItemViewHistory = new MenuItem("View History");
		menuItemLogout = new MenuItem("Logout");
		menu.getItems().addAll(menuItemManageProduct, menuItemViewHistory, menuItemLogout);
		menuBar.getMenus().add(menu);
		tableMyTransaction = new TableView<>();
    	transactionId = new TableColumn<>("ID");
    	userEmail = new TableColumn<>("Email");
    	date = new TableColumn<>("Date");
		tableTransactionDetail = new TableView<>();
		id = new TableColumn<>("ID");
		name = new TableColumn<>("Product");
		price = new TableColumn<>("Price");
		quantity = new TableColumn<>("Quantity");
		totalPrice = new TableColumn<>("Total Price");
    	history1 = FXCollections.observableArrayList();
    	history2 = FXCollections.observableArrayList();
    	
    	gp.add(lblMyTransaction, 0, 0);
    	gp.add(lblTransactionDetail, 1, 0);
    	gp.add(tableMyTransaction, 0, 1);
    	gp.add(tableTransactionDetail, 1, 1);
    	gp.add(lblTotalPrice, 1, 2);
    	
    	sp.setContent(bp);
    	bp.setTop(menuBar);
    	bp.setCenter(gp);
    	gp.setHgap(10);
    	gp.setVgap(10);
    	gp.setAlignment(Pos.CENTER);
    	
    	lblMyTransaction.setFont(Font.font(15));
    	lblTransactionDetail.setFont(Font.font(15));
    	lblTotalPrice.setFont(Font.font(15));
    	tableMyTransaction.setPrefWidth(300);
    	tableTransactionDetail.setPrefWidth(500);
    	tableMyTransaction.setPrefHeight(500);
    	tableTransactionDetail.setPrefHeight(500);
    	
    	transactionId.setCellValueFactory(new PropertyValueFactory<History1, String>("Id"));
    	userEmail.setCellValueFactory(new PropertyValueFactory<History1, String>("Email"));
    	date.setCellValueFactory(new PropertyValueFactory<History1, Date>("Date"));
    	
    	id.setCellValueFactory(new PropertyValueFactory<History2, String>("Id"));
    	name.setCellValueFactory(new PropertyValueFactory<History2, String>("Name"));
    	price.setCellValueFactory(new PropertyValueFactory<History2, Integer>("Price"));
    	quantity.setCellValueFactory(new PropertyValueFactory<History2, Integer>("Quantity"));
    	totalPrice.setCellValueFactory(new PropertyValueFactory<History2, Integer>("Total Price"));
    	totalPrice.setCellValueFactory(data -> {
            History2 item = data.getValue();
            int totalprice = item.gethasil();
            return new SimpleObjectProperty<>(totalprice);
        });
    	
    	transactionId.setMinWidth(bp.getWidth()/3);
    	userEmail.setMinWidth(bp.getWidth()/3);
    	date.setMinWidth(bp.getWidth()/3);
    	
    	id.setMinWidth(bp.getWidth()/5);
    	name.setMinWidth(bp.getWidth()/5);
    	price.setMinWidth(bp.getWidth()/5);
    	quantity.setMinWidth(bp.getWidth()/5);
    	totalPrice.setMinWidth(bp.getWidth()/5);
    	
    	tableMyTransaction.getColumns().addAll(transactionId, userEmail, date);
    	tableMyTransaction.setItems(history1);
		tableTransactionDetail.getColumns().addAll(id, name, price, quantity, totalPrice);
		tableTransactionDetail.setItems(history2);
		
		getData();

		tableMyTransaction.getSelectionModel().selectedItemProperty().addListener(
			(ObservableValue<? extends History1> observable, History1 oldValue, History1 newValue) -> {
			if (newValue != null) {
				updateDetail(newValue.getTransactionId());
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
	
	private void updateDetail(String TransactionID) {
		history2.clear();

		int totaltransaction = 0;
        try {
        	String select = "SELECT td.TransactionID, td.Quantity, mp.ProductName, mp.ProductPrice " +
                    "FROM transactiondetail td " +
                    "JOIN msproduct mp ON td.ProductID = mp.ProductID " +
                    "WHERE td.TransactionID = '" + TransactionID + "';";
            connect.rs = connect.execQuery(select);
            while (connect.rs.next()) {
                String id = connect.rs.getString("TransactionID");
                String nama = connect.rs.getString("ProductName");
                int price = connect.rs.getInt("ProductPrice");
                int qty = connect.rs.getInt("Quantity");
                int total = price * qty;
                history2.add(new History2(id, nama, price, qty));
                totaltransaction += total;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lblTotalPrice.setText("Total Price : " + totaltransaction);
    }

    private void getData() {
        try {
            String query = "SELECT * FROM transactionheader JOIN msuser ON transactionheader.UserID = msuser.UserID";
            connect.rs = connect.execQuery(query);
            while (connect.rs.next()) {
                String transactionId = connect.rs.getString("TransactionID");
                String email = connect.rs.getString("UserEmail");
                Date date = connect.rs.getDate("TransactionDate");
                tableMyTransaction.getItems().add(new History1(transactionId, email, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connect.rs != null) {
                    connect.rs.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
	
	public ViewHistoryScene(ChangeScene sceneManager) {
		this.sceneManager = sceneManager;
	}
	
}
