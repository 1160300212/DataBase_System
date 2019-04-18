package Lab1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Table_student extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_StuId;
	private JTextField textField_StuName;
	private JTextField textField_StuSex;
	private JTextField textField_StuAge;
	private JTextField textField_DeptNum;
	private JTextField textField_ClassNum;
	private JLabel lbl_StuId;
	private JLabel lbl_StuName;
	private JLabel lbl_StuSex;
	private JLabel lbl_StuAge;
	private JLabel lbl_DeptNum;
	private JLabel lbl_ClassNum;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;

	/**
	 * Create the frame.
	 */
	public Table_student(Statement stat) {
		frame = new JFrame("Table_Student");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);

		
		lbl_StuId = new JLabel("StuId");
		lbl_StuId.setBounds(14, 38, 72, 18);
		contentPane.add(lbl_StuId);
		
		lbl_StuName = new JLabel("StuName");
		lbl_StuName.setBounds(14, 72, 72, 18);
		contentPane.add(lbl_StuName);
		
		lbl_StuSex = new JLabel("StuSex");
		lbl_StuSex.setBounds(14, 105, 72, 18);
		contentPane.add(lbl_StuSex);
		
		lbl_StuAge = new JLabel("StuAge");
		lbl_StuAge.setBounds(14, 139, 72, 18);
		contentPane.add(lbl_StuAge);
		
		lbl_DeptNum = new JLabel("DeptNum");
		lbl_DeptNum.setBounds(14, 176, 72, 18);
		contentPane.add(lbl_DeptNum);
		
		lbl_ClassNum = new JLabel("ClassNum");
		lbl_ClassNum.setBounds(14, 213, 72, 18);
		contentPane.add(lbl_ClassNum);
		
		textField_StuId = new JTextField();
		textField_StuId.setBounds(83, 35, 123, 24);
		contentPane.add(textField_StuId);
		textField_StuId.setColumns(10);
		
		textField_StuName = new JTextField();
		textField_StuName.setBounds(83, 69, 123, 24);
		contentPane.add(textField_StuName);
		textField_StuName.setColumns(10);
		
		textField_StuSex = new JTextField();
		textField_StuSex.setBounds(83, 102, 123, 24);
		contentPane.add(textField_StuSex);
		textField_StuSex.setColumns(10);
		
		textField_StuAge = new JTextField();
		textField_StuAge.setBounds(83, 136, 123, 24);
		contentPane.add(textField_StuAge);
		textField_StuAge.setColumns(10);
		
		textField_DeptNum = new JTextField();
		textField_DeptNum.setBounds(83, 173, 123, 24);
		contentPane.add(textField_DeptNum);
		textField_DeptNum.setColumns(10);
		
		textField_ClassNum = new JTextField();
		textField_ClassNum.setBounds(83, 210, 123, 24);
		contentPane.add(textField_ClassNum);
		textField_ClassNum.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stuid = textField_StuId.getText();
				String stuname = textField_StuName.getText();
				String stusex = textField_StuSex.getText();
				String stuage = textField_StuAge.getText();
				String deptnum = textField_DeptNum.getText();
				String classnum = textField_ClassNum.getText();
				if(stuid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, StuId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(stuname.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, StuName can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(deptnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, DeptNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(classnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, ClassNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into student(StuId, StuName";
					if(!stusex.equals("")) {
						sql += ", StuSex";
					}
					if(!stuage.equals("")) {
						sql += ", StuAge";
					}
					sql += ", DeptNum, ClassNum) value('" + stuid + "', '" + stuname + "', ";
					if(!stusex.equals("")) {
						sql += "'" + stusex + "', ";
					}
					if(!stuage.equals("")) {
						sql += stuage + ", ";
					}
					sql += "'" + deptnum + "', '" + classnum + "')";
					System.out.println(sql);
					Boolean duplicate = false;
					Boolean foreign1 = false;
					Boolean foreign2 = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("StuId").equals(stuid)) {
								duplicate = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try { //检查外键是否存在
						result = stat.executeQuery("select * from department");
						while(result.next()) {
							if(result.getString("DeptNum").equals(deptnum)) {
								foreign1 = true;
							}
						}
						result = stat.executeQuery("select * from class");
						while(result.next()) {
							if(result.getString("ClassNum").equals(classnum)) {
								foreign2 = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(!(foreign1 && foreign2)) { //外键约束
						JOptionPane.showMessageDialog(frame, "Error, Foreign Key not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						textField_DeptNum.setText("");
						textField_ClassNum.setText("");
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
						textField_StuName.setText("");
						textField_StuSex.setText("");
						textField_StuAge.setText("");
						textField_DeptNum.setText("");
						textField_ClassNum.setText("");
					}
				}
			}
			
		});
		button_insert.setBounds(261, 34, 113, 27);
		contentPane.add(button_insert);
		
		button_exit = new JButton("Exit");
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_exit.setBounds(261, 204, 113, 27);
		contentPane.add(button_exit);
		
		button_delete = new JButton("Delete");
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stuid = textField_StuId.getText();
				String stuname = textField_StuName.getText();
				String stusex = textField_StuSex.getText();
				String stuage = textField_StuAge.getText();
				String deptnum = textField_DeptNum.getText();
				String classnum = textField_ClassNum.getText();
				String sql = "delete from student ";
				Boolean have_where = false;
				Boolean stuid_exist = false;
				Boolean stuname_exist = false;
				Boolean stusex_exist = false;
				Boolean stuage_exist = false;
				Boolean deptnum_exist = false;
				Boolean classnum_exist = false;
				if(!stuid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from student");
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
						have_where = true;
						sql += "where StuId = '" + stuid + "'";
					}
					else if(stuid.equals("all")) {
						have_where = true;
					}
					else {
						JOptionPane.showMessageDialog(frame, "StuId not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!stuname.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("StuName").equals(stuname)) {
								stuname_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(stuname_exist) { 
						if(have_where) {
							sql += " && StuName = '" + stuname + "'";
						}
						else {
							have_where = true;
							sql += "where StuName = '" + stuname + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "StuName not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!stusex.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("StuSex").equals(stusex)) {
								stusex_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(stusex_exist) {
						if(have_where) {
							sql += " && StuSex = '" + stusex + "'";
						}
						else {
							have_where = true;
							sql += "where StuSex = '" + stusex + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "StuSex not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!stuage.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("StuAge").equals(stuage)) {
								stuage_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(stuage_exist) {
						if(have_where) {
							sql += " && StuAge = '" + stuage + "'";
						}
						else {
							have_where = true;
							sql += "where StuAge = '" + stuage + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "StuAge not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!deptnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("DeptNum").equals(deptnum)) {
								deptnum_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(deptnum_exist) {
						if(have_where) {
							sql += " && DeptNum = '" + deptnum + "'";
						}
						else {
							have_where = true;
							sql += "where DeptNum = '" + deptnum + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "DeptNum not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!classnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("ClassNum").equals(classnum)) {
								classnum_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(classnum_exist) {
						if(have_where) {
							sql += " && ClassNum = '" + classnum + "'";
						}
						else {
							have_where = true;
							sql += "where ClassNum = '" + classnum + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "ClassNum not exist!", "Error",JOptionPane.WARNING_MESSAGE);
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
					textField_StuId.setText("");
					textField_StuName.setText("");
					textField_StuSex.setText("");
					textField_StuAge.setText("");
					textField_DeptNum.setText("");
					textField_ClassNum.setText("");
				}
			}
		});
		button_delete.setBounds(261, 113, 113, 27);
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}

}
