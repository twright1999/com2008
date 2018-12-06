package teacher;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Student;
import Database.DAC;
import Database.DACAdmin;
import Database.DACTeacher;
import Utility.Module;
import Utility.PeriodOfStudy;

import javax.swing.JPasswordField;
import javax.swing.JTable;

public class GradeAdd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTable stdTable;
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
					GradeAdd frame = new GradeAdd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * 
	 * 
	 */
	
	public GradeAdd(String userID) throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nameField = new JTextField();
		nameField.setBounds(23, 98, 102, 28);
		contentPane.add(nameField);
		nameField.setColumns(10);

		JLabel lblStatus = new JLabel("Module");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(161, 69, 51, 16);
		contentPane.add(lblStatus);

		
		JLabel lblTitle = new JLabel("Grade");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(23, 69, 51, 16);
		contentPane.add(lblTitle);
		
		JLabel label = new JLabel("Period");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(306, 69, 51, 16);
		contentPane.add(label);
		
		JButton button = new JButton("Cancel");
		button.setBounds(187, 227, 87, 28);
		contentPane.add(button);
		
		String id = userID;
		
		int userId = Integer.parseInt(id);
		Student student = DAC.getStudent(userId);
		int regNumber = student.getRegNumber();
		
		
		
		
		
		JComboBox titleSelect = new JComboBox();
		lblTitle.setLabelFor(titleSelect);
		Module[] modules = DAC.getCurrentStudentModules(regNumber);
		String[] modulesID = new String[modules.length];
		for (int i = 0; i <modules.length; i++) {
			modulesID[i] = modules[i].getModuleId();
		}
		titleSelect.setModel(new DefaultComboBoxModel(modulesID));
		titleSelect.setBounds(161, 100, 113, 26);
		contentPane.add(titleSelect);
		
		JComboBox roleSelect = new JComboBox();
		lblStatus.setLabelFor(roleSelect);
		PeriodOfStudy[] periods = DAC.getAllStudentPeriods(regNumber);
		String[] periodsID = new String[periods.length];
		for (int i = 0; i <periods.length; i++) {
			if (periods[i] !=  null) {
			periodsID[i] = periods[i].getPeriodID();
			}
		}
		roleSelect.setModel(new DefaultComboBoxModel(periodsID));
		roleSelect.setMaximumRowCount(3);
		roleSelect.setBounds(303, 98, 82, 26);
		contentPane.add(roleSelect);
		
		JButton btnCancel = new JButton("Add Grade");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String gradeString = nameField.getText();
				float gradeFloat = Float.parseFloat(gradeString);
				String moduleID = titleSelect.getSelectedItem().toString();
				String periodID = roleSelect.getSelectedItem().toString();
				try {
					DACTeacher.addInitialGrade(gradeFloat, moduleID, periodID);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				StudentInfoT studentInfoT = new StudentInfoT(id);
				studentInfoT.setVisible(true);
				dispose();
			}
		});
		
		btnCancel.setBounds(306, 227, 124, 28);
		contentPane.add(btnCancel);
	}
}
