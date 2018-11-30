package admin;

import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AdminUI extends JFrame {

	private JPanel contentPane;
	private JButton btnUser;
	private JButton btnDepartment;
	private JButton btnModule;
	private JButton btnDegree;

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

		btnUser = new JButton("User");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				user.setVisible(true);
				dispose();
			}
		});
		btnUser.setBounds(64, 62, 104, 28);

		btnModule = new JButton("Module");
		btnModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModuleUI mod = new ModuleUI();
				mod.setVisible(true);
				dispose();
			}
		});
		btnModule.setBounds(64, 157, 104, 28);

		btnDegree = new JButton("Degree");
		btnDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DegreeUI degree = new DegreeUI();
				degree.setVisible(true);
				dispose();
			}
		});
		btnDegree.setBounds(278, 157, 104, 28);

		btnDepartment = new JButton("Department");
		btnDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DepartmentUI dep = new DepartmentUI();
				dep.setVisible(true);
				dispose();
			}
		});
		btnDepartment.setBounds(278, 62, 104, 28);
		contentPane.setLayout(null);
		contentPane.add(btnUser);
		contentPane.add(btnModule);
		contentPane.add(btnDegree);
		contentPane.add(btnDepartment);
	}
}
