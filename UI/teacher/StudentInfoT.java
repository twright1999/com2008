package teacher;

import java.awt.Component;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Accounts.Student;
import Database.DAC;
import Database.DACRegistrar;
import Database.DACTeacher;
import Utility.PeriodOfStudy;
import Utility.Grade;
import Utility.Module;

import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;

public class StudentInfoT extends JFrame {

	private JPanel contentPane;
	private JTable stdTable;
	static JTable table;
	protected Component frame;
	public JTextField userIDField;
	private JTable table_1;


	public void display_table_student(int userID) throws SQLException {
		Student student = DAC.getStudent(userID);
		DefaultTableModel model = (DefaultTableModel)stdTable.getModel();
		Object[] row = new Object[3];
		row[0]=student.getRegNumber();
		row[1]=student.getName();
		row[2]=student.getDegID();
		model.addRow(row);
	}
	
	public void display_table_module(int regNumber) throws SQLException {
		Grade[] grade = DAC.getStudentGrades(regNumber);
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		Object[] row = new Object[4];
		for(int i=0; i<grade.length; i++) {
			if (grade[i].getGradeID() != 0) {
			row[0]=grade[i].getGradeID();
			row[1]=grade[i].getModID();
			row[2]=grade[i].getInitialGrade();
			row[3]=grade[i].getResitGrade();
			model.addRow(row);
			}
		}
		
	}
	
	public void display_table_grade(int regNumber) throws SQLException {
		Grade[] grade = DAC.getStudentGrades(regNumber);
		DefaultTableModel model = (DefaultTableModel)table_1.getModel();
		Object[] row = new Object[1];
		for(int i=0;i<grade.length;i++) {
			row[0]=grade[i].getGradeID();
			model.addRow(row);
		}
	}
	
	
	
	/**
	 * Create the frame.
	 */
	public StudentInfoT(String userID) {
		setTitle("Student Information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userIDField = new JTextField();
		userIDField.setEditable(false);
		userIDField.setBounds(22, 6, 76, 28);
		userIDField.setText(userID);
		contentPane.add(userIDField);
		userIDField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(6, 352, 4, -4);
		contentPane.add(scrollPane);
		
		stdTable = new JTable();
		stdTable.setShowVerticalLines(true);
		stdTable.setShowHorizontalLines(true);
		stdTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Reg ID", "Name", "Level", "Degree ID"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(stdTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(22, 42, 541, 192);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Grade ID", "Module", "Initial Grade", "Resit Grade"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
			
		});
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(417, 337, 87, 28);
		contentPane.add(btnClose);
		
		JButton btnDeleteGrade = new JButton("Delete Grade");
		btnDeleteGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				DefaultTableModel model= (DefaultTableModel)table.getModel();
				DefaultTableModel gradeModel= (DefaultTableModel)table_1.getModel();

				int gradeID = (int)model.getValueAt(row, 0);
				System.out.println(gradeID);
				
