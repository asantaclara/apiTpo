package gui;


import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.ProductDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidProductException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JEditorPane;

public class NewProduct extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JButton btnAceptar,btnSalir;
	private JEditorPane editorPane;
	private NewProduct thisWindow = this;
	

	public NewProduct(){
		setResizable(false);
		Configurar();
		Eventos();
	}

	private void Configurar() {
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 338, 264);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		getContentPane().setLayout(null);
		JLabel lblIngresoDeNuevos = new JLabel("INGRESO DE NUEVOS PRODUCTOS");
		lblIngresoDeNuevos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIngresoDeNuevos.setBounds(37, 20, 250, 14);
		contentPane.add(lblIngresoDeNuevos);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombre.setBounds(10, 58, 73, 20);
		contentPane.add(lblNombre);
		
		JLabel lblDescripcion = new JLabel("DESCRIPCION");
		lblDescripcion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcion.setBounds(10, 93, 95, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecio = new JLabel("PRECIO");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecio.setBounds(10, 154, 73, 14);
		contentPane.add(lblPrecio);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(107, 60, 215, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		txtPrecio = new JTextField();
		txtPrecio.setToolTipText("Ingrese el valor numerico sin el signo $");
		txtPrecio.setBounds(107, 153, 145, 20);
		contentPane.add(txtPrecio);
		txtPrecio.setText("0");
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(204, 197, 89, 23);
		contentPane.add(btnAceptar);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(37, 197, 89, 23);
		contentPane.add(btnSalir);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 312, 2);
		contentPane.add(separator);
		
		editorPane = new JEditorPane();
		editorPane.setBounds(107, 89, 215, 53);
		contentPane.add(editorPane);
		
	}
	
	private void Eventos() {
		
		btnAceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProductDTO prod = new ProductDTO();
				
					if(!txtNombre.getText().isEmpty() && !editorPane.getText().isEmpty() && Float.parseFloat(txtPrecio.getText()) > 0) {
						prod.setTitle(txtNombre.getText());
						prod.setDescription(editorPane.getText());
						prod.setPrice(Float.parseFloat(txtPrecio.getText()));
						try {
							Controller.getInstance().addProduct(prod);
							JOptionPane.showMessageDialog(NewProduct.this, "EL PRODUCTO FUE AGREGADO CON EXITO", "GG", 1);
						} catch ( InvalidProductException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
							e1.printStackTrace();
						}catch (ConnectionException  e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
							e1.printStackTrace();
						} catch (AccessException e1) {
							JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
							e1.printStackTrace();
						}
					}
					else {
						JOptionPane.showMessageDialog(NewProduct.this, "Complete todos los campos antes de finalizar", "GG", 1);
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
}
