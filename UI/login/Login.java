package login;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import admin.AdminUI;
import registrar.RegistrarUI;
import security.RBAC;
import student.StudentUI;
import teacher.TeacherUI;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
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
					Login frame = new Login();
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
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 581, 417);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEnterUsernameAnd = new JLabel("Enter Username and Password");
		lblEnterUsernameAnd.setBounds(6, 6, 558, 53);
		lblEnterUsernameAnd.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterUsernameAnd.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		contentPane.add(lblEnterUsernameAnd);

		JPanel panel = new JPanel();
		panel.setBounds(16, 71, 536, 105);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblUserID = new JLabel("User ID:");
		lblUserID.setBounds(57, 13, 98, 29);
		lblUserID.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUserID);

		textField = new JTextField();
		lblUserID.setLabelFor(textField);
		textField.setBounds(167, 12, 100, 31);
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel.add(textField);
		textField.setColumns(8);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(61, 56, 94, 29);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblPassword);

		passwordField = new JPasswordField();
		lblPassword.setLabelFor(passwordField);
		passwordField.setBounds(167, 55, 100, 31);
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		passwordField.setColumns(8);
		panel.add(passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String user = textField.getText();
				int id = Integer.parseInt(user);
				char[] password = passwordField.getPassword();
				String pwdStr = String .valueOf(password);
				new RBAC();
				
				try {
					if (RBAC.verifyLogin(id, pwdStr)){
						char permission = RBAC.getPermission(id);
						switch (permission) {
						case 'S': {
							StudentUI student = new StudentUI(id);
							student.setVisible(true);
							dispose();
							break ;
						}
						case 'A' : {
							AdminUI admin = new AdminUI();
							admin.setVisible(true);
							dispose();
							break ;
						}
						case 'R' : {
							RegistrarUI registrar = new RegistrarUI();
							registrar.setVisible(true);
							dispose();
							break ;
						}
						case 'T' : {
							TeacherUI teacher = new TeacherUI();
							teacher.setVisible(true);
							dispose();
							break ;
						}
						}
					} else {
						JOptionPane.showMessageDialog(frame, "Wrong details/User does not exist");
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Something is wrong with the database");
				}
			}
		});
		btnLogin.setBounds(137, 219, 93, 37);
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 21));
		contentPane.add(btnLogin);
	}
}
