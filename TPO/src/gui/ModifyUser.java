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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import backEnd.Roles;
import controller.Controller;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidObserverException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;
import observer.Observer;

public class ModifyUser extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnSalir,btnAceptar,btnEliminar;
	private JTextField txtNombre,txtNombreUsuario,txtPassword;
	private JComboBox<Roles> boxRolPrincipal;
	private JComboBox<UserDTO> boxUsers;
	private ModifyUser thisWindow = this;

	public ModifyUser(){
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
		
		JLabel lblModificacionDeUsuarios = new JLabel("MODIFICACION DE USUARIOS");
		lblModificacionDeUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblModificacionDeUsuarios.setBounds(58, 11, 210, 19);
		contentPane.add(lblModificacionDeUsuarios);
		
		
		JLabel lblUsrSeleccionado = new JLabel("USUARIO A MODIFICAR\r\n");
		lblUsrSeleccionado.setBounds(43, 58, 164, 14);
		contentPane.add(lblUsrSeleccionado);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(260, 284, 89, 23);
		contentPane.add(btnAceptar);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(10, 284, 89, 23);
		contentPane.add(btnSalir);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 339, 6);
		contentPane.add(separator);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(20, 101, 58, 14);
		contentPane.add(lblNombre);
		
		JLabel lblRolPrincipal = new JLabel("ROL PRINCIPAL");
		lblRolPrincipal.setBounds(20, 137, 105, 14);
		contentPane.add(lblRolPrincipal);
		

		
		JLabel lblNombreDeUsuario = new JLabel("NOMBRE DE USUARIO");
		lblNombreDeUsuario.setBounds(20, 208, 172, 14);
		contentPane.add(lblNombreDeUsuario);
		
		JLabel lblContrasea = new JLabel("CONTRASE\u00D1A");
		lblContrasea.setBounds(20, 242, 132, 14);
		contentPane.add(lblContrasea);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(147, 98, 202, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtNombreUsuario = new JTextField();
		txtNombreUsuario.setBounds(171, 202, 178, 20);
		contentPane.add(txtNombreUsuario);
		txtNombreUsuario.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setToolTipText("Si usted no desea actualizar la contrase\u00F1a por favor deje este campo vacio.");
		txtPassword.setBounds(147, 239, 202, 20);
		contentPane.add(txtPassword);
		txtPassword.setColumns(10);
		
		boxRolPrincipal = new JComboBox<Roles>();
		boxRolPrincipal.setBounds(147, 131, 202, 20);
		contentPane.add(boxRolPrincipal);
		
		
		for(Roles  rol: Roles.values()) {
			boxRolPrincipal.addItem(rol);
		}
		boxRolPrincipal.setSelectedIndex(-1);
		
		boxUsers = new JComboBox<UserDTO>();
		boxUsers.setBounds(207, 55, 105, 20);
		contentPane.add(boxUsers);
		
		try {
			for(UserDTO usr :Controller.getInstance().getAllUsers()) {
				boxUsers.addItem(usr);
			}
			boxUsers.setSelectedIndex(-1);
		} catch ( InvalidRoleException | InvalidUserException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			//			e.printStackTrace();
		}catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			//			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			//			e1.printStackTrace();
		}
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 83, 339, 2);
		contentPane.add(separator_1);
		
		btnEliminar = new JButton("ELIMINAR\r\n");
		btnEliminar.setToolTipText("Una vez eliminado el usuario, este no podra restablecerse.");
		btnEliminar.setBounds(119, 284, 129, 23);
		contentPane.add(btnEliminar);
	}
	
	private void Eventos() {
		
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!areFieldsEmpty()){
						UserDTO usr = (UserDTO) boxUsers.getSelectedItem();
						usr.setName(txtNombre.getText());
						if(!txtPassword.getText().isEmpty())
							usr.setPassword(txtPassword.getText());
							usr.setPrincipalRole(boxRolPrincipal.getSelectedItem().toString());
							usr.setUserName(txtNombreUsuario.getText());
						try {
							Controller.getInstance().modifyUser(usr);
													JOptionPane.showMessageDialog(thisWindow, "USUARIO MODIFICADO EXITOSAMENTE", "GG", 1);
						} catch ( InvalidUserException | InvalidRoleException   e1) {
							switch (e1.getMessage()) {
							case "Duplicate userName":
								JOptionPane.showMessageDialog(thisWindow, "Nombre de usuario duplicado, por favor ingrese otro", "ERROR", 1);
								break;

							default:
								JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
								//			e1.printStackTrace();
								break;
							}
						}catch (ConnectionException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
							//			e1.printStackTrace();
						} catch (AccessException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
							//			e1.printStackTrace();
						}
					}
					else
						JOptionPane.showMessageDialog(thisWindow, "Complete todos los campos antes de continuar", "GG", 1);

				}
				
			});
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeObservers();
					thisWindow.dispose();
				} catch (InvalidObserverException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Error de Observer","ERROR",1);
					//			e1.printStackTrace();
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
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer", "ERROR", 1);
						//			e1.printStackTrace();
					}
				}
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boxUsers.getSelectedIndex() != -1) {
					UserDTO usr = (UserDTO) boxUsers.getSelectedItem();
				    int reply = JOptionPane.showConfirmDialog(null, "Estas seguro que deseas eliminar el usuario?", "Confirmacion", JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			        	try {
							Controller.getInstance().removeUser(usr);
							JOptionPane.showMessageDialog(null, "USUARIO ELIMINADO CON EXITO");
						} catch ( InvalidUserException | InvalidRoleException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
							//			e1.printStackTrace();
						}catch (ConnectionException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
							//			e1.printStackTrace();
						} catch (AccessException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
							//			e1.printStackTrace();
						}
			        }
				}
			}
		});
		
		
