package registrar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Database.DACRegistrar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

public class RegisterStudent extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField labelField;
	private JTextField sDateField;
	private JTextField regNumField;
	private JTextField eDateField;
	protected Component frame;


	/**
	 * Create the frame.
	 */
	public RegisterStudent() {
		setTitle("Registration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLabel = new JLabel("Label");
		lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLabel.setBounds(6, 18, 85, 21);
		contentPane.add(lblLabel);
		
		labelField = new JTextField();
		lblLabel.setLabelFor(labelField);
		labelField.setBounds(103, 14, 102, 28);
		contentPane.add(labelField);
		labelField.setColumns(10);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setToolTipText("");
		lblStartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartDate.setBounds(6, 51, 85, 21);
		contentPane.add(lblStartDate);
		
		sDateField = new JTextField();
		lblStartDate.setLabelFor(sDateField);
		sDateField.setToolTipText("YYYY/MM/DD");
		sDateField.setBounds(103, 47, 102, 28);
		contentPane.add(sDateField);
		sDateField.setColumns(10);
		
		JLabel lblRegNumber = new JLabel("Reg Number");
		lblRegNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegNumber.setBounds(6, 121, 85, 21);
		contentPane.add(lblRegNumber);
		
		regNumField = new JTextField();
		lblRegNumber.setLabelFor(regNumField);
		regNumField.setBounds(103, 117, 102, 28);
		contentPane.add(regNumField);
		regNumField.setColumns(10);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndDate.setBounds(6, 84, 85, 21);
		contentPane.add(lblEndDate);
		
		eDateField = new JTextField();
		lblEndDate.setLabelFor(eDateField);
		eDateField.setToolTipText("YYYY/MM/DD");
		eDateField.setBounds(103, 80, 102, 28);
		contentPane.add(eDateField);
		eDateField.setColumns(10);
		
		JLabel lblLevel = new JLabel("Level");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setBounds(233, 18, 85, 21);
		contentPane.add(lblLevel);
		
		JComboBox lvlSelect = new JComboBox();
		lblLevel.setLabelFor(lvlSelect);
		lvlSelect.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "P"}));
		lvlSelect.setBounds(330, 15, 85, 28);
		contentPane.add(lvlSelect);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String label = labelField.getText();
				String startDate = sDateField.getText();
				String endDate = eDateField.getText();
				String level = lvlSelect.getSelectedItem().toString();
				String regNum = regNumField.getText();
				int regNumber = Integer.parseInt(regNum);
				
				try {
					DACRegistrar.registerStudent(label, startDate, endDate, level, regNumber);
					JOptionPane.showMessageDialog(frame,
						    "Successfully Registered",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					dispose();
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame,
						    "Unsuccessful Registration",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		btnRegister.setBounds(330, 80, 87, 28);
		contentPane.add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(328, 121, 87, 28);
		contentPane.add(btnCancel);
	}
}
