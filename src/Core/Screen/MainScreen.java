package Core.Screen;

import Core.Util.ServerConection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {
    ServerConection ns_Servers ;
    ServerInfoScreen serverInfo ;
    Timer time = new Timer(10 , new TimeListener());


    public MainScreen(){
        //setting up server conection
        time.start();
        ns_Servers = new ServerConection();
        serverInfo =new ServerInfoScreen(ns_Servers.getServerInfo());
        JPanel info = serverInfo.getScrolling();

        //jfram settings
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(1025,1000);
        this.setLayout(null);
        this.setBackground(Color.gray);

        //elements in frame and thare settings
        info.setLocation(10,40);
        add(info);

        //end of file stuff
        revalidate();
        repaint();
    }
    private class TimeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                serverInfo.refresh();
            }catch (NullPointerException en){

            }

        }
    }
}