//		txtNombre.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				char vChar = arg0.getKeyChar();
//				if (!(Character.isAlphabetic(vChar)
//                        || (vChar == KeyEvent.VK_BACK_SPACE)
//                        || (vChar == KeyEvent.VK_DELETE))) {
//                    arg0.consume();
//                }
//						
//			}
//		});
//		
//		txtNombre.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				char vChar = arg0.getKeyChar();
//				if (!(Character.isAlphabetic(vChar)
//                        || (vChar == KeyEvent.VK_BACK_SPACE)
//                        || (vChar == KeyEvent.VK_DELETE))) {
//                    arg0.consume();
//                }
//						
//			}
//		});
		
		boxUsers.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					UserDTO usr = (UserDTO) boxUsers.getSelectedItem();
					txtNombre.setText(usr.getName());
					txtNombreUsuario.setText(usr.getUserName());
					boxRolPrincipal.setSelectedItem(Roles.valueOf( usr.getPrincipalRole()));
				}
			}
		});
		
	}
	
	private void removeObservers() throws InvalidObserverException {
		Controller.getInstance().removeObserverToUserService(thisWindow);
	}
	
	/**
	 * 
	 * @return verdadero en caso de que los ca
	 */
	private boolean areFieldsEmpty() {
		if(txtNombreUsuario.getText().isEmpty() || txtNombre.getText().isEmpty() 
			|| boxRolPrincipal.getSelectedIndex()== -1 ) {
			return true;
		}else
			return false;
	}

	@Override
	public void update() {
		UserDTO actualUser = (UserDTO)boxUsers.getSelectedItem();
		try {
			boxUsers.removeAllItems();
			List<UserDTO> list = Controller.getInstance().getAllUsers(); 
			
			for(UserDTO usr : list ) {
				boxUsers.addItem(usr);
			}
			if(!list.contains(actualUser)) {
				actualUser = (UserDTO) boxUsers.getItemAt(0);
			}
			txtNombre.setText(actualUser.getName());
			txtNombreUsuario.setText(actualUser.getUserName());
			boxRolPrincipal.setSelectedItem(actualUser.getPrincipalRole());
			boxUsers.setSelectedItem(actualUser);
			
		} catch (InvalidRoleException | InvalidUserException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			//			e.printStackTrace();
		}catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			//			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			//			e1.printStackTrace();
		}
		
	}

}
