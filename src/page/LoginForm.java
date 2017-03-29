package page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JPanel{
	JPanel container; //borderlayout 적용
	JPanel p_center; //gridlayout 적용
	JPanel p_south;//남쪽에 버튼이 들어갈 예정
	
	JLabel la_ld,la_pw;
	JTextField t_id;
	JPasswordField p_pw;
	JButton bt_login;
	
	public LoginForm() {
		container = new JPanel();
		p_center = new JPanel();
		p_south =new JPanel();
		
		la_ld = new JLabel("ID");
		la_pw = new JLabel("Password");
		t_id = new JTextField(15);
		p_pw = new JPasswordField(15);
		bt_login = new JButton("로그인");
		
		
		container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(2, 2));
		
		p_center.add(la_ld);
		p_center.add(t_id);
		p_center.add(la_pw);
		p_center.add(p_pw);
		p_south.add(bt_login);
		
		container.add(p_center);
		container.add(p_south,BorderLayout.SOUTH);
		add(container);
		setPreferredSize(new Dimension(700, 400));
		setBackground(Color.yellow);
	}
	
	
}
