package Core.Util;

import Core.Exceptions.ConnectionFailed;
import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Authclinet {
    private  int workload = 12;
    private  String hash;
    private byte[] ip;
    private byte[] pass;
    public  void Connection(String username, String password, String hash,  String server) throws ConnectionFailed {
        try {
            Socket client = new Socket("73.212.150.220", 8888);
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream in = in = new ObjectInputStream(client.getInputStream());
            this.hash = hash;
            out.writeObject(encryptPass(username));
            out.writeObject(encryptPass(password));
            out.writeObject(server);
            out.writeObject(hash);
            ip = (byte[]) in.readObject();
            pass = (byte[]) in.readObject();
        }catch (IOException e){
            throw new ConnectionFailed();

        }catch (ClassNotFoundException e) {
            throw new ConnectionFailed();
        }
    }

    public String getIp() {
        return decryptPass(ip);
    }

    public String getPass() {
        return decryptPass(pass);
    }

    public  byte[] encryptPass(String text){
        byte[] temp = null;
        try {
            KeyFile key = new KeyFile();
            Key aesKey = new SecretKeySpec(key.getKey(),"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,aesKey);
            temp= cipher.doFinal(text.getBytes());
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

    public  String decryptPass(byte[] pass){
        String temp = null;
        try {
            KeyFile key = new KeyFile();
            Key aesKey = new SecretKeySpec(hash.substring(7,23).getBytes(),"AES");
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

    public  String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }
}
