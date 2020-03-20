package com.wipro.sales.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.util.DBUtil;
public class SalesDao {
	public int insertSales(Sales sales) {
		Connection con = null;
		PreparedStatement psInsert = null;
		String sql = "insert into TBL_SALES values(?, ?, ?, ?, ?)";
		java.sql.Date sqlDate = new java.sql.Date(sales.getSalesDate().getTime());
		try {
			con = DBUtil.getDBConnection();
			psInsert = con.prepareStatement(sql);
			psInsert.setString(1, sales.getSalesID());
			psInsert.setDate(2, sqlDate);
			psInsert.setString(3, sales.getProductID());
			psInsert.setInt(4, sales.getQuantitySold());
			psInsert.setDouble(5, sales.getSalesPricePerUnit());
			if (psInsert.executeUpdate() == 1) 
				return 1;
			else 
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	public String generateSalesID(java.util.Date salesDate) {
		Connection con = null;
		PreparedStatement psGen = null;
		String sql = "select SEQ_SALES_ID.NEXTVAL from DUAL";
		int SEQ_SALES_ID = 0;
		String out = salesDate.toString().substring(salesDate.toString().length()-2, salesDate.toString().length());		
		try {
			con = DBUtil.getDBConnection();
			psGen = con.prepareStatement(sql);
			ResultSet rs = psGen.executeQuery();
			rs.next();
			SEQ_SALES_ID = rs.getInt(1);
			out += SEQ_SALES_ID;
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	public ArrayList<SalesReport> getSalesReport(){
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "select * from V_SALES_REPORT";
		ArrayList<SalesReport> list = new ArrayList<SalesReport>();
		try {
			con = DBUtil.getDBConnection();
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SalesReport salesReport = new SalesReport();
				salesReport.setSalesID(rs.getString(1));
				salesReport.setSalesDate(rs.getDate(2));
				salesReport.setProductID(rs.getString(3));
				salesReport.setProductName(rs.getString(4));
				salesReport.setQuantitySold(rs.getInt(5));
				salesReport.setProductUnitPrice(rs.getDouble(6));
				salesReport.setSalesPricePerUnit(rs.getDouble(7));
				salesReport.setProfitAmount(rs.getDouble(8));
				list.add(salesReport);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
