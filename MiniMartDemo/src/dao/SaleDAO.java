package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Sale;
import util.MySQLHelper;

public class SaleDAO {
	public String autoSaleId() {
		String id = "";
		String sql = "SELECT id FROM sale ORDER BY id DESC LIMIT 1";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getString(1);
				id = id.substring(2);
				int n = Integer.parseInt(id);
				n++;
				if(n<10)
					id = "S-000000000" + n;
				else if(n<100)
					id = "S-00000000" + n;
				else if(n<1000)
					id = "S-0000000" + n;
				else if(n<10000)
					id = "S-000000" + n;
				else if(n<100000)
					id = "S-00000" + n;
				else if(n<1000000)
					id = "S-0000" + n;
				else if(n<10000000)
					id = "S-000" + n;
				else if(n<100000000)
					id = "S-00" + n;
				else if(n<1000000000)
					id = "S-0" + n;
				else
					id = "S-" + n;
			}else {
				id = "S-0000000001";
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return id;
	}
	public boolean insert(Sale sale) {
		boolean result = false;
		String sql = "INSERT INTO sale(id, sale_date, total_price)"
				+ " VALUES(?, ?, ?)";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, sale.getId());
			ps.setString(2, sale.getSaleDate());
			ps.setInt(3, sale.getTotalPrice());
			
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
	
	public Sale selectSaleById(String sid) {
		Sale sale = new Sale();
		String sql = "SELECT * FROM sale WHERE id = ?";
		try {
			PreparedStatement ps = MySQLHelper.openDB().prepareStatement(sql);
			ps.setString(1, sid);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				sale.setId(rs.getString(1));
				sale.setSaleDate(rs.getString(2));
				sale.setTotalPrice(rs.getInt(3));
			}
			rs.close();
			ps.close();
			MySQLHelper.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sale;
	}
}
