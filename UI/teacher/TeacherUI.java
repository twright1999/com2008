package teacher;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Student;
import Database.DAC;
import login.Login;

public class TeacherUI extends JFrame {
	protected int userIDGlobal;
	protected static int regNumberGlobal;
	private JPanel contentPane;
	private JTable table;
	protected Component frame;
	private JLabel lblDoubleClickTo;

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
					TeacherUI frame = new TeacherUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void display_table() throws SQLException {
		Student[] students = DAC.getAllStudents();
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Object[] row = new Object[2];
		for(int i=0;i<students.length;i++) {
			row[0]=students[i].getUserID();
			row[1]=students[i].getName();
			model.addRow(row);
		}
	}

	/**
	 * Create the frame.
	 */
	public TeacherUI() {
		setTitle("Teacher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(19, 65, 395, 88);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"User ID", "Name"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					
					int row = table.getSelectedRow();
					DefaultTableModel model= (DefaultTableModel)table.getModel();

					String userId = model.getValueAt(row, 0).toString();
					StudentInfoT stdInfo = new StudentInfoT(userId);
					stdInfo.setLocationRelativeTo(null);
					stdInfo.setVisible(true);
					System.out.println(userId);
					userIDGlobal = Integer.parseInt(userId);
					try {
						regNumberGlobal = DAC.getStudent(userIDGlobal).getRegNumber();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogout.setBounds(307, 229, 102, 28);
		contentPane.add(btnLogout);
		
		lblDoubleClickTo = new JLabel("Double Click to Edit Student Grades ");
		lblDoubleClickTo.setBounds(19, 37, 227, 16);
		contentPane.add(lblDoubleClickTo);
		
		try {
			display_table();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
