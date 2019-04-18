package Lab1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

public class Table_teacher extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_TeaId;
	private JTextField textField_TeaName;
	private JTextField textField_TeaSex;
	private JTextField textField_TeaAge;
	private JTextField textField_DeptNum;
	private JLabel lbl_TeaId;
	private JLabel lbl_TeaName;
	private JLabel lbl_TeaSex;
	private JLabel lbl_TeaAge;
	private JLabel lbl_DeptNum;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;

	/**
	 * Create the frame.
	 */
	public Table_teacher(Statement stat) {
		frame = new JFrame("Table_Teacher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);

		
		lbl_TeaId = new JLabel("TeaId");
		lbl_TeaId.setBounds(14, 38, 72, 18);
		contentPane.add(lbl_TeaId);
		
		lbl_TeaName = new JLabel("TeaName");
		lbl_TeaName.setBounds(14, 72, 72, 18);
		contentPane.add(lbl_TeaName);
		
		lbl_TeaSex = new JLabel("TeaSex");
		lbl_TeaSex.setBounds(14, 105, 72, 18);
		contentPane.add(lbl_TeaSex);
		
		lbl_TeaAge = new JLabel("TeaAge");
		lbl_TeaAge.setBounds(14, 139, 72, 18);
		contentPane.add(lbl_TeaAge);
		
		lbl_DeptNum = new JLabel("DeptNum");
		lbl_DeptNum.setBounds(14, 176, 72, 18);
		contentPane.add(lbl_DeptNum);
		
		textField_TeaId = new JTextField();
		textField_TeaId.setBounds(83, 35, 123, 24);
		contentPane.add(textField_TeaId);
		textField_TeaId.setColumns(10);
		
		textField_TeaName = new JTextField();
		textField_TeaName.setBounds(83, 69, 123, 24);
		contentPane.add(textField_TeaName);
		textField_TeaName.setColumns(10);
		
		textField_TeaSex = new JTextField();
		textField_TeaSex.setBounds(83, 102, 123, 24);
		contentPane.add(textField_TeaSex);
		textField_TeaSex.setColumns(10);
		
		textField_TeaAge = new JTextField();
		textField_TeaAge.setBounds(83, 136, 123, 24);
		contentPane.add(textField_TeaAge);
		textField_TeaAge.setColumns(10);
		
		textField_DeptNum = new JTextField();
		textField_DeptNum.setBounds(83, 173, 123, 24);
		contentPane.add(textField_DeptNum);
		textField_DeptNum.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String teaid = textField_TeaId.getText();
				String teaname = textField_TeaName.getText();
				String teasex = textField_TeaSex.getText();
				String teaage = textField_TeaAge.getText();
				String deptnum = textField_DeptNum.getText();
				if(teaid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, StuId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(teaname.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, StuName can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(deptnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, DeptNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into teacher(TeaId, TeaName";
					if(!teasex.equals("")) {
						sql += ", TeaSex";
					}
					if(!teaage.equals("")) {
						sql += ", TeaAge";
					}
					sql += ", DeptNum) value('" + teaid + "', '" + teaname + "', ";
					if(!teasex.equals("")) {
						sql += "'" + teasex + "', ";
					}
					if(!teaage.equals("")) {
						sql += teaage + ", ";
					}
					sql += "'" + deptnum + "')";
					System.out.println(sql);
					Boolean duplicate = false;
					Boolean foreign = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from teacher");
						while(result.next()) {
							if(result.getString("TeaId").equals(teaid)) {
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
						textField_TeaId.setText("");
						textField_TeaName.setText("");
						textField_TeaSex.setText("");
						textField_TeaAge.setText("");
						textField_DeptNum.setText("");
					}
				}
			}
			
		});
		button_insert.setBounds(256, 34, 113, 27);
		contentPane.add(button_insert);
		
		button_exit = new JButton("Exit");
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_exit.setBounds(256, 172, 113, 27);
		contentPane.add(button_exit);
		
		button_delete = new JButton("Delete");
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String teaid = textField_TeaId.getText();
				String teaname = textField_TeaName.getText();
				String teasex = textField_TeaSex.getText();
				String teaage = textField_TeaAge.getText();
				String deptnum = textField_DeptNum.getText();
				String sql = "delete from teacher ";
				Boolean have_where = false;
				Boolean teaid_exist = false;
				Boolean teaname_exist = false;
				Boolean teasex_exist = false;
				Boolean teaage_exist = false;
				Boolean deptnum_exist = false;
				if(!teaid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teacher");
						while(result.next()) {
							if(result.getString("TeaId").equals(teaid)) {
								teaid_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(teaid_exist) {
						have_where = true;
						sql += "where TeaId = '" + teaid + "'";
					}
					else if(teaid.equals("all")) {
						have_where = true;
					}
					else {
						JOptionPane.showMessageDialog(frame, "TeaId not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!teaname.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teacher ");
						while(result.next()) {
							if(result.getString("TeaName").equals(teaname)) {
								teaname_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(teaname_exist) { 
						if(have_where) {
							sql += " && TeaName = '" + teaname + "'";
						}
						else {
							have_where = true;
							sql += "where TeaName = '" + teaname + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "TeaName not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!teasex.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teacher");
						while(result.next()) {
							if(result.getString("TeaSex").equals(teasex)) {
								teasex_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(teasex_exist) {
						if(have_where) {
							sql += " && TeaSex = '" + teasex + "'";
						}
						else {
							have_where = true;
							sql += "where TeaSex = '" + teasex + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "TeaSex not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!teaage.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teacher");
						while(result.next()) {
							if(result.getString("TeaAge").equals(teaage)) {
								teaage_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(teaage_exist) {
						if(have_where) {
							sql += " && TeaAge = '" + teaage + "'";
						}
						else {
							have_where = true;
							sql += "where TeaAge = '" + teaage + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "TeaAge not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!deptnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teacher");
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
					textField_TeaId.setText("");
					textField_TeaName.setText("");
					textField_TeaSex.setText("");
					textField_TeaAge.setText("");
					textField_DeptNum.setText("");
				}
			}
		});
		button_delete.setBounds(256, 101, 113, 27);
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}
}
