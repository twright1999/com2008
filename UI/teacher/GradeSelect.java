package teacher;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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
import Database.DACTeacher;
import Utility.*;
import Utility.Module;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GradeSelect extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	public JTextField regNumField;
	protected Component frame;

	/**
	 * Launch the application.
	 */

	public void display_table(int regNumber) throws SQLException {
		List<List<String>> studentGrades = DACTeacher.getStudentStatus(regNumber);
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Object[] row = new Object[2];
		for(int i=0;i<studentGrades.size();i++) {
			row[0]=studentGrades.get(i).get(0);
			row[1]=studentGrades.get(i).get(1);
			model.addRow(row);
		}
	}
	
	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public GradeSelect(int regNumber, String userID) throws SQLException {
		setTitle("All grades");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(18, 21, 414, 206);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Title", "Mark"
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
		regNumField.setText("1");
		contentPane.add(regNumField);
		regNumField.setColumns(10);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(170, 232, 87, 28);
		contentPane.add(btnClose);
		
		display_table(regNumber);
		
	}
}
