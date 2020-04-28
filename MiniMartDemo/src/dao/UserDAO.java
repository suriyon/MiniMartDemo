package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import util.MySQLHelper;

public class UserDAO {
	public boolean addUser(User user) {
		boolean result = false;
		String sql = "INSERT INTO user(username, password, type_of_user) VALUES(?, ?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getTypeOfUser());
			int row = ps.executeUpdate();
			if(row>0)
				result = true;
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}
	public boolean changePassword(User user, String newPassword) {
		boolean result = false;
		String sql = "UPDATE user SET password = ? WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(1, newPassword);
			int row = ps.executeUpdate();
			if(row>0)
				result = true;
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return result;
	}
	public boolean isEmptyUsername(String username) {
		boolean result = false;
		String sql = "SELECT * FROM user WHERE username = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				result = true;
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public User getUserInfo(String username, String password) {
		User user = new User();
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user.setId(rs.getInt(1));
				user.setTypeOfUser(rs.getInt(2));
				user.setUsername(rs.getString(3));
				user.setPassword(rs.getString(4));
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean login(String username, String password) {
		boolean result = false;
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				result = true;
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public ObservableList<User> selectAllUser() {
		ObservableList<User> listOfUsers = FXCollections.observableArrayList();
		String sql = "SELECT * FROM user";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt(1);
				int typeOfUser = rs.getInt(2);
				String username = rs.getString(3);
				String password = rs.getString(4);
				
				User user = new User(id, username, password, typeOfUser);
				listOfUsers.add(user);
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOfUsers;
	}
}
