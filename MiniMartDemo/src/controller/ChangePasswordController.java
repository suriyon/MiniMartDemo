package controller;

import com.jfoenix.controls.JFXButton;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.User;

public class ChangePasswordController {

    @FXML
    private AnchorPane pane_change_password;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_old_password;

    @FXML
    private TextField txt_new_password;

    @FXML
    private JFXButton btn_change_password;

    @FXML
    void changePassword(ActionEvent event) {
    	String username = txt_username.getText();
    	String oldPassword = txt_old_password.getText();
    	String newPassword = txt_new_password.getText();
    	
    	if(username.isEmpty() ||
    			oldPassword.isEmpty() ||
    			newPassword.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setHeaderText("Waring!!!");
    		alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วนก่อน");
    		alert.setTitle("Alert Warning");
    		
    		alert.showAndWait();
    	}else {
    		UserDAO dao = new UserDAO();
    		User user = dao.getUserInfo(username, oldPassword);
    		
    		boolean result = dao.changePassword(user, newPassword);
    		if(result) {
    			Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setHeaderText("Information!!!");
        		alert.setContentText("เปลี่ยนพาสเวิร์ดสำเร็จ");
        		alert.setTitle("Alert Information");
        		
        		alert.showAndWait();
    		}else {
    			Alert alert = new Alert(AlertType.ERROR);
        		alert.setHeaderText("Error!!!");
        		alert.setContentText("เกิดข้อผิดพลาดในการเปลี่ยนพาสเวิร์ด");
        		alert.setTitle("Alert Error");
        		
        		alert.showAndWait();
    		}
    		txt_username.setText("");
    		txt_old_password.setText("");
    		txt_new_password.setText("");
    	}
    }

}
