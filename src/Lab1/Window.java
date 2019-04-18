package Lab1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Window extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JButton button_choose;
	private JButton button_exit;
	private JButton button_inquiry;
	private JComboBox<String> comboBox;

	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public Window(Statement stat) {
		frame = new JFrame("Database:school");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Table");
		label.setBounds(55, 34, 72, 18);
		contentPane.add(label);
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("pos");
		comboBox.addItem("class");
		comboBox.addItem("course");
		comboBox.addItem("student");
		comboBox.addItem("teacher");
		comboBox.addItem("department");
		comboBox.addItem("teach_course");
		comboBox.addItem("choose_course");
		comboBox.setBounds(114, 31, 118, 24);
		contentPane.add(comboBox);
		
		button_choose = new JButton("Choose");
		button_choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String table = (String) comboBox.getSelectedItem();
				switch(table){
				case "choose_course": 
					new Table_choose_course(stat);
					break;
				case "class": 
					new Table_class(stat);
					break;
				case "course": 
					new Table_course(stat);
					break;
				case "department": 
					new Table_department(stat);
					break;
				case "pos": 
					new Table_pos(stat);
					break;
				case "student": 
					new Table_student(stat);
					break;
				case "teach_course": 
					new Table_teach_course(stat);
					break;
				case "teacher": 
					new Table_teacher(stat);
					break;
				}
			}
		});
		button_choose.setBounds(273, 30, 113, 27);
		contentPane.add(button_choose);
		
		button_exit = new JButton("Exit");
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_exit.setBounds(273, 202, 113, 27);
		contentPane.add(button_exit);
		
		button_inquiry = new JButton("Inquiry");
		button_inquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Inquiry(stat);
			}
		});
		button_inquiry.setBounds(273, 114, 113, 27);
		contentPane.add(button_inquiry);
		
		
		frame.setVisible(true);
	}
}
