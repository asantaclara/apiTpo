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

import controller.Controller;
import dto.ClientDTO;
import dto.ZoneDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidObserverException;
import exceptions.InvalidZoneException;
import observer.Observer;

public class ModifyClient extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCuit, txtNombre,txtDireccion,txtTelefono,txtEmail;
	private JComboBox<ZoneDTO> boxZonas;
	private JComboBox<ClientDTO> boxClientes;
	private JButton btnSalir,btnEliminar,btnAceptar;
	private ModifyClient thisWindow = this;


	public ModifyClient(){
		setResizable(false);
		Configurar();
		Eventos();
	}

	private void Configurar() {
		
		try {
			Controller.getInstance().addObserverToClientService(thisWindow);
			Controller.getInstance().addObserverToZoneService(thisWindow);
		} catch (InvalidObserverException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Error de Observer","ERROR",1);
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 341, 390);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblModificacionDeClientes = new JLabel("MODIFICACION DE CLIENTES");
		lblModificacionDeClientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblModificacionDeClientes.setBounds(58, 11, 210, 19);
		contentPane.add(lblModificacionDeClientes);
		
		boxClientes = new JComboBox<ClientDTO>();
		boxClientes.setBounds(157, 45, 153, 20);
		contentPane.add(boxClientes);
		
		try {
			List<ClientDTO> clientes =Controller.getInstance().getAllActiveClients();
			for(ClientDTO c : clientes) {
				boxClientes.addItem(c);
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
		contentPane.add(boxClientes);
		boxClientes.setSelectedIndex(-1);
		
		JLabel lblSeleccioneElCliente = new JLabel("CLIENTE A MODIFICAR");
		lblSeleccioneElCliente.setBounds(27, 48, 129, 14);
		contentPane.add(lblSeleccioneElCliente);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 81, 312, 2);
		contentPane.add(separator);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(10, 327, 89, 23);
		contentPane.add(btnSalir);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(233, 327, 89, 23);
		contentPane.add(btnAceptar);
		
		btnEliminar = new JButton("ELIMINAR");
		btnEliminar.setToolTipText("Una vez eliminado el cliente, este no podra restablecerse.");
		btnEliminar.setBounds(122, 327, 89, 23);
		contentPane.add(btnEliminar);
		
		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCuit.setBounds(45, 96, 46, 14);
		contentPane.add(lblCuit);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(45, 132, 57, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDireccion = new JLabel("DIRECCION");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDireccion.setBounds(45, 168, 76, 14);
		contentPane.add(lblDireccion);
		
		JLabel lblTelefono = new JLabel("TELEFONO");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefono.setBounds(45, 206, 76, 14);
		contentPane.add(lblTelefono);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(45, 284, 46, 14);
		contentPane.add(lblEmail);
		
		JLabel lblZona = new JLabel("ZONA");
		lblZona.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblZona.setBounds(45, 244, 46, 14);
		contentPane.add(lblZona);
		
		txtCuit = new JTextField();

		txtCuit.setBounds(157, 94, 157, 20);
		contentPane.add(txtCuit);
		txtCuit.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(157, 130, 157, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(157, 166, 157, 20);
		contentPane.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(157, 204, 157, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(157, 282, 157, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		boxZonas = new JComboBox<ZoneDTO>();
		boxZonas.setBounds(157, 242, 157, 20);
		contentPane.add(boxZonas);
		
		//SI quiero cabmiar el JCombo box a Zones en lugar de zones.getName como puedo setear la zona luego en el Combo box, Linea 289
		try {
			List<ZoneDTO> zones = Controller.getInstance().getAllZones();
			for(ZoneDTO z: zones)
				boxZonas.addItem(z);
		} catch ( InvalidZoneException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e1.printStackTrace();
		}
		boxZonas.setSelectedIndex(-1);
	}
	
	private void Eventos() {
		
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!areFieldsEmpty()){
						ClientDTO client = (ClientDTO) boxClientes.getSelectedItem();
						client.setCuit(txtCuit.getText());
						client.setAddress(txtDireccion.getText());
						client.setEmail(txtEmail.getText());
						client.setName(txtNombre.getText());
						client.setPhoneNumber(txtTelefono.getText());
						client.setZone(((ZoneDTO)boxZonas.getSelectedItem()).getName());
						try {
							Controller.getInstance().modifyClient(client);
							JOptionPane.showMessageDialog(thisWindow, "CLIENTE MODIFICADO EXITOSAMENTE", "GG", 1);
						} catch (InvalidClientException  | InvalidZoneException e1) {
							e1.printStackTrace();
							switch (e1.getMessage()) {
							case "Invalid phone number":
								JOptionPane.showMessageDialog(thisWindow, "Numero de telefono invalido, por favor cargue XXXX-XXXX", "ERROR", 1);
								break;
							case "Invalid email":
								JOptionPane.showMessageDialog(thisWindow, "La direccion de email es invalida", "ERROR", 1);
								break;
							case "Invalid cuit":
								JOptionPane.showMessageDialog(thisWindow, "El cuit es invalido, por favor cargue XX-XXXXXXXX-X", "ERROR", 1);
								break;
							case "Existing cuit":
								JOptionPane.showMessageDialog(thisWindow, "El cuit ya esta asociado a otro cliente", "ERROR", 1);
								break;
							case "Invalid address":
								JOptionPane.showMessageDialog(thisWindow, "La direccion es incorrecta", "ERROR", 1);
								break;	
							default:
								JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
								break;
							}
						}catch (ConnectionException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
							e1.printStackTrace();
						} catch (AccessException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(thisWindow, "Complete todos los campos antes de continuar", "GG", 1);						
					}

			}
				
		});
		
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
						super.windowClosed(e);
					} catch (InvalidObserverException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer","ERROR",1);
						e1.printStackTrace();
					}
				}
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boxClientes.getSelectedIndex() != -1) {
					ClientDTO client = (ClientDTO) boxClientes.getSelectedItem();
					
				    int reply = JOptionPane.showConfirmDialog(null, "Estas seguro que deseas eliminar el cliente?", "Confirmacion", JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			          try {
						Controller.getInstance().removeClient(client);
						JOptionPane.showMessageDialog(null, "CLIENTE ELIMINADO CON EXITO");
					} catch (InvalidClientException | InvalidZoneException e1) {
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
			}
		});

		boxClientes.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					ClientDTO client = (ClientDTO) boxClientes.getSelectedItem();
					txtCuit.setText(client.getCuit());
					txtDireccion.setText(client.getAddress());;
					txtEmail.setText(client.getEmail());
					txtNombre.setText(client.getName());
					txtTelefono.setText(client.getPhoneNumber());
					ZoneDTO zoneAux = new ZoneDTO();
					zoneAux.setName(client.getZone());
					boxZonas.setSelectedItem(zoneAux);
				}
			}
		});
		
	}
	
	private void removeObservers() throws InvalidObserverException {
		Controller.getInstance().addObserverToClientService(thisWindow);
		Controller.getInstance().addObserverToZoneService(thisWindow);
	}
	
	/**
	 * @return verdadero en caso de que los ca
	 */
	private boolean areFieldsEmpty() {
		if(txtCuit.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtEmail.getText().isEmpty() 
				|| txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty())
			return true;
		else
			return false;
	}

	@Override
	public void update() {
		ClientDTO client = (ClientDTO) boxClientes.getSelectedItem();
		try {
			boxClientes.removeAllItems();
			List<ClientDTO> clientes =Controller.getInstance().getAllActiveClients();
			
			for(ClientDTO cli : clientes) {
				boxClientes.addItem(cli);
			}
			if(!clientes.contains(client)) {
				client = (ClientDTO) boxClientes.getItemAt(0);
			}
			
			txtCuit.setText(client.getCuit());
			txtDireccion.setText(client.getAddress());;
			txtEmail.setText(client.getEmail());
			txtNombre.setText(client.getName());
			txtTelefono.setText(client.getPhoneNumber());
			boxZonas.setSelectedItem(client.getZone()); 
			
			boxClientes.setSelectedItem(client);
		} catch ( InvalidClientException | InvalidZoneException   e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e1.printStackTrace();
		}
		
	}
}
