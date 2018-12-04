package admin;

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

import Database.DACAdmin;
import registrar.RegistrarUI;

public class DeptAdd extends JFrame {

	private JPanel contentPane;
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
					DeptAdd frame = new DeptAdd();
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
	public DeptAdd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDeptCode = new JLabel("Department Code");
		lblDeptCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeptCode.setBounds(30, 49, 116, 16);
		contentPane.add(lblDeptCode);

		JTextField deptCodeField = new JTextField();
		lblDeptCode.setLabelFor(deptCodeField);
		deptCodeField.setBounds(158, 43, 242, 28);
		contentPane.add(deptCodeField);
		deptCodeField.setColumns(10);

		JLabel lblDeptName = new JLabel("Department Name");
		lblDeptName.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeptName.setBounds(30, 89, 116, 16);
		contentPane.add(lblDeptName);

		JTextField deptNameField = new JTextField();
		lblDeptName.setLabelFor(deptNameField);
		deptNameField.setBounds(158, 83, 242, 28);
		contentPane.add(deptNameField);
		deptNameField.setColumns(10);

		JButton btnAddUser = new JButton("Add Department");
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String code = deptCodeField.getText();
				String name = deptNameField.getText();
				
				try {
					DACAdmin.addDepartment(code, name);
					JOptionPane.showMessageDialog(frame,
						    "Successfully Added Department",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					DepartmentUI depUI = new DepartmentUI();
					depUI.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, 
							"This Department already exists", 
							"Warning", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAddUser.setBounds(152, 146, 133, 28);
		contentPane.add(btnAddUser);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DepartmentUI dept = null;
				try {
					dept = new DepartmentUI();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dept.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(152, 186, 133, 28);
		contentPane.add(btnCancel);
	}
}
