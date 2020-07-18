package Core.Screen;

import Core.Handlers.PlayerListHandler;
import Core.Util.ServerConection;
import Core.objects.CommandObjects;
import Core.objects.PlayerlistObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainScreen extends JFrame {
    ServerConection ns_Servers ;
    ServerInfoScreen serverInfo ;
    CommandObjects com = new CommandObjects();
    PlayerListHandler playerlist  = new PlayerListHandler();
    PlayerlistObject list;
    Thread server;


    public MainScreen(){
        //setting up server conection
        ns_Servers = new ServerConection();
        ns_Servers.starting();
        list= playerlist.getList();
        playerlist.addData(ns_Servers.getData());
        list= playerlist.getList();

        serverInfo =new ServerInfoScreen(ns_Servers.getServerInfo(),ns_Servers.getIn());
        com.setWriter(ns_Servers.getOutStream());
        JPanel info = serverInfo.getScrolling();

        //seting up the thread for the server info to come in on
        Runnable ser = serverInfo;
        server = new Thread(ser);
        server.start();

        //jfram settings
        this.addWindowListener(new closelistener());
        this.setVisible(true);
        this.setSize(1600,1000);
        this.setLayout(null);
        this.setBackground(Color.gray);

        //elements in frame and thare settings
        info.setLocation(20,40);
        com.setLocation(20,470);
        list.setLocation(830, 470);
        add(list);
        add(com);
        add(info);

        //end of file stuff
        revalidate();
        repaint();
    }
    private class closelistener implements WindowListener{

        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            server.stop();
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}
