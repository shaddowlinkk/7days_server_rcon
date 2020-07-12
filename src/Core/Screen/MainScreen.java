package Core.Screen;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    ServerInfoScreen serverInfo =new ServerInfoScreen();
    JPanel info = serverInfo.getScrolling();
    public MainScreen(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(1025,1000);
        setLayout(null);
        setBackground(Color.blue);

        info.setLocation(50,50);
        add(info);
        revalidate();
        repaint();
    }
}
