package Core.objects;

import Core.Enums.BasicCommands;
import Core.Enums.BotmanCommands;
import Core.Util.DataBaseCon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.SQLException;

public class CommandObjects extends JTextField {
    private PrintWriter writer;
    private Thread Server;
    private JTextArea screen;
    private int power;
    private String name;
    public CommandObjects(int power,String name){
        this.name=name;
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
             DataBaseCon con = new DataBaseCon();
             if (isInEnum(command, BasicCommands.class) || isInEnum(command.split("-")[1], BotmanCommands.class)) {
                 try {
                     if (power <= BasicCommands.valueOf(command).getPower()) {
                         if(command.equals("say")){
                             if(temp.contains("\"")) {
                                 String ne = temp.split(" ")[0] + " \"<" + name + "> " + temp.substring(5, temp.length() - 1) + "\"";
                                 temp = ne;
                             }else {
                                 String ne = temp.split(" ")[0] + " \"<" + name + "> " + temp+ "\"";
                                 temp = ne;
                             }
                         }
                         con.LogUseage(name,temp);
                         writer.println(temp);
                     } else {
                         JFrame f = new JFrame();
                         JOptionPane.showMessageDialog(f, "You do not have permission to run that command");
                     }
                 }catch (IllegalArgumentException en){

                 }
                 if(BotmanCommands.valueOf(command.split("-")[1]).getPower()>=power){
                     con.LogUseage(name,temp);
                     writer.println(temp);
                 }else {
                     JFrame f = new JFrame();
                     JOptionPane.showMessageDialog(f,"You do not have permission to run that command");
                 }
             }
         }catch (ArrayIndexOutOfBoundsException en){

         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
     }
 }
}
