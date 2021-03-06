package Core.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class banButton extends JButton {
    private PrintWriter writer;
    private JList list;


    public banButton(){
        setText("Ban");
        setVisible(true);
        setSize(75,25);
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
            Frame f = new Frame();
            int re = JOptionPane.showConfirmDialog(f,"Are you sure");
            if(re==JOptionPane.YES_OPTION) {
                writer.println("ban add " + list.getSelectedValue()+" 999 years "+JOptionPane.showInputDialog(f,"Enter reason"));
            }
        }
    }
}
