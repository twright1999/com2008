package student;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Student;
import Database.DAC;
import login.Login;

import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Color;

public class StudentUI extends JFrame {

	private JPanel contentPane;
	private JTable studentDetail;
	private JTable table_1;
	private JTextField idField;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentUI frame = new StudentUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public void display_table(int userID) throws SQLException {
		Student student = DAC.getStudent(userID);
		DefaultTableModel model = (DefaultTableModel)studentDetail.getModel();
		Object[] row = new Object[6];
		row[0]=student.getUserID();
		row[1]=student.getName();
		row[2]=student.getEmail();
		row[3]=student.getDegID();
		row[4]=student.getTutor();
		row[5]=student.getRegNumber();
		model.addRow(row);
	}
	
	/**
	 * Create the frame.
	 */
	public StudentUI(int userID) {
		setTitle("Student Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(6, 6, 834, 49);
		contentPane.add(scrollPane);
		
		studentDetail = new JTable();
		studentDetail.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"User ID", "Name", "Email", "Deg ID", "Tutor", "Reg ID"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(studentDetail);
		
		try {
			display_table(userID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(6, 70, 834, 110);
		contentPane.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		scrollPane_1.setViewportView(table_1);
		
		idField = new JTextField();
		idField.setBackground(UIManager.getColor("DesktopPane.background"));
		idField.setBorder(null);
		idField.setEnabled(false);
		idField.setEditable(false);
		idField.setVisible(false);
		idField.setBounds(6, 239, 1, 24);
		contentPane.add(idField);
		idField.setColumns(10);
	}
}
