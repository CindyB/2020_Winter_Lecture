

import java.io.*;
import java.beans.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.print.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.border.*;

public class Jmemo extends JFrame implements ClipboardOwner, ActionListener, Printable, UndoableEditListener {
	Container contentPane;
	private JTextArea ta = new JTextArea();
	private JMenuBar mb = new JMenuBar();// 메뉴바
	private JMenu m1, m2, m3, m4;// 메뉴
	private JMenuItem mi11, mi12, mi13, mi14, mi15, mi16, mi17, mi21, mi22, mi23, mi24, mi25, mi26, mi27, mi28, mi29,
			mi2a, mi2b, mi32, mi41, mi42;// 메뉴 아이템
	private JCheckBoxMenuItem mi31;

	String st = "";
	File file;
	private JOptionPane jOptionPane;
	Object options[] = { "예", "아니오", "취소" };
	int cnt;

	JViewport viewPort;
	JScrollPane scrollPane;

	UndoManager undoManager = new UndoManager();

	public Jmemo(String title) {
		super(title);
		try { // 룩앤필 설정 : 크로스
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("자바 룩앤필 에러 :" + e.getMessage());
			JOptionPane.showMessageDialog(this, "설정하신 룩앤필이 존재하지 않습니다.", "자바 룩앤필 에러", JOptionPane.ERROR_MESSAGE);
		}
		Image img = getToolkit().getImage("img/memo.gif");
		setIconImage(img);// 윈도우 아이콘 변경
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});// 윈도우 종료시 프로그램 종료
		ta.getDocument().addUndoableEditListener(this);
		contentPane = getContentPane();
		scrollPane = new JScrollPane(ta);
		viewPort = scrollPane.getViewport();
		viewPort.add(ta);
		contentPane.add(scrollPane);// TextArea

		this.setJMenuBar(mb);// 메뉴바
		/////////////////////////////// 메뉴
		/////////////////////////////// 구성//////////////////////////////////////////////
		m1 = new JMenu("파일");

		mi11 = new JMenuItem("새로만들기");
		mi12 = new JMenuItem("열기");
		mi13 = new JMenuItem("저장");
		mi14 = new JMenuItem("다른이름으로저장");
		mi15 = new JMenuItem("페이지설정");
		mi16 = new JMenuItem("인쇄");
		mi17 = new JMenuItem("끝내기");

		mi11.addActionListener(this);// 액션리스너 등록
		mi12.addActionListener(this);
		mi13.addActionListener(this);
		mi14.addActionListener(this);
		mi15.addActionListener(this);
		mi16.addActionListener(this);
		mi17.addActionListener(this);

		mi11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		mi12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		mi13.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		mi14.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		mi15.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
		mi16.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		mi17.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));

		mb.add(m1);

		m1.add(mi11);
		m1.add(mi12);
		m1.add(mi13);
		m1.add(mi14);
		m1.addSeparator();
		m1.add(mi15);
		m1.add(mi16);
		m1.addSeparator();
		m1.add(mi17);

		m2 = new JMenu("편집");

		mi21 = new JMenuItem("실행취소");
		mi22 = new JMenuItem(new DefaultEditorKit.CutAction());
		mi22.setText("잘라내기");
		mi23 = new JMenuItem(new DefaultEditorKit.CopyAction());
		mi23.setText("복사");
		mi24 = new JMenuItem(new DefaultEditorKit.PasteAction());
		mi24.setText("붙여넣기");
		mi25 = new JMenuItem("삭제");
		mi26 = new JMenuItem("찾기");
		mi27 = new JMenuItem("다음찾기");
		mi28 = new JMenuItem("바꾸기");
		mi29 = new JMenuItem("이동");
		mi2a = new JMenuItem("모두선택");
		mi2b = new JMenuItem("시간날짜");

		mi21.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		mi22.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		mi23.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		mi24.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		// mi25.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE));
		mi26.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		// mi27.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,ActionEvent.META_MASK));
		mi28.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		mi29.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		mi2a.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		// mi2b.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,ActionEvent.META_MASK));

		mi21.addActionListener(this);
		mi22.addActionListener(this);
		mi23.addActionListener(this);
		mi24.addActionListener(this);
		mi25.addActionListener(this);
		mi26.addActionListener(this);
		mi27.addActionListener(this);
		mi28.addActionListener(this);
		mi29.addActionListener(this);
		mi2a.addActionListener(this);
		mi2b.addActionListener(this);

		mb.add(m2);

		m2.add(mi21);
		m2.addSeparator();
		m2.add(mi22);
		m2.add(mi23);
		m2.add(mi24);
		m2.add(mi25);
		m2.addSeparator();
		m2.add(mi26);
		m2.add(mi27);
		m2.add(mi28);
		m2.add(mi29);
		m2.addSeparator();
		m2.add(mi2a);
		m2.add(mi2b);

		m3 = new JMenu("서식");

		mi31 = new JCheckBoxMenuItem("자동줄바꿈");
		mi32 = new JMenuItem("글꼴");

		mi31.addActionListener(this);
		mi32.addActionListener(this);

		mb.add(m3);

		m3.add(mi31);
		m3.add(mi32);

		m4 = new JMenu("도움말");

		mi41 = new JMenuItem("도움말항목");
		mi42 = new JMenuItem("메모장정보");

		mi41.addActionListener(this);
		mi42.addActionListener(this);

		mb.add(m4);

		m4.add(mi41);
		m4.addSeparator();

		m4.add(mi42);

	}

	public boolean keyDown(Event e, int key) {
		cnt++;
		System.out.println("SD");
		return false;
	}

	public void actionPerformed(ActionEvent ae)// 메뉴 액션 처리
	{
		try {
			String gac = ae.getActionCommand();
			if (gac.equals("새로만들기")) {
				File f = getFile();
				if (!("".equals(ta.getText())) && f == null) {
					jOptionPane = new JOptionPane();
					jOptionPane.showOptionDialog(this,
							getTitle().replaceAll("- 메모장", "") + " 파일의 내용이 변경되 었습니다.\n 변경된 내용을 저장 하시겠습니까?", "메모장",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if (jOptionPane.getValue().equals(options[0]))
						saveDocument(true);
					else
						ta.setText("");
					/*
					 * JOptionPane.showOptionDialog(this,getTitle().replaceAll("- 메모장","")+
					 * " 파일의 내용이 변경되 었습니다.\n 변경된 내용을 저장 하시겠습니까?",
					 * "메모장",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,
					 * options,options[0]);
					 */
				} else if (cnt > 0) {
					JOptionPane.showOptionDialog(this,
							getTitle().replaceAll("- 메모장", "") + " 파일의 내용이 변경되 었습니다.\n 변경된 내용을 저장 하시겠습니까?", "메모장",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}
			} else if (gac.equals("열기")) {
				cnt = 0;
				ta.setText("");
				openDocument();
			} else if (gac.equals("저장")) {
				saveDocument(false);
			} else if (gac.equals("다른이름으로저장")) {
				saveDocument(true);
			} else if (gac.equals("페이지설정")) {
				printPage();
			} else if (gac.equals("인쇄")) {
				printDocument();
			} else if (gac.equals("끝내기")) {
				System.exit(0);
			} else if (gac.equals("실행취소")) {
				undoManager.undo();
			} else if (gac.equals("삭제")) {
				ta.replaceRange("", ta.getSelectionStart(), ta.getSelectionEnd());
			} else if (gac.equals("찾기")) {
				FindDialog fd = new FindDialog(this);
				fd.setBounds(getX() + 40, getY() + 70, 450, 150);
				fd.show();
			} else if (gac.equals("바꾸기")) {
				ChangeDialog cd = new ChangeDialog(this);
				cd.setBounds(getX() + 40, getY() + 70, 450, 200);
				cd.show();
			} else if (gac.equals("이동")) {
				MoveDialog md = new MoveDialog(this);
				md.setBounds(getX() + 40, getY() + 70, 250, 130);
				md.show();
				int line = md.lineNumber();
				// viewPort.setViewPosition(new Point(0,line));
				ta.setCaretPosition(line);
				// System.out.print(line);
			} else if (gac.equals("자동줄바꿈")) {
				if (mi31.getState())
					ta.setLineWrap(true);
				else
					ta.setLineWrap(false);
			} else if (gac.equals("글꼴")) {
				FontDialog ftd = new FontDialog(this);
				ftd.setBounds(getX() + 40, getY() + 70, 530, 320);
				ftd.show();
				Font ft = ftd.fontSet();
				ta.setFont(ft);
			} else if (gac.equals("모두선택")) {
				ta.selectAll();
				ta.requestFocus();
			} else if (gac.equals("메모장정보")) {
				HelpDialog hd = new HelpDialog(this);
				hd.setBounds(getX() + 40, getY() + 70, 411, 313);
				hd.show();
			} else if (gac.equals("도움말항목")) {
				// String [] cmdar={"/img/hh.exe","/img/notepad.chm","/img/hh.exe"};
				// Process p = Runtime.getRuntime().exec("/img/hh.exe notepad.chm");
				// Process p = Runtime.getRuntime().exec(cmdar);
				// Process p = Runtime.getRuntime().exec("C:/WINNT/help/hh.exe notepad.chm");
				Process p = Runtime.getRuntime().exec("C:/WINNT/notepad.exe");
			}
		} catch (Exception e) {
		}
	}

	public void openDocument() {
		JFileChooser chooser = new JFileChooser();
		System.out.println(chooser.getFileFilter());
		chooser.setDialogTitle("파일 열기");
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)// cancel 버튼이 눌려지면 취소
			return;
		File f = chooser.getSelectedFile();// 대화상자에서 선택된 파일객체 인스턴스를 구한다

		if (!f.exists()) {// 파일이 존재하지 않으면 에러 메세지를 띄운후 취소
			JOptionPane.showMessageDialog(this, file.getName() + " 파일을 찾을 수 없습니다.", "파일 열기 에러",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		openFile(f);
	}/////////////////////// openDocument() ////////////////////

	public void openFile(File file) {/// 파일을 읽어들여 jta에 표시

		BufferedReader in = null;// 버퍼 문자 입력 스트림
		// ta.setText("");//텍스트에리어 내용 초기화
		setTitle(file.getName());// 윈도우 제목을 파일 이름으로
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException fnfe) {// 에러 메세지
			System.err.println("파일 열기 에러 :" + file.getName() + "파일을 찾을 수 없습니다.");
			JOptionPane.showMessageDialog(this, file.getName() + "파일을 찾을 수 없습니다.", "파일 열기에러",
					JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			System.err.println("파일 열기 에러 :" + file.getName() + "파일을 찾을 수 없습니다.");
			JOptionPane.showMessageDialog(this, e.getMessage(), "파일 열기에러", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {//// 한 줄씩 읽어서 string형에 저장한후 ta에 보낸다
			String string = "";
			while ((string = in.readLine()) != null) {
				st = st + (string + '\n');
			}
			ta.setText("");
			ta.setText(st);
		} catch (IOException ie) {
			System.err.println("파일 읽기 에러 :" + ie.getMessage());
		}
		try {
			in.close();// 입력 스트림 닫는다.
		} catch (IOException ie) {
		}

		ta.setCaretPosition(0);// 커서를 처음으로 위치
		viewPort.setViewPosition(new Point(0, 0));// 위쪽을 보여줌
		System.out.print(file.getName());
		this.file = file;// 읽어들인 파일을 file멤버 필드로 설정
	}////////////////////////////////////// openFile(File file) end/////////////////

	public File getFile() {/// 문서 윈도우의 내용을 파일로 알림
		return file;
	}

	public void saveDocument(boolean isSaveAs) {// true면 새이름으로 false면 저장
		if (isSaveAs == true) {// 새이름으로 저장할 경우 파일 대화상자 표시
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("새 이름으로 저장");
			int returnVal = chooser.showSaveDialog(this);

			if (returnVal != JFileChooser.APPROVE_OPTION)
				return;
			File f = chooser.getSelectedFile();
			if (f.exists()) {// 파일 이름이 이미 존재하면 덮어쓸 것인지 물어본다
				Object options[] = { "예", "아니오" };
				if (JOptionPane.showOptionDialog(this, "파일이 이미 존재 합니다.덮어쓸까요?", "경고", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]) != 0)
					return;// 아니오를 선택하면 저장취소
			}
			saveFile(f);
		} else if (isSaveAs == false) {// 현재 문서 윈도우에 지정된 파일 인스턴스를 가져온다.
			File f = getFile();
			if (f == null) {
				saveDocument(true);
			}
			if (!f.exists()) {// 파일이 이미 존재하지 않으면 저장을 계속할 것인지 한번더 확인 한다.
				Object options[] = { "예", "아니오" };
				if (JOptionPane.showOptionDialog(this, "파일이 존재하지 않습니다.그래도 저장 할까요?", "경고", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]) != 0)
					return;
			}
		}
	}///////// saveDocument(boolean isSaveAs) end//////////////

	public void saveFile(File file) {/////////// file 인자로 지정된 파일로 현재 내용 저장
		PrintWriter out = null;// 문자 출력 스트림

		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		} catch (IOException ie) {
			System.err.println("파일 저장 에러 :" + file.getName() + "파일을 생성 할 수 없습니다.");
			JOptionPane.showMessageDialog(this, file.getName() + "파일을 생성 할 수 없습니다.", "파일 저장 에러",
					JOptionPane.ERROR_MESSAGE);
		}

		String string = ta.getText();
		out.print(string);// 실제 파일에 쓴다
		if (out.checkError()) {// print() 메서드는 IOException을 던지지 않으므로 직접 검사를 해보아야 한다.
			System.err.println("파일 쓰기 에러");
		} else {
			JOptionPane.showMessageDialog(this, file.getName() + " 파일을 저장 하였습니다.", "안내 메세지",
					JOptionPane.INFORMATION_MESSAGE);
		}
		out.close();// 출력 스트림을 닫는다.
		setTitle(file.getName());// 현재 파일 이름으로 윈도우 제목 설정
		this.file = file;// 현재 저장된 파일 객체 인스턴스를 멤버 필드값으로 저장
	}//////////////////////////////////////// saveFile(File file)
		//////////////////////////////////////// end//////////////////

	public void printPage() {///// 쪽 설정////
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(this);
		pj.pageDialog(pj.defaultPage());
	}/// 쪽설정 끝////

	public void printDocument() {//// 프린트//////
		try {
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.setPrintable(this);
			pj.printDialog();
			pj.print();
		} catch (Exception e) {
		}
	}//// 프린트 끝////

	public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {//////// Printable 구현/////////
		if (pi >= 1) {
			return Printable.NO_SUCH_PAGE;
		}
		Graphics g2 = (Graphics2D) g;
		g2.translate((int) pf.getImageableX(), (int) pf.getImageableY());
		this.paint(g2);
		return Printable.PAGE_EXISTS;
	}//////// Printable 구현 끝/////////

	public void lostOwnership(Clipboard cb, Transferable contents) {
	}// ClipboardOwner 구현

	public void undoableEditHappened(UndoableEditEvent undoe) {// UndoableEditListener 구현
		if (undoManager != null)
			undoManager.addEdit(undoe.getEdit());
	}// UndoableEditListener 구현 끝

	public static void main(String args[]) {
		Jmemo jm = new Jmemo("제목 없음 - 메모장");
		jm.setBounds(300, 200, 600, 400);
		jm.setVisible(true);
	}
}

class FindDialog extends JDialog implements ActionListener {
	private JLabel fjdl = new JLabel();
	private JTextField jfdt = new JTextField();
	private JButton jdsbn = new JButton();
	private JButton jfdc = new JButton();
	private JPanel jfdp = new JPanel();
	private TitledBorder titledBorder1;
	private JRadioButton jfdup = new JRadioButton();
	private JRadioButton jfddown = new JRadioButton();
	private JCheckBox jfdul = new JCheckBox();

	public FindDialog(Frame parent) {
		super(parent, "찾기", false);

		setResizable(false);

		titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.gray, Color.white), "방향");
		jfdc.setBounds(342, 63, 98, 26);
		jfdc.setText("취소");
		jfdc.addActionListener(this);
		jdsbn.setBounds(342, 28, 98, 26);
		jdsbn.setText("다음 찾기");
		jdsbn.addActionListener(this);
		jdsbn.setEnabled(false);// 첫 프로그램 실행시 선택 되지 않게 설정
		fjdl.setText("찾을 내용:");
		fjdl.setBounds(17, 29, 58, 24);
		this.getContentPane().setLayout(null);
		jfdt.setBounds(94, 29, 235, 21);
		jfdp.setBorder(titledBorder1);
		jfdp.setBounds(128, 59, 200, 45);
		jfdp.setLayout(null);
		jfdup.setText("위쪽");
		jfdup.setBounds(18, 20, 62, 14);
		jfddown.setText("아래쪽");
		jfddown.setBounds(108, 20, 62, 14);
		jfdul.setText("대/소문자 구분");
		jfdul.setBounds(20, 75, 101, 26);
		this.getContentPane().add(fjdl, null);
		this.getContentPane().add(jfdt, null);
		this.getContentPane().add(jdsbn, null);
		this.getContentPane().add(jfdc, null);
		this.getContentPane().add(jfdp, null);
		jfdp.add(jfdup, null);
		jfdp.add(jfddown, null);
		this.getContentPane().add(jfdul, null);

		// jfdt.requestFocus();
		jfdt.setCaretPosition(0);
	}

	public void actionPerformed(ActionEvent ae) {
		try {
			String gac = ae.getActionCommand();

			if (gac.equals("취소")) {
				dispose();
			} else if (gac.equals("다음 찾기")) {
			}
		} catch (Exception e) {
		}
	}
}

