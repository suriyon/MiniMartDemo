package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.CategoryDAO;
import dao.UserDAO;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Category;


public class CategoryController implements Initializable{

	private ObservableList<Category> categories;
	private int index;
    @FXML
    private AnchorPane pane_category;

    @FXML
    private TableView<Category> tb_category;

    @FXML
    private TableColumn<Category, String> col_category_id;

    @FXML
    private TableColumn<Category, String> col_category_name;

    @FXML
    private JFXTextField txt_category_id;

    @FXML
    private JFXTextField txt_category_name;

    @FXML
    private TextField txt_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_add_category;

    @FXML
    private JFXButton btn_update_category;
    
    @FXML
    private JFXButton btn_clear;

    @FXML
    void addCategory(ActionEvent event) {
    	String id = txt_category_id.getText();
    	String name = txt_category_name.getText();
    	
    	if(id.isEmpty() || name.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {
    		Category category = new Category(id, name);
    		CategoryDAO dao = new CategoryDAO();
    		
    		boolean result = dao.insert(category);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("บันทึกข้อมูล Category สำเร็จ");
        		alert.setTitle("Alert Information");
        		
        		alert.showAndWait();
    			tb_category.getItems().add(category);
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("เกิดข้อผิดพลาดในการบันทึกข้อมูล Category");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
    		}
    		generateCategoryId();
    		txt_category_name.setText("");
    	}
    }

    @FXML
    void searchCategory(ActionEvent event) {
    	String search = txt_search.getText();
    	CategoryDAO dao = new CategoryDAO();
    	
    	// add 3 statements
    	tb_category.getItems().clear();
    	categories = dao.selectCategoryByName(search);
    	tb_category.setItems(categories);
    }

    @FXML
    void updateCategory(ActionEvent event) {
    	String id = txt_category_id.getText();
    	String name = txt_category_name.getText();
    	
    	if(id.isEmpty() || name.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {
    		Category category = new Category(id, name);
    		CategoryDAO dao = new CategoryDAO();
    		
    		boolean result = dao.update(category);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("แก้ไขข้อมูล Category สำเร็จ");
        		alert.setTitle("Alert Information");
        		
        		alert.showAndWait();
    			tb_category.getItems().set(index, category);
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("เกิดข้อผิดพลาดในการแก้ไขข้อมูล Category");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
    		}
    		generateCategoryId();
    		txt_category_name.setText("");
    		btn_add_category.setDisable(false);
			btn_update_category.setDisable(true);
    	}
    }

    @FXML
    void clearCategory(ActionEvent event) {
    	generateCategoryId();
    	txt_category_name.setText("");
    	btn_add_category.setDisable(false);
    	btn_update_category.setDisable(true);
    	tb_category.getSelectionModel().clearSelection();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		categories = FXCollections.observableArrayList();
		initCategoryTable();
		loadCategoryToTable();
		generateCategoryId();
	}

	private void loadCategoryToTable() {
		CategoryDAO dao = new CategoryDAO();
		categories = dao.selectAllCategory();
		
		tb_category.setItems(categories);
	}

	private void generateCategoryId() {
		CategoryDAO dao = new CategoryDAO();
		txt_category_id.setText(dao.autoCategoryId());
	}

	private void initCategoryTable() {
		col_category_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_category_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		tb_category.setOnMouseReleased(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Category category = tb_category.getSelectionModel().getSelectedItem();
				if(category!=null) {
					txt_category_id.setText(category.getId());
					txt_category_name.setText(category.getName());
					
					index = tb_category.getSelectionModel().getSelectedIndex();
					btn_add_category.setDisable(true);
					btn_update_category.setDisable(false);
				}
			}
		});
	}

}