					if (row >= 0) {

						

						try {
							model.removeRow(row);
							DACTeacher.removeGrade(gradeID);
							JOptionPane.showMessageDialog(frame,
								    "Delete Successful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
							
							table.revalidate(); 
						}
						catch (Exception w) {
							JOptionPane.showMessageDialog(frame,
								    "Delete Unsuccessful",
								    "Notice",
								    JOptionPane.PLAIN_MESSAGE);
						}
					}
			}
		});
		btnDeleteGrade.setBounds(22, 316, 109, 28);
		contentPane.add(btnDeleteGrade);
		
		
			
		
		
		
		
		JButton button_2 = new JButton("Advance student");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel)stdTable.getModel();
				int regNumber = (int) model.getValueAt(0,0);
				PeriodOfStudy period = null;
				try {
					period = DAC.getStudentPeriodOfStudy(regNumber);
					JOptionPane.showMessageDialog(frame,
						    "Advance Successful",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame,
						    "Advance Unsuccessful",
						    "Notice",
						    JOptionPane.PLAIN_MESSAGE);
					e1.printStackTrace();
				}
				String periodID = period.getPeriodID();
				String startDate = period.getStartDate();
				String endDate = period.getEndDate();
				String endResult = null;
				try {
					endResult = DACTeacher.advanceStudent(regNumber, periodID, startDate, endDate);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (endResult.contains("Bachelors") || endResult.contains("Masters")) {
					JOptionPane.showMessageDialog(frame, endResult);
				}
				else if (endResult == "Resit") {
					JOptionPane.showMessageDialog(frame, "Student will have to resit");
				}
				else if (endResult == "Next level") {
					JOptionPane.showMessageDialog(frame, "Student has advanced to next level");
				}
				else {
					JOptionPane.showMessageDialog(frame, "Connection failed!");
				}
				
				
			}
		});
		button_2.setBounds(395, 297, 135, 28);
		contentPane.add(button_2);
		
		table_1 = new JTable();
		table_1.setShowVerticalLines(true);
		table_1.setShowHorizontalLines(true);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Grade ID"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table_1);
		
		String id = userIDField.getText();
		
		int userId = Integer.parseInt(id);
		DefaultTableModel model = (DefaultTableModel)stdTable.getModel();
		DefaultTableModel gradeModel = (DefaultTableModel)table_1.getModel();
		//char label = (char)model.getValueAt(0, 2);
		
		try {
			display_table_student(userId);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int row = table_1.getSelectedRow();
		int regNumber = (int)model.getValueAt(0, 0);
		try {
			display_table_module(regNumber);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			display_table_grade(regNumber);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton button = new JButton("Add Grade");
		button.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//int userID = Integer.parseInt(userId);
				GradeAdd gradeAdd;
				try {
					gradeAdd = new GradeAdd(id);
					gradeAdd.setLocationRelativeTo(null);
					gradeAdd.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			
				});
		
		button.setBounds(22, 257, 109, 28);
		contentPane.add(button);
		
		JButton button_3 = new JButton("Edit Initial Grade");
		button_3.addActionListener(new ActionListener() {
				
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						//int userID = Integer.parseInt(userId);
						GradeUpdate gradeUpdate;
						try {
							DefaultTableModel model = (DefaultTableModel)table.getModel();
							int row = table.getSelectedRow();
							String modID = (String) model.getValueAt(row, 1);
							int gradeID = (int) model.getValueAt(row, 0);
							gradeUpdate = new GradeUpdate(id, modID, gradeID);
							gradeUpdate.setLocationRelativeTo(null);
							gradeUpdate.setVisible(true);
							dispose();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					}
					
						});
		
		button_3.setBounds(155, 257, 135, 28);
		contentPane.add(button_3);
		
		JButton button_1 = new JButton("Edit Resit Grade");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//int userID = Integer.parseInt(userId);
				ResitGradeUpdate resitGradeUpdate;
				try {
					DefaultTableModel model = (DefaultTableModel)table.getModel();
					int row = table.getSelectedRow();
					String modID = (String) model.getValueAt(row, 1);
					int gradeID = (int) model.getValueAt(row, 0);
					resitGradeUpdate = new ResitGradeUpdate(id, modID, gradeID);
					resitGradeUpdate.setLocationRelativeTo(null);
					resitGradeUpdate.setVisible(true);
					dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		button_1.setBounds(155, 316, 135, 28);
		contentPane.add(button_1);
		
		JButton button_4 = new JButton("View all grades");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					GradeSelect viewGrades = new GradeSelect(regNumber, userID);
					viewGrades.setLocationRelativeTo(null);
					viewGrades.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		button_4.setBounds(395, 257, 135, 28);
		contentPane.add(button_4);
		
		JLabel lblIsPlace = new JLabel("-1 is place holder for null");
		lblIsPlace.setBounds(395, 12, 168, 22);
		contentPane.add(lblIsPlace);
		
	}
}
