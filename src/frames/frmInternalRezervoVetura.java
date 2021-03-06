package frames;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class frmInternalRezervoVetura extends JInternalFrame {

	/**
	 * 
	 */
	int vid;
	int rid;
	int kid;
	
	private static final long serialVersionUID = 1L;
	private JTable tblVeturat;
	private JTable tblRezervimet;
	private JTextField txtRezervuesi;
	private JTextField textNrTelefonit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmInternalRezervoVetura frame = new frmInternalRezervoVetura();
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
	public frmInternalRezervoVetura() {
		setClosable(true);
		setBounds(100, 100, 651, 462);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				updateTable(tabbedPane.getSelectedIndex());
			}
		});
		tabbedPane.setBounds(10, 67, 615, 351);
		getContentPane().add(tabbedPane);
		
		JPanel pnVeturat = new JPanel();
		tabbedPane.addTab("Veturat", null, pnVeturat, null);	
		if (Gjuha.gjuha == "albanian") {
			tabbedPane.addTab("Veturat", null, pnVeturat, null);			
		} else {
			tabbedPane.addTab("Cars", null, pnVeturat, null);
		}
		pnVeturat.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 5, 610, 178);
		scrollPane.setPreferredSize(new Dimension(610, 178));
		pnVeturat.add(scrollPane);
		
		JLabel lblVeturaInfo = new JLabel("");
		lblVeturaInfo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVeturaInfo.setForeground(Color.RED);
		lblVeturaInfo.setBounds(10, 194, 344, 21);
		pnVeturat.add(lblVeturaInfo);
		
		tblVeturat = new JTable();
		tblVeturat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				DefaultTableModel model = (DefaultTableModel)tblVeturat.getModel();
				vid = (int)model.getValueAt(tblVeturat.getSelectedRow(), 0);
				
				try {
					String sql = "select * from veturat where vid='" + vid + "'";
					Database.pst = Database.conn.prepareStatement(sql);
					Database.res = Database.pst.executeQuery();
					
					while(Database.res.next()) {
						kid = Database.res.getInt("kid");
						lblVeturaInfo.setText( Database.res.getString("marka") + " " + Database.res.getString("modeli") + " " + Database.res.getString("vitiProdhimit") 
						+ " " + Database.res.getString("nderruesi") );
					}
					
					Database.pst.close();
				} catch (Exception e2) {
					
				}
			}
		});
		scrollPane.setViewportView(tblVeturat);
		
		JLabel lblEmriMbiermiRezervuesit = new JLabel("Emri Mbiemri Rezervuesit:");
		if ( Gjuha.gjuha == "albanian") {
			lblEmriMbiermiRezervuesit.setText("Emri Mbiemri Rezervuesit:");
		} else {
			lblEmriMbiermiRezervuesit.setText("Renter Name:");
		}
		lblEmriMbiermiRezervuesit.setBounds(10, 226, 138, 15);
		pnVeturat.add(lblEmriMbiermiRezervuesit);
		lblEmriMbiermiRezervuesit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtRezervuesi = new JTextField();
		txtRezervuesi.setBounds(179, 221, 175, 20);
		pnVeturat.add(txtRezervuesi);
		txtRezervuesi.setColumns(10);
		
		JLabel lblNurmiTelefonit = new JLabel("Nurmi Telefonit:");
		if ( Gjuha.gjuha == "albanian") {
			lblNurmiTelefonit.setText("Nurmi Telefonit:");
		} else {
			lblNurmiTelefonit.setText("Telephone Number:");
		}
		lblNurmiTelefonit.setBounds(10, 257, 88, 15);
		pnVeturat.add(lblNurmiTelefonit);
		lblNurmiTelefonit.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		textNrTelefonit = new JTextField();
		textNrTelefonit.setBounds(179, 252, 102, 20);
		pnVeturat.add(textNrTelefonit);
		textNrTelefonit.setColumns(10);
		
		JSpinner spKoha = new JSpinner();
		spKoha.setModel(new SpinnerNumberModel(1, 1, 6, 1));
		spKoha.setBounds(108, 284, 55, 20);
		pnVeturat.add(spKoha);
		
		JComboBox<String> cbKoha = new JComboBox<String>();
		cbKoha.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if ( cbKoha.getSelectedIndex() == 0 ) {
					spKoha.setModel(new SpinnerNumberModel(1, 1, 6, 1));
				} else if (cbKoha.getSelectedIndex() == 1) {
					spKoha.setModel(new SpinnerNumberModel(1, 1, 3, 1));
				} else {
					spKoha.setModel(new SpinnerNumberModel(1, 1, 12, 1));
				}
			}
		});
		
		if ( Gjuha.gjuha == "albanian") {
			cbKoha.setModel(new DefaultComboBoxModel<String>(new String[] {"Dite", "Jave", "Muaj"}));
		} else {
			cbKoha.setModel(new DefaultComboBoxModel<String>(new String[] {"Days", "Weeks", "Months"}));
		}
		cbKoha.setBounds(179, 284, 88, 20);
		pnVeturat.add(cbKoha);
		
		JButton btnNewButton = new JButton("REZERVO");
		if ( Gjuha.gjuha == "albanian") {
			btnNewButton.setText("REZERVO");
		} else {
			btnNewButton.setText("RESERVE");
		}
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtRezervuesi.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Ju lutem plotesoni fushen e rezervuesit!");
				} else if ( textNrTelefonit.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "Ju lutem shkruani numrin kontaktues te rezervuesit!");
				} else {
					
					LocalDate prej = LocalDate.now();
					LocalDate deri = LocalDate.now();
					int koha = (int) spKoha.getValue();
					double cmimi = 0;
					
					try {
						String sql = "select * from kategorite where kid='"+kid+"'";
						Database.pst = Database.conn.prepareStatement(sql);
						Database.res = Database.pst.executeQuery();
						
						while(Database.res.next()) {							
							if ( cbKoha.getSelectedItem() == "Dite") {
								deri = deri.plusDays(koha);
								cmimi = koha * Database.res.getInt("kostojaDitore");
							} else if ( cbKoha.getSelectedItem() == "Jave" ) {
								deri = deri.plusWeeks(koha);
								cmimi = koha * Database.res.getInt("kostojaJavore");
							} else {
								deri = deri.plusMonths(koha);
								cmimi = koha * Database.res.getInt("kostojaMujore");
							}
						}
					} catch (SQLException e) {
						// TODO: handle exception
					}
					

					String sql2 = "UPDATE veturat SET sasiaLire=sasiaLire-1 WHERE vid='" + vid + "';";
					try {
						
						Database.executeQueryDB(sql2);
						
						String sql = "INSERT INTO rezervimet (vid, emriMbiemri, numriTelefonit, prej, deri, cmimi) "
								+ "VALUES ('" + vid + "', '" + txtRezervuesi.getText() + "', '" + textNrTelefonit.getText() + "', '" + prej + "', '" + deri + "', '" + cmimi + "');";
						try {
							Database.executeQueryDB(sql);
							JOptionPane.showMessageDialog(null, "Rezervimi eshte bere nga data: " + prej + " deri: " + deri + ", cmimi="+ cmimi +" euro!");
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(null, "Ju lutem zgjedheni veturen te cilen deshironi ta rezervoni!");
						}
						
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Nuk ka automjete te keti lloji!");
						e.printStackTrace();
					} finally {
						updateTable(tabbedPane.getSelectedIndex());
					}
				}
			}
		});
		btnNewButton.setBounds(477, 240, 109, 47);
		pnVeturat.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNurmiDiteve = new JLabel("Zgjedh kohen:");
		if ( Gjuha.gjuha == "albanian") {
			lblNurmiDiteve.setText("Zgjedh kohen:");
		} else {
			lblNurmiDiteve.setText("Select time:");
		}
		lblNurmiDiteve.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNurmiDiteve.setBounds(10, 288, 88, 15);
		pnVeturat.add(lblNurmiDiteve);
		
		JPanel pnRezervimet = new JPanel();
		if ( Gjuha.gjuha == "albanian") {
			tabbedPane.addTab("Rezervimet", null, pnRezervimet, null);
		} else {
			tabbedPane.addTab("Reserved", null, pnRezervimet, null);
		}
		pnRezervimet.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 5, 610, 178);
		scrollPane_1.setPreferredSize(new Dimension(610, 178));
		pnRezervimet.add(scrollPane_1);
		
		JLabel lblInfo = new JLabel("");
		lblInfo.setForeground(Color.RED);
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInfo.setBounds(10, 207, 242, 20);
		pnRezervimet.add(lblInfo);
		
		JLabel lblVendosCmimin = new JLabel("");
		lblVendosCmimin.setForeground(Color.RED);
		lblVendosCmimin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVendosCmimin.setBounds(54, 238, 65, 14);
		pnRezervimet.add(lblVendosCmimin);
		
		JLabel lblVendosKohen = new JLabel("");
		lblVendosKohen.setForeground(Color.RED);
		lblVendosKohen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblVendosKohen.setBounds(94, 263, 65, 14);
		pnRezervimet.add(lblVendosKohen);
		
		tblRezervimet = new JTable();
		tblRezervimet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				DefaultTableModel model = (DefaultTableModel)tblRezervimet.getModel();
				
				rid = (int)model.getValueAt(tblRezervimet.getSelectedRow(), 0);
				
				double cmimi = (double)model.getValueAt(tblRezervimet.getSelectedRow(), 9);

				Date prej = (Date) model.getValueAt(tblRezervimet.getSelectedRow(), 7);
				Date deri = (Date) model.getValueAt(tblRezervimet.getSelectedRow(), 8);
				
				long diff = deri.getTime() - prej.getTime();
				
				lblVendosCmimin.setText(String.valueOf(cmimi)+ " Euro");
				lblVendosKohen.setText( TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " Dite");
				
				
				try {
					String sql = "select emriMbiemri,numriTelefonit from rezervimet where rid='" + rid + "'";
					Database.pst = Database.conn.prepareStatement(sql);
					Database.res = Database.pst.executeQuery();
					
					while(Database.res.next()) {
						lblInfo.setText( Database.res.getString("emriMbiemri") + " " + Database.res.getString("numriTelefonit") );
					}
					
					Database.pst.close();
				} catch (Exception e2) {
					
				}
			}
		});
		scrollPane_1.setViewportView(tblRezervimet);
		
		JLabel lblCmimi = new JLabel("Cmimi:");
		if ( Gjuha.gjuha == "albanian") {
			lblCmimi.setText("Cmimi:");
		} else {
			lblCmimi.setText("Price:");
		}
		lblCmimi.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCmimi.setBounds(10, 238, 49, 14);
		pnRezervimet.add(lblCmimi);
		
		JLabel lblKohaMbetur = new JLabel("Koha Mbetur:");
		if ( Gjuha.gjuha == "albanian") {
			lblKohaMbetur.setText("Koha Mbetur:");
		} else {
			lblKohaMbetur.setText("Time Left:");
		}
		lblKohaMbetur.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblKohaMbetur.setBounds(10, 263, 88, 14);
		pnRezervimet.add(lblKohaMbetur);
		
		
		JButton btnLiroje = new JButton("LIROJE");
		if ( Gjuha.gjuha == "albanian") {
			btnLiroje.setText("LIROJE");
		} else {
			btnLiroje.setText("RELEASE");
		}
		btnLiroje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sql2 = "UPDATE veturat SET sasiaLire=sasiaLire+1 WHERE vid = (select vid from rezervimet where rid='"+ rid +"');";
				try {
					
					Database.executeQueryDB(sql2);
					
					String sql = "DELETE FROM rezervimet WHERE rid='" + rid + "';";
					try {
						Database.executeQueryDB(sql);
						JOptionPane.showMessageDialog(null, "Vetura u lirua me sukses!");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Ju lutem zgjedheni veturen te cilen deshironi ta lironi!");
						e.printStackTrace();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					updateTable(tabbedPane.getSelectedIndex());
				}
			}
		});
		btnLiroje.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLiroje.setBounds(377, 225, 111, 53);
		pnRezervimet.add(btnLiroje);
		
		JLabel lblFormaPrRezervimin = new JLabel("FORMA P\u00CBR REZERVIMIN E VETURAVE ");
		if ( Gjuha.gjuha == "albanian") {
			lblFormaPrRezervimin.setText("FORMA P\u00CBR REZERVIMIN E VETURAVE ");
		} else {
			lblFormaPrRezervimin.setText("CAR RENTAL FORM");
		}
		lblFormaPrRezervimin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblFormaPrRezervimin.setBounds(145, 25, 320, 14);
		getContentPane().add(lblFormaPrRezervimin);
		
		updateTable(tabbedPane.getSelectedIndex());
		
	}
	
	public void updateTable(int tableNum) {
		try {
			if ( tableNum == 0 ) {
				String sql = "select vid as 'VID', marka as 'Marka', modeli as 'Modeli', vitiProdhimit as 'Viti Prodhimit', nderruesi as 'Nderruesi Shpejt.', numriUleseve as 'Nr. Uleseve', karburanti as 'Karburanti', shpenzimet as 'Shpenzimet', sasiaLire as 'Sasia' from veturat where sasiaLire>0";
				
				Database.pst = Database.conn.prepareStatement(sql);
				Database.res = Database.pst.executeQuery();
				
				tblVeturat.setModel(DbUtils.resultSetToTableModel(Database.res));
				Database.pst.close();				
			} else {
				String sql = "Select * from shfaqrezervimet";
				
				Database.pst = Database.conn.prepareStatement(sql);
				Database.res = Database.pst.executeQuery();
				
				tblRezervimet.setModel(DbUtils.resultSetToTableModel(Database.res));
				Database.pst.close();

			}
		} catch (Exception e) {
			
		}
	}
}
