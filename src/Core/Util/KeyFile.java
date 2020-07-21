package Core.Util;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class KeyFile {
    private RandomAccessFile IN;
    public byte[] getKey(){
        byte[] key = new byte[16];
        try {
            IN = new RandomAccessFile("auth.key","r");
            for (int i=0;i<16;i++){
                key[i]=IN.readByte();
            }
        } catch (FileNotFoundException e) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f,"You don't have the auth.key file or it is in the wrong place\n please contact tech support to get help");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }
}
