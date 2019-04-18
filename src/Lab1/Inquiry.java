package Lab1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Inquiry extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_table;
	private JTextField textField_field;
	private JTextField textField_condition;
	private JButton btnExit;
	private JButton btnInquiry;
	private JLabel lblTable;
	private JLabel lblFields;
	private JLabel lblConditions;
	private JButton btnAdd;
	private JButton btnClear;
	private JLabel lblGroup;
	private JTextField textField_group;
	private JTextField textField_having;
	private JLabel lblHaving;
	
	String sql = "";
	ArrayList<String> field = new ArrayList<String>();
	ArrayList<String> table = new ArrayList<String>();
	String condition = "";
	String group = "";
	String having = "";
	String subquiry = "";
	private JButton btnSubquiry;


	/**
	 * Create the frame.
	 */
	public Inquiry(Statement stat) {
		frame = new JFrame("Inquiry");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		textField_table = new JTextField();
		textField_table.setBounds(127, 74, 86, 25);
		contentPane.add(textField_table);
		textField_table.setColumns(10);
		
		textField_field = new JTextField();
		textField_field.setBounds(127, 37, 86, 25);
		contentPane.add(textField_field);
		textField_field.setColumns(10);
		
		textField_condition = new JTextField();
		textField_condition.setBounds(127, 112, 86, 25);
		contentPane.add(textField_condition);
		textField_condition.setColumns(10);
		
		textField_group = new JTextField();
		textField_group.setBounds(127, 149, 86, 25);
		contentPane.add(textField_group);
		textField_group.setColumns(10);
		
		textField_having = new JTextField();
		textField_having.setBounds(127, 187, 86, 25);
		contentPane.add(textField_having);
		textField_having.setColumns(10);
		
		lblTable = new JLabel("Tables");
		lblTable.setBounds(37, 74, 76, 25);
		contentPane.add(lblTable);
		
		lblFields = new JLabel("Fields");
		lblFields.setBounds(37, 37, 88, 25);
		contentPane.add(lblFields);
		
		lblConditions = new JLabel("Conditions");
		lblConditions.setBounds(37, 112, 88, 25);
		contentPane.add(lblConditions);
		
		lblGroup = new JLabel("Group by");
		lblGroup.setBounds(37, 149, 72, 25);
		contentPane.add(lblGroup);
		
		lblHaving = new JLabel("Having");
		lblHaving.setBounds(37, 187, 72, 25);
		contentPane.add(lblHaving);
		
		
		btnInquiry = new JButton("Inquiry");
		btnInquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sql = "select ";
				System.out.println(field.size());
				for(int i = 0 ; i < field.size(); i++) {
					if(i != 0) {
						sql += ", ";
					}
					if(!group.equals("")) {
						sql += "any_value(" + field.get(i) + ")";
					}
					else sql += field.get(i);
				}
				sql += " from ";
				for(int i = 0 ; i < table.size(); i++) {
					if(i != 0) {
						sql += ", ";
					}
					sql += table.get(i);
				}
				sql += condition + subquiry;
				sql += group;
				sql += having;
				
				System.out.println(sql);
				
				try {
					ResultSet result = stat.executeQuery(sql);
					while(result.next()) {
						for(int i = 0 ; i < field.size(); i++) {
							if(!group.equals("")) {
								System.out.print(result.getString("any_value(" + field.get(i) + ")") + " ");
							}
							else System.out.print(result.getString(field.get(i)) + " ");
						}
			        	System.out.println("");
			        }
				} catch (SQLException e1) {
					e1.printStackTrace();
					System.out.println(sql);
					JOptionPane.showMessageDialog(frame, "Input illegal !", "ERROR",JOptionPane.WARNING_MESSAGE);
				}
				
				field.clear();
				table.clear();
				condition = "";
				group = "";
				having = "";
				subquiry = "";
			}
		});
		btnInquiry.setBounds(271, 137, 113, 27);
		contentPane.add(btnInquiry);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnExit.setBounds(271, 187, 113, 27);
		contentPane.add(btnExit);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f = textField_field.getText();
				String t = textField_table.getText();
				if(!f.equals("")) {
					field.add(f);
				}
				if(!t.equals("")) {
					table.add(t);
				}
				if(!textField_condition.getText().equals("")) {
					condition = " where " + textField_condition.getText();
				}
				if(!textField_group.getText().equals("")) {
					group = " group by " + textField_group.getText();
				}
				if(!textField_having.getText().equals("")) {
					having = " having " + textField_having.getText();
				}
				
				JOptionPane.showMessageDialog(frame, "Already add!", "Tips",JOptionPane.WARNING_MESSAGE);
				textField_field.setText("");
				textField_table.setText("");
				textField_condition.setText("");
				textField_group.setText("");
				textField_having.setText("");
			}
		});
		btnAdd.setBounds(271, 37, 113, 27);
		contentPane.add(btnAdd);
		
		btnSubquiry = new JButton("Subquiry");
		btnSubquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sql = "select ";
				System.out.println(field.size());
				for(int i = 0 ; i < field.size(); i++) {
					if(i != 0) {
						sql += ", ";
					}
					if(!group.equals("")) {
						sql += "any_value(" + field.get(i) + ")";
					}
					else sql += field.get(i);
				}
				sql += " from ";
				for(int i = 0 ; i < table.size(); i++) {
					if(i != 0) {
						sql += ", ";
					}
					sql += table.get(i);
				}
				sql += condition;
				sql += group;
				sql += having;
				subquiry = " (" + sql + ")";
				field.clear();
				table.clear();
				condition = "";
				group = "";
				having = "";
			}
		});
		btnSubquiry.setBounds(271, 87, 113, 27);
		contentPane.add(btnSubquiry);
		
	}
}
