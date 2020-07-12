package Core.Screen;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;

public class ServerInfoScreen extends JTextArea {
    BufferedReader output;
    public ServerInfoScreen(BufferedReader in){
        output=in;
        setBackground(Color.white);
        setSize(960,400);
        //setPreferredSize(new Dimension(940,400));
        setLocation(0,0);
        setVisible(true);
        setEditable(false);
        setLineWrap(true);
    }
    public JPanel getScrolling(){
        JPanel out = new JPanel();
        out.setVisible(true);
        JScrollPane pane =  new JScrollPane(this);
        pane.setLocation(0,0);
        pane.setVisible(true);
        pane.setPreferredSize(new Dimension(this.getWidth()+20,this.getHeight()));
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        out.add(pane);
        out.setSize(this.getWidth()+20,this.getHeight()+5);

        return out;
    }
    public void refresh(){
        try {
            append(output.readLine()+"\n");

        } catch (SocketException en){
            System.out.println("server stoped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
