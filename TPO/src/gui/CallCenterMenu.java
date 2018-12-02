package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import backEnd.ClaimType;
import controller.Controller;
import dto.ClaimDTO;
import dto.ClientDTO;
import dto.CompositeClaimDTO;
import dto.IncompatibleZoneClaimDTO;
import dto.InvoiceDTO;
import dto.InvoiceItemDTO;
import dto.MoreQuantityClaimDTO;
import dto.ProductItemDTO;
import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidObserverException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;
import observer.Observer;

public class CallCenterMenu extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private int xx,xy;
	private JTable claimTable;
	private JTable invoiceTable;
	private JTable itemsTable;
	private CallCenterMenu thisWindow = this;
	
	private JComboBox<ClientDTO> clientesComboBox;
	private DefaultTableModel dtmClaim;
	private DefaultTableModel dtmInvoice;
	private DefaultTableModel dtmItemFact;
	private JRadioButton rdbtnZonaIncompatible,rdbtnFacturacionIncorrecta,rdbtnProductosFaltantes,rdbtnMayorCantidad,rdbtnCantidadIncorrecta;
	private JEditorPane editorPane;
	private JLabel lblInvoiceID,lblInvoiceSeleccionado,lblReclamosDeCompuestosVisible,lblReclamosDeCompuestos;
	private JButton btnEnviarReclamo;
	
	private List<ProductItemDTO> finalProducts;

	private JButton btnSalir;
	private JButton btnReclamoCompuesto;

	/**
	 * Create the frame.
	 */
	public CallCenterMenu() {
		setResizable(false);
		configuration();
		events();
	}
	
	private void configuration() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1100, 572);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngresoDeReportes = new JLabel("INGRESO DE REPORTES");
		lblIngresoDeReportes.setBackground(Color.CYAN);
		lblIngresoDeReportes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblIngresoDeReportes.setBounds(10, 11, 266, 29);
		contentPane.add(lblIngresoDeReportes);
		
		JLabel lblNumCliente = new JLabel("ID cliente");
		lblNumCliente.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNumCliente.setBounds(38, 61, 86, 25);
		contentPane.add(lblNumCliente);
		
		clientesComboBox = new JComboBox<>();
		clientesComboBox.setBounds(134, 65, 142, 20);
		
		
		try {
			List<ClientDTO> clientes;
			clientes = Controller.getInstance().getAllActiveClients();
			for(ClientDTO c : clientes) {
				clientesComboBox.addItem(c);
			}
		} catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e1.printStackTrace();
		} catch (InvalidClientException | InvalidZoneException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e1.printStackTrace();
		}

		
		contentPane.add(clientesComboBox);
		clientesComboBox.setSelectedIndex(-1);
		
		rdbtnZonaIncompatible = new JRadioButton("Zona Incompatible");
		rdbtnZonaIncompatible.setBounds(32, 93, 142, 23);
		contentPane.add(rdbtnZonaIncompatible);
		
		rdbtnFacturacionIncorrecta = new JRadioButton("Facturacion incorrecta");
		rdbtnFacturacionIncorrecta.setBounds(32, 119, 157, 23);
		contentPane.add(rdbtnFacturacionIncorrecta);
		
		rdbtnProductosFaltantes = new JRadioButton("Productos faltantes");
		rdbtnProductosFaltantes.setBounds(32, 145, 142, 23);
		contentPane.add(rdbtnProductosFaltantes);
		
		rdbtnMayorCantidad = new JRadioButton("Mayor cantidad");
		rdbtnMayorCantidad.setBounds(32, 171, 142, 23);
		contentPane.add(rdbtnMayorCantidad);
		
		rdbtnCantidadIncorrecta = new JRadioButton("Cantidad incorrecta");
		rdbtnCantidadIncorrecta.setBounds(32, 197, 142, 23);
		contentPane.add(rdbtnCantidadIncorrecta);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "DESCRIPCION", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 294, 284, 242);
		contentPane.add(panel);
		panel.setLayout(null);
		
		editorPane = new JEditorPane();
		editorPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		editorPane.setBounds(10, 21, 265, 210);
		panel.add(editorPane);
		
		btnEnviarReclamo = new JButton("");
		btnEnviarReclamo.setIcon(new ImageIcon(CallCenterMenu.class.getResource("/images/btnAzulEnviar.jpg")));

		btnEnviarReclamo.setEnabled(false);
		btnEnviarReclamo.setBounds(760, 491, 174, 45);
		contentPane.add(btnEnviarReclamo);
		
		
		//--------------------------------------INICIO TABLA RECLAMOS-----------------------------------------------------------------
		
		JScrollPane scrollPaneClaim = new JScrollPane();
		scrollPaneClaim.setBounds(298, 34, 786, 249);
		contentPane.add(scrollPaneClaim);
		
		dtmClaim = new DefaultTableModel(){

			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		
		dtmClaim.setColumnCount(6);
		dtmClaim.setRowCount(3);
		
		String col1[] = {"Numero Reclamo","Cliente", "Fecha", "Descripcion","Estado","Tipo"};
		dtmClaim.setColumnIdentifiers(col1);
		claimTable = new JTable(dtmClaim);
		
		scrollPaneClaim.setViewportView(claimTable);
		
		//--------------------------------------FIN TABLA RECLAMOS--------------------------------------------------------------------
		//--------------------------------------INICIO TABLA FACTURAS-----------------------------------------------------------------

		JScrollPane scrollPaneInvoice = new JScrollPane();
		scrollPaneInvoice.setBounds(298,312,334,168);
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
		dtmInvoice.setRowCount(3);
		
		String col2[] = {"ID FACTURA","FECHA", "CLIENTE"};
		dtmInvoice.setColumnIdentifiers(col2);
		invoiceTable = new JTable(dtmInvoice);
		
		scrollPaneInvoice.setViewportView(invoiceTable);
		
		
		//---------------------------------INICIO TABLA ITEMS FACTURA-----------------------------------
		JScrollPane scrollPaneInvItems = new JScrollPane();
		scrollPaneInvItems.setBounds(642, 312, 442, 168);
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
		dtmItemFact.setRowCount(3);
		
		String col3[] = {"ID PRODUCTO","CANTIDAD"};
		dtmItemFact.setColumnIdentifiers(col3);
		itemsTable = new JTable(dtmItemFact);
		
		scrollPaneInvItems.setViewportView(itemsTable);
		
		JLabel lblReclamosDelUsuario = new JLabel("RECLAMOS DEL USUARIO");
		lblReclamosDelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReclamosDelUsuario.setBounds(298, 11, 167, 20);
		contentPane.add(lblReclamosDelUsuario);
		
		JLabel lblFacturas = new JLabel("FACTURAS");
		lblFacturas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFacturas.setBounds(298, 294, 96, 14);
		contentPane.add(lblFacturas);
		
		JLabel lblProductosDeLa = new JLabel("PRODUCTOS DE LA FACTURA");
		lblProductosDeLa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProductosDeLa.setBounds(642, 294, 198, 14);
		contentPane.add(lblProductosDeLa);
		
		lblInvoiceSeleccionado = new JLabel("Factura Seleccionada: ");
		lblInvoiceSeleccionado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInvoiceSeleccionado.setBounds(10, 254, 148, 29);
		contentPane.add(lblInvoiceSeleccionado);
		
		lblInvoiceID = new JLabel("");
		lblInvoiceID.setBounds(152, 254, 65, 29);
		contentPane.add(lblInvoiceID);
		
		lblReclamosDeCompuestosVisible = new JLabel("Reclamos Simples: ");
		lblReclamosDeCompuestosVisible.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReclamosDeCompuestosVisible.setBounds(10, 229, 118, 22);
		contentPane.add(lblReclamosDeCompuestosVisible);
		
		lblReclamosDeCompuestos = new JLabel("");
		lblReclamosDeCompuestos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReclamosDeCompuestos.setBounds(126, 229, 150, 22);
		contentPane.add(lblReclamosDeCompuestos);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 51, 279, 2);
		contentPane.add(separator);
		
		btnSalir = new JButton("");

		btnSalir.setIcon(new ImageIcon(CallCenterMenu.class.getResource("/images/btnNaranjaSalir.jpg")));
		btnSalir.setBounds(517, 491, 174, 45);
		contentPane.add(btnSalir);
		
		btnReclamoCompuesto = new JButton("Reclamo Compuesto");
		btnReclamoCompuesto.setBounds(308, 491, 157, 29);
		contentPane.add(btnReclamoCompuesto);
		
		//Observer
		try {
			Controller.getInstance().addObserverToClaimService(thisWindow);
			Controller.getInstance().addObserverToClientService(thisWindow);
			Controller.getInstance().addObserverToCompositeClaimService(thisWindow);
			Controller.getInstance().addObserverToIncompatibleZoneClaimService(thisWindow);
			Controller.getInstance().addObserverToInvoiceService(thisWindow);
			Controller.getInstance().addObserverToMoreQuantityClaimService(thisWindow);
			Controller.getInstance().addObserverToProductService(thisWindow);
			Controller.getInstance().addObserverToWrongInvoicingClaimService(thisWindow);
			Controller.getInstance().addObserverToZoneService(thisWindow);
		} catch (InvalidObserverException e) {
			JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
			e.printStackTrace();
		}
		
	}
	
	private void events() {
		
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				 xx = e.getX();
			     xy = e.getY();
			}
		});
				
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            thisWindow.setLocation(x - xx, y - xy);  
			}
		});
		
		clientesComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					updateClientComboBox();
				}
			}
		});
		
		btnReclamoCompuesto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String individualClaims = JOptionPane.showInputDialog(thisWindow, "Ingrese separado por espacios los ID de los reclamos simples para formar el compuesto", "Ingresar reclamo compuesto manualmente",1);
				String[] splitted = individualClaims.split(" ");
				CompositeClaimDTO dto = new CompositeClaimDTO();
				dto.setDescription(editorPane.getText());
				dto.setDate(new Date());
				
				try {
					for (int i = 0; i < splitted.length; i++) {
						System.out.println(splitted[i]);
						ClaimDTO claim = Controller.getInstance().getClaimById(Integer.parseInt(splitted[i]));
						if(dto.getClientId() == 0) {
							dto.setClientId(claim.getClientId());
						}
						if(claim.getClass() == dto.getClass()) {
							throw new InvalidClaimException("Claim is composite");
						}
						
						dto.addIndividualClaimId(Integer.valueOf(splitted[i]));
					}
						
						Controller.getInstance().addCompositeClaim(dto);
						
				} catch (InvalidClaimException e2) {
					e2.printStackTrace();
					if(e2.getMessage() == "Claim not found") {
						JOptionPane.showMessageDialog(thisWindow, "Los ID ingresados son incorrectos", "ERROR", 1);
					}else if(e2.getMessage() == "Claim is composite") {
						JOptionPane.showMessageDialog(thisWindow, "Uno de los reclamos es compuesto", "ERROR", 1);
					} else if(e2.getMessage() == "The claim doesn't belong to the client"){
						JOptionPane.showMessageDialog(thisWindow, "Los reclamos no pertenecen al mismo cliente", "ERROR", 1);
					} else if(e2.getMessage() == "Description not found") {
						JOptionPane.showMessageDialog(thisWindow, "No se cargo la descripcion", "ERROR", 1);
					} else {
						e2.printStackTrace();
					}
				} catch(NumberFormatException e3) {
					JOptionPane.showMessageDialog(thisWindow, "Los ID ingresados son incorrectos", "ERROR", 1);
					e3.printStackTrace();
				} catch (ConnectionException | AccessException | InvalidClientException | InvalidZoneException
						| InvalidInvoiceException | InvalidProductException | InvalidProductItemException
						| InvalidUserException | InvalidRoleException | InvalidTransitionException
						| InvalidInvoiceItemException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		ListSelectionModel model = invoiceTable.getSelectionModel();
		
		//EVENTO DONDE SE ESCUCHA UN DOBLE CLICK SOBRE UNA FACTURA 
		invoiceTable.addMouseListener(new MouseAdapter() {

		    public void mousePressed(MouseEvent mouseEvent) {
		    	lblInvoiceID.setText("");
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	lblInvoiceID.setText((String) dtmInvoice.getValueAt(row, 0));
		        }
		    }

		});
		
		model.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!model.isSelectionEmpty()) {
					dtmItemFact.setRowCount(0);
					int selectedRow = model.getMinSelectionIndex();
					List<InvoiceDTO> actualInvoices;
					List<ProductItemDTO> invoiceItems;
					try {
						//Busco las facturas del cliente seleccionado en el comboBox
						ClientDTO actualClient = (ClientDTO)clientesComboBox.getSelectedItem();
						actualInvoices = Controller.getInstance().getInvoicesByClient(actualClient.getId());
						for(InvoiceDTO inv : actualInvoices) {
							//Pregunto si el ID de la factura obtenido de la invoiceTable es igual a alguna factura del cliente.
							if(inv.getInvoiceId() == Integer.parseInt((String)dtmInvoice.getValueAt(selectedRow, 0))) {
								invoiceItems = inv.getProductItems();
								for(ProductItemDTO prod : invoiceItems) {
									dtmItemFact.addRow(prod.toDataRow());
								}
							}
						}
					} catch (ConnectionException  e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
						e1.printStackTrace();
					} catch (InvalidClientException | InvalidProductException | InvalidZoneException | InvalidInvoiceException | InvalidProductItemException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e1.printStackTrace();
					}
				}
			}
		});
		
		ListSelectionModel modelClaim = claimTable.getSelectionModel();
		/**
		 * Evento en el cual se registra un doble click sobre la tabla de Reclamos, si el reclamo seleccionado es de tipo Compuesto, se cargan los ID de reclamos simples
		 * que lo componen.
		 */
		claimTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			    if (e.getClickCount() == 2) {
			      JTable target = (JTable)e.getSource();
			      int row = target.getSelectedRow();
					if(!modelClaim.isSelectionEmpty() && ((String)dtmClaim.getValueAt(row, 5)).compareTo("CompositeClaim") == 0) {
						try {
							lblReclamosDeCompuestos.setText("");
							CompositeClaimDTO claim = (CompositeClaimDTO)Controller.getInstance().getClaimById(Integer.valueOf((String)dtmClaim.getValueAt(row, 0)));
							for (Integer c : claim.getInidividualClaimsId()) {
								lblReclamosDeCompuestos.setText(lblReclamosDeCompuestos.getText() +c.toString()+ "-");
							}
						} catch (ConnectionException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
							e1.printStackTrace();
						} catch (AccessException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
							e1.printStackTrace();
						} catch (InvalidClaimException | InvalidClientException | InvalidInvoiceException | InvalidProductException | InvalidZoneException|
								InvalidProductItemException|InvalidInvoiceItemException | InvalidUserException|InvalidRoleException|InvalidTransitionException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
							e1.printStackTrace();
						}
					}
					else
						lblReclamosDeCompuestos.setText("");
			    }
			  }
		});
		
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Observer
				try {
					removeObservers(thisWindow);
					thisWindow.dispose();
				} catch (InvalidObserverException e1) {
					JOptionPane.showMessageDialog(thisWindow, e1.getMessage(), "Observer Error", 1);
					e1.printStackTrace();
				}
			}
		});
		
		//
		btnEnviarReclamo.setEnabled(true);
		
		btnEnviarReclamo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				List<Integer> claims = new ArrayList<>();
				try {
						ClientDTO actualClient = (ClientDTO) clientesComboBox.getSelectedItem();
						int selectedCount = ((rdbtnZonaIncompatible.isSelected() ? 1 : 0) + (rdbtnFacturacionIncorrecta.isSelected() ? 1 : 0) 
								+ (rdbtnCantidadIncorrecta.isSelected() ? 1 : 0) + (rdbtnProductosFaltantes.isSelected() ? 1 : 0) 
								+ (rdbtnMayorCantidad.isSelected() ? 1 : 0));
						if(selectedCount != 0 && actualClient == null) {
							JOptionPane.showMessageDialog(thisWindow, "No se selecciono cliente", "GG", 1);
						} else if(lblInvoiceID.getText().isEmpty() && (rdbtnCantidadIncorrecta.isSelected() || rdbtnMayorCantidad.isSelected() || rdbtnProductosFaltantes.isSelected())) {
							JOptionPane.showMessageDialog(thisWindow, "NO SE SELECCIONO UNA FACTURA", "GG", 1);
						} else {
							// --------------------------------- incompatible zone ------------------------------------------
							if(rdbtnZonaIncompatible.isSelected()) {
								int i =addIncompatibleZoneClaim(actualClient);
								
								if (i != 0) {
									claims.add(i);
								}
								
								rdbtnZonaIncompatible.setSelected(false);
							}						
							
							//--------------------------------- WRONG INVOICE--------------------------------- 
							if(rdbtnFacturacionIncorrecta.isSelected()) {
								int i =addWrongInvoicingClaim(actualClient);
								
								if (i != 0) {
									claims.add(i);
								}
								rdbtnFacturacionIncorrecta.setSelected(false);
							}
							//--------------------------------- MISSING QUANTITY---------------------------------
							if(rdbtnCantidadIncorrecta.isSelected()) {
								int i = addMissingQuantityClaim(actualClient);
								
								if (i != 0) {
									claims.add(i);
								}
								rdbtnCantidadIncorrecta.setSelected(false);
							}
							//--------------------------------- MISSING PRODUCTS--------------------------------- 
							if(rdbtnProductosFaltantes.isSelected()) {
								int i = addMissingProductsClaim(actualClient);
								
								if (i != 0) {
									claims.add(i);
								}
								rdbtnProductosFaltantes.setSelected(false);
							}
							//--------------------------------- MORE QUANTITY--------------------------------- 
							if(rdbtnMayorCantidad.isSelected()) {
								int i = addMoreQuantityClaim(actualClient);
								
								if (i != 0) {
									claims.add(i);
								}
								rdbtnMayorCantidad.setSelected(false);
							}
							
						}
						
						
						if(claims.size() > 1 && (selectedCount == claims.size())) {
							addCompositeClaim(claims, actualClient);
							JOptionPane.showMessageDialog(thisWindow, "RECLAMO COMPUESTO CARGADO EXITOSAMENTE", "GG", 1);
						} else if(claims.size() == 1 && selectedCount == 1 ) {
								JOptionPane.showMessageDialog(thisWindow, "RECLAMO CARGADO EXITOSAMENTE", "GG", 1);
						} else if (claims.size() != 0){
							String enteredClaims = "";
							
							for (Integer integer : claims) {
								enteredClaims =  enteredClaims + " " + integer;
							}
							JOptionPane.showMessageDialog(thisWindow, "Problema en la carga de reclamos, los reclamos cargados correctamente son los" + enteredClaims, "ERROR", 1);
							
						} else if (selectedCount == 0){
							JOptionPane.showMessageDialog(thisWindow, "No se selecciono ninguna opcion", "ERROR", 1);
						}
						editorPane.setText("");
			} catch ( InvalidClientException | InvalidZoneException | InvalidClaimException | InvalidInvoiceException 
						| InvalidProductException | InvalidProductItemException | SQLException | InvalidInvoiceItemException | InvalidUserException | InvalidRoleException | InvalidTransitionException e) {
				if(e.getMessage() == "Description not found") {
					JOptionPane.showMessageDialog(thisWindow, "Descripcion no ingresada", "ERROR", 1);
				} else {					
					JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
				}
				e.printStackTrace();
			} catch (ConnectionException e) {
				JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
				e.printStackTrace();
			} catch (AccessException e) {
				JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
				e.printStackTrace();
			}
		}
		});
		
		thisWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				//Observer
				try {
					removeObservers(thisWindow);
					super.windowClosed(e);
				} catch (InvalidObserverException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
					e1.printStackTrace();
				}
			}
		});
		
	}

	
	private int addIncompatibleZoneClaim(ClientDTO actualClient) throws InvalidClientException, InvalidClaimException, ConnectionException, AccessException, InvalidZoneException, SQLException {
		IncompatibleZoneClaimDTO incompatibleZoneClaim = new IncompatibleZoneClaimDTO();
		incompatibleZoneClaim.setDescription(editorPane.getText());
		incompatibleZoneClaim.setClientId(actualClient.getId());

		return Controller.getInstance().addIncompatibleZoneClaim(incompatibleZoneClaim);
	}
	
	private int addWrongInvoicingClaim(ClientDTO actualClient) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidInvoiceItemException, InvalidProductItemException {
		WrongInvoicingClaimDTO WrongInvoicingClaim = new WrongInvoicingClaimDTO();
		WrongInvoicingClaim.setClientId(actualClient.getId());
		WrongInvoicingClaim.setDescription(editorPane.getText());
		WrongInvoiceLoad w = new WrongInvoiceLoad(actualClient.getId());
		w.setModal(true);
		w.setVisible(true);
		
		Map<Integer,InvoiceItemDTO> inconsistencias = w.getProductsLoaded();
		
		if(!inconsistencias.isEmpty()) {
			for(Integer i: inconsistencias.keySet()) {
				WrongInvoicingClaim.addInvoiceItemDTO(inconsistencias.get(i).getInvoiceId(), inconsistencias.get(i).getInconsistency());	
			}
			return Controller.getInstance().addWrongInvoicingClaim(WrongInvoicingClaim);
		}
		return 0;
		
	}
	
	private int addMissingQuantityClaim(ClientDTO actualClient) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		if(!lblInvoiceID.getText().isEmpty()) {	
			
			MoreQuantityClaimDTO MqcMissingQuantity = new MoreQuantityClaimDTO();
			MqcMissingQuantity.setClaimType(ClaimType.MISSING_QUANTITY.toString());
			MqcMissingQuantity.setClientId(actualClient.getId());
			MqcMissingQuantity.setDescription(editorPane.getText());
			MqcMissingQuantity.setInvoiceId(Integer.parseInt(lblInvoiceID.getText()));
										
			ProductsLoad p = new ProductsLoad("CANTIDAD INCORRECTA",Integer.parseInt(lblInvoiceID.getText()));
			p.setModal(true);
			p.setVisible(true);
			finalProducts=p.getProductsLoaded();
			//NO CONTINUA HASTA QUE NO FINALIZA LA VENTANA PRODUCTSLOAD
			if(!finalProducts.isEmpty()) {
				for(ProductItemDTO c :finalProducts) {
					MqcMissingQuantity.addProductItemDTO(c);
				}
				return Controller.getInstance().addMoreQuantityClaim(MqcMissingQuantity);
			}	

		}
		return 0;

	}
	
	private int addMissingProductsClaim(ClientDTO actualClient) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		if(!lblInvoiceID.getText().isEmpty()) {	
			MoreQuantityClaimDTO MqcMissingProducts = new MoreQuantityClaimDTO();
			MqcMissingProducts.setClaimType(ClaimType.MISSING_PRODUCT.toString());
			MqcMissingProducts.setClientId(actualClient.getId());
			MqcMissingProducts.setDescription(editorPane.getText());
			MqcMissingProducts.setInvoiceId(Integer.parseInt(lblInvoiceID.getText()));
			
			
			ProductsLoad p = new ProductsLoad("PRODUCTOS FALTANTES",Integer.parseInt(lblInvoiceID.getText()));
			p.setModal(true);
			p.setVisible(true);
			finalProducts = p.getProductsLoaded();
			
			if (!finalProducts.isEmpty()) {
				for(ProductItemDTO c :finalProducts) {
					MqcMissingProducts.addProductItemDTO(c);
				}
				
				return Controller.getInstance().addMoreQuantityClaim(MqcMissingProducts);									
			}
		}
		return 0;
	}
	
	private int addMoreQuantityClaim(ClientDTO actualClient) throws InvalidClaimException, InvalidClientException, ConnectionException, AccessException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException {
		if(!lblInvoiceID.getText().isEmpty()) {	
			MoreQuantityClaimDTO MqcMoreQuantity = new MoreQuantityClaimDTO();
			MqcMoreQuantity.setClaimType(ClaimType.MORE_QUANTITY.toString());
			MqcMoreQuantity.setClientId(actualClient.getId());
			MqcMoreQuantity.setDescription(editorPane.getText());
			MqcMoreQuantity.setInvoiceId(Integer.parseInt(lblInvoiceID.getText()));
		
			ProductsLoad p = new ProductsLoad("MAYOR CANTIDADES",Integer.parseInt(lblInvoiceID.getText()));
			p.setModal(true);
			p.setVisible(true);
			finalProducts=p.getProductsLoaded();
			if(!finalProducts.isEmpty()) {
				for(ProductItemDTO c :finalProducts) {
					MqcMoreQuantity.addProductItemDTO(c);
				}
				return Controller.getInstance().addMoreQuantityClaim(MqcMoreQuantity);
			}
		}
		return 0;
	}
	
	private void addCompositeClaim(List<Integer> claims, ClientDTO actualClient) throws InvalidClaimException, ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidProductException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		CompositeClaimDTO compClaim = new CompositeClaimDTO();
		for(Integer claimId : claims) {
			compClaim.addIndividualClaimId(claimId);
		}
		compClaim.setDescription(editorPane.getText());
		compClaim.setClientId(actualClient.getId());
		Controller.getInstance().addCompositeClaim(compClaim);			
	}
	
	private void removeObservers(Observer thisWindow) throws InvalidObserverException {
		Controller.getInstance().removeObserverToClaimService(thisWindow);
		Controller.getInstance().removeObserverToClientService(thisWindow);
		Controller.getInstance().removeObserverToCompositeClaimService(thisWindow);
		Controller.getInstance().removeObserverToIncompatibleZoneClaimService(thisWindow);
		Controller.getInstance().removeObserverToInvoiceService(thisWindow);
		Controller.getInstance().removeObserverToMoreQuantityClaimService(thisWindow);
		Controller.getInstance().removeObserverToProductService(thisWindow);
		Controller.getInstance().removeObserverToWrongInvoicingClaimService(thisWindow);
		Controller.getInstance().removeObserverToZoneService(thisWindow);
	}
	
	private void updateClientComboBox() {
		ClientDTO actualClient = (ClientDTO) clientesComboBox.getSelectedItem();
		List<ClaimDTO> claimsByClient;
		List<InvoiceDTO> invoicesByClient;
		try {
			dtmItemFact.setRowCount(0); //Limpio la tabla de items cuando cambio de cliente.
			claimsByClient = Controller.getInstance().getClaimsFromClient(actualClient.getId());
			dtmClaim.setRowCount(0);
			//-------------------------
			invoicesByClient = Controller.getInstance().getInvoicesByClient(actualClient.getId());
			dtmInvoice.setRowCount(0);
			
			for(ClaimDTO cl : claimsByClient){
				dtmClaim.addRow(cl.toDataRow());
			}
			
			for(InvoiceDTO inv : invoicesByClient) {
				dtmInvoice.addRow(inv.toDataRow());
			}
		} catch (InvalidInvoiceItemException | InvalidClaimException | InvalidClientException
				| InvalidInvoiceException | InvalidProductException | InvalidZoneException
				| InvalidProductItemException | InvalidUserException | InvalidRoleException | InvalidTransitionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e1.printStackTrace();
		} catch (ConnectionException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} 
	}
	
	@Override
	public void update() {
		//guardo el cliente que tengo seleccionado
		ClientDTO client = (ClientDTO) clientesComboBox.getSelectedItem();
		try {
			clientesComboBox.removeAllItems();
			List<ClientDTO> clientes = Controller.getInstance().getAllActiveClients();
			for(ClientDTO c : clientes) {
				clientesComboBox.addItem(c);
			}
			//seteo el cliente que estaba seleccionado nuevamente
			updateClientComboBox();
			clientesComboBox.setSelectedItem(client);
		} catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} catch (InvalidClientException | InvalidZoneException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		} 
	}
}