class ChangeDialog extends JDialog implements ActionListener {
	private JLabel jcdl1 = new JLabel();
	private JLabel jcdl2 = new JLabel();
	private JTextField jcdtf1 = new JTextField();
	private JTextField jcdtf2 = new JTextField();
	private JButton jcdb1 = new JButton();
	private JButton jcdb2 = new JButton();
	private JButton jcdb3 = new JButton();
	private JButton jcdb4 = new JButton();
	private JCheckBox jcdcb = new JCheckBox();

	public ChangeDialog(Frame parent) {
		super(parent, "바꾸기", false);

		setResizable(false);

		jcdcb.setText("대/소문자 구분");
		jcdcb.setBounds(24, 125, 178, 24);
		jcdb4.setBounds(318, 135, 106, 28);
		jcdb4.setText("취소");
		jcdb3.setBounds(318, 102, 106, 28);
		jcdb3.setText("모두 바꾸기");

		jcdb2.setBounds(318, 68, 106, 28);
		jcdb2.setText("바꾸기");
		jcdb1.setBounds(318, 35, 106, 28);
		jcdb1.setText("다음 찾기");
		jcdtf2.setBounds(100, 75, 197, 22);
		jcdtf1.setBounds(100, 39, 197, 22);
		jcdl2.setText("바꿀 내용: ");
		jcdl2.setBounds(20, 59, 79, 28);
		jcdl1.setText("찾을 내용: ");
		jcdl1.setBounds(20, 29, 79, 28);
		this.getContentPane().setLayout(null);
		this.getContentPane().add(jcdtf2, null);
		this.getContentPane().add(jcdl1, null);
		this.getContentPane().add(jcdl2, null);
		this.getContentPane().add(jcdtf1, null);
		this.getContentPane().add(jcdb1, null);
		this.getContentPane().add(jcdb2, null);
		this.getContentPane().add(jcdb3, null);
		this.getContentPane().add(jcdb4, null);
		this.getContentPane().add(jcdcb, null);
	}

