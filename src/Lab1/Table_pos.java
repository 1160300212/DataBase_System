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

public class Table_pos extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_Pos;
	private JTextField textField_ClassNum;
	private JTextField textField_StuId;
	private JLabel lblPos;
	private JLabel lblClassNum;
	private JLabel lblStuId;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;


	/**
	 * Create the frame.
	 */
	public Table_pos(Statement stat) {
		frame = new JFrame("Table_Pos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		lblPos = new JLabel("Pos");
		lblPos.setBounds(32, 51, 72, 18);
		contentPane.add(lblPos);
		
		textField_Pos = new JTextField();
		textField_Pos.setBounds(109, 48, 123, 24);
		contentPane.add(textField_Pos);
		textField_Pos.setColumns(10);
		
		lblClassNum = new JLabel("ClassNum");
		lblClassNum.setBounds(32, 94, 72, 18);
		contentPane.add(lblClassNum);
		
		textField_ClassNum = new JTextField();
		textField_ClassNum.setBounds(109, 91, 123, 24);
		contentPane.add(textField_ClassNum);
		textField_ClassNum.setColumns(10);
		
		lblStuId = new JLabel("StuId");
		lblStuId.setBounds(32, 135, 72, 18);
		contentPane.add(lblStuId);
		
		textField_StuId = new JTextField();
		textField_StuId.setBounds(109, 132, 123, 24);
		contentPane.add(textField_StuId);
		textField_StuId.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pos = textField_Pos.getText();
				String classnum = textField_ClassNum.getText();
				String stuid = textField_StuId.getText();
				if(pos.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, Pos can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(classnum.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, ClassNum can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(stuid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, StuId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into pos(Pos, ClassNum, StuId) value('" + pos + "', '" + classnum +"', '" + stuid + "')";
					System.out.println(sql);
					Boolean duplicate = false;
					Boolean foreign1 = false;
					Boolean foreign2 = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from pos");
						while(result.next()) {
							if(result.getString("Pos").equals(pos) && result.getString("ClassNum").equals(classnum)) {
								duplicate = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try { //检查外键是否存在
						result = stat.executeQuery("select * from class");
						while(result.next()) {
							if(result.getString("ClassNum").equals(classnum)) {
								foreign1 = true;
							}
						}
						result = stat.executeQuery("select * from student");
						while(result.next()) {
							if(result.getString("StuId").equals(stuid)) {
								foreign2 = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(!(foreign1 && foreign2)) { //外键约束
						JOptionPane.showMessageDialog(frame, "Error, Foreign Key not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						textField_ClassNum.setText("");
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
						textField_ClassNum.setText("");
						textField_Pos.setText("");
					}
				}
			}
			
		});
		button_insert.setBounds(261, 47, 113, 27);
		contentPane.add(button_insert);
		
		button_delete = new JButton("Delete");
		button_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pos = textField_Pos.getText();
				String classnum = textField_ClassNum.getText();
				String stuid = textField_StuId.getText();
				String sql = "delete from pos ";
				Boolean have_where = false;
				Boolean pos_exist = false;
				Boolean classnum_exist = false;
				Boolean stuid_exist = false;
				if(!pos.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from pos");
						while(result.next()) {
							if(result.getString("Pos").equals(pos)) {
								pos_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(pos_exist) {
						have_where = true;
						sql += "where Pos = '" + pos + "'";
					}
					else if(pos.equals("all")) {
						have_where = true;
					}
					else {
						JOptionPane.showMessageDialog(frame, "Pos not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!classnum.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from pos");
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
						JOptionPane.showMessageDialog(frame, "Pos not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!stuid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from pos");
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
						JOptionPane.showMessageDialog(frame, "Pos not exist!", "Error",JOptionPane.WARNING_MESSAGE);
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
					textField_ClassNum.setText("");
					textField_Pos.setText("");
				}
			}
			
		});
		button_delete.setBounds(261, 90, 113, 27);
		contentPane.add(button_delete);
		
		button_exit = new JButton("Exit");
		button_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		button_exit.setBounds(261, 131, 113, 27);
		contentPane.add(button_exit);
		
		frame.setVisible(true);
	}

}
