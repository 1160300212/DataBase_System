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

public class Table_teach_course extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_CouId;
	private JTextField textField_TeaId;
	private JTextField textField_Time;
	private JTextField textField_Classroom;
	private JLabel lblCouId;
	private JLabel lblTeaId;
	private JLabel lblTime;
	private JLabel lblClassroom;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;


	/**
	 * Create the frame.
	 */
	public Table_teach_course(Statement stat) {
		frame = new JFrame("Table_Course");
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
		
		lblTeaId = new JLabel("TeaId");
		lblTeaId.setBounds(32, 94, 72, 18);
		contentPane.add(lblTeaId);
		
		textField_TeaId = new JTextField();
		textField_TeaId.setBounds(109, 91, 123, 24);
		contentPane.add(textField_TeaId);
		textField_TeaId.setColumns(10);
		
		lblTime = new JLabel("Time");
		lblTime.setBounds(32, 135, 72, 18);
		contentPane.add(lblTime);
		
		textField_Time = new JTextField();
		textField_Time.setBounds(109, 132, 123, 24);
		contentPane.add(textField_Time);
		textField_Time.setColumns(10);
		
		lblClassroom = new JLabel("Classroom");
		lblClassroom.setBounds(32, 175, 72, 18);
		contentPane.add(lblClassroom);
		
		textField_Classroom = new JTextField();
		textField_Classroom.setBounds(109, 172, 123, 24);
		contentPane.add(textField_Classroom);
		textField_Classroom.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String couid = textField_CouId.getText();
				String teaid = textField_TeaId.getText();
				String time = textField_Time.getText();
				String classroom = textField_Classroom.getText();
				if(couid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, CouId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(teaid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, TeaId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into teach_course(";
					if(!time.equals("")) {
						sql += "Time, ";
					}
					if(!classroom.equals("")) {
						sql += "Classroom, ";
					}
					sql += "TeaId, CouId) value(";
					if(!time.equals("")) {
						sql += "'" + time + "', ";
					}
					if(!classroom.equals("")) {
						sql += "'" + classroom + "', ";
					}
					sql += "'" + teaid + "', '" + couid + "')";
					System.out.println(sql);
					Boolean duplicate = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from teach_course");
						while(result.next()) {
							if(result.getString("CouId").equals(couid) && result.getString("TeaId").equals(teaid)) {
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
					textField_CouId.setText("");
					textField_TeaId.setText("");
					textField_Time.setText("");
					textField_Classroom.setText("");
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
				String teaid = textField_TeaId.getText();
				String time = textField_Time.getText();
				String classroom = textField_Classroom.getText();
				String sql = "delete from teach_course ";
				Boolean have_where = false;
				Boolean couid_exist = false;
				Boolean teaid_exist = false;
				Boolean time_exist = false;
				Boolean classroom_exist = false;
				if(!couid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teach_course");
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
				if(!teaid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teach_course");
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
						if(have_where) {
							sql += " && TeaId = '" + teaid + "'";
						}
						else {
							have_where = true;
							sql += "where TeaId = '" + teaid + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "TeaId not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!time.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teach_course");
						while(result.next()) {
							if(result.getString("Time").equals(time)) {
								time_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(time_exist) {
						if(have_where) {
							sql += " && Time = '" + time + "'";
						}
						else {
							have_where = true;
							sql += "where Time = '" + time + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Time not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!classroom.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from teach_course");
						while(result.next()) {
							if(result.getString("Classroom").equals(classroom)) {
								classroom_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(classroom_exist) {
						if(have_where) {
							sql += " && Classroom = '" + classroom + "'";
						}
						else {
							have_where = true;
							sql += "where Classroom = '" + classroom + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Classroom not exist!", "Error",JOptionPane.WARNING_MESSAGE);
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
					textField_TeaId.setText("");
					textField_Time.setText("");
					textField_Classroom.setText("");
				}
			}
		});
		button_delete.setBounds(261, 90, 113, 27);
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}

}
