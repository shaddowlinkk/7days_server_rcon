package Core.Screen;

import Core.Handlers.PlayerListHandler;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class ServerInfoScreen extends JTextArea implements Runnable {
    BufferedReader output;
    InputStream in;
    JScrollPane pane;
    PlayerListHandler pl;
    int i =1;
    public ServerInfoScreen(BufferedReader in, InputStream st){
        this.in=st;
        output=in;
        setBackground(Color.white);
        setSize(1460,400);
        //setPreferredSize(new Dimension(940,400));
        setLocation(0,0);
        setVisible(true);
        setEditable(false);
        setLineWrap(true);
        setBackground(Color.black);
        setForeground(Color.green);
    }
    public JPanel getScrolling(){
        JPanel out = new JPanel();
        out.setVisible(true);
        out.setBorder(BorderFactory.createTitledBorder("Server Info"));
        pane =  new JScrollPane(this);
        pane.setLocation(0,0);
        pane.setVisible(true);
        pane.setPreferredSize(new Dimension(this.getWidth()+20,this.getHeight()));
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        out.add(pane);
        out.setSize(this.getWidth()+20,this.getHeight()+20);
        return out;
    }

    public void setPl(PlayerListHandler pl) {
        this.pl = pl;
    }

    //todo move to another class
    @Override
    public void run()  {
        while (true) {
            try {
                //if (in.available()>0) {
                    try {
                        String tmp = output.readLine() + "\n";
                        if(tmp.contains("INF Player connected")){
                            pl.addElement(tmp.split(",")[2].split("=")[1]);
                        }
                        if(tmp.contains("INF Player disconnected")){
                            pl.removeElement(tmp.split(",")[3].split("=")[1]);
                        }
                        if (!tmp.contains("by Telnet from")){
                            append(tmp);
                            selectAll();
                            //setCaretPosition(getCaretPosition() + tmp.length());
                            }
                    } catch (SocketException en) {
                        System.out.println("server stoped");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                //}
                Thread.sleep(1);
            } catch (InterruptedException en){

            }
        }
    }
}
