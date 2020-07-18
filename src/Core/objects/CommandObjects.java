package Core.objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class CommandObjects extends JTextField {
    private PrintWriter writer;
    private Thread Server;
    private JTextArea screen;
    public CommandObjects(){
        setVisible(true);
        setSize(800,25);
        setColumns(16);
        addActionListener(new commandListener());
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    private class commandListener implements ActionListener{

     @Override
     public void actionPerformed(ActionEvent e) {
         String temp= getText();
         setText("");
         writer.println(temp);
     }
 }
}
