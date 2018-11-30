package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class QueryUserMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane,panel;
	private JButton btnRankingDeClientes;
	private JButton btnReclamosPorMes;
	private JButton btnReclamosPorCategoria;
	private JButton btnRankingDeUsuarios;
	private JButton btnEstadoDeReclamos;
	private JButton btnLogs;
	
	public QueryUserMenu() {
		setResizable(false);
		configuration();
		events();
	}

	private void configuration() {
			setBackground(Color.WHITE);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 729, 476);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(220, 20, 60));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			panel = new JPanel();
			panel.setBackground(Color.DARK_GRAY);
			panel.setBounds(0, 0, 346, 490);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("ReportHandler");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblNewLabel.setForeground(new Color(240, 248, 255));
			lblNewLabel.setBounds(86, 314, 178, 27);
			panel.add(lblNewLabel);
			
			JLabel label = new JLabel("");
			
			label.setBounds(-38, 0, 420, 303);
			label.setVerticalAlignment(SwingConstants.TOP);
			label.setIcon(new ImageIcon(Login.class.getResource("/images/bg.jpg")));
			panel.add(label);
			
			JLabel lblWeGotit = new JLabel("....We got it....");
			lblWeGotit.setHorizontalAlignment(SwingConstants.CENTER);
			lblWeGotit.setForeground(new Color(240, 248, 255));
			lblWeGotit.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblWeGotit.setBounds(104, 352, 141, 27);
			panel.add(lblWeGotit);
			
			
			JLabel lblMenuQueryUser = new JLabel("MENU DE CONSULTAS");
			lblMenuQueryUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblMenuQueryUser.setBounds(423, 11, 254, 41);
			contentPane.add(lblMenuQueryUser);
			
			JSeparator separator_1 = new JSeparator();
			separator_1.setBounds(356, 63, 357, 4);
			contentPane.add(separator_1);
			
			JSeparator separator_2 = new JSeparator();
			separator_2.setBounds(356, 159, 357, 4);
			contentPane.add(separator_2);
			
			btnRankingDeClientes = new JButton("Ranking de Clientes");
			btnRankingDeClientes.setBounds(448, 190, 159, 23);
			contentPane.add(btnRankingDeClientes);
			
			btnReclamosPorMes = new JButton("Reclamos por mes");
			btnReclamosPorMes.setBounds(448, 292, 159, 23);
			contentPane.add(btnReclamosPorMes);
			
			btnReclamosPorCategoria = new JButton("Reclamos por categoria");
			btnReclamosPorCategoria.setBounds(448, 258, 159, 23);
			contentPane.add(btnReclamosPorCategoria);
			
			btnRankingDeUsuarios = new JButton("Ranking de Usuarios");
			btnRankingDeUsuarios.setBounds(448, 224, 159, 23);
			contentPane.add(btnRankingDeUsuarios);
			
			btnEstadoDeReclamos = new JButton("ESTADO DE RECLAMOS");
			btnEstadoDeReclamos.setBounds(448, 91, 159, 23);
			contentPane.add(btnEstadoDeReclamos);
			
			btnLogs = new JButton("LOGS");
	
			btnLogs.setBounds(448, 125, 159, 23);
			contentPane.add(btnLogs);

	}

	
	private void events() {
		btnRankingDeUsuarios.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RankingPerUser ru = new RankingPerUser();
				ru.setVisible(true);
				
			}
		});
		
		btnRankingDeClientes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RankingPerClient rc = new RankingPerClient();
				rc.setVisible(true);
				
			}
		});
		
		btnReclamosPorCategoria.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClaimsPerCategory cc = new ClaimsPerCategory();
				cc.setVisible(true);
				
			}
		});
		
		btnReclamosPorMes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClaimsPerMonth cm = new ClaimsPerMonth();
				cm.setVisible(true);
			}
		});
		
		btnEstadoDeReclamos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClaimsForQueryUser cu = new ClaimsForQueryUser();
				cu.setVisible(true);
				
			}
		});
		
		btnLogs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LogsForQueryUser lu = new LogsForQueryUser();
				lu.setVisible(true);
			}
		});
	}
}
