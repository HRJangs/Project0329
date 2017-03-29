package homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class CopyMain extends JFrame implements ActionListener, Runnable{
	JProgressBar bar;
	JButton bt_open, bt_save ,bt_copy;
	JTextField t_open,t_save;
	JFileChooser chooser; //���Ͽ�������
	File file;//�о���� ���� 
	Thread thread; //���縦 ������ ���뾲����, ���θ޼���� �츮�� �˰��ִ� �� ����ζ� �Ҹ��� ���ø����̼��� ��� ����ϴ� ������ �����Ѵ�. ���� ���� ���ѷ����� �����¿� ��Ʈ������ �ȵȴ�
	long total; //���������� ��ü�뷮
	
	
	public CopyMain() {
		bar=new JProgressBar();
		bt_open=new JButton("����");
		bt_save=new JButton("����");
		bt_copy=new JButton("�������");
		
		t_open=new JTextField(35);
		t_save=new JTextField(35);
		
		chooser=new JFileChooser("C:/java_workspace2");
		
		bar.setPreferredSize(new Dimension(450,50));//bar ������ ������ �ø���
		//bar.setBackground(Color.PINK);
		bar.setString("0%");
		
		setLayout(new FlowLayout());
		
		add(bar);
		add(bt_open);
		add(t_open);
		add(bt_save);
		add(t_save);
		add(bt_copy);
		
		//��ư�� ������ ����
		bt_open.addActionListener(this); //������
		bt_save.addActionListener(this);
		bt_copy.addActionListener(this);
		
		setSize(500,200);
		setVisible(true);
		setLocationRelativeTo(null); //���������� ������ �ƴ� ��������� ��� ���� �̷��� ����̾����� �������� �Ȱ� ȭ�� �����
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	@Override
	public void actionPerformed(ActionEvent e) { //��Ұ������� if(){}else if(){} �߰����� �������� �������ϱ� switch��(���� if-else��)�� �Ẹ�� (���߿� )
		Object obj=e.getSource();//�̺�Ʈ�� ����Ų �̺�Ʈ �ҽ� (�̺�Ʈ�� ����Ų ��ü , ��ü ���⼭ ������Ʈ ! ��ư)
		
		if(obj== bt_open){
			open();			
		}else if(obj==bt_save){
			save();			
		}else if(obj==bt_copy){			
			//������ ���� ���縦 �������� ���� �����忡�� ��Ű�� 
			//������ �����ڿ� runnable ���� ��ü�� �μ��� ������ runnable ��ü���� �������� run()�޼��带 �����Ѵ�
			thread=new Thread(this); //runnable������ ��
			thread.start();//�츮�� run���� 
		}
	}
	public void open(){
		int result=chooser.showOpenDialog(this); //JFrame
		
		if(result==JFileChooser.APPROVE_OPTION){//������ư �°� ��������
			file=chooser.getSelectedFile();
			t_open.setText(file.getAbsolutePath());		
			total=file.length();
		}		
	}
	public void save(){
		int result=chooser.showSaveDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			File file=chooser.getSelectedFile();
			t_save.setText(file.getAbsolutePath());			
		}
	}
	//���ξ������ ���ѷ����� ������ �ȉ� �����尡 ��� �۾��� �ؾ��ߴ��� ! 
	public void copy(){ //���Ͻ�Ʈ�� �ʿ� ����� 
		FileInputStream fis=null;
		FileOutputStream fos=null;
	
		try {
			fis=new FileInputStream(file);
			fos=new FileOutputStream(t_save.getText());//������ ���!! ������� ���� 
			System.out.println("d");
			//������ ��Ʈ���� ���� ������ �б� !! 
			int data;		
			int count=0;
			
			while(true){				
				data=fis.read();//1byte �б�				
				if(data==-1)break;				
				count++;
				fos.write(data);//1byte ���!
				int v=(int)getPercent(count);
				//���α׷����ٿ� ����
				bar.setValue(v);
				//bar.setString();
			}
				JOptionPane.showMessageDialog(this, "����Ϸ�");
				
			}catch (FileNotFoundException e) {		
				e.printStackTrace();	
			} catch (IOException e) {	
				e.printStackTrace();
			}finally{			
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {				
						e.printStackTrace();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e1) {							
							e1.printStackTrace();
					}
				}
			}
		}
	public void run(){
		copy();
	}
	//������ = 100% ��ũ��/��üũ��
	//���� ������ ���ϱ� ����
	
	public long getPercent(int currentRead){//int �� long���� ����
		return (100*currentRead)/total;
		
		
	}
	public static void main(String[] args) {
		new CopyMain();
	}

}