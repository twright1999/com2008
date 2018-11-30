package teacher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class StudentGrade extends JFrame {

	private JPanel contentPane;
	private JTable gradesTable;
	private JButton btnResit;
	private JButton btnProgress;
	private JButton btnRepeat;
	private JButton btnGraduate;
	private JButton btnBack;

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
		setBounds(100, 100, 592, 305);
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
		btnResit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Insert Code here
			}
		});
		btnResit.setBounds(16, 131, 103, 28);
		contentPane.add(btnResit);
		
		btnProgress = new JButton("Progress");
		btnProgress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Insert Code here
			}
		});
		btnProgress.setBounds(162, 131, 103, 28);
		contentPane.add(btnProgress);
		
		btnRepeat = new JButton("Repeat");
		btnRepeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Insert Code here
			}
		});
		btnRepeat.setBounds(307, 131, 103, 28);
		contentPane.add(btnRepeat);
		
		btnGraduate = new JButton("Graduate");
		btnGraduate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Insert Code here
			}
		});
		btnGraduate.setBounds(462, 131, 87, 28);
		contentPane.add(btnGraduate);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TeacherUI teacher = new TeacherUI();
				teacher.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(462, 219, 87, 28);
		contentPane.add(btnBack);
	}

}
