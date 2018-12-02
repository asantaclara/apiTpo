package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.InvoiceDTO;
import dto.InvoiceItemDTO;
import dto.ProductItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidZoneException;

public class WrongInvoiceLoad extends JDialog {

	/* * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private DefaultTableModel dtmInvoice,dtmItemFact,dtmLoaded;
	private JButton btnAceptar,btnCancelar,btnAgregar,btnQuitar;
	private JLabel lblTitulo;
	private JTable invoiceTable,itemsTable,loadedTable;
	
	private HashMap<Integer,InvoiceItemDTO> finalInconsistencys;
	private JEditorPane editorPane;
	private JPanel panel;
	
	private int actualClientID;
	private JLabel lblFacturasIngresadas;
	private WrongInvoiceLoad thisWindow = this;
	
	/**
	 * Create the frame.
	 * @return 
	 */
	public WrongInvoiceLoad(int actualClientID) {
		this.actualClientID = actualClientID;
		setResizable(false);
		configuration();
		events();
	}
	
	public HashMap<Integer, InvoiceItemDTO> getProductsLoaded() {
		return finalInconsistencys;
	}

	
	private void configuration() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 711, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(541, 397, 120, 33);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("CANCELAR");
		btnCancelar.setBounds(326, 397, 120, 33);
		contentPane.add(btnCancelar);
		
		lblTitulo = new JLabel("INGRESO DE FACTURACION INCORRECTA");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitulo.setBounds(206, 11, 303, 26);
		contentPane.add(lblTitulo);
		
		btnAgregar = new JButton("AGREGAR INCONSISTENCIA");
		
		btnAgregar.setBounds(42, 362, 207, 24);
		contentPane.add(btnAgregar);
		
		btnQuitar = new JButton("QUITAR");
		btnQuitar.setBounds(84, 397, 120, 33);
		contentPane.add(btnQuitar);
		
		//--------------------------------------INICIO TABLA FACTURAS-----------------------------------------------------------------
		JScrollPane scrollPaneInvoice = new JScrollPane();
		scrollPaneInvoice.setBounds(10,163,273,188);
		contentPane.add(scrollPaneInvoice);
		
