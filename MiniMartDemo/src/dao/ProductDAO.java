package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Category;
import model.Product;
import util.MySQLHelper;

public class ProductDAO {
	public void decreaseAmount(String id, int amount) {
		String sql = "UPDATE product SET amount = amount - ? WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setInt(1, amount);
			ps.setString(2, id);

			ps.executeUpdate();
			
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void increaseAmount(String id, int amount) {
		String sql = "UPDATE product SET amount = amount + ? WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setInt(1, amount);
			ps.setString(2, id);

			ps.executeUpdate();
			
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String autoProductId() {
		String id = "";
		String sql = "SELECT id FROM product ORDER BY id DESC LIMIT 1";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString(1);
				id = id.substring(4);
				int n = Integer.parseInt(id);
				n++;
				if(n<10)
					id = "PRO-00000" + n;
				else if(n<100)
					id = "PRO-0000" + n;
				else if(n<1000)
					id = "PRO-000" + n;
				else if(n<10000)
					id = "PRO-00" + n;
				else if(n<100000)
					id = "PRO-0" + n;
				else
					id = "PRO-" + n;
			}else {
				id = "PRO-000001";
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean insert(Product product) {
		boolean result = false;
		String sql = "INSERT INTO product(id, name, amount, price, cat_id) VALUES(?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, product.getId());
			ps.setString(2, product.getName());
			ps.setInt(3, product.getAmount());
			ps.setInt(4, product.getPrice());
			ps.setString(5, product.getCategory().getId());
			
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
	public boolean update(Product product) {
		boolean result = false;
		String sql = "UPDATE product SET name = ?, price = ?, cat_id = ? WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(4, product.getId());
			ps.setString(1, product.getName());			
			ps.setInt(2, product.getPrice());
			ps.setString(3, product.getCategory().getId());
			
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
	
	public ObservableList<Product> selectAllProduct() {
		ObservableList<Product> list = FXCollections.observableArrayList();
		String sql = "SELECT * FROM product";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				String catId = rs.getString(5);
				
				CategoryDAO dao = new CategoryDAO();			
				
				Category category = dao.selectCategoryById(catId);
				
				Product product = new Product(id, name, amount, price, category);
				list.add(product);
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<Product> selectProductByName(String sname) {
		ObservableList<Product> list = FXCollections.observableArrayList();
		String sql = "SELECT * FROM product WHERE name LIKE ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, "%" + sname + "%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				String catId = rs.getString(5);
				
				CategoryDAO dao = new CategoryDAO();			
				
				Category category = dao.selectCategoryById(catId);
				
				Product product = new Product(id, name, amount, price, category);
				list.add(product);
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return list;
	}
	
	public Product selectProductById(String sid) {
		Product product = new Product();
		String sql = "SELECT * FROM product WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, sid);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				String catId = rs.getString(5);
				
				CategoryDAO dao = new CategoryDAO();			
				
				Category category = dao.selectCategoryById(catId);
				
				product = new Product(id, name, amount, price, category);
				
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return product;
	}
}
