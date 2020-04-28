package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dao.CategoryDAO;
import dao.ProductDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Category;
import model.Product;

public class ProductController implements Initializable{
	private ObservableList<Product> products;
	private ObservableList<Category> categories;
	private int productIndex;
	private int categoryIndex;
    @FXML
    private AnchorPane pane_product;
    
    @FXML
    private TableView<Product> tb_product;

    @FXML
    private TableColumn<Product, String> col_product_id;

    @FXML
    private TableColumn<Product, String> col_product_name;

    @FXML
    private TableColumn<Product, Integer> col_product_amount;

    @FXML
    private TableColumn<Product, Integer> col_product_price;

    @FXML
    private TableColumn<Product, String> col_category_name;

    @FXML
    private JFXTextField txt_product_id;

    @FXML
    private JFXTextField txt_product_name;

    @FXML
    private JFXTextField txt_product_amount;

    @FXML
    private JFXTextField txt_product_price;

    @FXML
    private JFXComboBox<Category> cmb_category;

    @FXML
    private JFXTextField txt_product_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_clear;

    @FXML
    void addProduct(ActionEvent event) {
    	String id = txt_product_id.getText();
    	String name = txt_product_name.getText();    	
    	
    	if(id.isEmpty() || 
    			name.isEmpty() ||
    			txt_product_amount.getText().isEmpty() ||
    			txt_product_price.getText().isEmpty() ||
    			cmb_category.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {
    		int amount = Integer.parseInt(txt_product_amount.getText());
    		int price = Integer.parseInt(txt_product_price.getText());
    		
    		Category category = cmb_category.getSelectionModel().getSelectedItem();
    		
    		Product product = new Product(id, name, amount, price, category);
    		
    		ProductDAO dao = new ProductDAO();
    		
    		boolean result = dao.insert(product);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("บันทึกข้อมูลสินค้าสำเร็จ");
        		alert.setTitle("Alert Information");
        		
        		alert.showAndWait();
    			tb_product.getItems().add(product);
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("เกิดข้อผิดพลาดในการบันทึกข้อมูลสินค้า");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
    		}
    		generateProductId();
    		txt_product_name.setText("");
    		txt_product_price.setText("");
    		txt_product_amount.setText("");
    		cmb_category.getSelectionModel().clearSelection();
    	}
    }

    @FXML
    void clearProduct(ActionEvent event) {
    	btn_add.setDisable(false);
    	btn_update.setDisable(true);
    	txt_product_name.setText("");
    	txt_product_amount.setText("");
    	txt_product_price.setText("");
    	
    	cmb_category.getSelectionModel().clearSelection();
    	tb_product.getSelectionModel().clearSelection();
    	generateProductId();
    	
    	ProductDAO dao = new ProductDAO();
    	products = dao.selectAllProduct();
    	
    	tb_product.setItems(products);
    }

    @FXML
    void searchProduct(ActionEvent event) {
    	String search = txt_product_search.getText();
    	
    	ProductDAO dao = new ProductDAO();
    	products = dao.selectProductByName(search);
    	
    	tb_product.setItems(products);
    	
    	txt_product_search.setText("");
    }

    @FXML
    void updateProduct(ActionEvent event) {
    	String id = txt_product_id.getText();
    	String name = txt_product_name.getText();    	
    	
    	if(id.isEmpty() || 
    			name.isEmpty() ||
    			txt_product_amount.getText().isEmpty() ||
    			txt_product_price.getText().isEmpty() ||
    			cmb_category.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {
    		int amount = Integer.parseInt(txt_product_amount.getText());
    		int price = Integer.parseInt(txt_product_price.getText());
    		
    		Category category = cmb_category.getSelectionModel().getSelectedItem();
    		
    		Product product = new Product(id, name, amount, price, category);
    		
    		ProductDAO dao = new ProductDAO();
    		
    		boolean result = dao.update(product);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("แก้ไขข้อมูลสินค้าสำเร็จ");
        		alert.setTitle("Alert Information");
        		
        		alert.showAndWait();
    			tb_product.getItems().set(productIndex, product);
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("เกิดข้อผิดพลาดในการแก้ไขข้อมูลสินค้า");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
    		}
    		generateProductId();
    		txt_product_name.setText("");
    		txt_product_price.setText("");
    		txt_product_amount.setText("");
    		cmb_category.getSelectionModel().clearSelection();
    		tb_product.getSelectionModel().clearSelection();
    		btn_add.setDisable(false);
    		btn_update.setDisable(true);
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		products = FXCollections.observableArrayList();
		categories = FXCollections.observableArrayList();
		initProductTable();
		loadProductToTable();
		loadCategoryToComboBox();
		generateProductId();
	}

	private void generateProductId() {
		ProductDAO dao = new ProductDAO();
		txt_product_id.setText(dao.autoProductId());
	}

	private void loadCategoryToComboBox() {
		CategoryDAO dao = new CategoryDAO();
		categories = dao.selectAllCategory();
		
		cmb_category.setItems(categories);
	}

	

	private void initProductTable() {
		col_product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		col_product_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		col_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		col_category_name.setCellValueFactory(new PropertyValueFactory<>("category"));
		
		tb_product.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				productIndex = tb_product.getSelectionModel().getSelectedIndex();
				Product product = tb_product.getSelectionModel().getSelectedItem();
				
				if(product!=null) {
					
					txt_product_id.setText(product.getId());
					txt_product_name.setText(product.getName());
					txt_product_amount.setText(product.getAmount() + "");
					txt_product_price.setText(String.valueOf(product.getPrice()));
					
					String catName = product.getCategory().getName();
					for(int i=0; i<=categories.size(); i++) {
						if(catName.equals(categories.get(i).getName())) {
							cmb_category.getSelectionModel().select(i);
							categoryIndex = i;
							break;
						}
					}
					btn_add.setDisable(true);
					btn_update.setDisable(false);
				
				}
			}
		});
	}

	private void loadProductToTable() {
		ProductDAO dao = new ProductDAO();
		products = dao.selectAllProduct();
		
		
			tb_product.setItems(products);
	}
}
