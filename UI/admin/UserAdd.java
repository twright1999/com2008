package admin;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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

import Database.DACAdmin;

public class UserAdd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField pwField;
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

		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(23, 104, 71, 16);
		contentPane.add(lblName);

		nameField = new JTextField();
		lblName.setLabelFor(nameField);
		nameField.setBounds(106, 98, 102, 28);
		contentPane.add(nameField);
		nameField.setColumns(10);

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
		lblStatus.setBounds(240, 31, 51, 16);
		contentPane.add(lblStatus);

		JComboBox roleSelect = new JComboBox();
		lblStatus.setLabelFor(roleSelect);
		roleSelect.setModel(new DefaultComboBoxModel(new String[] {"Student", "Teacher", "Registrar"}));
		roleSelect.setSelectedIndex(0);
		roleSelect.setMaximumRowCount(3);
		roleSelect.setBounds(303, 26, 82, 26);
		contentPane.add(roleSelect);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(23, 31, 51, 16);
		contentPane.add(lblTitle);
		
		JComboBox titleSelect = new JComboBox();
		lblTitle.setLabelFor(titleSelect);
		titleSelect.setModel(new DefaultComboBoxModel(new String[] {"Mr", "Ms"}));
		titleSelect.setBounds(86, 26, 82, 26);
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
		
		JButton btnAddUser = new JButton("Add User");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String title= titleSelect.getSelectedItem().toString();
				String name = nameField.getText().toString();
				String permission = roleSelect.getSelectedItem().toString();
				char role = permission.charAt(0);
				String pw = pwField.getText().toString();
				String tName = title+" "+name;
				
				try {
					DACAdmin.addAccount(tName, pw, role);
					JOptionPane.showMessageDialog(frame,
						    "Successfully Added Account",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					User user = new User();
					user.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddUser.setBounds(298, 132, 87, 28);
		contentPane.add(btnAddUser);
	}
}
