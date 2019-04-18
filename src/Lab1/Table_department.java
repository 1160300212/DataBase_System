package Lab1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.DuplicateFormatFlagsException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Table_department extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_DeptNum;
	private JTextField textField_DeptName;
	private JTextField textField_Dean;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;


	/**
	 * Create the frame.
	 */
	public Table_department(Statement stat) {
		frame = new JFrame("Table_Department");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblDeptnum = new JLabel("DeptNum");
		lblDeptnum.setBounds(32, 51, 72, 18);
		contentPane.add(lblDeptnum);
		
		textField_DeptNum = new JTextField();
		textField_DeptNum.setBounds(109, 48, 123, 24);
		contentPane.add(textField_DeptNum);
		textField_DeptNum.setColumns(10);
		
		JLabel lblDeptname = new JLabel("DeptName");
		lblDeptname.setBounds(32, 94, 72, 18);
		contentPane.add(lblDeptname);
		
		textField_DeptName = new JTextField();
		textField_DeptName.setBounds(109, 91, 123, 24);
		contentPane.add(textField_DeptName);
		textField_DeptName.setColumns(10);
		
		JLabel lblDean = new JLabel("Dean");
		lblDean.setBounds(32, 135, 72, 18);
		contentPane.add(lblDean);
		
		textField_Dean = new JTextField();
		textField_Dean.setBounds(109, 132, 123, 24);
		contentPane.add(textField_Dean);
		textField_Dean.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deptnum = textField_DeptNum.getText();
				String deptname = textField_DeptName.getText();
				String dean = textField_Dean.getText();
				if(deptnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, DeptNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(deptname.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, DeptName can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into department(DeptNum, DeptName, ";
					if(!dean.equals("")) {
						sql += "Dean";
					}
					sql += ") value('" + deptnum + "', '" + deptname + "', ";
					if(!dean.equals("")) {
						sql += "'" + dean + "'";
					}
					sql += ")";
					System.out.println(sql);
					Boolean duplicate = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from department");
						while(result.next()) {
							if(result.getString("DeptNum").equals(deptnum)) {
								duplicate = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
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
					textField_DeptNum.setText("");
					textField_DeptName.setText("");
					textField_Dean.setText("");
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
		button_delete.setBounds(261, 90, 113, 27);
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deptnum = textField_DeptNum.getText();
				String deptname = textField_DeptName.getText();
				String dean = textField_Dean.getText();
				String sql = "delete from department ";
				Boolean have_where = false;
				Boolean deptnum_exist = false;
				Boolean deptname_exist = false;
				Boolean dean_exist = false;
				if(!deptnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from department");
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
						have_where = true;
						sql += "where DeptNum = '" + deptnum + "'";
					}
					else if(deptnum.equals("all")) {
						have_where = true;
					}
					else {
						JOptionPane.showMessageDialog(frame, "DeptNum not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!deptname.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from department");
						while(result.next()) {
							if(result.getString("DeptName").equals(deptname)) {
								deptname_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(deptname_exist) { 
						if(have_where) {
							sql += " && DeptName = '" + deptname + "'";
						}
						else {
							have_where = true;
							sql += "where DeptName = '" + deptname + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "DeptName not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!dean.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from department");
						while(result.next()) {
							if(result.getString("Dean").equals(dean)) {
								deptnum_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(dean_exist) {
						if(have_where) {
							sql += " && Dean = '" + dean + "'";
						}
						else {
							have_where = true;
							sql += "where Dean = '" + dean + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Dean not exist!", "Error",JOptionPane.WARNING_MESSAGE);
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
					textField_DeptNum.setText("");
					textField_DeptName.setText("");
					textField_Dean.setText("");
				}
			}
		});
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}

}
