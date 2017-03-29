package game.word;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm extends JPanel implements ActionListener{
	JPanel container; //borderlayout 적용
	JPanel p_center; //gridlayout 적용
	JPanel p_south;//남쪽에 버튼이 들어갈 예정
	
	JLabel la_ld,la_pw;
	JTextField t_id;
	JPasswordField p_pw;
	JButton bt_login;
	
	GameWindow gameWindow;
	GamePanel gamePanel;
	
	public LoginForm(GameWindow gameWindow) {
		container = new JPanel();
		p_center = new JPanel();
		p_south =new JPanel();
		
		la_ld = new JLabel("ID");
		la_pw = new JLabel("Password");
		t_id = new JTextField("batman",15);
		p_pw = new JPasswordField("1234",15);
		bt_login = new JButton("로그인");
		this.gameWindow =gameWindow;
		
		container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(2, 2));
		
		p_center.add(la_ld);
		p_center.add(t_id);
		p_center.add(la_pw);
		p_center.add(p_pw);
		p_south.add(bt_login);
		
		bt_login.addActionListener(this);
		
		container.add(p_center);
		container.add(p_south,BorderLayout.SOUTH);
		add(container);
		setPreferredSize(new Dimension(400, 100));
		//setBackground(Color.yellow);
	}

	public void loginCheck(){
		String id = t_id.getText();
		String pw  =new String(p_pw.getPassword());
		
		if(id.equals("batman")&&pw.equals("1234")){
			JOptionPane.showMessageDialog(this, "로그인 성공");
			gameWindow.setPage(1);
		}else{
			JOptionPane.showMessageDialog(this, "아이디 혹은 비밀번호가 일치하지 않습니다.");
		}
	}
	public void actionPerformed(ActionEvent e) {
		loginCheck();
	}
}
