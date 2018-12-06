package registrar;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Database.DAC;
import Database.DACRegistrar;
import Utility.*;
import Utility.Module;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ModuleSelect extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	public JTextField regNumField;
	public JTextField degIDField;
	protected Component frame;

	public void display_table(int regNumber, String degID) throws SQLException {
		Module[] mod = DAC.getAvailableModules(regNumber, degID);
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Object[] row = new Object[3];
		for(int i=0;i<mod.length;i++) {
			row[0]=mod[i].getModuleId();
			row[1]=mod[i].getName();
			row[2]=mod[i].getCredits();
			model.addRow(row);
		}
	}
	
	/**
	 * Create the frame.
	 */
	public ModuleSelect(String regNo, String degId) {
		setTitle("Available Modules");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(18, 21, 370, 206);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Mod ID", "Name", "Credits"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);

		regNumField = new JTextField();
		regNumField.setVisible(false);
		regNumField.setEditable(false);
		regNumField.setBounds(216, 231, 102, 28);
		regNumField.setText(regNo);
		contentPane.add(regNumField);
		regNumField.setColumns(10);
		
		degIDField = new JTextField();
		degIDField.setVisible(false);
		degIDField.setEditable(false);
		degIDField.setBounds(330, 231, 102, 28);
		degIDField.setText(degId);
		contentPane.add(degIDField);
		degIDField.setColumns(10);
		
		JButton btnAdd_1 = new JButton("Add");
		btnAdd_1.setToolTipText("Select a module and click this button to add to student");
		btnAdd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				DefaultTableModel model= (DefaultTableModel)table.getModel();

				String selected = model.getValueAt(row, 0).toString();
				String regNum = regNumField.getText();
				int regNumber = Integer.parseInt(regNum);
				
					if (row >= 0) {

						model.removeRow(row);

						try {
							DACRegistrar.addStudentModule(regNumber, selected);
							JOptionPane.showMessageDialog(frame,
								    "Add Successful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
							table.revalidate();
						}
						catch (Exception w) {
							JOptionPane.showInputDialog(this, "Connection Error!");
						}
					}
			}
		});
		btnAdd_1.setBounds(18, 229, 87, 28);
		contentPane.add(btnAdd_1);
		
		JButton btnClose = new JButton("Close");
		btnClose.setToolTipText("Click this when finished adding modules");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(117, 229, 87, 28);
		contentPane.add(btnClose);
		
		String regNum = regNumField.getText();
		int regNumber = Integer.parseInt(regNum);
		String degID = degIDField.getText();
		
		try {
			display_table(regNumber, degID);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
