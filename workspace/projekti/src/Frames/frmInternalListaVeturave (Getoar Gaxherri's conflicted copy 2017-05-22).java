	package frames;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmInternalListaVeturave extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtMarka;
	private JTextField txtModeli;
	private JTextField txtVitiProdhimit;
	private JTextField textField_6;
	private JTable tblVeturat;
	
	int vid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmInternalListaVeturave frame = new frmInternalListaVeturave();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public frmInternalListaVeturave() {
		setClosable(true);
		setTitle("Lista e Veturave");
		setBounds(100, 100, 641, 473);
		getContentPane().setLayout(null);
		
		JLabel lblFormaPrMenaxhimin = new JLabel("FORMA P\u00CBR MENAXHIMIN E VETURAVE");
		if (Gjuha.gjuha == "albanian") {
			lblFormaPrMenaxhimin.setText("FORMA P\u00CBR MENAXHIMIN E VETURAVE");
		} else {
			lblFormaPrMenaxhimin.setText("CAR MANAGMENT FORM");
		}
		lblFormaPrMenaxhimin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFormaPrMenaxhimin.setBounds(130, 11, 320, 14);
		getContentPane().add(lblFormaPrMenaxhimin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 615, 166);
		getContentPane().add(scrollPane);
		
		JComboBox cmbNderruesi = new JComboBox();
		if (Gjuha.gjuha == "albanian") {
			cmbNderruesi.setModel(new DefaultComboBoxModel(new String[] {"Automatik", "Manual"}));
		} else {
			cmbNderruesi.setModel(new DefaultComboBoxModel(new String[] {"Automatic", "Manual"}));
		}
		cmbNderruesi.setBounds(94, 344, 130, 20);
		getContentPane().add(cmbNderruesi);
		
		JComboBox cmbKarburanti = new JComboBox();
		if (Gjuha.gjuha == "albanian") {
			cmbKarburanti.setModel(new DefaultComboBoxModel(new String[] {"Naft�", "Benzin�"}));
		} else {
			cmbKarburanti.setModel(new DefaultComboBoxModel(new String[] {"Dizel", "Gasoline"}));
		}
		cmbKarburanti.setBounds(94, 373, 130, 20);
		getContentPane().add(cmbKarburanti);
		
		JComboBox cmbKategoria = new JComboBox();
		cmbKategoria.setBounds(94, 314, 130, 20);
		getContentPane().add(cmbKategoria);
		
		try {
			String kategoria = "select kategorite from kategorite;";
			Database.pst = Database.conn.prepareStatement(kategoria);
			Database.res = Database.pst.executeQuery();
			
			while(Database.res.next()) {
				cmbKategoria.addItem(Database.res.getString("kategorite"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		JSpinner spNrUleseve = new JSpinner();
		spNrUleseve.setBounds(384, 284, 91, 20);
		getContentPane().add(spNrUleseve);
		
		JSpinner spShpenzimet = new JSpinner();
		spShpenzimet.setBounds(384, 314, 91, 20);
		getContentPane().add(spShpenzimet);
		
		JSpinner spSasia = new JSpinner();
		spSasia.setBounds(384, 344, 91, 20);
		getContentPane().add(spSasia);
		
		tblVeturat = new JTable();
		tblVeturat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel)tblVeturat.getModel();
				vid = (int)model.getValueAt(tblVeturat.getSelectedRow(), 0);
				
				try {
					String sql = "select * from veturat where vid='"+vid+"'";
					Database.pst = Database.conn.prepareStatement(sql);
					Database.res = Database.pst.executeQuery();
					
					while(Database.res.next()) {
						cmbKategoria.setSelectedIndex(Integer.valueOf(Database.res.getString("kid"))-1);
						txtMarka.setText(Database.res.getString("marka"));
						txtModeli.setText(Database.res.getString("modeli"));
						txtVitiProdhimit.setText(Database.res.getString("vitiProdhimit"));
						cmbNderruesi.setSelectedItem(Database.res.getString("nderruesi"));
						spNrUleseve.setValue(Database.res.getInt("numriUleseve"));
						cmbKarburanti.setSelectedItem(Database.res.getString("karburanti"));
						spShpenzimet.setValue(Database.res.getInt("shpenzimet"));
						spSasia.setValue(Database.res.getInt("sasiaTotale"));
					}
					
					Database.pst.close();
				} catch (Exception e2) {
					
				}
			}
		});
		scrollPane.setViewportView(tblVeturat);
		
		JLabel lblMarka = new JLabel("Marka:");
		if (Gjuha.gjuha == "albanian") {
			lblMarka.setText("Marka:");
		} else {
			lblMarka.setText("Mark:");
		}
		lblMarka.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMarka.setBounds(10, 256, 74, 14);
		getContentPane().add(lblMarka);
		
		txtMarka = new JTextField();
		txtMarka.setColumns(10);
		txtMarka.setBounds(94, 254, 130, 20);
		getContentPane().add(txtMarka);
		
		JLabel lblModeli = new JLabel("Modeli:");
		if (Gjuha.gjuha == "albanian") {
			lblModeli.setText("Modeli:");
		} else {
			lblModeli.setText("Model:");
		}
		lblModeli.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblModeli.setBounds(10, 286, 74, 14);
		getContentPane().add(lblModeli);
		
		txtModeli = new JTextField();
		txtModeli.setColumns(10);
		txtModeli.setBounds(94, 284, 130, 20);
		getContentPane().add(txtModeli);
		
		JLabel lblNderruesi = new JLabel("Nderruesi:");
		if (Gjuha.gjuha == "albanian") {
			lblNderruesi.setText("Nderruesi:");
		} else {
			lblNderruesi.setText("Gear Shifter:");
		}
		lblNderruesi.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNderruesi.setBounds(10, 346, 74, 14);
		getContentPane().add(lblNderruesi);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setForeground(Color.RED);
		lblInfo.setBounds(10, 409, 465, 23);
		getContentPane().add(lblInfo);
		
		JLabel lblVitiProdhimit = new JLabel("Viti Prodhimit:");
		if (Gjuha.gjuha == "albanian") {
			lblVitiProdhimit.setText("Viti Prodhimit:");
		} else {
			lblVitiProdhimit.setText("Year Produced:");
		}
		lblVitiProdhimit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVitiProdhimit.setBounds(271, 256, 91, 14);
		getContentPane().add(lblVitiProdhimit);
		
		txtVitiProdhimit = new JTextField();
		txtVitiProdhimit.setColumns(10);
		txtVitiProdhimit.setBounds(384, 254, 91, 20);
		getContentPane().add(txtVitiProdhimit);
		
		JLabel lblNrUleseve = new JLabel("Nr. Uleseve:");
		if (Gjuha.gjuha == "albanian") {
			lblNrUleseve.setText("Nr. Uleseve:");
		} else {
			lblNrUleseve.setText("Seats:");
		}
		lblNrUleseve.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNrUleseve.setBounds(271, 286, 91, 14);
		getContentPane().add(lblNrUleseve);
		
		JLabel lblShpenzimet = new JLabel("Shpenzimet:");
		if (Gjuha.gjuha == "albanian") {
			lblShpenzimet.setText("Shpenzimet:");
		} else {
			lblShpenzimet.setText("Expenses:");
		}
		lblShpenzimet.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblShpenzimet.setBounds(271, 319, 91, 14);
		getContentPane().add(lblShpenzimet);
		
		JButton btnRuaj = new JButton("Ruaj");
		if (Gjuha.gjuha == "albanian") {
			btnRuaj.setText("Ruaj");
		} else {
			btnRuaj.setText("Save");
		}
		btnRuaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sql = "update veturat set marka='"+ txtMarka.getText() +"', modeli ='" + txtModeli.getText() + "',  kid = '" + cmbKategoria.getSelectedIndex()+1 + "', "
						+ "nderruesi = '" + cmbNderruesi.getSelectedItem() + "', karburanti = '" + cmbKarburanti.getSelectedItem() + "', vitiProdhimit = '" + txtVitiProdhimit.getText() + "',  "
						+ "numriUleseve = '" + spNrUleseve.getValue() + "', shpenzimet = '" + spShpenzimet.getValue() + "', sasiaTotale = '" + spSasia.getValue() + "', sasiaLire='" + spSasia.getValue() + "' where vid ='" + vid + "';";
				
				if ( vid != 0) {
					try {
						Database.executeQueryDB(sql);
						lblInfo.setText("Ruajtja �sht� b�r� me sukses!");
						updateTable();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					lblInfo.setText("Nuk keni zgjedhur asnje rresht!");
				}
				
			}
		});
		btnRuaj.setBounds(516, 280, 89, 35);
		getContentPane().add(btnRuaj);
		
		JButton btnPastro = new JButton("Pastro");
		if (Gjuha.gjuha == "albanian") {
			btnPastro.setText("Pastro");
		} else {
			btnPastro.setText("Clear");
		}
		btnPastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtMarka.setText("");
				txtModeli.setText("");
				txtVitiProdhimit.setText("");
				cmbKarburanti.setSelectedIndex(0);
				cmbKategoria.setSelectedIndex(0);
				cmbNderruesi.setSelectedIndex(0);
				spNrUleseve.setValue(0);
				spShpenzimet.setValue(0);
				spSasia.setValue(0);
			}
		});
		btnPastro.setBounds(516, 371, 89, 35);
		getContentPane().add(btnPastro);
		
		JButton btnFshij = new JButton("Fshij");
		if (Gjuha.gjuha == "albanian") {
			btnFshij.setText("Fshij");
		} else {
			btnFshij.setText("Delete");
		}
		btnFshij.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ( vid == 0) {
					lblInfo.setText("Nuk keni zgjedhur asnje rresht!");
				} else {
					String[] options = new String[] {"Po", "Jo"};
					int pergjigja = JOptionPane.showOptionDialog(null, "A jeni i sigurt?", "Konfirmo",
					        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
					        null, options, options[0]);
					
					String sql="delete from veturat where vid='" + vid + "'";
				
				
					if ( pergjigja == 0 ) {
						try {
							Database.executeQueryDB(sql);
							btnPastro.doClick();
							lblInfo.setText("Vetura u fshi me sukses!");
							updateTable();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, "Duhet te lirohen te gjitha rezervimet para se te behet fshirja e vetures nga databaza!");
						}
					} else {
						lblInfo.setText("");
					}
				}
				
			}
		});
		btnFshij.setBounds(516, 325, 89, 35);
		getContentPane().add(btnFshij);
		
		JButton button_3 = new JButton("K\u00EBrko");
		if (Gjuha.gjuha == "albanian") {
			button_3.setText("K\u00EBrko");
		} else {
			button_3.setText("Search");
		}
		button_3.setBounds(516, 48, 89, 23);
		getContentPane().add(button_3);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(345, 48, 161, 22);
		getContentPane().add(textField_6);
		
		JLabel lblKarburanti = new JLabel("Karburanti:");
		if (Gjuha.gjuha == "albanian") {
			lblKarburanti.setText("Karburanti:");
		} else {
			lblKarburanti.setText("Fuel:");
		}
		lblKarburanti.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblKarburanti.setBounds(10, 375, 74, 14);
		getContentPane().add(lblKarburanti);
		
		JLabel lblSasia = new JLabel("Sasia Totale:");
		if (Gjuha.gjuha == "albanian") {
			lblSasia.setText("Sasia Totale:");
		} else {
			lblSasia.setText("Total Quantity:");
		}
		lblSasia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSasia.setBounds(271, 348, 91, 14);
		getContentPane().add(lblSasia);
		
		JLabel lblKategoria = new JLabel("Kategoria:");
		if (Gjuha.gjuha == "albanian") {
			lblKategoria.setText("Kategoria:");
		} else {
			lblKategoria.setText("Category:");
		}
		lblKategoria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblKategoria.setBounds(10, 317, 74, 17);
		getContentPane().add(lblKategoria);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(256, 253, 1, 139);
		getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(497, 253, 1, 179);
		getContentPane().add(separator_1);
		
		updateTable();
	}
	public void updateTable() {
		try {
			String sql = "select * from listoveturat";
			
			Database.pst = Database.conn.prepareStatement(sql);
			Database.res = Database.pst.executeQuery();
			
			tblVeturat.setModel(DbUtils.resultSetToTableModel(Database.res));
			Database.pst.close();
		} catch (Exception e) {
			
		}
	}
}
