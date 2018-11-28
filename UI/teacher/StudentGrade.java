package teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class StudentGrade extends JFrame {

	private JPanel contentPane;
	private JTable gradesTable;
	private JButton btnResit;
	private JButton btnProgress;
	private JButton btnRepeat;
	private JButton btnGraduate;

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
					StudentGrade frame = new StudentGrade();
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
	public StudentGrade() {
		setTitle("Student Grades");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(6, 18, 566, 101);
		contentPane.add(scrollPane);
		
		gradesTable = new JTable();
		gradesTable.setShowVerticalLines(true);
		gradesTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"Current Level", "Level 1", "Level 2", "Level 3", "Level 4", "Weighted Average", "Final Mark"
			}
		));
		gradesTable.getColumnModel().getColumn(0).setPreferredWidth(88);
		gradesTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		gradesTable.getColumnModel().getColumn(2).setPreferredWidth(61);
		gradesTable.getColumnModel().getColumn(3).setPreferredWidth(57);
		gradesTable.getColumnModel().getColumn(4).setPreferredWidth(53);
		gradesTable.getColumnModel().getColumn(5).setPreferredWidth(112);
		gradesTable.getColumnModel().getColumn(6).setPreferredWidth(70);
		scrollPane.setViewportView(gradesTable);
		
		btnResit = new JButton("Resit");
		btnResit.setBounds(16, 131, 103, 28);
		contentPane.add(btnResit);
		
		btnProgress = new JButton("Progress");
		btnProgress.setBounds(162, 131, 103, 28);
		contentPane.add(btnProgress);
		
		btnRepeat = new JButton("Repeat");
		btnRepeat.setBounds(307, 131, 103, 28);
		contentPane.add(btnRepeat);
		
		btnGraduate = new JButton("Graduate");
		btnGraduate.setBounds(462, 131, 87, 28);
		contentPane.add(btnGraduate);
	}

}
