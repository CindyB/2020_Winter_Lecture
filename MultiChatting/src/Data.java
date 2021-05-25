

import javax.swing.JComponent;
import javax.swing.JTextArea;

public class Data {
    JTextArea msgOut;

    public void addObj(JComponent jComponent){
        msgOut = (JTextArea) jComponent;
    }

    public void refreshData(String data){
        msgOut.append(data);
    }
}
