package gui;

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

public class LogsForQueryUser extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LogsForQueryUser thisWindow = this;
	private DefaultTableModel dtmLog;
	private JTable logTable;
	private JScrollPane scrollPaneClaim;
	private JComboBox<ClaimDTO> comboBoxReclamos;
	private JComboBox<ClientDTO> comboBoxClientes;


	public LogsForQueryUser() {
		thisWindow.setResizable(false);
		configurar();
		eventos();
	}

	/**
	 * Create the frame.
	 */
	private void configurar() {
		
		try {
			Controller.getInstance().addObserverToClaimService(thisWindow);
			Controller.getInstance().addObserverToClientService(thisWindow);
		} catch (InvalidObserverException e1) {
			e1.printStackTrace();
		}
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//--------------------------------------INICIO TABLA-----------------------------------------------------------------
		
				scrollPaneClaim = new JScrollPane();
				scrollPaneClaim.setBounds(5, 70, 784, 255);
				contentPane.add(scrollPaneClaim);
				
				dtmLog = new DefaultTableModel(){

				    /**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
				    public boolean isCellEditable(int row, int column) {
				       return false;
				    }
				};
				
				
				dtmLog.setColumnCount(6);
				dtmLog.setRowCount(0);
				
				String col1[] = {"ID RECLAMO","FECHA","USER ID","ESTADO PREVIO","ESTADO ACTUAL","DESCRIPCION"};
				dtmLog.setColumnIdentifiers(col1);
				logTable = new JTable(dtmLog);
				
				scrollPaneClaim.setViewportView(logTable);
				
				comboBoxReclamos = new JComboBox<ClaimDTO>();
				comboBoxReclamos.setBounds(579, 35, 190, 20);
				contentPane.add(comboBoxReclamos);
				
				comboBoxClientes = new JComboBox<ClientDTO>();
				comboBoxClientes.setBounds(329, 35, 199, 20);
				contentPane.add(comboBoxClientes);
				
				//--------------------------------------FIN TABLA -------------------------------------------------------------
				
				try {
					for(ClientDTO c : Controller.getInstance().getAllClients()) {
						comboBoxClientes.addItem(c);
					}
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
				
			comboBoxReclamos.setSelectedIndex(-1);
			comboBoxClientes.setSelectedIndex(-1);
			
			JLabel lblConsultaDeTratamientos = new JLabel("CONSULTA DE TRATAMIENTOS");
			lblConsultaDeTratamientos.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblConsultaDeTratamientos.setBounds(10, 26, 255, 33);
			contentPane.add(lblConsultaDeTratamientos);
				
	}
	
	private void eventos() {
		
		comboBoxClientes.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					dtmLog.setRowCount(0);
					comboBoxReclamos.removeAllItems();
					try {
						List<ClaimDTO> claims = Controller.getInstance().getClaimsFromClient(((ClientDTO)comboBoxClientes.getSelectedItem()).getId());
						for(ClaimDTO claim : claims) {
							comboBoxReclamos.addItem(claim);
						}
					
					} catch ( InvalidClaimException | InvalidClientException
							| InvalidInvoiceException | InvalidProductException | InvalidZoneException
							| InvalidProductItemException | InvalidUserException | InvalidRoleException
							| InvalidTransitionException | InvalidInvoiceItemException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e1.printStackTrace();
					} catch (ConnectionException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
					} 
				}
			}
		});
		
		comboBoxReclamos.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					dtmLog.setRowCount(0);
					try {
						List<TransitionDTO> trans = Controller.getInstance().getAllTransitionsOfClaim(((ClaimDTO)comboBoxReclamos.getSelectedItem()).getClaimId());
						for(TransitionDTO t : trans) {
							dtmLog.addRow(t.toDataRow());
						}
					
					} catch ( InvalidClaimException | InvalidClientException
							| InvalidInvoiceException | InvalidProductException | InvalidZoneException
							| InvalidProductItemException | InvalidUserException | InvalidRoleException
							| InvalidTransitionException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e1.printStackTrace();
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
		
		thisWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					//Observer
					try {
						Controller.getInstance().removeObserverToClaimService(thisWindow);
						Controller.getInstance().removeObserverToClientService(thisWindow);
						super.windowClosed(e);
					} catch (InvalidObserverException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
						e1.printStackTrace();
					}
				}
		});
		
	}

	@Override
	public void update() {
		ClientDTO actualClient = (ClientDTO) comboBoxClientes.getSelectedItem();
		ClaimDTO actualClaim = (ClaimDTO) comboBoxReclamos.getSelectedItem();
		try {
			for(ClientDTO c : Controller.getInstance().getAllClients()) {
				comboBoxClientes.addItem(c);
			}
		} catch ( InvalidClientException | InvalidZoneException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e1.printStackTrace();
		}
		comboBoxClientes.setSelectedItem(actualClient);
		if(actualClaim != null) {
			comboBoxReclamos.setSelectedItem(actualClaim);
		}
	}
}
