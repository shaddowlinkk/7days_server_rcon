package Core.objects;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class giveLevelButton extends JButton {
    private PrintWriter writer;
    private JList list;


    public giveLevelButton(){
        setText("set level");
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
            int level = Integer.parseInt(JOptionPane.showInputDialog("Enter level"));
            long i= getxp(level);
            writer.println("bm-givexp "+list.getSelectedValue()+" "+getxp(level));

        }
    }
    private long getxp(int lvl){
        if(lvl==1){
            return 0;
        }else if(lvl>=60){
            return ((10000*(Math.round((Math.pow(1.05,60)))))+getxp(lvl-1));
        }
        else return ((10000*(Math.round((Math.pow(1.05,lvl)))))+getxp(lvl-1));
    }
}
