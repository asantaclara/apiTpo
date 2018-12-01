package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.ProductDTO;
import dto.ProductItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;

public class ProductsLoad extends JDialog {

	/* * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dtm;
	private JButton btnAceptar,btnCancelar,btnAgregar,btnQuitar;
	private JComboBox<String> productsName;
	private JSpinner spinner;
	private JLabel lblTitulo;
	private int invoiceId;
	private ProductsLoad thisWindow = this;
	
	private List<ProductDTO> products;
	
	private ArrayList<ProductItemDTO> finalProducts;
	

	/**
	 * Create the frame.
	 * @return 
	 */
	public ProductsLoad(String claimType,int invoiceId) {
		setResizable(false);
		this.invoiceId = invoiceId;
		configuration(claimType);
		events();
	}
	
	public List<ProductItemDTO> getProductsLoaded() {
		return finalProducts;
	}

	
	private void configuration(String claimType) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(400, 344, 120, 33);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(38, 344, 120, 33);
		contentPane.add(btnCancelar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 94, 554, 14);
		contentPane.add(separator);
		
		lblTitulo = new JLabel("INGRESO DE "+ claimType);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitulo.setBounds(161, 11, 249, 26);
		contentPane.add(lblTitulo);
		
		productsName = new JComboBox<String>();
		productsName.setToolTipText("Unicamente apareceran los productos de la factura");
		productsName.setBounds(38, 59, 173, 24);
		
		try {
			products = Controller.getInstance().getInvoiceProducts(invoiceId);
			for(ProductDTO p : products) {
				productsName.addItem(p.getTitle());
			}
		} catch (InvalidProductException | InvalidInvoiceException | InvalidClientException | InvalidZoneException | InvalidProductItemException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		}
		
		contentPane.add(productsName);
		
		
		spinner = new JSpinner();
		//Determino que el valor minimo del spinner es 1.
		((SpinnerNumberModel) spinner.getModel()).setMinimum(0);
		spinner.setBounds(249, 59, 69, 24);
		contentPane.add(spinner);
		spinner.setValue(0);
		
		
		btnAgregar = new JButton("AGREGAR PRODUCTO");
		btnAgregar.setBounds(364, 59, 156, 24);
		contentPane.add(btnAgregar);
		
		btnQuitar = new JButton("QUITAR");
		btnQuitar.setBounds(215, 344, 120, 33);
		contentPane.add(btnQuitar);
		//--------------------------INICIO TABLA-------------------------------------------------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 544, 227);
		contentPane.add(scrollPane);
		
		
		dtm = new DefaultTableModel(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		dtm.setColumnCount(4);
		dtm.setRowCount(0);
		
		String col1[] = {"ID","TITULO", "DESCRIPCION", "PRECIO UNITARIO","CANTIDAD","TOTAL"};
		dtm.setColumnIdentifiers(col1);
		table = new JTable(dtm);
		
		scrollPane.setViewportView(table);
		//--------------------------FIN TABLA-------------------------------------------------------
		
		finalProducts = new ArrayList<ProductItemDTO>();
		
		}
	
	private void events() {
			
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				spinner.getValue().toString();
				for(ProductDTO prod : products) {
					if(prod.getTitle().equals(productsName.getSelectedItem())) {
						int quantity = (int) spinner.getValue();
						String[] aux = new String[6];
						for (int i = 0; i < prod.toDataRow().length; i++) {
							aux[i] = prod.toDataRow()[i];
						}
						aux[4] = String.valueOf(quantity);
						aux[5] = String.valueOf(quantity * prod.getPrice());
						dtm.addRow(aux);
					}
				}
			}
		});
			
		btnQuitar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()!= -1) {
					dtm.removeRow(table.getSelectedRow());
				}
				else
					JOptionPane.showMessageDialog(ProductsLoad.this,"No se ha seleccionado ningun producto");
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisWindow.dispose();
				
			}
		});
		/**
		 * Una vez aceptado se genera una ArrayList con todos los ProductItem seleccionados
		 */
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(ProductDTO prod : products) {	
					for(int y = 0;y < table.getRowCount();y++) {
						if(prod.getProductId() == Integer.parseInt(table.getValueAt(y, 0).toString())) {
							int quantity = Integer.parseInt(table.getValueAt(y,4).toString());
							finalProducts.add(new ProductItemDTO(prod,quantity));
						}
					}
				}
				thisWindow.dispose();
			}
		});
	
	}
}
