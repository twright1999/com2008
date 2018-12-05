package registrar;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.*;
import Database.*;
import login.Login;

import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrarUI extends JFrame {
	private JPanel contentPane;
	private JTable table;
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
					RegistrarUI frame = new RegistrarUI();
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
		Object[] row = new Object[3];
		for(int i=0;i<students.length;i++) {
			row[0]=students[i].getUserID();
			row[1]=students[i].getName();
			row[2]=students[i].getRegNumber();
			model.addRow(row);
		}
	}

	/**
	 * Create the frame.
	 */
	public RegistrarUI() {
		setTitle("Registrar");
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
				"User ID", "Name", "Reg ID"
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
			//@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					
					int row = table.getSelectedRow();
					DefaultTableModel model= (DefaultTableModel)table.getModel();

					String userId = model.getValueAt(row, 0).toString();
					StudentInfo stdInfo = new StudentInfo(userId);
					stdInfo.setLocationRelativeTo(null);
					stdInfo.setVisible(true);
					System.out.println(userId);
				}
			}
		});
		
		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentAdd stdAdd = new StudentAdd();
				stdAdd.setVisible(true);
				dispose();
			}
		});
		btnAddStudent.setBounds(307, 19, 102, 28);
		contentPane.add(btnAddStudent);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int row = table.getSelectedRow();
				DefaultTableModel model= (DefaultTableModel)table.getModel();

				int selected = (int) model.getValueAt(row, 0);
				
					if (row >= 0) {

						model.removeRow(row);

						try {
							DACRegistrar.removeStudent(selected);
							JOptionPane.showMessageDialog(frame,
								    "Delete Successful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
							table.revalidate();
						}
						catch (Exception w) {
							JOptionPane.showInputDialog(this, "Connection Error!");
						}
					}
			}
		});
		btnDelete.setBounds(19, 229, 87, 28);
		contentPane.add(btnDelete);
		
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
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterStudent register = new RegisterStudent();
				register.setVisible(true);
				register.setLocationRelativeTo(null);
			}
		});
		btnRegister.setBounds(165, 229, 87, 28);
		contentPane.add(btnRegister);
		
		try {
			display_table();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
