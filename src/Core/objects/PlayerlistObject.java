package Core.objects;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PlayerlistObject extends JList {
    DefaultListModel model;
    public PlayerlistObject(){
        setVisible(true);
        setLocation(830, 470);
        setSize(250,400);
         model = new DefaultListModel();
        setModel(model);
    }
}
