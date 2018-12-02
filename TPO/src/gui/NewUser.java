package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import backEnd.Roles;
import controller.Controller;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class NewUser extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSalir,btnAceptar;
	private JTextField txtNombre,txtNombreUsuario,txtPassword;
	private JComboBox<Roles> boxRolPrincipal;
	private NewUser thisWindow = this;

	public NewUser(){
		setResizable(false);
		Configurar();
		Eventos();
	}

	private void Configurar() {
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 317);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		getContentPane().setLayout(null);
		JLabel lblNuevoUsr = new JLabel("INGRESO DE NUEVO USUARIO");
		lblNuevoUsr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNuevoUsr.setBounds(75, 20, 222, 14);
		contentPane.add(lblNuevoUsr);
		
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(208, 257, 89, 23);
		contentPane.add(btnAceptar);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(37, 257, 89, 23);
		contentPane.add(btnSalir);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 339, 2);
		contentPane.add(separator);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(21, 72, 58, 14);
		contentPane.add(lblNombre);
		
		JLabel lblRolPrincipal = new JLabel("ROL PRINCIPAL");
		lblRolPrincipal.setBounds(21, 108, 105, 14);
		contentPane.add(lblRolPrincipal);
		
		
		JLabel lblNombreDeUsuario = new JLabel("NOMBRE DE USUARIO");
		lblNombreDeUsuario.setBounds(21, 179, 132, 14);
		contentPane.add(lblNombreDeUsuario);
		
		JLabel lblContrasea = new JLabel("CONTRASE\u00D1A");
		lblContrasea.setBounds(21, 213, 83, 14);
		contentPane.add(lblContrasea);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(148, 66, 201, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(148, 173, 201, 20);
		contentPane.add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(148, 210, 201, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		boxRolPrincipal = new JComboBox<Roles>();
		boxRolPrincipal.setBounds(148, 102, 201, 20);
		contentPane.add(boxRolPrincipal);
		
		for(Roles  rol: Roles.values()) {
			boxRolPrincipal.addItem(rol);
		}
		boxRolPrincipal.setSelectedIndex(-1);
		

		
	}
	
	private void Eventos() {
		
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserDTO usr = new UserDTO();
				if(!areFieldsEmpty()) {
					try {
						usr.setName(txtNombre.getText());
						usr.setPassword(txtPassword.getText());
						usr.setPrincipalRole(boxRolPrincipal.getSelectedItem().toString());
						usr.setUserName(txtNombreUsuario.getText());
						Controller.getInstance().addUser(usr);
						JOptionPane.showMessageDialog(null, "USUARIO AGREGADO CON EXITO");
					} catch ( InvalidRoleException | InvalidUserException e1) {
						e1.printStackTrace();
						switch (e1.getMessage()) {
							case "Duplicate userName":
								JOptionPane.showMessageDialog(thisWindow, "Nombre de usuario duplicado, por favor ingrese otro", "ERROR", 1);
								break;
							default:
								JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
								break;
						}
					}catch (ConnectionException  e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
						e1.printStackTrace();
					} 
				}
				else {
					JOptionPane.showMessageDialog(NewUser.this, "Complete todos los campos antes de continuar", "GG", 1);
				}
			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisWindow.dispose();
			}
		});
	}
	
	private boolean areFieldsEmpty() {
		if(txtNombre.getText().isEmpty() || txtPassword.getText().isEmpty() || txtNombreUsuario.getText().isEmpty() || boxRolPrincipal.getSelectedIndex()== -1 ) 
			return true;
		else
			return false;
	}

}
