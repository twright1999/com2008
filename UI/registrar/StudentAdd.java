package registrar;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Accounts.Student;
import Database.DAC;
import Database.DACRegistrar;

public class StudentAdd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField idField;
	private JTextField ptField;
	private JTextField degIDField;
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
					StudentAdd frame = new StudentAdd();
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
	public StudentAdd() {
		setTitle("New Student");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserID = new JLabel("User ID");
		lblUserID.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserID.setBounds(25, 25, 85, 16);
		contentPane.add(lblUserID);
		
		idField = new JTextField();
		lblUserID.setLabelFor(idField);
		idField.setBounds(122, 19, 102, 28);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel lblPersonalTutor = new JLabel("Personal Tutor");
		lblPersonalTutor.setVerticalAlignment(SwingConstants.CENTER);
		lblPersonalTutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonalTutor.setForeground(Color.BLACK);
		lblPersonalTutor.setEnabled(true);
		lblPersonalTutor.setBounds(25, 65, 85, 16);
		contentPane.add(lblPersonalTutor);
		
		ptField = new JTextField();
		lblPersonalTutor.setLabelFor(ptField);
		ptField.setBounds(122, 59, 102, 28);
		contentPane.add(ptField);
		ptField.setColumns(10);
		
		JLabel lblDegreeId = new JLabel("Degree ID");
		lblDegreeId.setHorizontalAlignment(SwingConstants.CENTER);
		lblDegreeId.setBounds(25, 108, 85, 16);
		contentPane.add(lblDegreeId);
		
		degIDField = new JTextField();
		degIDField.setBounds(122, 99, 102, 28);
		contentPane.add(degIDField);
		degIDField.setColumns(10);
		lblDegreeId.setLabelFor(degIDField);
		
		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = idField.getText();
				int userID = Integer.parseInt(user);
				String pt = ptField.getText();
				String degID = degIDField.getText();
				
				try {
					DACRegistrar.addStudent(pt, degID, userID);
					JOptionPane.showMessageDialog(frame,
						    "Successfully Added Student",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					RegistrarUI regUI = new RegistrarUI();
					regUI.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame,
						    "Student already exists/This user doesn't exist",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnAddStudent.setBounds(297, 59, 102, 28);
		contentPane.add(btnAddStudent);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			RegistrarUI reg = new RegistrarUI();
				reg.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(297, 119, 102, 28);
		contentPane.add(btnCancel);
	}
}
