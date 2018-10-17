package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Exceptions.AccessException;
import Exceptions.ConnectException;
import Exceptions.ProductException;
import Models.Product;

public class ProductDAO {
	/**
	 * 
	 * @param productId
	 * @return
	 * @throws ConnectException
	 * @throws AccessException
	 * @throws ProductException
	 */
	static public Product getProductThroughId(int productId) throws ConnectException, AccessException, ProductException{
		Connection con = null;  
		Statement stmt = null;  
		ResultSet rs = null; 
		
		try {    
			con = ConnectionFactory.getInstance().getConection();
		}
		catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			throw new ConnectException("No esta disponible el acceso al Servidor");
		}
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Error de acceso");
		}
		
		String SQL = "SELECT  * FROM products where productId = " + productId;
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Error de consulta");
		}
		try {
			
			if(rs.next()){
				Product product = new Product(rs.getString(1), rs.getString(2), rs.getFloat(3));
				return product;
			}
			else{
				throw new ProductException("El cliente con id: " + productId + " no existe");
			}
		} catch (SQLException e) {
			throw new ConnectException("No es posible acceder a los datos");
		}
	}
	/**
	 * Dado un producto lo almacena en la BD
	 * @param Product p
	 * @throws ConnectException
	 * @throws AccessException
	 */
	static public void saveProduct(Product p) throws ConnectException, AccessException{
		Connection con;
		
		try {
			con = ConnectionFactory.getInstance().getConection();
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectException("No esta disponible el acceso al Servidor");
		} 
		
		PreparedStatement stm;
		try {
			stm = con.prepareStatement("insert into products values(?,?,?,?)");
			stm.setInt(1, p.getProductId());
			stm.setString(2, p.getTitle());
			stm.setString(3, p.getDescription());
			stm.setFloat(4, p.getPrice());
			stm.executeUpdate();
			
		} catch (SQLException e) {
			throw new AccessException("Error de acceso");
		}
		
		try {
			stm.execute();
		} catch (SQLException e) {
			throw new AccessException("No se pudo guardar");
		}
	}
}
