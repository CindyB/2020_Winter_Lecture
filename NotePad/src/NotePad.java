

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;

import javax.swing.JSeparator;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

public class NotePad extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JMenuItem mnOpen,mnNew,mnSave,mnPrint,mnExit;
	private JMenuItem mnCancel,mnCut,mnCopy,mnPaste,mnDel,mnAll,mnDate,mnGo;	
	private JCheckBoxMenuItem mnWordWrap,mnState;
	private JTextArea textArea;
	private JToolBar toolBar;
	private JLabel lncol;
	//되돌리기(실행취소)
	private UndoManager undo;
	//텍스트 내용이 수정되었는지 확인하기 위해
	private int length;
	private String path;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel
					("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					NotePad frame = new NotePad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NotePad() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NotePad.class.getResource("/memo/memoimage.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("메모장");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("\uD30C\uC77C(F)");
		menuBar.add(mnNewMenu);
		
		mnNew = new JMenuItem("\uC0C8\uB85C\uB9CC\uB4E4\uAE30(N)");
		mnNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnNewMenu.add(mnNew);
		
		mnOpen = new JMenuItem("\uC5F4\uAE30(O)...");
		mnOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnNewMenu.add(mnOpen);
		
		mnSave = new JMenuItem("\uC800\uC7A5(S)");
		mnSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnNewMenu.add(mnSave);
		
		JMenuItem mnReSave = new JMenuItem("\uB2E4\uB978 \uC774\uB984\uC73C\uB85C \uC800\uC7A5(A)");
		mnNewMenu.add(mnReSave);
		
		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);
		
		JMenuItem mnPage = new JMenuItem("\uD398\uC774\uC9C0 \uC124\uC815(U)");
		mnNewMenu.add(mnPage);
		
		mnPrint = new JMenuItem("\uC778\uC1C4(P)");
		mnPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mnNewMenu.add(mnPrint);
		
		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);
		
		mnExit = new JMenuItem("\uB05D\uB0B4\uAE30(X)");
		mnNewMenu.add(mnExit);
		
		JMenu mnNewMenu_1 = new JMenu("\uD3B8\uC9D1(E)");
		mnNewMenu_1.setMnemonic('E');
		menuBar.add(mnNewMenu_1);
		
		mnCancel = new JMenuItem("\uC2E4\uD589\uCDE8\uC18C(U)");
		mnCancel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnCancel);
		
		JSeparator separator_2 = new JSeparator();
		mnNewMenu_1.add(separator_2);
		
		mnCut = new JMenuItem("\uC798\uB77C\uB0B4\uAE30(T)");
		mnCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnCut);
		
		mnCopy = new JMenuItem("\uBCF5\uC0AC(C)");
		mnCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnCopy);
		
		mnPaste = new JMenuItem("\uBD99\uC5EC\uB123\uAE30(P)");
		mnPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnPaste);
		
		mnDel = new JMenuItem("\uC0AD\uC81C(L)");
		mnDel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		mnNewMenu_1.add(mnDel);
		
		JSeparator separator_3 = new JSeparator();
		mnNewMenu_1.add(separator_3);
		
		JMenuItem mnFind = new JMenuItem("\uCC3E\uAE30(F)...");
		mnFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnFind);
		
		JMenuItem mnNextFind = new JMenuItem("\uB2E4\uC74C \uCC3E\uAE30(N)");
		mnNextFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnNextFind);
		
		JMenuItem mnChange = new JMenuItem("\uBC14\uAFB8\uAE30(R)...");
		mnChange.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnChange);
		
		mnGo = new JMenuItem("\uC774\uB3D9(G)");
		mnGo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnGo);
		
		JSeparator separator_4 = new JSeparator();
		mnNewMenu_1.add(separator_4);
		
		mnAll = new JMenuItem("\uBAA8\uB450 \uC120\uD0DD(A)");
		mnAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnNewMenu_1.add(mnAll);
		
		mnDate = new JMenuItem("\uC2DC\uAC04/\uB0A0\uC9DC(D)");
		mnDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mnNewMenu_1.add(mnDate);
		
		JMenu mnNewMenu_2 = new JMenu("\uC11C\uC2DD(O)");
		mnNewMenu_2.setMnemonic('O');
		menuBar.add(mnNewMenu_2);
		
		mnWordWrap = new JCheckBoxMenuItem("\uC790\uB3D9 \uC904 \uBC14\uAFC8(W)");
		mnWordWrap.setSelected(true);		
		mnNewMenu_2.add(mnWordWrap);
		
		JMenuItem mnFont = new JMenuItem("\uAE00\uAF34(F)...");
		mnNewMenu_2.add(mnFont);		
		
		JMenu mnNewMenu_3 = new JMenu("\uBCF4\uAE30(V)");
		menuBar.add(mnNewMenu_3);
		
		mnState = new JCheckBoxMenuItem("\uC0C1\uD0DC\uD45C\uC2DC\uC904");
		mnState.setEnabled(false);
		mnNewMenu_3.add(mnState);
		
		JMenu mnNewMenu_4 = new JMenu("\uB3C4\uC6C0\uB9D0(H)");
		menuBar.add(mnNewMenu_4);
		
		JMenuItem mntmNewMenuItem_13 = new JMenuItem("\uB3C4\uC6C0\uB9D0 \uBCF4\uAE30");
		mnNewMenu_4.add(mntmNewMenuItem_13);
		
		JMenuItem mntmNewMenuItem_14 = new JMenuItem("\uBA54\uBAA8\uC7A5 \uC815\uBCF4");
		mnNewMenu_4.add(mntmNewMenuItem_14);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		//되돌리기
		undo=new UndoManager();
		textArea.getDocument().addUndoableEditListener(undo);
		//키보드 리스너
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pos();
			}			
		});
		//마우스 리스너
		textArea.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				pos();
			}			
		});
		
			
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 26));
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		toolBar = new JToolBar();	
		toolBar.setVisible(false);
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		lncol = new JLabel("Ln:        Col:");
		toolBar.add(lncol);
		//파일
		mnOpen.addActionListener(this);
		mnNew.addActionListener(this);
		mnSave.addActionListener(this);
		mnPrint.addActionListener(this);
		mnExit.addActionListener(this);
		//편집
		mnCancel.addActionListener(this);
		mnCut.addActionListener(this);
		mnCopy.addActionListener(this);
		mnPaste.addActionListener(this);
		mnDel.addActionListener(this);
		mnAll.addActionListener(this);
		mnDate.addActionListener(this);
		mnGo.addActionListener(this);
		
		//서식
		mnWordWrap.addActionListener(this);
		//보기
		mnState.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item=(JMenuItem)e.getSource();
		
		if(item==mnNew) { //새로만들기
			newfile();
		}else if(item==mnOpen) {	//열기
			newOpen();
		}else if(item==mnSave) {	//저장
			newSave();
		}else if(item==mnPrint) {
			try {
				textArea.print();
			} catch (PrinterException e1) {				
				e1.printStackTrace();
			}
		}else if(item==mnExit) {//끝내기
			//저장할 내용이 있는 경우
			
			//저장할 내용이 없는 경우
			dispose();			
			//System.exit(0);
		}else if(item==mnCancel) {
			undo.undo();
		}else if(item==mnCut) { //잘라내기
			textArea.cut();
		}else if(item==mnCopy) {//복사
			textArea.copy();
		}else if(item==mnPaste) {//붙여넣기
			textArea.paste();			
		}else if(item==mnDel) {//삭제
			textArea.replaceSelection("");
		}else if(item==mnAll) { //모두선택
			textArea.selectAll();
		}else if(item==mnDate) {//시간/날짜
			SimpleDateFormat sdf=new SimpleDateFormat
					("a h:mm yyyy-MM-dd");
			textArea.replaceSelection(sdf.format(new Date()));
		}else if(item==mnGo) { //이동
			go();			
		}else if(item==mnWordWrap) {
			//mnWordWrap 의 선택여부를 확인한 후 
			//선택을 해제하면 상태표시줄의 메뉴를 활성화 시킴				
			if(!mnWordWrap.isSelected()) {
				mnState.setEnabled(true);
				mnState.setSelected(true);
				//toolbar 보여주기
				toolBar.setVisible(true);
			}else {			
				mnState.setEnabled(false);
				mnState.setSelected(false);
				//toolbar 감추기
				toolBar.setVisible(false);
			}			
		}else if(item==mnState) {
			//상태표시줄이 체크되면 toolbar 보여주기			
			//상태표시줄이 체크 해제되면 toolbar 감추기
			if(mnState.isSelected()) {
				toolBar.setVisible(true);
				pos();
			}else {
				toolBar.setVisible(false);
			}
		}
	}
	
	//현재 커서의 위치 계산
	private void pos() {
		int pos=textArea.getCaretPosition();
		int line=0,start=0;
		
		try {
				line=textArea.getLineOfOffset(pos)+1;
				start=textArea.getLineStartOffset(line-1);
		} catch (BadLocationException e) {			
			e.printStackTrace();
		}
		lncol.setText("Ln : "+line+", Col : "+(pos-start+1));		
	}	
	private void newfile() {		
		int curlen=textArea.getText().length();
		if(curlen>0) {//내용이 있는 경우
			//저장이 되어 있는 상태인지 아닌지에 따라 달라짐
			if(getTitle().equals("메모장")) {
				String options[]= {"저장","저장 안 함(N)","취소"};
				int retval=JOptionPane.showOptionDialog(this, 
						"변경 내용을 제목 없음에 저장하시겠습니까?",
						"메모장", JOptionPane.DEFAULT_OPTION,-1,
						null,options,options[0]);
				if(retval==0) {//저장 : JFileChooser 띄우기
					JFileChooser chooser=getFileChooser();
					//저장 창 띄우기
					retval=chooser.showSaveDialog(this);
					//사용자가 경로를 선택한 후 파일명을 넣고 저장을 누른 경우
					File f=null;
					if(retval==JFileChooser.APPROVE_OPTION) {
						//사용자가 선택한 경로와 파일명 가져오기
						f=chooser.getSelectedFile();			
						filecheck(f);						
					}
				}else if(retval==1) { //저장안함
					//textArea 에 있는 모든 내용 지우기
					textArea.setText("");
					setTitle("메모장");
				}else { //취소-아무것도 안함
				  }
			}
		}else{//내용이 없는 경우
			
		}
	}//newfile() 종료
	private JFileChooser getFileChooser() {
		JFileChooser chooser=new JFileChooser();
		//기존에 제공되는 파일형식 중 모든 파일 없애기
		chooser.setAcceptAllFileFilterUsed(false);
		//새로운 파일 형식 추가하기
		chooser.addChoosableFileFilter(new FileNameExtensionFilter
				("텍스트파일(*.txt)","txt"));
		//파일 형식 중에서 모든 파일 추가하기
		chooser.addChoosableFileFilter(chooser.getAcceptAllFileFilter());
		//파일이름 처음부터 지정하기
		chooser.setSelectedFile(new File("*.txt"));	
		return chooser;
	}
	private void filecheck(File f) {
		int retval=0;
		//파일명에 확장자를 포함하고 있는지 아닌지 여부에 따라
		//확장자 작업 필요  => endsWith() 문장의 끝나는 부분 확인가능
		if(!f.getName().endsWith(".txt")) {
			f=new File(f.getPath()+".txt");
		}
		//동일한 경로에 동일한 파일명이라면 
		//경고창을 띄워 덮어쓸 것인지,  다른 새로운 이름을 받을 것인지 결정해야함		
		if(f.exists()) {
			retval=JOptionPane.showConfirmDialog(textArea, 
					f.getName()+"가 이미 있습니다.\n 바꾸시겠습니까?",
					"다른 이름으로 저장 확인",JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE);
			//retVal==APPROVE_OPTION 면 저장
			if(retval==0) {
				save(f);
			}else{//다시 한번 저장 창 보여주기
				JFileChooser chooser=getFileChooser();
				chooser.setSelectedFile(new File(""));
				//저장 창 띄우기
				retval=chooser.showSaveDialog(this);
				if(retval==0) {
					f=chooser.getSelectedFile();
					filecheck(f);
				}
			}			
		}else {
			save(f);
		}
	}
	private void save(File f) {
		//파일저장
		try(BufferedWriter writer=new BufferedWriter(new FileWriter(f))) {
			textArea.setText(textArea.getText().replaceAll("\n", "\r\n"));
			writer.write(textArea.getText());
		}catch(Exception e) {
			e.printStackTrace();
		}
		//저장 후 textArea 지우기
		textArea.setText("");
	}	//save 종료
	private void newSave() {
		JFileChooser chooser=getFileChooser();
		int retval=chooser.showSaveDialog(this);
		if(retval==JFileChooser.APPROVE_OPTION) {
			//사용자가 선택한 경로와 파일명 가져오기
			File f=chooser.getSelectedFile();
			filecheck(f);
		}
	}
	private void newOpen()	{
		//내용이 없는 경우
				//사용자가 선택한 파일을 메모장에 보여주기
				if(textArea.getText().length()==0) {
					//getFileChooser() 메소드 사용
					JFileChooser chooser=getFileChooser();			
					//showOpenDialog() - 열기
					int retval=chooser.showOpenDialog(this);
					//사용자가 선택한 파일을 읽어서 메모장에 보여주기
					File f=null;
					if(retval==JFileChooser.APPROVE_OPTION) {
						f=chooser.getSelectedFile();
						
						try(BufferedReader buf=new BufferedReader(new FileReader(
											f.getPath()))){
							while(buf.readLine()!=null)
								textArea.append(buf.readLine()+"\n");					
						}catch(IOException e) {
							e.printStackTrace();
						}
						//메모장 타이틀 변경
						setTitle(f.getName());
						//파일을 열어서 가져온 후 변경된 내용이 있는지 확인하기 위해
						length=textArea.getText().length();
						path=f.getPath();
					}
				}else	{	//내용이 있는 경우
					if(getTitle().equals("메모장"))	{	//새파일->저장하고 파일 열기
						newfile();
						newOpen();
					}else	{
						//기존 파일을 열어 수정한 경우
						//변경내용을 저장하겠냐는 메세지창 띄우기
						int cur_len=textArea.getText().length();
						if(length>cur_len || length<cur_len)	{
							String options[]= {"저장","저장 안 함(N)","취소"};
							int retval=JOptionPane.showOptionDialog(this,"변경 내용을 "+path+"에 저장하시겠습니까?",
									"메모장",JOptionPane.DEFAULT_OPTION,-1,null,options,options[0]);
							if(retval==JFileChooser.APPROVE_OPTION)	{//저장
								File f=new File(path);
								save(f);
								setTitle("메모장");
								newOpen();
							}else	{//저장안함
								textArea.setText("");
								setTitle("메모장");
								newOpen();
							}
						}else	{	//파일을 연 후 수정하지 않은 경우
							textArea.setText("");
							setTitle("메모장");
							newOpen();
						}
						//수정하지 않은 경우
						//열기 화면 열기
					}
				}
			}//newOpen 종료
	private void go() {
		int curpos=textArea.getCaretPosition();
		try {
			//사용자의 커서가 있는 라인의 번호를 사용
			int line=textArea.getLineOfOffset(curpos)+1;
			
			//입력을 받도록 한다.
			String linenum=(String)JOptionPane.showInputDialog(this, "줄 번호(L):",
					"줄 이동", JOptionPane.CLOSED_OPTION,null,null,line);
			//라인번호를 넣어서 해당 라인의 컬럼 시작위치를 알아낸 다음
			int cur=textArea.getLineStartOffset(Integer.parseInt(linenum)-1);
			//커서를 시작 위치에 둔다
			textArea.select(cur, cur);
			
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}//go 종료
}
















