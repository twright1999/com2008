package login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

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

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(57, 13, 98, 29);
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblUsername);

		textField = new JTextField();
		lblUsername.setLabelFor(textField);
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
				String userName = textField.getText().toString();
				char[] password = passwordField.getPassword();
				String pwdStr = String .valueOf(password);
			}
		});
		btnLogin.setBounds(137, 219, 93, 37);
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 21));
		contentPane.add(btnLogin);
	}
}
