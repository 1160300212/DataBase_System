package Lab1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Table_choose_course extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_CouId;
	private JTextField textField_StuId;
	private JTextField textField_grade;
	private JLabel lblCouId;
	private JLabel lblStuId;
	private JLabel lblgrade;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;


	/**
	 * Create the frame.
	 */
	public Table_choose_course(Statement stat) {
		frame = new JFrame("Table_Choose_Course");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		lblCouId = new JLabel("CouId");
		lblCouId.setBounds(32, 51, 72, 18);
		contentPane.add(lblCouId);
		
		textField_CouId = new JTextField();
		textField_CouId.setBounds(109, 48, 123, 24);
		contentPane.add(textField_CouId);
		textField_CouId.setColumns(10);
		
		lblStuId = new JLabel("StuId");
		lblStuId.setBounds(32, 94, 72, 18);
		contentPane.add(lblStuId);
		
		textField_StuId = new JTextField();
		textField_StuId.setBounds(109, 91, 123, 24);
		contentPane.add(textField_StuId);
		textField_StuId.setColumns(10);
		
		lblgrade = new JLabel("grade");
		lblgrade.setBounds(32, 135, 72, 18);
		contentPane.add(lblgrade);
		
		textField_grade = new JTextField();
		textField_grade.setBounds(109, 132, 123, 24);
		contentPane.add(textField_grade);
		textField_grade.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String couid = textField_CouId.getText();
				String stuid = textField_StuId.getText();
				String grade = textField_grade.getText();
				if(couid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, CouId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(stuid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, StuId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into choose_course(";
					if(!grade.equals("")) {
						sql += "grade, ";
					}
					sql += "StuId, CouId) value(";
					if(!grade.equals("")) {
						sql += grade + ", ";
					}
					sql += "'" + stuid + "', '" + couid + "')";
					System.out.println(sql);
					Boolean duplicate = false;
					Boolean foreign1 = false;
					Boolean foreign2 = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from choose_course");
						while(result.next()) {
							if(result.getString("CouId").equals(couid) && result.getString("StuId").equals(stuid)) {
								duplicate = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try { //检查外键是否存在
						result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("StuId").equals(stuid)) {
								foreign1 = true;
							}
						}
						result = stat.executeQuery("select * from course");
						while(result.next()) {
							if(result.getString("CouId").equals(couid)) {
								foreign2 = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(!(foreign1 && foreign2)) { //外键约束
						JOptionPane.showMessageDialog(frame, "Error, Foreign Key not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						textField_CouId.setText("");
						textField_StuId.setText("");
					}
					else {
						if(duplicate) { //重复插入时警告
							JOptionPane.showMessageDialog(frame, "Error, Record already exist !", "Error",JOptionPane.WARNING_MESSAGE);
						}
						else {
							try {
								stat.execute(sql);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(frame, "Inserted!", "Tips",JOptionPane.WARNING_MESSAGE);
						}
						textField_StuId.setText("");
						textField_CouId.setText("");
						textField_grade.setText("");
					}
				}
			}
			
		});
		button_insert.setBounds(261, 47, 113, 27);
		contentPane.add(button_insert);
		
		button_exit = new JButton("Exit");
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_exit.setBounds(261, 131, 113, 27);
		contentPane.add(button_exit);
		
		button_delete = new JButton("Delete");
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String couid = textField_CouId.getText();
				String stuid = textField_StuId.getText();
				String grade = textField_grade.getText();
				String sql = "delete from choose_course ";
				Boolean have_where = false;
				Boolean couid_exist = false;
				Boolean stuid_exist = false;
				Boolean grade_exist = false;
				if(!couid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from choose_course");
						while(result.next()) {
							if(result.getString("CouId").equals(couid)) {
								couid_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(couid_exist) {
						have_where = true;
						sql += "where CouId = '" + couid + "'";
					}
					else if(couid.equals("all")) {
						have_where = true;
					}
					else {
						JOptionPane.showMessageDialog(frame, "CouId not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!stuid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from choose_course");
						while(result.next()) {
							if(result.getString("StuId").equals(stuid)) {
								stuid_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(stuid_exist) { 
						if(have_where) {
							sql += " && StuId = '" + stuid + "'";
						}
						else {
							have_where = true;
							sql += "where StuId = '" + stuid + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "StuId not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!grade.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from choose_course");
						while(result.next()) {
							if(result.getString("grade").equals(grade)) {
								grade_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(grade_exist) {
						if(have_where) {
							sql += " && grade = '" + grade + "'";
						}
						else {
							have_where = true;
							sql += "where grade = '" + grade + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "grade not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				System.out.println(sql);
				if(!have_where) { //提示
					JOptionPane.showMessageDialog(frame, "All records will be deleted, if you want to continue, input 'all' in the first textfield!", "Warning",JOptionPane.WARNING_MESSAGE);
				}
				else { //执行删除操作
					try {
						stat.execute(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(frame, "Deleted!", "Tips",JOptionPane.WARNING_MESSAGE);
					textField_CouId.setText("");
					textField_StuId.setText("");
					textField_grade.setText("");
				}
			}
		});
		button_delete.setBounds(261, 90, 113, 27);
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}

}
