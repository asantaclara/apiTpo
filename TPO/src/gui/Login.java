package gui;



import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class Login extends JFrame {

	private static final long serialVersionUID = 3391365526767033923L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField password1;
	private JPasswordField password2;
	private Button button;
	private JPanel panel;
	int xx,xy;
	private Login thisWindow = this;

	public Login() {
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
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
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
		
		JLabel lblUnDesarrolloEn = new JLabel("Desarrollo en conjunto de Alejando Santa Clara y Ezequiel Perucca");
		lblUnDesarrolloEn.setForeground(Color.WHITE);
		lblUnDesarrolloEn.setBackground(Color.WHITE);
		lblUnDesarrolloEn.setBounds(10, 390, 336, 36);
		panel.add(lblUnDesarrolloEn);
		
		button = new Button("LOGIN");
		
		button.setFont(new Font("Dialog", Font.BOLD, 12));
		button.setForeground(Color.WHITE);
		button.setBackground(Color.DARK_GRAY);
		button.setBounds(395, 334, 283, 44);
		contentPane.add(button);
		
		txtUserName = new JTextField();
		txtUserName.setBounds(395, 67, 283, 36);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		
		JLabel lblUsername = new JLabel("USUARIO");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUsername.setBounds(395, 42, 114, 14);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("CONTRASENA");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPassword.setBounds(395, 127, 96, 14);
		contentPane.add(lblPassword);
		
		JLabel lblRepeatPassword = new JLabel("REPITA CONTRASENA");
		lblRepeatPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRepeatPassword.setBounds(395, 212, 133, 14);
		contentPane.add(lblRepeatPassword);
		
		password1 = new JPasswordField();
		password1.setBounds(395, 152, 283, 36);
		contentPane.add(password1);
		
		password2 = new JPasswordField();
		password2.setBounds(395, 237, 283, 36);
		contentPane.add(password2);
	}
	
	private void events() {
		/**
		 * Boton salir
		 */
		JLabel lbl_close = new JLabel("X");
		lbl_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				System.exit(0);
			}
		});
		lbl_close.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_close.setForeground(Color.BLACK);
		lbl_close.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_close.setBounds(691, 0, 37, 27);
		contentPane.add(lbl_close);
		
		/**
		 * Boton Login
		 */
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserDTO actualUsr = new UserDTO();
				actualUsr.setUserName(txtUserName.getText());
				if(Arrays.equals(password1.getPassword(),password2.getPassword())) {
					actualUsr.setPassword(String.valueOf(password1.getPassword()));
					
					try {
						interfaceRedirectByRol( Controller.getInstance().getUserByUsernameAndPassword(actualUsr));	
					} catch ( ConnectionException | AccessException | InvalidRoleException e) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e.printStackTrace();
					} catch (InvalidUserException e) {
						JOptionPane.showMessageDialog(thisWindow,"La combinacion de usuario y contrasena es incorrecta, por favor reintente");
						e.printStackTrace();
					} finally {
						txtUserName.setText("");
						password1.setText("");
						password2.setText("");						
					}
					
				}
				else {
					JOptionPane.showMessageDialog(thisWindow,"Las contrasenas no coinciden, intente nuevamente");
					password1.setText("");
					password2.setText("");
				}
			} 
		});
		
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				 xx = e.getX();
			     xy = e.getY();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				
				int x = arg0.getXOnScreen();
	            int y = arg0.getYOnScreen();
	            thisWindow.setLocation(x - xx, y - xy);  
			}
		});
	}
	
	private void interfaceRedirectByRol(UserDTO actualUsr) {
		switch(actualUsr.getSecondaryRole()) {
		case "CALL_CENTER_RESPONSABLE":
			CallCenterMenu menu = new CallCenterMenu();
			menu.setVisible(true);
			break;
		case "DISTRIBUTION_RESPONSABLE":
			DistributionResponsableMenu DistributionMenu = new DistributionResponsableMenu(actualUsr.getUserId());
			DistributionMenu.setVisible(true);
			break;
		case "INVOICING_RESPONSABLE":
			WrongInvoicingClaimMenu InoicingClaiMenu = new WrongInvoicingClaimMenu(actualUsr.getUserId());
			InoicingClaiMenu.setVisible(true);
			break;
		case "ZONE_RESPONSABLE":
			ZoneResponsableMenu zoneRespMenu = new ZoneResponsableMenu(actualUsr.getUserId());
			zoneRespMenu.setVisible(true);
			break;
		case "QUERY_USER":
			QueryUserMenu queryUserMenu = new QueryUserMenu();
			queryUserMenu.setVisible(true);
			break;
		case "ADMINISTRATOR":
			AdministratorMenu admMenu = new AdministratorMenu();
			admMenu.setVisible(true);
			break;
		}
	}
	
}