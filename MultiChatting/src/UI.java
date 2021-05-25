

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UI extends JFrame implements Runnable {
    //로그인 패널
    private JPanel loginPanel;
    //로그인 버튼
    protected  JButton loginButton;

    //대화명 라벨
    private JLabel inLabel;
    //대화명 출력 라벨
    protected  JLabel outLabel;
    //대화명 입력 텍스트필드
    protected  JTextField idInput;

    //로그아웃 패넛
    private JPanel logoutPanel;
    //로그아웃 버튼
    protected  JButton logoutButton;

    //메세지 입력 패널 구성
    private JPanel msgPanel;
    //메세지 입력 텍스트 필드
    protected JTextField msgInput;
    //종료 버튼
    protected JButton exitButton;

    //화면 구성 전환을 위한 카드 레이아웃.
    protected  Container tab;
    protected  CardLayout cardLayout;

    //채팅 내용 출력 창
    protected  JTextArea msgOut;

    protected String id;

    //생성자
    public UI(){
        //메인 프레임 구성
        //super("::멀티챗::");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Client");
        setLayout(new BorderLayout());
        setSize(500,500);
        //로그인 패널 화면 구성
        loginPanel = new JPanel();
        //로그인 패널 레이아웃 설정
        loginPanel.setLayout(new BorderLayout());
        //로그인 입력필드/버튼 생성
        idInput = new JTextField(15);
        loginButton = new JButton("로그인");
        //로그인 패널에 위젯 구성
        inLabel = new JLabel("대화명 ");
        loginPanel.add(inLabel,BorderLayout.WEST);
        loginPanel.add(idInput,BorderLayout.CENTER);
        loginPanel.add(loginButton,BorderLayout.EAST);
        //로그아웃 패널 구성
        logoutPanel = new JPanel();
        //로그아웃 패널 레이아웃 구성
        logoutPanel.setLayout(new BorderLayout());
        outLabel = new JLabel();
        logoutButton = new JButton("로그아웃");

        //로그아웃 패널에 위젯 구성
        logoutPanel.add(outLabel,BorderLayout.CENTER);
        logoutPanel.add(logoutButton,BorderLayout.EAST);

        //메세지 패널 구성
        msgPanel = new JPanel();
        //메세지 입력 패널 레이아웃 구성
        msgPanel.setLayout(new BorderLayout());
        msgInput = new JTextField();

        //메세지 입력 패널에 위젯 구성
        exitButton = new JButton("종료");
        msgPanel.add(msgInput,BorderLayout.CENTER);
        msgPanel.add(exitButton,BorderLayout.EAST);
        add(msgPanel,BorderLayout.SOUTH);

        //로그인/로그아웃 패널 중 하나를 선택하는 CardLayout 패널
        tab = new JPanel();
        cardLayout = new CardLayout();
        tab.setLayout(cardLayout);
        tab.add(loginPanel,Constants.LOGIN_TXT);
        tab.add(logoutPanel,Constants.LOGOUT_TXT);
        add(tab,BorderLayout.NORTH);

        //메세지 출력 영역 초기화
        msgOut = new JTextArea("",10,30);
        //JTextArea의 내용을 수정하지 못하도록 한다.
        msgOut.setEditable(false);

        //메세지 출력 영역 스크롤바를 구성한다.
        //수직 스크롤 바는 항상 나타내고 수평 스크롤바는 필요할때 나타나도록 프로그래밍 한다.
        JScrollPane jsp = new JScrollPane(msgOut,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(jsp,BorderLayout.CENTER);

        setVisible(true);
    }

    public void addButtonActionListener(ActionListener listener){
        //이벤트 리스너 등록
        logoutButton.addActionListener(listener);
        loginButton.addActionListener(listener);
        exitButton.addActionListener(listener);
        msgInput.addActionListener(listener);
    }

    @Override
    public void run() {

    }
}
