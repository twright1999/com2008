package admin;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AdminUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminUI frame = new AdminUI();
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
	public AdminUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton btnUser = new JButton("User");
		btnUser.setBounds(64, 62, 104, 28);

		JButton btnModule = new JButton("Module");
		btnModule.setBounds(64, 157, 104, 28);

		JButton btnDegree = new JButton("Degree");
		btnDegree.setBounds(278, 157, 104, 28);

		JButton btnDepartment = new JButton("Department");
		btnDepartment.setBounds(278, 62, 104, 28);
		contentPane.setLayout(null);
		contentPane.add(btnUser);
		contentPane.add(btnModule);
		contentPane.add(btnDegree);
		contentPane.add(btnDepartment);
	}
}
