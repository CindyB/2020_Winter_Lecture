

import com.google.gson.Gson;

import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;

public class Controller implements Runnable {

    private final UI v;
    private final Data chatData;
    Socket socket;
    BufferedReader inMsg;
    PrintWriter outMsg;
    boolean status;
    Gson gson;
    Message m;
    Thread thread;
    Logger logger;

    public Controller(UI v, Data chatData) {
        this.v = v;
        this.chatData = chatData;
        logger = Logger.getLogger(this.getClass().getName());
        gson = new Gson();
    }

    public void appMain(){
        chatData.addObj(v.msgOut);
        v.addButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object obj = e.getSource();
                if(obj == v.exitButton){
                    System.exit(0);
                } else if(obj == v.loginButton){
                    v.id = v.idInput.getText();
                    v.outLabel.setText(" 대화명 : "+v.id);
                    v.cardLayout.show(v.tab,Constants.LOGIN_TXT);
                    connectServer();
                } else if(obj == v.logoutButton){
                    //로그아웃 메시지 전송
                    outMsg.println(gson.toJson(new Message(v.id,"","",Constants.LOGOUT_TXT)));
                    //대화창 클리어
                    v.msgOut.setText("");
                    //로그인 패널로 전환
                    v.cardLayout.show(v.tab,Constants.LOGIN_TXT);
                    outMsg.close();
                    try{
                        inMsg.close();
                        socket.close();
                    } catch(IOException ex){}
                    status = false;
                } else if (obj == v.msgInput){
                    try {
                        if(v.msgInput.getText(0,2).equals("/w")){
                            String[] googling = v.msgInput.getText().split(" ",3);
                            outMsg.println(gson.toJson(new Message(v.id,"",googling[2],Constants.WHISPER_TXT,googling[1])));
                        } else{
                            outMsg.println(gson.toJson(new Message(v.id,"",v.msgInput.getText(),Constants.MESSAGE_TXT)));
                        }
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    v.msgInput.setText("");
                }
            }
        });
    }
    public void connectServer(){
        try{
            //소켓 생성
            socket = new Socket("127.0.0.1",8888);
            logger.log(INFO,"[Client] Server 연결 성공");

            //입출력 스트림 생성
            inMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outMsg = new PrintWriter(socket.getOutputStream(),true);

            //서버에 로그인 메세지 전달
            m = new Message(v.id,"","",Constants.LOGIN_TXT);
            outMsg.println(gson.toJson(m));

            //메세지 수신을 위한 스레드 생성
            thread = new Thread(this);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msg;
        status = true;
        while(status){
            try{
                //메세지 수신 및 파싱
                msg = inMsg.readLine();
                m = gson.fromJson(msg, Message.class);

                //MultiChatData 객체로 데이터 갱신
                chatData.refreshData(m.getId()+">"+m.getMsg()+"\n");

                //커서를 현재 대화 메세지에 표시
                v.msgOut.setCaretPosition(v.msgOut.getDocument().getLength());
            } catch (IOException e) {
                logger.log(WARNING,"[MultiChatUI]메세지 스트림 종료!!");
                e.printStackTrace();
            }
        }
        logger.info("[MultiChatUI]" + thread.getName() + "메세지 수신 스레드 종료됨!!");
    }

    public static void main(String[] args){
        Controller app = new Controller(new UI(),new Data());
        app.appMain();
    }
}
