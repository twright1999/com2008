package registrar;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ModuleSelect extends JFrame {

	private JPanel contentPane;

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
					ModuleSelect frame = new ModuleSelect();
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
	public ModuleSelect() {
		setTitle("Available Modules");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 38, 118, 175);
		contentPane.add(scrollPane);
		
		JList moduleList = new JList();
		moduleList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Mod1", "Mod2", "Mod3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(moduleList);
		
		JButton btnGet = new JButton("Get");
		btnGet.setBounds(172, 48, 87, 28);
		contentPane.add(btnGet);
		
		JButton btnAdd_1 = new JButton("Add");
		btnAdd_1.setBounds(172, 105, 87, 28);
		contentPane.add(btnAdd_1);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(172, 168, 87, 28);
		contentPane.add(btnRemove);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(283, 37, 112, 176);
		contentPane.add(scrollPane_1);
		
		JList selectedMod = new JList();
		scrollPane_1.setViewportView(selectedMod);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentInfo stdInfo = new StudentInfo();
				stdInfo.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(172, 218, 87, 28);
		contentPane.add(btnCancel);
	}
}