	public void actionPerformed(ActionEvent ae) {
		dispose();
	}
}

class MoveDialog extends JDialog implements ActionListener {
	private JTextField jmdt = new JTextField();
	private JButton jmdb1 = new JButton();
	private JButton jmdb2 = new JButton();
	String getline = new String();

	public MoveDialog(Frame parent) {
		super(parent, "이동", true);

		jmdt.setBounds(14, 21, 115, 26);
		this.getContentPane().setLayout(null);
		jmdb1.setBounds(143, 16, 87, 29);
		jmdb1.setText("확인");
		jmdb1.addActionListener(this);
		jmdb2.setBounds(144, 60, 87, 29);
		jmdb2.setText("취소");
		jmdb2.addActionListener(this);
		this.getContentPane().add(jmdb1, null);
		this.getContentPane().add(jmdb2, null);
		this.getContentPane().add(jmdt, null);
	}

	public int lineNumber() {
		return Integer.parseInt(getline);
	}

	public void actionPerformed(ActionEvent ae) {
		try {
			String gac = ae.getActionCommand();

			if (gac.equals("확인")) {
				getline = jmdt.getText();
				dispose();
			} else if (gac.equals("취소")) {
				dispose();
			}
		} catch (Exception e) {
		}
	}
}

class FontDialog extends Dialog implements ActionListener {
	private Label jfdl1 = new Label();
	private Label jfdl2 = new Label();
	private Label jfdl3 = new Label();
	private Label jfdl4 = new Label();
	private Label jfdl5 = new Label();
	private Label jfdl6 = new Label();
	private TextField jfdtf1 = new TextField();
	private TextField jfdtf2 = new TextField();
	private TextField jfdtf3 = new TextField();
	private List jfdls1 = new List();
	private List jfdls2 = new List();
	private List jfdls3 = new List();
	private Choice jfdcb = new Choice();
	private Button jfdb1 = new Button();
	private Button jfdb2 = new Button();
	private TextField jfdtf4 = new TextField();
	Graphics g;

