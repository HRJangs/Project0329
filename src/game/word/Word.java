package game.word;
//게임에 등장할 대상 단어가 각각y축을 따로 갖고, 대량으로 만들어져야 하기 때문에
//결국 재사용성을 위한 코드집합인 클래스로 가자

import java.awt.Graphics;

public class Word {
	int y;
	int x;
	int velX;
	int velY; //단어가 움직일 속도
	String name;
	public Word(String name, int x, int y) {
		//단어가 태어날때 갖추어야할 코드값
		this.name=name;
		this.x=x;
		this.y=y;
	}
	
	//이 객체에 반영될 데이터 변화 코드
	public void tick(){
		y+=5;
		
	}
	//그 반영된 데이틀 이용하여 화면에 그리기
	public void render(Graphics g){
		g.drawString(name, x, y);
	}
	
}
