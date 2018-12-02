package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.ClaimDTO;
import dto.ClientDTO;
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



public class ClaimsForQueryUser extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel dtmClaim;
	private JTable claimTable;
	private JComboBox<ClientDTO> clientesComboBox;
	private ClaimsForQueryUser thisWindow = this;

	/**
	 * Launch the application.
	 */

	public ClaimsForQueryUser(){
		this.setResizable(false);
		configurar();
		eventos();
	}
	
	private void configurar() {
		
		//Observer
		try {
			Controller.getInstance().addObserverToClaimService(thisWindow);
			Controller.getInstance().addObserverToClientService(thisWindow);
			Controller.getInstance().addObserverToCompositeClaimService(thisWindow);
			Controller.getInstance().addObserverToIncompatibleZoneClaimService(thisWindow);
			Controller.getInstance().addObserverToMoreQuantityClaimService(thisWindow);
			Controller.getInstance().addObserverToWrongInvoicingClaimService(thisWindow);
		} catch (InvalidObserverException e) {
			JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 715, 350);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		clientesComboBox = new JComboBox<ClientDTO>();
		clientesComboBox.setBounds(398, 21, 130, 20);
		contentPane.add(clientesComboBox);
		
		
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
		clientesComboBox.setSelectedIndex(-1);
		//--------------------------------------INICIO TABLA RECLAMOS-----------------------------------------------------------------
		
		JScrollPane scrollPaneClaim = new JScrollPane();
		scrollPaneClaim.setBounds(10, 52, 689, 258);
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
		
		
		dtmClaim.setColumnCount(6);
		dtmClaim.setRowCount(3);
		
		String col1[] = {"Numero Reclamo","Cliente", "Fecha", "Descripcion","Estado","Tipo"};
		dtmClaim.setColumnIdentifiers(col1);
		claimTable = new JTable(dtmClaim);
		
		scrollPaneClaim.setViewportView(claimTable);
		
		//--------------------------------------FIN TABLA RECLAMOS--------------------------------------------------------------------
		
		JLabel lblRankingDeUsuarios = new JLabel("ESTADO DE RECLAMOS");
		lblRankingDeUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblRankingDeUsuarios.setBounds(10, 15, 330, 26);
		contentPane.add(lblRankingDeUsuarios);
		

		
		JLabel lblIdCliente = new JLabel("ID CLIENTE");
		lblIdCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdCliente.setBounds(305, 18, 83, 23);
		contentPane.add(lblIdCliente);
		
	}
	
	private void eventos() {
	
		clientesComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					try {
						claimsToTable();
					} catch ( InvalidClaimException | InvalidClientException
							| InvalidInvoiceException | InvalidProductException | InvalidZoneException
							| InvalidProductItemException | InvalidUserException | InvalidRoleException
							| InvalidTransitionException | InvalidInvoiceItemException e1) {
						
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);

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
		
		thisWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					//Observer
					try {
						Controller.getInstance().removeObserverToClaimService(thisWindow);
						Controller.getInstance().removeObserverToClientService(thisWindow);
						Controller.getInstance().removeObserverToCompositeClaimService(thisWindow);
						Controller.getInstance().removeObserverToIncompatibleZoneClaimService(thisWindow);
						Controller.getInstance().removeObserverToMoreQuantityClaimService(thisWindow);
						Controller.getInstance().removeObserverToWrongInvoicingClaimService(thisWindow);
						super.windowClosed(e);
					} catch (InvalidObserverException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
						e1.printStackTrace();
					}
				}
		});
		
		
	}
	
	private void claimsToTable() throws ConnectionException, AccessException, InvalidClaimException, InvalidClientException, InvalidInvoiceException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidInvoiceItemException {
		List<ClaimDTO> claimsByClient;
		
			claimsByClient = Controller.getInstance().getClaimsFromClient((((ClientDTO)clientesComboBox.getSelectedItem())).getId());
			dtmClaim.setRowCount(0);
			for(ClaimDTO cl : claimsByClient){
				dtmClaim.addRow(cl.toDataRow());
			}
		

	}
	@Override
	public void update() {
		ClientDTO client = (ClientDTO) clientesComboBox.getSelectedItem();
		try {
			clientesComboBox.removeAllItems();
			List<ClientDTO> clientes =Controller.getInstance().getAllActiveClients();
			for(ClientDTO c : clientes) {
				clientesComboBox.addItem(c);
			}
			claimsToTable();
			clientesComboBox.setSelectedItem(client);
		} catch (InvalidInvoiceItemException | InvalidClientException 
				| InvalidClaimException | InvalidInvoiceException | InvalidProductException | InvalidZoneException 
				| InvalidProductItemException | InvalidUserException | InvalidRoleException | InvalidTransitionException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		} catch (ConnectionException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		}
	}
	
}
