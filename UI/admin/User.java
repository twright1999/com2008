package admin;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Account;
import Database.DAC;
import Database.DACAdmin;

public class User extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JButton btnAdd;
	private JButton btnCancel;
	private JScrollPane scrollPane;
	private JButton btnDelete;
	protected Component frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User frame = new User();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void display_table() throws SQLException {
		Account[] accounts = DAC.getAccounts();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Object[] row = new Object[3];
		for(int i=0;i<accounts.length;i++) {
			row[0]=accounts[i].getUserID();
			row[1]=accounts[i].getName();
			row[2]=accounts[i].getPermission();
			model.addRow(row);
		}
	}
	
	/**
	 * Create the frame.
	 */
	public User() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserAdd userAdd = new UserAdd();
				userAdd.setVisible(true);
				dispose();
			}
		});
		btnAdd.setBounds(323, 29, 87, 28);
		contentPane.setLayout(null);
		contentPane.add(btnAdd);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(11, 63, 416, 110);
		contentPane.add(scrollPane);
		
				table = new JTable();
				scrollPane.setViewportView(table);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"UserID", "Name", "Permission"
					}
				) {
					boolean[] columnEditables = new boolean[] {
						false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				table.setShowVerticalLines(true);
				table.setShowHorizontalLines(true);
				
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				DefaultTableModel model= (DefaultTableModel)table.getModel();

				int selected = (int) model.getValueAt(row, 0);
				
					if (row >= 0) {

						model.removeRow(row);

						try {
							DACAdmin.removeAccount(selected);
							JOptionPane.showMessageDialog(frame,
								    "Delete Successful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
							table.revalidate();
						}
						catch (Exception w) {
							JOptionPane.showMessageDialog(frame,
								    "Delete Unsuccessful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
						}
					}
			}
		});
		btnDelete.setBounds(21, 185, 87, 28);
		contentPane.add(btnDelete);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUI admin = new AdminUI();
				admin.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(323, 185, 87, 28);
		contentPane.add(btnCancel);
		
		try {
			display_table();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
