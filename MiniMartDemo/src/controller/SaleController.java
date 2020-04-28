package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ProductDAO;
import dao.SaleDAO;
import dao.SaleDetailDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Product;
import model.Sale;
import model.SaleDetail;
import util.DateUtil;

public class SaleController implements Initializable{
	
	private ObservableList<SaleDetail> list;
	private String invoiceId;
	private int totalPrice;
	private Product product;
    @FXML
    private AnchorPane pane_sale;

    @FXML
    private Label lbl_sale_id;

    @FXML
    private Label lbl_sale_date;

    @FXML
    private Label lbl_total_price;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXTextField txt_product_id;

    @FXML
    private JFXTextField txt_product_name;
    
    @FXML
    private JFXTextField txt_amount;

    @FXML
    private JFXButton btn_add_product;

    @FXML
    private TableView<SaleDetail> tb_product;

    @FXML
    private TableColumn<SaleDetail, String> col_product_id;

    @FXML
    private TableColumn<SaleDetail, String> col_product_name;

    @FXML
    private TableColumn<SaleDetail, Integer> col_product_amount;

    @FXML
    private TableColumn<SaleDetail, Integer> col_product_price;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_print;

    @FXML
    void addProduct(ActionEvent event) {
    	int productPrice = product.getPrice();
    	int amount = Integer.parseInt(txt_amount.getText());
    	int price = productPrice*amount;
    	
    	totalPrice += price;
    	lbl_total_price.setText(totalPrice + "");
    	
    	SaleDetail detail = new SaleDetail();
    	detail.setProductId(txt_product_id.getText());
    	detail.setProductName(txt_product_name.getText());
    	detail.setAmount(amount);
    	detail.setPrice(price);
    	
    	list.add(detail);    	
    	
    	tb_product.getItems().add(detail);
    	
    	clearText();
    }

    private void clearText() {
		txt_amount.setText("");
		txt_product_id.setText("");
		txt_product_name.setText("");
		txt_search.setText("");
	}

	@FXML
    void printInvoice(ActionEvent event) {

    }

    @FXML
    void saveSale(ActionEvent event) {
    	invoiceId = lbl_sale_id.getText();
    	String saleDate = lbl_sale_date.getText();    	
    	
    	// Save Sale Detail
    	int size = list.size();
    	for(int i=0; i<size; i++) {
    		SaleDetail detail = tb_product.getItems().get(i);
    		detail.setSaleId(invoiceId);
    		
    		SaleDetailDAO detailDAO = new SaleDetailDAO();
    		detailDAO.insert(detail);
    		
    		ProductDAO productDAO = new ProductDAO();
    		productDAO.decreaseAmount(detail.getProductId(), detail.getAmount());
    	}
    	
    	// Save Sale
    	Sale sale = new Sale(invoiceId, saleDate, totalPrice);
    	SaleDAO saleDAO = new SaleDAO();
    	boolean result = saleDAO.insert(sale);
    	
    	if(result) {
			Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setHeaderText("Information!!!");
    		alert.setContentText("บันทึกรายการขายสำเร็จ");
    		alert.setTitle("Alert Information");
    		
    		alert.showAndWait();
    		list = FXCollections.observableArrayList(); 
    		tb_product.getItems().clear();
    		
    		totalPrice = 0;
    		lbl_total_price.setText(totalPrice + "");
    		
//			btn_print.setDisable(false);
//			btn_save.setDisable(true);
		}else {
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setHeaderText("Error!!!");
    		alert.setContentText("เกิดข้อผิดพลาดในการบันทึกรายการขาย");
    		alert.setTitle("Alert Error");
    		
    		alert.showAndWait();
		}
    	generateInvoiceId();
    }

    @FXML
    void searchProduct(ActionEvent event) {
    	String id = txt_search.getText();
    	ProductDAO dao = new ProductDAO();
    	
    	product = dao.selectProductById(id);
    	txt_product_id.setText(product.getId());
    	txt_product_name.setText(product.getName());
    }

   

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		totalPrice = 0;
		product = new Product();
		list = FXCollections.observableArrayList();
		initSaleTable();
		generateInvoiceId();
		generateSaleDate();
		lbl_total_price.setText(totalPrice + "");
	}

	private void generateSaleDate() {
		lbl_sale_date.setText(DateUtil.format("YYYY-MM-dd"));
	}

	private void generateInvoiceId() {
		SaleDAO dao = new SaleDAO();
		lbl_sale_id.setText(dao.autoSaleId());
	}

	private void initSaleTable() {
		col_product_id.setCellValueFactory(new PropertyValueFactory<>("productId"));
		col_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
		col_product_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		col_product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
	}

}

