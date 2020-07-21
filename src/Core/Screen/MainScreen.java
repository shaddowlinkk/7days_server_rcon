package Core.Screen;

import Core.Handlers.PlayerListHandler;
import Core.Util.DataBaseCon;
import Core.Util.ServerConection;
import Core.objects.CommandObjects;
import Core.objects.PlayerlistObject;
import Core.objects.banButton;
import Core.objects.resetPlayerButton;
import Core.objects.giveLevelButton;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Hashtable;

public class MainScreen extends JFrame {
    private DataBaseCon dataBase;
    private ServerConection ns_Servers ;
    private ServerInfoScreen serverInfo ;
    private CommandObjects com = new CommandObjects();
    private PlayerListHandler playerlist  = new PlayerListHandler();
    private PlayerlistObject list;
    private Thread server;
    private banButton ban= new banButton();
    private resetPlayerButton resetPlayer = new resetPlayerButton();
    private giveLevelButton givexp = new giveLevelButton();

    private static int workload = 12;
    private boolean auth= false;
    private int power=-1;

    public MainScreen(){
        //setting up server conection
        setVisible(false);
        int result = JOptionPane.showConfirmDialog(this,"Do you want to register", "New User",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            register(this);
            JOptionPane.showMessageDialog(this,"You have registered an account please contact lead technical support\n to get the account activated");
            System.exit(0);
        }else{
            for (int i =0;i<3;i++){
                auth=login(this);
                if(auth){
                    break;
                }
            }
            if(!auth){
                System.exit(0);
            }
            if(power<0){
                JOptionPane.showMessageDialog(this, "please contact lead tech support to get you account activated");
                System.exit(0);
            }
        }
        ns_Servers = new ServerConection();
        ns_Servers.starting();
        list= playerlist.getList();
        playerlist.addData(ns_Servers.getData());
        list= playerlist.getList();
        serverInfo =new ServerInfoScreen(ns_Servers.getServerInfo(),ns_Servers.getIn());
        serverInfo.setPl(playerlist);
        com.setWriter(ns_Servers.getOutStream());
        ban.setWriter(ns_Servers.getOutStream());
        ban.setList(list);
        resetPlayer.setWriter(ns_Servers.getOutStream());
        resetPlayer.setList(list);
        givexp.setWriter(ns_Servers.getOutStream());
        givexp.setList(list);
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
        this.setTitle("NSTech server Rcon");

        //elements in frame and thare settings
        info.setLocation(20,40);
        com.setLocation(20,470);
        list.setLocation(830, 470);
        ban.setLocation(1100,470);
        resetPlayer.setLocation(1200,470);
        givexp.setLocation(1350,470);

        add(givexp);
        add(resetPlayer);
        add(ban);
        add(list);
        add(com);
        add(info);

        //end of file stuff
        setVisible(true);
        revalidate();
        repaint();
    }

    public void register(JFrame frame){
        //custom popups
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);
        int i = JOptionPane.showConfirmDialog(frame, panel, "register", JOptionPane.OK_CANCEL_OPTION);

        //authentication
        if (i==JOptionPane.OK_OPTION){
            try {
                dataBase = new DataBaseCon();
                dataBase.RegisterUser(username.getText(),hashPassword(new String(password.getPassword())));
            } catch (SQLException throwables) {

            }


        }else {
            System.exit(0);
        }

    }

    //loging in
    public Boolean login(JFrame frame) {

        //custom popups
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
        label.add(new JLabel("Username", SwingConstants.RIGHT));
        label.add(new JLabel("Password", SwingConstants.RIGHT));
        panel.add(label, BorderLayout.WEST);

        JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        JTextField username = new JTextField();
        controls.add(username);
        JPasswordField password = new JPasswordField();
        controls.add(password);
        panel.add(controls, BorderLayout.CENTER);

        int i = JOptionPane.showConfirmDialog(frame, panel, "login", JOptionPane.OK_CANCEL_OPTION);

        //authentication
        if (i==JOptionPane.OK_OPTION){
            String auth = null;
            try {
                dataBase = new DataBaseCon();
                auth = dataBase.getAuth(username.getText());
                power=dataBase.getPower(username.getText());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return checkPassword(new String(password.getPassword()), auth);
        }else {
            System.exit(0);
        }
        return false;
    }


    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
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
