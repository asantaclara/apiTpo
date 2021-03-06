package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.Product;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidProductException;

public class ProductDAO {

	public List<Product> getAllProducts() throws ConnectionException, AccessException, InvalidProductException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM Products WHERE Active = 1"; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<Product> returnList = new LinkedList<>();
				Product newProduct = null;
				
				while(rs.next()){					
					newProduct = new Product(rs.getString(2), rs.getString(3), rs.getFloat(4));
					newProduct.setProductId(rs.getInt(1));
					returnList.add(newProduct);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	public Product getActiveProduct(int productId) throws ConnectionException, AccessException, InvalidProductException {
		return getProduct(productId, false);
	}
	public Product getProduct(int productId) throws ConnectionException, AccessException, InvalidProductException {
		return getProduct(productId, true);
	}
	private Product getProduct(int productId, boolean activeFlag) throws ConnectionException, AccessException, InvalidProductException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM Products WHERE ProductId = " + productId;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					if(rs.getByte(5) == 1) {					
						Product newProduct = new Product(rs.getString(2), rs.getString(3), rs.getFloat(4));
						newProduct.setProductId(rs.getInt(1));
						return newProduct;
					} else {
						throw new InvalidProductException("The product is not active");
					}
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

	public void save(Product product) throws ConnectionException, AccessException, InvalidProductException{
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(product.getProductId() != 0) {
				throw new InvalidProductException("Product already in data base");
			}
			
			product.setProductId(SqlUtils.lastId("Products", "ProductId") + 1); 
			try {
				prepStm = con.prepareStatement("insert into Products values(?,?,?,?,?)");
				prepStm.setInt(1, product.getProductId());
				prepStm.setString(2, product.getTitle());
				prepStm.setString(3, product.getDescription());
				prepStm.setFloat(4, product.getPrice());
				prepStm.setByte(5, (byte) ((product.isActive() == true) ? 1 : 0));
				
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

	public void modify(Product product) throws ConnectionException, AccessException, InvalidProductException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm;
			
			if(product.getProductId() == 0) {
				throw new InvalidProductException("Product not in data base");
			}
			
			try {
				prepStm = con.prepareStatement("UPDATE Products SET Title = ?, Description = ?, Price = ?, Active = ? WHERE ProductId = " + product.getProductId());
				prepStm.setString(1, product.getTitle());
				prepStm.setString(2, product.getDescription());
				prepStm.setFloat(3, product.getPrice());
				prepStm.setByte(4, (byte) ((product.isActive() == true) ? 1 : 0));
				
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
}
