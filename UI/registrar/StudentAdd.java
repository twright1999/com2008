package registrar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

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
		lblUserID.setBounds(40, 41, 85, 16);
		contentPane.add(lblUserID);
		
		JTextField idField = new JTextField();
		lblUserID.setLabelFor(idField);
		idField.setBounds(145, 35, 102, 28);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JLabel lblPersonalTutor = new JLabel("Personal Tutor");
		lblPersonalTutor.setVerticalAlignment(SwingConstants.CENTER);
		lblPersonalTutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonalTutor.setForeground(Color.BLACK);
		lblPersonalTutor.setEnabled(true);
		lblPersonalTutor.setBounds(40, 99, 85, 16);
		contentPane.add(lblPersonalTutor);
		
		JTextField ptField = new JTextField();
		lblPersonalTutor.setLabelFor(ptField);
		ptField.setBounds(145, 93, 102, 28);
		contentPane.add(ptField);
		ptField.setColumns(10);
		
		JLabel lblPeriodOfStudy = new JLabel("Period of Study");
		lblPeriodOfStudy.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodOfStudy.setBounds(40, 147, 85, 16);
		contentPane.add(lblPeriodOfStudy);
		
		JTextField periodField = new JTextField();
		lblPeriodOfStudy.setLabelFor(periodField);
		periodField.setBounds(145, 141, 102, 28);
		contentPane.add(periodField);
		periodField.setColumns(10);
		
		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Insert Code here
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
