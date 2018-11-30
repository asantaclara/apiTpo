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

public class AdministratorMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	int coordenadaXX,coordenadaXY;
	private JButton btnNewClient,btnModificarCliente,btnNuevoUsuario;
	private JButton btnModificarUsuario;
	private JButton btnNuevoProducto;
	private JButton btnModificarProducto;
	private JButton btnModificarRolSecundario;


	public AdministratorMenu() {
		setResizable(false);
		configuration();
		events();
	}

	private void configuration() {
			setBackground(Color.WHITE);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
			
			btnNewClient = new JButton("NUEVO CLIENTE");
			btnNewClient.setBounds(454, 78, 193, 23);
			contentPane.add(btnNewClient);
			
			JLabel lblMenuDeAdministracion = new JLabel("MENU DE ADMINISTRADOR");
			lblMenuDeAdministracion.setFont(new Font("Tahoma", Font.PLAIN, 18));
			lblMenuDeAdministracion.setBounds(423, 11, 254, 41);
			contentPane.add(lblMenuDeAdministracion);
			
			btnModificarCliente = new JButton("MODIFICAR CLIENTE");
			btnModificarCliente.setBounds(454, 131, 193, 23);
			contentPane.add(btnModificarCliente);
			
			btnNuevoUsuario = new JButton("NUEVO USUARIO");

			btnNuevoUsuario.setBounds(454, 184, 193, 23);
			contentPane.add(btnNuevoUsuario);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(356, 169, 357, 4);
			contentPane.add(separator);
			
			btnModificarUsuario = new JButton("MODIFICAR USUARIO");

			btnModificarUsuario.setBounds(454, 229, 193, 23);
			contentPane.add(btnModificarUsuario);
			
			JSeparator separator_1 = new JSeparator();
			separator_1.setBounds(356, 63, 357, 4);
			contentPane.add(separator_1);
			
			JSeparator separator_2 = new JSeparator();
			separator_2.setBounds(356, 307, 357, 4);
			contentPane.add(separator_2);
			
			btnNuevoProducto = new JButton("NUEVO PRODUCTO");

			btnNuevoProducto.setBounds(454, 322, 193, 23);
			contentPane.add(btnNuevoProducto);
			
			btnModificarProducto = new JButton("MODIFICAR PRODUCTO");
			btnModificarProducto.setBounds(454, 367, 193, 23);
			contentPane.add(btnModificarProducto);
			
			JSeparator separator_3 = new JSeparator();
			separator_3.setBounds(356, 401, 357, 4);
			contentPane.add(separator_3);
			
			btnModificarRolSecundario = new JButton("MODIFICAR ROL SECUNDARIO");

			btnModificarRolSecundario.setBounds(454, 273, 193, 23);
			contentPane.add(btnModificarRolSecundario);
					

	}

	
	private void events() {
		
		btnNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewUser newUsrGui = new NewUser();
				newUsrGui.setVisible(true);
			}
		});
		
		btnModificarCliente.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ModifyClient mc = new ModifyClient();
				mc.setVisible(true);
			}
		});
		
		btnNewClient.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				NewClient nc = new NewClient();
				nc.setVisible(true);
			}
		});
		
		btnModificarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ModifyUser mu = new ModifyUser();
				mu.setVisible(true);
			}
		});
		
		btnNuevoProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewProduct np = new NewProduct();
				np.setVisible(true);
			}
		});
		
		btnModificarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyProduct mp = new ModifyProduct();
				mp.setVisible(true);
				
			}
		});
		
		btnModificarRolSecundario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserRoleAdministrator ua = new UserRoleAdministrator();
				ua.setVisible(true);
			}
		});
	}
}
