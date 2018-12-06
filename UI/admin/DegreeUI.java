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

import Database.DAC;
import Database.DACAdmin;
import Utility.Degree;

public class DegreeUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
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
					DegreeUI frame = new DegreeUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void display_table() throws SQLException {
		Degree[] degrees = DAC.getDegrees();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Object[] row = new Object[4];
		for(int i=0;i<degrees.length;i++) {
			row[0]=degrees[i].getDegID();
			row[1]=degrees[i].getName();
			row[2]=degrees[i].getLevelOfStudy();
			row[3]=degrees[i].getDepID();
			model.addRow(row);
		}
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public DegreeUI() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DegreeAdd degAdd = new DegreeAdd();
				degAdd.setVisible(true);
				dispose();
			}
		});
		btnAdd.setBounds(323, 29, 87, 28);
		contentPane.setLayout(null);
		contentPane.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 63, 416, 110);
		contentPane.add(scrollPane);
		
				table = new JTable();
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				scrollPane.setViewportView(table);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Degree ID", "Name", "Level", "Dep ID"
					}
				) {
					boolean[] columnEditables = new boolean[] {
						false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
				table.getColumnModel().getColumn(0).setPreferredWidth(69);
				table.getColumnModel().getColumn(1).setPreferredWidth(228);
				table.getColumnModel().getColumn(2).setPreferredWidth(45);
				table.getColumnModel().getColumn(3).setPreferredWidth(52);
				table.setShowVerticalLines(true);
				table.setShowHorizontalLines(true);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				DefaultTableModel model= (DefaultTableModel)table.getModel();

				String selected = model.getValueAt(row, 0).toString();
				
					if (row >= 0) {

						model.removeRow(row);

						try {
							DACAdmin.removeDegree(selected);
							JOptionPane.showMessageDialog(frame,
								    "Delete Successful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
							table.revalidate();
						}
						catch (Exception w) {
							JOptionPane.showMessageDialog(frame,
								    "Delete unSuccessful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
						}
					}
			}
		});
		btnDelete.setBounds(21, 185, 87, 28);
		contentPane.add(btnDelete);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUI admin = new AdminUI();
				admin.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(323, 185, 87, 28);
		contentPane.add(btnCancel);
		display_table();
	}

}
