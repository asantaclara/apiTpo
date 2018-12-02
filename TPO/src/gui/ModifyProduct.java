package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import dto.ProductDTO;
import dto.UserDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidObserverException;
import exceptions.InvalidProductException;
import observer.Observer;

public class ModifyProduct extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtPrecio;
	private JEditorPane editorPane;
	private JButton btnSalir;
	private JButton btnEliminar;
	private JButton btnAceptar;
	private JComboBox<ProductDTO> cbProducts;
	private ModifyProduct thisWindow = this;

	public ModifyProduct(){
		setResizable(false);
		Configurar();
		Eventos();
	}

	private void Configurar() {
		
		try {
			Controller.getInstance().addObserverToProductService(thisWindow);
		} catch (InvalidObserverException e1) {
			JOptionPane.showMessageDialog(thisWindow,"Error de Observer", "ERROR", 1);
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 350);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblModificacionDeProductos = new JLabel("MODIFICACION DE PRODUCTOS");
		lblModificacionDeProductos.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblModificacionDeProductos.setBounds(58, 11, 239, 19);
		contentPane.add(lblModificacionDeProductos);
		
		
		JLabel lblProdSeleccionado = new JLabel("PRODUCTO A MODIFICAR\r\n");
		lblProdSeleccionado.setBounds(10, 58, 150, 14);
		contentPane.add(lblProdSeleccionado);
		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setBounds(260, 284, 89, 23);
		contentPane.add(btnAceptar);
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBounds(10, 284, 89, 23);
		contentPane.add(btnSalir);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 41, 339, 6);
		contentPane.add(separator);
		
		cbProducts = new JComboBox<ProductDTO>();
		cbProducts.setBounds(170, 55, 165, 20);
		
		
		try {
			for(ProductDTO prod :Controller.getInstance().getAllProducts()) {
				cbProducts.addItem(prod);
			}
			cbProducts.setSelectedIndex(-1);
		} catch ( InvalidProductException  e) {
			JOptionPane.showMessageDialog(thisWindow, "Base de datos corrompida! Comuniquese con el administrador de sistema", "ERROR", 1);
			//			e.printStackTrace();
		}catch (ConnectionException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de conexion", "ERROR", 1);
			//			e1.printStackTrace();
		} catch (AccessException e1) {
			JOptionPane.showMessageDialog(thisWindow, "Problemas de acceso a la base de datos", "ERROR", 1);
			//			e1.printStackTrace();
		}
		contentPane.add(cbProducts);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 83, 339, 2);
		contentPane.add(separator_1);
		
		btnEliminar = new JButton("ELIMINAR\r\n");
		btnEliminar.setToolTipText("Una vez eliminado el producto, este no podra restablecerse.");
		btnEliminar.setBounds(135, 284, 89, 23);
		contentPane.add(btnEliminar);
		
		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(10, 125, 69, 14);
		contentPane.add(lblNombre);
		
		JLabel lblDescripcion = new JLabel("DESCRIPCION");
		lblDescripcion.setBounds(10, 166, 79, 14);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrecio = new JLabel("PRECIO");
		lblPrecio.setBounds(10, 232, 46, 14);
		contentPane.add(lblPrecio);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(99, 166, 250, 49);
		contentPane.add(scrollPane);
		
		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(99, 122, 250, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(99, 229, 129, 20);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);
		txtPrecio.setToolTipText("Ingrese el valor numerico sin el signo $");
	}
	
	private void Eventos() {
		
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					removeObservers();
					thisWindow.dispose();
				} catch (InvalidObserverException e1) {
					JOptionPane.showMessageDialog(thisWindow, "Error de observer", "ERROR", 1);
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
						JOptionPane.showMessageDialog(thisWindow, "Error de Observer","ERROR", 1);
						//			e1.printStackTrace();
					}
				}
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cbProducts.getSelectedIndex() != -1) {
				    int reply = JOptionPane.showConfirmDialog(null, "Estas seguro que deseas eliminar el producto?", "Confirmacion", JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			          try {
						Controller.getInstance().removeProduct((ProductDTO)cbProducts.getSelectedItem());
						JOptionPane.showMessageDialog(null, "PRODUCTO ELIMINADO CON EXITO");
					} catch (  InvalidProductException  e1) {
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
		
		
		cbProducts.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					ProductDTO prod = (ProductDTO) cbProducts.getSelectedItem();
					txtNombre.setText(prod.getTitle());
					txtPrecio.setText(String.valueOf(prod.getPrice()));
					editorPane.setText(prod.getDescription());
				}
			}
		});
		
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!txtNombre.getText().isEmpty() && Float.parseFloat(txtPrecio.getText()) > 0 && !editorPane.getText().isEmpty()) {
					ProductDTO prod = (ProductDTO) cbProducts.getSelectedItem();
					prod.setDescription(editorPane.getText());
					prod.setPrice(Float.parseFloat(txtPrecio.getText()));
					prod.setTitle(txtNombre.getText());
					try {
						Controller.getInstance().modifyProduct(prod);
						JOptionPane.showMessageDialog(ModifyProduct.this, "PRODUCTO MODIFICADO EXITOSAMENTE", "GG", 1);
					} catch ( InvalidProductException e1) {
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
				else {
					JOptionPane.showMessageDialog(ModifyProduct.this, "Complete todos los campos antes de continuar", "GG", 1);
				}
				

			}
		});
		
	}
	
	private void removeObservers() throws InvalidObserverException {
		Controller.getInstance().removeObserverToProductService(thisWindow);
	}
	
	@Override
	public void update() {
		ProductDTO actualProd = (ProductDTO) cbProducts.getSelectedItem();
		try {
			cbProducts.removeAllItems();
			List<ProductDTO> clientes =Controller.getInstance().getAllProducts();
			for(ProductDTO c : clientes) {
				cbProducts.addItem(c);
			}
			if(!clientes.contains(actualProd)) {
				actualProd = (ProductDTO) cbProducts.getItemAt(0);
			}
			txtNombre.setText(actualProd.getTitle());
			txtPrecio.setText(String.valueOf(actualProd.getPrice()));
			editorPane.setText(actualProd.getDescription());
			cbProducts.setSelectedItem(actualProd);
			
		} catch ( InvalidProductException  e) {
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
