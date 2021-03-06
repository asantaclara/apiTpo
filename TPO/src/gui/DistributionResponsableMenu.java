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
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import backEnd.State;
import controller.Controller;
import dto.ClientDTO;
import dto.MoreQuantityClaimDTO;
import dto.ProductItemDTO;
import dto.TransitionDTO;
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

public class DistributionResponsableMenu extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int userId;
	
	private JPanel contentPane;
	
	int xx,xy;
	private JTable claimTable;
	private JTable invoiceTable;
	private DefaultTableModel dtmClaim;
	private DefaultTableModel dtmInvoice;
	private DistributionResponsableMenu thisWindow = this;
	
	private JComboBox<ClientDTO> clientesComboBox;
	private JComboBox<State> stateComboBox;
	private JEditorPane txtDescripcion;
	
	private JButton btnEnviarReclamo;
	private JButton btnAllClaims;

	private JLabel lblRclamoSeleccionado;
	private JLabel lblReclamoID;

	private JButton btnSalir;

	/**
	 * Create the frame.
	 */
	public DistributionResponsableMenu(int UserId) {
		setResizable(false);
		this.userId = UserId;
		configuration();
		events();
	}
	
	private void configuration() {
		
		//Observer
		try {
			Controller.getInstance().addObserverToClaimService(thisWindow);
			Controller.getInstance().addObserverToClientService(thisWindow);
			Controller.getInstance().addObserverToInvoiceService(thisWindow);
			Controller.getInstance().addObserverToMoreQuantityClaimService(thisWindow);
			Controller.getInstance().addObserverToProductService(thisWindow);
		} catch (InvalidObserverException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
			e1.printStackTrace();
		}

		//
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1001, 572);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngresoDeReportes = new JLabel("RECLAMOS DE DISTRIBUCION");
		lblIngresoDeReportes.setBackground(Color.CYAN);
		lblIngresoDeReportes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIngresoDeReportes.setBounds(30, 7, 218, 29);
		contentPane.add(lblIngresoDeReportes);
		
		JLabel lblNumCliente = new JLabel("ID cliente");
		lblNumCliente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNumCliente.setBounds(50, 58, 86, 25);
		contentPane.add(lblNumCliente);
		
		clientesComboBox = new JComboBox<>();
		clientesComboBox.setBounds(146, 62, 83, 20);
		
		try {
			List<ClientDTO> clientes =Controller.getInstance().getAllActiveClients();
			for(ClientDTO c : clientes) {
				clientesComboBox.addItem(c);
			}
		} catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} catch (InvalidZoneException | InvalidClientException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		} 
		contentPane.add(clientesComboBox);
		clientesComboBox.setSelectedIndex(-1);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Descripci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 337, 581, 162);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtDescripcion = new JEditorPane();
		txtDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDescripcion.setBounds(10, 22, 563, 129);
		panel.add(txtDescripcion);
		
		btnEnviarReclamo = new JButton("");
		btnEnviarReclamo.setIcon(new ImageIcon(CallCenterMenu.class.getResource("/images/btnAzulEnviar.jpg")));

		btnEnviarReclamo.setEnabled(false);
		btnEnviarReclamo.setBounds(680, 498, 174, 38);
		contentPane.add(btnEnviarReclamo);
		
		btnSalir = new JButton("");
		btnSalir.setIcon(new ImageIcon(CallCenterMenu.class.getResource("/images/btnNaranjaSalir.jpg")));
		btnSalir.setBounds(233, 498, 174, 38);
		btnSalir.setEnabled(true);
		contentPane.add(btnSalir);
		
		
		//--------------------------------------INICIO TABLA RECLAMOS-----------------------------------------------------------------
		
		JScrollPane scrollPaneClaim = new JScrollPane();
		scrollPaneClaim.setBounds(298, 34, 677, 298);
		contentPane.add(scrollPaneClaim);
		
		dtmClaim = new DefaultTableModel(){

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		
		dtmClaim.setColumnCount(5);
		dtmClaim.setRowCount(3);
		
		String col1[] = {"Numero Reclamo","Cliente", "Fecha", "Descripcion","Estado"};
		dtmClaim.setColumnIdentifiers(col1);
		claimTable = new JTable(dtmClaim);
		
		scrollPaneClaim.setViewportView(claimTable);
		
		//Carga por primera vez todos los reclamos a la tabla
		setClaimsToModel();
		
		//--------------------------------------FIN TABLA RECLAMOS-----------------------------------------------------------------
		//--------------------------------------INICIO TABLA FACTURAS-----------------------------------------------------------------
	
		JScrollPane scrollPaneInvoice = new JScrollPane();
		scrollPaneInvoice.setBounds(601,342,374,153);
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
		
		String col2[] = {"PRODUCTO","CANTIDAD"};
		dtmInvoice.setColumnIdentifiers(col2);
		invoiceTable = new JTable(dtmInvoice);
		
		scrollPaneInvoice.setViewportView(invoiceTable);


		//---------------------------------FIN TABLA ITEMS FACTURA-----------------------------------
		
		JLabel lblReclamosDelUsuario = new JLabel("RECLAMOS DEL USUARIO");
		lblReclamosDelUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReclamosDelUsuario.setBounds(298, 11, 167, 20);
		contentPane.add(lblReclamosDelUsuario);
		
		JLabel label = new JLabel("");
		label.setBounds(40, 58, 46, 14);
		contentPane.add(label);
		
		btnAllClaims = new JButton("VER TODOS LOS RECLAMOS");
		btnAllClaims.setBounds(40, 126, 198, 23);
		contentPane.add(btnAllClaims);
		
		stateComboBox = new JComboBox<State>(State.values());
		//stateComboBox = new JComboBox<State>();
		stateComboBox.setBounds(128, 265, 141, 20);
		contentPane.add(stateComboBox);
		
		JLabel lblNuevoEstado = new JLabel("Nuevo estado");
		lblNuevoEstado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNuevoEstado.setBounds(10, 263, 98, 20);
		contentPane.add(lblNuevoEstado);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 172, 278, 14);
		contentPane.add(separator);
		
		lblRclamoSeleccionado = new JLabel("Reclamo Seleccionado: ");
		lblRclamoSeleccionado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRclamoSeleccionado.setBounds(10, 197, 148, 29);
		contentPane.add(lblRclamoSeleccionado);
		
		lblReclamoID = new JLabel("");
		lblReclamoID.setBounds(164, 197, 65, 29);
		contentPane.add(lblReclamoID);
		

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
					List<MoreQuantityClaimDTO> claimsByClient;
					ClientDTO actualClient = (ClientDTO) clientesComboBox.getSelectedItem();
					try {
						dtmClaim.setRowCount(0);
						dtmInvoice.setRowCount(0);
						claimsByClient = Controller.getInstance().getAllOpenMoreQuantityClaimsByClient(actualClient.getId());
						
					for(MoreQuantityClaimDTO cl : claimsByClient){
						dtmClaim.addRow(cl.toDataRow());
						
					}
						
					} catch ( InvalidClaimException | InvalidClientException | InvalidInvoiceException | InvalidProductException | InvalidZoneException | InvalidProductItemException 
							| InvalidUserException | InvalidRoleException | InvalidTransitionException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e1.printStackTrace();
					} catch (ConnectionException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
						e1.printStackTrace();
					} 

				}
			}
		});
		
		
		
