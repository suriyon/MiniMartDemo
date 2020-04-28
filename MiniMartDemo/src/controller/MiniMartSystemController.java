package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.UserSession;

public class MiniMartSystemController implements Initializable{
	
	private User user;
	
	@FXML
	private AnchorPane pane_main;
	
	@FXML
    private JFXButton toolbar_sale;
	
    @FXML
    private JFXButton toolbar_user;
    
    @FXML
    private JFXButton toolbar_login;

    @FXML
    private JFXButton toolbar_category;

    @FXML
    private JFXButton toolbar_product;

    @FXML
    private JFXButton toolbar_exit;
    
    @FXML
    private JFXButton toolbar_home;

    @FXML
    private AnchorPane pane_content;
    
    @FXML
    private BorderPane pane_border;
    
    @FXML
    private ImageView img_login;
    
    @FXML
    private Label lbl_user;

    @FXML
    void clearContent(ActionEvent event) {
    	pane_border.setLeft(null);
    	pane_border.setCenter(null);
    }
    
    @FXML
    void exitProgram(ActionEvent event) {
    	Platform.exit();
//    	Stage stage = (Stage) pane_main.getScene().getWindow();
//    	stage.close();
    }

    @FXML
    void showCategoryFrame(ActionEvent event) {
    	loadUI("CategoryFXML.fxml");
    }    

	@FXML
    void showProductFrame(ActionEvent event) {
		loadUI("ProductFXML.fxml");
    }

    @FXML
    void showUser(ActionEvent event) {
    	loadUI("UserFXML.fxml");
    }
    
    @FXML
    void showSale(ActionEvent event) {
    	loadUI("SaleFXML.fxml");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		user = new User();
		//System.out.println(user);
	}

	private void loadUI(String ui) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/" + ui));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pane_border.setLeft(root);
	}
	
	@FXML
    void showLogin(ActionEvent event) {
		if(toolbar_login.getText().equals("Login")) {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("/view/LoginFXML.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//pane_border.setCenter(root);
						
			// Open Login Dialog
			Stage stage = new Stage();
			Scene scene = new Scene(root);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        
	        stage.setScene(scene);
	        stage.showAndWait();
			
	        if(UserSession.getUsername()!=null) {
		        toolbar_login.setText("Logout");
		        img_login.setImage(new Image("images24/unlock_24px.png"));
		        
		        lbl_user.setText(UserSession.getUsername());
		        enableButton();
	        }
		} else if(toolbar_login.getText().equals("Logout")) {
			UserSession.setUsername(null);
			toolbar_login.setText("Login");
	        img_login.setImage(new Image("images24/lock_24px.png"));
	        
	        lbl_user.setText("Unknown User");
	        disableButton();
		}
	
    }

	private void disableButton() {
		// TODO Auto-generated method stub
		toolbar_category.setDisable(true);
		toolbar_product.setDisable(true);
		toolbar_user.setDisable(true);
		toolbar_sale.setDisable(true);
	}

	private void enableButton() {
		// TODO Auto-generated method stub
		toolbar_category.setDisable(false);
		toolbar_product.setDisable(false);
		toolbar_user.setDisable(false);
		toolbar_sale.setDisable(false);
	}
}
