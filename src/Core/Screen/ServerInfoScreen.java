package Core.Screen;

import javax.swing.*;
import java.awt.*;

public class ServerInfoScreen extends JTextArea {
    public ServerInfoScreen(){
        setBackground(Color.white);
        setSize(600,400);
        setPreferredSize(new Dimension(600,400));
        setVisible(true);
        setEditable(false);
    }
    public JPanel getScrolling(){
        JPanel out = new JPanel();
        out.setPreferredSize(new Dimension(600,400));
        out.setVisible(true);
        JScrollPane pane =  new JScrollPane(this);
        pane.setVisible(true);
        pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        return out;
    }
}
