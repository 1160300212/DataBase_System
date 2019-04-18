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

public class Table_course extends JFrame {

	JFrame frame;
	private JPanel contentPane;
	private JTextField textField_CouId;
	private JTextField textField_CouName;
	private JTextField textField_CouHour;
	private JTextField textField_CouCredit;
	private JLabel lblCouId;
	private JLabel lblHCouName;
	private JLabel lblCouHour;
	private JLabel lblCoucredit;
	private JButton button_insert;
	private JButton button_delete;
	private JButton button_exit;


	/**
	 * Create the frame.
	 */
	public Table_course(Statement stat) {
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
		
		lblHCouName = new JLabel("CouName");
		lblHCouName.setBounds(32, 94, 72, 18);
		contentPane.add(lblHCouName);
		
		textField_CouName = new JTextField();
		textField_CouName.setBounds(109, 91, 123, 24);
		contentPane.add(textField_CouName);
		textField_CouName.setColumns(10);
		
		lblCouHour = new JLabel("CouHour");
		lblCouHour.setBounds(32, 135, 72, 18);
		contentPane.add(lblCouHour);
		
		textField_CouHour = new JTextField();
		textField_CouHour.setBounds(109, 132, 123, 24);
		contentPane.add(textField_CouHour);
		textField_CouHour.setColumns(10);
		
		lblCoucredit = new JLabel("CouCredit");
		lblCoucredit.setBounds(32, 175, 72, 18);
		contentPane.add(lblCoucredit);
		
		textField_CouCredit = new JTextField();
		textField_CouCredit.setBounds(109, 172, 123, 24);
		contentPane.add(textField_CouCredit);
		textField_CouCredit.setColumns(10);
		
		button_insert = new JButton("Insert");
		button_insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String couid = textField_CouId.getText();
				String couname = textField_CouName.getText();
				String couhour = textField_CouHour.getText();
				String coucredit = textField_CouCredit.getText();
				if(couid.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, CouId can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else if(couname.equals("")) { //插入空值时警告
					JOptionPane.showMessageDialog(frame, "Error, CouName can't be null !", "Error",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String sql = "insert into course(CouId, CouName";
					if(!couhour.equals("")) {
						sql += ", CouHour";
					}
					if(!coucredit.equals("")) {
						sql += ", CouCredit";
					}
					sql += ") value('" + couid + "', '" + couname + "'";
					if(!couhour.equals("")) {
						sql += ", " + couhour;
					}
					if(!coucredit.equals("")) {
						sql += ", " + coucredit;
					}
					sql += ")";
					System.out.println(sql);
					Boolean duplicate = false;
					ResultSet result;
					try { //检查主键是否存在
						result = stat.executeQuery("select * from course");
						while(result.next()) {
							if(result.getString("CouId").equals(couid)) {
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
					textField_CouName.setText("");
					textField_CouHour.setText("");
					textField_CouCredit.setText("");
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
				String couname = textField_CouName.getText();
				String couhour = textField_CouHour.getText();
				String coucredit = textField_CouCredit.getText();
				String sql = "delete from course ";
				Boolean have_where = false;
				Boolean couid_exist = false;
				Boolean couname_exist = false;
				Boolean couhour_exist = false;
				Boolean coucredit_exist = false;
				if(!couid.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from course");
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
				if(!couname.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from course");
						while(result.next()) {
							if(result.getString("CouName").equals(couname)) {
								couname_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(couname_exist) { 
						if(have_where) {
							sql += " && CouName = '" + couname + "'";
						}
						else {
							have_where = true;
							sql += "where CouName = '" + couname + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "CouName not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!couhour.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from course");
						while(result.next()) {
							if(result.getString("CouHour").equals(couhour)) {
								couhour_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(couhour_exist) {
						if(have_where) {
							sql += " && CouHour = '" + couhour + "'";
						}
						else {
							have_where = true;
							sql += "where CouHour = '" + couhour + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "CouHour not exist!", "Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				if(!coucredit.equals("")) { // 动态构造sql语句
					try { 
						ResultSet result = stat.executeQuery("select * from course");
						while(result.next()) {
							if(result.getString("CouCredit").equals(coucredit)) {
								coucredit_exist = true;
							}
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(coucredit_exist) {
						if(have_where) {
							sql += " && CouCredit = '" + coucredit + "'";
						}
						else {
							have_where = true;
							sql += "where CouCredit = '" + coucredit + "'";
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "CouCredit not exist!", "Error",JOptionPane.WARNING_MESSAGE);
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
					textField_CouName.setText("");
					textField_CouHour.setText("");
					textField_CouCredit.setText("");
				}
			}
		});
		button_delete.setBounds(261, 90, 113, 27);
		contentPane.add(button_delete);
		
		frame.setVisible(true);
	}

}
