package teacher;

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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Student;
import Database.DAC;
import Database.DACRegistrar;
import Utility.PeriodOfStudy;
import Utility.Grade;
import Utility.Module;

import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class StudentInfoT extends JFrame {

	private JPanel contentPane;
	private JTable stdTable;
	static JTable table;
	protected Component frame;
	public JTextField userIDField;

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
					StudentInfo frame = new StudentInfo();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public void display_table_student(int userID) throws SQLException {
		Student student = DAC.getStudent(userID);
		//PeriodOfStudy period = DAC.getStudentPeriodOfStudy(regNumber, label);
		DefaultTableModel model = (DefaultTableModel)stdTable.getModel();
		Object[] row = new Object[3];
		row[0]=student.getRegNumber();
		row[1]=student.getName();
		//row[2]=student.getLevel();
		row[2]=student.getDegID();
		model.addRow(row);
	}
	
	public void display_table_module(int regNumber) throws SQLException {
		Module[] mod = DAC.getCurrentStudentModules(regNumber);
		Grade[] grade = DAC.getStudentGrades(regNumber);
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
	public StudentInfoT(String userID) {
		setTitle("Student Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAddModule = new JButton("Add Module");
		btnAddModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GradeSelect modPick = new GradeSelect();
				
				DefaultTableModel model= (DefaultTableModel)stdTable.getModel();
				
				String regNumber = model.getValueAt(0, 0).toString();
				String degID = model.getValueAt(0, 4).toString();
				
				modPick.setVisible(true);
				modPick.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				modPick.regNumField.setText(regNumber);
				modPick.degIDField.setText(degID);
			}
		});
		
		userIDField = new JTextField();
		userIDField.setEditable(false);
		userIDField.setBounds(22, 6, 76, 28);
		userIDField.setText(userID);
		contentPane.add(userIDField);
		userIDField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(6, 352, 4, -4);
		contentPane.add(scrollPane);
		
		stdTable = new JTable();
		stdTable.setShowVerticalLines(true);
		stdTable.setShowHorizontalLines(true);
		stdTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Reg ID", "Name", "Level", "Degree ID"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(stdTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(22, 42, 541, 192);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Module", "Initial Grade", "Resit Grade"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		btnAddModule.setBounds(22, 320, 109, 28);
		contentPane.add(btnAddModule);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(475, 320, 109, 28);
		contentPane.add(btnClose);
		
		JButton btnDeleteModule = new JButton("Delete Module");
		btnDeleteModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				DefaultTableModel model= (DefaultTableModel)table.getModel();
				DefaultTableModel stdModel= (DefaultTableModel)stdTable.getModel();

				String selected = model.getValueAt(row, 0).toString();
				int regID = (int)stdModel.getValueAt(0, 0);
				
					if (row >= 0) {

						model.removeRow(row);

						try {
							DACRegistrar.dropModule(regID, selected);
							JOptionPane.showMessageDialog(frame,
								    "Delete Successful",
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
		btnDeleteModule.setBounds(159, 320, 109, 28);
		contentPane.add(btnDeleteModule);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.revalidate();
			}
		});
		btnRefresh.setBounds(376, 320, 87, 28);
		contentPane.add(btnRefresh);
		
		String id = userIDField.getText();
		
		int userId = Integer.parseInt(id);
		DefaultTableModel model = (DefaultTableModel)stdTable.getModel();
		//int regNumber = (int)model.getValueAt(0, 0);
		//char label = (char)model.getValueAt(0, 2);
		
		try {
			display_table_student(userId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		try {
			display_table_module(regNumber);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
	}
}
