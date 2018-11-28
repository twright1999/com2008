package admin;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class User extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User frame = new User();
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
	public User() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBounds(35, 35, 51, 16);
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);

		JTextField textField = new JTextField();
		textField.setBounds(98, 29, 102, 28);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(218, 29, 87, 28);
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});

		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(323, 29, 87, 28);

		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setCellSelectionEnabled(true);
		table.setBounds(11, 63, 416, 110);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null }, },
				new String[] { "column1", "column2", "column3" }));
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		contentPane.setLayout(null);
		contentPane.add(lblSearch);
		contentPane.add(textField);
		contentPane.add(btnSearch);
		contentPane.add(btnAdd);
		contentPane.add(table);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(21, 185, 87, 28);
		contentPane.add(btnDelete);
	}
}
