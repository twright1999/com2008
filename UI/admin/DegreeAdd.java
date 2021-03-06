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

public class DegreeAdd extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
					DegreeAdd frame = new DegreeAdd();
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
	public DegreeAdd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDegreeCode = new JLabel("Degree Code");
		lblDegreeCode.setHorizontalAlignment(SwingConstants.CENTER);
		lblDegreeCode.setBounds(23, 26, 109, 16);
		contentPane.add(lblDegreeCode);

		JTextField codeField = new JTextField();
		lblDegreeCode.setLabelFor(codeField);
		codeField.setBounds(144, 20, 102, 28);
		contentPane.add(codeField);
		codeField.setColumns(10);

		JLabel lblDegreeName = new JLabel("Degree Name");
		lblDegreeName.setHorizontalAlignment(SwingConstants.CENTER);
		lblDegreeName.setBounds(23, 66, 109, 16);
		contentPane.add(lblDegreeName);

		JTextField dNameField = new JTextField();
		lblDegreeName.setLabelFor(dNameField);
		dNameField.setBounds(144, 60, 102, 28);
		contentPane.add(dNameField);
		dNameField.setColumns(10);

		JLabel lblDep = new JLabel("Department");
		lblDep.setHorizontalAlignment(SwingConstants.CENTER);
		lblDep.setBounds(23, 103, 109, 16);
		contentPane.add(lblDep);

		JTextField deptField = new JTextField();
		lblDep.setLabelFor(deptField);
		deptField.setBounds(144, 97, 102, 28);
		contentPane.add(deptField);
		deptField.setColumns(10);

		JLabel lblLevel = new JLabel("Level of Study");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setBounds(239, 27, 99, 16);
		contentPane.add(lblLevel);

		JComboBox levelSelect = new JComboBox();
		lblLevel.setLabelFor(levelSelect);
		levelSelect.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4"}));
		levelSelect.setSelectedIndex(0);
		levelSelect.setMaximumRowCount(4);
		levelSelect.setBounds(350, 22, 82, 26);
		contentPane.add(levelSelect);

		JLabel lblDegreeTitle = new JLabel("Degree Title");
		lblDegreeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblDegreeTitle.setBounds(249, 60, 82, 28);
		contentPane.add(lblDegreeTitle);
		
		JComboBox mBSelect = new JComboBox();
		lblDegreeTitle.setLabelFor(mBSelect);
		mBSelect.setModel(new DefaultComboBoxModel(new String[] {"MSc", "MEng", "BSc", "MPsy"}));
		mBSelect.setBounds(350, 61, 82, 28);
		contentPane.add(mBSelect);
		
		JLabel lblPlacement = new JLabel("Placement");
		lblPlacement.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlacement.setBounds(252, 103, 79, 16);
		contentPane.add(lblPlacement);
		
		JComboBox placeSelect = new JComboBox();
		lblPlacement.setLabelFor(placeSelect);
		placeSelect.setModel(new DefaultComboBoxModel(new String[] {"True", "False"}));
		placeSelect.setBounds(350, 98, 82, 28);
		contentPane.add(placeSelect);
		
		JButton btnAddDegree = new JButton("Add Degree");
		btnAddDegree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String degID = codeField.getText();
				String name = dNameField.getText();
				char level = levelSelect.getSelectedItem().toString().charAt(0);
				String depID = deptField.getText();
				String title = mBSelect.getSelectedItem().toString();
				String fullName = title+" "+name;
				String place = placeSelect.getSelectedItem().toString();
				boolean placement;
				if (place == "True") {
					placement = true;
				}else {
					placement = false;
				}
				
				try {
					DACAdmin.addDegree(degID, fullName, level, depID, placement);
					JOptionPane.showMessageDialog(frame,
						    "Successfully Added Degree",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					DegreeUI degUI = new DegreeUI();
					degUI.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddDegree.setBounds(173, 173, 109, 28);
		contentPane.add(btnAddDegree);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DegreeUI deg = null;
				try {
					deg = new DegreeUI();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				deg.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(173, 214, 109, 28);
		contentPane.add(btnCancel);
	}
}
