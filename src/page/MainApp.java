package page;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp extends JFrame implements ActionListener{
	JPanel p_north;
	URL[] url = new URL[3];
	String[] path={"/login.png","/content.png","/etc.png"};
	ImageIcon[] icon = new ImageIcon[3];
	JButton[] menu = new JButton[3];
	//���������� �����Ѵ�
	LoginForm loginform;
	Content content;
	JPanel p_center;//���������� ������
	Etc etc = new Etc();
	//JPanel[] page = new JPanel[3];
	
	ArrayList<JPanel> arr;
	
	public MainApp() {
		arr = new ArrayList<JPanel>();
		p_center =new JPanel();
		p_north = new JPanel();
		for(int i =0; i<path.length;i++){
			url[i] =this.getClass().getResource(path[i]);
			menu[i] = new JButton(new ImageIcon(url[i]));
			p_north.add(menu[i]);
			menu[i].addActionListener(this);
		}
		add(p_north,BorderLayout.NORTH);
		
		loginform = new LoginForm();
		content = new Content();
		p_center.add(loginform);  //�α��� ��
		p_center.add(content);  //������
		p_center.add(etc);  //��Ÿ������
		add(p_center);
		
		arr.add(loginform);
		arr.add(content);
		arr.add(etc);
		
		setSize(700,590);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
			for(int i =0;i<arr.size();i++){
				if(menu[i]==obj){
				arr.get(i).setVisible(true);
			}else{
				arr.get(i).setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new MainApp();
	}
	
}
