package admin;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DegreeAdd extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

		JLabel lblLead = new JLabel("Lead Department");
		lblLead.setHorizontalAlignment(SwingConstants.CENTER);
		lblLead.setBounds(23, 103, 109, 16);
		contentPane.add(lblLead);

		JTextField lDeptField = new JTextField();
		lblLead.setLabelFor(lDeptField);
		lDeptField.setBounds(144, 97, 102, 28);
		contentPane.add(lDeptField);
		lDeptField.setColumns(10);

		JLabel lblDept = new JLabel("Other Departments");
		lblDept.setHorizontalAlignment(SwingConstants.CENTER);
		lblDept.setBounds(23, 144, 109, 16);
		contentPane.add(lblDept);

		JLabel lblLevel = new JLabel("Level of Study");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setBounds(239, 27, 99, 16);
		contentPane.add(lblLevel);

		JComboBox levelSelect = new JComboBox();
		lblLevel.setLabelFor(levelSelect);
		levelSelect.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "p" }));
		levelSelect.setSelectedIndex(0);
		levelSelect.setMaximumRowCount(4);
		levelSelect.setBounds(350, 22, 82, 26);
		contentPane.add(levelSelect);

		JButton btnAddDegree = new JButton("Add Degree");
		btnAddDegree.setBounds(297, 97, 109, 28);
		contentPane.add(btnAddDegree);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(144, 144, 122, 98);
		contentPane.add(scrollPane);

		JList deptList = new JList();
		lblDept.setLabelFor(deptList);
		deptList.setSelectionBackground(new Color(169, 169, 169));
		scrollPane.setViewportView(deptList);
		deptList.setSelectionForeground(SystemColor.infoText);
		deptList.setSelectedIndices(new int[] { 0 });
		deptList.setModel(new AbstractListModel() {
			String[] values = new String[] { "Dept1", "Dept2", "Dept3", "Dept4", "Dept5", "Dept6", "Dept7" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		deptList.setSelectedIndex(0);
	}
}