	Font f;
	String fontname = "SansSerif";
	int fontstyle = Font.PLAIN;
	int size = 8;

	String[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private String[] allSizes = { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
			"48", "72" };
	private String[] allStyle = { "보통", "기울임꼴", "굵게", "굵은 기울임꼴" };

	FontDialog(Frame parent) {
		super(parent, "글꼴", true);

		setResizable(false);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
		setLayout(null);

		for (int i = 0; i < allFonts.length; i++) {
			jfdls1.add(allFonts[i]);
		}

		for (int i = 0; i < 16; i++) {
			jfdls3.add(allSizes[i]);
		}

		for (int i = 0; i < 4; i++) {
			jfdls2.add(allStyle[i]);
		}
		jfdl1.setText("글꼴");
		jfdl1.setBounds(10, 24, 98, 26);
		jfdl2.setText("글꼴 스타일");
		jfdl2.setBounds(198, 26, 98, 26);
		jfdl3.setText("크기");
		jfdl3.setBounds(340, 24, 98, 26);
		jfdl4.setText("보기");
		jfdl4.setBounds(266, 157, 49, 25);
		jfdl5.setText("스크립트");
		jfdl5.setBounds(266, 236, 98, 25);
		jfdtf1.setBounds(10, 49, 181, 24);
		jfdtf2.setBounds(201, 49, 129, 24);
		jfdtf3.setBounds(340, 49, 91, 24);
		jfdls1.setBounds(10, 77, 181, 79);
		jfdls1.addActionListener(this);
		jfdls2.setBounds(201, 77, 129, 79);
		jfdls2.addActionListener(this);
		jfdls3.setBounds(340, 77, 91, 79);
		jfdls3.addActionListener(this);
		jfdcb.setBounds(258, 263, 180, 22);
		jfdb1.setBounds(436, 51, 82, 28);
		jfdb1.setLabel("확인");
		jfdb1.addActionListener(this);
		jfdb2.setBounds(436, 85, 82, 28);
		jfdb2.setLabel("취소");
		jfdb2.addActionListener(this);

		add(jfdcb);
		add(jfdl4);
		add(jfdl5);
		add(jfdl1);
		add(jfdtf1);
		add(jfdls1);
		add(jfdb1);
		add(jfdb2);
		add(jfdls3);
		add(jfdl3);
		add(jfdtf3);
		add(jfdls2);
		add(jfdtf2);
		add(jfdl2);

		jfdl6.setBounds(212, 190, 220, 40);
		jfdl6.setText("가나다AaBbYyZz");
		add(jfdl6);
	}

	public void paint(Graphics g) {
		g.setColor(Color.lightGray);
		g.draw3DRect(202, 182, 232, 50, false);
		g.setColor(Color.black);
	}

	public Font fontSet() {
		jfdls1.getSelectedItem();
		fontname = jfdls1.getSelectedItem();

		if (jfdls2.getSelectedItem().equals("보통"))
			fontstyle = Font.PLAIN;
		if (jfdls2.getSelectedItem().equals("기울임꼴"))
			fontstyle = Font.ITALIC;
		if (jfdls2.getSelectedItem().equals("굵게"))
			fontstyle = Font.BOLD;
		if (jfdls2.getSelectedItem().equals("굵은 기울임꼴"))
			fontstyle = Font.ITALIC + Font.BOLD;

		jfdtf3.setText(jfdls3.getSelectedItem());
		size = Integer.parseInt(jfdls3.getSelectedItem());

		return f = new Font(fontname, fontstyle, size);
	}

	public void actionPerformed(ActionEvent ae) {
		if ((ae.getSource()).equals(jfdls1)) {
			jfdtf1.setText(jfdls1.getSelectedItem());
			fontname = jfdls1.getSelectedItem();
			f = new Font(fontname, fontstyle, size);
			jfdl6.setFont(f);
		} else if ((ae.getSource()).equals(jfdls2)) {
			jfdtf2.setText(jfdls2.getSelectedItem());

			if (jfdls2.getSelectedItem().equals("보통"))
				fontstyle = Font.PLAIN;
			if (jfdls2.getSelectedItem().equals("기울임꼴"))
				fontstyle = Font.ITALIC;
			if (jfdls2.getSelectedItem().equals("굵게"))
				fontstyle = Font.BOLD;
			if (jfdls2.getSelectedItem().equals("굵은 기울임꼴"))
				fontstyle = Font.ITALIC + Font.BOLD;

			f = new Font(fontname, fontstyle, size);
			jfdl6.setFont(f);
		} else if ((ae.getSource()).equals(jfdls3)) {
			jfdtf3.setText(jfdls3.getSelectedItem());
			size = Integer.parseInt(jfdls3.getSelectedItem());
			f = new Font(fontname, fontstyle, size);
			jfdl6.setFont(f);
		} else if ((ae.getSource()).equals(jfdb1)) {
			dispose();
		} else if ((ae.getSource()).equals(jfdb2)) {
			dispose();
		}
	}
}

class HelpDialog extends Dialog implements ActionListener {// 도움말 새창
	Button ok;// 누르면 창이 닫히는 버튼
	Image img = getToolkit().getImage("img/memo.gif");
	Image mh = getToolkit().getImage("img/mh.gif");
	Image minfo = getToolkit().getImage("img/minfo.gif");
	Font f = new Font("굴림", Font.PLAIN, 12);

