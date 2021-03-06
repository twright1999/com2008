package student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Student;
import Database.DAC;
import Database.DACTeacher;
import login.Login;

public class StudentUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable studentDetail;
	private JTable studentGrade;
	private JButton btnLogout;

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
	
	public void displayTable(int regNumber) throws SQLException {
		List<List<String>> grades = DACTeacher.getStudentStatus(regNumber);
		DefaultTableModel model = (DefaultTableModel)studentGrade.getModel();
		Object[] row = new Object[2];
		for(int i=0;i<grades.size();i++) {
			row[0]=grades.get(i).get(0);
			row[1]=grades.get(i).get(1);
			model.addRow(row);
		}
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
		studentDetail.setShowVerticalLines(true);
		studentDetail.setShowHorizontalLines(true);
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
		scrollPane_1.setBounds(6, 70, 834, 156);
		contentPane.add(scrollPane_1);
		
		studentGrade = new JTable();
		studentGrade.setShowVerticalLines(true);
		studentGrade.setShowHorizontalLines(true);
		studentGrade.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Title", "Mark"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane_1.setViewportView(studentGrade);
		
		btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(753, 229, 87, 28);
		contentPane.add(btnLogout);
		int regNumber = (int) studentDetail.getValueAt(0, 5);
		try {
			displayTable(regNumber);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
