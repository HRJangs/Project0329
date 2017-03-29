package game.word;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ItemListener , Runnable, ActionListener{
	JPanel p_west; //왼쪽 컨트롤 영역
	JPanel p_center; //단어 그래픽 처리 영역
	JLabel la_user;//게임 로그인 유저명
	JLabel la_score;//게임점수
	Choice choice;
	JTextField t_input;//게임 입력창
	JButton bt_start;//게임시작
	JButton bt_pause;//게임시작
	GameWindow gamewindow;
	String res ="C:/java_workspace2/Project0329/res";
	FileInputStream fis;
	InputStreamReader reader; 
	BufferedReader buffr; //문자기반 버퍼 스트림
	ArrayList<String> wordList = new ArrayList<String>();  //조사한 단어를 담아놓자 게임에 써먹기 위해
	ArrayList<Word> words = new ArrayList<Word>();
	Thread thread;//단어게임을 진행할 스레드
	Word word;
	boolean flag=true;
	
	public GamePanel(GameWindow gamewindow) {
		p_west = new JPanel();
		//이 영역은 지금부터 그림을 그릴 영역이다
		p_center= new JPanel(){
			public void paintComponent(Graphics g) {
				//기존그림 지우기
				g.setColor(Color.white);
				g.fillRect(0, 0, 750, 700);
				g.setColor(Color.blue);
				//모든 워드들에 대한 render();호출
				for(int i=0;i<words.size();i++){
					words.get(i).render(g);
				}
			}
		};
		this.gamewindow=gamewindow;
		setLayout(new BorderLayout());
		setBackground(Color.MAGENTA);
		setPreferredSize(new Dimension(900, 700));
		la_user  =new JLabel("장현령 님");
		la_score = new JLabel("0점");
		choice = new Choice();
		t_input = new JTextField(12);
		bt_start = new JButton("start");
		bt_pause =new JButton("pause");
		
		choice.add("▼단어 선택");
		choice.addItemListener(this);
		choice.setPreferredSize(new Dimension(135, 40));
		t_input.setPreferredSize(new Dimension(135, 40));
		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.CYAN);
		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);
		
		bt_start.addActionListener(this);
		add(p_center);
		add(p_west,BorderLayout.WEST);
		setVisible(false);//최초 안보임
		getCategory();
	
	}
	//초이스 컴포넌트에 채워질 파일명 조사하기
	public void getCategory(){
		
		File file = new File(res);
		//파일 디렉토리 섞여있는 배열 반환
		File[] fileList = file.listFiles();
		/*
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].isFile()){
				String name=fileList[i].getName();
				String[] arr = name.split("\\.");
				if(arr[1].equals("txt")){
					choice.add(name);
				}
			}
		}*/
		ArrayList<String> dirList = new ArrayList<String>();
		//디렉토리만 골라내자
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].isFile()){
				dirList.add(fileList[i].getName());
				if(dirList.get(i).substring(dirList.get(i).length()-3, dirList.get(i).length()).equals("txt")){
					choice.add(dirList.get(i).substring(0, dirList.get(i).length()-4));
				}
			}
		}
	}
	//단어읽어오기
	public void getWord(){
		try {
			int index = choice.getSelectedIndex();
				if(index!=0){
						fis = new FileInputStream(res+"/"+choice.getSelectedItem()+".txt");
						reader =new InputStreamReader(fis,"utf-8");
						buffr =new BufferedReader(reader);
						String data;
						while(true){
							data = buffr.readLine();
							if(data==null)break;
							wordList.add(data);
							createWord();
						}
				}
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(buffr!= null){
					try {
						buffr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(reader!=null){
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fis!=null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	} 
	//준비된 단어를 화면에 보여주자
	public void createWord(){
		for(int i=0;i<wordList.size();i++){
			Word word = new Word(wordList.get(i),i*(75)+10,100);
			//워드 객체명단 만들기
			words.add(word);
		}
	}
	public void startGame(){
		if(thread==null){
			thread = new Thread(this);
			thread.start();
		}
	}
	public void pauseGame(){
		flag=false;
	}
	public void itemStateChanged(ItemEvent e) {
		getWord();
	}
	public void actionPerformed(ActionEvent e) {
		Object obj= e.getSource();
		
		if(obj==bt_start){
			startGame();
		}else if(obj==bt_pause){
			pauseGame();
		}
	}
	public void run() {
		while(flag){
			try {
				thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//모든 단어들에 대해서 tick();
			//모든 단어들에 대해 render();
			for(int i=0;i<words.size();i++){
				words.get(i).tick();
			}
			p_center.repaint();
		}
	}
}
