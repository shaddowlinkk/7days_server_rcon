package Core.objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class resetPlayerButton extends JButton {
    private PrintWriter writer;
    private JList list;


    public resetPlayerButton(){
        setText("Reset Player");
        setVisible(true);
        setSize(125,25);
        addActionListener(new BanAction());
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public void setList(JList list) {
        this.list = list;
    }

    public class BanAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //add ban command 
            writer.println("bm-resetplayer "+list.getSelectedValue());
        }
    }
}
