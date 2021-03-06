package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.Invoice;
import backEnd.MoreQuantityClaim;
import backEnd.ProductItem;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidProductException;

public class ProductItemDAO {

	public static void save(ProductItem pi) throws InvalidProductException, ConnectionException, AccessException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(pi.getId() != 0) {
				throw new InvalidProductException("ProductItem already in data base");
			}
			
			pi.setId(SqlUtils.lastId("ProductsItems", "ProductItemId") + 1); 
			try {
				prepStm = con.prepareStatement("insert into ProductsItems values(?,?,?)");
				prepStm.setInt(1, pi.getId());
				prepStm.setInt(2, pi.getProduct().getProductId());
				prepStm.setInt(3, pi.getQuantity());
				
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}		
			
			try {
				prepStm.execute();
			} catch (SQLException e) {
				throw new AccessException("Save error");
			}	
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	public static ProductItem getProductItem(int id) throws ConnectionException, AccessException, InvalidProductException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM ProductsItems WHERE ProductItemId = " + id; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					ProductItem newProduct = new ProductItem(new ProductDAO().getProduct(rs.getInt(2)), rs.getInt(3));
					newProduct.setId(rs.getInt(1));
					return newProduct;
				}
				else{
					throw new InvalidProductException("Product not found");
				}
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public static List<ProductItem> getProductItemsOfInvoice(Invoice invoice) throws ConnectionException, AccessException, InvalidProductException{
		String SQL = "SELECT * FROM ProductsItems JOIN Invoice_ProductItem ON "
				+ "ProductsItems.ProductItemId = Invoice_ProductItem.ProductItemId WHERE Invoice_ProductItem.InvoiceId = " + invoice.getId(); 
		return getAllProductItems(SQL);
	}
	
	public static List<ProductItem> getProductItemsOfMoreQuantityClaim(MoreQuantityClaim claim) throws ConnectionException, AccessException, InvalidProductException{
		String SQL = "SELECT * FROM ProductsItems JOIN MoreQuantityClaim_ProductItem ON ProductsItems.ProductItemId = MoreQuantityClaim_ProductItem.ProductItemId "
				+ "WHERE MoreQuantityClaim_ProductItem.MoreQuantityClaimId = " + claim.getClaimId();
		return getAllProductItems(SQL);
	}
	
	private static List<ProductItem> getAllProductItems(String sql) throws ConnectionException, AccessException, InvalidProductException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<ProductItem> returnList = new LinkedList<>();
				ProductItem newProductItem = null;
				
				while(rs.next()){				
					newProductItem = new ProductItem(new ProductDAO().getProduct(rs.getInt(2)), rs.getInt(3));
					newProductItem.setId(rs.getInt(1));
					returnList.add(newProductItem);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
}
