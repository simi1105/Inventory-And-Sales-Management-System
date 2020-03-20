package com.wipro.sales.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wipro.sales.bean.Product;
import com.wipro.sales.util.DBUtil;
public class StockDao {
	public int insertStock(Product stock) {
		Connection con = null;
		PreparedStatement psInsert = null;
		String sql = "insert into TBL_STOCK values(?, ?, ?, ?, ?)";
		
		try {
			con = DBUtil.getDBConnection();
			psInsert = con.prepareStatement(sql);
			psInsert.setString(1, stock.getProductID());
			psInsert.setString(2, stock.getProductName());
			psInsert.setInt(3, stock.getQuantityOnHand());
			psInsert.setDouble(4, stock.getProductUnitPrice());
			psInsert.setInt(5, stock.getReorderLevel());
			
			if (psInsert.executeUpdate() == 1) 
				return 1;
			else 
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public String generateProductID(String productName) {
		Connection con = null;
		PreparedStatement psGen = null;
		String sql = "select SEQ_PRODUCT_ID.NEXTVAL from DUAL";
		int SEQ_PRODUCT_ID = 0;
		String out = "";
		try {
			con = DBUtil.getDBConnection();
			psGen = con.prepareStatement(sql);
			ResultSet rs = psGen.executeQuery();
			rs.next();
			SEQ_PRODUCT_ID = rs.getInt(1);
			out += productName.substring(0, 2);
			out += SEQ_PRODUCT_ID;
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int updateStock(String productID,int soldQty) {
		Connection con = null;
		PreparedStatement psUpdate = null;
		String sql = "update TBL_STOCK set Quantity_On_Hand = Quantity_On_Hand - ?"
				+ "where Product_ID = ?";
		try {
			con = DBUtil.getDBConnection();
			psUpdate = con.prepareStatement(sql);
			psUpdate.setInt(1, soldQty);
			psUpdate.setString(2, productID);
			if (psUpdate.executeUpdate() == 1)
				return 1;
			else 
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public Product getStock(String productID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM TBL_STOCK WHERE Product_ID = ?";
		try {
			conn = DBUtil.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productID);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			Product stock = new Product();
			stock.setProductID(rs.getString(1));
			stock.setProductName(rs.getString(2));
			stock.setQuantityOnHand(rs.getInt(3));
			stock.setProductUnitPrice(rs.getDouble(4));
			stock.setReorderLevel(rs.getInt(5));
			return stock;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public int deleteStock(String productID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE TBL_STOCK WHERE Product_ID = ?";
		try {
			conn = DBUtil.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productID);
			if (pstmt.executeUpdate() == 1) return 1;
			else return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
