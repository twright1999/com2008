package admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class UserAdd extends JFrame {

	private JPanel contentPane;
	private JTextField idField;
	private JTextField fnField;
	private JTextField lnField;
	private JTextField pwField;

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
					UserAdd frame = new UserAdd();
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
	public UserAdd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblUserId = new JLabel("User ID");
		lblUserId.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserId.setBounds(23, 26, 71, 16);
		contentPane.add(lblUserId);

		idField = new JTextField();
		lblUserId.setLabelFor(idField);
		idField.setBounds(106, 20, 102, 28);
		contentPane.add(idField);
		idField.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setBounds(23, 66, 71, 16);
		contentPane.add(lblFirstName);

		fnField = new JTextField();
		lblFirstName.setLabelFor(fnField);
		fnField.setBounds(106, 60, 102, 28);
		contentPane.add(fnField);
		fnField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setBounds(23, 103, 71, 16);
		contentPane.add(lblLastName);

		lnField = new JTextField();
		lblLastName.setLabelFor(lnField);
		lnField.setBounds(106, 97, 102, 28);
		contentPane.add(lnField);
		lnField.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(23, 144, 71, 16);
		contentPane.add(lblPassword);

		pwField = new JTextField();
		lblPassword.setLabelFor(pwField);
		pwField.setBounds(106, 138, 102, 28);
		contentPane.add(pwField);
		pwField.setColumns(10);

		JLabel lblStatus = new JLabel("Role");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(240, 26, 51, 16);
		contentPane.add(lblStatus);

		JComboBox roleSelect = new JComboBox();
		lblStatus.setLabelFor(roleSelect);
		roleSelect.setModel(new DefaultComboBoxModel(new String[] { "Student", "Teacher", "Registrar" }));
		roleSelect.setSelectedIndex(0);
		roleSelect.setMaximumRowCount(3);
		roleSelect.setBounds(303, 21, 82, 26);
		contentPane.add(roleSelect);

		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User user = new User();
				user.setVisible(true);
				dispose();
			}
		});
		btnAddUser.setBounds(298, 132, 87, 28);
		contentPane.add(btnAddUser);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(240, 66, 51, 16);
		contentPane.add(lblTitle);
		
		JComboBox titleSelect = new JComboBox();
		lblTitle.setLabelFor(titleSelect);
		titleSelect.setModel(new DefaultComboBoxModel(new String[] {"Mr", "Ms"}));
		titleSelect.setBounds(303, 61, 82, 26);
		contentPane.add(titleSelect);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				user.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(298, 172, 87, 28);
		contentPane.add(btnCancel);
	}
}
