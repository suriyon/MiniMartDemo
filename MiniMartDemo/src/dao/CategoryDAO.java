package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import util.MySQLHelper;

public class CategoryDAO {
	public String autoCategoryId() {
		String id = "";
		String sql = "SELECT id FROM category ORDER BY id DESC LIMIT 1";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString(1);
				id = id.substring(4);
				int n = Integer.parseInt(id);
				n++;
				if(n<10)
					id = "CAT-00" + n;
				else if(n<100)
					id = "CAT-0" + n;
				else
					id = "CAT-" + n;
			}else {
				id = "CAT-001";
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return id;
	}
	public boolean insert(Category category) {
		boolean result = false;
		String sql = "INSERT INTO category(id, name) VALUES(?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, category.getId());
			ps.setString(2, category.getName());
			
			int row = ps.executeUpdate();
			if(row > 0)
				result = true;
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean update(Category category) {
		boolean result = false;
		String sql = "UPDATE category SET name = ? WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(2, category.getId());
			ps.setString(1, category.getName());
			
			int row = ps.executeUpdate();
			if(row > 0)
				result = true;
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		return result;
	}
	public ObservableList<Category> selectAllCategory() {
		ObservableList<Category> list = FXCollections.observableArrayList();
		String sql = "SELECT * FROM category";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				
				Category category = new Category(id, name);
				list.add(category);
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<Category> selectCategoryByName(String sname) {
		ObservableList<Category> list = FXCollections.observableArrayList();
		String sql = "SELECT * FROM category WHERE name LIKE ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, "%" + sname + "%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				
				Category category = new Category(id, name);
				list.add(category);
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return list;
	}
	public Category selectCategoryById(String sid) {
		Category category = new Category();
		String sql = "SELECT * FROM category WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, sid);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				
				category = new Category(id, name);
				
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return category;
	}
}
