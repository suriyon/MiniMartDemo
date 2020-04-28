package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Sale;
import model.SaleDetail;
import util.MySQLHelper;

public class SaleDetailDAO {
	
	public boolean insert(SaleDetail detail) {
		boolean result = false;
		String sql = "INSERT INTO sale_detail(sale_id, product_id, amount, price)"
				+ " VALUES(?, ?, ?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, detail.getSaleId());
			ps.setString(2, detail.getProductId());
			ps.setInt(3, detail.getAmount());
			ps.setInt(4, detail.getPrice());
			
			int row = ps.executeUpdate();
			if(row > 0)
				result = true;
			
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public ObservableList<SaleDetail> selectSaleDetailById(String sid) {
		ObservableList<SaleDetail> list 
				= FXCollections.observableArrayList();
		String sql = "SELECT s.sale_id, p.id, p.name, s.amount, s.price"
				+ " FROM sale_detail as s, product as p"
				+ " WHERE sale_id = ? AND s.product_id = p.id";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, sid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SaleDetail detail = new SaleDetail();
				detail.setSaleId(rs.getString(1));
				detail.setProductId(rs.getString(2));
				detail.setProductName(rs.getString(3));
				detail.setAmount(rs.getInt(4));
				detail.setPrice(rs.getInt(5));
				
				list.add(detail);
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
