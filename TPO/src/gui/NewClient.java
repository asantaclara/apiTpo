package gui;


import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.ClientDTO;
import dto.ZoneDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidZoneException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class NewClient extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCuit, txtNombre,txtDireccion,txtTelefono,txtEmail;
	private JComboBox<ZoneDTO> boxZonas;
	private JButton btnAceptar,btnSalir;
	private NewClient thisWindow = this;

	public NewClient(){
		setResizable(false);
		Configurar();
		Eventos();
	}

	private void Configurar() {
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 299, 360);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngresoDeNuevos = new JLabel("INGRESO DE NUEVOS CLIENTES");
		lblIngresoDeNuevos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIngresoDeNuevos.setBounds(34, 11, 225, 19);
		contentPane.add(lblIngresoDeNuevos);
		
		JLabel lblCuit = new JLabel("CUIT");
		lblCuit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCuit.setBounds(30, 66, 46, 14);
		contentPane.add(lblCuit);
		
		JLabel lblNombre = new JLabel("NOMBRE\r\n");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNombre.setBounds(30, 98, 57, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDireccion = new JLabel("DIRECCION");
		lblDireccion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDireccion.setBounds(30, 134, 76, 14);
		contentPane.add(lblDireccion);
		
		JLabel lblTelefono = new JLabel("TELEFONO");
		lblTelefono.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTelefono.setBounds(30, 172, 76, 14);
		contentPane.add(lblTelefono);
		
		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(30, 250, 46, 14);
		contentPane.add(lblEmail);
		
		JLabel lblZona = new JLabel("ZONA");
		lblZona.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblZona.setBounds(30, 210, 46, 14);
		contentPane.add(lblZona);
		
		txtCuit = new JTextField();

		txtCuit.setBounds(139, 63, 120, 20);
		contentPane.add(txtCuit);
		txtCuit.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(139, 95, 120, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(139, 131, 120, 20);
		contentPane.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(139, 169, 120, 20);
		contentPane.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(139, 250, 120, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		boxZonas = new JComboBox<ZoneDTO>();
		boxZonas.setBounds(139, 207, 120, 20);
		contentPane.add(boxZonas);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(170, 297, 89, 23);
		contentPane.add(btnAceptar);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(30, 297, 89, 23);
		contentPane.add(btnSalir);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 41, 263, 2);
		contentPane.add(separator);
		
		try {
			List<ZoneDTO> zones = Controller.getInstance().getAllZones();
			for(ZoneDTO z: zones)
				boxZonas.addItem(z);
		} catch (ConnectionException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			e.printStackTrace();
		} catch (AccessException e) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			e.printStackTrace();
		} catch (InvalidZoneException e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			e.printStackTrace();
		}
		boxZonas.setSelectedIndex(-1);

		
	}
	
	private void Eventos() {
		
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!txtCuit.getText().isEmpty() && !txtDireccion.getText().isEmpty() && !txtEmail.getText().isEmpty() 
					&& !txtNombre.getText().isEmpty() && !txtTelefono.getText().isEmpty() &&  boxZonas.getSelectedIndex() != -1){
					ClientDTO client = new ClientDTO();
					client.setCuit(txtCuit.getText());
					client.setAddress(txtDireccion.getText());
					client.setEmail(txtEmail.getText());
					client.setName(txtNombre.getText());
					client.setPhoneNumber(txtTelefono.getText());
					client.setZone(String.valueOf(boxZonas.getSelectedItem()));
					try {
						Controller.getInstance().addClient(client);
					} catch (InvalidClientException | InvalidZoneException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
						e1.printStackTrace();
					} catch (ConnectionException  e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
						e1.printStackTrace();
					} catch (AccessException e1) {
						JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(thisWindow, "CLIENTE INGRESADO EXITOSAMENTE", "GG", 1);
					thisWindow.dispose();
				}
				else
					JOptionPane.showMessageDialog(thisWindow, "Complete todos los campos antes de finalizar", "GG", 1);

			}
		});
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisWindow.dispose();
				
			}
		});
		
		txtCuit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char vChar = arg0.getKeyChar();
				if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    arg0.consume();
                }
						
			}
		});
		
		txtNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char vChar = arg0.getKeyChar();
				if (!(Character.isAlphabetic(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    arg0.consume();
                }
						
			}
		});
		
		
		txtTelefono.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char vChar = arg0.getKeyChar();
				if (!(Character.isDigit(vChar)
                        || (vChar == KeyEvent.VK_BACK_SPACE)
                        || (vChar == KeyEvent.VK_DELETE))) {
                    arg0.consume();
                }
						
			}
		});

	}
	
	
}
