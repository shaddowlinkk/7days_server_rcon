package Core.Util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerConection {
    private BufferedReader out;
    private PrintWriter writer;
    private InputStream in;
    private Socket server;
    ArrayList<String> data = new ArrayList<>();
    public ServerConection(){
        try {
            DataBaseCon baseCon = new DataBaseCon();
            byte[][] info = baseCon.getServerinfo("wildmount");
            server = new Socket(decryptPass(info[0]), 26939);
            InputStream input = server.getInputStream();
            OutputStream output = server.getOutputStream();
            BufferedReader readers = new BufferedReader(new InputStreamReader(input));
            String line = readers.readLine();
            writer = new PrintWriter(output, true);
            writer.println(decryptPass(info[1]));
            line = readers.readLine();
            while (!line.contains("successful")){
                writer.println(decryptPass(info[1]));
                line = readers.readLine();
            }
            out=readers;
            in=input;
        }catch (IOException e){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public BufferedReader getServerInfo(){
        return out;
    }
    public PrintWriter getOutStream(){
        return writer;
    }
    public InputStream getIn() {
        return in;
    }
    public void close(){
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getData() {
        return data;
    }
    public void starting(){
        try {

            String line = "";
            while (!line.contains("end session")) {
                line = out.readLine();
            }
            line = out.readLine();
            line = out.readLine();
            writer.println("lp");
            line = out.readLine();
            while (!line.contains("in the game")) {
                data.add(line);
                line = out.readLine();
            }
        }catch (IOException e){

        }
    }
    public String decryptPass(byte[] pass){
        String temp = null;
        try {
            KeyFile key = new KeyFile();
            Key aesKey = new SecretKeySpec(key.getKey(),"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,aesKey);
            temp= new String(cipher.doFinal(pass));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
