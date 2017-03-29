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
	JFileChooser chooser; //파일열기위한
	File file;//읽어들일 파일 
	Thread thread; //복사를 실행할 전용쓰레드, 메인메서드는 우리가 알고있는 그 실행부라 불리는 어플리케이션의 운영을 담당하는 역할을 수행한다. 따라서 절대 무한루프나 대기상태에 빠트려서는 안된다
	long total; //원본파일의 전체용량
	
	
	public CopyMain() {
		bar=new JProgressBar();
		bt_open=new JButton("열기");
		bt_save=new JButton("저장");
		bt_copy=new JButton("복사실행");
		
		t_open=new JTextField(35);
		t_save=new JTextField(35);
		
		chooser=new JFileChooser("C:/java_workspace2");
		
		bar.setPreferredSize(new Dimension(450,50));//bar 사이즈 강제로 늘리기
		//bar.setBackground(Color.PINK);
		bar.setString("0%");
		
		setLayout(new FlowLayout());
		
		add(bar);
		add(bt_open);
		add(t_open);
		add(bt_save);
		add(t_save);
		add(bt_copy);
		
		//버튼과 리스너 연결
		bt_open.addActionListener(this); //리스너
		bt_save.addActionListener(this);
		bt_copy.addActionListener(this);
		
		setSize(500,200);
		setVisible(true);
		setLocationRelativeTo(null); //절대모니터의 기준이 아닌 상대적으로 어디 옆에 이런것 대상이없으니 의존하지 안고 화면 가운데옴
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	@Override
	public void actionPerformed(ActionEvent e) { //평소같았으면 if(){}else if(){} 했겠지만 가독성이 떨어지니까 switch문(다중 if-else문)을 써보자 (나중에 )
		Object obj=e.getSource();//이벤트를 일으킨 이벤트 소스 (이벤트를 일으킨 객체 , 주체 여기서 컴포넌트 ! 버튼)
		
		if(obj== bt_open){
			open();			
		}else if(obj==bt_save){
			save();			
		}else if(obj==bt_copy){			
			//메인이 직접 복사를 수행하지 말고 쓰레드에게 시키자 
			//쓰레드 생성자에 runnable 구현 객체를 인수로 넣으면 runnable 객체에서 재정의한 run()메서드를 수행한다
			thread=new Thread(this); //runnable구현한 자
			thread.start();//우리꺼 run수행 
		}
	}
	public void open(){
		int result=chooser.showOpenDialog(this); //JFrame
		
		if(result==JFileChooser.APPROVE_OPTION){//긍정버튼 맞게 눌렀는지
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
	//메인쓰레드는 무한루프에 빠지면 안됌 쓰레드가 대신 작업을 해야했던것 ! 
	public void copy(){ //파일스트림 필요 입출력 
		FileInputStream fis=null;
		FileOutputStream fos=null;
	
		try {
			fis=new FileInputStream(file);
			fos=new FileOutputStream(t_save.getText());//저장할 경로!! 만들어질 파일 
			System.out.println("d");
			//생성된 스트림을 통해 데이터 읽기 !! 
			int data;		
			int count=0;
			
			while(true){				
				data=fis.read();//1byte 읽기				
				if(data==-1)break;				
				count++;
				fos.write(data);//1byte 출력!
				int v=(int)getPercent(count);
				//프로그래서바에 적용
				bar.setValue(v);
				//bar.setString();
			}
				JOptionPane.showMessageDialog(this, "복사완료");
				
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
	//진행율 = 100% 현크기/전체크기
	//현재 진행율 구하기 공식
	
	public long getPercent(int currentRead){//int 를 long으로 받자
		return (100*currentRead)/total;
		
		
	}
	public static void main(String[] args) {
		new CopyMain();
	}

}