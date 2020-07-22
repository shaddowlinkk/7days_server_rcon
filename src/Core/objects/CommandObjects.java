package Core.objects;

import Core.Enums.BasicCommands;
import Core.Enums.BotmanCommands;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class CommandObjects extends JTextField {
    private PrintWriter writer;
    private Thread Server;
    private JTextArea screen;
    private int power;
    public CommandObjects(int power){
        this.power=power;
        setVisible(true);
        setSize(800,25);
        setColumns(16);
        addActionListener(new commandListener());
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }
    public <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }

    private class commandListener implements ActionListener{

     @Override
     public void actionPerformed(ActionEvent e) {
         String temp= getText();
         setText("");
         String command = (temp.split(" ").length>1)? temp.split(" ")[0]:temp;
         try {
             if (isInEnum(command, BasicCommands.class) || isInEnum(command.split("-")[1], BotmanCommands.class)) {
                 if (power<=BasicCommands.valueOf(command).getPower()){
                     writer.println(temp);
                 }else {
                     JFrame f = new JFrame();
                     JOptionPane.showMessageDialog(f,"You do not have permission to run that command");
                 }
                 if(BotmanCommands.valueOf(command.split("-")[1]).getPower()>=power){
                     writer.println(temp);
                 }else {
                     JFrame f = new JFrame();
                     JOptionPane.showMessageDialog(f,"You do not have permission to run that command");
                 }
             }
         }catch (ArrayIndexOutOfBoundsException en){

         }
     }
 }
}
