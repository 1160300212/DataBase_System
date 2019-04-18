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

public class Table_class extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_ClassNum;
	private JTextField textField_HeadTea;
	private JTextField textField_DeptNum;
	private JLabel lblClassNum;
	private JLabel lblHeadTea;
	private JLabel lblDeptNum;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;


	/**
	 * Create the frame.
	 */
	public Table_class(Statement stat) {
		frame = new JFrame("Table_Class");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		lblClassNum = new JLabel("ClassNum");
		lblClassNum.setBounds(32, 51, 72, 18);
		contentPane.add(lblClassNum);
		
		textField_ClassNum = new JTextField();
		textField_ClassNum.setBounds(109, 48, 123, 24);
		contentPane.add(textField_ClassNum);
		textField_ClassNum.setColumns(10);
		
		lblHeadTea = new JLabel("HeadTea");
		lblHeadTea.setBounds(32, 94, 72, 18);
		contentPane.add(lblHeadTea);
		
		textField_HeadTea = new JTextField();
		textField_HeadTea.setBounds(109, 91, 123, 24);
		contentPane.add(textField_HeadTea);
		textField_HeadTea.setColumns(10);
		
		lblDeptNum = new JLabel("DeptNum");
		lblDeptNum.setBounds(32, 135, 72, 18);
		contentPane.add(lblDeptNum);
		
		textField_DeptNum = new JTextField();
		textField_DeptNum.setBounds(109, 132, 123, 24);
		contentPane.add(textField_DeptNum);
		textField_DeptNum.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String classnum = textField_ClassNum.getText();
				String headtea = textField_HeadTea.getText();
				String deptnum = textField_DeptNum.getText();
				if(classnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, ClassNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(deptnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, DeptNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into class(ClassNum, ";
					if(!headtea.equals("")) {
						sql += "HeadTea, ";
					}
					sql += "DeptNum) value('" + classnum + "', ";
					if(!headtea.equals("")) {
						sql += "'" + headtea + "', ";
					}
					sql += "'" + deptnum + "')";
					System.out.println(sql);
					Boolean duplicate = false;
					Boolean foreign = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from class");
						while(result.next()) {
							if(result.getString("ClassNum").equals(classnum)) {
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
								foreign = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(!foreign) { //外键约束
						JOptionPane.showMessageDialog(frame, "Error, Foreign Key not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						textField_DeptNum.setText("");
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
						textField_ClassNum.setText("");
						textField_HeadTea.setText("");
						textField_DeptNum.setText("");
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
				String classnum = textField_ClassNum.getText();
				String headtea = textField_HeadTea.getText();
				String deptnum = textField_DeptNum.getText();
				String sql = "delete from class ";
				Boolean have_where = false;
				Boolean classnum_exist = false;
				Boolean headtea_exist = false;
				Boolean deptnum_exist = false;
				if(!classnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from class");
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
						have_where = true;
						sql += "where ClassNum = '" + classnum + "'";
					}
					else if(classnum.equals("all")) {
						have_where = true;
					}
					else {
						JOptionPane.showMessageDialog(frame, "ClassNum not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!headtea.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from class");
						while(result.next()) {
							if(result.getString("HeadTea").equals(headtea)) {
								headtea_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(headtea_exist) { 
						if(have_where) {
							sql += " && HeadTea = '" + headtea + "'";
						}
						else {
							have_where = true;
							sql += "where HeadTea = '" + headtea + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "HeadTea not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!deptnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from class");
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
					textField_ClassNum.setText("");
					textField_HeadTea.setText("");
					textField_DeptNum.setText("");
				}
			}
			
		});
		button_delete.setBounds(261, 90, 113, 27);
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}

}