	HelpDialog(Frame parent) {
		super(parent, "메모장 정보", true);

		setResizable(false);

		setLayout(null);
		ok = new Button("확 인");
		ok.addActionListener(this);
		ok.setBounds(290, 280, 90, 25);
		add(ok);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
	}

	public void paint(Graphics g) {
		Insets insets = getInsets();

		g.setFont(f);
		g.drawImage(minfo, insets.left, insets.top, 410, 77, this);
		g.drawImage(mh, 15, 105, 32, 30, this);
		g.drawString("Wicrosoft (R) 메모장", 66, 120);
		g.drawString("버전 1.0 (빌드 2195: Service Pack 3)", 66, 137);
		g.drawString("Copyright (C) 1981-1999 Wicrosoft Corp.", 66, 153);
		g.drawString("이 제품은 다음 사용자에게 사용이 허가되었습니다.", 66, 200);
		g.drawString("jdk 깔린 컴텨", 66, 216);
		g.setColor(Color.lightGray);
		g.draw3DRect(66, 248, 334, 1, false);
		g.setColor(Color.black);
		String p = Runtime.getRuntime().totalMemory() / 1024 + "";
		g.drawString("Virtual Machine에서 사용할 수 있는 실제 메모리: " + p, 66, 265);
	}

	public void actionPerformed(ActionEvent ae) {
		dispose();
	}
}
