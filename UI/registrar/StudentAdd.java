package registrar;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.UIManager;

public class StudentAdd extends JFrame {

	private JPanel contentPane;

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
		lblUserID.setBounds(126, 54, 85, 16);
		contentPane.add(lblUserID);
		
		JTextField idField = new JTextField();
		lblUserID.setLabelFor(idField);
		idField.setBounds(231, 48, 102, 28);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel lblPersonalTutor = new JLabel("Personal Tutor");
		lblPersonalTutor.setVerticalAlignment(SwingConstants.CENTER);
		lblPersonalTutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonalTutor.setForeground(Color.BLACK);
		lblPersonalTutor.setEnabled(true);
		lblPersonalTutor.setBounds(126, 112, 85, 16);
		contentPane.add(lblPersonalTutor);
		
		JTextField ptField = new JTextField();
		lblPersonalTutor.setLabelFor(ptField);
		ptField.setBounds(231, 106, 102, 28);
		contentPane.add(ptField);
		ptField.setColumns(10);
		
		JLabel lblPeriodOfStudy = new JLabel("Period of Study");
		lblPeriodOfStudy.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodOfStudy.setBounds(126, 160, 85, 16);
		contentPane.add(lblPeriodOfStudy);
		
		JTextField periodField = new JTextField();
		lblPeriodOfStudy.setLabelFor(periodField);
		periodField.setBounds(231, 154, 102, 28);
		contentPane.add(periodField);
		periodField.setColumns(10);
		
		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.setBounds(231, 212, 102, 28);
		contentPane.add(btnAddStudent);
	}

}
