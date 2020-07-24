package Core.Util;

import Core.Exceptions.AuthenticationFailed;
import Core.Exceptions.ConnectionFailed;

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
    public ServerConection(String username, String password, String hash,  String servername) throws AuthenticationFailed {
        try {
            Authclinet client = new Authclinet();
            try {
                client.Connection(username,password,hash,servername);
            } catch (ConnectionFailed connectionFailed) {
                System.out.println("connection failed");
                throw new AuthenticationFailed();
            }
            server = new Socket(client.getIp(), 26939);
            InputStream input = server.getInputStream();
            OutputStream output = server.getOutputStream();
            BufferedReader readers = new BufferedReader(new InputStreamReader(input));
            String line = readers.readLine();
            writer = new PrintWriter(output, true);
            writer.println(client.getPass());
            line = readers.readLine();
            while (!line.contains("successful")){
                writer.println(client.getPass());
                line = readers.readLine();
            }
            out=readers;
            in=input;
        }catch (IOException e){

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

        }catch (NullPointerException e){
            System.out.println("could not start the player list");
        }
    }
}