		dtmInvoice = new DefaultTableModel(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		dtmInvoice.setColumnCount(3);
		dtmInvoice.setRowCount(0);
		
		String col1[] = {"ID FACTURA","FECHA", "CLIENTE"};
		dtmInvoice.setColumnIdentifiers(col1);
		invoiceTable = new JTable(dtmInvoice);
		
		
		scrollPaneInvoice.setViewportView(invoiceTable);
		
	

	
		
		//---------------------------------INICIO TABLA ITEMS FACTURA-----------------------------------
		JScrollPane scrollPaneInvItems = new JScrollPane();
		scrollPaneInvItems.setBounds(293, 163, 194, 223);
		contentPane.add(scrollPaneInvItems);
		
		dtmItemFact = new DefaultTableModel(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		dtmItemFact.setColumnCount(3);
		dtmItemFact.setRowCount(0);
		
		String col2[] = {"ID PRODUCTO","CANTIDAD"};
		dtmItemFact.setColumnIdentifiers(col2);
		itemsTable = new JTable(dtmItemFact);
		
		scrollPaneInvItems.setViewportView(itemsTable);
		//---------------------------------FIN TABLA ITEMS FACTURA-----------------------------------
		
		//---------------------------------INICIO TABLA LOADED---------------------------------------
		JScrollPane scrollPaneLoaded = new JScrollPane();
		scrollPaneLoaded.setBounds(497, 163, 198, 223);
		contentPane.add(scrollPaneLoaded);
		
		dtmLoaded = new DefaultTableModel(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		dtmLoaded.setColumnCount(2);
		dtmLoaded.setRowCount(0);
		
		String col3[] = {"ID FACTURA","INCONSISTENCIA"};
		dtmLoaded.setColumnIdentifiers(col3);
		loadedTable = new JTable(dtmLoaded);
		
		scrollPaneLoaded.setViewportView(loadedTable);
		
		loadedTable = new JTable(dtmLoaded);
		
		
		//---------------------------------FIN TABLA LOADED-----------------------------------------
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Ingrese la inconsistencia", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 35, 685, 105);
		contentPane.add(panel);
		panel.setLayout(null);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(10, 21, 665, 73);
		panel.add(editorPane);
		
		JLabel lblFacturas = new JLabel("FACTURAS");
		lblFacturas.setBounds(10, 145, 66, 14);
		contentPane.add(lblFacturas);
		
		JLabel lblProductos = new JLabel("PRODUCTOS");
		lblProductos.setBounds(293, 145, 80, 14);
		contentPane.add(lblProductos);
		
		lblFacturasIngresadas = new JLabel("FACTURAS INGRESADAS");
		lblFacturasIngresadas.setBounds(497, 145, 130, 14);
		contentPane.add(lblFacturasIngresadas);
		

		finalInconsistencys= new HashMap<Integer, InvoiceItemDTO>();
		
		}
	
	private void events() {
			
		
		//Ni bien entro cargo todos las facturas
		try {
			for(InvoiceDTO inv: Controller.getInstance().getInvoicesByClient(actualClientID)) {
				dtmInvoice.addRow(inv.toDataRow());
			}
		} catch ( InvalidClientException | InvalidProductException | InvalidZoneException | InvalidInvoiceException | InvalidProductItemException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e1.printStackTrace();
		} catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} 
		
		
		ListSelectionModel InvoiceModel = invoiceTable.getSelectionModel();
		btnAgregar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!editorPane.getText().equals("") && invoiceTable.getSelectedRow() != -1) {
					int selectedRow = InvoiceModel.getMinSelectionIndex();
					int IdFactura = Integer.parseInt((String)dtmInvoice.getValueAt(selectedRow, 0));
					
					InvoiceItemDTO invItem = new InvoiceItemDTO(IdFactura, editorPane.getText());
					//SI EL ID DEL INVOICE ITEM NO ESTA DENTRO DEL MAPA, PROCEDO A AGREGARLO
					Integer key = new Integer(invItem.getInvoiceId());
					if(!finalInconsistencys.containsKey(key)) {
						finalInconsistencys.put(new Integer(key), invItem);
					}
					else {
						JOptionPane.showMessageDialog(WrongInvoiceLoad.this, "YA SE REGISTRO UNA CORRECCION SOBRE LA FACTURA", "GG", 1);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(WrongInvoiceLoad.this, "INGRESE UNA DESCRIPCION", "GG", 1);
				}
				editorPane.setText("");
				updateLoadedInvoicesTable();
				
			}
		});
		
		InvoiceModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!InvoiceModel.isSelectionEmpty()) {
					dtmItemFact.setRowCount(0);
					int selectedRow = InvoiceModel.getMinSelectionIndex();
					List<InvoiceDTO> actualInvoices;
					List<ProductItemDTO> invoiceItems;
					try {
						//Busco las facturas del cliente seleccionado en el comboBox
						actualInvoices = Controller.getInstance().getInvoicesByClient(actualClientID);
						for(InvoiceDTO inv : actualInvoices) {
							//Pregunto si el ID de la factura obtenido de la invoiceTable es igual a alguna factura del cliente.
							if(inv.getInvoiceId() == Integer.parseInt((String)dtmInvoice.getValueAt(selectedRow, 0))) {
								invoiceItems = inv.getProductItems();
								for(ProductItemDTO prod : invoiceItems) {
									dtmItemFact.addRow(prod.toDataRow());
								}
							}
						}
					} catch (InvalidClientException | InvalidProductException | InvalidZoneException | InvalidInvoiceException | InvalidProductItemException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e1.printStackTrace();
					}
					 catch (ConnectionException  e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
						e1.printStackTrace();
					} 
				}
			}
		});
			
		btnQuitar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(invoiceTable.getSelectedRow() != -1) {
					int selectedRow = InvoiceModel.getMinSelectionIndex();
					int IdFactura = Integer.parseInt((String)dtmInvoice.getValueAt(selectedRow, 0));
					//SI EL ID DEL INVOICE ITEM NO ESTA DENTRO DEL MAPA, PROCEDO A AGREGARLO
					Integer key = new Integer(IdFactura);
					if(finalInconsistencys.containsKey(IdFactura)) {
						finalInconsistencys.remove(new Integer(key));
					}
					else {
						JOptionPane.showMessageDialog(WrongInvoiceLoad.this, "LA FACTURA SELECCIONADA NO SE ENC", "GG", 1);
					}
					
				}
				else {
					JOptionPane.showMessageDialog(WrongInvoiceLoad.this, "INGRESE UNA DESCRIPCION", "GG", 1);
				}
				editorPane.setText("");
				updateLoadedInvoicesTable();
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisWindow.dispose();
				
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(finalInconsistencys.isEmpty()) {
					JOptionPane.showMessageDialog(WrongInvoiceLoad.this, "La lista esta vacia, por favor cargue alguna factura o presione cancelar para salir", "GG", 1);
				} else {					
					thisWindow.dispose();
				}
			}
		});
	
	}
	
	private void updateLoadedInvoicesTable() {
		dtmLoaded.setRowCount(0);
		for(Entry<Integer, InvoiceItemDTO> i: finalInconsistencys.entrySet()) {
			InvoiceItemDTO inv = i.getValue();
			dtmLoaded.addRow(inv.toDataRow());
		}
	}
}
