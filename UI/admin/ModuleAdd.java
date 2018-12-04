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

public class ModuleAdd extends JFrame {

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
					ModuleAdd frame = new ModuleAdd();
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
	public ModuleAdd() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblModId = new JLabel("Module ID");
		lblModId.setHorizontalAlignment(SwingConstants.CENTER);
		lblModId.setBounds(23, 26, 87, 16);
		contentPane.add(lblModId);

		JTextField modIDField = new JTextField();
		lblModId.setLabelFor(modIDField);
		modIDField.setBounds(116, 20, 102, 28);
		contentPane.add(modIDField);
		modIDField.setColumns(10);

		JLabel lblModName = new JLabel("Module Name");
		lblModName.setHorizontalAlignment(SwingConstants.CENTER);
		lblModName.setBounds(23, 66, 87, 16);
		contentPane.add(lblModName);

		JTextField modNameField = new JTextField();
		lblModName.setLabelFor(modNameField);
		modNameField.setBounds(116, 60, 102, 28);
		contentPane.add(modNameField);
		modNameField.setColumns(10);

		JLabel lblCredit = new JLabel("Credits");
		lblCredit.setHorizontalAlignment(SwingConstants.CENTER);
		lblCredit.setBounds(23, 103, 87, 16);
		contentPane.add(lblCredit);

		JTextField credField = new JTextField();
		lblCredit.setLabelFor(credField);
		credField.setBounds(116, 100, 102, 28);
		contentPane.add(credField);
		credField.setColumns(10);

		JLabel lblCore = new JLabel("Core");
		lblCore.setHorizontalAlignment(SwingConstants.CENTER);
		lblCore.setBounds(240, 72, 87, 16);
		contentPane.add(lblCore);

		JComboBox coreSelect = new JComboBox();
		coreSelect.setModel(new DefaultComboBoxModel(new String[] {"True", "False"}));
		lblCore.setLabelFor(coreSelect);
		coreSelect.setBounds(332, 66, 100, 28);
		contentPane.add(coreSelect);

		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuration.setBounds(258, 26, 51, 16);
		contentPane.add(lblDuration);

		JComboBox durationSelect = new JComboBox();
		lblDuration.setLabelFor(durationSelect);
		durationSelect
				.setModel(new DefaultComboBoxModel(new String[] { "Autumn", "Spring", "Summer", "Academic Year" }));
		durationSelect.setSelectedIndex(0);
		durationSelect.setMaximumRowCount(4);
		durationSelect.setBounds(332, 21, 100, 26);
		contentPane.add(durationSelect);

		JLabel lblDegreeId = new JLabel("Degree ID");
		lblDegreeId.setHorizontalAlignment(SwingConstants.CENTER);
		lblDegreeId.setBounds(23, 144, 87, 16);
		contentPane.add(lblDegreeId);

		JTextField degreeIDField = new JTextField();
		lblDegreeId.setLabelFor(degreeIDField);
		degreeIDField.setBounds(116, 140, 102, 28);
		contentPane.add(degreeIDField);
		degreeIDField.setColumns(10);
		
		JLabel lblLevel = new JLabel("Level");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setBounds(23, 184, 87, 16);
		contentPane.add(lblLevel);
		
		JComboBox levelSelect = new JComboBox();
		lblLevel.setLabelFor(levelSelect);
		levelSelect.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "P"}));
		levelSelect.setBounds(116, 179, 102, 27);
		contentPane.add(levelSelect);
		
		JButton btnAddModule = new JButton("Add Module");
		btnAddModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String modID = modIDField.getText();
				String name = modNameField.getText();
				String cred = credField.getText();
				int credits = Integer.parseInt(cred);
				String taught = durationSelect.getSelectedItem().toString();
				char level = levelSelect.getSelectedItem().toString().charAt(0);
				String degID = degreeIDField.getText();
				String core = coreSelect.getSelectedItem().toString();
				int obligatory;
				
				if (core == "True") {
					obligatory = 1;
				}else {
					obligatory = 0;
				}
				
				try {
					DACAdmin.addModule(modID, name, credits, taught, obligatory, level, degID);
					JOptionPane.showMessageDialog(frame,
						    "Successfully Added Module",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					ModuleUI modUI = new ModuleUI();
					modUI.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAddModule.setBounds(280, 138, 108, 28);
		contentPane.add(btnAddModule);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModuleUI mod = null;
				try {
					mod = new ModuleUI();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mod.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(280, 178, 108, 28);
		contentPane.add(btnCancel);
	}
}
