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
import dto.ClaimQuantityPerMonthDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidObserverException;
import observer.Observer;

public class ClaimsPerMonth extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel dtm;
	private JTable table;
	private ClaimsPerMonth thisWindow = this;



	public ClaimsPerMonth(){
		try {
			this.setResizable(false);
			configurar();
			eventos();
			loadTable();
		} catch (ConnectionException e) {
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
		setBounds(100, 100, 477, 320);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 451, 243);
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
		
		dtm.setColumnCount(2);
		dtm.setRowCount(0);
		
		String col1[] = {"MES","CANTIDAD"};
		dtm.setColumnIdentifiers(col1);
		table = new JTable(dtm);
		
		scrollPane.setViewportView(table);
		
		JLabel lblRankingDeUsuarios = new JLabel("CANTIDAD DE RECLAMOS POR MES");
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

	private void loadTable() throws ConnectionException, AccessException {
		 List<ClaimQuantityPerMonthDTO> claims = Controller.getInstance().getRankingClaimsPerMonth();
		 for(ClaimQuantityPerMonthDTO c : claims)
			 dtm.addRow(c.toDataRow());
	}
	
	@Override
	public void update() {
		try {
			dtm.setRowCount(0);
			loadTable();
		} catch (ConnectionException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		}
	}

}