//		ListSelectionModel model = claimTable.getSelectionModel();

		claimTable.addMouseListener(new MouseAdapter() {

		    public void mousePressed(MouseEvent mouseEvent) {
		    	lblReclamoID.setText("");
		    	//Una vez seleccionado un reclamo, habilito el boton para enviar
		    	btnEnviarReclamo.setEnabled(true);
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	dtmInvoice.setRowCount(0);
		        	lblReclamoID.setText((String) dtmClaim.getValueAt(row, 0));
		        	try {
						List<MoreQuantityClaimDTO> actualClaim = Controller.getInstance().getAllMoreQuantityClaimsDTO();
						//FALTA REALIZAR EL METODO
						//Controller.getInstance().getMoreQuantityClaimById(Integer.parseInt(dtmClaim.getValueAt(row, 0).toString()));
						for(MoreQuantityClaimDTO inv :actualClaim) {
							
							if(inv.getClaimId() == Integer.parseInt(dtmClaim.getValueAt(row, 0).toString())) {
								List <ProductItemDTO> products = inv.getProducts();
								for(ProductItemDTO p: products) {
									dtmInvoice.addRow(p.toDataRow());
								}
							}
						}
					} catch (NumberFormatException | InvalidClaimException | InvalidClientException | InvalidInvoiceException | InvalidProductException	| InvalidZoneException 
							| InvalidProductItemException | InvalidUserException | InvalidRoleException | InvalidTransitionException e) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e.printStackTrace();
					}catch (ConnectionException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
						e1.printStackTrace();
					} 
		        	
		        }
		    }
		});
		
		btnAllClaims.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clientesComboBox.setSelectedIndex(-1);
				setClaimsToModel();
			}
		});

		
		btnEnviarReclamo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TransitionDTO transition = new TransitionDTO();
				transition.setClaimId(Integer.parseInt((lblReclamoID.getText() == "") ? "0" : lblReclamoID.getText()));
				transition.setDescription(txtDescripcion.getText());
				transition.setNewState(stateComboBox.getSelectedItem().toString());
				transition.setResponsableId(userId);
				
			try {
				Controller.getInstance().treatClaim(transition);
				JOptionPane.showMessageDialog(DistributionResponsableMenu.this, "RECLAMO DE FACTURACION INCORRECTA TRATADO CON EXITO", "TRATAMIENTO EXITOSO", 1);
				txtDescripcion.setText("");
				stateComboBox.setSelectedIndex(-1);
				
			} catch ( InvalidClaimException | InvalidClientException | InvalidInvoiceException | InvalidProductException | InvalidZoneException	| InvalidProductItemException 
					| InvalidTransitionException | InvalidUserException | InvalidRoleException | InvalidInvoiceItemException e1) {
				e1.printStackTrace();
				if(e1.getMessage() == "Missing parameters") {
					JOptionPane.showMessageDialog(thisWindow, "Parametros cargados incorrectamente", "ERROR", 1);
				} else if (e1.getMessage().contains("Invalid transition from")){ 
					JOptionPane.showMessageDialog(thisWindow, "No se puede seleccionar un estado igual o previo al actual", "ERROR", 1);
				} else if(e1.getMessage().contains("Invalid description")){
					JOptionPane.showMessageDialog(thisWindow, "Debe cargar la descripcion", "ERROR", 1);
				} else if(e1.getMessage().contains("Incompatible Role")){
					JOptionPane.showMessageDialog(thisWindow, "No tiene permisos para tratar el reclamo", "ERROR", 1);
				}else {					
					JOptionPane.showMessageDialog(thisWindow, "Problemas con la base de datos, comuniquese con el administrador", "ERROR", 1);
				}
			} catch (ConnectionException e1) {
				JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
				e1.printStackTrace();
			} catch (AccessException e1) {
				JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
				e1.printStackTrace();
			}
		
			}
		});

		thisWindow.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
					//Observer
					try {
						removeObservers();
						super.windowClosed(e);
					} catch (InvalidObserverException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Error de observer", "ERROR", 1);
						e1.printStackTrace();
					}
				}
		});
	
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeObservers();
					thisWindow.dispose();
				} catch (InvalidObserverException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(thisWindow, "Error de observer", "ERROR", 1);
				}
			}
		});
	}
	
	private void removeObservers() throws InvalidObserverException {
		Controller.getInstance().removeObserverToClaimService(thisWindow);
		Controller.getInstance().removeObserverToClientService(thisWindow);
		Controller.getInstance().removeObserverToInvoiceService(thisWindow);
		Controller.getInstance().removeObserverToMoreQuantityClaimService(thisWindow);
		Controller.getInstance().removeObserverToProductService(thisWindow);
	}
	
	
	/**
	 * Busca todos los reclamos y los carga al dataModel de la tabla
	 */
	private void setClaimsToModel() {
		try {
			dtmClaim.setRowCount(0);
			//dtmInvoice.setRowCount(0);
			List<MoreQuantityClaimDTO> claims = Controller.getInstance().getAllClaimsForDistributionResponsable();
			for(MoreQuantityClaimDTO c : claims) {
				dtmClaim.addRow(c.toDataRow());
			}
			
			
		} catch ( InvalidClientException | InvalidZoneException	| InvalidUserException | InvalidRoleException | InvalidTransitionException | InvalidClaimException | InvalidInvoiceException 
				| InvalidProductException | InvalidProductItemException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e1.printStackTrace();
		} catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e1.printStackTrace();
		}
		
		
	}

	@Override
	public void update() {
		ClientDTO client = (ClientDTO) clientesComboBox.getSelectedItem();
		
			clientesComboBox.removeAllItems();
			List<ClientDTO> clientes;
			try {
				clientes = Controller.getInstance().getAllActiveClients();
				for(ClientDTO c : clientes) {
					clientesComboBox.addItem(c);
				}
				setClaimsToModel();
				if(!clientes.contains(client)) {
					client = clientesComboBox.getItemAt(0);
				}
				clientesComboBox.setSelectedItem(client);
			} catch (InvalidZoneException | InvalidClientException e) {
				JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
				e.printStackTrace();
			} catch (ConnectionException e1) {
				JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
				e1.printStackTrace();
			} catch (AccessException e1) {
				JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
				e1.printStackTrace();
			} 
		

	}
}
