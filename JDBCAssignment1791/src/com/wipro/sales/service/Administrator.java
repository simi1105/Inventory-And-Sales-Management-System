package com.wipro.sales.service;
import java.util.ArrayList;
import java.util.Date;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.bean.Product;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;
public class Administrator {
	private static StockDao stockDao = new StockDao();
	private static SalesDao salesDao = new SalesDao();
	
	public String insertStock(Product stock) {
		if (stock != null && stock.getProductName().length() >= 2) {
			String productID = stockDao.generateProductID(stock.getProductName());
			stock.setProductID(productID);
			if (stockDao.insertStock(stock) == 1)
				return productID;
			else
				return "Enter valid data";
		} else {
			return "Enter valid data";
		}
	}
	public String deleteStock(String ProductID) {
		if (stockDao.deleteStock(ProductID) == 1)
			return "Record deleted";
		else 
			return "Can't be deleted";
	}
	public String insertSales(Sales sales) {
		if (sales == null) 
			return "Enter valid data";
		
		if (stockDao.getStock(sales.getProductID()) == null)
			return "Enter valid data";
		
		if (stockDao.getStock(sales.getProductID()).getQuantityOnHand() < sales.getQuantitySold())
			return "Stock not enough";
		
		if (sales.getSalesDate().before(new Date()))
			return "Date not valid";
		
		String salesID = salesDao.generateSalesID(sales.getSalesDate());
		sales.setSalesID(salesID);
		
		if (salesDao.insertSales(sales) == 1) {
			if (stockDao.updateStock(sales.getProductID(), sales.getQuantitySold()) == 1)
				return "Record inserted";
			else 
				return "Error";
		} else {
			return "Error";
		}
	}
	public ArrayList<SalesReport> getSalesReport(){
		return salesDao.getSalesReport();
	}
}
