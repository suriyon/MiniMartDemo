package controller;

import com.jfoenix.controls.JFXButton;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import model.UserSession;


public class LoginController {
	
	private User user;

    @FXML
    private AnchorPane pane_login;

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    @FXML
    private JFXButton btn_login;
    
    @FXML
    private JFXButton btn_cancel;

    @FXML
    void login(ActionEvent event) {
    	userlogin(event);  	    	
    }
    @FXML
    void checkLogin(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		userlogin();
    	}
    }
    
    private void userlogin() {
		// TODO Auto-generated method stub
		
	}
	@FXML
    void cancel(ActionEvent event) {
    	UserSession.setUsername(null);
		
		// Close Login Dialog
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
    }
    
	private void userlogin(ActionEvent event) {
		String username = txt_username.getText();
    	String password = txt_password.getText();
    	
    	if(username.isEmpty() || password.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {    		
    		UserDAO dao = new UserDAO();
    		
    		boolean result = dao.login(username, password);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("ยินดีต้อนรับผู้ใช้งานระบบ");
        		alert.setTitle("Alert Information");        		
        		
        		user = dao.getUserInfo(username, password);
        		
        		alert.showAndWait();
        		        	
        		UserSession.setUsername(user.getUsername());
        		
        		// Close Login Dialog
        		Node node = (Node) event.getSource();
        		Stage stage = (Stage) node.getScene().getWindow();
        		stage.close();
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("ยูสเซอร์เนม หรือพาสเวิร์ดไม่ถูกต้อง ล็อคอินใหม่");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
        		
    		}
    		txt_username.setText("");
    		txt_password.setText("");
    		txt_username.requestFocus();
    	}  
	}
}

