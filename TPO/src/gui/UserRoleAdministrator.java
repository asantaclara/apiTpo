package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import backEnd.Roles;
import controller.Controller;
import dto.RoleDTO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidObserverException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;
import observer.Observer;

public class UserRoleAdministrator extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSalir,btnEliminarRolSecundario;
	private JComboBox<Roles> cbRoles;
	private JComboBox<UserDTO> boxUsers;
	private JButton btnAgregarRolSecundario;
	private UserRoleAdministrator thisWindow = this;
	private JLabel lblRolPrincipal;
	private JLabel lblRolSecundario;

	public UserRoleAdministrator(){
		setResizable(false);
		Configurar();
		Eventos();
	}

	private void Configurar() {
		
		try {
			Controller.getInstance().addObserverToUserService(thisWindow);
		} catch (InvalidObserverException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 350);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblModificacionDeUsuarios = new JLabel("MODIFICACION DE ROL TEMPORAL");
		lblModificacionDeUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblModificacionDeUsuarios.setBounds(43, 11, 254, 19);
		contentPane.add(lblModificacionDeUsuarios);
		
		
		JLabel lblUsrSeleccionado = new JLabel("USUARIO A MODIFICAR\r\n");
		lblUsrSeleccionado.setBounds(43, 58, 129, 14);
		contentPane.add(lblUsrSeleccionado);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(10, 284, 89, 23);
		contentPane.add(btnSalir);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 339, 6);
		contentPane.add(separator);
		
		JLabel lblRolPrincipalFijo = new JLabel("ROL PRINCIPAL");
		lblRolPrincipalFijo.setBounds(20, 107, 105, 14);
		contentPane.add(lblRolPrincipalFijo);
		
		JLabel lblRolSecundarioFijo = new JLabel("ROL SECUNDARIO");
		lblRolSecundarioFijo.setBounds(20, 172, 105, 14);
		contentPane.add(lblRolSecundarioFijo);
		
		JLabel lblNuevoRol = new JLabel("NUEVO ROL");
		lblNuevoRol.setBounds(129, 206, 132, 14);
		contentPane.add(lblNuevoRol);
		
		cbRoles = new JComboBox<Roles>();
		cbRoles.setBounds(75, 231, 202, 20);
		contentPane.add(cbRoles);
		
		for(Roles  rol: Roles.values()){
			cbRoles.addItem(rol);
		}
		cbRoles.setSelectedIndex(-1);
		
		boxUsers = new JComboBox<UserDTO>();
		boxUsers.setBounds(207, 55, 105, 20);
		contentPane.add(boxUsers);
		
		try {
			for(UserDTO usr :Controller.getInstance().getAllUsers()) {
				boxUsers.addItem(usr);
			}
			boxUsers.setSelectedIndex(-1);
		} catch (InvalidRoleException | InvalidUserException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		} catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} 
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 83, 339, 2);
		contentPane.add(separator_1);
		
		btnEliminarRolSecundario = new JButton("ELIMINAR\r\n");
		btnEliminarRolSecundario.setToolTipText("");
		btnEliminarRolSecundario.setBounds(140, 284, 89, 23);
		contentPane.add(btnEliminarRolSecundario);
		
		btnAgregarRolSecundario = new JButton("AGREGAR");
		btnAgregarRolSecundario.setBounds(260, 287, 89, 23);
		contentPane.add(btnAgregarRolSecundario);
		
		lblRolPrincipal = new JLabel("");
		lblRolPrincipal.setBounds(147, 107, 202, 14);
		contentPane.add(lblRolPrincipal);
		
		lblRolSecundario = new JLabel("");
		lblRolSecundario.setBounds(147, 172, 202, 14);
		contentPane.add(lblRolSecundario);
	}
	
	private void Eventos() {
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeObservers();
					thisWindow.dispose();
				} catch (InvalidObserverException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
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
					} catch (InvalidObserverException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
						e1.printStackTrace();
					}
				}
		});
		
		btnEliminarRolSecundario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserDTO usr = (UserDTO) boxUsers.getSelectedItem();
				RoleDTO rol = new RoleDTO();
				rol.setUserId(usr.getUserId());
				try {
					Controller.getInstance().removeRole(rol);
					JOptionPane.showMessageDialog(thisWindow, "ROL ELIMINADO CON EXITO", "GG", 1);

				} catch (InvalidUserException | InvalidRoleException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
					e1.printStackTrace();
				} catch (ConnectionException  e1) {
					JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
					e1.printStackTrace();
				} catch (AccessException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
					e1.printStackTrace();
				} 
			}
		});
		
		boxUsers.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					UserDTO usr = (UserDTO) boxUsers.getSelectedItem();
					lblRolPrincipal.setText(usr.getPrincipalRole());
					lblRolSecundario.setText(((usr.getPrincipalRole().equals(usr.getSecondaryRole()) ? "NO POSEE" : usr.getSecondaryRole())));
				}
			}
		});
		
		btnAgregarRolSecundario.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RoleDTO rol = new RoleDTO();
				rol.setUserId(((UserDTO)boxUsers.getSelectedItem()).getUserId());
				rol.setRole(((Roles)cbRoles.getSelectedItem()).toString());
				try {
					Controller.getInstance().addRole(rol);
					JOptionPane.showMessageDialog(thisWindow, "ROL AGREGADO CON EXITO", "GG", 1);
				} catch (InvalidUserException | InvalidRoleException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
					e1.printStackTrace();
				} catch (ConnectionException  e1) {
					JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
					e1.printStackTrace();
				} catch (AccessException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
					e1.printStackTrace();
				} 
				
			}
		});
		
	}
	
	private void removeObservers() throws InvalidObserverException {
		Controller.getInstance().removeObserverToUserService(thisWindow);
	}

	@Override
	public void update() {
		UserDTO user = (UserDTO) boxUsers.getSelectedItem();
		try {
			boxUsers.removeAllItems();
			List<UserDTO> users = Controller.getInstance().getAllUsers();
			for(UserDTO c : users) {
				boxUsers.addItem(c);
			}
			boxUsers.setSelectedItem(user);
		} catch ( InvalidRoleException | InvalidUserException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		} catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} 
		
	}
}
