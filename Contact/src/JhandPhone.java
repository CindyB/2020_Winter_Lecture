

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JhandPhone extends JFrame {

	static String fname = "c:\\temp\\jusoloc.txt";

	static class Address {
		String name;
		String phonenumber;

		Address(String s1, String s2) { // 생성자
			this.name = s1;
			this.phonenumber = s2;
		}
	}

	JPanel p1, p2;
	JLabel la1, la2;
	JTextField tf1, tf2;
	JTextArea ta;

	JButton[] b = new JButton[7];
	String[] txt = { "생성", "삭제", "수정", "검색", "Clear", "Exit","출력"};

	int i = 0;
	int temp = 0;
	int j = 0;
	int k = 0;

	HashMap<String, String> dic = new HashMap<String, String>();
	ArrayList<Address> tel = new ArrayList<Address>();

	JhandPhone() { // 생성자

		setTitle("연락처 관리 프로그램");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(null);

		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 6));
		p1.setSize(400, 30);
		p1.setLocation(460, 50);

		la1 = new JLabel("이   름");
		la2 = new JLabel("전화번호");

		tf1 = new JTextField();
		tf2 = new JTextField();

		p1.add(la1);
		p1.add(tf1);
		p1.add(la2);
		p1.add(tf2);

		p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 1));
		p2.setSize(320, 90);
		p2.setLocation(540, 150);

		MyActionListenter mal = new MyActionListenter();

		for (i = 0; i < b.length; i++) {
			b[i] = new JButton(txt[i]);
			b[i].addActionListener(mal);
		}
		for (i = 0; i < b.length; i++) {
			p2.add(b[i]);
		}

		ta = new JTextArea();
		JScrollPane js = new JScrollPane(ta);
		js.setSize(430, 300);
		js.setLocation(10, 10);
		add(js);

		add(p1);
		add(p2);

		setSize(900, 350);
		setVisible(true);
	}

	private void showGUI() {
		inputStream();
	}

	private void inputStream() {
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fname)); 
			String s, name = "", phonenumber = "";
			StringTokenizer str;
			System.out.println("-----------txt파일의 데이터----------");

			while ((s = reader.readLine()) != null) {
				System.out.println(s);
				
				str = new StringTokenizer(s, "\t");
				
				name = str.nextToken();
				
				phonenumber = str.nextToken();
				
				tel.add(new Address(name, phonenumber));
			}
			System.out.println("-------------------------------");
			reader.close();

			for (int i = 0; i < tel.size(); i++) {
				ta.append(tel.get(i).name + "\t" + tel.get(i).phonenumber + "\n");
			}
		} catch (IOException e) {
			System.out.println("입출력 오류");
		}
	}

	private void outputStream() {

		String str = "";

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("c:\\temp\\jusoloc.txt"));
			for (int i = 0; i < tel.size(); i++) {

				str = tel.get(i).name + "\t" + tel.get(i).phonenumber;

				writer.write(str);
				writer.newLine();
			}
			writer.close(); // close the file
		} catch (IOException e) {
			System.out.println("입출력 오류");//
		}
	}

	class MyActionListenter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JButton btn = (JButton) e.getSource();

				for (int i = 0; i < 7; i++) {
					
					if (btn.getText().equals(txt[i])) {
						int ITEM = i + 1;
						menu(ITEM);
					}
				}
			} catch (IOException ee) {
			}
		}
	}

	private void menu(int item) throws IOException {

		switch (item) {
		case 1:
			create();
			print();
			break;

		case 2:
			delete();
			outputStream();
			print();
			break;

		case 3:
			update();
			outputStream();
			print();
			break;

		case 4:
			search();
			break;

		case 5:
			clear();
			break;

		case 6:
			exit();
			break;
			
		case 7:
			print();
			break;
		}
	}

	private void create() throws IOException {

		String wstr = "";

		BufferedWriter bw = new BufferedWriter(new FileWriter(fname, true));

		tel.add(new Address(tf1.getText(), tf2.getText()));
		
		wstr = tf1.getText() + "\t" + tf2.getText();

		bw.write(wstr); // 파일에 문자열 쓰기
		bw.newLine();

		bw.close();

		System.out.println("저장완료"); // check
	}

	private void delete() throws IOException {

		String[] read_str = new String[50]; // 최대 연락처 개수를 50개로 가정
		String str = "";
		int count = 0;

		String name = tf1.getText();

		BufferedReader br = new BufferedReader(new FileReader(fname));

		if (!br.ready()) {
			System.out.printf("\n!! 연락처 파일이 없습니다. !!\n");
			return ;
		}
		for (int i = 0; i < tel.size(); i++) {
			str = br.readLine();
			if (str == null) {
				break;
			}
			
			if (tel.get(i).name.equals(name)) {
				tel.remove(i);
				System.out.println("삭제가 완료되었습니다.");				
			}
		}
		br.close();
	}

	private void update() throws IOException {

		Address update;
		int i, find_index = -1;
		String name = tf1.getText();
		String phonenumber = tf2.getText();
		String str = "", wstr = "";
		BufferedReader br = new BufferedReader(new FileReader(fname));

		update = new Address(name, phonenumber);

		if (!br.ready()) {
			System.out.printf("\n!! 연락처 파일이 없습니다. !!\n");
			return;
		}
		for (i = 0; i < tel.size(); i++) {
			
			str = br.readLine();
			if (str == null)
				break;
			if (tel.get(i).name.equals(name)) { // 이름이 존재한다면
				find_index = i;
				tel.set(find_index, update);
				break;
			}
		}
		System.out.println("업데이트가 완료되었습니다.");
		br.close();	
				
	}

	private void search() throws IOException {
		
		BufferedReader br = new BufferedReader(new FileReader(fname));
		String str = "";
		String name = tf1.getText(); // 찾을 이름

		for (int i = 0; i < tel.size(); i++) {
			str = br.readLine();
			if (str == null) {
				break;
			}		

			if (tel.get(i).name.equals(name)) {
				ta.setText(str);
				break;
			} else {
				ta.setText("검색 결과가 없습니다");
			}
		}
		System.out.println("검색완료");// check
	}

	private void clear() {
		
		try {
			tf1.setText("");
			tf2.setText("");
			ta.setText("");
			System.out.println("지우기 완료");// check

		} catch (NullPointerException e) {
			System.out.println("NullPointerException");
		}
	}

	private void exit() {

		outputStream();
		System.exit(0);
	}
	
	private void print()
	{
		tel.clear();
		ta.setText("");
		inputStream();
	}
	public static void main(String[] args) throws IOException {
		JhandPhone s = new JhandPhone();
		s.showGUI();
	}
}