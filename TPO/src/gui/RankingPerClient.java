package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.Controller;
import dto.ClientDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidObserverException;
import exceptions.InvalidZoneException;
import observer.Observer;

public class RankingPerClient extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;
	private RankingPerClient thisWindow = this;

	/**
	 * Launch the application.
	 */

	public RankingPerClient(){
		try {
			this.setResizable(false);
			configurar();
			eventos();
			loadTable();
		} catch (InvalidClientException | InvalidZoneException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		}
	}
	
	private void configurar() {
		
		//Observer
		try {
			Controller.getInstance().addObserverToClientService(thisWindow);
			Controller.getInstance().addObserverToCompositeClaimService(thisWindow);
			Controller.getInstance().addObserverToIncompatibleZoneClaimService(thisWindow);
			Controller.getInstance().addObserverToMoreQuantityClaimService(thisWindow);
			Controller.getInstance().addObserverToWrongInvoicingClaimService(thisWindow);
			Controller.getInstance().addObserverToZoneService(thisWindow);
		} catch (InvalidObserverException e) {
			JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 715, 320);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 679, 233);
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
		
		dtm.setColumnCount(6);
		dtm.setRowCount(0);
		
		String col1[] = { "RANKING","CANTIDAD RECLAMOS","ID","NOMBRE","CUIT","ZONA"};
		dtm.setColumnIdentifiers(col1);
		table = new JTable(dtm);
		
		scrollPane.setViewportView(table);
		
		JLabel lblRankingDeUsuarios = new JLabel("RANKING DE CLIENTES");
		lblRankingDeUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblRankingDeUsuarios.setBounds(10, 5, 330, 26);
		contentPane.add(lblRankingDeUsuarios);
		
	}
	
	private void eventos() {
		
		thisWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
					//Observer
					try {
						Controller.getInstance().removeObserverToClientService(thisWindow);
						Controller.getInstance().removeObserverToCompositeClaimService(thisWindow);
						Controller.getInstance().removeObserverToIncompatibleZoneClaimService(thisWindow);
						Controller.getInstance().removeObserverToMoreQuantityClaimService(thisWindow);
						Controller.getInstance().removeObserverToWrongInvoicingClaimService(thisWindow);
						Controller.getInstance().removeObserverToZoneService(thisWindow);
						super.windowClosed(e);
					} catch (InvalidObserverException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
						e1.printStackTrace();
					}
				}
		});
		
	}
	
	private void loadTable() throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException {
			List<ClientDTO> clients = Controller.getInstance().getRankingClientsOfClaims();
			for(ClientDTO cl: clients) 
				dtm.addRow(cl.toDataRow());
	}

	@Override
	public void update() {
		try {
			dtm.setRowCount(0);
			loadTable();
		} catch (InvalidClientException | InvalidZoneException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		}
		
	}

}
