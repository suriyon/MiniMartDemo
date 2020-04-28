package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.User;

public class UserController implements Initializable{
	ObservableList<User> users = FXCollections.observableArrayList();
    @FXML
    private AnchorPane pane_user;

    @FXML
    private TableView<User> tb_user;

    @FXML
    private TableColumn<User, String> col_username;

    @FXML
    private TableColumn<User, String> col_password;

    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXTextField txt_password;

    @FXML
    private JFXButton btn_add_user;

    @FXML
    void addUser(ActionEvent event) {
    	String username = txt_username.getText();
    	String password = txt_password.getText();
    	
    	if(username.isEmpty() || password.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {
    		User user = new User(username, password);
    		UserDAO dao = new UserDAO();
    		
    		boolean result = dao.addUser(user);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("บันทึกข้อมูลผู้ใช้สำเร็จ");
        		alert.setTitle("Alert Information");
        		
        		alert.showAndWait();
    			tb_user.getItems().add(user);
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("เกิดข้อผิดพลาดในการบันทึกข้อมูลผู้ใช้");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
    		}
    		txt_username.setText("");
    		txt_password.setText("");
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initUserTable();
		loadUserToTable();
	}

	private void loadUserToTable() {
		UserDAO dao = new UserDAO();
		users = dao.selectAllUser();
		
		tb_user.setItems(users);
	}

	private void initUserTable() {
		col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
		col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
	}

}
